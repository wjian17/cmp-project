package com.analizy.cmp.advice;

import com.analizy.cmp.core.resp.CmpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: wangjian
 * @date: 2021/03/31 11:32
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.analizy.cmp")
public class AuthorizateControllerAdvice {

    @ExceptionHandler({OAuth2Exception.class})
    public CmpResponse cmpExceptionHandler(OAuth2Exception oauth2Exception) {
        return new CmpResponse();
    }

}
