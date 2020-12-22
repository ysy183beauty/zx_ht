package com.npt.rms.arch.manager.impl;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.dict.NptDict;
import com.npt.rms.arch.bean.ZTree;
import com.npt.rms.arch.dao.NptOrganizationDao;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.rms.arch.manager.NptOrganizationManager;
import com.npt.rms.base.manager.impl.NptCachedManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.summer.extend.cache.ICache;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/26 15:55
 * 备注：
 */
@Service
public class NptOrganizationManagerImpl extends NptCachedManagerImpl<NptLogicDataProvider> implements NptOrganizationManager{

    @Autowired
    private NptOrganizationDao orgDao;
    @Resource(name = "nptOrganizationCache")
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

    @Override
    public List<Map> selectUnitInfo(String sql) {
        return this.orgDao.selectUnitInfo(sql);
    }

    @Override
    public List<ZTree> selectOrganTreeList(Long organId) {
        return this.orgDao.selectOrganTreeList(organId);
    }

    @Override
    public Integer selectCurrentModelId(NptDict host) {
        return this.orgDao.selectCurrentModelId(host);
    }

    @Override
    public List<Map> selectpoolId(String sql) {
        return this.orgDao.selectpoolId(sql);
    }

    @Override
    public Integer updateCondition(String sql) {
        return this.orgDao.updateCondition(sql);
    }
}
