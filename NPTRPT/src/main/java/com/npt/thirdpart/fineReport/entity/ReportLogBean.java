package com.npt.thirdpart.fineReport.entity;


import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 项目: NPTWebApp
 * 作者: 张磊
 * 日期: 16/11/28 上午11:41
 * 备注: 企业信用报告导出历史
 */
@Entity
@Table(name = "NPT_REPORT_LOG")
public class ReportLogBean extends NptEntitySerializable {
    private Long userId;
    private String template;
    private String rpbs;
    private String rpmc;
    private String fileName;
    private String reportType;

    @Column(name = "USER_ID")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "TEMPLATE")
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Column(name = "RPBS")
    public String getRpbs() {
        return rpbs;
    }

    public void setRpbs(String rpbs) {
        this.rpbs = rpbs;
    }

    @Column(name = "RPMC")
    public String getRpmc() {
        return rpmc;
    }

    public void setRpmc(String rpmc) {
        this.rpmc = rpmc;
    }

    @Column(name = "FILE_NAME")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "REPORT_TYPE")
    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
}
