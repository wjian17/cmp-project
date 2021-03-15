package com.analizy.cmp.core.error;

/**
 * @author: wangjian
 * @date: 2021/01/18 9:30
 */
public enum QuartzErrorCode implements CmpErrorCode{

    NOT_FOUND("",404);

    private String errorCode;

    private int httpCode;

    QuartzErrorCode(String errorCode, int httpCode) {
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
