package com.npt.rms.log.dao.impl;

import com.npt.rms.base.dao.NptHibernateBaseDaoImpl;
import com.npt.rms.log.dao.NptLogDao;
import com.npt.rms.log.entity.NptLog;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/26 15:13
 * 备注：
 */
@Repository
public class NptLogDaoImpl extends NptHibernateBaseDaoImpl<NptLog> implements NptLogDao {
}
