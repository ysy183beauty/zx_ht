package com.credit.CreditCard.manager.impl;

import com.credit.CreditCard.dao.NptCreditSearchLogDao;
import com.credit.CreditCard.entity.NptCreditSearchLog;
import com.credit.CreditCard.manager.NptCreditSearchLogManager;
import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.base.manager.NptBaseManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 项目： NPTWebApp
 * 作者： zhanglei
 * 时间： 2017/06/13 11:53
 * 描述：
 */
@Service
@Transactional
public class NptCreditSearchLogManagerImpl extends NptBaseManagerImpl<NptCreditSearchLog, Integer> implements NptCreditSearchLogManager {

    @Autowired
    private NptCreditSearchLogDao dao;

    @Override
    public NptBaseDao<NptCreditSearchLog, Integer> getThisDao() {
        return dao;
    }

    @Override
    public Collection<NptCreditSearchLog> getCreditHotSearch(Integer days, Integer limit) {
        return dao.getCreditHotSearch(days, limit);
    }
}
