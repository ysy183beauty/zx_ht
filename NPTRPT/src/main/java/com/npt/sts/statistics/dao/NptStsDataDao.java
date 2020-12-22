package com.npt.sts.statistics.dao;

import com.npt.sts.statistics.entity.NptStsData;
import org.summer.extend.orm.dao.BaseDao;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 项目: NPTSTS
 * 作者: 张磊
 * 日期: 2016/12/06 下午05:37
 * 备注:
 */
public interface NptStsDataDao extends BaseDao<NptStsData> {
    /**
     * @see com.npt.sts.statistics.manager.NptStsDataManager#getStatisticsMax
     */
    Integer getStatisticsMax(String orgId, String subId, String startDate, String endDate);

    /**
     * 作者: 张磊
     * 日期: 16/10/27 下午8:17
     * 备注: 获取数据库select数据
     */
    List<Map> getStatistics(String orgId, String subId, String startDate, String endDate);

    /**
     * 作者: 张磊
     * 日期: 16/10/27 下午8:19
     * 备注: 获取某个表截至到某个时间的总数据量
     */
    Integer getTotalCount(String id, String startDate);

    /**
     * 作者: 张磊
     * 日期: 16/10/27 下午8:18
     * 备注: 获取某个表某个时间段内的数据变化情况
     */
    List getDetails(String id, String startDate, String endDate);

    /**
     * @see com.npt.sts.statistics.manager.NptStsDataManager#getRysxCount
     */
    List getRysxCount(String startDate);

    /**
     * @see com.npt.sts.statistics.manager.NptStsDataManager#getRysxList
     */
    List getRysxList(String startDate, String endDate);

    /**
     * 作者: 张磊
     * 日期: 2017/04/05 下午03:27
     * 备注: 执行查询sql
     */
    List findBySql(String sql);

    /**
     * 查询单位名称信息
     */
    List<Map> getOrgName();

    /**
     * 单位行政效能考评结果详情--单位名称信息
     * @return
     */
    List<Map> selectUnitNameList(Integer stsStypeId);

    /**
     * 单位行政效能考评结果
     */
    List<Map> selectUnitResult(String sql);
}

