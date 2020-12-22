package com.npt.thirdpart.fineReport.extend.manager;

import com.npt.thirdpart.fineReport.extend.entity.FindReport;
import org.summer.extend.manager.BaseManager;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FindReportManager extends BaseManager<FindReport> {

    FindReport findFindReportById(Long id);

    boolean validateReportSecurity(Long id, Set<String> roles);

    void updateFindReport(FindReport report, String[] addRoles, String... delRoles);

    boolean execDisabled(Long id);

    boolean execEnabled(Long id);

    List<FindReport> findFindReportByRoles(String[] roles);

    /**
     * 作者: 张磊
     * 日期: 17/1/10 上午11:50
     * 备注: 验证用户是否有权限访问某报表，有权限返回true
     */
    boolean validateSecurity(Long reportletId, String reportlet);

    /**
     * 通过sql查询数据信息
     */
     List<Map> selectDataBySql(String sql);
}