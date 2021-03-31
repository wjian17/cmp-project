package com.analizy.cmp.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginView {
    
    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("loginProcessUrl","http://localhost:8083/auth/oauth/login");
        return "login";
    }
}