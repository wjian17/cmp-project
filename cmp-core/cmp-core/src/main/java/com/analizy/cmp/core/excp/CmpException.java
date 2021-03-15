package com.analizy.cmp.core.excp;

import com.analizy.cmp.core.error.CmpErrorCode;
import com.analizy.cmp.core.i18n.MessageSourceUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wangjian
 * @date: 2021/01/13 15:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CmpException extends RuntimeException {

    private CmpErrorCode cmpErrorCode;

    private String errorCode;

    private Object errorDetail;

    public CmpException(CmpErrorCode cmpErrorCode, Object... errorCodeParams) {
        super(MessageSourceUtil.getMessage(cmpErrorCode, errorCodeParams));
        this.cmpErrorCode = cmpErrorCode;
        this.errorCode = cmpErrorCode == null ? null : cmpErrorCode.getErrorCode();
    }
}
