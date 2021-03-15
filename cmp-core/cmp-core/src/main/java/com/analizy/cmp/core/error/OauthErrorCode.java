package com.analizy.cmp.core.error;

import cn.hutool.http.HttpStatus;
import com.analizy.cmp.core.i18n.MessageSourceUtil;

/**
 * @author: wangjian
 * @date: 2021/01/18 9:30
 */
public enum OauthErrorCode implements CmpErrorCode{

    FORBIDDEN("HTTP.ERROR.FORBIDDEN", HttpStatus.HTTP_FORBIDDEN),
    UNAUTHORIZED("HTTP.ERROR.UNAUTHORIZED", HttpStatus.HTTP_UNAUTHORIZED);

    private String errorCode;

    private int httpCode;

    OauthErrorCode(String errorCode, int httpCode) {
        this.errorCode = errorCode;
        this.httpCode = httpCode;
    }

    @Override
    public int getHttpCode() {
        return this.httpCode;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return MessageSourceUtil.getMessage(this.errorCode);
    }
}
