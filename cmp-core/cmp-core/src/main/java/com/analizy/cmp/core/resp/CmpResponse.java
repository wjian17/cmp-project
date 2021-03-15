package com.analizy.cmp.core.resp;

import cn.hutool.http.HttpStatus;
import com.analizy.cmp.core.constant.HttpConstant;
import com.analizy.cmp.core.error.CmpErrorCode;
import com.analizy.cmp.core.excp.CmpException;
import com.analizy.cmp.core.i18n.MessageSourceUtil;
import com.analizy.cmp.core.util.TracerUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author: wangjian
 * @date: 2021/01/13 15:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CmpResponse {

    private String requestId = TracerUtil.getTracerId();

    private Integer httpCode = HttpStatus.HTTP_OK;

    private String code = HttpConstant.CODE_SUCCESS;

    private String message = HttpConstant.CODE_SUCCESS;

    public CmpResponse(CmpErrorCode cmpErrorCode) {
        this.httpCode = cmpErrorCode.getHttpCode();
        this.code = cmpErrorCode.getErrorCode();
        this.message = MessageSourceUtil.getMessage(cmpErrorCode.getErrorCode());
    }

    public CmpResponse(Integer httpCode, String code, String message) {
        this.httpCode = httpCode;
        this.code = code;
        this.message = message;
    }
}
