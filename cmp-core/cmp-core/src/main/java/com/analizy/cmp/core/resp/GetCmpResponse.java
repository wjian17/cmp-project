package com.analizy.cmp.core.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: wangjian
 * @date: 2021/01/18 11:18
 */
@ApiModel("get返回实体")
@Data
public class GetCmpResponse<T> extends CmpResponse {

    @ApiModelProperty("返回信息")
    private T detail;

    public GetCmpResponse(T detail){
        this.detail = detail;
    }

    public GetCmpResponse(){
    }
}
