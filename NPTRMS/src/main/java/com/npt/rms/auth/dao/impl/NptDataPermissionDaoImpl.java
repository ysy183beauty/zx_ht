package com.npt.rms.auth.dao.impl;

import com.npt.rms.auth.dao.NptDataPermissionDao;
import com.npt.rms.auth.entity.NptDataPermission;
import com.npt.rms.base.dao.NptHibernateBaseDaoImpl;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/22 15:29
 * 描述:
 */
@Repository
public class NptDataPermissionDaoImpl extends NptHibernateBaseDaoImpl<NptDataPermission> implements NptDataPermissionDao{
}
