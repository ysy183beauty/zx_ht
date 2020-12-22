package com.npt.internet.model.service.impl;

import com.npt.bridge.model.*;
import com.npt.dsp.manager.NptDatabaseManager;
import com.npt.grs.model.service.NptGRSBaseModelService;
import com.npt.internet.model.service.NptInternetSyncService;
import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.bridge.dict.NptRmsDict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/12/1 17:31
 * 描述:
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NptInternetSyncServiceBase implements NptInternetSyncService{

    @Resource(name = "rmsArchService")
    private NptRmsArchService archService;
    @Resource(name = "nptGrsModelService")
    private NptGRSBaseModelService modelService;
    @Resource(name = "nptDssOracleManager")
    protected NptDatabaseManager databaseManager;

    /**
     * 作者：OWEN
     * 时间：2016/12/1 22:19
     * 描述:
     * 同步组织机构信息
     *
     * @param orgList
     */
    @Override
    public NptRmsDict syncDataProvider(Collection<NptLogicDataProvider> orgList) {
        if(null != orgList){
            Collection<NptLogicDataProvider> updateList = new ArrayList<>();
            Collection<NptLogicDataProvider> insertList = new ArrayList<>();
            for(NptLogicDataProvider org:orgList){
                NptLogicDataProvider existedOrg = archService.findOrgById(org.getId());
                if(null != existedOrg){
                    org.copyTo(existedOrg);
                    updateList.add(existedOrg);
                }else {
                    insertList.add(org);
                }
            }
            archService.batchSaveOrg(insertList);
            archService.batchUpdateOrg(updateList);
            return NptRmsDict.RST_SUCCESS;
        }
        return NptRmsDict.RST_INVALID_PARAMS;
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
    public NptRmsDict syncBaseModel(NptBaseModelStructure structure) {
        if(null == structure) {
            return NptRmsDict.RST_EXCEPTION("传递的数据不可识别！");
        }

        NptRmsDict beforeResult = beforeSyncBaseModel(structure);
        if(!NptRmsDict.RST_SUCCESS.equals(beforeResult)){
            return beforeResult;
        }

        NptRmsDict cleanResult = cleanOldModel(structure.getModel());
        if(!NptRmsDict.RST_SUCCESS.equals(cleanResult)){
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
     */
    @Override
    public NptRmsDict syncBaseModelData(NptBaseModelPoolRow poolData) {
        if(null == poolData) {
            return NptRmsDict.RST_EXCEPTION("传递的数据不可识别！");
        }
        return syncPoolData(poolData);
    }

    /**
     *作者：owen
     *时间：2016/12/14 15:01
     *描述:
     *      同步当前数据池的数据
     */
    private NptRmsDict syncPoolData(NptBaseModelPoolRow poolData) {
        if(null == poolData){
            return NptRmsDict.RST_EXCEPTION("要同步的数据池数据不存在!");
        }
        NptBaseModelPool pool = poolData.getPool();
        List rows = poolData.getPoolData();

        if(null == pool){
            return NptRmsDict.RST_EXCEPTION("数据同步失败：数据池不存在！");
        }
        if(null != rows) {
            NptLogicDataField pkField = archService.fastFindDataFieldById(pool.getPrimaryFieldId());
            NptLogicDataType poolType = archService.fastFindDataTypeById(pool.getDataTypeId());
            if (null != pkField && null != poolType) {
                NptLogicDataField ukField = archService.fastFindDataFieldById(poolType.getUkFieldId());
                if (null == ukField) {
                    return NptRmsDict.RST_EXCEPTION("数据类别[" + poolType.getAlias() + "]未指定数据主键");
                }
                Boolean tableExist = databaseManager.isTableExisted(poolType.getTypeDbName());
                if (!tableExist) {
                    return NptRmsDict.RST_EXCEPTION("数据类别[" + poolType.getAlias() + "]对应的物理表不存在!");
                }
                for (int i = 0; i < rows.size(); i++) {
                    Map<String, String> currentRow = (Map<String, String>) rows.get(i);
                    String pkValue = String.valueOf(currentRow.get(pkField.getFieldDbName()));
                    String ukValue = String.valueOf(currentRow.get(ukField.getFieldDbName()));
                    if (null == pkValue || null == ukValue || pkValue.isEmpty() || ukValue.isEmpty()) {
                        return NptRmsDict.RST_EXCEPTION("数据类别[" + poolType.getAlias() + "]接收到的数据主键或业务主键值为空");
                    }

                    Boolean rowExisted = isRowExistedByPKUK(poolType, pkField, pkValue, ukField, ukValue);
                    Boolean result;
                    if (rowExisted) {
                        Map<String, String> updateCondition = new HashMap<>();
                        updateCondition.put(ukField.getFieldDbName(), ukValue);
                        updateCondition.put(pkField.getFieldDbName(), pkValue);
                        currentRow.remove(pkField.getFieldDbName());
                        currentRow.remove(ukField.getFieldDbName());

                        result = databaseManager.updateData(poolType.getTypeDbName(), poolData.getFields(), currentRow, updateCondition);
                    } else {
                        result = databaseManager.insertRow(poolType.getTypeDbName(), poolData.getFields(), currentRow);
                    }
                    if (!result) {
                        return NptRmsDict.RST_EXCEPTION("同步数据在更新数据或插入数据时出现异常!");
                    }
                }
            }else {
                return NptRmsDict.RST_EXCEPTION("要同步数据的数据池(" + pool.getId() + "--" + pool.getPoolTitle() + "不存在业务主键或数据类别!");
            }
        }
        return NptRmsDict.RST_SUCCESS;
    }

    private Boolean isRowExistedByPKUK(NptLogicDataType poolType, NptLogicDataField pkField, String pkValue, NptLogicDataField ukField, String ukValue){
        Collection<NptLogicDataField> tempFields = new ArrayList<>();
        tempFields.add(pkField);
        tempFields.add(ukField);
        Map<String,String> condition = new HashMap<>();
        condition.put(pkField.getFieldDbName(),pkValue);
        condition.put(ukField.getFieldDbName(),ukValue);
        String sql = databaseManager.makeUniqueSql(poolType.getTypeDbName(),tempFields,condition,NptRmsDict.CST_ENG_AS_CHN);

        return null != databaseManager.queryUnique(sql);
    }

    /**
     *作者：OWEN
     *时间：2016/12/6 20:40
     *描述:
     *      同步模型前的数据校验
     */
    private NptRmsDict beforeSyncBaseModel(NptBaseModelStructure structure){
        Assert.notNull(structure);

        if(null == structure.getModel()){
            return NptRmsDict.RST_EXCEPTION("模型为空");
        }
        if(null == structure.getModelGroups() || structure.getModelGroups().isEmpty()){
            return NptRmsDict.RST_EXCEPTION("模型分组为空");
        }
        if(null == structure.getGrouPoolMap() || structure.getGrouPoolMap().isEmpty()){
            return NptRmsDict.RST_EXCEPTION("模型数据池为空");
        }
        if(null == structure.getPoolDataType() || structure.getPoolDataType().isEmpty()){
            return NptRmsDict.RST_EXCEPTION("数据池对应的数据类别为空");
        }
        if(null == structure.getTypeFieldMap() || structure.getTypeFieldMap().isEmpty()){
            return NptRmsDict.RST_EXCEPTION("数据池字段为空");
        }

        return NptRmsDict.RST_SUCCESS;
    }

    /**
     *作者：OWEN
     *时间：2016/12/6 20:47
     *描述:
     *      清除旧模型
     */
    private NptRmsDict cleanOldModel(NptBaseModel model){
        Long modelId = model.getId();
        NptBaseModel o_model = modelService.findBaseModelById(modelId);
        if(null != o_model){
            return modelService.deleteBaseModelById(o_model.getId());
        }
        return NptRmsDict.RST_SUCCESS;
    }

    /**
     *作者：OWEN
     *时间：2016/12/5 17:24
     *描述:
     *      新增模型
     */
    private NptRmsDict storeBaseModel(NptBaseModelStructure modelStructure){
        Assert.notNull(modelStructure);

        NptRmsDict result = modelService.directSave(modelStructure.getModel());
        if(!NptRmsDict.RST_SUCCESS.equals(result)){
            return result;
        }

        result = addBaseModelGroups(modelStructure);
        if(!NptRmsDict.RST_SUCCESS.equals(result)){
            return result;
        }

        result = addOrUpdateBaseModelGroupoolDataTypeAndFields(modelStructure);
        if(!NptRmsDict.RST_SUCCESS.equals(result)){
            return result;
        }

        result = addOrUpdateRealDataTables(modelStructure);
        if(!NptRmsDict.RST_SUCCESS.equals(result)){
            return result;
        }

        result = addBaseModelGroupools(modelStructure);
        if(!NptRmsDict.RST_SUCCESS.equals(result)){
            return result;
        }

        result = addBaseModelMainFields(modelStructure);
        if(!NptRmsDict.RST_SUCCESS.equals(result)){
            return result;
        }

        return NptRmsDict.RST_SUCCESS;
    }

    /**
     *作者：OWEN
     *时间：2016/12/5 17:26
     *描述:
     *      新增数据池依赖的配置信息【数据类别】【数据字段】
     */
    private NptRmsDict addOrUpdateBaseModelGroupoolDataTypeAndFields(NptBaseModelStructure structure){

        Collection<NptLogicDataType> newTypeList = structure.getPoolDataType().values();
        Collection<NptLogicDataField> newFieldList = new ArrayList<>();
        Collection<NptLogicDataField> extFieldList = new ArrayList<>();
        if(null != newTypeList && !newTypeList.isEmpty()){
            for(NptLogicDataType t:newTypeList){
                Collection<NptLogicDataField> fields = structure.getTypeFieldMap().get(t.getId());
                if(null != fields){
                    newFieldList.addAll(fields);
                }

                NptLogicDataType eType = archService.findDataTypeById(t.getId());
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

            Collection<NptLogicDataField> updFieldList = new ArrayList<>();
            Collection<NptLogicDataField> delFieldList = new ArrayList<>();
            Collection<NptLogicDataField> sveFieldList = new ArrayList<>();
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
        return NptRmsDict.RST_SUCCESS;
    }

    /**
     *作者：owen
     *时间：2016/12/13 15:21
     *描述:
     *      针对新推送过来的数据类别,检测其数据表是否已存在
     *
     *      不存在则创建该表,存在则检测其包含的实际字段是否已完全覆盖
     */
    private NptRmsDict addOrUpdateRealDataTables(NptBaseModelStructure structure){
        Map<Long,NptLogicDataType> poolTypeMap = structure.getPoolDataType();
        if(null != poolTypeMap){
            Collection<NptLogicDataType> dataTypes = poolTypeMap.values();
            Map<Long,Collection<NptLogicDataField>> typeFieldMap = structure.getTypeFieldMap();
            if(null != dataTypes && !dataTypes.isEmpty() && null != typeFieldMap && !typeFieldMap.isEmpty()){
                for(NptLogicDataType type:dataTypes){
                    Boolean tableExisted = databaseManager.isTableExisted(type.getTypeDbName());
                    if(tableExisted){
                        Collection<NptLogicDataField> unCovered = databaseManager.isTableCoverFields(type.getTypeDbName(),typeFieldMap.get(type.getId()));
                        if(unCovered.isEmpty()){
                            continue;
                        }else {
                            if(!databaseManager.addColumn(type.getTypeDbName(),unCovered)){
                                return NptRmsDict.RST_EXCEPTION("为实际物理表" + type.getAlias() + "<-->" + type.getTypeDbName() + "添加字段出现未知异常!");
                            }
                        }
                    }else {
                        if(!databaseManager.createTable(type.getTypeDbName(),typeFieldMap.get(type.getId()))){
                            return NptRmsDict.RST_EXCEPTION("创建实体表[" + type.getAlias() + "<-->" + type.getTypeDbName() + "]失败!");
                        }
                    }
                }
            }
        }
        return NptRmsDict.RST_SUCCESS;
    }

    /**
     *作者：OWEN
     *时间：2016/12/5 17:27
     *描述:
     *      新增模型分组
     */
    private NptRmsDict addBaseModelGroups(NptBaseModelStructure structure){
        Collection<NptBaseModelGroup> groups = structure.getModelGroups();
        if(null != groups && !groups.isEmpty()){
            for(NptBaseModelGroup group:groups){
                NptRmsDict result = modelService.directSave(group);
                if(!result.equals(NptRmsDict.RST_SUCCESS)){
                    return result;
                }
            }
            return NptRmsDict.RST_SUCCESS;
        }
        return NptRmsDict.RST_INVALID_PARAMS;
    }

    /**
     *作者：OWEN
     *时间：2016/12/5 17:27
     *描述:
     *      新增模型数据池
     */
    private NptRmsDict addBaseModelGroupools(NptBaseModelStructure structure){
        Map<Long,Collection<NptBaseModelPool>> grouPoolMap = structure.getGrouPoolMap();
        if(null == grouPoolMap || grouPoolMap.isEmpty()){
            return NptRmsDict.RST_INVALID_PARAMS;
        }
        Set<Long> groupIdSet = grouPoolMap.keySet();
        for(Long groupId:groupIdSet) {
            Collection<NptBaseModelPool> poolList = grouPoolMap.get(groupId);
            if(null != poolList && !poolList.isEmpty()){
                for(NptBaseModelPool pool:poolList){
                    NptRmsDict result = modelService.directSave(pool);
                    if(!result.equals(NptRmsDict.RST_SUCCESS)){
                        return result;
                    }
                }
            }
        }
        return NptRmsDict.RST_SUCCESS;
    }

    /**
     *作者：owen
     *时间：2016/12/16 17:59
     *描述:
     *
     */
    private NptRmsDict addBaseModelMainFields(NptBaseModelStructure structure){
        if(null != structure){
            Collection<NptBaseModelIndex> mainFieldss = structure.getMainFieldList();
            if(null != mainFieldss && !mainFieldss.isEmpty()){
                for(NptBaseModelIndex mf:mainFieldss){
                    NptRmsDict result = modelService.directSave(mf);
                    if(!result.equals(NptRmsDict.RST_SUCCESS)){
                        return result;
                    }
                }
            }
        }
        return NptRmsDict.RST_SUCCESS;
    }
}
