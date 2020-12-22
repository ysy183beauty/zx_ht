package com.npt.thirdpart.fineReport.extend.entity;

import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.*;

/**
 * 项目: NPTRPT
 * 作者: 张磊
 * 日期: 17/1/6 上午11:9
 * 备注: 报告模板历史表
 */
@Entity
@Table(name = "NPT_REPORT_TEMPLATE_HISTORY")
public class FindReportHistory extends NptEntitySerializable {
    private FindReport rptReport;
    private String rptPath;

    @ManyToOne
    @JoinColumn(name = "RPT_ID")
    public FindReport getRptReport() {
        return rptReport;
    }

    public void setRptReport(FindReport rptReport) {
        this.rptReport = rptReport;
    }

    @Column(name = "RPT_PATH")
    public String getRptPath() {
        return rptPath;
    }

    public void setRptPath(String rptPath) {
        this.rptPath = rptPath;
    }
}
