package com.analizy.cmp.core.error;

/**
 * @author: wangjian
 * @date: 2021/01/18 9:08
 */
public interface CmpErrorCode {

    int getHttpCode();

    String getErrorCode();

    String getErrorMessage();

}