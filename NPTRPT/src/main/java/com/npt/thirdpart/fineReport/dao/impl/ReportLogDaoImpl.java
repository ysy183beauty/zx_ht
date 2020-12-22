package com.npt.thirdpart.fineReport.dao.impl;

import com.npt.thirdpart.fineReport.dao.ReportLogDao;
import com.npt.thirdpart.fineReport.entity.ReportLogBean;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

import java.util.List;
import java.util.Map;

/**
 * 项目: NPTWebApp
 * 作者: 张磊
 * 日期: 16/11/28 下午2:16
 * 备注:
 */
@Repository
public class ReportLogDaoImpl extends HibernateSequenceBaseDaoImpl<ReportLogBean> implements ReportLogDao {
    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactoryOverride(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        super.setSessionFactory(sessionFactory);
    }
    @Override
    public List<Map> selectPersonReportHistory(String sql) {
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List<Map> list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }
}
