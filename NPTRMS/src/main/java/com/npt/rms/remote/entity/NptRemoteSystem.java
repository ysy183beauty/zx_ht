package com.npt.rms.remote.entity;

import com.npt.bridge.base.NptEntitySerializable;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.util.NptRmsUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/28 16:15
 * 描述:
 */
@Entity
@Table(name = "NPT_REMOTE_SYSTEM")
public class NptRemoteSystem extends NptEntitySerializable{
    public static final String PROPERTY_ACTION_TYPE = "actionType";


    private String siteIp;
    private Integer sitePort;
    private String actionName;
    private Integer actionType;

    public NptRemoteSystem() {
        super();
        this.setStatus(NptDict.IDS_ENABLED.getCode());
        this.setCreatorId(NptRmsUtil.getCurrentUserId());
        this.setCreateTime(NptCommonUtil.getCurrentSysDate());
    }

    public NptRemoteSystem(String siteIp,Integer sitePort, String actionName, Integer actionType) {
        this();
        this.siteIp = siteIp;
        this.sitePort = sitePort;
        this.actionName = actionName;
        this.actionType = actionType;
    }

    @Column(name = "SITE_IP",nullable = false,length = 32)
    public String getSiteIp() {
        return siteIp;
    }

    public void setSiteIp(String siteIp) {
        this.siteIp = siteIp;
    }

    @Column(name = "SITE_PORT",nullable = false)
    public Integer getSitePort() {
        return sitePort;
    }

    public void setSitePort(Integer sitePort) {
        this.sitePort = sitePort;
    }

    @Column(name = "ACTION_NAME",nullable = false,length = 256)
    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    @Column(name = "ACTION_TYPE",nullable = false)
    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    @Transient
    public String getActionUrl(){
        //return "http://" + this.getSiteIp() + ":" + this.getSitePort() + this.getActionName();
        String url="https://" + this.getSiteIp()+ this.getActionName();
        return url;
    }
}
