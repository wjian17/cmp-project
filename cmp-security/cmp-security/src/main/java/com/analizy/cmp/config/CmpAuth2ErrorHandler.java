package com.analizy.cmp.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.client.http.OAuth2ErrorHandler;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.nio.charset.Charset;

/**
 * @author: wangjian
 * @date: 2021/04/29 14:00
 */
public class CmpAuth2ErrorHandler extends OAuth2ErrorHandler {
    public CmpAuth2ErrorHandler(OAuth2ProtectedResourceDetails resource) {
        super(resource);
    }

    public CmpAuth2ErrorHandler(ResponseErrorHandler errorHandler, OAuth2ProtectedResourceDetails resource) {
        super(errorHandler, resource);
    }
}
