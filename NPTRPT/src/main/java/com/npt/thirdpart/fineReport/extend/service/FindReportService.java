package com.npt.thirdpart.fineReport.extend.service;

import com.npt.thirdpart.fineReport.extend.entity.FindReport;

/**
 * 项目: NPTRPT
 * 作者: 张磊
 * 日期: 17/1/6 上午11:42
 * 备注:
 */
public interface FindReportService {
    /**
     * 作者: 张磊
     * 日期: 17/1/6 上午11:46
     * 备注: 保存模板和历史记录
     */
    void save(FindReport report);

    void deleteById(Long id);

    void updateFindReport(FindReport data, String[] addRoles, String[] delRoles);
}
