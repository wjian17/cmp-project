package com.analizy.cmp.workflow.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

/**
 * @author: wangjian
 * @date: 2021/01/13 16:29
 */
@Slf4j
@Component
public class ManagerTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("ManagerTaskListener");
    }
}
