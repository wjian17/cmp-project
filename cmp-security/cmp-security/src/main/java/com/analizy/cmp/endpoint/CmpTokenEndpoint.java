package com.analizy.cmp.endpoint;

import com.analizy.cmp.core.resp.GetCmpResponse;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: wangjian
 * @date: 2021/03/17 11:49
 */

//@FrameworkEndpoint
public class CmpTokenEndpoint {

    @RequestMapping(value = "/oauth/authorize")
    public GetCmpResponse authorize(HttpServletRequest request) {

        return new GetCmpResponse();

    }
}
