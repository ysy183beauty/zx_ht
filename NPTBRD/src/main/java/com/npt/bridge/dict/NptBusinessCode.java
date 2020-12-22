package com.npt.bridge.dict;

import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/13 13:56
 * 备注：
 */
@Entity
@Table(name = "NPT_BUSINESS_CODE")
public class NptBusinessCode extends NptEntitySerializable {

    public static final String PROPERTY_CODE_VALUE = "codeValue";
    public static final String PROPERTY_CODE_FIELDID = "fieldId";

    /**
     * 码名称，用于转换之后的显示
     */
    private String codeName;
    /**
     * 码标题，添加码表时指定的意会名称
     */
    private String codeTitle;
    /**
     * 码值
     */
    private String codeValue;
    /**
     * 码类别，由NptDataField的ID指定
     */
    private Long fieldId;

    @Column(name = "CODE_NAME",nullable = false,length = 128)
    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    @Column(name = "CODE_TITLE",nullable = false,length = 128)
    public String getCodeTitle() {
        return codeTitle;
    }

    public void setCodeTitle(String codeTitle) {
        this.codeTitle = codeTitle;
    }

    @Column(name = "CODE_VALUE",nullable = false)
    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    @Column(name = "CODE_FIELD_ID",nullable = false)
    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    @Override
    public void copyTo(NptBaseEntity entity) {

        if(null != entity) {
            NptBusinessCode that = (NptBusinessCode) entity;
            that.setCodeName(this.getCodeName());
            that.setCodeTitle(this.getCodeTitle());
            that.setCodeValue(this.getCodeValue());
            that.setFieldId(this.getFieldId());

            super.copyTo(entity);
        }
    }
}
