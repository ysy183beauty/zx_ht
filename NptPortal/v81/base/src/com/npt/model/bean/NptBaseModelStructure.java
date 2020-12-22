package com.npt.model.bean;

import com.npt.arch.entity.NptLogicDataField;
import com.npt.arch.entity.NptLogicDataTable;
import com.npt.model.entity.NptBaseModel;
import com.npt.model.entity.NptBaseModelGroup;
import com.npt.model.entity.NptBaseModelMainField;
import com.npt.model.entity.NptBaseModelPool;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 11:59
 * 描述:
 */
public class NptBaseModelStructure implements Serializable{
    private NptBaseModel model;
    private Collection<NptBaseModelGroup> modelGroups;
    private Map<Long,Collection<NptBaseModelPool>> grouPoolMap;
    private Map<Long,NptLogicDataTable> poolDataType;
    private Map<Long,Collection<NptLogicDataField>> typeFieldMap;
    private Collection<NptBaseModelMainField> mainFieldList;

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

    public Map<Long, NptLogicDataTable> getPoolDataType() {
        return poolDataType;
    }

    public void setPoolDataType(Map<Long, NptLogicDataTable> poolDataType) {
        this.poolDataType = poolDataType;
    }

    public Collection<NptBaseModelMainField> getMainFieldList() {
        return mainFieldList;
    }

    public void setMainFieldList(Collection<NptBaseModelMainField> mainFieldList) {
        this.mainFieldList = mainFieldList;
    }
}
