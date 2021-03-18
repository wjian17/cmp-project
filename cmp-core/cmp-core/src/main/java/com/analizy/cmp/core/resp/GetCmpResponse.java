package com.analizy.cmp.core.resp;

/**
 * @author: wangjian
 * @date: 2021/01/18 11:18
 */
public class GetCmpResponse<T> extends CmpResponse {

    private T detail;

    public GetCmpResponse(T detail){
        this.detail = detail;
    }

    public GetCmpResponse(){
    }
}
