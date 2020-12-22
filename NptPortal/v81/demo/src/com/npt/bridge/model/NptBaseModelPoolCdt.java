package com.npt.bridge.model;

import com.npt.bridge.base.NptEntitySerializable;
import com.npt.bridge.dict.NptDict;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 作者: owen
 * 时间: 2017/3/13 上午11:46
 * 描述:
 * 每个数据池的查询条件
 */
@Entity
@Table(name = "NPT_BMPOOL_CONDITION")
public class NptBaseModelPoolCdt extends NptEntitySerializable {


    public static final String PROPERTY_POOL_ID = "poolId";

    private Long poolId;
    private Long fieldId;
    private String fieldDBName;
    private String fieldTitle;
    private Integer conditionType;
    private String fieldQueryValue;

    public NptBaseModelPoolCdt() {
        this.setStatus(NptDict.IDS_ENABLED.getCode());
    }

    @Column(name = "POOL_ID", nullable = false)
    public Long getPoolId() {
        return poolId;
    }

    public void setPoolId(Long poolId) {
        this.poolId = poolId;
    }

    @Column(name = "FIELD_ID", nullable = false)
    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    @Column(name = "CDT_TYPE", nullable = false)
    public Integer getConditionType() {
        return conditionType;
    }

    public void setConditionType(Integer conditionType) {
        this.conditionType = conditionType;
    }

    @Transient
    public String getFieldDBName() {
        return fieldDBName;
    }

    public void setFieldDBName(String fieldDBName) {
        this.fieldDBName = fieldDBName;
    }

    @Transient
    public String getFieldTitle() {
        return fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    @Transient
    public String getFieldQueryValue() {
        return fieldQueryValue;
    }

    public void setFieldQueryValue(String fieldQueryValue) {
        this.fieldQueryValue = fieldQueryValue;
    }
}
