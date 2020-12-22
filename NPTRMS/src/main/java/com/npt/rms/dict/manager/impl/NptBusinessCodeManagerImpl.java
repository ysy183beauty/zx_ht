package com.npt.rms.dict.manager.impl;

import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dict.NptBusinessCode;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.base.manager.impl.NptCachedManagerImpl;
import com.npt.rms.dict.dao.NptBusinessCodeDao;
import com.npt.rms.dict.manager.NptBusinessCodeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.summer.extend.cache.ICache;
import org.summer.extend.orm.condition.Conditions;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/17 21:25
 * 备注：
 */
@Service("rmsCodeTableManager")
public class NptBusinessCodeManagerImpl extends NptCachedManagerImpl<NptBusinessCode> implements NptBusinessCodeManager {

    @Autowired
    private NptBusinessCodeDao codeTableDao;
    @Resource(name = "nptBusinessCodeCache")
    private ICache cache;

    public ICache getCache() {
        return cache;
    }

    public void setCache(ICache cache) {
        this.cache = cache;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/28 17:27
     * 描述:
     * 通过码类型与码值获取唯一的码信息
     *
     * @param codeValue
     * @param fieldId
     */
    @Override
    public NptBusinessCode getCode(String codeValue, Long fieldId) {
        return this.findUniqueByCondition(
                Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()),
                Conditions.eq(NptBusinessCode.PROPERTY_CODE_VALUE,codeValue),
                Conditions.eq(NptBusinessCode.PROPERTY_CODE_FIELDID,fieldId));
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/28 17:28
     * 描述:
     * 加载指定码类型的所有码信息
     *
     * @param fieldId
     */
    @Override
    public Collection<NptBusinessCode> listCode(Long fieldId) {
        return this.findByCondition(
                Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()),
                Conditions.eq(NptBusinessCode.PROPERTY_CODE_FIELDID,fieldId),
                Conditions.asc(NptBaseEntity.PROPERTY_DISPLAY_ORDER));
    }

    @Override
    public Collection<NptBusinessCode> listAll() {
        return this.findByCondition(
                Conditions.eq(NptBaseEntity.PROPERTY_STATUS,NptDict.IDS_ENABLED.getCode()),
                Conditions.gt(NptBaseEntity.PROPERTY_ID, Long.valueOf(NptCommonUtil.INTEGER_0)));
    }

    @Override
    public void updateSyncStamp(Date date) {
        NptBusinessCode lastSync = findById(NptBusinessCodeManager.CODE_LAST_SYNC_STAMP_ID);
        if(null != lastSync){
            lastSync.setLastModifyTime(date);

            this.update(lastSync);
        }
    }

    @Override
    public Collection<NptBusinessCode> listIncrement(Date date) {
        Collection<NptBusinessCode> result = new ArrayList<>();

        NptBaseEntity lastSync = findById(NptBusinessCodeManager.CODE_LAST_SYNC_STAMP_ID);
        if(null != lastSync && null != date){
            Collection<NptBusinessCode> needSync = this.findByCondition(
                    Conditions.eq(NptBaseEntity.PROPERTY_STATUS,NptDict.IDS_ENABLED.getCode()),
                    Conditions.gt(NptBusinessCode.PROPERTY_CODE_FIELDID,Long.valueOf(NptCommonUtil.INTEGER_0)),
                    Conditions.gt(NptBaseEntity.PROPERTY_CREATE_TIME,lastSync.getLastModifyTime()),
                    Conditions.le(NptBaseEntity.PROPERTY_CREATE_TIME,date));

            if(null != needSync && !needSync.isEmpty()){
                result.addAll(needSync);
            }
        }

        return result;
    }

    @Override
    public void deleteAndSaveCode(Long fieldId, Collection<NptBusinessCode> codes) {
        this.deleteByProperty(NptBusinessCode.PROPERTY_CODE_FIELDID, fieldId);
        this.saveAll(codes);
    }

    @Override
    protected ICache getLocalEhcache() {
        return getCache();
    }
}
