package com.npt.sts.statistics.manager.impl;

import com.npt.bridge.dict.NptDict;
import com.npt.sts.statistics.dao.NptStsDataDao;
import com.npt.sts.statistics.entity.NptStsData;
import com.npt.sts.statistics.manager.NptStsDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.manager.BaseManagerImpl;

import java.math.BigDecimal;
import java.util.*;

/**
 * 项目: NPTSTS
 * 作者: 张磊
 * 日期: 2016/12/06 下午05:37
 * 备注:
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NptStsDataManagerImpl extends BaseManagerImpl<NptStsData> implements NptStsDataManager {
    @Autowired
    private NptStsDataDao dao;

    @Override
    public Integer getStatisticsMax(String orgId, String subId, String startDate, String endDate) {
        return dao.getStatisticsMax(orgId, subId, startDate, endDate);
    }

    @Override
    public List<Map> getStatistics(String orgId, String subId, String startDate, String endDate) {
        return dao.getStatistics(orgId, subId, startDate, endDate);
    }

    @Override
    public List getDetails(String id, String startDate, String endDate) {
        int totalCount = dao.getTotalCount(id, startDate);
        List<List> details = dao.getDetails(id, startDate, endDate);
        List result = new ArrayList();
        for (List detail : details) {
            ArrayList list = new ArrayList(detail);
            // 计算总量
            totalCount += Integer.parseInt(String.valueOf(detail.get(1))) - Integer.parseInt(String.valueOf(detail.get(3)));
            list.add(String.valueOf(totalCount));
            // 格式化时间
            String time = String.valueOf(detail.get(0));
            time = formatDateString(time, "/");
            list.set(0, time);
            result.add(list);
        }
        return result;
    }

    @Override
    public String formatDateString(String time, String sep) {
        if (time == null) return null;
        if (sep == null) sep = "/";
        switch (time.length()) {
            case 8:
                time = time.substring(0, 4) + sep + time.substring(4, 6) + sep + time.substring(6, 8);
                break;
            case 6:
                time = time.substring(0, 4) + sep + time.substring(4, 6);
                break;
            case 4:
                break;
            default:
                time = null;
        }
        return time;
    }

    @Override
    public List getRysxCount(String startDate) {
        return dao.getRysxCount(startDate);
    }

    @Override
    public List getRysxList(String startDate, String endDate) {
        return dao.getRysxList(startDate, endDate);
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/05 下午03:54
     * 备注: 数据总量
     */
    @Override
    public Object countZsjl() {
        String sql = "SELECT sum(t.NUM_ROWS) FROM USER_TABLES t, NPT_DATA_TYPE dataType WHERE t.TABLE_NAME = dataType.DATA_TYPE_DBNAME AND t.TABLE_NAME LIKE 'NCE_%'";
        List queryList = dao.findBySql(sql);
        return queryList.get(0);
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/05 下午03:54
     * 备注: 组织机构数量
     */
    @Override
    public Object countZzjgsl() {
        String sql = "select count(1) from NPT_ORGANIZATION";
        List queryList = dao.findBySql(sql);
        return queryList.get(0);
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/05 下午03:43
     * 备注: 用户数量
     */
    @Override
    public Object countYhsl() {
        String sql = "select count(1) from SEC_USER";
        List queryList = dao.findBySql(sql);
        return queryList.get(0);
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/05 下午05:50
     * 备注: 数据量占比
     */
    @Override
    public Object groupSjlzb() {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map;
        Collection<?> byHql = dao.findBySql("SELECT org.OBJ_ALIAS, nvl(sum(t.NUM_ROWS),0) FROM USER_TABLES t, NPT_DATA_TYPE dataType, NPT_ORGANIZATION org WHERE t.TABLE_NAME = dataType.DATA_TYPE_DBNAME AND t.TABLE_NAME LIKE 'NCE_%' AND dataType.PARENT_ID = org.ID AND org.PARENT_ID = '-2' GROUP BY org.OBJ_ALIAS");
        for (Object o : byHql) {
            map = new HashMap<>();
            map.put("name", ((Object[]) o)[0]);
            map.put("value", ((Object[]) o)[1]);
            result.add(map);
        }
        return result;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/05 下午07:50
     * 备注: 下属机构数据量
     */
    @Override
    public Object groupXsjgsjl() {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map;
        List sqlQueryList = dao.findBySql("select org1.OBJ_ALIAS,count(org2.OBJ_ALIAS) from NPT_ORGANIZATION org1 LEFT JOIN NPT_ORGANIZATION org2  on org2.parent_id=org1.id where org1.PARENT_ID=-2 GROUP BY org1.OBJ_ALIAS");
        for (Object o : sqlQueryList) {
            map = new HashMap<>();
            map.put("name", ((Object[]) o)[0]);
            map.put("value", ((Object[]) o)[1]);
            result.add(map);
        }
        return result;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/13 上午10:25
     * 备注: 实名用户
     */
    @Override
    public Object countSmyh() {
        String sql = "select count(1) from NPT_CREDIT_USER where FLAG=3";
        List queryList = dao.findBySql(sql);
        return queryList.get(0);
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/12 下午09:19
     * 备注: 数据增长率
     */
    @Override
    public Object countSjzzl() {
        BigDecimal b1 = getSjl(NptDict.STS_MONTH_DATA, -2);
        BigDecimal b2 = getSjl(NptDict.STS_MONTH_DATA, -1);
        if (b1 != null && b2 != null) {
            return b2.subtract(b1).divide(b1, 0, BigDecimal.ROUND_HALF_DOWN);
        }
        return 100;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/13 上午10:34
     * 备注: 实名用户增长率
     */
    @Override
    public Object countSmyhzzl() {
        BigDecimal b1 = getSjl(NptDict.STS_MONTH_CREDIT_USER, -2);
        BigDecimal b2 = getSjl(NptDict.STS_MONTH_CREDIT_USER, -1);
        if (b1 != null && b2 != null) {
            return b2.subtract(b1).divide(b1, 0, BigDecimal.ROUND_HALF_DOWN);
        }
        return 100;
    }

    private BigDecimal getSjl(NptDict stsType, int month) {
        List list = dao.findBySql("SELECT sjl\n" +
                "FROM (\n" +
                "  SELECT *\n" +
                "  FROM NPT_STS_DATA\n" +
                "  WHERE STS_TYPE_ID = " + String.valueOf(stsType.getCode()) + " AND to_char(DATA_TIME, 'YYYYMM') = to_char(add_months(sysdate, " + String.valueOf(month) + "), 'YYYYMM')\n" +
                "  ORDER BY DATA_TIME DESC)\n" +
                "WHERE ROWNUM = 1");
        if (list.size() > 0) {
            return (BigDecimal) list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/20 下午08:46
     * 备注: 每月注册实名用户数
     */
    @Override
    public Object countMyzcsmyhs() {
        List list = new ArrayList();
        for (int i = -10; i < 0; i++) {
            BigDecimal value = getSjl(NptDict.STS_MONTH_CREDIT_USER, i);
            list.add(value == null ? Integer.valueOf(0) : value);
        }
        return list;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/20 下午09:12
     * 备注: 每月数据增长数
     */
    @Override
    public Object countMysjzzs() {
        List list = new ArrayList();
        BigDecimal value = null;
        BigDecimal value_next = null;
        for (int i = -10; i <= 0; i++) {
            value_next = getSjl(NptDict.STS_MONTH_DATA, i);
            value_next = value_next == null ? BigDecimal.valueOf(0) : value_next;
            if (value == null) {
                value = value_next;
                continue;
            }
            list.add(value_next.subtract(value));
            value = value_next;
        }
        return list;
    }

    @Override
    public List<Map> getOrgName() {
        return dao.getOrgName();
    }

    @Override
    public List<Map> selectUnitNameList(Integer stsStypeId) {
        return dao.selectUnitNameList(stsStypeId);
    }

    @Override
    public List<Map> selectUnitResult(String sql) {
        return dao.selectUnitResult(sql);
    }
}

