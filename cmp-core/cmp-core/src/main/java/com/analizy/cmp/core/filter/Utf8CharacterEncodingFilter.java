package com.analizy.cmp.core.filter;

import com.analizy.cmp.core.constant.HttpConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: wangjian
 * @date: 2021/01/22 9:42
 */
//@Configuration
public class Utf8CharacterEncodingFilter extends CharacterEncodingFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setContentType(HttpConstant.APPLICATION_JSON);
        response.setCharacterEncoding(HttpConstant.UTF_8);
        filterChain.doFilter(request,response);
    }
}
