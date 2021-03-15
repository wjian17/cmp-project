package com.analizy.cmp.view;

import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: wangjian
 * @date: 2021/01/19 16:39
 */
@Controller
@SessionAttributes("authorizationRequest")
public class AuthorizationView {

    @RequestMapping("/oauth/confirm_access")
    public ModelAndView getAcceConfirmation(Map<String,Object> model, HttpServletRequest request){
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        ModelAndView view = new ModelAndView();
        view.setViewName("grant");
        view.addObject("clientId", authorizationRequest.getClientId());
        view.addObject("scopes",authorizationRequest.getScope());
        return view;

    }
}
