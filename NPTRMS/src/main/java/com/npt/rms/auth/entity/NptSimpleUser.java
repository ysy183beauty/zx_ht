package com.npt.rms.auth.entity;

import com.npt.bridge.util.NptCommonUtil;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/11 15:34
 * 描述:
 */
@Entity
@Table(name = "SEC_USER")
public class NptSimpleUser implements Serializable{
    public static final String PROPERTY_USER_LOGIN_NAME = "loginName";
    public static final String PROPERTY_USER_ORG_ID = "orgId";

    private Long userId;
    private String loginName;
    private String password;
    private String userName;
    private String sex;
    private String mobile;
    private String email;
    private boolean enable;
    private String orgId;
    private String remark;
    private Long modifyDate;
    private String modifyUser;
    private String wechatNo;


    @Id
    @Column(name = "ID_",nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "USERNAME_")
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Column(name = "PASSWORD_")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "NAME_")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "MOBILE_")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "EMAIL_")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "ENABLE_")
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Column(name = "FIELD01_")
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Transient
    public Long getUserOrgId(){
        try {
            return Long.parseLong(this.getOrgId());
        }catch (Exception e){
            return NptCommonUtil.getDefaultParentId();
        }
    }

    public void setUserOrgId(Long id){
        this.setOrgId(String.valueOf(id));
    }

    @Column(name = "DESCRIPTION_")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "MODIFY_STAMP_")
    public Long getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Long modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Column(name = "MODIFIER_")
    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Column(name = "SEX_")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "FIELD02_")
    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }
}
