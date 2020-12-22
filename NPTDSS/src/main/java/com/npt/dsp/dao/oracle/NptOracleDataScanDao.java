package com.npt.dsp.dao.oracle;

import com.npt.dsp.dao.NptDataScanDao;
import com.npt.rms.base.dao.NptHibernateDaoSupport;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/28 12:15
 * 备注：
 */
@Repository
public class NptOracleDataScanDao extends NptHibernateDaoSupport implements NptDataScanDao{


    @Override
    public List<Map<String, String>> loadDataTypeByOrg(String orgCode) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t.table_name, t.comments FROM user_tab_comments t ")
                .append("WHERE SUBSTR (T.TABLE_NAME, 0, 4) = 'NCE_' ")
                .append("AND SUBSTR(T.TABLE_NAME,INSTR(T.TABLE_NAME,'_',-1),LENGTH(T.TABLE_NAME)) = '_")
                .append(orgCode)
                .append("' AND T.TABLE_TYPE = 'TABLE' ");


        Session session = sessionFactory.getCurrentSession();

        List<Map<String,String>> result = session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

        return result;
    }

    @Override
    public List<Map<String, String>> loadDataField(String dataDBName) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t.column_name,t.comments,c.data_type,to_char(c.data_length) as data_length FROM user_col_comments t , user_tab_columns c ")
                .append(" WHERE     t.table_name = '")
                .append(dataDBName)
                .append("' AND t.table_name = c.table_name AND t.column_name = c.column_name");


        Session session = sessionFactory.getCurrentSession();

        List<Map<String,String>> result = session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

        return result;
    }

    @Override
    public void backupDataTypeAndFields() {


    }
}
