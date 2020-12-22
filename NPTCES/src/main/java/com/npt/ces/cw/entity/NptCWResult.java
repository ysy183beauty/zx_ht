package com.npt.ces.cw.entity;

import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * author: owen
 * date:   2017/7/6 上午10:17
 * note:
 */
@Entity
@Table(name = "NPT_CW_RESULT")
public class NptCWResult extends NptEntitySerializable{

    public static final String PROPERTY_CREDIT_ENTITY_ID = "creditEntityId";
    public static final String PROPERTY_CREDIT_ENTITY_TITLE = "creditEntityTitle";
    public static final String PROPERTY_CREDIT_ENTITY_TYPE = "creditEntityType";
    public static final String PROPERTY_RISK_SCORE = "riskScore";
    public static final String PROPERTY_BATCH_NO = "batchNo";


    //预警结果批次号
    private String batchNo;

    //信用实体ID，比如企业的工商注册号统一信用代码，个人的身份证号码
    private String creditEntityId;

    //信用实体标题，比如企业名称或个人姓名
    private String creditEntityTitle;

    //信用实体类别，比如是企业实体还是个人实体还是其它
    private Integer creditEntityType;

    //用于预警评估的基础模型ID
    private Long baseModelId;

    //用于预警评估的基础模型的主数据池ID
    private Long modelMainPoolId;

    //当前信用实体在预警评估模型中主数据池中的数据主键值，以便精确定位信用实体的相关信息
    private String creditEntityUFieldValue;

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

    @Column(name = "CENTITY_TYPE",nullable = false)
    public Integer getCreditEntityType() {
        return creditEntityType;
    }

    public void setCreditEntityType(Integer creditEntityType) {
        this.creditEntityType = creditEntityType;
    }

    @Column(name = "MODEL_ID")
    public Long getBaseModelId() {
        return baseModelId;
    }

    public void setBaseModelId(Long baseModelId) {
        this.baseModelId = baseModelId;
    }

    @Column(name = "MPOOL_ID")
    public Long getModelMainPoolId() {
        return modelMainPoolId;
    }

    public void setModelMainPoolId(Long modelMainPoolId) {
        this.modelMainPoolId = modelMainPoolId;
    }

    @Column(name = "CENTITY_UFIELD_VALUE",length = 64)
    public String getCreditEntityUFieldValue() {
        return creditEntityUFieldValue;
    }

    public void setCreditEntityUFieldValue(String creditEntityUFieldValue) {
        this.creditEntityUFieldValue = creditEntityUFieldValue;
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
}
