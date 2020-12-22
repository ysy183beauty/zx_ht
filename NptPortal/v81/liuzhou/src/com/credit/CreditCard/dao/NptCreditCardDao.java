package com.credit.CreditCard.dao;

import com.credit.CreditCard.entity.NptCreditCard;
import com.credit.CreditCard.manager.NptCreditCardManager;
import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.dict.NptDict;

import java.util.List;

/**
 * 项目： NPTWebApp
 * 作者： zhanglei
 * 时间： 2017/06/13 11:53
 * 描述：
 */
public interface NptCreditCardDao extends NptBaseDao<NptCreditCard, Integer> {
    /**
     * @see com.credit.CreditCard.manager.NptCreditCardManager#search(com.npt.bridge.dict.NptDict, java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    List<NptCreditCard> search(NptDict searchType, String key, Integer currentPage, Integer pageSize);

    /**
     * @see NptCreditCardManager#getTotolCount(com.npt.bridge.dict.NptDict, java.lang.String)
     */
    long getTotolCount(NptDict searchType, String key);
}
