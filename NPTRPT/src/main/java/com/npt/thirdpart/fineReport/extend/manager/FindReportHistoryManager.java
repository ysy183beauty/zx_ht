package com.npt.thirdpart.fineReport.extend.manager;

import com.npt.thirdpart.fineReport.extend.entity.FindReport;
import com.npt.thirdpart.fineReport.extend.entity.FindReportHistory;
import org.summer.extend.manager.BaseManager;

/**
 * 项目: NPTRPT
 * 作者: 张磊
 * 日期: 2017/01/06 上午11:41
 * 备注:
 */
public interface FindReportHistoryManager extends BaseManager<FindReportHistory> {

    /**
     * 作者: 张磊
     * 日期: 17/1/6 下午2:50
     * 备注: 保存报表历史
     */
    void save(FindReport report);

    boolean execDisabled(Long id);

    boolean execEnabled(Long id);
}

