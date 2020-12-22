package com.npt.rms.auth.dao.impl;

import com.npt.rms.auth.dao.NptSimpleUserDao;
import com.npt.rms.auth.entity.NptSimpleUser;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/11 15:42
 * 描述:
 */
@Repository
public class NptSimpleUserDaoImpl extends HibernateSequenceBaseDaoImpl<NptSimpleUser> implements NptSimpleUserDao{
}
