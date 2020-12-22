package com.credit.CreditCard.dao.impl;

import com.credit.CreditCard.dao.NptCreditCardDao;
import com.credit.CreditCard.entity.NptCreditCard;
import com.npt.base.NptBaseDaoImpl;
import com.npt.bridge.dict.NptDict;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目： NPTWebApp
 * 作者： zhanglei
 * 时间： 2017/06/13 11:53
 * 描述：
 */
@Repository
public class NptCreditCardDaoImpl extends NptBaseDaoImpl<NptCreditCard, Integer> implements NptCreditCardDao {
    @Override
    protected Class<NptCreditCard> getEntityClass() {
        return NptCreditCard.class;
    }

    @Override
    public List<NptCreditCard> search(NptDict searchType, String key, Integer currentPage, Integer pageSize) {
        Criteria criteria = getSession().createCriteria(NptCreditCard.class);
        if (searchType != null && searchType != NptDict.PDM_UNKNOW) {
            criteria.add(Restrictions.eq(NptCreditCard.PROPERTY_CARD_TYPE, searchType.getCode()));
        }
        if (key != null) {
            criteria.add(Restrictions.like(NptCreditCard.PROPERTY_CARD_TITLE, key, MatchMode.ANYWHERE));
        }
        criteria.addOrder(Order.asc(NptCreditCard.PROPERTY_CARD_TITLE));
        criteria.setFirstResult((currentPage - 1) * pageSize);
        criteria.setMaxResults(pageSize);
        return criteria.list();
    }

    @Override
    public long getTotolCount(NptDict searchType, String key) {
        Criteria criteria = getSession().createCriteria(NptCreditCard.class);
        if (searchType != null && searchType != NptDict.PDM_UNKNOW) {
            criteria.add(Restrictions.eq(NptCreditCard.PROPERTY_CARD_TYPE, searchType.getCode()));
        }
        if (key != null) {
            criteria.add(Restrictions.like(NptCreditCard.PROPERTY_CARD_TITLE, key, MatchMode.ANYWHERE));
        }
        return ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
    }

}
