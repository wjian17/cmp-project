package com.analizy.cmp.rest.remote.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wangjian
 * @date: 2021/03/26 9:57
 */
@Data
@ApiModel("ApiDto")
@NoArgsConstructor
@AllArgsConstructor
public class ApiDTO {

    @ApiModelProperty("key键")
    private String keyId;

    @ApiModelProperty("value值")
    private String valueId;

}
