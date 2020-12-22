package com.npt.thirdpart.fineReport.manager.impl;

import com.npt.thirdpart.fineReport.dao.ReportLogDao;
import com.npt.thirdpart.fineReport.entity.ReportLogBean;
import com.npt.thirdpart.fineReport.manager.ReportLogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.manager.BaseManagerImpl;

import java.util.List;
import java.util.Map;

/**
 * 项目: NPTWebApp
 * 作者: 张磊
 * 日期: 16/11/28 下午2:16
 * 备注:
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ReportLogManagerImpl extends BaseManagerImpl<ReportLogBean> implements ReportLogManager {
    @Autowired
    private ReportLogDao dao;
    @Override
    public List<Map> selectPersonReportHistory(String sql) {
        return this.dao.selectPersonReportHistory(sql);
    }
}
