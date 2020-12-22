package com.npt.rms.auth.manager.impl;

import com.npt.rms.auth.dao.NptDataRolePermissionDao;
import com.npt.rms.auth.entity.NptDataRolePermission;
import com.npt.rms.auth.manager.NptDataRolePermissionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.manager.BaseManagerImpl;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/8 12:48
 * 备注：
 */
@Service
public class NptDataRolePermissionManagerImpl extends BaseManagerImpl<NptDataRolePermission> implements NptDataRolePermissionManager{

    @Autowired
    private NptDataRolePermissionDao rolePermissionDao;
}
