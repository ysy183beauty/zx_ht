package com.npt.database.manager;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.database.bean.NameTitleType;
import com.npt.bridge.database.dao.NptDataBaseDao;
import com.npt.bridge.database.dao.NptMapSqlResultTransformerNoBlank;
import com.npt.bridge.database.dao.NptMapSqlResultTransformerWithBlank;
import com.npt.bridge.database.manager.NptDatabaseManager;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/28 11:46
 * 备注：
 */
@Service
@Transactional
public class NptOracleManager implements NptDatabaseManager {


    @Autowired
    private NptDataBaseDao dao;

    private String innerOrderby(Collection<NptLogicDataField> fields, String tbName, Map<String, String> where, Map<String, String> orderBy,Boolean exact) {

        StringBuilder sb = new StringBuilder();

        sb.append("(SELECT ");

        sb.append(NptCommonUtil.getFieldString(fields, ",", NptDict.CST_ENG_AS_CHN)).append(" FROM ").append(tbName);

        sb.append(makeConditionString(where, fields,exact));

        if (null != orderBy && !orderBy.isEmpty()) {
            String fieldName = orderBy.keySet().iterator().next();

            sb.append(" ORDER BY ").append(fieldName).append(" ").append(orderBy.get(fieldName)).append(" NULLS LAST, ROWNUM ASC");
        }

        sb.append(") nptTB ");

        return sb.toString();
    }

    private String middleHead() {
        return "(SELECT ROWNUM AS " + NptDatabaseManager.SQL_ROWNUM_ENGLISH + ",nptTB.* FROM ";
    }

    private String middleTail(int currentPage, int pageSize) {
        int limit = pageSize * currentPage;
        return " WHERE ROWNUM <= " + limit + ") nptLimit";
    }

    private String outSelect(Collection<NptLogicDataField> fields) {
        return "SELECT " + NptCommonUtil.getFieldString(fields, ",", NptDict.CST_ONLY_CHN) + " FROM ";
    }

    private String outWhere(int currentPage, int pageSize) {
        return " WHERE nptLimit." + NptDatabaseManager.SQL_ROWNUM_ENGLISH + " >= " + ((currentPage - 1) * pageSize + 1);
    }

    private String innerMultiLike(Collection<NptLogicDataField> fields,String dtName,Map<String,String> orWhere,NptDict type){
        StringBuilder sb = new StringBuilder();

        sb.append("(SELECT ");
        sb.append(NptCommonUtil.getFieldString(fields,",",type));
        sb.append(" FROM ");
        sb.append(dtName);

        sb.append(orLike(orWhere));

        sb.append(") nptTB");

        return sb.toString();
    }

    private String orLike(Map<String,String> orWhere){
        StringBuilder sb = new StringBuilder();
        if(null != orWhere){
            sb.append(" WHERE ");

            Set<String> cnSet = orWhere.keySet();
            Iterator<String> iterator = cnSet.iterator();
            while (iterator.hasNext()){

                String c = iterator.next();
                String noBlankColumnName = " replace(trim(" + c + "),' ','') ";
                String noBlankSearchValue = StringUtils.deleteWhitespace(orWhere.get(c));

                sb.append(noBlankColumnName).append(" LIKE '%").append(noBlankSearchValue).append("%' ");

                if(iterator.hasNext()){
                    sb.append(" OR ");
                }
            }
        }
        return sb.toString();
    }

    private String commonMultiLike(Collection<NptLogicDataField> fields,String dtName,Map<String,String> orWhere,NptDict type){
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT ");
        sb.append(NptCommonUtil.getFieldString(fields,",",type));
        sb.append(" FROM ");
        sb.append(dtName);

        sb.append(orLike(orWhere));

        return sb.toString();
    }

