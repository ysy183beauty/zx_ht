package com.npt.bridge.sync.entity;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Map;

/**
 * 作者: 张磊
 * 日期: 2017/03/22 下午11:38
 * 备注: CMS用户
 */
@Entity
@Table(name = "NPT_CREDIT_USER")
public class CreditCmsUser extends NptSyncBase<Integer> {

    public static final String PROPERTY_FLAG = "flag";
    public static final String PROPERTY_TYPE = "type";
    public static final String TYPE_COMPANY = "company";
    public static final String TYPE_PERSON = "person";

    private String realname;//姓名
    private String mobile;//电话
    private String idCard;//身份证号
    private String type;//类型（个人/企业）

    @JSONField(serialize = false)
    private String flag;//认证状态
    @JSONField(serialize = false)
    private String idCardImg;//身份证照片名称，按","分隔
    @JSONField(serialize = false)
    private String failComment;//认证失败原因

    private Map<String, String> mapFile;//需要Base64编码

    public CreditCmsUser() {
        super();
    }

    @Override
    public <T extends NptSyncBase> void update(T entity) {
        CreditCmsUser that = (CreditCmsUser) entity;
        this.realname = that.getRealname();
        this.mobile = that.getMobile();
        this.idCard = that.getIdCard();
        this.type = that.getType();
        this.flag = that.getFlag();
        this.idCardImg = that.getIdCardImg();
        this.failComment = that.getFailComment();
    }

    @Basic
    @Column(name = "REALNAME", nullable = false)
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    @Basic
    @Column(name = "MOBILE")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "ID_CARD", nullable = false)
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Basic
    @Column(name = "TYPE", nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "FLAG")
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Basic
    @Column(name = "ID_CARD_IMG")
    public String getIdCardImg() {
        return idCardImg;
    }

    public void setIdCardImg(String idCardImg) {
        this.idCardImg = idCardImg;
    }

    @Basic
    @Column(name = "FAIL_COMMENT")
    public String getFailComment() {
        return failComment;
    }

    public void setFailComment(String failComment) {
        this.failComment = failComment;
    }

    @Transient
    public Map<String, String> getMapFile() {
        return mapFile;
    }

    public void setMapFile(Map<String, String> mapFile) {
        this.mapFile = mapFile;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/09 下午01:44
     * 备注: 获取类型名称
     */
    @Transient
    public String getTypeName() {
        return type.equals(TYPE_COMPANY) ? "企业" : "个人";
    }
}
