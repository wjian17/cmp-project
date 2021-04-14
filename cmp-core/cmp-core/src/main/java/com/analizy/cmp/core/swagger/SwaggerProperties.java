package com.analizy.cmp.core.swagger;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: wangjian
 * @date: 2021/01/11 16:05
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "config.swagger-ui")
@ConditionalOnProperty(prefix = "config.swagger-ui", name = "enable", havingValue = "true")
public class SwaggerProperties {

    @Value("${title:title}")
    private String title;

    @Value("${desc:desc}")
    private String desc;

    @Value("${termsOfServiceUrl:termsOfServiceUrl}")
    private String termsOfServiceUrl;

    @Value("${security.oauth2.client.access-token-uri}")
    private String accessTokenUri;

    @Value("${name:name}")
    private String name;

    @Value("${url:url}")
    private String url;

    @Value("${email:email}")
    private String email;

}
