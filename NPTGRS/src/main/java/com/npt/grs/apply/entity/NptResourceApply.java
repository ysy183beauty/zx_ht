package com.npt.grs.apply.entity;

import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/10 15:16
 * 备注：
 */
@Entity
@Table(name = "NPT_RESOURCE_APPLY")
public class NptResourceApply extends NptEntitySerializable{

    public static final String PROPERTY_APPLY_NO = "applyNo";
    public static final String PROPERTY_APPLY_USER_ID = "applyUserId";
    public static final String PROPERTY_APPLY_USER_ORG = "applyUserOrg";
    public static final String PROPERTY_APPLY_BUSINESS_KEY = "applyBusinessKey";
    public static final String PROPERTY_APPLY_POOL_ID = "applyGrouPoolId";
    public static final String PROPERTY_APPLY_POOL_TITLE = "applyGrouPoolTitle";
    public static final String PROPERTY_APPLY_STATUS= "applyStatus";
    public static final String PROPERTY_APPLY_PROVIDER = "applyProviderId";

    private String applyNo;

    private Long applyUserId;

    private String applyUserName;

    private String applyUserTel;

    private Long applyUserOrg;

    private String applyUserOrgTitle;

    private String applyReason;

    private Long applyProviderId;

    private String applyProviderTitle;

    private Long applyGrouPoolId;

    private String applyGrouPoolTitle;

    private String applyBusinessKey;

    private Integer applyType;

    private Date applyedStartDate;

    private Date applyedEndDate;

    private Date confirmedStartDate;

    private Date confirmedEndDate;

    private Long stepUserId;

    private Integer applyStatus;

    private String applyStatusTitle;

    private String expiredTitle;

    //申请批次号
    @Column(name = "APPLY_NO",nullable = false,length = 64)
    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    //申请人
    @Column(name = "APPLY_USER_ID",nullable = false)
    public Long getApplyUserId() {
        return applyUserId;
    }


    public void setApplyUserId(Long applyUserId) {
        this.applyUserId = applyUserId;
    }

    @Column(name = "APPLY_GROUPOOL_ID",nullable = false)
    public Long getApplyGrouPoolId() {
        return applyGrouPoolId;
    }

    public void setApplyGrouPoolId(Long applyGrouPoolId) {
        this.applyGrouPoolId = applyGrouPoolId;
    }

    //申请的业务数据ID
    @Column(name = "APPLY_BUSINESS_KEY",length = 256)
    public String getApplyBusinessKey() {
        return applyBusinessKey;
    }

    public void setApplyBusinessKey(String applyBusinessKey) {
        this.applyBusinessKey = applyBusinessKey;
    }

    //申请类型,0表示全量申请，1表示业务数据条级申请
    @Column(name = "APPLY_TYPE",nullable = false)
    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    //申请查看的起始时间
    @Column(name = "APPLY_START_TIME",nullable = false)
    public Date getApplyedStartDate() {
        return applyedStartDate;
    }

    public void setApplyedStartDate(Date applyedStartDate) {
        this.applyedStartDate = applyedStartDate;
    }

    //申请查看的结束时间
    @Column(name = "APPLY_END_TIME",nullable = false)
    public Date getApplyedEndDate() {
        return applyedEndDate;
    }

    public void setApplyedEndDate(Date applyedEndDate) {
        this.applyedEndDate = applyedEndDate;
    }

    //确认人确定的查看起始时间
    @Column(name = "CONFIRMED_START_TIME")
    public Date getConfirmedStartDate() {
        return confirmedStartDate;
    }

    public void setConfirmedStartDate(Date confirmedStartDate) {
        this.confirmedStartDate = confirmedStartDate;
    }

    //确认人确定的查看结束时间
    @Column(name = "CONFIRMED_END_TIME")
    public Date getConfirmedEndDate() {
        return confirmedEndDate;
    }

    public void setConfirmedEndDate(Date confirmedEndDate) {
        this.confirmedEndDate = confirmedEndDate;
    }

    //阶段处理者ID
    @Column(name = "STEP_USER_ID",nullable = false)
    public Long getStepUserId() {
        return stepUserId;
    }

    public void setStepUserId(Long stepUserId) {
        this.stepUserId = stepUserId;
    }

    //阶段处理状态
    @Column(name = "APPLY_STATUS",nullable = false)
    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    //申请人姓名
    @Column(name = "APPLY_USER_NAME",length = 128)
    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    //申请人电话
    @Column(name = "APPLY_USR_TEL",length = 32)
    public String getApplyUserTel() {
        return applyUserTel;
    }

    public void setApplyUserTel(String applyUserTel) {
        this.applyUserTel = applyUserTel;
    }

    //申请人所属单位
    @Column(name = "APPLY_USER_ORG",length = 256)
    public Long getApplyUserOrg() {
        return applyUserOrg;
    }

    public void setApplyUserOrg(Long applyUserOrg) {
        this.applyUserOrg = applyUserOrg;
    }

    //申请理由
    @Column(name = "APPLY_REASON",length = 512)
    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    //资源提供者ID
    @Column(name = "APPLY_PROVIDER_ID",nullable = false)
    public Long getApplyProviderId() {
        return applyProviderId;
    }

    public void setApplyProviderId(Long applyProviderId) {
        this.applyProviderId = applyProviderId;
    }

    @Column(name = "APPLY_PROVIDER_TITLE",nullable = false,length = 128)
    public String getApplyProviderTitle() {
        return applyProviderTitle;
    }

    public void setApplyProviderTitle(String applyProviderTitle) {
        this.applyProviderTitle = applyProviderTitle;
    }

    @Column(name = "APPLY_GROUPOOL_TITLE",nullable = false,length = 128)
    public String getApplyGrouPoolTitle() {
        return applyGrouPoolTitle;
    }

    public void setApplyGrouPoolTitle(String applyGrouPoolTitle) {
        this.applyGrouPoolTitle = applyGrouPoolTitle;
    }

    @Transient
    public String getApplyStatusTitle() {
        return applyStatusTitle;
    }

    public void setApplyStatusTitle(String applyStatusTitle) {
        this.applyStatusTitle = applyStatusTitle;
    }

    @Transient
    public String getExpiredTitle() {
        return expiredTitle;
    }

    public void setExpiredTitle(String expiredTitle) {
        this.expiredTitle = expiredTitle;
    }

    @Transient
    public String getApplyUserOrgTitle() {
        return applyUserOrgTitle;
    }

    public void setApplyUserOrgTitle(String applyUserOrgTitle) {
        this.applyUserOrgTitle = applyUserOrgTitle;
    }
}
