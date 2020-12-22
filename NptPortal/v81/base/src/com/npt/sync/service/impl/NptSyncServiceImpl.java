package com.npt.sync.service.impl;

import com.npt.arch.entity.NptLogicDataField;
import com.npt.arch.entity.NptLogicDataProvider;
import com.npt.arch.entity.NptLogicDataTable;
import com.npt.arch.service.NptArchService;
import com.npt.database.service.NptDataBaseService;
import com.npt.dict.NptDict;
import com.npt.model.bean.NptBaseModelIncData;
import com.npt.model.bean.NptBaseModelStructure;
import com.npt.model.entity.NptBaseModel;
import com.npt.model.entity.NptBaseModelGroup;
import com.npt.model.entity.NptBaseModelMainField;
import com.npt.model.entity.NptBaseModelPool;
import com.npt.model.service.NptModelService;
import com.npt.sync.service.NptSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 12:01
 * 描述:
 */
@Service
@Transactional
public class NptSyncServiceImpl implements NptSyncService{

    @Autowired
    private NptArchService archService;
    @Autowired
    private NptModelService modelService;
    @Autowired
    private NptDataBaseService dataBaseService;



    /**
     * 作者：OWEN
     * 时间：2016/12/1 22:19
     * 描述:
     * 同步组织机构信息
     *
     * @param providers
     */
    @Override
    public NptDict syncDataProvider(Collection<NptLogicDataProvider> providers) {
        if(null != providers){
            Collection<NptLogicDataProvider> updateList = new ArrayList<NptLogicDataProvider>();
            Collection<NptLogicDataProvider> insertList = new ArrayList<NptLogicDataProvider>();
            for(NptLogicDataProvider pro:providers){
                NptLogicDataProvider existedOrg = archService.findProviderById(pro.getId());
                if(null != existedOrg){
                    pro.copyTo(existedOrg);
                    updateList.add(existedOrg);
                }else {
                    insertList.add(pro);
                }
            }
            archService.batchSaveProvider(insertList);
            archService.batchUpdateProvider(updateList);
            return NptDict.RST_SUCCESS;
        }
        return NptDict.RST_INVALID_PARAMS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/12/1 22:19
     * 描述:
     * 同步基础模型
     *
     * @param structure
     */
    @Override
    public NptDict syncBaseModelStructure(NptBaseModelStructure structure) {
        if(null == structure) {
            return NptDict.RST_EXCEPTION("传递的数据不可识别！");
        }

        NptDict beforeResult = beforeSyncBaseModel(structure);
        if(!NptDict.RST_SUCCESS.equals(beforeResult)){
            return beforeResult;
        }

        NptDict cleanResult = cleanOldModel(structure.getModel());
        if(!NptDict.RST_SUCCESS.equals(cleanResult)){
            return cleanResult;
        }

        return storeBaseModel(structure);
    }

    /**
     * 作者：owen
     * 时间：2016/12/14 14:43
     * 描述:
     * 同步基础模型的增量数据
     *
     * @param poolData
     */
    @Override
    public NptDict syncBaseModelData(NptBaseModelIncData poolData) {
        if(null == poolData) {
            return NptDict.RST_EXCEPTION("传递的数据不可识别！");
        }
        return syncPoolData(poolData);
    }




    /**
     *作者：owen
     *时间：2016/12/14 15:01
     *描述:
     *      同步当前数据池的数据
     */
    private NptDict syncPoolData(NptBaseModelIncData poolData) {
        if(null == poolData){
            return NptDict.RST_EXCEPTION("要同步的数据池数据不存在!");
        }
        NptBaseModelPool pool = poolData.getPool();
        List rows = poolData.getPoolData();

        if(null == pool){
            return NptDict.RST_EXCEPTION("数据同步失败：数据池不存在！");
        }
        if(null != rows) {
            NptLogicDataField pkField = archService.fastFindDataFieldById(pool.getPrimaryFieldId());
            NptLogicDataTable poolType = archService.fastFindDataTableById(pool.getDataTypeId());
            if (null != pkField && null != poolType) {
                NptLogicDataField ukField = archService.fastFindDataFieldById(poolType.getUkFieldId());
                if (null == ukField) {
                    return NptDict.RST_EXCEPTION("数据类别[" + poolType.getAlias() + "]未指定数据主键");
                }
                Boolean tableExist = dataBaseService.isTableExisted(poolType.getTypeDbName());
                if (!tableExist) {
                    return NptDict.RST_EXCEPTION("数据类别[" + poolType.getAlias() + "]对应的物理表不存在!");
                }
                for (int i = 0; i < rows.size(); i++) {
                    Map<String, String> currentRow = (Map<String, String>) rows.get(i);
                    String pkValue = String.valueOf(currentRow.get(pkField.getFieldDbName()));
                    String ukValue = String.valueOf(currentRow.get(ukField.getFieldDbName()));
                    if (null == pkValue || null == ukValue || pkValue.isEmpty() || ukValue.isEmpty()) {
                        return NptDict.RST_EXCEPTION("数据类别[" + poolType.getAlias() + "]接收到的数据主键或业务主键值为空");
                    }

                    Boolean rowExisted = isRowExistedByPKUK(poolType, pkField, pkValue, ukField, ukValue);
                    Boolean result;
                    if (rowExisted) {
                        Map<String, String> updateCondition = new HashMap<String,String>();
                        updateCondition.put(ukField.getFieldDbName(), ukValue);
                        updateCondition.put(pkField.getFieldDbName(), pkValue);
                        currentRow.remove(pkField.getFieldDbName());
                        currentRow.remove(ukField.getFieldDbName());

                        result = dataBaseService.updateData(poolType.getTypeDbName(), poolData.getFields(), currentRow, updateCondition);
                    } else {
                        result = dataBaseService.insertRow(poolType.getTypeDbName(), poolData.getFields(), currentRow);
                    }
                    if (!result) {
                        return NptDict.RST_EXCEPTION("同步数据在更新数据或插入数据时出现异常!");
                    }
                }
            }else {
                return NptDict.RST_EXCEPTION("要同步数据的数据池(" + pool.getId() + "--" + pool.getPoolTitle() + "不存在业务主键或数据类别!");
            }
        }
        return NptDict.RST_SUCCESS;
    }

    private Boolean isRowExistedByPKUK(NptLogicDataTable poolType,NptLogicDataField pkField,String pkValue,NptLogicDataField ukField,String ukValue){
        Collection<NptLogicDataField> tempFields = new ArrayList<NptLogicDataField>();
        tempFields.add(pkField);
        tempFields.add(ukField);
        Map<String,String> condition = new HashMap<String,String>();
        condition.put(pkField.getFieldDbName(),pkValue);
        condition.put(ukField.getFieldDbName(),ukValue);
        String sql = dataBaseService.makeUniqueSql(poolType.getTypeDbName(),tempFields,condition,NptDict.CST_ENG_AS_CHN);

        return null != dataBaseService.queryUnique(sql);
    }

    /**
     *作者：OWEN
     *时间：2016/12/6 20:40
     *描述:
     *      同步模型前的数据校验
     */
    private NptDict beforeSyncBaseModel(NptBaseModelStructure structure){
        Assert.notNull(structure);

        if(null == structure.getModel()){
            return NptDict.RST_EXCEPTION("模型为空");
        }
        if(null == structure.getModelGroups() || structure.getModelGroups().isEmpty()){
            return NptDict.RST_EXCEPTION("模型分组为空");
        }
        if(null == structure.getGrouPoolMap() || structure.getGrouPoolMap().isEmpty()){
            return NptDict.RST_EXCEPTION("模型数据池为空");
        }
        if(null == structure.getPoolDataType() || structure.getPoolDataType().isEmpty()){
            return NptDict.RST_EXCEPTION("数据池对应的数据类别为空");
        }
        if(null == structure.getTypeFieldMap() || structure.getTypeFieldMap().isEmpty()){
            return NptDict.RST_EXCEPTION("数据池字段为空");
        }

        return NptDict.RST_SUCCESS;
    }

    /**
     *作者：OWEN
     *时间：2016/12/6 20:47
     *描述:
     *      清除旧模型
     */
    private NptDict cleanOldModel(NptBaseModel model){
        Long modelId = model.getId();
        NptBaseModel o_model = modelService.findBaseModelById(modelId);
        if(null != o_model){
            return modelService.deleteBaseModelById(o_model.getId());
        }
        return NptDict.RST_SUCCESS;
    }

    /**
     *作者：OWEN
     *时间：2016/12/5 17:24
     *描述:
     *      新增模型
     */
    private NptDict storeBaseModel(NptBaseModelStructure modelStructure){
        Assert.notNull(modelStructure);

        NptDict result = modelService.directSave(modelStructure.getModel());
        if(!NptDict.RST_SUCCESS.equals(result)){
            return result;
        }

        result = addBaseModelGroups(modelStructure);
        if(!NptDict.RST_SUCCESS.equals(result)){
            return result;
        }

        result = addOrUpdateBaseModelGroupoolDataTypeAndFields(modelStructure);
        if(!NptDict.RST_SUCCESS.equals(result)){
            return result;
        }

        result = addOrUpdateRealDataTables(modelStructure);
        if(!NptDict.RST_SUCCESS.equals(result)){
            return result;
        }

        result = addBaseModelGroupools(modelStructure);
        if(!NptDict.RST_SUCCESS.equals(result)){
            return result;
        }

        result = addBaseModelMainFields(modelStructure);
        if(!NptDict.RST_SUCCESS.equals(result)){
            return result;
        }

        return NptDict.RST_SUCCESS;
    }

    /**
     *作者：OWEN
     *时间：2016/12/5 17:26
     *描述:
     *      新增数据池依赖的配置信息【数据类别】【数据字段】
     */
    private NptDict addOrUpdateBaseModelGroupoolDataTypeAndFields(NptBaseModelStructure structure){

        Collection<NptLogicDataTable> newTypeList = structure.getPoolDataType().values();
        Collection<NptLogicDataField> newFieldList = new ArrayList<NptLogicDataField>();
        Collection<NptLogicDataField> extFieldList = new ArrayList<NptLogicDataField>();
        if(null != newTypeList && !newTypeList.isEmpty()){
            for(NptLogicDataTable t:newTypeList){
                Collection<NptLogicDataField> fields = structure.getTypeFieldMap().get(t.getId());
                if(null != fields){
                    newFieldList.addAll(fields);
                }

                NptLogicDataTable eType = archService.findDataTableById(t.getId());
                if(null != eType){
                    t.copyTo(eType);
                    archService.update(eType);
                }else {
                    archService.save(t);
                }

                Collection<NptLogicDataField> eFields = archService.listDataField(t.getId(),null,null);
                if(null != eFields && !eFields.isEmpty()){
                    extFieldList.addAll(eFields);
                }
            }

            Collection<NptLogicDataField> updFieldList = new ArrayList<NptLogicDataField>();
            Collection<NptLogicDataField> delFieldList = new ArrayList<NptLogicDataField>();
            Collection<NptLogicDataField> sveFieldList = new ArrayList<NptLogicDataField>();
            delFieldList.addAll(extFieldList);
            sveFieldList.addAll(newFieldList);

            for(NptLogicDataField n:newFieldList){
                for(NptLogicDataField e:extFieldList){
                    if(n.equals(e)){
                        n.copyTo(e);
                        updFieldList.add(e);
                    }
                }
            }

            delFieldList.removeAll(newFieldList);
            sveFieldList.removeAll(extFieldList);
            for(NptLogicDataField f:sveFieldList){
                archService.save(f);
            }
            for(NptLogicDataField f:delFieldList){
                archService.delete(f);
            }
            for(NptLogicDataField f:updFieldList){
                archService.update(f);
            }
        }
        return NptDict.RST_SUCCESS;
    }

    /**
     *作者：owen
     *时间：2016/12/13 15:21
     *描述:
     *      针对新推送过来的数据类别,检测其数据表是否已存在
     *
     *      不存在则创建该表,存在则检测其包含的实际字段是否已完全覆盖
     */
    private NptDict addOrUpdateRealDataTables(NptBaseModelStructure structure){
        Map<Long,NptLogicDataTable> poolTypeMap = structure.getPoolDataType();
        if(null != poolTypeMap){
            Collection<NptLogicDataTable> dataTypes = poolTypeMap.values();
            Map<Long,Collection<NptLogicDataField>> typeFieldMap = structure.getTypeFieldMap();
            if(null != dataTypes && !dataTypes.isEmpty() && null != typeFieldMap && !typeFieldMap.isEmpty()){
                for(NptLogicDataTable type:dataTypes){
                    Boolean tableExisted = dataBaseService.isTableExisted(type.getTypeDbName());
                    if(tableExisted){
                        Collection<NptLogicDataField> unCovered = dataBaseService.isTableCoverFields(type.getTypeDbName(),typeFieldMap.get(type.getId()));
                        if(unCovered.isEmpty()){
                            continue;
                        }else {
                            if(!dataBaseService.addColumn(type.getTypeDbName(),unCovered)){
                                return NptDict.RST_EXCEPTION("为实际物理表" + type.getAlias() + "<-->" + type.getTypeDbName() + "添加字段出现未知异常!");
                            }
                        }
                    }else {
                        if(!dataBaseService.createTable(type.getTypeDbName(),typeFieldMap.get(type.getId()))){
                            return NptDict.RST_EXCEPTION("创建实体表[" + type.getAlias() + "<-->" + type.getTypeDbName() + "]失败!");
                        }
                    }
                }
            }
        }
        return NptDict.RST_SUCCESS;
    }

    /**
     *作者：OWEN
     *时间：2016/12/5 17:27
     *描述:
     *      新增模型分组
     */
    private NptDict addBaseModelGroups(NptBaseModelStructure structure){
        Collection<NptBaseModelGroup> groups = structure.getModelGroups();
        if(null != groups && !groups.isEmpty()){
            for(NptBaseModelGroup group:groups){
                NptDict result = modelService.directSave(group);
                if(!result.equals(NptDict.RST_SUCCESS)){
                    return result;
                }
            }
            return NptDict.RST_SUCCESS;
        }
        return NptDict.RST_INVALID_PARAMS;
    }

    /**
     *作者：OWEN
     *时间：2016/12/5 17:27
     *描述:
     *      新增模型数据池
     */
    private NptDict addBaseModelGroupools(NptBaseModelStructure structure){
        Map<Long,Collection<NptBaseModelPool>> grouPoolMap = structure.getGrouPoolMap();
        if(null == grouPoolMap || grouPoolMap.isEmpty()){
            return NptDict.RST_INVALID_PARAMS;
        }
        Set<Long> groupIdSet = grouPoolMap.keySet();
        for(Long groupId:groupIdSet) {
            Collection<NptBaseModelPool> poolList = grouPoolMap.get(groupId);
            if(null != poolList && !poolList.isEmpty()){
                for(NptBaseModelPool pool:poolList){
                    NptDict result = modelService.directSave(pool);
                    if(!result.equals(NptDict.RST_SUCCESS)){
                        return result;
                    }
                }
            }
        }
        return NptDict.RST_SUCCESS;
    }

    /**
     *作者：owen
     *时间：2016/12/16 17:59
     *描述:
     *
     */
    private NptDict addBaseModelMainFields(NptBaseModelStructure structure){
        if(null != structure){
            Collection<NptBaseModelMainField> mainFieldss = structure.getMainFieldList();
            if(null != mainFieldss && !mainFieldss.isEmpty()){
                for(NptBaseModelMainField mf:mainFieldss){
                    NptDict result = modelService.directSave(mf);
                    if(!result.equals(NptDict.RST_SUCCESS)){
                        return result;
                    }
                }
            }
        }
        return NptDict.RST_SUCCESS;
    }
}
