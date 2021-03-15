package com.analizy.cmp.core.filter;

import brave.propagation.ExtraFieldPropagation;
import com.analizy.cmp.core.constant.HttpConstant;
import com.analizy.cmp.core.constant.LanguageType;
import org.springframework.cloud.sleuth.instrument.web.SleuthWebProperties;
import org.springframework.cloud.sleuth.instrument.web.TraceWebServletAutoConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * 自定义过滤器,
 * 获取当前的SessionId, 放入Baggage中
 * 注意, 因为不是所有的请求都需要往后传递, 所以会对一些请求跳过执行
 *
 * @author: wangjian
 * @date: 2021/01/19 11:36
 */
@Component
@Order(TraceWebServletAutoConfiguration.TRACING_FILTER_ORDER + 1)
public class TraceFilter extends GenericFilterBean {

    private Pattern skipPattern = Pattern.compile(SleuthWebProperties.DEFAULT_SKIP_PATTERN);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            throw new ServletException("Filter just supports HTTP requests");
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        boolean skip = skipPattern.matcher(httpRequest.getRequestURI()).matches();

        if (!skip) {
            ExtraFieldPropagation.set(HttpConstant.SESSION_ID, httpRequest.getSession().getId());
            String language = httpRequest.getHeader(HttpConstant.ACCEPT_LANGUAGE);
            ExtraFieldPropagation.set(HttpConstant.ACCEPT_LANGUAGE, language == null ? LanguageType.ZH_CN : language);
        }
        filterChain.doFilter(request, response);
    }
}
