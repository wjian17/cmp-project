package com.analizy.cmp.config;

import com.analizy.cmp.core.util.SpringApplicationUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;

/**
 * @author: wangjian
 * @date: 2021/03/31 14:55
 */
@Configuration
@ConditionalOnBean({AuthorizationEndpoint.class,SpringApplicationUtil.class})
@ConditionalOnProperty(prefix = "config.oauth", name = "enable", havingValue = "true")
public class CmpAuthEndpointConfigure implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        AuthorizationEndpoint authorizationEndpoint = SpringApplicationUtil.getApplicationContext().getBean(AuthorizationEndpoint.class);
        authorizationEndpoint.setErrorPage("forward:/slefOAuth/error");
    }
}
