package com.analizy.cmp.core.resp;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @author: wangjian
 * @date: 2021/01/18 11:18
 */
public class ListCmpResponse<T> extends CmpResponse {

    private List<T> rows;

    public ListCmpResponse(List<T> rows){
        this.rows = rows;
    }
}
