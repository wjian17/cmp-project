package com.analizy.cmp.core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author: wangjian
 * @date: 2021/01/13 10:46
 */
@Configuration
public class SpringApplicationUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;


    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext arg) throws BeansException {
        if(applicationContext==null){
            applicationContext = arg;
        }
    }
}
