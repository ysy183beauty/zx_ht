package com.npt.ces.cw.entity;

import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * author: owen
 * date:   2017/7/6 上午10:18
 * note:
 */
@Entity
@Table(name = "NPT_CW_DMS_RESULT")
public class NptCWDmsResult extends NptEntitySerializable{

    public static final String PROPERTY_RESULT_ID = "resultId";
    public static final String PROPERTY_BASE_MODEL_GROUP_ID = "baseModelGroupId";
    public static final String PROPERTY_DIMENSION_NAME = "dimensionName";
    public static final String PROPERTY_CREDIT_ENTITY_ID = "creditEntityId";
    public static final String PROPERTY_CREDIT_ENTITY_TITLE = "creditEntityTitle";
    public static final String PROPERTY_CREDIT_ENTITY_TYPE = "creditEntityType";
    public static final String PROPERTY_RISK_SCORE = "riskScore";
    public static final String PROPERTY_BATCH_NO = "batchNo";

    //批次号
    private String batchNo;

    //预警结果ID
    private Long resultId;

    //基础模型分组ID
    private Long baseModelGroupId;

    //预警维度名称
    private String dimensionName;

    //信用实体ID，比如企业的工商注册号统一信用代码，个人的身份证号码
    private String creditEntityId;

    //信用实体标题，比如企业名称或个人姓名
    private String creditEntityTitle;

    //信用实体类型
    private Integer creditEntityType;

    //风险指数
    private Integer riskScore;

    //风险等级
    private String riskLevel;

    //维度权重
    private Integer dmsWeight;

    //维度备注
    private String dmsNote;

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

    @Column(name = "GROUP_ID")
    public Long getBaseModelGroupId() {
        return baseModelGroupId;
    }

    public void setBaseModelGroupId(Long baseModelGroupId) {
        this.baseModelGroupId = baseModelGroupId;
    }

    @Column(name = "DMS_TITLE",nullable = false,length = 255)
    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    @Column(name = "CENTITY_TYPE",nullable = false)
    public Integer getCreditEntityType() {
        return creditEntityType;
    }

    public void setCreditEntityType(Integer creditEntityType) {
        this.creditEntityType = creditEntityType;
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

    @Transient
    public String getDmsNote() {
        return dmsNote;
    }

    public void setDmsNote(String dmsNote) {
        this.dmsNote = dmsNote;
    }

    @Transient
    public Integer getDmsWeight() {
        return dmsWeight;
    }

    public void setDmsWeight(Integer dmsWeight) {
        this.dmsWeight = dmsWeight;
    }
}
