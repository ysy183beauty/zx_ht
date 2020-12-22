package com.npt.thirdpart.fineReport.extend.entity;

import com.npt.bridge.base.NptEntitySerializable;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "NPT_REPORT_TEMPLATE")
public class FindReport extends NptEntitySerializable {

    public static Integer MAX_MENU_INDEX = 4;

    //报告模板名称
    private String rptName;

    //报告模板路径
    private String rptPath;

    //报告模板历史版本
    private Set<FindReportHistory> rptHistory;

    //报告模板描述
    private String rptNote;

    //报告模板类型
    private Integer rptCategory;

    //报告模板宿主
    private Integer rptHost;

    //报告公开级别
    private Integer pubLevel;

    private Set<String> roles;

    //显示方式
    private Integer showStyle;

    //菜单index
    private Integer menuIndex;

    public FindReport() {
        rptHistory = new HashSet<>();
    }

    @Column(name = "RPT_NAME")
    public String getRptName() {
        return rptName;
    }

    public void setRptName(String rptName) {
        this.rptName = rptName;
    }

    @Column(name = "RPT_PATH")
    public String getRptPath() {
        return rptPath;
    }

    public void setRptPath(String rptPath) {
        this.rptPath = rptPath;
    }

    @OneToMany(mappedBy = "rptReport")
    @LazyCollection(LazyCollectionOption.EXTRA)
    public Set<FindReportHistory> getRptHistory() {
        return rptHistory;
    }

    public void setRptHistory(Set<FindReportHistory> rptHistory) {
        this.rptHistory = rptHistory;
    }

    public void addRptHistory(FindReportHistory rptHistory) {
        this.rptHistory.add(rptHistory);
    }

    @Column(name = "RPT_NOTE")
    public String getRptNote() {
        return rptNote;
    }

    public void setRptNote(String rptNote) {
        this.rptNote = rptNote;
    }

    @Column(name = "RPT_CATEGORY")
    public Integer getRptCategory() {
        return rptCategory;
    }

    public void setRptCategory(Integer rptCategory) {
        this.rptCategory = rptCategory;
    }

    @Column(name = "RPT_HOST")
    public Integer getRptHost() {
        return rptHost;
    }

    public void setRptHost(Integer rptHost) {
        this.rptHost = rptHost;
    }

    @Column(name = "PUB_LEVEL")
    public Integer getPubLevel() {
        return pubLevel;
    }

    public void setPubLevel(Integer pubLevel) {
        this.pubLevel = pubLevel;
    }

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "SYS_FIND_REPORT_ROLE", joinColumns = @JoinColumn(name = "REPORT_ID_"))
    @Column(name = "ROLE_")
    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Column(name = "SHOW_STYLE")
    public Integer getShowStyle() {
        return showStyle;
    }

    public void setShowStyle(Integer showStyle) {
        this.showStyle = showStyle;
    }

    @Column(name = "MENU_INDEX")
    public Integer getMenuIndex() {
        return menuIndex;
    }

    public void setMenuIndex(Integer menuIndex) {
        this.menuIndex = menuIndex;
    }
}