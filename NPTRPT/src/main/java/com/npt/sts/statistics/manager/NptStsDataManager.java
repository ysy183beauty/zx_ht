package com.npt.sts.statistics.manager;

import com.npt.sts.statistics.entity.NptStsData;
import org.summer.extend.manager.BaseManager;

import java.util.List;
import java.util.Map;

/**
 * 项目: NPTSTS
 * 作者: 张磊
 * 日期: 2016/12/06 下午05:37
 * 备注:
 */
public interface NptStsDataManager extends BaseManager<NptStsData> {
    /**
     * 作者: 张磊
     * 日期: 16/10/31 下午2:59
     * 备注: 获取
     *
     * @see com.npt.sts.statistics.manager.NptStsDataManager#getStatistics
     * 里面的最大值
     */
    Integer getStatisticsMax(String orgId, String subId, String startDate, String endDate);

    /**
     * 作者: 张磊
     * 日期: 16/10/26 上午10:42
     * 备注: 获取数据库select数据
     *
     * @param orgId
     * @param subId
     * @param startDate 起始时间
     * @param endDate   结束时间
     */
    List<Map> getStatistics(String orgId, String subId, String startDate, String endDate);

    /**
     * 作者: 张磊
     * 日期: 16/10/28 上午11:44
     * 备注: 获取某时间段内数据变化情况
     */
    List getDetails(String id, String startDate, String endDate);

    /**
     * 作者: 张磊
     * 日期: 16/10/28 下午2:42
     * 备注: 格式化时间
     * 2016     -> 2016
     * 201601   -> 2016-01
     * 20160101 -> 2016-01-01
     */
    String formatDateString(String time, String sep);

    /**
     * 作者: 张磊
     * 日期: 16/10/31 下午2:50
     * 备注: 获取截至到某时间的荣誉、失信信息总量
     */
    List getRysxCount(String startDate);

    /**
     * 作者: 张磊
     * 日期: 16/10/31 下午2:51
     * 备注: 获取某时间段内的荣誉、失信信息增加数量
     */
    List getRysxList(String startDate, String endDate);

    /**
     * 作者: 张磊
     * 日期: 2017/04/05 下午03:20
     * 备注: 数据总量
     */
    Object countZsjl();

    /**
     * 作者: 张磊
     * 日期: 2017/04/05 下午03:54
     * 备注: 组织机构数量
     */
    Object countZzjgsl();

    /**
     * 作者: 张磊
     * 日期: 2017/04/05 下午03:31
     * 备注: 用户数量
     */
    Object countYhsl();

    /**
     * 作者: 张磊
     * 日期: 2017/04/05 下午05:50
     * 备注: 数据量占比
     */
    Object groupSjlzb();

    /**
     * 作者: 张磊
     * 日期: 2017/04/05 下午07:50
     * 备注: 下属机构数据量
     */
    Object groupXsjgsjl();

    /**
     * 作者: 张磊
     * 日期: 2017/04/12 下午09:19
     * 备注: 数据增长率
     */
    Object countSjzzl();

    /**
     * 作者: 张磊
     * 日期: 2017/04/13 上午10:24
     * 备注: 实名用户
     */
    Object countSmyh();

    /**
     * 作者: 张磊
     * 日期: 2017/04/13 上午10:34
     * 备注: 实名用户增长率
     */
    Object countSmyhzzl();

    /**
     * 作者: 张磊
     * 日期: 2017/04/20 下午08:46
     * 备注: 每月注册实名用户数
     */
    Object countMyzcsmyhs();

    /**
     * 作者: 张磊
     * 日期: 2017/04/20 下午09:12
     * 备注: 每月数据增长数
     */
    Object countMysjzzs();

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

