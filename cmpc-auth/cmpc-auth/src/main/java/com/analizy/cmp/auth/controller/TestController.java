package com.analizy.cmp.auth.controller;

import com.analizy.cmp.core.resp.GetCmpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wangjian
 * @date: 2021/03/17 14:58
 */
@RestController
@RequestMapping("/api/v1")
public class TestController {

    @GetMapping("/test")
    public GetCmpResponse getCmpResponse(){
        return new GetCmpResponse();
    }
}
