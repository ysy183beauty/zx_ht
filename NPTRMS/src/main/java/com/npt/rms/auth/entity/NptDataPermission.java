package com.npt.rms.auth.entity;

import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/22 15:03
 * 描述:
 */
@Entity
@Table(name = "NPT_DATA_PERMISSION")
public class NptDataPermission extends NptEntitySerializable{

    public static final String PROPERTY_ORG_ID = "orgId";
    public static final String PROPERTY_ACTION = "action";

    private String permissionTitle;
    private Long orgId;
    private Integer action;

    @Column(name = "ORG_ID",nullable = false)
    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    @Column(name = "ACTION_ID",nullable = false)
    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    @Column(name = "PERMISSION_TITLE",nullable = false,length = 128)
    public String getPermissionTitle() {
        return permissionTitle;
    }

    public void setPermissionTitle(String permissionTitle) {
        this.permissionTitle = permissionTitle;
    }
}
