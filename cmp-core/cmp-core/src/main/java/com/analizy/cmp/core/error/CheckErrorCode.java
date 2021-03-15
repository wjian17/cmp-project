package com.analizy.cmp.core.error;

/**
 * @author: wangjian
 * @date: 2021/01/18 9:09
 */
public enum CheckErrorCode implements CmpErrorCode{

    ;

    private String errorCode;

    private int httpCode;

    CheckErrorCode(String errorCode, int httpCode) {
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
