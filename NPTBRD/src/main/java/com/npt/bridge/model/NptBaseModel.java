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
 * 日期：2016/10/10 19:55
 * 备注：
 *      基础模型信息
 */
@Entity
@Table(name = "NPT_BASE_MODEL")
public class NptBaseModel extends NptEntitySerializable{

    public static final String PROPERTY_HOST_ID = "hostId";
    public static final String PROPERTY_MODEL_NAME = "modelName";
    public static final String PROPERTY_MODEL_TITLE = "modelNote";
    public static final String PROPERTY_CATE_ID = "cateId";

    private String modelName;
    private String modelNote;
    private Integer hostId;
    private Integer cateId;
    private String hostTitle;
    private String cateTitle;

    @Column(name = "MODEL_NAME",nullable = false,length = 64)
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Column(name = "MODEL_NOTE",nullable = false,length = 256)
    public String getModelNote() {
        return modelNote;
    }

    public void setModelNote(String modelNote) {
        this.modelNote = modelNote;
    }

    @Column(name = "HOST_ID",nullable = false)
    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

    @Column(name = "CATE_ID",nullable = false)
    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    @Transient
    public String getHostTitle() {
        return hostTitle;
    }

    public void setHostTitle(String hostTitle) {
        this.hostTitle = hostTitle;
    }

    @Transient
    public String getCateTitle() {  return cateTitle; }

    public void setCateTitle(String cateTitle) { this.cateTitle = cateTitle; }
    @Override
    public void copyTo(NptBaseEntity entity) {
        super.copyTo(entity);
        NptBaseModel that = (NptBaseModel) entity;
        that.setModelName(this.getModelName());
        that.setModelNote(this.getModelNote());
        that.setHostId(this.getHostId());
        that.setCateId(this.getCateId());
        that.setHostTitle(this.getHostTitle());
        that.setCateTitle(this.getHostTitle());
    }
}
