package com.npt.grs.model.manager.impl;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.NptBaseModelPoolIndex;
import com.npt.grs.model.dao.NptBaseModelPoolIndexDao;
import com.npt.grs.model.manager.NptBaseModelPoolIndexManager;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.rms.base.manager.impl.NptCachedManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.summer.extend.cache.ICache;
import org.summer.extend.orm.condition.Conditions;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/11 15:38
 * 备注：
 */
@Service
public class NptBaseModelPoolIndexManagerImpl extends NptCachedManagerImpl<NptBaseModelPoolIndex> implements NptBaseModelPoolIndexManager {
    @Resource(name = "rmsArchService")
    private NptRmsArchService rmsArchService;
    @Resource(name = "nptBMGroupMFieldCache")
    private ICache cache;

    @Autowired
    private NptBaseModelPoolIndexDao baseModelGroupMFieldDao;

    public ICache getCache() {
        return cache;
    }

    public void setCache(ICache cache) {
        this.cache = cache;
    }

    /**
     * 作者：owen
     * 日期：2016/10/20 12:17
     * 备注：
     * 获取模型组的列表主字段
     * 参数：
     * 返回：
     *
     * @param group
     */
    @Override
    public Collection<NptLogicDataField> getBaseModelGroupMFields(NptBaseModelGroup group) {
        Collection<NptLogicDataField> result = new ArrayList<>();
        if(null != group){
            Collection<NptBaseModelPoolIndex> mainFields = this.findByCondition(
                    Conditions.eq(NptBaseModelPool.PROPERTY_GROUP_ID,group.getId()),
                    Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));

            if(null != mainFields){
                Collection<Long> fieldIdList = new ArrayList<>();
                for(NptBaseModelPoolIndex f:mainFields){
                    fieldIdList.add(f.getFieldId());
                }
                Collection<NptLogicDataField> searchResult = rmsArchService.listDataField(fieldIdList);
                if(null != searchResult){
                    result.addAll(searchResult);
                }
            }
        }
        return result;
    }

    /**
     * 作者：owen
     * 日期：2016/10/20 12:18
     * 备注：
     * 获取字段详情
     * 参数：
     * 返回：
     *
     * @param id
     */
    @Override
    public NptLogicDataField getBaseModelGrouPoolFieldById(Long id) {
        return rmsArchService.fastFindDataFieldById(id);
    }

    @Override
    protected ICache getLocalEhcache() {
        return getCache();
    }
}
