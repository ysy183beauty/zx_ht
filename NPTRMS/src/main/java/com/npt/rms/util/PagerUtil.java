package com.npt.rms.util;

/**
 * 分页控件工具类
 *
 * @author zhuxiaofei
 * @version 1.0
 * @since 2014-7-17
 */
public class PagerUtil {

    /**
     * 得到分页数据sql
     *
     * @param formsql  原始sql
     * @param pageNo   选择页数
     * @param pageSize 每页显示条数
     * @return
     */
    public static String getPageDataSql(String formsql, int pageNo, int pageSize) {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT * FROM ( SELECT A.*, ROWNUM RN  FROM ( ");
        sb.append(formsql);
        sb.append(" ) A WHERE ROWNUM <= " + (pageNo + 1) + "*" + pageSize + " ) WHERE RN >= " + (pageNo + 1) + "*" + pageSize + "-"
                + pageSize + "+1  ");
        return sb.toString();
    }

    /**
     * 得到分页数据的总条数sql
     *
     * @param formsql 原始sql
     * @return
     */
    public static String getCountsSql(String formsql) {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT COUNT(*) ALLCOUNTS FROM ( ");
        sb.append(formsql);
        sb.append(" )  ");
        return sb.toString();
    }

}
