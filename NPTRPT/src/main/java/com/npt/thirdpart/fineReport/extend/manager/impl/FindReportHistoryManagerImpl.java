package com.npt.thirdpart.fineReport.extend.manager.impl;

import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.util.NptRmsUtil;
import com.npt.thirdpart.fineReport.extend.entity.FindReport;
import com.npt.thirdpart.fineReport.extend.entity.FindReportHistory;
import com.npt.thirdpart.fineReport.extend.manager.FindReportHistoryManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.manager.BaseManagerImpl;

/**
 * 项目: NPTRPT
 * 作者: 张磊
 * 日期: 2017/01/06 上午11:41
 * 备注:
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FindReportHistoryManagerImpl extends BaseManagerImpl<FindReportHistory> implements FindReportHistoryManager {

    @Override
    public void save(FindReport report) {
        FindReportHistory rptHistory = new FindReportHistory();
        rptHistory.setStatus(NptDict.IDS_ENABLED.getCode());
        rptHistory.setCreateTime(NptCommonUtil.getCurrentSysTimestamp());
        rptHistory.setCreatorId(NptRmsUtil.getCurrentUserId());

        rptHistory.setRptPath(report.getRptPath());
        rptHistory.setRptReport(report);
        this.save(rptHistory);
    }

    @Override
    @Transactional
    public boolean execDisabled(Long id) {
        FindReportHistory result = this.findById(id);
        if (result != null && result.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {
            result.setStatus(NptDict.IDS_DISABLED.getCode());
            this.dao.update(result);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean execEnabled(Long id) {
        FindReportHistory result = this.findById(id);
        if (result != null && !result.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {
            result.setStatus(NptDict.IDS_ENABLED.getCode());
            this.dao.update(result);
            return true;
        }
        return false;
    }
}

