package com.analizy.cmp.workflow.config;

import lombok.extern.slf4j.Slf4j;
import org.flowable.job.service.impl.asyncexecutor.AbstractAsyncExecutor;
import org.flowable.job.service.impl.asyncexecutor.AsyncExecutor;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @author: wangjian
 * @date: 2021/01/13 17:00
 */
@Slf4j
@Configuration
public class FlowableConfig {

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;

    @Value("${workflow.bpmnPath:classpath:process/*.bpmn20.xml}")
    private String bmpnPath;

    @Value("${workflow.job.lockTime:5000}")
    private int jobLockTime;

    @Bean
    public MyEngineConfigurer loadMyEngineConfigurer(){
        return new MyEngineConfigurer();
    }

    public class MyEngineConfigurer implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration>{

        @Override
        public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
            springProcessEngineConfiguration.setAsyncExecutorActivate(true);
            try {
                springProcessEngineConfiguration.setDeploymentResources(resourcePatternResolver.getResources(bmpnPath));
            }catch (Exception e){
                log.error("process file load error:{}",e.getMessage());
            }
            AsyncExecutor asyncExecutor = springProcessEngineConfiguration.getAsyncExecutor();
            if(asyncExecutor instanceof AbstractAsyncExecutor){
                asyncExecutor.setAsyncJobLockTimeInMillis(jobLockTime);
            }
        }
    }
}
