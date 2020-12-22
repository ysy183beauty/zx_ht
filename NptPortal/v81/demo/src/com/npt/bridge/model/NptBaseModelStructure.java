package com.npt.bridge.model;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataType;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/28 14:46
 * 描述:
 */
public class NptBaseModelStructure implements Serializable{
    private NptBaseModel model;
    private Collection<NptBaseModelGroup> modelGroups;
    private Map<Long,Collection<NptBaseModelPool>> grouPoolMap;
    private Map<Long,NptLogicDataType> poolDataType;
    private Map<Long,Collection<NptLogicDataField>> typeFieldMap;
    private Map<Long,Collection<NptBaseModelPoolIndex>> poolIndexMap;
    private Map<Long,Collection<NptBaseModelPoolCdt>> poolCdtMap;

    public NptBaseModel getModel() {
        return model;
    }

    public void setModel(NptBaseModel model) {
        this.model = model;
    }

    public Collection<NptBaseModelGroup> getModelGroups() {
        return modelGroups;
    }

    public void setModelGroups(Collection<NptBaseModelGroup> modelGroups) {
        this.modelGroups = modelGroups;
    }

    public Map<Long, Collection<NptBaseModelPool>> getGrouPoolMap() {
        return grouPoolMap;
    }

    public void setGrouPoolMap(Map<Long, Collection<NptBaseModelPool>> grouPoolMap) {
        this.grouPoolMap = grouPoolMap;
    }

    public Map<Long, Collection<NptLogicDataField>> getTypeFieldMap() {
        return typeFieldMap;
    }

    public void setTypeFieldMap(Map<Long, Collection<NptLogicDataField>> poolFieldMap) {
        this.typeFieldMap = poolFieldMap;
    }

    public Map<Long, NptLogicDataType> getPoolDataType() {
        return poolDataType;
    }

    public void setPoolDataType(Map<Long, NptLogicDataType> poolDataType) {
        this.poolDataType = poolDataType;
    }

    public Map<Long, Collection<NptBaseModelPoolIndex>> getPoolIndexMap() {
        return poolIndexMap;
    }

    public void setPoolIndexMap(Map<Long, Collection<NptBaseModelPoolIndex>> poolIndexMap) {
        this.poolIndexMap = poolIndexMap;
    }

    public Map<Long, Collection<NptBaseModelPoolCdt>> getPoolCdtMap() {
        return poolCdtMap;
    }

    public void setPoolCdtMap(Map<Long, Collection<NptBaseModelPoolCdt>> poolCdtMap) {
        this.poolCdtMap = poolCdtMap;
    }
}
