package com.analizy.cmp.quartz.service.impl;

import cn.hutool.core.date.DateUtil;
import com.analizy.cmp.core.constant.DateFormatType;
import com.analizy.cmp.core.excp.CmpException;
import com.analizy.cmp.core.excp.QuartzException;
import com.analizy.cmp.core.resp.CmpResponse;
import com.analizy.cmp.core.resp.ListCmpResponse;
import com.analizy.cmp.quartz.config.TaskInfo;
import com.analizy.cmp.quartz.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author: wangjian
 * @date: 2021/01/18 10:03
 */
@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    @Qualifier("quartzScheduler")
    private Scheduler scheduler;

    /**
     * @param @return 参数
     * @return List<TaskInfo>    返回类型
     * @throws
     * @Title: list
     * @Description: 任务列表
     */
    @Override
    public ListCmpResponse<TaskInfo> queryJobList() {
        List<TaskInfo> list = new ArrayList<>();
        try {
            for (String groupJob : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(groupJob))) {
                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                    for (Trigger trigger : triggers) {
                        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                        String cronExpression = "";
                        String createTime = "";
                        String milliSeconds = "";
                        String repeatCount = "";
                        String startDate = "";
                        String endDate = "";
                        if (trigger instanceof CronTrigger) {
                            CronTrigger cronTrigger = (CronTrigger) trigger;
                            cronExpression = cronTrigger.getCronExpression();
                            createTime = cronTrigger.getDescription();
                        } else if (trigger instanceof SimpleTrigger) {
                            SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;
                            milliSeconds = simpleTrigger.getRepeatInterval() + "";
                            repeatCount = simpleTrigger.getRepeatCount() + "";
                            startDate = DateUtil.format(simpleTrigger.getStartTime(), DateFormatType.FORMAT_YYYYMMDD_HHMISS);
                            endDate = DateUtil.format(simpleTrigger.getEndTime(), DateFormatType.FORMAT_YYYYMMDD_HHMISS);
                        }
                        TaskInfo info = new TaskInfo();
                        info.setJobName(jobKey.getName());
                        info.setJobGroup(jobKey.getGroup());
                        info.setJobDescription(jobDetail.getDescription());
                        info.setJobStatus(triggerState.name());
                        info.setCronExpression(cronExpression);
                        info.setCreateTime(createTime);
                        info.setRepeatCount(repeatCount);
                        info.setStartDate(startDate);
                        info.setMilliSeconds(milliSeconds);
                        info.setEndDate(endDate);
                        list.add(info);
                    }
                }
            }
            log.info("任务的数量为:{}" + list.size());
        } catch (SchedulerException e) {
            log.info("查询任务失败，原因是:{}" + e.getMessage());
            throw new CmpException();
        }
        return new ListCmpResponse<>(list);
    }

    /**
     * @param @param  inputMap
     * @param @return 参数
     * @return Boolean    返回类型
     * @throws
     * @Title: setSimpleTrigger
     * @Description: 简单调度
     */
    @Override
    @SuppressWarnings({"unchecked"})
    public CmpResponse setSimpleTriggerJob(TaskInfo info) {
        log.info("TaskService--data-s-->setSimpleTriggerJob()" + info);
        String jobName = info.getJobName();
        String jobGroup = info.getJobGroup();
        String jobDescription = info.getJobDescription();
        Long milliSeconds = Long.parseLong(info.getMilliSeconds());
        Integer repeatCount = Integer.parseInt(info.getRepeatCount());
        Date startDate = DateUtil.parseDate(info.getStartDate());
        Date endDate = DateUtil.parseDate(info.getEndDate());
        try {
            // 触发器的key值
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            // job的key值
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            if (checkExists(jobName, jobGroup)) {
                log.info(
                        "===> AddJob fail, job already exist, jobGroup:{}, jobName:{}",
                        jobGroup, jobName);
                throw new CmpException();
            }
            /* 简单调度 */
            SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder
                    .newTrigger()
                    .withIdentity(triggerKey)
                    .startAt(startDate)
                    .withSchedule(
                            SimpleScheduleBuilder.simpleSchedule()
                                    .withIntervalInMilliseconds(milliSeconds)
                                    .withRepeatCount(repeatCount))
                    .endAt(endDate).build();
            Class<? extends Job> clazz = (Class<? extends Job>) Class
                    .forName(jobName);
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobKey)
                    .withDescription(jobDescription).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException | ClassNotFoundException e) {
            log.info("任务添加失败！--->简单调度" + e.getMessage());
            throw new CmpException();
        }
        return new CmpResponse();
    }

    /**
     * @param @param info    参数
     * @return void    返回类型
     * @throws
     * @Title: addJob
     * @Description: 保存定时任务
     */
    @Override
    @SuppressWarnings("unchecked")
    public CmpResponse addJob(TaskInfo info) {
        String jobName = info.getJobName(), jobGroup = info.getJobGroup(), cronExpression = info
                .getCronExpression(), jobDescription = info.getJobDescription(), createTime = DateFormatUtils
                .format(new Date(), DateFormatType.FORMAT_YYYYMMDD_HHMISS);
        try {
            if (checkExists(jobName, jobGroup)) {
                throw new CmpException();
            }

            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

            CronScheduleBuilder schedBuilder = CronScheduleBuilder
                    .cronSchedule(cronExpression)
                    .withMisfireHandlingInstructionDoNothing();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey).withDescription(createTime)
                    .withSchedule(schedBuilder).build();

            Class<? extends Job> clazz = (Class<? extends Job>) Class
                    .forName(jobName);
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobKey)
                    .withDescription(jobDescription).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException | ClassNotFoundException e) {
            log.info("保存定时任务-->类名不存在或执行表达式错误--->复杂调度" + e.getMessage());
            throw new CmpException();
        }
        return new CmpResponse();
    }

    /**
     * @param @param info    参数
     * @return void    返回类型
     * @throws
     * @Title: edit
     * @Description: 修改定时任务
     */
    @Override
    public CmpResponse editJob(TaskInfo info) {
        String jobName = info.getJobName(), jobGroup = info.getJobGroup(), cronExpression = info
                .getCronExpression(), jobDescription = info.getJobDescription(), createTime = DateFormatUtils
                .format(new Date(), DateFormatType.FORMAT_YYYYMMDD_HHMISS);
        try {
            if (!checkExists(jobName, jobGroup)) {
                throw new CmpException();
            }
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            JobKey jobKey = new JobKey(jobName, jobGroup);
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
                    .cronSchedule(cronExpression)
                    .withMisfireHandlingInstructionDoNothing();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey).withDescription(createTime)
                    .withSchedule(cronScheduleBuilder).build();

            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            jobDetail.getJobBuilder().withDescription(jobDescription);
            HashSet<Trigger> triggerSet = new HashSet<>();
            triggerSet.add(cronTrigger);

            scheduler.scheduleJob(jobDetail, triggerSet, true);
        } catch (SchedulerException e) {
            log.info("修改定时任务-->类名不存在或执行表达式错误--->复杂调度" + e.getMessage());
            throw new CmpException();
        }
        return new CmpResponse();
    }

    /**
     * @param @param jobName
     * @param @param jobGroup    参数
     * @return void    返回类型
     * @throws
     * @Title: delete
     * @Description: 删除定时任务
     */
    @Override
    public CmpResponse deleteJob(String jobName, String jobGroup) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        try {
            if (checkExists(jobName, jobGroup)) {
                scheduler.pauseTrigger(triggerKey);
                scheduler.unscheduleJob(triggerKey);
                log.info("===> delete, triggerKey:{}", triggerKey);
            }
        } catch (SchedulerException e) {
            log.info("删除定时任务-->复杂调度" + e.getMessage());
            throw new CmpException();
        }
        return new CmpResponse();
    }

    /**
     * @param @param jobName
     * @param @param jobGroup    参数
     * @return void    返回类型
     * @throws
     * @Title: pause
     * @Description: 暂停定时任务
     */
    @Override
    public CmpResponse pauseJob(String jobName, String jobGroup) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        try {
            if (checkExists(jobName, jobGroup)) {
                scheduler.pauseTrigger(triggerKey);
                log.info("===> Pause success, triggerKey:{}", triggerKey);
            }
        } catch (SchedulerException e) {
            throw new CmpException();
        }
        return new CmpResponse();
    }

    /**
     * @param @param jobName
     * @param @param jobGroup    参数
     * @return void    返回类型
     * @throws
     * @Title: resume
     * @Description: 恢复暂停任务
     */
    @Override
    public CmpResponse resumeJob(String jobName, String jobGroup) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        try {
            if (checkExists(jobName, jobGroup)) {
                scheduler.resumeTrigger(triggerKey);
                log.info("===> Resume success, triggerKey:{}", triggerKey);
            }
        } catch (SchedulerException e) {
            log.info("重新开始任务-->复杂调度" + e.getMessage());
            e.printStackTrace();
        }
        return new CmpResponse();
    }

    /**
     * @param @param  jobName
     * @param @param  jobGroup
     * @param @return
     * @param @throws SchedulerException    参数
     * @return boolean    返回类型
     * @throws
     * @Title: checkExists
     * @Description: 验证任务是否存在
     */
    private boolean checkExists(String jobName, String jobGroup)
            throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        return scheduler.checkExists(triggerKey);
    }

}
