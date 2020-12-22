package com.npt.model.entity;


import com.npt.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/20 19:43
 * 备注：
 */
@Entity
@Table(name = "NPT_BM_POOL_POOL")
public class NptBaseModelPoolLink extends NptEntitySerializable {

    public static final String PROPERTY_FROM_POOL_ID = "fromPoolId";
    public static final String PROPERTY_TO_POOL_ID = "toPoolId";
    public static final String PROPERTY_FROM_POOL_REFKEYID = "poolRefKeyId";


    private Long fromPoolId;
    private Long poolRefKeyId;
    private Long toPoolId;
    private String poolRefKeyTitle;
    private String toPoolTitle;
    private String toPoolProviderTitle;


    @Column(name = "FROM_POOL_ID",nullable = false)
    public Long getFromPoolId() {
        return fromPoolId;
    }

    public void setFromPoolId(Long fromPoolId) {
        this.fromPoolId = fromPoolId;
    }


    @Column(name = "REF_FIELD_ID",nullable = false)
    public Long getPoolRefKeyId() {
        return poolRefKeyId;
    }

    public void setPoolRefKeyId(Long poolRefKeyId) {
        this.poolRefKeyId = poolRefKeyId;
    }


    @Column(name = "TO_POOL_ID",nullable = false)
    public Long getToPoolId() {
        return toPoolId;
    }

    public void setToPoolId(Long toPoolId) {
        this.toPoolId = toPoolId;
    }

    @Transient
    public String getPoolRefKeyTitle() {
        return poolRefKeyTitle;
    }

    public void setPoolRefKeyTitle(String poolRefKeyTitle) {
        this.poolRefKeyTitle = poolRefKeyTitle;
    }

    @Transient
    public String getToPoolTitle() {
        return toPoolTitle;
    }

    public void setToPoolTitle(String toPoolTitle) {
        this.toPoolTitle = toPoolTitle;
    }

    @Transient
    public String getToPoolProviderTitle() {
        return toPoolProviderTitle;
    }

    public void setToPoolProviderTitle(String toPoolProviderTitle) {
        this.toPoolProviderTitle = toPoolProviderTitle;
    }
}
