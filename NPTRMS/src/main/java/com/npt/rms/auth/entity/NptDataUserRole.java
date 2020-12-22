package com.npt.rms.auth.entity;

import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/27 21:08
 * 备注：
 *      用户与角色的对应关系
 */
@Entity
@Table(name = "NPT_DATA_USER_ROLE")
public class NptDataUserRole extends NptEntitySerializable{
    public static final String PROPERTY_USER_ID = "userId";
    public static final String PROPERTY_ROLE_ID = "roleId";


    private Long userId;

    private Long roleId;

    @Column(name = "ROLE_ID",nullable = false)
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Column(name = "USER_ID",nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
