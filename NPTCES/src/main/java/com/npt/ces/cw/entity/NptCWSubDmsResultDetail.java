package com.npt.ces.cw.entity;

import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * author: owen
 * date:   2017/7/6 上午10:19
 * note:
 */
@Entity
@Table(name = "NPT_CW_SDMS_DETAIL")
public class NptCWSubDmsResultDetail extends NptEntitySerializable{

    public static final String PROPERTY_SUB_DIMENSION_RST_ID = "subDimensionRstId";

    //批次号
    private String batchNo;

    //子维度结果ID
    private Long subDimensionRstId;

    //信用实体ID
    private String creditEntityId;

    //信用实体标题
    private String creditEntityTitle;

    //信用实体类型
    private Integer creditEntityType;

    //实体数据元数据表ID
    private Long dataTypeId;

    //实体数据元数据表中相关数据的数据主键值
    private String uFieldValue;

    //事件发生时间
    private String affairWhen;

    //事件发生地点
    private String affairWhere;

    //事件描述
    private String affairWhat;

    //事件涉及的数字（比如金额）
    private String affairAmount;


    @Column(name = "BATCH_NO",nullable = false,length = 64)
    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    @Column(name = "SDMSR_ID",nullable = false)
    public Long getSubDimensionRstId() {
        return subDimensionRstId;
    }

    public void setSubDimensionRstId(Long subDimensionRstId) {
        this.subDimensionRstId = subDimensionRstId;
    }

    @Column(name = "CENTITY_ID",nullable = false,length = 128)
    public String getCreditEntityId() {
        return creditEntityId;
    }

    public void setCreditEntityId(String creditEntityId) {
        this.creditEntityId = creditEntityId;
    }

    @Column(name = "CENTITY_TITLE",nullable = false)
    public String getCreditEntityTitle() {
        return creditEntityTitle;
    }

    public void setCreditEntityTitle(String creditEntityTitle) {
        this.creditEntityTitle = creditEntityTitle;
    }

    @Column(name = "CENTITY_TYPE",nullable = false)
    public Integer getCreditEntityType() {
        return creditEntityType;
    }

    public void setCreditEntityType(Integer creditEntityType) {
        this.creditEntityType = creditEntityType;
    }

    @Column(name = "DATATYPE_ID")
    public Long getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(Long dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    @Column(name = "UFIELD_VALUE")
    public String getuFieldValue() {
        return uFieldValue;
    }

    public void setuFieldValue(String uFieldValue) {
        this.uFieldValue = uFieldValue;
    }

    @Column(name = "AFFAIR_WHEN")
    public String getAffairWhen() {
        return affairWhen;
    }

    public void setAffairWhen(String affairWhen) {
        this.affairWhen = affairWhen;
    }

    @Column(name = "AFFAIR_WHERE")
    public String getAffairWhere() {
        return affairWhere;
    }

    public void setAffairWhere(String affairWhere) {
        this.affairWhere = affairWhere;
    }

    @Column(name = "AFFAIR_WHAT")
    public String getAffairWhat() {
        return affairWhat;
    }

    public void setAffairWhat(String affairWhat) {
        this.affairWhat = affairWhat;
    }

    @Column(name = "AFFAIR_AMOUNT")
    public String getAffairAmount() {
        return affairAmount;
    }

    public void setAffairAmount(String affairAmount) {
        this.affairAmount = affairAmount;
    }
}
