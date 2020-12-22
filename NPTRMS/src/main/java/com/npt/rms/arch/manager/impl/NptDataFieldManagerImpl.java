package com.npt.rms.arch.manager.impl;

import com.npt.rms.arch.dao.NptDataFieldDao;
import com.npt.bridge.arch.NptLogicDataField;
import com.npt.rms.arch.manager.NptDataFieldManager;
import com.npt.rms.base.manager.impl.NptCachedManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.summer.extend.cache.ICache;

import javax.annotation.Resource;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/27 20:07
 * 备注：
 */
@Service
public class NptDataFieldManagerImpl extends NptCachedManagerImpl<NptLogicDataField> implements NptDataFieldManager{

    @Autowired
    private NptDataFieldDao dataFieldDao;
    @Resource(name = "nptDataFieldCache")
    private ICache cache;

    public ICache getCache() {
        return cache;
    }

    public void setCache(ICache cache) {
        this.cache = cache;
    }

    @Override
    protected ICache getLocalEhcache() {
        return getCache();
    }
}
