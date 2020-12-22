package com.npt.rms.arch.manager.impl;

import com.npt.rms.arch.dao.NptDataTypeDao;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.rms.arch.manager.NptDataTypeManager;
import com.npt.rms.base.manager.impl.NptCachedManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.summer.extend.cache.ICache;

import javax.annotation.Resource;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/27 20:06
 * 备注：
 */
@Service
public class NptDataTypeManagerImpl extends NptCachedManagerImpl<NptLogicDataType> implements NptDataTypeManager{

    @Autowired
    private NptDataTypeDao dataTypeDao;
    @Resource(name = "nptDataTypeCache")
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
