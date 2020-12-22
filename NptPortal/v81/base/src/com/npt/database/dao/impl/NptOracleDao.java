package com.npt.database.dao.impl;

import com.jeecms.common.hibernate4.HibernateSimpleDao;
import com.npt.database.dao.NptDataBaseDao;
import com.npt.util.NptUtil;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 14:01
 * 描述:
 */
@Repository
public class NptOracleDao extends HibernateSimpleDao implements NptDataBaseDao{
    @Override
    public List getList(String sql, ResultTransformer t) {

        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery(sql);
            if(null != t){
                query.setResultTransformer(t);
            }
            List result = query.list();
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList();
        }
    }

    @Override
    public int getCount(String sql) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery(sql);

            BigDecimal result = (BigDecimal) query.uniqueResult();

            return result.intValue();
        }catch (Exception e){
            return NptUtil.INTEGER_0;
        }
    }

    @Override
    public boolean update(String sql) {
        try {
            Session session = sessionFactory.getCurrentSession();
            SQLQuery sqlQuery =  session.createSQLQuery(sql);
            sqlQuery.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
