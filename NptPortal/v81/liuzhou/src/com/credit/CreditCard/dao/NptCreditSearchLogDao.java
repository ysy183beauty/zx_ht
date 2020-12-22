package com.credit.CreditCard.dao;

import com.credit.CreditCard.entity.NptCreditSearchLog;
import com.credit.CreditCard.manager.NptCreditSearchLogManager;
import com.npt.bridge.base.NptBaseDao;

import java.util.Collection;

/**
 * 项目： NPTWebApp
 * 作者： zhanglei
 * 时间： 2017/06/13 11:53
 * 描述：
 */
public interface NptCreditSearchLogDao extends NptBaseDao<NptCreditSearchLog, Integer>{

    /**
     * @see NptCreditSearchLogManager#getCreditHotSearch(java.lang.Integer, java.lang.Integer)
     */
    Collection<NptCreditSearchLog> getCreditHotSearch(Integer days, Integer limit);
}
