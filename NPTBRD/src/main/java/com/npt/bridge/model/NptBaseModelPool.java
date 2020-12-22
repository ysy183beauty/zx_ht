package com.npt.bridge.model;

import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/10 20:05
 * 备注：
 */
@Entity
@Table(name = "NPT_BASEMODEL_GROUPOOL")
public class NptBaseModelPool extends NptEntitySerializable{
    @Override
    public void copyTo(NptBaseEntity entity) {
        super.copyTo(entity);
        NptBaseModelPool that = (NptBaseModelPool) entity;
        that.setpFieldTitle(this.getpFieldTitle());
        that.setProviderTitle(this.getProviderTitle());
        that.setProviderId(this.getProviderId());
        that.setPoolTitle(this.getPoolTitle());
        that.setModelId(this.getModelId());
        that.setGroupId(this.getGroupId());
        that.setDataTypeId(this.getDataTypeId());
        that.setPrimaryFieldId(this.getPrimaryFieldId());
        that.setMainPool(this.getMainPool());
        that.setLinkedPoolCount(this.getLinkedPoolCount());
        that.setLockLevel(this.getLockLevel());
        that.setTitleFieldId(this.getTitleFieldId());
        that.setOrderFieldId(this.getOrderFieldId());
        that.setLinkTitle(this.getLinkTitle());
        that.setPoolNote(this.getPoolNote());
        that.setScoreWeight(this.getScoreWeight());
        that.setDataHost(this.getDataHost());
    }

    public static final String PROPERTY_MODEL_ID = "modelId";
    public static final String PROPERTY_GROUP_ID = "groupId";
    public static final String PROPERTY_POOL_WEIGHT = "mainPool";
    public static final String PROPERTY_LOCK_LEVEL = "lockLevel";
    public static final String PROPERTY_SCORE_WEIGHT = "scoreWeight";
    public static final String PROPERTY_DATA_HOST = "dataHost";

    private String pFieldTitle;
    private String providerTitle;
    private Long providerId;
    private String poolTitle;
    private Long modelId;
    private Long groupId;
    private Long dataTypeId;
    private Long primaryFieldId;
    private Integer mainPool;
    private Integer linkedPoolCount;
    private Integer lockLevel;
    private Long titleFieldId;
    private Long orderFieldId;
    private String linkTitle;
    private String poolNote;
    private Integer scoreWeight;
    private Integer dataHost;

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

    @Column(name = "LOCK_LEVEL",nullable = false)
    public Integer getLockLevel() {
        return lockLevel;
    }

    public void setLockLevel(Integer lockLevel) {
        this.lockLevel = lockLevel;
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

    @Transient
    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    @Column(name = "TITLE_FIELD", nullable = false)
    public Long getTitleFieldId() {
        return titleFieldId;
    }

    public void setTitleFieldId(Long titleFieldId) {
        this.titleFieldId = titleFieldId;
    }

    @Column(name = "ORDER_FIELD")
    public Long getOrderFieldId() {
        return orderFieldId;
    }

    public void setOrderFieldId(Long orderFieldId) {
        this.orderFieldId = orderFieldId;
    }

    @Transient
    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    @Column(name = "POOL_NOTE")
    public String getPoolNote() {
        return poolNote;
    }

    public void setPoolNote(String poolNote) {
        this.poolNote = poolNote;
    }

    @Column(name = "POOL_WEIGHT", nullable = false)
    public Integer getScoreWeight() {
        return scoreWeight;
    }

    public void setScoreWeight(Integer scoreWeight) {
        this.scoreWeight = scoreWeight;
    }

    @Column(name = "DATA_HOST",nullable = false)
    public Integer getDataHost() {
        return dataHost;
    }

    public void setDataHost(Integer dataHost) {
        this.dataHost = dataHost;
    }
}
