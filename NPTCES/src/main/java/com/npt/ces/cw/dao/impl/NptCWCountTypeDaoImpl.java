package com.npt.ces.cw.dao.impl;

import com.npt.ces.cw.dao.NptCWCountTypeDao;
import com.npt.ces.cw.entity.NptCWCountType;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

/**
 * 项目: NPTCES
 * 作者: 张磊
 * 日期: 2017/07/18 下午03:27
 * 备注:
 */
@Repository
public class NptCWCountTypeDaoImpl extends HibernateSequenceBaseDaoImpl<NptCWCountType>
        implements NptCWCountTypeDao {
}

