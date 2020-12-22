package com.npt.thirdpart.fineReport.dao;

import com.npt.thirdpart.fineReport.entity.ReportLogBean;
import org.summer.extend.orm.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * 项目: NPTWebApp
 * 作者: 张磊
 * 日期: 16/11/28 下午2:16
 * 备注:
 */
public interface ReportLogDao extends BaseDao<ReportLogBean> {
    /**
     * 查询个人信用历史报告
     */
    List<Map> selectPersonReportHistory(String sql);
}
