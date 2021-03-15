package com.analizy.cmp.workflow.listener;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author: wangjian
 * @date: 2021/01/13 16:34
 */
@Slf4j
@Component
public class JobEndExecutionListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) {
        log.info("delegateExecution");
    }
}
