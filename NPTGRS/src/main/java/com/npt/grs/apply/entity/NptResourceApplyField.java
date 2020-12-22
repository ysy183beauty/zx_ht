package com.npt.grs.apply.entity;

import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/10 15:36
 * 备注：
 */
@Entity
@Table(name = "NPT_RESOURCE_APPLY_FIELDS")
public class NptResourceApplyField extends NptEntitySerializable{

    private String applyNo;
    private Long fieldId;


    //申请批次号
    @Column(name = "APPLY_NO",nullable = false,length = 64)
    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    //申请的字段ID
    @Column(name = "FIELD_ID",nullable = false)
    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

}
