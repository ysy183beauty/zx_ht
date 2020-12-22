package com.npt.bridge.database;

import org.hibernate.transform.ResultTransformer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 15:20
 * 描述:
 */
public class NptSqlResultTransformer implements ResultTransformer {

    @Override
    public Object transformTuple(Object[] values, String[] columns) {
        Map<String,Object> map =new LinkedHashMap<String,Object>(1);
        int i =0;
        for(String column : columns){
            Object value = values[i];
            map.put(column, null == value?"-":value);
            i++;
        }
        return map;
    }

    @Override
    public List transformList(List list) {
        return list;
    }
}
