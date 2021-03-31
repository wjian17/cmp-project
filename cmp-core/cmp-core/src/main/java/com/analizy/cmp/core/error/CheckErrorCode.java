package com.analizy.cmp.core.error;

/**
 * @author: wangjian
 * @date: 2021/01/18 9:09
 */
public enum CheckErrorCode implements CmpErrorCode{

    SERVER_ERROR("SERVER_ERROR",500);

    private String errorCode;

    private int httpCode;

    CheckErrorCode(String errorCode, int httpCode) {
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
        return null;
    }
}
