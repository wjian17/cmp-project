package com.analizy.cmp.auth.controller;

import com.analizy.cmp.core.resp.CmpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wangjian
 * @date: 2021/01/22 16:52
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @RequestMapping(value = "test",method = RequestMethod.GET)
    public CmpResponse query(){
        log.info("query is running");
        return new CmpResponse();
    }
}
