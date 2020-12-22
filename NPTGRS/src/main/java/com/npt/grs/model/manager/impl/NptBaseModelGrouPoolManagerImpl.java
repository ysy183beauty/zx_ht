package com.npt.grs.model.manager.impl;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.grs.model.dao.NptBaseModelGrouPoolDao;
import com.npt.grs.model.manager.NptBaseModelGrouPoolManager;
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
 * 日期：2016/10/10 20:22
 * 备注：
 */
@Service
public class NptBaseModelGrouPoolManagerImpl extends NptCachedManagerImpl<NptBaseModelPool> implements NptBaseModelGrouPoolManager {

    @Resource(name = "rmsArchService")
    private NptRmsArchService rmsArchService;
    @Resource(name = "nptBMGroupoolCache")
    private ICache cache;

    @Autowired
    private NptBaseModelGrouPoolDao baseModelGrouPoolDao;

    public ICache getCache() {
        return cache;
    }

    public void setCache(ICache cache) {
        this.cache = cache;
    }

    /**
     * 作者：owen
     * 日期：2016/10/20 12:09
     * 备注：
     * 获取模型组的所有数据池
     * 参数：
     * 返回：
     *
     * @param group
     */
    @Override
    public Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModelGroup group) {
        return this.findByCondition(Conditions.eq(NptBaseModelPool.PROPERTY_GROUP_ID,group.getId()));
    }



    /**
     * 作者：owen
     * 日期：2016/10/20 12:10
     * 备注：
     * 获取模型组数据池的所有字段
     * 参数：
     * 返回：
     *
     * @param p
     */
    @Override
    public Collection<NptLogicDataField> getBaseModelGrouPoolFields(NptBaseModelPool p, NptDict status) {
        Collection<NptLogicDataField> result = new ArrayList<>();
        if(null != p){
            NptLogicDataType type = rmsArchService.fastFindDataTypeById(p.getDataTypeId());
            if(null != type){
                Collection<NptLogicDataField> searchResult = rmsArchService.listDataField(type.getId(),null,status);
                result.addAll(searchResult);
            }
        }
        return result;
    }

    /**
     * 作者：owen
     * 日期：2016/10/31 14:36
     * 备注：
     * 加载默认不可视的字段集
     * 参数：
     * 返回：
     *
     * @param p
     */
    @Override
    public Collection<NptLogicDataField> getBaseModelGrouPoolDefaultHiddenFields(NptBaseModelPool p) {
        Collection<NptLogicDataField> allField = getBaseModelGrouPoolFields(p, NptDict.IDS_ENABLED);
        Collection<NptLogicDataField> result = new ArrayList<>();
        if(null != allField && !allField.isEmpty()){
            for(NptLogicDataField field:allField){
                if(field.getPubLevel().equals(NptDict.FAL_AUTH.getCode())
                        || field.getShowStyle().equals(NptDict.FSS_PARTHIDE_TEXT.name())){
                    result.add(field);
                }
            }
        }
        return result;
    }

    /**
     * 作者：owen
     * 日期：2016/10/20 12:11
     * 备注：
     * 获取模型组数据池的物理表详情
     * 参数：
     * 返回：
     *
     * @param p
     */
    @Override
    public NptLogicDataType getBaseModelGrouPoolDataType(NptBaseModelPool p) {
        if(null != p){
            return rmsArchService.fastFindDataTypeById(p.getDataTypeId());
        }
        return null;
    }

    /**
     * 作者：owen
     * 日期：2016/10/20 12:13
     * 备注：
     * 获取模型组数据池物理表的提供者详情
     * 参数：
     * 返回：
     *
     * @param p
     */
    @Override
    public NptLogicDataProvider getBaseModelGrouPoolDataTypeProvider(NptBaseModelPool p) {
        NptLogicDataType dataType = getBaseModelGrouPoolDataType(p);
        return rmsArchService.findParent(dataType);
    }

    /**
     * 作者：owen
     * 日期：2016/10/26 13:57
     * 备注：
     * 获取指定的OGR信息
     * 参数：
     * 返回：
     *
     * @param id
     */
    @Override
    public NptLogicDataProvider getBaseModelGrouPoolDataTypeProviderById(Long id) {
        return rmsArchService.fastFindOrgById(id);
    }

    /**
     * 作者：owen
     * 日期：2016/10/20 13:59
     * 备注：
     * 获取基本模型的主数据池
     * 参数：
     * 返回：
     *
     * @param m
     */
    @Override
    public NptBaseModelPool getBaseModelMainGrouPool(NptBaseModel m) {
        return this.findUniqueByCondition(
                Conditions.eq(NptBaseModelGroup.PROPERTY_MODEL_ID,m.getId()),
                Conditions.eq(NptBaseModelPool.PROPERTY_POOL_WEIGHT, NptDict.CLD_MAIN.getCode())
        );
    }

    @Override
    protected ICache getLocalEhcache() {
        return getCache();
    }
}
