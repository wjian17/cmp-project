package com.analizy.cmp.core.i18n;

import brave.propagation.ExtraFieldPropagation;
import cn.hutool.core.util.StrUtil;
import com.analizy.cmp.core.constant.HttpConstant;
import com.analizy.cmp.core.constant.LanguageType;
import com.analizy.cmp.core.error.CmpErrorCode;
import com.analizy.cmp.core.util.SpringApplicationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

/**
 * @author: wangjian
 * @date: 2021/01/18 9:17
 */
@Slf4j
public class MessageSourceUtil {

    public static String getMessage(String code, Object... args) {
        String message = null;
        try {
            message = SpringApplicationUtil.getApplicationContext().getBean(MessageSource.class).getMessage(code, args, getLanguage());
        } catch (NoSuchMessageException e) {
            log.error("资源文件未找到对应配置：{}", code);
        }
        return message;
    }

    public static String getMessage(CmpErrorCode cmpErrorCode, Object args){
        return getMessage(cmpErrorCode.getErrorCode(),args);
    }

    public static Locale getLanguage(){
        String language = ExtraFieldPropagation.get(HttpConstant.HEAD_LANGUAGE);
        if(StrUtil.isEmpty(language)){
            language = LanguageType.ZH_CN;
        }
        return Locale.forLanguageTag(language);
    }
}
