package com.analizy.cmp.core.util;


import brave.Tracer;

/**
 * @author: wangjian
 * @date: 2021/01/13 15:58
 */
public class TracerUtil {

    public static String getTracerId() {
        Tracer tracer = SpringApplicationUtil.getApplicationContext().getBean(Tracer.class);
        return tracer != null && tracer.currentSpan() != null ? tracer.currentSpan().context().traceIdString() : null;
    }
}
