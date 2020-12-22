package com.npt.bridge.model;

import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 作者: owen
 * 时间: 2017/3/14 下午9:13
 * 描述:
 */
@Entity
@Table(name = "NPT_BM_POOL_INDEX")
public class NptBaseModelPoolIndex extends NptEntitySerializable{

    public static final String PROPERTY_POOL_ID = "poolId";

    private Long poolId;
    private Long fieldId;

    @Column(name = "POOL_ID",nullable = false)
    public Long getPoolId() {
        return poolId;
    }

    public void setPoolId(Long poolId) {
        this.poolId = poolId;
    }

    @Column(name = "field_id",nullable = false)
    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }
}
