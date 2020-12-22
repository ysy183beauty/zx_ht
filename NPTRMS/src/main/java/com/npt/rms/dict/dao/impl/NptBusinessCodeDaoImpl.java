package com.npt.rms.dict.dao.impl;

import com.npt.bridge.dict.NptBusinessCode;
import com.npt.rms.dict.dao.NptBusinessCodeDao;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/17 21:26
 * 备注：
 */
@Repository
public class NptBusinessCodeDaoImpl extends HibernateSequenceBaseDaoImpl<NptBusinessCode> implements NptBusinessCodeDao {
}
