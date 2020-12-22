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
 * 日期：2016/10/20 11:10
 * 备注：
 *      模型子组
 */
@Entity
@Table(name = "NPT_BASEMODEL_GROUP")
public class NptBaseModelGroup extends NptEntitySerializable{

    public static final String PROPERTY_MODEL_ID = "modelId";
    public static final String PROPERTY_GROUP_NAME = "groupName";
    public static final String PROPERTY_GROUP_TITLE = "groupTitle";
    public static final String PROPERTY_GROUP_CODE = "specialCode";

    private Long modelId;
    private String groupName;
    private Integer specialCode;

    @Override
    public void copyTo(NptBaseEntity entity) {
        super.copyTo(entity);
        NptBaseModelGroup that = (NptBaseModelGroup) entity;
        that.setModelId(this.getModelId());
        that.setGroupName(this.getGroupName());
        that.setSpecialCode(this.getSpecialCode());
        that.setGroupTitle(this.getGroupTitle());
        that.setMainGroup(this.getMainGroup());
        that.setGroupNote(this.getGroupNote());
    }

    private String groupTitle;
    private Integer mainGroup;
    private String groupNote;

    @Column(name = "MODEL_ID",nullable = false)
    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    @Column(name = "GROUP_NAME",nullable = false,length = 128)
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Column(name = "GROUP_TITLE",nullable = false,length = 128)
    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    @Transient
    public Integer getMainGroup() {
        return mainGroup;
    }

    public void setMainGroup(Integer mainGroup) {
        this.mainGroup = mainGroup;
    }

    @Column(name = "GROUP_NOTE",length = 256)
    public String getGroupNote() {
        return groupNote;
    }

    public void setGroupNote(String groupNote) {
        this.groupNote = groupNote;
    }

    @Column(name = "SPECIAL_CODE")
    public Integer getSpecialCode() {
        return specialCode;
    }

    public void setSpecialCode(Integer specialCode) {
        this.specialCode = specialCode;
    }
}
