package com.npt.bridge.sync.entity;

import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 项目：NPTWebApp
 * 作者：97175
 * 日期：2016/11/7 11:58
 * 备注：
 */
@Entity
@Table(name = "NPT_APPEAL_DETAIL")
public class NptUserAppealDetail extends NptEntitySerializable{

    private String appealNo;

    private Long fieldId;

    private String appealValue;

    private String defaultValue;

    private String detailResult;

    @Column(name = "APPEAL_NO",nullable = false,length = 64)
    public String getAppealNo() {
        return appealNo;
    }

    public void setAppealNo(String appealNo) {
        this.appealNo = appealNo;
    }

    @Column(name = "FIELD_ID",nullable = false)
    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    @Column(name = "APPEAL_VALUE",nullable = false,length = 1024)
    public String getAppealValue() {
        return appealValue;
    }

    public void setAppealValue(String appealValue) {
        this.appealValue = appealValue;
    }

    @Column(name = "DEFAULT_VALUE",nullable = false,length = 1024)
    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Column(name = "DETAIL_RESULT",length = 1024)
    public String getDetailResult() {
        return detailResult;
    }

    public void setDetailResult(String detailResult) {
        this.detailResult = detailResult;
    }
}
