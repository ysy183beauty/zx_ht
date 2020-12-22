package com.npt.rms.auth.dao.impl;

import com.npt.rms.auth.dao.NptDataUserRoleDao;
import com.npt.rms.auth.entity.NptDataUserRole;
import com.npt.rms.base.dao.NptHibernateBaseDaoImpl;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/2 20:55
 * 备注：
 */
@Repository
public class NptDataUserRoleDaoImpl extends NptHibernateBaseDaoImpl<NptDataUserRole> implements NptDataUserRoleDao{
}
