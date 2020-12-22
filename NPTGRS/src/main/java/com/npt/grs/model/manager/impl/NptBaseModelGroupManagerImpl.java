package com.npt.grs.model.manager.impl;

import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.grs.model.dao.NptBaseModelGroupDao;
import com.npt.grs.model.manager.NptBaseModelGroupManager;
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
 * 日期：2016/10/20 11:34
 * 备注：
 */
@Service
public class NptBaseModelGroupManagerImpl extends NptCachedManagerImpl<NptBaseModelGroup> implements NptBaseModelGroupManager{
    @Autowired
    private NptBaseModelGroupDao baseModelGroupDao;
    @Resource(name = "nptBMGroupCache")
    private ICache cache;

    public ICache getCache() {
        return cache;
    }

    public void setCache(ICache cache) {
        this.cache = cache;
    }


    /**
     * 作者：owen
     * 日期：2016/10/20 11:54
     * 备注：
     * 获取模型下的所有分组
     * 参数：
     * 返回：
     *
     * @param m
     */
    @Override
    public Collection<NptBaseModelGroup> getBaseModelGroups(NptBaseModel m) {
        return this.findByCondition(
                Conditions.eq(NptBaseModelGroup.PROPERTY_MODEL_ID,m.getId()),
                Conditions.eq(NptBaseModelGroup.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()),
                Conditions.asc(NptBaseEntity.PROPERTY_DISPLAY_ORDER));
    }

    @Override
    protected ICache getLocalEhcache() {
        return getCache();
    }
}
