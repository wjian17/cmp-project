package com.analizy.cmp.core.resp;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.analizy.cmp.core.constant.HttpConstant;
import com.analizy.cmp.core.error.CmpErrorCode;
import com.analizy.cmp.core.excp.CmpException;
import com.analizy.cmp.core.i18n.MessageSourceUtil;
import com.analizy.cmp.core.util.TracerUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author: wangjian
 * @date: 2021/01/13 15:54
 */
@Data
@ApiModel("公共返回实体")
@NoArgsConstructor
@AllArgsConstructor
public class CmpResponse {

    @ApiModelProperty("唯一id")
    private String requestId = TracerUtil.getTracerId();

    @ApiModelProperty("返回状态码")
    private Integer httpCode = HttpStatus.HTTP_OK;

    @ApiModelProperty("返回码")
    private String code = HttpConstant.CODE_SUCCESS;

    @ApiModelProperty("返回信息")
    private String message = HttpConstant.CODE_SUCCESS;

    public CmpResponse(CmpErrorCode cmpErrorCode) {
        this.httpCode = cmpErrorCode.getHttpCode();
        this.code = cmpErrorCode.getErrorCode();
        this.message = StrUtil.isEmpty(cmpErrorCode.getErrorMessage()) ? MessageSourceUtil.getMessage(cmpErrorCode.getErrorCode()) : cmpErrorCode.getErrorMessage();
    }

    public CmpResponse(Integer httpCode, String code, String message) {
        this.httpCode = httpCode;
        this.code = code;
        this.message = message;
    }
}
