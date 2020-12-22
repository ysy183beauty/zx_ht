package com.credit.CreditCard.dao.impl;

import com.credit.CreditCard.dao.NptCreditSearchLogDao;
import com.credit.CreditCard.entity.NptCreditSearchLog;
import com.npt.base.NptBaseDaoImpl;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;

/**
 * 项目： NPTWebApp
 * 作者： zhanglei
 * 时间： 2017/06/13 11:53
 * 描述：
 */
@Repository
public class NptCreditSearchLogDaoImpl extends NptBaseDaoImpl<NptCreditSearchLog, Integer> implements NptCreditSearchLogDao {

    @Override
    public Collection<NptCreditSearchLog> getCreditHotSearch(Integer days, Integer limit) {
        Criteria criteria = getSession().createCriteria(NptCreditSearchLog.class);
        if(days != null){
            long DAY_IN_MS = 1000 * 60 * 60 * 24;
            criteria.add(Restrictions.ge(NptCreditSearchLog.PROPERTY_CREATE_TIME, new Timestamp(System.currentTimeMillis() - (days * DAY_IN_MS))));
        }
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.groupProperty(NptCreditSearchLog.PROPERTY_SEARCH_KEY), NptCreditSearchLog.PROPERTY_SEARCH_KEY);
        projectionList.add(Projections.rowCount(), NptCreditSearchLog.PROPERTY_HOT_VALUE);
        criteria.setProjection(projectionList);
        criteria.addOrder(Order.desc(NptCreditSearchLog.PROPERTY_HOT_VALUE));
        if (limit != null) {
            criteria.setMaxResults(limit);
        }
        criteria.setResultTransformer(Transformers.aliasToBean(NptCreditSearchLog.class));
        return criteria.list();
    }

    @Override
    protected Class<NptCreditSearchLog> getEntityClass() {
        return NptCreditSearchLog.class;
    }
}
