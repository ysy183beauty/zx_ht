package com.npt.ces.cw.dao.impl;

import com.npt.ces.cw.dao.NptCWResultDao;
import com.npt.ces.cw.entity.NptCWResult;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

/**
 * 项目: NPTCES
 * 作者: 张磊
 * 日期: 2017/07/06 下午04:38
 * 备注:
 */
@Repository
public class NptCWResultDaoImpl extends HibernateSequenceBaseDaoImpl<NptCWResult>
        implements NptCWResultDao {
}

