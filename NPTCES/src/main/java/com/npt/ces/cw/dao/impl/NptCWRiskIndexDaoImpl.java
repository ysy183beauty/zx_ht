package com.npt.ces.cw.dao.impl;

import com.npt.ces.cw.dao.NptCWRiskIndexDao;
import com.npt.ces.cw.entity.NptCWRiskIndex;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

/**
 * 项目: NPTCES
 * 作者: 张磊
 * 日期: 2017/07/17 下午07:46
 * 备注:
 */
@Repository
public class NptCWRiskIndexDaoImpl extends HibernateSequenceBaseDaoImpl<NptCWRiskIndex>
        implements NptCWRiskIndexDao {
}

