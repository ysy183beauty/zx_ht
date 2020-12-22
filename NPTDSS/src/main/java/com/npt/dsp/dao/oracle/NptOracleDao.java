package com.npt.dsp.dao.oracle;

import com.npt.bridge.database.dao.NptDataBaseDao;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.base.dao.NptHibernateDaoSupport;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目: NPTDataShare
 * 作者: owen
 * 日期: 16/9/28 下午2:24
 * 备注:
 */
@Repository
public class NptOracleDao extends NptHibernateDaoSupport implements NptDataBaseDao {
    @Override
    public List getList(String sql,ResultTransformer t) {

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
            return NptCommonUtil.IntegerZero();
        }
    }

    @Override
    public Long getLongCount(String sql) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery(sql);

            BigDecimal result = (BigDecimal) query.uniqueResult();

            return result.longValue();
        }catch (Exception e){
            return 0L;
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
