package com.npt.ces.cw.entity;

import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * author: owen
 * date:   2017/7/6 上午10:21
 * note:
 */
@Entity
@Table(name = "NPT_CW_PRD_CONCERN")
public class NptCWProviderConcern extends NptEntitySerializable{

    public static final String PROPERTY_PROVIDER_ID = "providerId";
    public static final String PROPERTY_CREDIT_ENTITY_ID = "creditEntityId";
    public static final String PROPERTY_CREDIT_ENTITY_TYPE = "creditEntityType";


    //订阅单位ID
    private Long providerId;

    //信用实体ID
    private String creditEntityId;

    //信用实体类型
    private Integer creditEntityType;

    @Column(name = "PRD_ID",nullable = false)
    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    @Column(name = "CENTITY_ID",nullable = false,length = 128)
    public String getCreditEntityId() {
        return creditEntityId;
    }

    public void setCreditEntityId(String creditEntityId) {
        this.creditEntityId = creditEntityId;
    }

    @Column(name = "CENTITY_TYPE",nullable = false)
    public Integer getCreditEntityType() {
        return creditEntityType;
    }

    public void setCreditEntityType(Integer creditEntityType) {
        this.creditEntityType = creditEntityType;
    }
}
