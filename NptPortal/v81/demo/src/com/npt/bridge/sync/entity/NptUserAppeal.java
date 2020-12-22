package com.npt.bridge.sync.entity;

import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


/**
 * 项目: NPTGRS
 * 作者: 张磊
 * 日期: 16/11/2 下午4:10
 * 备注:
 */
@Entity
@Table(name = "NPT_APPEAL_INFO")
public class NptUserAppeal extends NptEntitySerializable {

    public static final String PROPERTY_APPEAL_NO = "appealNo";
    public static final String PROPRERTY_APPEAL_DT_ID = "appealDTID";
    public static final String PROPRTTY_APPEAL_PK_ID = "appealPKID";
    public static final String PROPERTY_APPEAL_PK_TITLE = "appealPKTitle";
    public static final String PROPERTY_APPEAL_STATUS = "appealStatus";
    public static final String PROPERTY_APPEAL_PROVIDER = "appealProviderId";
    public static final String PROPERTY_APPEAL_DT_TITLE = "appealDTTitle";
    public static final String PROPERTY_APPEAL_BUSINESS_KEY = "appealBusinessKey";

    private String appealNo;
    private Long appealDTID;
    private Long appealPKID;
    private String appealRemark;
    /**
     * @see com.npt.bridge.dict.NptDict#RAS_WAITTING
     */
    private Integer appealStatus;
    private Long appealProviderId;
    private String appealProviderTitle;
    private String appealBusinessKey;
    private String appealDTTitle;
    private String appealUser;
    private String appealUserTel;
    private String appealUserEmail;
    private String attachmentDir;
    private String expiredTitle;
    private String appealStatusTitle;
    private Date frozenStartDate;
    private Date frozenEndDate;
    private Long stepUserId;
    private String appealResult;
    /**
     * @see com.npt.bridge.dict.NptDict#SOURCE_NPT
     */
    private Integer source;
    private String attach;//附件
    private String desc;//说明

    @Column(name = "APPEAL_NO",nullable = false,length = 64)
    public String getAppealNo() {
        return appealNo;
    }

    public void setAppealNo(String appealNo) {
        this.appealNo = appealNo;
    }

    @Column(name = "APPEAL_DTID",nullable = false)
    public Long getAppealDTID() {
        return appealDTID;
    }

    public void setAppealDTID(Long appealDTID) {
        this.appealDTID = appealDTID;
    }

    @Column(name = "APPEAL_PKID",nullable = false)
    public Long getAppealPKID() {
        return appealPKID;
    }

    public void setAppealPKID(Long appealPKID) {
        this.appealPKID = appealPKID;
    }

    @Column(name = "APPEAL_USER")
    public String getAppealUser() {
        return appealUser;
    }

    public void setAppealUser(String appealUser) {
        this.appealUser = appealUser;
    }

    @Column(name = "APPEAL_STATUS")
    public Integer getAppealStatus(){ return appealStatus; }

    public void setAppealStatus(int appealStatus){ this.appealStatus = appealStatus; }

    @Column(name = "APPEAL_PROVIDER_ID")
    public Long getAppealProviderId(){ return appealProviderId; }

    public void setAppealProviderId(Long appealProviderId){ this.appealProviderId = appealProviderId; }

    @Column(name = "APPEAL_PROVIDER_TITLE")
    public String getAppealProviderTitle(){ return appealProviderTitle;}

    public void setAppealProviderTitle(String appealProviderTitle){ this.appealProviderTitle = appealProviderTitle; }

    @Column(name = "APPEAL_DT_TITLE")
    public String getAppealDTTitle(){ return  appealDTTitle; }

    public void setAppealDTTitle(String appealDTTitle){ this.appealDTTitle = appealDTTitle; }

    @Column(name = "APPEAL_BUSINESS_KEY")
    public String getAppealBusinessKey(){ return  appealBusinessKey; }

    public void setAppealBusinessKey(String appealBusinessKey){ this.appealBusinessKey = appealBusinessKey; }

    @Column(name = "APPEAL_FROZEN_START_DATE")
    public Date getFrozenStartDate(){ return frozenStartDate; }

    public void setFrozenStartDate(Date frozenStartDate){ this.frozenStartDate = frozenStartDate; }

    @Column(name = "APPEAL_FROZEN_END_DATE")
    public Date getFrozenEndDate(){ return frozenEndDate; }

    public void setFrozenEndDate(Date frozenEndDate){ this.frozenEndDate = frozenEndDate; }

    @Column(name = "STEP_USER_ID")
    public Long getStepUserId() {
        return stepUserId;
    }

    public void setStepUserId(Long stepUserId) {
        this.stepUserId = stepUserId;
    }

    @Column(name = "APPEAL_USER_TEL")
    public String getAppealUserTel() {
        return appealUserTel;
    }

    public void setAppealUserTel(String appealUserTel) {
        this.appealUserTel = appealUserTel;
    }

    @Column(name = "APPEAL_USER_EMAIL")
    public String getAppealUserEmail() {
        return appealUserEmail;
    }

    public void setAppealUserEmail(String appealUserEmail) {
        this.appealUserEmail = appealUserEmail;
    }

    @Column(name = "ATTACHMENT_DIR",length = 1024)
    public String getAttachmentDir() {
        return attachmentDir;
    }

    public void setAttachmentDir(String attachmentDir) {
        this.attachmentDir = attachmentDir;
    }

    @Column(name = "APPEAL_RESULT",length = 1024)
    public String getAppealResult() {
        return appealResult;
    }

    public void setAppealResult(String appealResult) { this.appealResult = appealResult;}

    @Column(name = "APPEAL_REMARK",length = 1024)
    public String getAppealRemark() {
        return appealRemark;
    }

    public void setAppealRemark(String appealRemark) {
        this.appealRemark = appealRemark;
    }

    @Column(name = "APPEAL_SOURCE", nullable = false)
    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    @Column(name = "APPEAL_ATTACH")
    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    @Column(name = "APPEAL_DESC")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Transient
    public String getExpiredTitle(){ return  expiredTitle; }

    public void setExpiredTitle(String expiredTitle){ this.expiredTitle = expiredTitle;}

    @Transient
    public String getAppealStatusTitle(){ return appealStatusTitle; }

    public void setAppealStatusTitle(String appealStatusTitle){ this.appealStatusTitle = appealStatusTitle; }

}
