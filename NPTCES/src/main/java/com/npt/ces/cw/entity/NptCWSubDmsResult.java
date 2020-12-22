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
@Table(name = "NPT_CW_SDMS_RESULT")
public class NptCWSubDmsResult extends NptEntitySerializable{

    public static final String PROPERTY_RESULT_ID = "resultId";
    public static final String PROPERTY_DIMENSION_RST_ID = "dimensionRstId";
    public static final String PROPERTY_CREDIT_ENTITY_TYPE = "creditEntityType";
    public static final String PROPERTY_RISK_SCORE = "riskScore";
    public static final String PROPERTY_BATCH_NO = "batchNo";

    //批次号
    private String batchNo;

    //结果ID
    private Long resultId;

    //维度ID
    private Long dimensionRstId;

    //数据来源单位ID
    private Long providerId;
    //数据来源单位名称
    private String providerTitle;

    //当前子维度的标题
    private String subDimensionTitle;

    //信用实体ID，比如企业的工商注册号统一信用代码，个人的身份证号码
    private String creditEntityId;

    //信用实体标题，比如企业名称或个人姓名
    private String creditEntityTitle;

    //信用实体类型
    private Integer creditEntityType;

    //实体数据元数据表ID
    private Long dataTypeId;

    //风险指数
    private Integer riskScore;

    //风险等级
    private String riskLevel;

    @Column(name = "BATCH_NO",nullable = false,length = 64)
    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    @Column(name = "RST_ID",nullable = false)
    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    @Column(name = "DMSR_ID",nullable = false)
    public Long getDimensionRstId() {
        return dimensionRstId;
    }

    public void setDimensionRstId(Long dimensionRstId) {
        this.dimensionRstId = dimensionRstId;
    }

    @Column(name = "SDMS_TITLE",nullable = false)
    public String getSubDimensionTitle() {
        return subDimensionTitle;
    }

    public void setSubDimensionTitle(String subDimensionTitle) {
        this.subDimensionTitle = subDimensionTitle;
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

    @Column(name = "RISK_SCORE",nullable = false)
    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    @Column(name = "RISK_LEVEL",nullable = false,length = 16)
    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    @Column(name = "CENTITY_ID",nullable = false,length = 128)
    public String getCreditEntityId() {
        return creditEntityId;
    }

    public void setCreditEntityId(String creditEntityId) {
        this.creditEntityId = creditEntityId;
    }

    @Column(name = "CENTITY_TITLE",nullable = false,length = 255)
    public String getCreditEntityTitle() {
        return creditEntityTitle;
    }

    public void setCreditEntityTitle(String creditEntityTitle) {
        this.creditEntityTitle = creditEntityTitle;
    }

    @Column(name = "PROVIDER_ID")
    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    @Column(name = "PROVIDER_TITLE",length = 128)
    public String getProviderTitle() {
        return providerTitle;
    }

    public void setProviderTitle(String providerTitle) {
        this.providerTitle = providerTitle;
    }
}
