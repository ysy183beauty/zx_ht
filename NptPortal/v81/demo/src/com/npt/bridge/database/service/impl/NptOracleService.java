package com.npt.bridge.database.service.impl;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.database.NptSqlResultTransformer;
import com.npt.bridge.database.dao.NptDataBaseDao;
import com.npt.bridge.database.service.NptDataBaseService;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 12:05
 * 描述:
 */
@Service
@Transactional
public class NptOracleService implements NptDataBaseService {


    @Autowired
    private NptDataBaseDao dao;

    private String innerOrderby(Collection<NptLogicDataField> fields, String tbName, Map<String, String> where, Map<String, String> orderBy,Boolean exact) {

        StringBuilder sb = new StringBuilder();

        sb.append("(SELECT ");

        sb.append(NptCommonUtil.getFieldString(fields, ",", NptDict.CST_ENG_AS_CHN)).append(" FROM ").append(tbName);

        sb.append(makeConditionString(where, fields,exact));

        if (null != orderBy && !orderBy.isEmpty()) {
            String fieldName = orderBy.keySet().iterator().next();

            sb.append(" ORDER BY ").append(fieldName).append(" ").append(orderBy.get(fieldName));
        }

        sb.append(") nptTB ");

        return sb.toString();
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

    private String middleHead() {
        return "(SELECT ROWNUM AS " + NptDataBaseService.SQL_ROWNUM_ENGLISH + ",nptTB.* FROM ";
    }

    private String middleTail(int currentPage, int pageSize) {
        int limit = pageSize * currentPage;
        return " WHERE ROWNUM <= " + limit + ") nptLimit";
    }

    private String outSelect(Collection<NptLogicDataField> fields) {
        return "SELECT " + NptCommonUtil.getFieldString(fields, ",", NptDict.CST_ONLY_CHN) + " FROM ";
    }

    private String outWhere(int currentPage, int pageSize) {
        return " WHERE nptLimit." + NptDataBaseService.SQL_ROWNUM_ENGLISH + " >= " + ((currentPage - 1) * pageSize + 1);
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
        sbCount.append("SELECT COUNT(1) FROM ").append(innerOrderby(fields, dtName, condition, orderBy,exact));

        String[] result = new String[2];
        result[0] = sbCount.toString();
        result[1] = sb.toString();

        return result;
    }

    @Override
    public List<Object> queryList(String sql, Collection<NptLogicDataField> fields) {
        return dao.getList(sql, new NptSqlResultTransformer());
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

                if (false == exact) {
                    sb.append(noBlankColumnName).append(" LIKE '%").append(noBlankSearchValue).append("%'");
                } else{
                    sb.append(noBlankColumnName).append(" = '").append(noBlankSearchValue).append("'");
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

    @Override
    public String makeCountSql(String dtName, Collection<NptLogicDataField> fields, Map<String, String> condition) {
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT COUNT(1) FROM ").append(dtName);

        sb.append(makeConditionString(condition, null,true));

        return sb.toString();
    }

    /**
     * 作者：owen
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
    public String[] makeMultiLikeSql(String dtName,
                                   Collection<NptLogicDataField> fields,
                                   Map<String, String> orWhere,
                                   Integer currentPage,
                                   Integer pageSize,
                                   NptDict type) {
        String[] sql = new String[2];
        StringBuilder sbCount = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        sbCount.append("SELECT COUNT(1) FROM ").append(innerMultiLike(fields, dtName, orWhere, NptDict.CST_ENG_AS_CHN));

        sql[0] = sbCount.toString();
        if (null != currentPage && null != pageSize) {
            sb.append(outSelect(fields))
                    .append(middleHead())
                    .append(innerMultiLike(fields, dtName, orWhere, NptDict.CST_ENG_AS_CHN))
                    .append(middleTail(currentPage, pageSize))
                    .append(outWhere(currentPage, pageSize));
        } else {
            sb.append(commonMultiLike(fields, dtName, orWhere, type));
        }
        sql[1] = sb.toString();
        return sql;
    }

    @Override
    public String[] makeListDataSql(
            String dtName,
            Collection<NptLogicDataField> fields,
            Map<String, String> condition,
            Map<String,String> orderBy) {

        String[] sql = new String[2];
        StringBuilder sbCount = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        sbCount.append("SELECT COUNT(1) FROM ").append(innerOrderby(fields, dtName, condition, null,true));

        sql[0] = sbCount.toString();

        sb.append("SELECT * FROM(")
                .append(makeUniqueSql(dtName, fields, condition, NptDict.CST_ENG_AS_CHN));

                if(null != orderBy && NptCommonUtil.INTEGER_1.equals(orderBy.size())){
                    sb.append(" ORDER BY ").append(orderBy.keySet().iterator().next());
                    sb.append(" ").append(orderBy.values().iterator().next());
                }

                sb.append(")");

        sql[1] = sb.toString();

        return sql;
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
                .append(makeUniqueSql(dtName, fields, condition, NptDict.CST_ENG_AS_CHN));

        if (!StringUtils.isBlank(orderBy)) {
            sb.append(" ORDER BY ").append(orderBy);
        }

        sb.append(")");

        if (null != count) {
            sb.append("WHERE ROWNUM <= ").append(String.valueOf(count));
        }

        sql[1] = sb.toString();

        return sql;
    }

    @Override
    public Object queryUnique(String sql) {
        List<Object> result = dao.getList(sql, new NptSqlResultTransformer());
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
     * 作者: 张磊
     * 日期: 2017/03/26 下午05:54
     * 备注: 执行sql
     */
    @Override
    public void execute(String sql) {
        dao.update(sql);
    }


    @Override
    public Boolean updateData(String dtName, Collection<NptLogicDataField> fields, Map<String, String> changeInfo, Map<String, String> condition) {
        Boolean flag = true;
        StringBuilder sb = new StringBuilder();

        Map<String, NptLogicDataField> namedMap = new HashMap<String, NptLogicDataField>();
        for (NptLogicDataField f : fields) {
            namedMap.put(f.getFieldDbName(), f);
        }

        if (null != changeInfo && changeInfo.size() > 0) {
            StringBuilder set = new StringBuilder();
            set.append(" SET ");
            Set<String> keys = changeInfo.keySet();
            for (String key : keys) {
                NptLogicDataField thisField = namedMap.get(key);
                if (null != thisField) {
                    String columnValue = String.valueOf(changeInfo.get(key));
                    set.append(key).append(" = ");
                    if ("DATE".equals(thisField.getFieldDbType())) {
                        set.append("to_date('").append(columnValue).append("','yyyy-mm-dd hh24:mi:ss')");
                    } else {
                        set.append("'").append(columnValue).append("'");
                    }
                    set.append(",");
                }
            }
            set.deleteCharAt(set.length() - 1);
            sb.append(" UPDATE ").append(dtName)
                    .append(set)
                    .append(makeConditionString(condition, null,true));
            flag = dao.update(sb.toString());

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
        Collection<NptLogicDataField> unCoveredField = new ArrayList<NptLogicDataField>();
        String sql = "SELECT COLUMN_NAME FROM user_tab_columns WHERE TABLE_NAME='" + dtName + "'";
        List result = dao.getList(sql, null);
        if (null != fields && null != result && result.size() > 0) {
            Collection<String> columnNames = result;
            for (NptLogicDataField field : fields) {
                if (!columnNames.contains(field.getFieldDbName())) {
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
    public Boolean createTable(String dtName, Collection<NptLogicDataField> fields) {
        if (null != dtName && !dtName.isEmpty() && null != fields && !fields.isEmpty()) {
            StringBuilder sb = new StringBuilder("create table ").append(dtName).append(" (");
            for (NptLogicDataField field : fields) {
                sb.append(field.getFieldDbName()).append(" ");
                if (-1 != field.getFieldDbType().indexOf("CHAR")) {
                    sb.append(field.getFieldDbType()).append(" (").append(field.getFieldDbLen()).append(")");
                } else {
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
        if (null != dtName && !dtName.isEmpty() && null != fields && !fields.isEmpty()) {
            StringBuilder sb = new StringBuilder("alter table " + dtName);
            for (NptLogicDataField field : fields) {
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
     * 作者：owen
     * 时间：2016/12/13 21:14
     * 描述:
     * 获取控制实体数据的保留字段
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

        Collection<NptLogicDataField> revList = new ArrayList<NptLogicDataField>();
        revList.add(createUser);
        revList.add(createTime);
        revList.add(modifyUser);
        revList.add(modifyTime);
        revList.add(status);

        return revList;
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
        if (null != dtName && !dtName.isEmpty() && null != keyValue && !keyValue.isEmpty() && null != fields && !fields.isEmpty()) {
            StringBuilder sb = new StringBuilder("insert into ").append(dtName);
            StringBuilder columns = new StringBuilder("(");
            StringBuilder values = new StringBuilder(" values(");

            Map<String, NptLogicDataField> namedMap = new HashMap<String, NptLogicDataField>();
            for (NptLogicDataField f : fields) {
                namedMap.put(f.getFieldDbName(), f);
            }

            Set<String> names = keyValue.keySet();
            for (String n : names) {
                NptLogicDataField thisField = namedMap.get(n);
                if (null != thisField) {
                    columns.append(n);
                    if ("DATE".equals(thisField.getFieldDbType())) {
                        values.append("to_date('").append(String.valueOf(keyValue.get(n))).append("','yyyy-mm-dd hh24:mi:ss')");
                    } else {
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

    /**
     * author: owen
     * date:   2017/4/17 下午3:00
     * note:
     * 获取逻辑数据表的总记录数
     *
     * @param dataType
     */
    @Override
    public Long qureyTableRows(NptLogicDataType dataType) {

        Long result = 0L;

        if(null != dataType && !StringUtils.isBlank(dataType.getTypeDbName())) {
            String sql = "select num_rows from user_tables where table_name = '" + dataType.getTypeDbName() + "'";

            result = dao.getLongCount(sql);
        }
        return result;
    }
}
