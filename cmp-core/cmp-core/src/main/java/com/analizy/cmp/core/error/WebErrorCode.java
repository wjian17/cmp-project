package com.analizy.cmp.core.error;

/**
 * @author: wangjian
 * @date: 2021/01/18 16:50
 */
public enum  WebErrorCode implements CmpErrorCode {

    SERVER_ERROR("",404),
    METHOD_NOT_ALLOWED("",404);

    private String errorCode;

    private int httpCode;

    WebErrorCode(String errorCode, int httpCode) {
        this.errorCode = errorCode;
        this.httpCode = httpCode;
    }

    @Override
    public int getHttpCode() {
        return 0;
    }

    @Override
    public String getErrorCode() {
        return null;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }
}