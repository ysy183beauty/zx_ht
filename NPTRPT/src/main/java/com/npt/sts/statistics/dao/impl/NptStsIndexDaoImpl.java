package com.npt.sts.statistics.dao.impl;

import com.npt.sts.statistics.dao.NptStsIndexDao;
import com.npt.sts.statistics.entity.NptStsIndex;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

/**
 * 项目: NPTSTS
 * 作者: 张磊
 * 日期: 2016/12/06 下午05:37
 * 备注:
 */
@Repository
public class NptStsIndexDaoImpl extends HibernateSequenceBaseDaoImpl<NptStsIndex>
        implements NptStsIndexDao {
}

