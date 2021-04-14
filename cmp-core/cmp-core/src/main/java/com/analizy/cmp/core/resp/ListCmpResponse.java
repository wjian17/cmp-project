package com.analizy.cmp.core.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @author: wangjian
 * @date: 2021/01/18 11:18
 */

@ApiModel("get返回实体")
public class ListCmpResponse<T> extends CmpResponse {

    @ApiModelProperty("返回信息")
    private List<T> rows;

    public ListCmpResponse(List<T> rows){
        this.rows = rows;
    }
}
