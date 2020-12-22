package com.npt.bridge.arch;

import com.npt.bridge.base.NptAuthorityEntity;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "NPT_DATA_FIELD")
public class NptLogicDataField extends NptAuthorityEntity {

    private String fieldName;
    private String fieldDbName;
    private String fieldDbType;
    private Integer fieldDbLen;
    private Integer likeQuery;
    private Object fieldValue;

    @Basic
    @Column(name = "FIELD_NAME", nullable = false, length = 128)
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Basic
    @Column(name = "FIELD_DB_NAME", nullable = false, length = 64)
    public String getFieldDbName() {
        return fieldDbName;
    }

    public void setFieldDbName(String fieldDbName) {
        this.fieldDbName = fieldDbName;
    }


    @Transient
    public Integer getLikeQuery() {
        return likeQuery;
    }

    public void setLikeQuery(Integer likeQuery) {
        this.likeQuery = likeQuery;
    }

    @Transient
    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }


    @Column(name = "FIELD_DBTYPE",nullable = false,length = 128)
    public String getFieldDbType() {
        return fieldDbType;
    }

    public void setFieldDbType(String fieldDbType) {
        this.fieldDbType = fieldDbType;
    }

    @Column(name = "FIELD_DBLEN",nullable = false)
    public Integer getFieldDbLen() {
        return fieldDbLen;
    }

    public void setFieldDbLen(Integer fieldDbLen) {
        this.fieldDbLen = fieldDbLen;
    }
}
