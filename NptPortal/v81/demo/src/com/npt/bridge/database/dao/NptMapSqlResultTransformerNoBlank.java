package com.npt.bridge.database.dao;

import com.npt.bridge.util.NptCommonUtil;
import org.hibernate.transform.ResultTransformer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/21 10:26
 * 备注：
 */
public class NptMapSqlResultTransformerNoBlank<T> implements ResultTransformer{

    @Override
    public Object transformTuple(Object[] values, String[] columns) {
        Map<String,Object> map =new LinkedHashMap<String,Object>(1);
        int i =0;
        for(String column : columns){
            Object value = values[i];
            map.put(column, null == value? NptCommonUtil.BLANK_DATA_PRESENT:value);
            i++;
        }
        return map;
    }

    @Override
    public List transformList(List list) {
        return list;
    }
}
