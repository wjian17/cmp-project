package com.analizy.cmp.workflow.config;

import com.analizy.cmp.config.CmpResourceServerConfigure;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author: wangjian
 * @date: 2021/03/17 15:48
 */

@Configuration
@EnableResourceServer
@ConditionalOnProperty(prefix = "config.oauth-resource", name = "enable", havingValue = "true")
public class ResourceServerConfiguration extends CmpResourceServerConfigure {
}
