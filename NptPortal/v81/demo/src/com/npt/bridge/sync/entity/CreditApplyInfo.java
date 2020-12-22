package com.npt.bridge.sync.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/3/25
 * 备注: 申请
 */
@Entity
@Table(name = "NPT_USER_APPLY_INFO")
public class CreditApplyInfo extends NptSyncBase<Integer> {

    private Integer userId;
    private String applyTime;
    private String applyFlag;
    private String applyType;

    public CreditApplyInfo() {
        super();
    }

    @Override
    public <T extends NptSyncBase> void update(T entity) {
        CreditApplyInfo that = (CreditApplyInfo) entity;
        this.userId = that.getId();
        this.applyTime = that.getApplyTime();
        this.applyFlag = that.getApplyFlag();
        this.applyType = that.getApplyType();
    }

    @Column(name = "USER_ID")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "APPLY_TIME", length = 60)
    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    @Column(name = "APPLY_FLAG", length = 20)
    public String getApplyFlag() {
        return applyFlag;
    }

    public void setApplyFlag(String applyFlag) {
        this.applyFlag = applyFlag;
    }

    @Column(name = "APPLY_TYPE", length = 20)
    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

}
