package com.npt.thirdpart.fineReport.extend.manager.impl;


import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.util.NptRmsUtil;
import com.npt.thirdpart.fineReport.extend.dao.FindReportDao;
import com.npt.thirdpart.fineReport.extend.entity.FindReport;
import com.npt.thirdpart.fineReport.extend.manager.FindReportManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.manager.BaseManagerImpl;
import org.summer.extend.orm.condition.Conditions;
import org.summer.extend.security.PlatformSecurityContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FindReportManagerImpl extends BaseManagerImpl<FindReport> implements FindReportManager {
    @Autowired
    private FindReportDao dao;

    @Override
    @Transactional
    public FindReport findFindReportById(Long id) {
        FindReport result = this.findById(id);
        if (result != null) {
            result.getRoles().size();
        }
        return result;
    }

    @Override
    @Transactional
    public void updateFindReport(FindReport report, String[] addRoles, String... delRoles) {
        FindReport result = this.findById(report.getId());
        result.setModifyId(NptRmsUtil.getCurrentUserId());
        result.setLastModifyTime(NptCommonUtil.getCurrentSysTimestamp());
        result.setPubLevel(report.getPubLevel());
        result.setRptCategory(report.getRptCategory());
        result.setRptHost(report.getRptHost());
        result.setRptName(report.getRptName());
        result.setRptNote(report.getRptNote());
        result.setRptPath(report.getRptPath());
        result.setShowStyle(report.getShowStyle());
        result.setMenuIndex(report.getMenuIndex());
        result.setStatus(report.getStatus());
        if (addRoles != null && addRoles.length > 0) {
            Set<String> roles = result.getRoles();
            for (String r : addRoles) {
                roles.add(r);
            }
        }
        if (delRoles != null && delRoles.length > 0) {
            Set<String> roles = result.getRoles();
            for (String r : delRoles) {
                roles.remove(r);
            }
        }
        this.update(result);
    }

    @Override
    @Transactional
    public boolean execDisabled(Long id) {
        FindReport result = this.findById(id);
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
        FindReport result = this.findById(id);
        if (result != null && !result.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {
            result.setStatus(NptDict.IDS_ENABLED.getCode());
            this.dao.update(result);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateReportSecurity(Long id, Set<String> roles) {
        FindReport result = this.findById(id);
        if (result == null) return false;
        return false;
    }

    @Override
    public List<FindReport> findFindReportByRoles(String[] roles) {
//		Conditions.or(Conditions.ne("security", FindReport.EnumSecurity.role.value), Conditions.("security", FindReport.EnumSecurity.role.value))
        if (roles == null || roles.length == 0) {
            return java.util.Collections.emptyList();
        }

        List<FindReport> allReports = this.findByCondition(Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()), Conditions.eq("showStyle", NptDict.RPS_NORMAL.getCode()), Conditions.eq("rptCategory", NptDict.RPC_STATISTIC.getCode()), Conditions.asc("rptName"));
        List<FindReport> reports = new ArrayList<>();
        for (FindReport r : allReports) {
            if (r.getPubLevel() != NptDict.RPP_AUTH.getCode()) {
                reports.add(r);
            } else {
                Set<String> reportRoles = r.getRoles();
                for (String ur : roles) {
                    if (reportRoles.contains(ur)) {
                        reports.add(r);
                        break;
                    }
                }
            }
        }
        return reports;
    }

    @Override
    public boolean validateSecurity(Long reportletId, String reportlet) {
        FindReport findReport = this.findFindReportById(reportletId);
        if (findReport == null) {
//            this.log("参数错误，报表Id不存在：" + reportletId);
            return false;
        }
        if (!findReport.getRptPath().equals(reportlet)) {
//            this.log("参数错误，参数无法匹配：" + reportlet);
            return false;
        }
        if (!findReport.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {
//            this.log("报表未启用：" + reportlet);
            return false;
        }

        Integer security = findReport.getPubLevel();
        if (security.equals(NptDict.RPP_ANONYMOUS.getCode())) {
            return true;
        } else if (security.equals(NptDict.RPP_LOGIN.getCode())) {
            if (PlatformSecurityContext.isLogined()) {
                return true;
            }
//            this.log("没有访问权限：要求登录才能访问：" + reportlet);
        } else {
            if (PlatformSecurityContext.isLogined()) {
                if (PlatformSecurityContext.getCurrentOperator().isAdministrator()) {
                    return true;
                }
                Set<String> roles = findReport.getRoles();
                for (String r : PlatformSecurityContext.getCurrentOperatorRoles()) {
                    if (roles.contains(r)) {
                        return true;
                    }
                }
//                this.log("没有访问权限：角色不满足：" + reportlet);
            } else {
//                this.log("没有访问权限：要求登录才能访问：" + reportlet);
            }
        }
        return false;
    }

    @Override
    public List<Map> selectDataBySql(String sql) {
        return this.dao.selectDataBySql(sql);
    }

}
