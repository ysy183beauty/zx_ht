package com.npt.grs.model.manager.impl;

import com.npt.grs.model.dao.NptBaseModelDao;
import com.npt.bridge.model.NptBaseModel;
import com.npt.grs.model.manager.NptBaseModelManager;
import com.npt.rms.base.manager.impl.NptCachedManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.summer.extend.cache.ICache;
import org.summer.extend.orm.condition.Conditions;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/10 20:22
 * 备注：
 */
@Service
public class NptBaseModelManagerImpl extends NptCachedManagerImpl<NptBaseModel> implements NptBaseModelManager {

    @Autowired
    private NptBaseModelDao baseModelDao;
    @Resource(name = "nptBaseModelCache")
    private ICache cache;

    public ICache getCache() {
        return cache;
    }

    public void setCache(ICache cache) {
        this.cache = cache;
    }


    /**
     * 作者：owen
     * 日期：2016/10/20 11:45
     * 备注：
     * 加载所有模型实体
     * 参数：
     * 返回：
     */
    @Override
    public Collection<NptBaseModel> listAll() {
        return this.findAll();
    }

    /**
     * 作者：owen
     * 日期：2016/10/20 11:45
     * 备注：
     * 根据宿主加载模型实体
     * 参数：
     * 返回：
     *
     * @param id
     */
    @Override
    public Collection<NptBaseModel> listByHost(Long id) {
        return this.findByCondition(Conditions.eq(NptBaseModel.PROPERTY_HOST_ID,id));
    }

    @Override
    protected ICache getLocalEhcache() {
        return getCache();
    }
}
