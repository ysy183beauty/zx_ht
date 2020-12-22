package com.npt.rms.remote.dao.impl;

import com.npt.rms.base.dao.NptHibernateBaseDaoImpl;
import com.npt.rms.remote.dao.NptRemoteSystemDao;
import com.npt.rms.remote.entity.NptRemoteSystem;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/28 16:21
 * 描述:
 */
@Repository
public class NptRemoteSystemDaoImpl extends NptHibernateBaseDaoImpl<NptRemoteSystem> implements NptRemoteSystemDao{
}
