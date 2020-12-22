package com.npt.rms.auth.dao.impl;

import com.npt.rms.auth.dao.NptDataRoleDao;
import com.npt.rms.auth.entity.NptDataRole;
import com.npt.rms.base.dao.NptHibernateBaseDaoImpl;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/27 21:12
 * 备注：
 */
@Repository
public class NptDataRoleDaoImpl extends NptHibernateBaseDaoImpl<NptDataRole> implements NptDataRoleDao{
}
