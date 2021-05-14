package com.analizy.cmp.rest.remote;

import com.analizy.cmp.core.resp.CmpResponse;
import com.analizy.cmp.core.resp.GetCmpResponse;
import com.analizy.cmp.rest.remote.controller.RemoteApiFeign;
import com.analizy.cmp.rest.remote.dto.ApiDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author: wangjian
 * @date: 2021/01/22 16:52
 */
@Slf4j
@RestController
@RequestMapping("/remote/api/v1")
public class ApiFeign implements RemoteApiFeign {

    @Autowired(required = false)
    private RestOperations restTemplate;

    @Override
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public CmpResponse query() {
        log.info("query is running");
        restTemplate = new RestTemplate();
//        ((RestTemplate) restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
//            @Override
//            // Ignore 400
//            public void handleError(ClientHttpResponse response) throws IOException {
//                if (response.getRawStatusCode() != 400) {
//                    super.handleError(response);
//                }
//            }
//        });
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
        formData.add("token", "25265721-8e07-4fa2-9686-e61008834651");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic cmVzdDpyZXN0");
        ResponseEntity responseEntity = restTemplate.exchange("http://localhost:8083/auth/oauth/check_token", HttpMethod.POST,
                new HttpEntity<MultiValueMap<String, String>>(formData, headers), Map.class);
        return new GetCmpResponse<>(new ApiDTO(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
    }

    @Override
    @RequestMapping(value = "test1", method = RequestMethod.GET)
    public CmpResponse query1() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("token", "2c95cb63-625c-4b81-a224-53e10a0aa44b");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Basic dXNlcl9jbGllbnQ6MTIzNDU2");
//        restTemplate.exchange("http://localhost:8083/oauth/check_token", HttpMethod.POST,
//                new HttpEntity<MultiValueMap<String, String>>(formData, headers), Map.class).getBody();
        return new CmpResponse();
    }
}
