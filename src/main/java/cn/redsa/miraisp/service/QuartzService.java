package cn.redsa.miraisp.service;

import cn.redsa.miraisp.entity.AddJobDTO;
import cn.redsa.miraisp.entity.PageBean;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;

public interface QuartzService {

    PageBean getAllJobs(Long pageNo, Long pageSize);

    JobDataMap getJobData(String jGroup, String jName);

    void runJob(String jobGroup, String jobName) throws SchedulerException;
    void addJob(AddJobDTO addJobDTO) throws SchedulerException;

    void updateJobData(String jobGroup, String jobName, JobDataMap jobData);
    void pauseJob(String jobName, String jobGroup) throws SchedulerException;

    void resumeJob(String jobName, String jobGroup) throws SchedulerException;

    void rescheduleJob(String triggerGroup, String triggerName, String cron) throws SchedulerException;

    void deleteJob(String jobGroup, String jobName) throws SchedulerException;
}
