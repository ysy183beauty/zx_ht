package com.npt.rms.auth.entity;

import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/27 21:09
 * 备注：
 *      角色与权限的关系关系，这里的PermissionId指的是NptDataPermission中的id
 */
@Entity
@Table(name = "NPT_DATA_ROLE_PERMISSION")
public class NptDataRolePermission extends NptEntitySerializable{
    public static final String PROPERTY_ROLE_ID = "roleId";
    public static final String PROPERTY_PERMISSION_ID = "permissionId";

    private Long roleId;
    private Long permissionId;

    @Column(name = "ROLE_ID",nullable = false)
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Column(name = "PERMISSION_ID",nullable = false)
    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}
