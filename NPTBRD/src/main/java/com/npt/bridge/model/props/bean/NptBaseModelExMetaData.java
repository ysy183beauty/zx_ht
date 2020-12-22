package com.npt.bridge.model.props.bean;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.model.NptBaseModelPool;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * author: owen
 * date:   2017/7/11 上午11:34
 * note:
 */
public class NptBaseModelExMetaData<
        MP extends NptBaseModelProperties,
        GP extends NptBaseModelGroupProperties,
        PP extends NptBaseModelPoolProperties> implements Serializable{


    public NptBaseModelExMetaData(){
        this.completed = false;
        this.completeNote = "刚被初始化，未进行任何操作";
    }


    private NptBaseModelEx<MP> modelEx;

    private NptBaseModelPool mainPool;
    private NptLogicDataType pkDataType;
    private NptLogicDataField pkField;
    private NptLogicDataField ukField;

    private Collection<NptBaseModelGroupEx<GP>> groupExes;

    private Map<Long,List<NptBaseModelPoolEx<PP>>> poolExMap;

    Boolean completed;
    String completeNote;

    public NptBaseModelEx<MP> getModelEx() {
        return modelEx;
    }

    public void setModelEx(NptBaseModelEx<MP> modelEx) {
        this.modelEx = modelEx;
    }

    public Collection<NptBaseModelGroupEx<GP>> getGroupExes() {
        return groupExes;
    }

    public void setGroupExes(Collection<NptBaseModelGroupEx<GP>> groupExes) {
        this.groupExes = groupExes;
    }

    public Map<Long, List<NptBaseModelPoolEx<PP>>> getPoolExMap() {
        return poolExMap;
    }

    public void setPoolExMap(Map<Long, List<NptBaseModelPoolEx<PP>>> poolExMap) {
        this.poolExMap = poolExMap;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getCompleteNote() {
        return completeNote;
    }

    public void setCompleteNote(String completeNote) {
        this.completeNote = completeNote;
    }

    public NptBaseModelPool getMainPool() {
        return mainPool;
    }

    public void setMainPool(NptBaseModelPool mainPool) {
        this.mainPool = mainPool;
    }

    public NptLogicDataField getPkField() {
        return pkField;
    }

    public void setPkField(NptLogicDataField pkField) {
        this.pkField = pkField;
    }

    public NptLogicDataType getPkDataType() {
        return pkDataType;
    }

    public void setPkDataType(NptLogicDataType pkDataType) {
        this.pkDataType = pkDataType;
    }

    public NptLogicDataField getUkField() {
        return ukField;
    }

    public void setUkField(NptLogicDataField ukField) {
        this.ukField = ukField;
    }
}
