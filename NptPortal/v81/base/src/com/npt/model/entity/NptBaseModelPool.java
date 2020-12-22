package com.npt.model.entity;

import com.npt.base.NptBaseEntity;
import com.npt.base.NptEntitySerializable;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/10 20:05
 * 备注：
 */
@Entity
@Table(name = "NPT_BASEMODEL_GROUPOOL")
public class NptBaseModelPool extends NptEntitySerializable {
    @Override
    public void copyTo(NptBaseEntity entity) {
        Assert.isTrue(entity instanceof NptBaseModelPool);
        super.copyTo(entity);
        NptBaseModelPool that = (NptBaseModelPool) entity;
        that.setModelId(this.getModelId());
        that.setDataTypeId(this.getDataTypeId());
        that.setGroupId(this.getGroupId());
        that.setPrimaryFieldId(this.getPrimaryFieldId());
        that.setMainPool(this.getMainPool());
    }

    public static final String PROPERTY_MODEL_ID = "modelId";
    public static final String PROPERTY_GROUP_ID = "groupId";
    public static final String PROPERTY_POOL_WEIGHT = "mainPool";

    private String pFieldTitle;
    private String providerTitle;
    private String poolTitle;
    private Long modelId;
    private Long groupId;
    private Long dataTypeId;
    private Long primaryFieldId;
    private Integer mainPool;
    private Integer linkedPoolCount;

    @Column(name = "MODEL_ID",nullable = false)
    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    @Column(name = "GROUP_ID",nullable = false)
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Column(name = "DATATYPE_ID",nullable = false)
    public Long getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(Long dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    @Column(name = "PFIELD_ID",nullable = false)
    public Long getPrimaryFieldId() {
        return primaryFieldId;
    }

    public void setPrimaryFieldId(Long primaryFieldId) {
        this.primaryFieldId = primaryFieldId;
    }

    @Column(name = "MAIN_POOL",nullable = false)
    public Integer getMainPool() {
        return mainPool;
    }

    public void setMainPool(Integer mainPool) {
        this.mainPool = mainPool;
    }

    @Transient
    public String getPoolTitle() {
        return poolTitle;
    }

    public void setPoolTitle(String poolTitle) {
        this.poolTitle = poolTitle;
    }

    @Transient
    public String getpFieldTitle() {
        return pFieldTitle;
    }

    public void setpFieldTitle(String pFieldTitle) {
        this.pFieldTitle = pFieldTitle;
    }

    @Transient
    public Integer getLinkedPoolCount() {
        return linkedPoolCount;
    }

    public void setLinkedPoolCount(Integer linkedPoolCount) {
        this.linkedPoolCount = linkedPoolCount;
    }

    @Transient
    public String getProviderTitle() {
        return providerTitle;
    }

    public void setProviderTitle(String providerTitle) {
        this.providerTitle = providerTitle;
    }
}
