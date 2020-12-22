package com.npt.model.entity;


import com.npt.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/11 15:34
 * 备注：
 */
@Entity
@Table(name = "NPT_BMGROUP_MFIELDS")
public class NptBaseModelMainField extends NptEntitySerializable {


    public static final String PROPERTY_POOL_ID = "poolId";

    private Long poolId;
    private Long groupId;
    private Long fieldId;
    private Integer searchCondition;

    @Column(name = "GROUP_ID",nullable = false)
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    //字段ID
    @Column(name = "FIELD_ID",nullable = false)
    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    @Column(name = "POOL_ID",nullable = false)
    public Long getPoolId() {
        return poolId;
    }

    public void setPoolId(Long poolId) {
        this.poolId = poolId;
    }

    @Column(name = "SEARCH_CONDITION")
    public Integer getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(Integer searchCondition) {
        this.searchCondition = searchCondition;
    }
}
