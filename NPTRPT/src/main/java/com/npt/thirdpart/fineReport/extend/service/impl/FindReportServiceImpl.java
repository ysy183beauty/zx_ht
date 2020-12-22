package com.npt.thirdpart.fineReport.extend.service.impl;

import com.npt.thirdpart.fineReport.extend.entity.FindReport;
import com.npt.thirdpart.fineReport.extend.entity.FindReportHistory;
import com.npt.thirdpart.fineReport.extend.manager.FindReportHistoryManager;
import com.npt.thirdpart.fineReport.extend.manager.FindReportManager;
import com.npt.thirdpart.fineReport.extend.service.FindReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.orm.condition.Conditions;

import java.util.List;

/**
 * 项目: NPTRPT
 * 作者: 张磊
 * 日期: 17/1/6 上午11:46
 * 备注:
 */
@Service("rptService")
@Transactional
public class FindReportServiceImpl implements FindReportService {
    @Autowired
    private FindReportManager findReportManager;
    @Autowired
    private FindReportHistoryManager findReportHistoryManager;

    @Override
    public void save(FindReport report) {
        findReportManager.save(report);
        findReportHistoryManager.save(report);
    }

    @Override
    public void deleteById(Long id) {
        findReportHistoryManager.deleteByProperty("rptReport.id", id);
        findReportManager.deleteById(id);
    }

    @Override
    public void updateFindReport(FindReport data, String[] addRoles, String[] delRoles) {
        FindReport report = findReportManager.findById(data.getId());
        // 将模板插入历史
        if (findReportHistoryManager.countByCondition(Conditions.eq("rptReport.id", data.getId()), Conditions.eq("rptPath", data.getRptPath())) == 0) {
            findReportHistoryManager.save(report);
        }
        // 将其它模板设为禁用
        List<FindReportHistory> reportHistoryList = findReportHistoryManager.findByCondition(Conditions.eq("rptReport.id", data.getId()), Conditions.ne("rptPath", data.getRptPath()));
        for (FindReportHistory history : reportHistoryList) {
            findReportHistoryManager.execDisabled(history.getId());
        }

        findReportManager.updateFindReport(data, addRoles, delRoles);
    }
}
