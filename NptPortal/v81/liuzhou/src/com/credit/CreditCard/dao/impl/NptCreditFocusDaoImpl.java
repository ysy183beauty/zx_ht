package com.credit.CreditCard.dao.impl;

import com.credit.CreditCard.dao.NptCreditFocusDao;
import com.credit.CreditCard.entity.NptCreditFocus;
import com.npt.base.NptBaseDaoImpl;
import com.npt.bridge.dict.NptDict;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 项目： NPTWebApp
 * 作者： zhanglei
 * 时间： 2017/06/13 11:53
 * 描述：
 */
@Repository
public class NptCreditFocusDaoImpl extends NptBaseDaoImpl<NptCreditFocus, Integer> implements NptCreditFocusDao {
    @Override
    protected Class<NptCreditFocus> getEntityClass() {
        return NptCreditFocus.class;
    }

    @Override
    public Collection<NptCreditFocus> getCreditTalent(NptDict searchType, Integer limit) {
        Criteria criteria = getSession().createCriteria(NptCreditFocus.class);
        if (searchType != null && searchType != NptDict.PDM_UNKNOW) {
            criteria.add(Restrictions.eq(NptCreditFocus.PROPERTY_TO_CARD_TYPE, searchType.getCode()));
        }
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.groupProperty(NptCreditFocus.PROPERTY_TO_CARD_ID), NptCreditFocus.PROPERTY_TO_CARD_ID);
        projectionList.add(Projections.rowCount(), NptCreditFocus.PROPERTY_HOT_VALUE);
        criteria.setProjection(projectionList);
        criteria.addOrder(Order.desc(NptCreditFocus.PROPERTY_HOT_VALUE));
        if (limit != null) {
            criteria.setMaxResults(limit);
        }
        criteria.setResultTransformer(Transformers.aliasToBean(NptCreditFocus.class));
        return criteria.list();
    }

    @Override
    public boolean isFollowing(Integer fromUserId, Integer toUserId) {
        Criteria criteria = getSession().createCriteria(NptCreditFocus.class);
        criteria.add(Restrictions.eq(NptCreditFocus.PROPERTY_FROM_CARD_ID, fromUserId));
        criteria.add(Restrictions.eq(NptCreditFocus.PROPERTY_TO_CARD_ID, toUserId));
        criteria.setProjection(Projections.rowCount());
        return ((Number) criteria.uniqueResult()).intValue() != 0;
    }

    @Override
    public NptCreditFocus findFocus(Integer fromUserId, Integer toUserId) {
        Criteria criteria = getSession().createCriteria(NptCreditFocus.class);
        criteria.add(Restrictions.eq(NptCreditFocus.PROPERTY_FROM_CARD_ID, fromUserId));
        criteria.add(Restrictions.eq(NptCreditFocus.PROPERTY_TO_CARD_ID, toUserId));
        return (NptCreditFocus) criteria.uniqueResult();
    }

    @Override
    public long getFollowingCount(Integer fromUserId) {
        Criteria criteria = getSession().createCriteria(NptCreditFocus.class);
        criteria.add(Restrictions.eq(NptCreditFocus.PROPERTY_FROM_CARD_ID, fromUserId));
        return ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
    }

    @Override
    public Collection<NptCreditFocus> getFollowing(Integer fromUserId, Integer currentPage, Integer pageSize) {
        Criteria criteria = getSession().createCriteria(NptCreditFocus.class);
        criteria.add(Restrictions.eq(NptCreditFocus.PROPERTY_FROM_CARD_ID, fromUserId));
        criteria.addOrder(Order.desc(NptCreditFocus.PROPERTY_CREATE_TIME));
        criteria.setFirstResult((currentPage - 1) * pageSize);
        criteria.setMaxResults(pageSize);
        return criteria.list();
    }

    @Override
    public long getFollowerCount(Integer toUserId) {
        Criteria criteria = getSession().createCriteria(NptCreditFocus.class);
        criteria.add(Restrictions.eq(NptCreditFocus.PROPERTY_TO_CARD_ID, toUserId));
        return ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
    }

    @Override
    public Collection<NptCreditFocus> getFollower(Integer toUserId, Integer currentPage, Integer pageSize) {
        Criteria criteria = getSession().createCriteria(NptCreditFocus.class);
        criteria.add(Restrictions.eq(NptCreditFocus.PROPERTY_TO_CARD_ID, toUserId));
        criteria.addOrder(Order.desc(NptCreditFocus.PROPERTY_CREATE_TIME));
        criteria.setFirstResult((currentPage - 1) * pageSize);
        criteria.setMaxResults(pageSize);
        return criteria.list();
    }
}
