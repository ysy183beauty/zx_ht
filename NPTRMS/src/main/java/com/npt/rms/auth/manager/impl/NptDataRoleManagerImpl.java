package com.npt.rms.auth.manager.impl;

import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dict.NptDict;
import com.npt.rms.auth.dao.NptDataRoleDao;
import com.npt.rms.auth.entity.NptDataRole;
import com.npt.rms.auth.manager.NptDataRoleManager;
import com.npt.rms.base.manager.impl.NptCachedManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.summer.extend.cache.ICache;
import org.summer.extend.orm.condition.Conditions;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/27 21:35
 * 备注：
 */
@Service
public class NptDataRoleManagerImpl extends NptCachedManagerImpl<NptDataRole> implements NptDataRoleManager{

    @Autowired
    private NptDataRoleDao dataRoleDao;
    @Resource(name = "nptDataRoleCache")
    private ICache cache;

    public ICache getCache() {
        return cache;
    }

    public void setCache(ICache cache) {
        this.cache = cache;
    }

    /**
     * 作者：owen
     * 日期：2016/10/31 21:36
     * 备注：
     * 加载所有可用的角色
     * 参数：
     * 返回：
     */
    @Override
    public Collection<NptDataRole> listRoles() {
        return this.findByCondition(Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
    }

    @Override
    protected ICache getLocalEhcache() {
        return this.getCache();
    }

    @Override
    public Boolean checkRole(String roleName) {
        NptDataRole dataRole = dataRoleDao.findUniqueByCondition(
                Conditions.eq(NptDataRole.PROPERTY_ROLE_NAME,roleName)
        );
        if (null == dataRole){
            return true;
        }
        return false;
    }
}
