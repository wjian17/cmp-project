package com.analizy.cmp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author: wangjian
 * @date: 2021/01/11 11:41
 */
@Slf4j
@EnableEurekaServer
@SpringBootApplication(scanBasePackages = {"com.analizy.cmp.*"})
public class CmpRegisterApplication {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        SpringApplication.run(CmpRegisterApplication.class,args);
        log.info("################################################");
        log.info("####: Service was started:{} seconds.",(System.currentTimeMillis()-start)/1000);
        log.info("################################################");
    }
}
