package com.analizy.cmp.endpoint;

import com.analizy.cmp.core.error.CheckErrorCode;
import com.analizy.cmp.core.error.WebErrorCode;
import com.analizy.cmp.core.resp.CmpResponse;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: wangjian
 * @date: 2021/03/31 14:12
 */

@FrameworkEndpoint
public class CmpAuthEndpoint {

    @RequestMapping("/slefOAuth/error")
    @ResponseBody
    public CmpResponse handleError(HttpServletRequest request) {
        Object error = request.getAttribute("error");
        if (error instanceof CmpResponse) {
            return (CmpResponse) error;
        }
        return new CmpResponse(WebErrorCode.SERVER_ERROR);
    }
}
