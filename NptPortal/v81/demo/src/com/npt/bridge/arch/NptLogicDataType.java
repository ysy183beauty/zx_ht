package com.npt.bridge.arch;


import com.npt.bridge.base.NptAuthorityEntity;
import com.npt.bridge.base.NptBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "NPT_DATA_TYPE")
public class NptLogicDataType extends NptAuthorityEntity{

    public static final String PROPERTY_DATA_TYPE_DBNAME = "typeDbName";
    public static final String PROPERTY_UKFIELD_ID = "ukFieldId";

    private String typeName;
    private String typeDbName;
    private String keyword;
    private Integer typeCategory;
    private Long ukFieldId;
    private String ukFieldTitle;

    @Column(name = "DATA_TYPE_NAME",nullable = false,length = 128)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Column(name = "DATA_TYPE_DBNAME",nullable = false,length = 256)
    public String getTypeDbName() {
        return typeDbName;
    }

    public void setTypeDbName(String typeDbName) {
        this.typeDbName = typeDbName;
    }

    @Column(name = "DATA_TYPE_KEYWORD",length = 64)
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Column(name = "DATA_TYPE_CATEGORY")
    public Integer getTypeCategory() {
        return typeCategory;
    }

    public void setTypeCategory(Integer typeCategory) {
        this.typeCategory = typeCategory;
    }

    @Override
    public void copyTo(NptBaseEntity entity) {
        super.copyTo(entity);
        NptLogicDataType that = (NptLogicDataType) entity;
        that.setTypeName(this.getTypeName());
        that.setTypeCategory(this.getTypeCategory());
        that.setKeyword(this.getKeyword());
        that.setTypeDbName(this.getTypeDbName());
        that.setUkFieldId(this.getUkFieldId());
    }

    //数据主键
    @Column(name = "DATA_TYPE_UFIELD",nullable = false)
    public Long getUkFieldId() {
        return ukFieldId;
    }

    public void setUkFieldId(Long ukFieldId) {
        this.ukFieldId = ukFieldId;
    }

    @Transient
    public String getUkFieldTitle() {
        return ukFieldTitle;
    }

    public void setUkFieldTitle(String ukFieldTitle) {
        this.ukFieldTitle = ukFieldTitle;
    }
}
