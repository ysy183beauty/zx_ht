package com.npt.rms.auth.manager.impl;

import com.npt.rms.auth.dao.NptDataUserRoleDao;
import com.npt.rms.auth.entity.NptDataUserRole;
import com.npt.rms.auth.manager.NptDataUserRoleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.manager.BaseManagerImpl;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/2 20:54
 * 备注：
 */
@Service
public class NptDataUserRoleManagerImpl extends BaseManagerImpl<NptDataUserRole> implements NptDataUserRoleManager{

    @Autowired
    private NptDataUserRoleDao userRoleDao;
}
