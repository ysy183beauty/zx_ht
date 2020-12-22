package com.npt.bridge.arch.manager.impl;

import com.npt.bridge.arch.dao.NptLogicDataProviderDao;
import com.npt.bridge.arch.manager.NptLogicDataProviderManager;
import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.base.manager.NptBaseManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2017/1/16 16:57
 * 描述:
 */
@Service
@Transactional
public class NptLogicDataProviderManagerImpl extends NptBaseManagerImpl<NptLogicDataProvider, Long> implements NptLogicDataProviderManager {

    @Autowired
    private NptLogicDataProviderDao providerDao;

    @Override
    public NptBaseDao<NptLogicDataProvider, Long> getThisDao() {
        return providerDao;
    }

    /**
     * 作者: jss
     * 日期: 2018年3月1日16:09:04
     * 备注: 通过父code获得子部门数据
     */
    @Override
    public List<NptLogicDataProvider> listChild(Long fOrgId){
        return providerDao.listChild(fOrgId);
    }

    public List listSectionCount(Long goupId) {
        return providerDao.listSectionCount(goupId);
    }
}