    @Override
    public String[] makePaginationSql(
            String dtName,
            Collection<NptLogicDataField> fields,
            int currentPage,
            int pageSize,
            Map<String, String> condition,
            Map<String, String> orderBy,
            Boolean exact) {

        if (null == fields || fields.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        sb.append(outSelect(fields))
                .append(middleHead())
                .append(innerOrderby(fields, dtName, condition, orderBy,exact))
                .append(middleTail(currentPage, pageSize))
                .append(outWhere(currentPage, pageSize));

        StringBuilder sbCount = new StringBuilder();
        sbCount.append("select count(1) from ")
                .append(dtName)
                .append(makeConditionString(condition, fields,exact));

        String[] result = new String[2];
        result[0] = sbCount.toString();
        result[1] = sb.toString();

        return result;
    }

    @Override
    public String[] makeDistinctSql(String dtName, NptLogicDataField field, String where) {
        return new String[0];
    }

    /**
     * author: owen
     * date:   2017/3/23 下午2:29
     * note:
     * 多字段或关系的模糊查询
     *
     * @param dtName
     * @param fields
     * @param orWhere
     * @param type
     */
    @Override
    public String makeMultiLikeSql(String dtName,
                                   Collection<NptLogicDataField> fields,
                                   Map<String, String> orWhere,
                                   Integer currentPage,
                                   Integer pageSize,
                                   NptDict type) {

        StringBuilder sb = new StringBuilder();

        if(null != currentPage && null != pageSize) {
            sb.append(outSelect(fields))
                    .append(middleHead())
                    .append(innerMultiLike(fields, dtName, orWhere, NptDict.CST_ENG_AS_CHN))
                    .append(middleTail(currentPage, pageSize))
                    .append(outWhere(currentPage, pageSize));
        }else {
            sb.append(commonMultiLike(fields,dtName,orWhere,type));
        }

        return sb.toString();
    }

    @Override
    public List<Object> queryList(String sql, Collection<NptLogicDataField> fields) {

        NptMapSqlResultTransformerNoBlank<NptLogicDataField> transformer = new NptMapSqlResultTransformerNoBlank<>();

        return dao.getList(sql, transformer);
    }

    private String makeConditionString(Map<String, String> where, Collection<NptLogicDataField> fields,Boolean exact) {

        StringBuilder sb = new StringBuilder(" ");

        if (null != where && !where.isEmpty()) {
            sb.append(" WHERE ");

            Set<String> keys = where.keySet();
            int idx = 0;
            for (String key : keys) {
                idx++;
                String columnValue = where.get(key);

                String noBlankColumnName = " replace(trim(" + key + "),' ','') ";
                String noBlankSearchValue = StringUtils.deleteWhitespace(columnValue);

                if (true == exact) {
                    sb.append(noBlankColumnName).append(" = '").append(noBlankSearchValue).append("'");
                } else{
                    sb.append(noBlankColumnName).append(" LIKE '%").append(noBlankSearchValue).append("%'");
                }
                if (idx < keys.size()) {
                    sb.append(" AND ");
                }
            }
        }
        return sb.toString();
    }

    @Override
    public String makeUniqueSql(String dtName, Collection<NptLogicDataField> fields, Map<String, String> where, NptDict type) {
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT ");

        sb.append(NptCommonUtil.getFieldString(fields, ",", type)).append(" FROM ").append(dtName);

        sb.append(makeConditionString(where, null,true));

        return sb.toString();
    }

    /**
     * 作者：97175
     * 日期：2016/11/7 22:04
     * 备注：
     * 模糊查询
     * 参数：
     * 返回：
     *
     * @param dtName
     * @param fields
     * @param condition
     * @param type
     */
    @Override
    public String makeLikeSql(String dtName, Collection<NptLogicDataField> fields, Map<String, String> condition, NptDict type) {
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT ");

        sb.append(NptCommonUtil.getFieldString(fields, ",", type)).append(" FROM ").append(dtName);

        sb.append(makeConditionString(condition, fields,false));

        return sb.toString();
    }

    @Override
    public String[] makeLastedDataSql(String dtName,
                                      Collection<NptLogicDataField> fields,
                                      Map<String, String> condition,
                                      String orderBy,
                                      Integer count) {
        String[] sql = new String[2];
        StringBuilder sbCount = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        sbCount.append("SELECT COUNT(1) FROM ").append(innerOrderby(fields, dtName, condition, null,true));

        sql[0] = sbCount.toString();

        sb.append("SELECT * FROM(")
                .append(makeUniqueSql(dtName, fields, condition, NptDict.CST_ENG_AS_CHN))
                .append(" ORDER BY ")
                .append(orderBy)
                .append(")")
                .append("WHERE ROWNUM <= ")
                .append(String.valueOf(count));
        sql[1] = sb.toString();

        return sql;
    }
    @Override
    public String[] makeAllDataSql(String dtName, Collection<NptLogicDataField> fields, Map<String, String> condition, String orderBy, Integer count) {
        String[] sql = new String[2];
        StringBuilder sbCount = new StringBuilder();
        StringBuilder sb = new StringBuilder();


        sbCount.append("SELECT COUNT(1) FROM ").append(innerOrderby(fields, dtName, condition, null,true));

        if(null != count && count > 0){
            sb.append("SELECT * FROM(");
        }

        sb.append(makeUniqueSql(dtName,fields,condition, NptDict.CST_ENG_AS_CHN));

        if(null != orderBy && !StringUtils.isEmpty(orderBy)){
            sb.append(" ORDER BY ").append(orderBy);
        }

        if(null != count && count > 0){
            sb.append(") WHERE ROWNUM <= ").append(String.valueOf(count));
        }

        return sql;
    }
    @Override
    public Object queryUnique(String sql) {

        NptMapSqlResultTransformerNoBlank<NptLogicDataField> transformer =
                new NptMapSqlResultTransformerNoBlank<>();

        List<Object> result = dao.getList(sql, transformer);
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    @Override
    public int getCount(String sql) {
        return dao.getCount(sql);
    }

    @Override
    public DB_TYPE checkDBType() {
        return DB_TYPE.ORACLE;
    }

    /**
     *  author: zhanglei
     *  date:   2017/05/24 17:03
     *  note:
     *
     */
    @Override
    public Long getRowCount(NptLogicDataType dataType, NptLogicDataField dataField, String pkValue) {
//        assert null != dataType && null != dataField && null != pkValue;
        if (null != dataType && null != dataField && null != pkValue) {
            String sql = "select count(1) from " + dataType.getTypeDbName() + " where " + dataField.getFieldDbName() + " ='" + pkValue + "'";
            return dao.getLongCount(sql);
        } else {
            return 0L;
        }
    }

    /**
     * 作者：owen
     * 日期：2016/11/2 19:57
     * 备注：
     * 模糊查询中首先定位目标表
     * 参数：
     * 返回：
     */
    @Override
    public List<Object> locateFuzzyQueryTables(String pkLike, String aimLike) {
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT distinct T.id AS ").append(NptBaseEntity.PROPERTY_ID)
                .append(",t.obj_alias AS ").append(NptBaseEntity.PROPERTY_ALIAS)
                .append(",t.parent_id AS ").append(NptBaseEntity.PROPERTY_PARENT_ID)
                .append(",t.data_type_dbname AS ").append(NptLogicDataType.PROPERTY_DATA_TYPE_DBNAME)
                .append(" FROM npt_data_type t,")
                .append("(SELECT p.parent_id FROM npt_data_field p ")
                .append(" WHERE P.OBJ_ALIAS LIKE '%")
                .append(pkLike).append("%'")
                .append(" INTERSECT ")
                .append(" SELECT f.parent_id FROM npt_data_field f ")
                .append(" WHERE f.OBJ_ALIAS LIKE '%")
                .append(aimLike).append("%') x ")
                .append(" WHERE t.id = x.parent_id");

        return dao.getList(sb.toString(), new NptMapSqlResultTransformerNoBlank<>());
    }

    @Override
    public Boolean updateData(String dtName, Collection<NptLogicDataField> fields, Map<String, String> changeInfo, Map<String, String> condition) {
        Boolean flag = true;
        StringBuilder sb = new StringBuilder();

        Map<String,NptLogicDataField> namedMap = new HashMap<>();
        for(NptLogicDataField f:fields){
            namedMap.put(f.getFieldDbName(),f);
        }

        if (null != changeInfo && changeInfo.size() > 0) {
            StringBuilder set = new StringBuilder();
            set.append(" SET ");
            Set<String> keys = changeInfo.keySet();
            for (String key : keys) {
                NptLogicDataField thisField = namedMap.get(key);
                if(null != thisField){
                    String columnValue = String.valueOf(changeInfo.get(key));
                    set.append(key).append(" = ");
                    if("DATE".equals(thisField.getFieldDbType())){
                        set.append("to_date('").append(columnValue).append("','yyyy-mm-dd hh24:mi:ss)");
                    }else {
                        set.append("'").append(columnValue).append("'");
                    }
                    set.append(",");
                }
            }
            set.deleteCharAt(set.length() - 1);
            sb.append(" UPDATE ").append(dtName)
                    .append(set)
                    .append(makeConditionString(condition, null,true));
          flag =  dao.update(sb.toString());

        }
        return flag;
    }

    /**
     * 作者：owen
     * 时间：2016/12/13 15:25
     * 描述:
     * 检测表是否存在
     *
     * @param dtName
     */
    @Override
    public Boolean isTableExisted(String dtName) {
        String sql = "select count(1) from tabs t where t.table_name ='" + dtName + "'";

        int count = dao.getCount(sql);

        return count > 0;
    }

    /**
     * 作者：owen
     * 时间：2016/12/13 15:29
     * 描述:
     * 检测表是否已完全包含指定的字段名称
     *
     * @param dtName
     * @param fields
     */
    @Override
    public Collection<NptLogicDataField> isTableCoverFields(String dtName, Collection<NptLogicDataField> fields) {
        Collection<NptLogicDataField> unCoveredField = new ArrayList<>();
        String sql = "SELECT COLUMN_NAME FROM user_tab_columns WHERE TABLE_NAME='" + dtName + "'";
        List result = dao.getList(sql,null);
        if(null != fields && null != result && result.size() > 0){
            Collection<String> columnNames = result;
            for(NptLogicDataField field:fields){
                if(!columnNames.contains(field.getFieldDbName())){
                    unCoveredField.add(field);
                }
            }
        }
        return unCoveredField;
    }

    /**
     * 作者：owen
     * 时间：2016/12/13 15:37
     * 描述:
     * 创建表并创建业务主键创建索引
     *
     * @param dtName
     * @param fields
     */
    @Override
    public Boolean createTable(String dtName, Collection<NptLogicDataField> fields ) {
        if(null != dtName && !dtName.isEmpty() && null != fields && !fields.isEmpty()){
            StringBuilder sb = new StringBuilder("create table ").append(dtName).append(" (");
            for(NptLogicDataField field:fields){
                sb.append(field.getFieldDbName()).append(" ");
                if(-1 != field.getFieldDbType().indexOf("VARCHAR")){
                    sb.append(field.getFieldDbType()).append(" (").append(field.getFieldDbLen()).append(")");
                }else {
                    sb.append(field.getFieldDbType());
                }
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");

            return dao.update(sb.toString());
        }
        return true;
    }

    /**
     * 作者：owen
     * 时间：2016/12/13 17:31
     * 描述:
     * 为表添加字段
     *
     * @param dtName
     * @param fields
     */
    @Override
    public Boolean addColumn(String dtName, Collection<NptLogicDataField> fields) {
        if(null != dtName && !dtName.isEmpty() && null != fields && !fields.isEmpty()) {
            StringBuilder sb = new StringBuilder("alter table " + dtName);
            for(NptLogicDataField field:fields){
                sb.append(" add " + field.getFieldDbName() + " " + field.getFieldDbType() + "(" + field.getFieldDbLen() + ") ");
            }
            return dao.update(sb.toString());
        }
        return true;
    }

    /**
     * 作者：owen
     * 时间：2016/12/13 15:38
     * 描述:
     * 删除表及索引
     *
     * @param dtName
     * @param pkName
     */
    @Override
    public Boolean dropTable(String dtName, String pkName) {
        return null;
    }

    public static final String RF_CREATE_TIME = "NCF_CREATE_TIME";
    public static final String RF_CREATE_USER = "NCF_CREATE_USER";
    public static final String RF_MODIFY_TIME = "NCF_MODIFY_TIME";
    public static final String RF_MODIFY_USER = "NCF_MODIFY_USER";
    public static final String RF_STATUS = "NCF_STATUS";


    /**
     *作者：owen
     *时间：2016/12/13 21:14
     *描述:
     *      获取控制实体数据的保留字段
     */
    @Override
    public Collection<NptLogicDataField> getReservedDataField() {
        NptLogicDataField createTime = new NptLogicDataField();
        createTime.setFieldDbName(RF_CREATE_TIME);
        createTime.setFieldDbType("DATE");
        createTime.setAlias("创建时间");

        NptLogicDataField createUser = new NptLogicDataField();
        createUser.setFieldDbName(RF_CREATE_USER);
        createUser.setFieldDbType("LONG");
        createUser.setAlias("创建人");

        NptLogicDataField modifyTime = new NptLogicDataField();
        modifyTime.setFieldDbName(RF_MODIFY_TIME);
        modifyTime.setFieldDbType("TIMESTAMP");
        modifyTime.setAlias("更新时间");

        NptLogicDataField modifyUser = new NptLogicDataField();
        modifyUser.setFieldDbName(RF_MODIFY_USER);
        modifyUser.setFieldDbType("LONG");
        modifyUser.setAlias("更新人");

        NptLogicDataField status = new NptLogicDataField();
        status.setFieldDbName(RF_STATUS);
        status.setFieldDbType("INTEGER");
        status.setAlias("数据状态");

        Collection<NptLogicDataField> revList = new ArrayList<>();
        revList.add(createUser);
        revList.add(createTime);
        revList.add(modifyUser);
        revList.add(modifyTime);
        revList.add(status);

        return revList;
    }

    /**
     *作者：owen
     *时间：2016/12/13 21:14
     *描述:
     *      字段扫描时确认指定表是否已添加了保留字段
     *
     *      若未添加保留字段,则不配置之
     */
    @Override
    public Boolean containReservedField(Collection<NameTitleType> c) {
        if(null != c){
            Collection<String> revNames = new ArrayList<>();
            for(NameTitleType ntt:c){
                String name = ntt.name;
                int speIdx = name.indexOf("@");
                revNames.add(name.substring(speIdx + 1));
            }

            if(revNames.contains(RF_CREATE_TIME)
                    && revNames.contains(RF_CREATE_USER)
                    && revNames.contains(RF_MODIFY_TIME)
                    && revNames.contains(RF_MODIFY_USER)
                    && revNames.contains(RF_STATUS)){
                return true;
            }
        }
        return false;
    }

    /**
     * 作者：owen
     * 时间：2016/12/14 12:22
     * 描述:
     * 获取指定表指定字段的增量非锁定数据列表
     *
     * @param dtName
     * @param fields
     */
    @Override
    public List getIncrementData(String dtName, Collection<NptLogicDataField> fields, Timestamp last, Timestamp max, Integer start, Integer end, Boolean transform) {
        if(null == dtName || dtName.isEmpty() || null == fields || fields.isEmpty()){
            return null;
        }
        StringBuilder sbPage = new StringBuilder();
        StringBuilder sb = new StringBuilder("select ");
        Iterator<NptLogicDataField> iterator = fields.iterator();
        while (iterator.hasNext()){
            NptLogicDataField field = iterator.next();
            if("DATE".equals(field.getFieldDbType())){
                sb.append("to_char(").append(field.getFieldDbName()).append(",'yyyy-mm-dd hh24:mi:ss') as ").append(field.getFieldDbName());
            }else {
                sb.append(field.getFieldDbName());
            }
            sb.append(",");
        }
        sb.append("rownum as npt_rowno");
        sb.append(" from ").append(dtName);
        sb.append(" where ").append(RF_STATUS).append(" <> ").append(NptDict.IDS_LOCKED.getCode());
        if(null != last) {
            sb.append(" and ").append(RF_MODIFY_TIME).append(" >= to_timestamp('").append(last).append("','yyyy-mm-dd hh24:mi:ss:ff6')");
        }
        if(null != max) {
            sb.append(" and ").append(RF_MODIFY_TIME).append(" < to_timestamp('").append(max).append("','yyyy-mm-dd hh24:mi:ss:ff6')");
        }
        if(null != start && null != end){
            sbPage.append("select * from (").append(sb.toString()).append(" and rownum < ").append(end);
            sbPage.append(") where npt_rowno >= ").append(start);
        }else {
            sbPage.append(sb.toString());
        }

        if(transform){
            return dao.getList(sbPage.toString(),new NptMapSqlResultTransformerNoBlank<>());
        }else {
            return dao.getList(sbPage.toString(),new NptMapSqlResultTransformerWithBlank());
        }
    }

    /**
     * 作者：owen
     * 时间：2016/12/14 16:23
     * 描述:
     * 向指定的表中插入一行记录
     *
     * @param dtName
     * @param keyValue
     */
    @Override
    public Boolean insertRow(String dtName, Collection<NptLogicDataField> fields, Map<String, String> keyValue) {
        if(null != dtName && !dtName.isEmpty() && null != keyValue && !keyValue.isEmpty() && null != fields && !fields.isEmpty()){
            StringBuilder sb = new StringBuilder("insert into ").append(dtName);
            StringBuilder columns = new StringBuilder("(");
            StringBuilder values = new StringBuilder(" values(");

            Map<String,NptLogicDataField> namedMap = new HashMap<>();
            for(NptLogicDataField f:fields){
                namedMap.put(f.getFieldDbName(),f);
            }

            Set<String> names = keyValue.keySet();
            for(String n:names){
                NptLogicDataField thisField = namedMap.get(n);
                if(null != thisField) {
                    columns.append(n);
                    if ("DATE".equals(thisField.getFieldDbType())) {
                        values.append("to_date('").append(String.valueOf(keyValue.get(n))).append("','yyyy-mm-dd hh24:mi:ss')");
                    } else{
                        values.append("'").append(String.valueOf(keyValue.get(n))).append("'");
                    }
                    columns.append(",");
                    values.append(",");
                }
            }
            columns.deleteCharAt(columns.length() - 1);
            values.deleteCharAt(values.length() - 1);
            columns.append(")");
            values.append(")");

            sb.append(columns.toString()).append(values.toString());

            return dao.update(sb.toString());
        }
        return true;
    }


    public void createRefreshTableIndex(Map<String, List<String>> tfMap) {

        String INDEX_NAME = "IDX_NAME";
        String TABLE_NAME = "TB_NAME";
        String COLUMN_NAME = "CLM_NAME";
        String INDEX_TYPE = "IDX_TYPE";



        StringBuffer sb = new StringBuffer();

        sb.append("SELECT t.index_name AS ").append(INDEX_NAME).append(",")
                .append("t.table_name AS ").append(TABLE_NAME).append(",")
                .append("t.column_name AS ").append(COLUMN_NAME).append(",")
                .append("i.index_type AS ").append(INDEX_TYPE)
                .append(" FROM user_ind_columns t, user_indexes i ")
                .append(" WHERE t.index_name = i.index_name ")
                .append(" AND t.table_name = ");

        ResultTransformer transformer = new NptMapSqlResultTransformerWithBlank();

        if(null != tfMap && !tfMap.isEmpty()){

            /**
             *  过滤指定表已创建的索引
             */
            Set<String> tbNames = tfMap.keySet();
            for(String tbn:tbNames){
                String sql = sb.toString() + "'" + tbn + "'";

                List<Object> result = dao.getList(sql,transformer);
                if(null != result || !result.isEmpty()){
                    for(Object rr:result){
                        Map<String,Object> row = (Map<String, Object>) rr;
                        String createdCLMN = String.valueOf(row.get(COLUMN_NAME));

                        tfMap.get(tbn).remove(createdCLMN);
                    }
                }
            }

            /**
             *  对已过滤字段的表依次创建索引
             */

            for (String tbn : tbNames) {
                List<String> clms = tfMap.get(tbn);
                if (null != clms && !clms.isEmpty()) {
                    for (String cn : clms) {
                        Map<String,Object> idxNo = (Map<String, Object>) queryUnique("select SEQ_CREDIT_INDEX_NO.nextval as NNO from dual");
                        if (null != idxNo) {

                            try {
                            String idxName = "NPTOI_" + String.valueOf(idxNo.get("NNO"));
                            String createSql = "CREATE INDEX " + idxName + " ON " + tbn + "(" + cn + ")";

                            dao.update(createSql);

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }


        }
    }
}

