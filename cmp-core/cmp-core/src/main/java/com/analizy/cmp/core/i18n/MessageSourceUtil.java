package com.analizy.cmp.core.i18n;

import brave.propagation.ExtraFieldPropagation;
import cn.hutool.core.util.StrUtil;
import com.analizy.cmp.core.constant.HttpConstant;
import com.analizy.cmp.core.constant.LanguageType;
import com.analizy.cmp.core.error.CmpErrorCode;
import com.analizy.cmp.core.util.SpringApplicationUtil;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * @author: wangjian
 * @date: 2021/01/18 9:17
 */
public class MessageSourceUtil {

    public static String getMessage(String code,Object... args){
        return SpringApplicationUtil.getApplicationContext().getBean(MessageSource.class).getMessage(code,args,getLanguage());
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
