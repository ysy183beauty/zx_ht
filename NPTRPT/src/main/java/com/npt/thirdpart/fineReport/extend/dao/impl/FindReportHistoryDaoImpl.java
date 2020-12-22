package com.npt.thirdpart.fineReport.extend.dao.impl;

import com.npt.thirdpart.fineReport.extend.dao.FindReportHistoryDao;
import com.npt.thirdpart.fineReport.extend.entity.FindReportHistory;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

/**
 * 项目: NPTRPT
 * 作者: 张磊
 * 日期: 2017/01/06 上午11:41
 * 备注:
 */
@Repository
public class FindReportHistoryDaoImpl extends HibernateSequenceBaseDaoImpl<FindReportHistory>
        implements FindReportHistoryDao {
}

