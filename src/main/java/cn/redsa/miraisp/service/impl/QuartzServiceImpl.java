package cn.redsa.miraisp.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.redsa.miraisp.entity.AddJobDTO;
import cn.redsa.miraisp.entity.PageBean;
import cn.redsa.miraisp.job.HelloJob;
import cn.redsa.miraisp.service.QuartzService;
import cn.redsa.miraisp.utils.QuartzUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.quartz.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;

@Service
@Slf4j
public class QuartzServiceImpl implements QuartzService {

    @Resource
    private Scheduler scheduler;
    @Resource
    EntityManager entityManager;
    @Resource
    QuartzUtil quartzUtil;

    @Override
    public PageBean getAllJobs(Long pageNo, Long pageSize) {

        String countSql = "SELECT DISTINCT count(*) FROM QRTZ_JOB_DETAILS";
        long count = ((BigInteger)entityManager.createNativeQuery(countSql).getSingleResult()).longValue();
        String nativeSql = "SELECT \n" +
                "\tQRTZ_JOB_DETAILS.JOB_NAME,\n" +
                "\tQRTZ_JOB_DETAILS.JOB_GROUP,\n" +
                "\tQRTZ_JOB_DETAILS.JOB_CLASS_NAME,\n" +
                "\tQRTZ_TRIGGERS.TRIGGER_NAME,\n" +
                "\tQRTZ_TRIGGERS.TRIGGER_GROUP,\n" +
                "\tDATE_FORMAT( from_unixtime( QRTZ_TRIGGERS.START_TIME / 1000 ), '%Y-%m-%d %H:%i:%s' ) START_TIME,\n" +
                "\tDATE_FORMAT( from_unixtime( QRTZ_TRIGGERS.END_TIME / 1000 ), '%Y-%m-%d %H:%i:%s' ) END_TIME,\n" +
                "\tDATE_FORMAT( from_unixtime( QRTZ_TRIGGERS.PREV_FIRE_TIME / 1000 ), '%Y-%m-%d %H:%i:%s' ) PREV_FIRE_TIME,\n" +
                "\tDATE_FORMAT( from_unixtime( QRTZ_TRIGGERS.NEXT_FIRE_TIME / 1000 ), '%Y-%m-%d %H:%i:%s' ) NEXT_FIRE_TIME,\n" +
                "\tQRTZ_CRON_TRIGGERS.CRON_EXPRESSION,\n" +
                "\tQRTZ_CRON_TRIGGERS.TIME_ZONE_ID, \n" +
                "\tQRTZ_TRIGGERS.TRIGGER_STATE \n" +
                "FROM\n" +
                "\tQRTZ_JOB_DETAILS\n" +
                "\tLEFT JOIN QRTZ_TRIGGERS ON QRTZ_TRIGGERS.JOB_GROUP = QRTZ_JOB_DETAILS.JOB_GROUP AND QRTZ_TRIGGERS.JOB_NAME = QRTZ_JOB_DETAILS.JOB_NAME\n" +
                "\tLEFT JOIN QRTZ_CRON_TRIGGERS ON QRTZ_JOB_DETAILS.JOB_NAME = QRTZ_TRIGGERS.JOB_NAME \n" +
                "\tAND QRTZ_TRIGGERS.TRIGGER_GROUP = QRTZ_CRON_TRIGGERS.TRIGGER_GROUP AND QRTZ_TRIGGERS.TRIGGER_NAME = QRTZ_CRON_TRIGGERS.TRIGGER_NAME\n" +
                "\tLIMIT ?,?";

        //List resultList = entityManager.createNativeQuery(nativeSql).setParameter(1, 0).setParameter(2, 10).getResultList();
        Query query = entityManager.createNativeQuery(nativeSql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        Long start = (pageNo-1) * pageSize;

        query.setParameter(1, start);
        query.setParameter(2, pageSize);

        List<Map<String, Object>> resultList = query.getResultList();


        return new PageBean<>(count, resultList);
    }

    @Override
    public JobDataMap getJobData(String jobGroup, String jobName) {
        JobDataMap jobDetail = quartzUtil.getJobDataMap(jobGroup, jobName);

        return jobDetail;
    }

    @Override
    public void runJob(String jobGroup, String jobName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        scheduler.triggerJob(jobKey);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addJob(AddJobDTO addJobDTO) throws SchedulerException {
        // 构建JobDetail
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity(addJobDTO.getJobName(), "定时发送任务群组")
                .build();
        jobDetail.getJobDataMap().put("sender", addJobDTO.getSender());
        jobDetail.getJobDataMap().put("to", addJobDTO.getTo());
        jobDetail.getJobDataMap().put("msg", addJobDTO.getMsg());
        // 按新的cronExpression表达式构建一个新的trigger

        //NameMatcher<JobKey> jobKeyNameMatcher = NameMatcher.jobNameEquals(jName);


        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(IdUtil.fastUUID(), "定时发送触发器群组")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(addJobDTO.getCron()))
                .build();
        // 启动调度器
        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Override
    public void updateJobData(String jobGroup, String jobName, JobDataMap jobData) {
        quartzUtil.updateJobDataMap(jobGroup, jobName, jobData);
    }

    @Override
    public void pauseJob(String jobName, String jobGroup) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(jobName, jobGroup));

    }

    @Override
    public void resumeJob(String jobName, String jobGroup) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(jobName, jobGroup));
    }

    @Override
    public void rescheduleJob(String triggerGroup, String triggerName, String cron) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 按新的cronExpression表达式重新构建trigger
        if (trigger == null){
            return;
        }
        CronTrigger newTrigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionDoNothing())
                .build();
        // 按新的trigger重新设置job执行，重启触发器
        scheduler.rescheduleJob(triggerKey, newTrigger);
    }

    @Override
    public void deleteJob(String jobGroup, String jobName) throws SchedulerException {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobName, jobGroup));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobName, jobGroup));
        scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
    }
}
