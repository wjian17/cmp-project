package com.analizy.cmp.rest.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * @author: wangjian
 * @date: 2021/04/13 14:09
 */
@ApiModel
@Data
public class RequestMap2<T> {

    @ApiModelProperty("param1")
    private String param1;

    @ApiModelProperty("param2")
    private String param2;

    @ApiModelProperty("T")
    private T t;
}
