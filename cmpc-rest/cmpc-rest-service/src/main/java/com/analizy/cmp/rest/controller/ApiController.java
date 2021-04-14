package com.analizy.cmp.rest.controller;

import com.analizy.cmp.core.resp.CmpResponse;
import com.analizy.cmp.core.resp.GetCmpResponse;
import com.analizy.cmp.rest.dto.request.RequestMap;
import com.analizy.cmp.rest.dto.request.RequestMap2;
import com.analizy.cmp.rest.remote.controller.RemoteApiFeign;
import com.analizy.cmp.rest.remote.dto.ApiDTO;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author: wangjian
 * @date: 2021/01/22 16:52
 */
@Slf4j
@Api(value="api管理value",tags = "api管理tags")
@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @ApiOperation(value = "test接口")
    @RequestMapping(value = "test",method = RequestMethod.GET)
    public GetCmpResponse<ApiDTO> query(String param1,String param2){
        log.info("query is running");
        return new GetCmpResponse<>(new ApiDTO(param1,param2));
    }

    @ApiOperation(value = "test1接口")
    @RequestMapping(value = "test1",method = RequestMethod.POST)
    public CmpResponse query1(@RequestBody RequestMap requestMap){
        MultiValueMap<String, String> formData = new LinkedMultiValueMap();
        formData.add("token","2c95cb63-625c-4b81-a224-53e10a0aa44b");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type","application/json");
        headers.set("Authorization","Basic dXNlcl9jbGllbnQ6MTIzNDU2");
        return new CmpResponse();
    }

    @ApiOperation(value = "test2接口")
    @RequestMapping(value = "test2",method = RequestMethod.POST)
    public CmpResponse query2(@RequestBody RequestMap2<RequestMap> requestMap2){
        MultiValueMap<String, String> formData = new LinkedMultiValueMap();
        formData.add("token","2c95cb63-625c-4b81-a224-53e10a0aa44b");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type","application/json");
        headers.set("Authorization","Basic dXNlcl9jbGllbnQ6MTIzNDU2");
        return new CmpResponse();
    }
}
