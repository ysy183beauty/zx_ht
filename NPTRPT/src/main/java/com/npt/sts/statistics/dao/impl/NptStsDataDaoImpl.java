package com.npt.sts.statistics.dao.impl;

import com.npt.sts.statistics.dao.NptStsDataDao;
import com.npt.sts.statistics.entity.NptStsData;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 项目: NPTSTS
 * 作者: 张磊
 * 日期: 2016/12/06 下午05:37
 * 备注:
 */
@Repository
public class NptStsDataDaoImpl extends HibernateSequenceBaseDaoImpl<NptStsData>
        implements NptStsDataDao {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactoryOverride(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public Integer getStatisticsMax(String orgId, String subId, String startDate, String endDate) {
        // where 条件
        StringBuilder whereCondition = new StringBuilder();
        whereCondition.append(" where 1=1\n");
        if (startDate != null && endDate != null) {
            whereCondition.append("  and substr(CREATE_TIME, 0, ").append(startDate.length()).append(") >= '").append(startDate).append("' AND substr(CREATE_TIME, 0, ").append(endDate.length()).append(") <= '").append(endDate).append("'\n");
        }
        if (orgId != null) {
            whereCondition.append(" AND org_id='").append(orgId).append("'\n");
        }
        if (subId != null) {
            whereCondition.append(" AND sub_id='").append(subId).append("'\n");
        }

        // select 语句
        StringBuilder sql = new StringBuilder();
        sql.append("select max(sum(INSERT_AMOUNT)-sum(DELETE_AMOUNT)) max_count\n" +
                "from npt_stats_subject\n");

        sql.append(whereCondition);
        sql.append("GROUP BY (org_id,org_nm,sub_id,sub_name,table_type,table_name)");

        BigDecimal o = (BigDecimal) sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).uniqueResult();
        return o == null ? 0 : o.intValue();
    }

    @Override
    public List<Map> getStatistics(String orgId, String subId, String startDate, String endDate) {
        // where 条件
        StringBuilder whereCondition = new StringBuilder();
        whereCondition.append(" where 1=1\n");
        if (startDate != null && endDate != null) {
            whereCondition.append("  and substr(CREATE_TIME, 0, ").append(startDate.length()).append(") >= '").append(startDate).append("' AND substr(CREATE_TIME, 0, ").append(endDate.length()).append(") <= '").append(endDate).append("'\n");
        }
        if (orgId != null) {
            whereCondition.append(" AND org_id='").append(orgId).append("'\n");
        }
        if (subId != null) {
            whereCondition.append(" AND sub_id='").append(subId).append("'\n");
        }

        // select 语句
        StringBuilder sql = new StringBuilder();
        sql.append("select to_char(org_id) org_id,org_nm,to_char(sub_id) sub_id,sub_name,to_char(table_type) table_type,table_name," +
                "  sum(INSERT_AMOUNT)-sum(DELETE_AMOUNT) count " +
                " from npt_stats_subject ");

        sql.append(whereCondition);
        sql.append(" GROUP BY (org_id,org_nm,sub_id,sub_name,table_type,table_name)");

        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
        List<Map> list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    @Override
    public Integer getTotalCount(String id, String startDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("select sum(INSERT_AMOUNT)-sum(DELET_AMOUNT) from NPT_STS_GET_TOTAL\n" +
                "  WHERE substr(CREATE_TIME,0,").append(startDate.length()).append(")<'").append(startDate).append("'\n" +
                "and TABLE_TYPE = '").append(id).append("'\n" +
                "GROUP BY TABLE_TYPE");
        BigDecimal o = (BigDecimal) sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).uniqueResult();
        return o == null ? 0 : o.intValue();
    }

    @Override
    public List getDetails(String id, String startDate, String endDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("select substr(CREATE_TIME,0,").append(startDate.length()).append("),to_char(sum(INSERT_AMOUNT)),to_char(sum(UPDATE_AMOUNT)),to_char(sum(DELET_AMOUNT)) from NPT_STS_GET_TOTAL\n" +
                "  WHERE substr(CREATE_TIME, 0, ").append(startDate.length()).append(") >= '").append(startDate).append("' AND substr(CREATE_TIME, 0, ").append(endDate.length()).append(") <= '").append(endDate).append("'\n" +
                "and TABLE_TYPE='").append(id).append("'\n" +
                "  GROUP BY substr(CREATE_TIME,0,").append(startDate.length()).append(")\n" +
                "ORDER BY substr(CREATE_TIME,0,").append(startDate.length()).append(")");
        return sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.TO_LIST).list();
    }

    @Override
    public List getRysxCount(String startDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("select to_char(org_id) org_id,org_nm,to_char(sub_id) sub_id,sub_name,to_char(code_value) code_value,code_name,sum(INSERT_AMOUNT) -sum(DELETE_AMOUNT) total_amount from npt_sTats_credit\n" +
                "where substr(CREATE_TIME,0,").append(startDate.length()).append(")<'").append(startDate).append("'\n" +
                "GROUP BY (org_id,org_nm,sub_id,sub_name,code_value,code_name)");
        return sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    @Override
    public List getRysxList(String startDate, String endDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("select to_char(org_id) org_id,org_nm,to_char(sub_id) sub_id,sub_name,to_char(code_value) code_value,code_name,sum(INSERT_AMOUNT) -sum(DELETE_AMOUNT) total_amount from npt_sTats_credit\n" +
                "where substr(CREATE_TIME,0,").append(startDate.length()).append(")>='").append(startDate).append("' and substr(CREATE_TIME,0,").append(endDate.length()).append(")<='").append(endDate).append("'\n" +
                "GROUP BY (org_id,org_nm,sub_id,sub_name,code_value,code_name)");
        return sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    @Override
    public List findBySql(String sql) {
        return sessionFactory.getCurrentSession().createSQLQuery(sql).list();
    }

    @Override
    public List<Map> getOrgName() {
        // where 条件
        StringBuilder sql = new StringBuilder();
        sql.append("select n.ORG_ID,n.ORG_NM from npt_stats_subject n group by n.ORG_ID,n.ORG_NM order by n.ORG_NM asc");
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
        List<Map> list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    @Override
    public List<Map> selectUnitNameList(Integer stsStypeId) {
        StringBuilder sb=new StringBuilder("");
        sb.append("select a.id as ORG_ID,a.org_name as ORG_NM from NPT_ORGANIZATION a where a.status=1");
        sb.append("and a.id in(");
        sb.append("select t.org_id from NPT_STS_DATA t where t.sts_type_id="+stsStypeId+" and t.status=1 group by t.org_id");
        sb.append(")group by a.id,a.org_name");
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sb.toString());
        List<Map> list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    @Override
    public List<Map> selectUnitResult(String sql) {
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List<Map> list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }
}

