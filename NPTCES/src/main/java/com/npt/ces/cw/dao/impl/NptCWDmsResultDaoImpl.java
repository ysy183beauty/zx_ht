package com.npt.ces.cw.dao.impl;

import com.npt.ces.cw.dao.NptCWDmsResultDao;
import com.npt.ces.cw.entity.NptCWDmsResult;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

/**
 * 项目: NPTCES
 * 作者: 张磊
 * 日期: 2017/07/06 下午04:37
 * 备注:
 */
@Repository
public class NptCWDmsResultDaoImpl extends HibernateSequenceBaseDaoImpl<NptCWDmsResult>
        implements NptCWDmsResultDao {
}

