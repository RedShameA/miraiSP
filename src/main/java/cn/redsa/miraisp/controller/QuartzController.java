package cn.redsa.miraisp.controller;

import cn.redsa.miraisp.entity.AddJobDTO;
import cn.redsa.miraisp.entity.PageBean;
import cn.redsa.miraisp.service.QuartzService;
import cn.redsa.miraisp.vo.ResultMap;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *@Author: CJ
 *@Date: 2021-11-2 11:41
 */
@Slf4j
@RestController
@RequestMapping(path = "/quartz")
public class QuartzController {

    @Autowired
    private QuartzService quartzService;

    @RequestMapping("/list")
    public ResultMap list(Long pageNo, Long pageSize) {
        PageBean allJobs = quartzService.getAllJobs(pageNo, pageSize);
        return new ResultMap().success().data(allJobs);
    }
    @GetMapping("/jobData")
    public ResultMap getJobData(String jobGroup, String jobName){
        JobDataMap jobData = quartzService.getJobData(jobGroup, jobName);
        return new ResultMap().success().data(jobData);
    }

    @GetMapping("/runJob")
    public ResultMap runJob(String jobGroup, String jobName){
        try {
            quartzService.runJob(jobGroup, jobName);
            return new ResultMap().success().message("立即执行成功！");
        } catch (SchedulerException e) {
            return new ResultMap().error().message("立即执行失败！");
        }
    }

    /**
     * 新增定时任务
     * @return ResultMap
     */
    @PostMapping(path = "/addJob")
    public ResultMap addJob(@RequestBody @Validated AddJobDTO addJobDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = bindingResult.getFieldError().getDefaultMessage();
            return new ResultMap().fail().message(error);
        }
        try {
            quartzService.addJob(addJobDTO);
            return new ResultMap().success().message("添加任务成功");
        } catch (Exception e) {
            String errMsg = "未知错误！";
            if (e.getMessage().contains("Unable to store Trigger"))
                errMsg = "Trigger名已存在！";
            if (e.getMessage().contains("Unable to store Job"))
                errMsg = "Job名已存在！";
            if (e.getMessage().contains("CronExpression"))
                errMsg = "Cron表达式不合法！";
            e.printStackTrace();
            return new ResultMap().error().message(errMsg);
        }
    }

    @PostMapping("/updateJobData")
    public ResultMap updateJobData(@RequestBody  Map<String, Object> params){
        try{
            String jobGroup = (String) params.get("jobGroup");
            String jobName = (String) params.get("jobName");
            Map<String, Object> jobData = (Map<String, Object>) params.get("jobData");
            JobDataMap jobDataMap = new JobDataMap(jobData);
            quartzService.updateJobData(jobGroup, jobName, jobDataMap);
            return new ResultMap().success().message("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultMap().error().message("修改失败");
        }
    }

    /**
     * 暂停任务
     * @return ResultMap
     */
    @PostMapping(path = "/pauseJob")
    public ResultMap pauseJob(String jobName, String jobGroup) {
        try {
            quartzService.pauseJob(jobName, jobGroup);
            return new ResultMap().success().message("暂停任务成功");
        } catch (SchedulerException e) {
            e.printStackTrace();
            return new ResultMap().error().message("暂停任务失败");
        }
    }

    /**
     * 恢复任务
     * @return ResultMap
     */
    @PostMapping(path = "/resumeJob")
    public ResultMap resumeJob(String jobName, String jobGroup) {
        try {
            quartzService.resumeJob(jobName, jobGroup);
            return new ResultMap().success().message("恢复任务成功");
        } catch (SchedulerException e) {
            e.printStackTrace();
            return new ResultMap().error().message("恢复任务失败");
        }
    }

    /**
     * 重启任务
     *
     * @param triggerGroup 任务组
     * @param triggerName 任务名称
     * @param cron cron表达式
     * @return ResultMap
     */
    @PostMapping(path = "/rescheduleJob")
    public ResultMap rescheduleJob(String triggerGroup, String triggerName, String cron) {
        try {
            quartzService.rescheduleJob(triggerGroup, triggerName, cron);
            return new ResultMap().success().message("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMap().error().message("修改失败");
        }
    }

    /**
     * 删除任务
     *
     * @param jobGroup 任务组
     * @param jobName 任务名称
     * @return ResultMap
     */
    @GetMapping(path = "/deleteJob")
    public ResultMap deleteJob(String jobGroup, String jobName) {
        try {
            quartzService.deleteJob(jobGroup, jobName);
            return new ResultMap().success().message("删除任务成功");
        } catch (SchedulerException e) {
            e.printStackTrace();
            return new ResultMap().error().message("删除任务失败");
        }
    }

    /**
     * 查询任务
     *
     * @param pageNum 页码
     * @param pageSize 每页显示多少条数据
     * @return Map
     */
    //@GetMapping(path = "/queryjob")
    //@ResponseBody
    //public ResultMap queryjob(Integer pageNum, Integer pageSize) {
    //    PageInfo<JobAndTriggerDto> pageInfo = quartzService.getJobAndTriggerDetails(pageNum, pageSize);
    //    Map<String, Object> map = new HashMap<>();
    //    if (!StringUtils.isEmpty(pageInfo.getTotal())) {
    //        map.put("JobAndTrigger", pageInfo);
    //        map.put("number", pageInfo.getTotal());
    //        return new ResultMap().success().data(map).message("查询任务成功");
    //    }
    //    return new ResultMap().fail().message("查询任务成功失败，没有数据");
    //}
}
