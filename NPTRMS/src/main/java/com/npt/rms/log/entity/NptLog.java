package com.npt.rms.log.entity;



import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/26 14:08
 * 备注：
 */
@Entity
@Table(name = "NPT_LOG")
public class NptLog extends NptEntitySerializable {

    private String businessName;

    private String actionName;

    private Integer businessType;

    private Integer actionType;

    private String invokeIP;

    private String userName;

    private Integer resultCode;

    private String params;

    private String results;

    private String causedBy;


    @Column(name = "BUSINESS_NAME",nullable = false,length = 64)
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @Column(name = "ACTION_NAME",nullable = false,length = 128)
    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    @Column(name = "INVOKE_IP",length = 16)
    public String getInvokeIP() {
        return invokeIP;
    }

    public void setInvokeIP(String invokeIP) {
        this.invokeIP = invokeIP;
    }

    @Column(name = "RESULT_CODE",nullable = false)
    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }


    @Lob
    @Column(name = "PARAMS",columnDefinition = "CLOB")
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }


    @Lob
    @Column(name = "RESULTS",columnDefinition = "CLOB")
    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    @Lob
    @Column(name = "CAUSEDBY",columnDefinition = "CLOB")
    public String getCausedBy() {
        return causedBy;
    }

    public void setCausedBy(String causedBy) {
        this.causedBy = causedBy;
    }

    public void setCausedBy(Exception ex){
        String sOut = "";
        StackTraceElement[] trace = ex.getStackTrace();
        for (StackTraceElement s : trace) {
            sOut += "\tat " + s + "\r\n";
        }
        this.causedBy = sOut;
    }

    @Column(name = "USER_NAME",nullable = false,length = 128)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "BUSINESS_TYPE",nullable = false)
    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    @Column(name = "ACTION_TYPE",nullable = false)
    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }
}
