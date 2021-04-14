package com.analizy.cmp.rest.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * @author: wangjian
 * @date: 2021/04/13 14:09
 */
@Data
@ApiModel
public class RequestMap {

    @ApiParam("param1")
    private String param1;

    @ApiParam("param2")
    private String param2;
}
