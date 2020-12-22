package com.npt.bridge.arch.service.impl;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.arch.manager.NptLogicDataFieldManager;
import com.npt.bridge.arch.manager.NptLogicDataProviderManager;
import com.npt.bridge.arch.manager.NptLogicDataTableManager;
import com.npt.bridge.arch.service.NptArchService;
import com.npt.bridge.dict.NptDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 12:04
 * 描述:
 */
@Service
@Transactional
public class NptArchServiceImpl implements NptArchService{

    @Autowired
    private NptLogicDataProviderManager providerManager;
    @Autowired
    private NptLogicDataTableManager tableManager;
    @Autowired
    private NptLogicDataFieldManager fieldManager;

    /**
     * 作者：OWEN
     * 时间：2016/11/9 23:54
     * 描述:
     * 加载指定数据类别下挂的所有指定状态的数据字段
     *
     * @param typeId
     * @param pubLevel
     * @param status
     */
    @Override
    public Collection<NptLogicDataField> listDataField(Long typeId, NptDict pubLevel, NptDict status) {
        Collection<NptLogicDataField> result = new ArrayList<>();
        NptLogicDataType type = tableManager.findById(typeId);
        if(null != type){
            result = fieldManager.listDataField(typeId, pubLevel, status);
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
            NptLogicDataType _this = tableManager.findById(type.getId());
            if(null != _this){
                return providerManager.findById(_this.getParentId());
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
    public NptLogicDataProvider findProviderById(Long id) {
        return providerManager.findById(id);
    }

    @Override
    public NptLogicDataProvider fastFindProviderById(Long id) {
        return providerManager.findById(id);
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
    public NptLogicDataType findDataTableById(Long id) {
        return tableManager.findById(id);
    }

    @Override
    public NptLogicDataType fastFindDataTableById(Long id) {
        return tableManager.fastFindById(id);
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
        return fieldManager.findById(id);
    }

    @Override
    public NptLogicDataField fastFindDataFieldById(Long id) {
        return fieldManager.findById(id);
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
        providerManager.save(org);
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
        tableManager.save(type);
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
        fieldManager.save(field);
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
        providerManager.update(org);
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
        tableManager.update(type);
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
        fieldManager.update(field);
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
        providerManager.delete(org);
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
        tableManager.delete(type);
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
        try {
            fieldManager.delete(field);
        }catch (Exception e){
            e.printStackTrace();
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
    public void batchSaveDataTable(Collection<NptLogicDataType> list) {
        if(null != list && !list.isEmpty()){
            tableManager.saveAll(list);
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
            fieldManager.saveAll(list);
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
    public void batchUpdateDataTable(Collection<NptLogicDataType> list) {
        if(null != list && !list.isEmpty()){
            tableManager.updateAll(list);
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
            fieldManager.updateAll(list);
        }
    }

    /**
     * 作者：owen
     * 时间：2017/1/17 12:18
     * 描述:
     * 批量保存数据提供者
     *
     * @param list
     */
    @Override
    public void batchSaveProvider(Collection<NptLogicDataProvider> list) {
        if(null != list && !list.isEmpty()){
            providerManager.saveAll(list);
        }
    }

    /**
     * 作者：owen
     * 时间：2017/1/17 12:18
     * 描述:
     * 批量更新数据提供者
     *
     * @param list
     */
    @Override
    public void batchUpdateProvider(Collection<NptLogicDataProvider> list) {
        if(null != list && !list.isEmpty()){
            providerManager.updateAll(list);
        }
    }

    @Override
    public NptLogicDataType findDataTypeById(Long id) {
        return tableManager.findById(id);
    }

    @Override
    public NptLogicDataType fastFindDataTypeById(Long id) {
        return tableManager.fastFindById(id);
    }

}
