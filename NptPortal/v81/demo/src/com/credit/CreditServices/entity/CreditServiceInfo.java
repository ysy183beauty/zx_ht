package com.credit.CreditServices.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.jeecms.core.entity.CmsUser;
import com.npt.bridge.dict.NptDict;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 项目: zxcms
 * 作者: 张磊
 * 日期: 17/3/7 下午4:11
 * 备注: 信用服务
 */
@Entity
@Table(name = "CREDIT_SERVICE")
public class CreditServiceInfo implements Serializable {
    private Long id;
    @JSONField(serialize = false)
    private CmsUser user;//用户
    private String tel;//联系电话
    private String email;//联系邮箱
    private String source;//异议来源
    private String title;
    private String text;//详细描述
    private String attach;//附件路径
    /**
    * @see NptDict#CSF_OBJECTION
    */
    private String flag;//服务类别
    private Timestamp createTime;//创建时间
    private String appealNo;
    @JSONField(serialize = false)
    private String response;//处理结果
    @JSONField(serialize = false)
    private Timestamp responseTime;//处理时间
    @JSONField(serialize = false)
    private Integer syncStatus;//是否同步
    @JSONField(serialize = false)
    private Timestamp syncTime;//最后同步时间

    private Integer userId;
    private String attachFile;

    public CreditServiceInfo() {
        this.createTime = new Timestamp(new Date().getTime());
        this.syncStatus = NptDict.RCS_NOTSYNED.getCode();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CREDIT_SERVICE_SEQ")
    @SequenceGenerator(name = "CREDIT_SERVICE_SEQ", sequenceName = "CREDIT_SERVICE_SEQ", allocationSize = 1)
    @Column(name = "ID", nullable = false, precision = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
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
    @Column(name = "CREATE_TIME", nullable = false)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "APPEAL_NO", length = 64)
    public String getAppealNo() {
        return appealNo;
    }

    public void setAppealNo(String appealNo) {
        this.appealNo = appealNo;
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
    @Column(name = "RESPONSE_TIME")
    public Timestamp getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Timestamp responseTime) {
        this.responseTime = responseTime;
    }

    @Basic
    @Column(name = "SYNC_STATUS", nullable = false)
    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    @Basic
    @Column(name = "SYNC_TIME")
    public Timestamp getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Timestamp syncTime) {
        this.syncTime = syncTime;
    }

    @Transient
    public Integer getUserId() {
        return user.getId();
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Transient
    public String getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
    }
}