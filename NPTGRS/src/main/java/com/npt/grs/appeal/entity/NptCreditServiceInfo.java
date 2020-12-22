package com.npt.grs.appeal.entity;

import com.npt.bridge.sync.entity.NptSyncBase;
import com.npt.bridge.util.NptCommonUtil;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/3/21
 * 备注:
 */
@Entity
@Table(name = "NPT_CREDIT_SERVICE")
public class NptCreditServiceInfo extends NptSyncBase<Long> {

    public static final String PROPERTY_FLAG = "flag";
    public static final String PROPERTY_APPEAL_NO = "appealNo";

    private String tel;//联系电话
    private String email;//联系邮箱
    private String source;//异议来源
    private String title;
    private String text;//详细描述
    private String attach;//附件路径
    /**
     * @see com.npt.bridge.dict.NptDict#CSF_OBJECTION
     */
    private String flag;//服务类别
    private String response;//处理结果
    private Timestamp responseTime;//处理时间
    private String appealNo;

    private Integer userId;
    private String attachFile;

    public NptCreditServiceInfo() {
        super();
    }

    @Override
    public <T extends NptSyncBase> void update(T entity) {
        NptCreditServiceInfo that = (NptCreditServiceInfo) entity;
        this.tel = that.getTel();
        this.email = that.getEmail();
        this.source = that.getSource();
        this.title = that.getTitle();
        this.text = that.getText();
        this.attach = that.getAttach();
        this.flag = that.getFlag();
        this.response = that.getResponse();
        this.responseTime = that.getResponseTime();
        this.userId = that.getUserId();
    }

    @Basic
    @Column(name = "TEL", nullable = false, length = 30)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "EMAIL", nullable = false, length = 30)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "SOURCE", nullable = true, length = 500)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "TITLE", nullable = false, length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "TEXT", nullable = false, length = 4000)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "ATTACH", nullable = true, length = 128)
    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    @Basic
    @Column(name = "FLAG", nullable = false, length = 1)
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Basic
    @Column(name = "RESPONSE", nullable = true, length = 4000)
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Basic
    @Column(name = "USER_ID")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "RESPONSE_TIME")
    public Timestamp getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Timestamp responseTime) {
        this.responseTime = responseTime;
    }

    @Basic
    @Column(name = "APPEAL_NO", length = 64)
    public String getAppealNo() {
        return appealNo;
    }

    public void setAppealNo(String appealNo) {
        this.appealNo = appealNo;
    }

    @Transient
    public String getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/09 下午02:37
     * 备注: 获取服务类型名称
     */
    @Transient
    public String getFlagName() {
        return NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.CSF, Integer.parseInt(flag)).getTitle();
    }
}
