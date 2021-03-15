package com.analizy.cmp.quartz.config;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: wangjian
 * @date: 2021/01/11 11:41
 */
@Data
public class TaskInfo implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 增加或修改标识
     */
    private int id;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务分组
     */
    private String jobGroup;

    /**
     * 任务描述
     */
    private String jobDescription;

    /**
     * 任务状态
     */
    private String jobStatus;

    /**
     * 任务表达式
     */
    private String cronExpression;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 间隔时间（毫秒）
     */
    private String milliSeconds;

    /**
     * 重复次数
     */
    private String repeatCount;

    /**
     * 起始时间
     */
    private String startDate;

    /**
     * 终止时间
     */
    private String endDate;
}