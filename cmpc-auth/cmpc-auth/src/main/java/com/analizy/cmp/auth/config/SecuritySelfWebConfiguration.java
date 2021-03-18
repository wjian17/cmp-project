package com.analizy.cmp.auth.config;

import com.analizy.cmp.config.CmpAuthSecurityConfigure;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author: wangjian
 * @date: 2021/03/16 15:04
 */
@Primary
@Configuration
@EnableRedisHttpSession
@ConditionalOnProperty(prefix = "config.oauth", name = "enable", havingValue = "true")
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
public class SecuritySelfWebConfiguration extends CmpAuthSecurityConfigure {
}
