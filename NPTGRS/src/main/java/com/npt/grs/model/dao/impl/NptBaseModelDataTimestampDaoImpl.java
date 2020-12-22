package com.npt.grs.model.dao.impl;

import com.npt.grs.model.dao.NptBaseModelDataTimestampDao;
import com.npt.bridge.model.NptBaseModelPoolStamp;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2016/12/14 11:44
 * 描述:
 */
@Repository
public class NptBaseModelDataTimestampDaoImpl extends HibernateSequenceBaseDaoImpl<NptBaseModelPoolStamp> implements NptBaseModelDataTimestampDao {
}
