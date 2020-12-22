package com.npt.grs.query.globalSearch.manager.impl;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.database.manager.NptDatabaseManager;
import com.npt.bridge.dict.NptDict;
import com.npt.grs.query.globalSearch.manager.NptGlobalSearchManager;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.rms.auth.bean.NptGlobalSearchBean;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.orm.condition.Conditions;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/11/2 20:16
 * 备注：
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NptGlobalSearchManagerImpl implements NptGlobalSearchManager {

    @Resource(name = "nptDssOracleManager")
    private NptDatabaseManager databaseManager;

    @Resource(name = "rmsArchService")
    private NptRmsArchService rmsArchService;

    /**
     * 作者：owen
     * 日期：2016/11/2 20:18
     * 备注：
     * 依据模糊字段fromField及其值pkValue模糊查询所有字段名跟toField匹配的字段及其业务值
     * 参数：
     * 返回：
     *
     * @param pkValue
     * @param fromField
     * @param toField
     */
    @Override
    public Collection<NptGlobalSearchBean> search(String pkValue, String fromField, String toField) {

        List<NptGlobalSearchBean> beanList = new LinkedList<>();

        //定位所有同时模糊具有两标题的表
        List<Object> tableInfo = databaseManager.locateFuzzyQueryTables(fromField, toField);
        if (null != tableInfo) {
            for (Object obj : tableInfo) {

                try {
                    Map<String, String> keyValue = (Map<String, String>) obj;
                    NptGlobalSearchBean bean = new NptGlobalSearchBean();

                    Object id = keyValue.get(NptBaseEntity.PROPERTY_ID.toUpperCase());
                    Object orgId = keyValue.get(NptBaseEntity.PROPERTY_PARENT_ID.toUpperCase());

                    bean.setDataTypeId(((BigDecimal) id).longValue());
                    bean.setDataTypeTitle(keyValue.get(NptBaseEntity.PROPERTY_ALIAS.toUpperCase()));
                    String typeDbName = keyValue.get(NptLogicDataType.PROPERTY_DATA_TYPE_DBNAME.toUpperCase());

                    NptLogicDataProvider org = rmsArchService.findOrgById(((BigDecimal) orgId).longValue());
                    if (null != org) {
                        bean.setOrgTitle(org.getOrgName());
                    }


                    //查询所有模糊匹配主键标题的字段
                    Collection<NptLogicDataField> pkFieldList = rmsArchService.listDataFieldByConditions(
                            Conditions.eq(NptBaseEntity.PROPERTY_PARENT_ID, bean.getDataTypeId()),
                            Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()),
                            Conditions.like(NptBaseEntity.PROPERTY_ALIAS, fromField));
                    //查询所有模糊匹配目标标题的字段
                    Collection<NptLogicDataField> aimFieldList = rmsArchService.listDataFieldByConditions(
                            Conditions.eq(NptBaseEntity.PROPERTY_PARENT_ID, bean.getDataTypeId()),
                            Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()),
                            Conditions.like(NptBaseEntity.PROPERTY_ALIAS, toField));

                    List<NptGlobalSearchBean.NptSearchDataList> searchDataLists = new ArrayList<>();

                    if (null != pkFieldList && null != aimFieldList) {
                        for (NptLogicDataField pk : pkFieldList) {

                            NptGlobalSearchBean.NptSearchDataList searchDataList = bean.newNptSearchDataList();
                            List<NptLogicDataField> selectFieldList = new ArrayList<>();
                            selectFieldList.add(pk);
                            selectFieldList.addAll(aimFieldList);

                            Map<String, String> condition = new HashMap<>();
                            condition.put(pk.getFieldDbName(), pkValue);

                            String sql = databaseManager.makeLikeSql(typeDbName, selectFieldList, condition, NptDict.CST_ENG_AS_CHN);
                            Object result = databaseManager.queryList(sql, selectFieldList);
                            if (null != result) {
                                List<Object> resultList = (List<Object>) result;
                                searchDataList.setPkFieldId(pk.getId());
                                searchDataList.setDataList(resultList);
                                searchDataList.setSearchCount(resultList.size());
                            }
                            searchDataLists.add(searchDataList);
                        }
                        bean.setSearchDataList(searchDataLists);
                    }
                    beanList.add(bean);
                } catch (Exception e) {
                    continue;
                }
            }
        }
        Collections.sort(beanList);
        return beanList;
    }

    /**
     * 作者：97175
     * 日期：2016/11/7 20:45
     * 备注：
     * <p>
     * 参数：
     * 返回：
     *
     * @param type
     * @param byField
     * @param pkValue
     */
    @Override
    public Object queryUniqueData(NptLogicDataType type, NptLogicDataField byField, String pkValue) {
        if (null != type && null != byField) {
            Collection<NptLogicDataField> selectFields = rmsArchService.listDataField(type.getId(), null, NptDict.IDS_ENABLED);
            if (null != selectFields && selectFields.size() > 0) {
                Map<String, String> condition = new HashedMap();
                condition.put(byField.getFieldDbName(), pkValue);

                String sql =
                        databaseManager.makeUniqueSql(type.getTypeDbName(), selectFields, condition, NptDict.CST_ONLY_ENG);

                return databaseManager.queryUnique(sql);
            }
        }
        return null;
    }
}
