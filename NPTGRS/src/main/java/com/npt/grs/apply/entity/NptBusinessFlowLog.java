package com.npt.grs.apply.entity;

import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/19 15:23
 * 备注：
 */
@Entity
@Table(name = "NPT_BUSINESS_FLOW_LOG")
public class NptBusinessFlowLog extends NptEntitySerializable{

    public static final String PROPERTY_FLOW_NO = "flowNo";
    public static final String PROPERTY_APPLY_LOG_STATUS = "result";

    private String flowNo;
    private Integer result;
    private String remark;

    @Column(name = "FLOW_NO",nullable = false,length = 64)
    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    @Column(name = "STAGE_RESULT",nullable = false)
    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    @Column(name = "STAGE_REMARK",length = 512)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
