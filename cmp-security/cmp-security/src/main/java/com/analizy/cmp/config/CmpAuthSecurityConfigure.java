package com.analizy.cmp.config;

import cn.hutool.core.util.ArrayUtil;
import com.analizy.cmp.adapter.UserDetailsServiceAdapter;
import com.analizy.cmp.core.constant.HttpConstant;
import com.analizy.cmp.core.error.OauthErrorCode;
import com.analizy.cmp.core.resp.CmpResponse;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wangjian
 * @date: 2021/01/18 13:56
 */
@Slf4j
//@Configuration
//@ConditionalOnMissingBean(WebSecurityConfigurerAdapter.class)
//@ConditionalOnProperty(prefix = "config.oauth-security", name = "enable", havingValue = "true")
//@EnableWebSecurity(debug = true)
//@EnableRedisHttpSession
//@EnableGlobalMethodSecurity(jsr250Enabled = true,prePostEnabled = true,securedEnabled = true)
public class CmpAuthSecurityConfigure extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceAdapter userDetailsServiceAdapter;

    @Autowired
    private AuthenticationEventPublisher authenticationEventPublisher;

    private static String[] basicIgnoreUrls = new String[]{
            "/error",
            "/login",
            "/css/**",
            "/js/**",
            "/favicon.ico",
            "/swagger-ui.html",
            "/api/base/v1/api-docs",
            "/oauth/**",
            "/remote/**",
            "/webjars/**"
    };

    @Value("${config.security.ignoreUrls:ignoreUrls}")
    private String[] ignoreUrls;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(ArrayUtil.addAll(ignoreUrls,basicIgnoreUrls))
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
//                .logoutSuccessHandler((req, resp, authentication) ->
//                        doOut(resp, new CmpResponse())
//                )
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/oauth/login")
                // 登录页面
//                .loginPage("/oauth/login")
//                // 登录处理url
//                .loginProcessingUrl("/oauth/authorize")
//                .successHandler((req, resp, authentication) ->
//                        doOut(resp, new CmpResponse())
//                )
                .and()
                .csrf()
                .disable();
    }

    private void doOut(HttpServletResponse response, CmpResponse cmpResponse) throws IOException, ServletException {
        response.setContentType(HttpConstant.APPLICATION_JSON);
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(cmpResponse));
        out.flush();
        out.close();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception{
        return providerManager();
    }

    @Bean
    public ProviderManager providerManager() {
        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(daoAuthenticationProvider());
        ProviderManager providerManager = new ProviderManager(providers);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        providerManager.setAuthenticationEventPublisher(authenticationEventPublisher);
        return providerManager;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceAdapter);
        return daoAuthenticationProvider;
    }

    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
