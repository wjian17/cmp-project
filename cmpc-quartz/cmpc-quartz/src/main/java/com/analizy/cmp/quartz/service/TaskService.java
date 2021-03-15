package com.analizy.cmp.quartz.service;

import cn.hutool.core.date.DateUtil;
import com.analizy.cmp.core.constant.DateFormatType;
import com.analizy.cmp.core.excp.CmpException;
import com.analizy.cmp.core.resp.CmpResponse;
import com.analizy.cmp.core.resp.ListCmpResponse;
import com.analizy.cmp.quartz.config.TaskInfo;
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
 * @date: 2021/01/11 11:41
 */
public interface TaskService {

    /**
     * @param @return 参数
     * @return ListCmpResponse    返回类型
     * @throws
     * @Title: CmpResponse
     * @Description: 任务列表
     */
    ListCmpResponse<TaskInfo> queryJobList();

    /**
     * @param @param  inputMap
     * @param @return 参数
     * @return Boolean    返回类型
     * @throws
     * @Title: setSimpleTrigger
     * @Description: 简单调度
     */
    @SuppressWarnings({"unchecked"})
    CmpResponse setSimpleTriggerJob(TaskInfo info);

    /**
     * @param @param info    参数
     * @return void    返回类型
     * @throws
     * @Title: addJob
     * @Description: 保存定时任务
     */
    @SuppressWarnings("unchecked")
    CmpResponse addJob(TaskInfo info);

    /**
     * @param @param info    参数
     * @return void    返回类型
     * @throws
     * @Title: edit
     * @Description: 修改定时任务
     */
    CmpResponse editJob(TaskInfo info);

    /**
     * @param @param jobName
     * @param @param jobGroup    参数
     * @return void    返回类型
     * @throws
     * @Title: delete
     * @Description: 删除定时任务
     */
    CmpResponse deleteJob(String jobName, String jobGroup);

    /**
     * @param @param jobName
     * @param @param jobGroup    参数
     * @return void    返回类型
     * @throws
     * @Title: pause
     * @Description: 暂停定时任务
     */
    CmpResponse pauseJob(String jobName, String jobGroup);

    /**
     * @param @param jobName
     * @param @param jobGroup    参数
     * @return void    返回类型
     * @throws
     * @Title: resume
     * @Description: 恢复暂停任务
     */
    CmpResponse resumeJob(String jobName, String jobGroup);

}