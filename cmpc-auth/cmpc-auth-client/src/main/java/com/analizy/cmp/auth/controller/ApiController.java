package com.analizy.cmp.auth.controller;

import com.analizy.cmp.core.resp.CmpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

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


    @RequestMapping(value = "test1",method = RequestMethod.GET)
    public CmpResponse query1(){
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("token","2c95cb63-625c-4b81-a224-53e10a0aa44b");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type","application/json");
        headers.set("Authorization","Basic dXNlcl9jbGllbnQ6MTIzNDU2");
//        restTemplate.exchange("http://localhost:8083/oauth/check_token", HttpMethod.POST,
//                new HttpEntity<MultiValueMap<String, String>>(formData, headers), Map.class).getBody();
        return new CmpResponse();
    }
}
