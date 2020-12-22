package com.npt.thirdpart.fineReport.extend.dao.impl;

import com.npt.thirdpart.fineReport.extend.dao.FindReportDao;
import com.npt.thirdpart.fineReport.extend.entity.FindReport;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

import java.util.List;
import java.util.Map;

@Repository
public class FindReportDaoImpl extends HibernateSequenceBaseDaoImpl<FindReport> implements FindReportDao {
    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactoryOverride(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        super.setSessionFactory(sessionFactory);
    }
    @Override
    public List<Map> selectDataBySql(String sql) {
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List<Map> list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }
}
