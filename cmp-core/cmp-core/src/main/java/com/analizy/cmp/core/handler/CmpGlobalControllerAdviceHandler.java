package com.analizy.cmp.core.handler;

import com.analizy.cmp.core.error.CheckErrorCode;
import com.analizy.cmp.core.error.CmpErrorCode;
import com.analizy.cmp.core.error.WebErrorCode;
import com.analizy.cmp.core.excp.CmpException;
import com.analizy.cmp.core.resp.CmpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: wangjian
 * @date: 2021/01/19 10:31
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.analizy.cmp")
public class CmpGlobalControllerAdviceHandler {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        log.info("binder.getFieldDefaultPrefix {}", binder.getFieldDefaultPrefix());
        log.info("binder.getFieldMarkerPrefix {}", binder.getFieldMarkerPrefix());
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     *
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "harry");
    }

    @ExceptionHandler(CmpException.class)
    public CmpResponse cmpExceptionHandler(CmpException cmpException) {
        return new CmpResponse(cmpException.getCmpErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public CmpResponse exceptionHandler(Exception exception) {
        log.error("服务异常：cause:[{}],message:[{}]", exception.getCause(), exception.getMessage());
        return new CmpResponse(WebErrorCode.SERVER_ERROR);
    }
}
