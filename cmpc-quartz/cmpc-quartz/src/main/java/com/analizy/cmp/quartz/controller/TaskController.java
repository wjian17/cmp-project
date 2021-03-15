package com.analizy.cmp.quartz.controller;

import com.analizy.cmp.core.excp.CmpException;
import com.analizy.cmp.core.resp.CmpResponse;
import com.analizy.cmp.core.resp.ListCmpResponse;
import com.analizy.cmp.quartz.config.TaskInfo;
import com.analizy.cmp.quartz.service.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: wangjian
 * @date: 2021/01/18 10:00
 */
@RestController
@RequestMapping("/api/v1/taskController")
public class TaskController {

    private TaskService taskService;


    public ListCmpResponse<TaskInfo> queryJobList() throws CmpException {
        return taskService.queryJobList();
    }

    @SuppressWarnings({"unchecked"})
    public CmpResponse setSimpleTriggerJob(TaskInfo info) {
        return taskService.setSimpleTriggerJob(info);
    }

    @SuppressWarnings("unchecked")
    public CmpResponse addJob(TaskInfo info) {
        return taskService.addJob(info);
    }

    public CmpResponse editJob(TaskInfo info) {
        return taskService.editJob(info);
    }

    public CmpResponse deleteJob(String jobName, String jobGroup) {
        return taskService.deleteJob(jobName,jobGroup);
    }

    public CmpResponse pauseJob(String jobName, String jobGroup) {
        return taskService.pauseJob(jobName,jobGroup);
    }

    public CmpResponse resumeJob(String jobName, String jobGroup) {
        return taskService.resumeJob(jobName,jobGroup);
    }
}
