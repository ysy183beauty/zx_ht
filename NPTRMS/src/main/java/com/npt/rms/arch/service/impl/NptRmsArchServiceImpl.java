package com.npt.rms.arch.service.impl;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.arch.manager.NptDataFieldManager;
import com.npt.rms.arch.manager.NptDataTypeManager;
import com.npt.rms.arch.manager.NptOrganizationManager;
import com.npt.rms.arch.service.NptRmsArchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.summer.core.context.module.dependent.PublicBean;
import org.summer.extend.orm.Condition;
import org.summer.extend.orm.condition.Conditions;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/9 23:27
 * 描述:
 *      RMS模块对当前模块及外部模块的总操作服务类
 */
@Service("rmsArchService")
@Transactional
public class NptRmsArchServiceImpl implements NptRmsArchService,PublicBean{

    @Autowired
    private NptOrganizationManager organizationManager;
    @Autowired
    private NptDataTypeManager dataTypeManager;
    @Autowired
    private NptDataFieldManager dataFieldManager;


    /**
     * 作者：OWEN
     * 时间：2016/11/10 0:04
     * 描述:
     * 获取根机构
     */
    @Override
    public NptLogicDataProvider getRootOrg() {
        try {
            return organizationManager.findUniqueByCondition(
                    Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()),
                    Conditions.eq(NptBaseEntity.PROPERTY_PARENT_ID,NptCommonUtil.getDefaultParentId()));
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:52
     * 描述:
     * 加载指定机构下挂的所有子机构
     *
     * @param orgId
     */
    @Override
    public Collection<NptLogicDataProvider> listOrg(Long orgId) {
        List<NptLogicDataProvider> result = new ArrayList<>();
        if(null == orgId || orgId == NptCommonUtil.getDefaultParentId()){
            NptLogicDataProvider rootOrg = getRootOrg();
            if(null != rootOrg) {
                orgId = getRootOrg().getId();
                result.add(rootOrg);
            }
        }
        Collection<NptLogicDataProvider> searchResult =
                organizationManager.findByCondition(
                        Conditions.eq(NptBaseEntity.PROPERTY_PARENT_ID,orgId));
        if(null != searchResult){
            result.addAll(searchResult);
        }

        sortByChina(result);

        return result;
    }

    /**
     * 作者: owen
     * 时间: 2017/3/14 下午4:01
     * 描述:
     *      名称以中文拼音首字段排序
     */
    private void sortByChina(List<NptLogicDataProvider> list){
        // 对结果按别名排序
        Collections.sort(list, new Comparator<NptLogicDataProvider>() {
            @Override
            public int compare(NptLogicDataProvider o1, NptLogicDataProvider o2) {
                Comparator<Object> comparator = Collator.getInstance(java.util.Locale.CHINA);
                return comparator.compare(o1.getAlias(), o2.getAlias());
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 16:14
     * 描述:
     * 加载所有机构
     */
    @Override
    public Collection<NptLogicDataProvider> listAllOrg() {
        List<NptLogicDataProvider> result = new ArrayList<>();
        Collection<NptLogicDataProvider> searchResult = organizationManager.findAll();
        if(null != searchResult){
            result.addAll(searchResult);
        }

        sortByChina(result);

        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 16:21
     * 描述:
     * 加载所有正常启用的机构
     */
    @Override
    public Collection<NptLogicDataProvider> listAllEnabledOrg() {
        List<NptLogicDataProvider> result = new ArrayList<>();
        Collection<NptLogicDataProvider> searchResult = organizationManager.findByCondition(
                Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        if(null != searchResult){
            result.addAll(searchResult);
        }

        sortByChina(result);
        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:53
     * 描述:
     * 加载指定机构下挂的所有数据类别
     *
     * @param orgId
     */
    @Override
    public Collection<NptLogicDataType> listLinealDataType(Long orgId,NptDict status) {
        Collection<NptLogicDataType> result = new ArrayList<>();
        NptLogicDataProvider org = fastFindOrgById(orgId);
        if(null != org){
            Collection<NptLogicDataType> searchResult =
                    dataTypeManager.findByCondition(
                            null == status?
                                    Conditions.isNotNull(NptBaseEntity.PROPERTY_ID):
                                    Conditions.eq(NptBaseEntity.PROPERTY_STATUS, status.getCode()),
                            Conditions.eq(NptBaseEntity.PROPERTY_PARENT_ID,orgId));
            if(null != searchResult){
                result.addAll(searchResult);
            }
        }
        loadDataTypeUFieldTitle(result);
        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/14 19:43
     * 描述:
     * 加载指定机构及其所有子机构的数据类别
     *
     * @param org
     * @param result
     */
    @Override
    public void listAvailableDataTypeForBaseModel(NptLogicDataProvider org, Collection<NptLogicDataType> result) {
        if(null != org){
            Collection<NptLogicDataType> searchResult =
                    dataTypeManager.findByCondition(
                            Conditions.ne(NptLogicDataType.PROPERTY_UKFIELD_ID,NptCommonUtil.getDefaultParentId()),
                            Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()),
                            Conditions.eq(NptBaseEntity.PROPERTY_PARENT_ID,org.getId()));
            if(null != searchResult){
                result.addAll(searchResult);
            }

            Collection<NptLogicDataProvider> subOrgList = organizationManager.findByCondition(
                    Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()),
                    Conditions.eq(NptBaseEntity.PROPERTY_PARENT_ID,org.getId()));
            if(null != subOrgList && !subOrgList.isEmpty()){
                for(NptLogicDataProvider sub:subOrgList){
                    this.listAvailableDataTypeForBaseModel(sub,result);
                }
            }
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 16:16
     * 描述:
     * 加载所有数据类别
     */
    @Override
    public Collection<NptLogicDataType> listAllDataType() {
        Collection<NptLogicDataType> result = new ArrayList<>();
        Collection<NptLogicDataType> searchResult = dataTypeManager.findByCondition(
                Conditions.not(Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_DELETED.getCode())));
        if(null != searchResult){
            result.addAll(searchResult);
        }
        loadDataTypeUFieldTitle(result);
        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 16:21
     * 描述:
     * 加载所有正常启用的数据字段
     */
    @Override
    public Collection<NptLogicDataType> listAllEnabledDataType() {
        Collection<NptLogicDataType> result = new ArrayList<>();
        Collection<NptLogicDataType> searchResult = dataTypeManager.findByCondition(
                Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        if(null != searchResult){
            result.addAll(searchResult);
        }
        loadDataTypeUFieldTitle(result);
        return result;
    }

    private void loadDataTypeUFieldTitle(Collection<NptLogicDataType> list){
        if(null != list && !list.isEmpty()){
            for(NptLogicDataType type:list){
                loadDataTypeUFieldTitle(type);
            }
        }
    }

    private void loadDataTypeUFieldTitle(NptLogicDataType type){
        if(null != type){
            NptLogicDataField field = this.fastFindDataFieldById(type.getUkFieldId());
            if(null != field){
                type.setUkFieldTitle(field.getAlias());
            }else {
                type.setUkFieldTitle(NptDict.RST_UNKNOW.getTitle());
            }
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:54
     * 描述:
     * 加载指定数据类别下挂的所有数据字段
     *
     * @param typeId
     */
    @Override
    public Collection<NptLogicDataField> listDataField(Long typeId, NptDict pubLevel, NptDict status) {
        Collection<NptLogicDataField> result = new ArrayList<>();
        NptLogicDataType type = fastFindDataTypeById(typeId);
        if(null != type){
            Collection<NptLogicDataField> searchResult =
                    dataFieldManager.findByCondition(
                            null == status?
                                    Conditions.isNotNull(NptBaseEntity.PROPERTY_ID):
                                    Conditions.eq(NptBaseEntity.PROPERTY_STATUS,status.getCode()),
                            null == pubLevel?
                                    Conditions.isNotNull(NptBaseEntity.PROPERTY_ID):
                                    Conditions.eq(NptLogicDataField.PROPERTY_PUBLISH_LEVEL,pubLevel.getCode()),
                            Conditions.eq(NptBaseEntity.PROPERTY_PARENT_ID,typeId),
                            Conditions.asc(NptBaseEntity.PROPERTY_DISPLAY_ORDER));
            if(null != searchResult){
                result.addAll(searchResult);
            }
        }
        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/12/2 18:05
     * 描述:
     * 加载指定数据类别下挂的所有非指定状态的字段
     *
     * @param typeId
     * @param status
     */
    @Override
    public Collection<NptLogicDataField> listDataFieldEx(Long typeId, NptDict status) {
        Collection<NptLogicDataField> result = new ArrayList<>();
        NptLogicDataType type = fastFindDataTypeById(typeId);
        if(null != type){
            Collection<NptLogicDataField> searchResult =
                    dataFieldManager.findByCondition(
                            null == status?
                                    Conditions.isNotNull(NptBaseEntity.PROPERTY_ID):
                                    Conditions.not(Conditions.eq(NptBaseEntity.PROPERTY_STATUS,status.getCode())),
                            Conditions.eq(NptBaseEntity.PROPERTY_PARENT_ID,typeId),
                            Conditions.asc(NptBaseEntity.PROPERTY_DISPLAY_ORDER));
            if(null != searchResult){
                result.addAll(searchResult);
            }
        }
        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 11:46
     * 描述:
     * 依据数据字段ID列表批量进行加载
     *
     * @param idList
     */
    @Override
    public Collection<NptLogicDataField> listDataField(Collection<Long> idList) {
         return dataFieldManager.findByCondition(
                 Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()),
                 Conditions.in(NptBaseEntity.PROPERTY_ID,idList));
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 16:17
     * 描述:
     * 加载所有数据字段
     */
    @Override
    public Collection<NptLogicDataField> listAllDataField() {
        Collection<NptLogicDataField> result = new ArrayList<>();
        Collection<NptLogicDataField> searchResult = dataFieldManager.findAll();
        if(null != searchResult){
            result.addAll(searchResult);
        }
        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 16:22
     * 描述:
     * 加载所有正常启用的数据字段
     */
    @Override
    public Collection<NptLogicDataField> listAllEnabledDataField() {
        Collection<NptLogicDataField> result = new ArrayList<>();
        Collection<NptLogicDataField> searchResult = dataFieldManager.findByCondition(
                Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        if(null != searchResult){
            result.addAll(searchResult);
        }
        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 23:31
     * 描述:
     * 多条件查询机构列表
     *
     * @param conditions
     */
    @Override
    public Collection<NptLogicDataProvider> listOrganizationByConditions(Condition... conditions) {
        Collection<NptLogicDataProvider> result = new ArrayList<>();
        Collection<NptLogicDataProvider> searchResult = organizationManager.findByCondition(conditions);
        if(null != searchResult){
            result.addAll(searchResult);
        }
        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 23:32
     * 描述:
     * 多条件查询数据类别
     *
     * @param conditions
     */
    @Override
    public Collection<NptLogicDataType> listDataTypeByConditions(Condition... conditions) {
        Collection<NptLogicDataType> result = new ArrayList<>();
        Collection<NptLogicDataType> searchResult = dataTypeManager.findByCondition(conditions);
        if(null != searchResult){
            result.addAll(searchResult);
        }
        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 23:33
     * 描述:
     * 多条件查询数据类别字段
     *
     * @param conditions
     */
    @Override
    public Collection<NptLogicDataField> listDataFieldByConditions(Condition... conditions) {
        Collection<NptLogicDataField> result = new ArrayList<>();
        Collection<NptLogicDataField> searchResult = dataFieldManager.findByCondition(conditions);
        if(null != searchResult){
            result.addAll(searchResult);
        }
        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:56
     * 描述:
     * 依据机构ID获取机构所属的父机构
     *
     * @param org
     */
    @Override
    public NptLogicDataProvider findParent(NptLogicDataProvider org) {
        if(null != org){
            NptLogicDataProvider _this = fastFindOrgById(org.getId());
            if(null != _this){
                return fastFindOrgById(_this.getParentId());
            }
        }
        return null;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:55
     * 描述:
     * 依据数据类别ID获取数据类别所属的机构
     *
     * @param type
     */
    @Override
    public NptLogicDataProvider findParent(NptLogicDataType type) {
        if(null != type){
            NptLogicDataType _this = fastFindDataTypeById(type.getId());
            if(null != _this){
                return fastFindOrgById(_this.getParentId());
            }
        }
        return null;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:57
     * 描述:
     * 依据数据字段ID获取数据字段所属的数据类别
     *
     * @param field
     */
    @Override
    public NptLogicDataType findParent(NptLogicDataField field) {
        if(null != field){
            NptLogicDataField _this = fastFindDataFieldById(field.getId());
            if(null != _this){
                return fastFindDataTypeById(_this.getParentId());
            }
        }
        return null;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:06
     * 描述:
     * 通过ID获取机构信息
     *
     * @param id
     */
    @Override
    public NptLogicDataProvider findOrgById(Long id) {
        return organizationManager.findById(id);
    }

    @Override
    public NptLogicDataProvider fastFindOrgById(Long id) {
        return organizationManager.fastFindById(id);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:07
     * 描述:
     * 通过ID获取数据类别
     *
     * @param id
     */
    @Override
    public NptLogicDataType findDataTypeById(Long id) {
        return dataTypeManager.findById(id);
    }

    @Override
    public NptLogicDataType fastFindDataTypeById(Long id) {
        return dataTypeManager.fastFindById(id);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:07
     * 描述:
     * 通过ID获取数据类别字段
     *
     * @param id
     */
    @Override
    public NptLogicDataField findDataFieldById(Long id) {
        return dataFieldManager.findById(id);
    }

    @Override
    public NptLogicDataField fastFindDataFieldById(Long id) {
        return dataFieldManager.fastFindById(id);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/24 上午9:58
     * 备注: 批量获取数据类别字段
     */
    @Override
    public Collection<NptLogicDataField> fastFindDataFieldById(Collection<Long> ids) {
        return ids.stream().map(dataFieldManager::fastFindById).collect(Collectors.toList());
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:08
     * 描述:
     * 保存机构信息
     *
     * @param org
     */
    @Override
    public void save(NptLogicDataProvider org) {
        if(null != org) {
            organizationManager.save(org);
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:09
     * 描述:
     * 保存数据类别
     *
     * @param type
     */
    @Override
    public void save(NptLogicDataType type) {
        if(null != type){
            dataTypeManager.save(type);
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:10
     * 描述:
     * 保存数据类别字段
     *
     * @param field
     */
    @Override
    public void save(NptLogicDataField field) {
        if(null != field){
            dataFieldManager.save(field);
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:10
     * 描述:
     * 更新机构信息
     *
     * @param org
     */
    @Override
    public void update(NptLogicDataProvider org) {
        organizationManager.update(org);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:10
     * 描述:
     * 更新数据类别
     *
     * @param type
     */
    @Override
    public void update(NptLogicDataType type) {
        dataTypeManager.update(type);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:11
     * 描述:
     * 更新数据类别字段
     *
     * @param field
     */
    @Override
    public void update(NptLogicDataField field) {
        dataFieldManager.update(field);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:11
     * 描述:
     * 逻辑删除指定机构
     *
     * @param org
     */
    @Override
    public void delete(NptLogicDataProvider org) {
        if(null != org){
            NptLogicDataProvider _org = findOrgById(org.getId());
            if(null != _org){
                _org.setStatus(NptDict.IDS_DELETED.getCode());
                organizationManager.update(_org);
            }
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:12
     * 描述:
     * 逻辑删除数据类别
     *
     * @param type
     */
    @Override
    public void delete(NptLogicDataType type) {
        if(null != type){
            NptLogicDataType _type = dataTypeManager.findById(type.getId());
            if(null != _type){
                _type.setStatus(NptDict.IDS_DELETED.getCode());
                dataTypeManager.update(_type);
            }
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:13
     * 描述:
     * 逻辑删除数据类别字段
     *
     * @param field
     */
    @Override
    public void delete(NptLogicDataField field) {
        if(null != field){
            NptLogicDataField _field = dataFieldManager.findById(field.getId());
            if(null != _field){
                _field.setStatus(NptDict.IDS_DELETED.getCode());
                dataFieldManager.update(_field);
            }
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:25
     * 描述:
     * 禁用机构
     *
     * @param org
     */
    @Override
    public void disable(NptLogicDataProvider org) {
        if(null != org){
            NptLogicDataProvider _org = findOrgById(org.getId());
            if(null != _org){
                _org.setStatus(NptDict.IDS_DISABLED.getCode());
                organizationManager.update(_org);
            }
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:25
     * 描述:
     * 禁用数据类别
     *
     * @param type
     */
    @Override
    public void disable(NptLogicDataType type) {
        if(null != type){
            NptLogicDataType _type = dataTypeManager.findById(type.getId());
            if(null != _type){
                _type.setStatus(NptDict.IDS_DISABLED.getCode());
                dataTypeManager.update(_type);
            }
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:25
     * 描述:
     * 禁用数据类别字段
     *
     * @param field
     */
    @Override
    public void disable(NptLogicDataField field) {
        if(null != field){
            NptLogicDataField _field = dataFieldManager.findById(field.getId());
            if(null != _field){
                _field.setStatus(NptDict.IDS_DISABLED.getCode());
                dataFieldManager.update(_field);
            }
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:26
     * 描述:
     * 启用机构
     *
     * @param org
     */
    @Override
    public void enable(NptLogicDataProvider org) {
        if(null != org){
            NptLogicDataProvider _org = findOrgById(org.getId());
            if(null != _org){
                _org.setStatus(NptDict.IDS_ENABLED.getCode());
                organizationManager.update(_org);
            }
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:26
     * 描述:
     * 启用数据类别
     *
     * @param type
     */
    @Override
    public void enable(NptLogicDataType type) {
        if(null != type){
            NptLogicDataType _type = dataTypeManager.findById(type.getId());
            if(null != _type){
                _type.setStatus(NptDict.IDS_ENABLED.getCode());
                dataTypeManager.update(_type);
            }
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:26
     * 描述:
     * 启用数据类别字段
     *
     * @param field
     */
    @Override
    public void enable(NptLogicDataField field) {
        if(null != field){
            NptLogicDataField _field = dataFieldManager.findById(field.getId());
            if(null != _field){
                _field.setStatus(NptDict.IDS_ENABLED.getCode());
                dataFieldManager.update(_field);
            }
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:13
     * 描述:
     * 批量保存机构
     *
     * @param list
     */
    @Override
    public void batchSaveOrg(Collection<NptLogicDataProvider> list) {
        if(null != list && !list.isEmpty()){
            organizationManager.saveAll(list);
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:22
     * 描述:
     * 批量保存数据类别
     *
     * @param list
     */
    @Override
    public void batchSaveDataType(Collection<NptLogicDataType> list) {
        if(null != list && !list.isEmpty()){
            dataTypeManager.saveAll(list);
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:23
     * 描述:
     * 批量保存数据类别字段
     *
     * @param list
     */
    @Override
    public void batchSaveDataField(Collection<NptLogicDataField> list) {
        if(null != list && !list.isEmpty()){
            dataFieldManager.saveAll(list);
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:23
     * 描述:
     * 批量更新机构
     *
     * @param list
     */
    @Override
    public void batchUpdateOrg(Collection<NptLogicDataProvider> list) {
        if(null != list && !list.isEmpty()){
            for(NptLogicDataProvider org:list){
                update(org);
            }
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:24
     * 描述:
     * 批量更新数据类别
     *
     * @param list
     */
    @Override
    public void batchUpdateDataType(Collection<NptLogicDataType> list) {
        if(null != list && !list.isEmpty()){
            for(NptLogicDataType type:list){
                update(type);
            }
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:24
     * 描述:
     * 批量更新数据类别字段
     *
     * @param list
     */
    @Override
    public void batchUpdateDataField(Collection<NptLogicDataField> list) {
        if(null != list && !list.isEmpty()){
            for(NptLogicDataField field:list){
                update(field);
            }
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/25 17:21
     * 描述:
     * 更新字段的显示顺序
     *
     * @param fieldIds
     */
    @Override
    public void updateFieldDisplayOrder(List<Long> fieldIds) {
        if(null != fieldIds && !fieldIds.isEmpty()){
            Collection<NptLogicDataField> fieldList = new ArrayList<>();
            for(int i = 0; i < fieldIds.size();i++){
                NptLogicDataField field = this.findDataFieldById(fieldIds.get(i));
                if(null != field){
                    field.setDisplayOrder(i);
                    fieldList.add(field);
                }
            }
            batchUpdateDataField(fieldList);
        }
    }

    /**
     * 作者：owen
     * 时间：2016/12/15 13:49
     * 描述:
     * 设置数据类别的数据主键
     *
     * @param typeId
     * @param fieldId
     */
    @Override
    public NptDict setDataTypeUField(Long typeId, Long fieldId) {
        NptLogicDataType thisType = this.findDataTypeById(typeId);
        NptLogicDataField thisField = this.fastFindDataFieldById(fieldId);
        if(null == thisType || null == thisField){
            return NptDict.RST_EXCEPTION("指定的数据类别或字段不存在![数据类别ID:" + typeId + "],[数据字段ID:" + fieldId + "]");
        }
        if(!thisField.getParentId().equals(typeId)){
            return NptDict.RST_EXCEPTION("指定的数据类别和数据字段非父子关系![数据类别ID:" + typeId + "],[数据字段ID:" + fieldId + "]");
        }

        thisType.setUkFieldId(fieldId);
        update(thisType);

        return NptDict.RST_SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 16/12/20 上午12:36
     * 备注: 新增机构前，检查是否可用, true可用
     */
    @Override
    public boolean checkOrg(NptLogicDataProvider org) {
        return organizationManager.countByCondition(Conditions.eq("orgCode", org.getOrgCode())) == 0
                && organizationManager.countByCondition(Conditions.eq("orgName", org.getOrgName())) == 0;
    }
}
