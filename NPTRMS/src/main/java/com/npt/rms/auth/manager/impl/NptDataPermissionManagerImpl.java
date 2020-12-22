package com.npt.rms.auth.manager.impl;

import com.npt.rms.auth.dao.NptDataPermissionDao;
import com.npt.rms.auth.entity.NptDataPermission;
import com.npt.rms.auth.manager.NptDataPermissionManager;
import com.npt.rms.base.manager.impl.NptCachedManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.cache.ICache;
import org.summer.extend.orm.condition.Conditions;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/22 15:08
 * 描述:
 */
@Service
public class NptDataPermissionManagerImpl extends NptCachedManagerImpl<NptDataPermission> implements NptDataPermissionManager{
    @Autowired
    private NptDataPermissionDao permissionDao;
    @Resource(name = "nptDataPermissionCache")
    private ICache cache;

    @Override
    protected ICache getLocalEhcache() {
        return this.getCache();
    }

    public ICache getCache() {
        return cache;
    }

    public void setCache(ICache cache) {
        this.cache = cache;
    }

    @Override
    public boolean checkPermission(Integer actionId, Long orgId) {
        NptDataPermission permission = permissionDao.findUniqueByCondition(
                Conditions.eq(NptDataPermission.PROPERTY_ORG_ID,orgId),
                Conditions.eq(NptDataPermission.PROPERTY_ACTION,actionId)
        );
        if (null == permission){
            return true;
        }
        return false;
    }
}
