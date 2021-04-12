package com.analizy.cmp.config;

import cn.hutool.core.util.ArrayUtil;
import com.analizy.cmp.adapter.UserDetailsServiceAdapter;
import com.analizy.cmp.core.constant.HttpConstant;
import com.analizy.cmp.core.resp.CmpResponse;
import com.analizy.cmp.excp.OauthErrorCode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wangjian
 * @date: 2021/01/18 13:56
 */
//@Slf4j
//@Primary
//@Configuration
//@ConditionalOnProperty(prefix = "config.oauth", name = "enable", havingValue = "true")
//@EnableWebSecurity(debug = true)
//@EnableRedisHttpSession
//@EnableGlobalMethodSecurity(jsr250Enabled = true,prePostEnabled = true,securedEnabled = true)
public class CmpAuthSecurityConfigure extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceAdapter userDetailsServiceAdapter;

    @Autowired
    private AuthenticationEventPublisher authenticationEventPublisher;

    @Value("${config.security.ignoreUrls:ignoreUrls}")
    private String[] ignoreUrls;

    private static String[] basicIgnoreUrls = new String[]{
            "/actuator/**",
            "/remote/**",
            "/oauth/**",
            "/webjars/**",
            "/login/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //设置自定义登录页和登录接口
                .formLogin().loginProcessingUrl("/oauth/login")
                .successHandler((req, resp, authentication) ->
                        doOut(resp, new CmpResponse())
                )
                .and()
                .authorizeRequests()
                .antMatchers(ArrayUtil.addAll(basicIgnoreUrls,ignoreUrls)).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((req, resp, authentication) -> {
                    CmpResponse cmpResponse = new CmpResponse(OauthErrorCode.UNAUTHORIZED);
                    doOut(resp, cmpResponse);
                })
                .accessDeniedHandler((req, resp, authentication) -> {
                    CmpResponse cmpResponse = new CmpResponse(OauthErrorCode.FORBIDDEN);
                    doOut(resp, cmpResponse);
                })
                .and()
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler((req, resp, authentication) -> {
                    CmpResponse cmpResponse = new CmpResponse();
                    doOut(resp, cmpResponse);
                }).permitAll()
                .logoutUrl("/loginOut")
                .and()
                .csrf()
                .disable();
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

    private void doOut(HttpServletResponse response, CmpResponse cmpResponse) throws IOException, ServletException {
        response.setContentType(HttpConstant.APPLICATION_JSON);
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(cmpResponse));
        out.flush();
        out.close();
    }
}
