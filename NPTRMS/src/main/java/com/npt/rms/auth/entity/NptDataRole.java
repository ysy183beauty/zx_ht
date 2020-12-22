package com.npt.rms.auth.entity;

import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/27 16:47
 * 备注：
 */
@Entity
@Table(name = "NPT_DATA_ROLE")
public class NptDataRole extends NptEntitySerializable{

    public static final String PROPERTY_ROLE_NAME = "roleName";

    private String roleName;
    private String roleNote;

    @Column(name = "ROLE_NAME",nullable = false,length = 64)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Column(name = "ROLE_NOTE",length = 256)
    public String getRoleNote() {
        return roleNote;
    }

    public void setRoleNote(String roleNote) {
        this.roleNote = roleNote;
    }
}
