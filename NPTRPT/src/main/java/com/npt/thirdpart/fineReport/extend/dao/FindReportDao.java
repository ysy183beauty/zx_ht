package com.npt.thirdpart.fineReport.extend.dao;

import com.npt.thirdpart.fineReport.extend.entity.FindReport;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.dao.BaseDao;

import java.util.List;
import java.util.Map;

@Repository
public interface FindReportDao extends BaseDao<FindReport> {
    /**
     * 通过sql查询数据信息
     * @param sql
     * @return
     */
    List<Map> selectDataBySql(String sql);
}
