package com.analizy.cmp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: wangjian
 * @date: 2021/01/11 11:41
 */
@Slf4j
@EnableWebMvc
@EnableSwagger2
@EnableDiscoveryClient
@SpringBootApplication
public class CmpAuthApplication {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        SpringApplication.run(CmpAuthApplication.class,args);
        log.info("################################################");
        log.info("####: Service was started:{} seconds.",(System.currentTimeMillis()-start)/1000);
        log.info("################################################");
    }
}
