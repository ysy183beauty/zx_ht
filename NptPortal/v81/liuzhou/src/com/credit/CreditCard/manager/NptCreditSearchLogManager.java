package com.credit.CreditCard.manager;

import com.credit.CreditCard.entity.NptCreditSearchLog;
import com.npt.bridge.base.NptBaseManager;

import java.util.Collection;

/**
 * 项目： NPTWebApp
 * 作者： zhanglei
 * 时间： 2017/06/13 11:53
 * 描述：
 */
public interface NptCreditSearchLogManager extends NptBaseManager<NptCreditSearchLog, Integer>{

    /**
     * @author zhanglei
     * @date 2017/06/12 10:48
     * @param days
     * @param limit
     * @return
     */
    Collection<NptCreditSearchLog> getCreditHotSearch(Integer days, Integer limit);
}
