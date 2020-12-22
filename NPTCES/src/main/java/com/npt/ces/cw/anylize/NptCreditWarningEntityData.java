package com.npt.ces.cw.anylize;

import com.npt.bridge.dataBinder.NptWebFieldDataArray;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: owen
 * date:   2017/7/10 下午3:04
 * note:
 *      信用预警针对单一信用实体的待分析实体数据集合
 */
public class NptCreditWarningEntityData {

    //预警模型ID
    protected Long modelId;
    //信用实体业务主键
    private String creditEntityId;
    //信用实体标题
    private String creditEntityTitle;
    //信用实体类型
    private Integer creditEntityType;
    //信用实体基础信息
    private NptWebFieldDataArray creditEntityBasicInfo;
    //信用实体真实数据[poolId -> <fieldName,fieldData>]
    private Map<Long,Collection<NptWebFieldDataArray>> entityDataMap;
    //实体数据是否已完成加载
    private Boolean dataLoaded;
    private String dataLoadedNote;

    public NptCreditWarningEntityData(){
        modelId = NptCommonUtil.getDefaultParentId();
        creditEntityId = StringUtils.EMPTY;
        creditEntityTitle = StringUtils.EMPTY;
        entityDataMap = new HashMap<>();
        dataLoaded = false;
    }


    public String getCreditEntityId() {
        return creditEntityId;
    }

    public void setCreditEntityId(String creditEntityId) {
        this.creditEntityId = creditEntityId;
    }

    public String getCreditEntityTitle() {
        return creditEntityTitle;
    }

    public void setCreditEntityTitle(String creditEntityTitle) {
        this.creditEntityTitle = creditEntityTitle;
    }

    public Map<Long, Collection<NptWebFieldDataArray>> getEntityDataMap() {
        return entityDataMap;
    }

    public void setEntityDataMap(Map<Long, Collection<NptWebFieldDataArray>> entityDataMap) {
        this.entityDataMap = entityDataMap;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Boolean getDataLoaded() {
        return dataLoaded;
    }

    public void setDataLoaded(Boolean dataLoaded) {
        this.dataLoaded = dataLoaded;
    }

    public NptWebFieldDataArray getCreditEntityBasicInfo() {
        return creditEntityBasicInfo;
    }

    public void setCreditEntityBasicInfo(NptWebFieldDataArray creditEntityBasicInfo) {
        this.creditEntityBasicInfo = creditEntityBasicInfo;
    }

    public String getDataLoadedNote() {
        return dataLoadedNote;
    }

    public void setDataLoadedNote(String dataLoadedNote) {
        this.dataLoadedNote = dataLoadedNote;
    }

    public Integer getCreditEntityType() {
        return creditEntityType;
    }

    public void setCreditEntityType(Integer creditEntityType) {
        this.creditEntityType = creditEntityType;
    }
}
