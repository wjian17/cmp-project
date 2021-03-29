package com.analizy.cmp.rest.remote.controller;

import com.analizy.cmp.core.resp.CmpResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: wangjian
 * @date: 2021/01/22 16:52
 */
@FeignClient(name = "rest")
public interface RemoteApiFeign {

    @RequestMapping(value = "/remote/rest/api/v1/test",method = RequestMethod.GET)
    public CmpResponse query();


    @RequestMapping(value = "/remote/rest/api/v1/test1",method = RequestMethod.GET)
    public CmpResponse query1();
}
