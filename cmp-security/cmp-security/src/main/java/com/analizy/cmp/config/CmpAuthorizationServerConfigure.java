package com.analizy.cmp.config;

import cn.hutool.core.map.MapUtil;
import com.analizy.cmp.adapter.UserDetailsServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.Calendar;
import java.util.Map;

/**
 * @author: wangjian
 * @date: 2021/01/18 15:19
 */
//@Configuration
//@EnableAuthorizationServer
//@ConditionalOnProperty(prefix = "config.oauth", name = "enable", havingValue = "true")
public class CmpAuthorizationServerConfigure extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private UserDetailsServiceAdapter userDetailsServiceAdapter;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${config.oauth2.access-token-validity-seconds:2592000}")
    private int accessTokenValiditySeconds;

    @Value("${config.oauth2.refresh-token-validity-seconds:2592000}")
    private int refreshTokenValiditySeconds;

    @Autowired
    private CmpWebResponseExceptionTranslator cmpWebResponseExceptionTranslator;


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients()
                // 获取 token 的策略
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()");
//                .addTokenEndpointAuthenticationFilter();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
//        clientDetailsService.setSelectClientDetailsSql(OauthConstant.DEFAULT_SELECT_STATEMENT);
//        clientDetailsService.setFindClientDetailsSql(OauthConstant.DEFAULT_FIND_STATEMENT);
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST)
                .tokenStore(redisTokenStore())
                .tokenServices(defaultTokenServices())
                .tokenEnhancer(tokenEnhancer())
                .userDetailsService(userDetailsServiceAdapter)
                .authenticationManager(authenticationManager)
                .reuseRefreshTokens(false)
                .exceptionTranslator(cmpWebResponseExceptionTranslator);
    }

//    @Bean
//    public TokenEndpointAuthenticationFilter tokenEndpointAuthenticationFilter(){
//        return new TokenEndpointAuthenticationFilter(authenticationManager,);
//    }

    @Bean
    @Primary
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(redisTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setAccessTokenValiditySeconds(accessTokenValiditySeconds);
        tokenServices.setRefreshTokenValiditySeconds(refreshTokenValiditySeconds);
        return tokenServices;
    }

    public RedisTokenStore redisTokenStore(){
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        return redisTokenStore;
    }

    private TokenEnhancer tokenEnhancer(){
        return (accessToken,authentication)->{
            final Map<String,Object> additionInfo = MapUtil.newHashMap();
            additionInfo.put("license","made by cmp");
            ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(additionInfo);
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.MINUTE,120);
            ((DefaultOAuth2AccessToken)accessToken).setExpiration(nowTime.getTime());
            return accessToken;
        };
    }
}
