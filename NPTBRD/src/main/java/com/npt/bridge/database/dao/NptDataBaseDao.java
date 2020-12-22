package com.npt.bridge.database.dao;

import org.hibernate.transform.ResultTransformer;

import java.util.List;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 14:00
 * 描述:
 */
public interface NptDataBaseDao {
    List getList(String sql, ResultTransformer transformer);

    int getCount(String sql);

    Long getLongCount(String sql);

    boolean update(String sql);
}
