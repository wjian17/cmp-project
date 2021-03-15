package com.analizy.cmp.workflow.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * @author: wangjian
 * @date: 2021/01/13 16:40
 */
@Slf4j
@Component
public class BossJavaDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        log.info("delegateExecution");
    }
}
