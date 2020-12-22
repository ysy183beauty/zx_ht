package com.npt.arch.service;

import com.npt.arch.entity.NptLogicDataField;
import com.npt.arch.entity.NptLogicDataProvider;
import com.npt.arch.entity.NptLogicDataTable;
import com.npt.dict.NptDict;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 12:03
 * 描述:
 */
public interface NptArchService {

    /**
     *作者：OWEN
     *时间：2016/11/9 23:54
     *描述:
     *      加载指定数据类别下挂的所有指定状态的数据字段
     */
    Collection<NptLogicDataField> listDataField(Long typeId, NptDict pubLevel, NptDict status);


    /**
     *作者：OWEN
     *时间：2016/11/9 23:56
     *描述:
     *      依据机构ID获取机构所属的父机构
     */
    NptLogicDataProvider findParent(NptLogicDataProvider org);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:55
     *描述:
     *      依据数据类别ID获取数据类别所属的机构
     */
    NptLogicDataProvider findParent(NptLogicDataTable type);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:57
     *描述:
     *      依据数据字段ID获取数据字段所属的数据类别
     */
    NptLogicDataTable findParent(NptLogicDataField field);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:06
     *描述:
     *      通过ID获取机构信息
     */
    NptLogicDataProvider findProviderById(Long id);
    NptLogicDataProvider fastFindProviderById(Long id);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:07
     *描述:
     *      通过ID获取数据类别
     */
    NptLogicDataTable findDataTableById(Long id);
    NptLogicDataTable fastFindDataTableById(Long id);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:07
     *描述:
     *      通过ID获取数据类别字段
     */
    NptLogicDataField findDataFieldById(Long id);
    NptLogicDataField fastFindDataFieldById(Long id);


    /**
     *作者：OWEN
     *时间：2016/11/9 23:08
     *描述:
     *      保存机构信息
     */
    void save(NptLogicDataProvider org);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:09
     *描述:
     *      保存数据类别
     */
    void save(NptLogicDataTable type);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:10
     *描述:
     *      保存数据类别字段
     */
    void save(NptLogicDataField field);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:10
     *描述:
     *      更新机构信息
     */
    void update(NptLogicDataProvider org);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:10
     *描述:
     *      更新数据类别
     */
    void update(NptLogicDataTable type);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:11
     *描述:
     *      更新数据类别字段
     */
    void update(NptLogicDataField field);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:11
     *描述:
     *      逻辑删除指定机构
     */
    void delete(NptLogicDataProvider org);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:12
     *描述:
     *      逻辑删除数据类别
     */
    void delete(NptLogicDataTable type);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:13
     *描述:
     *      逻辑删除数据类别字段
     */
    void delete(NptLogicDataField field);


    /**
     *作者：OWEN
     *时间：2016/11/9 23:22
     *描述:
     *      批量保存数据类别
     */
    void batchSaveDataTable(Collection<NptLogicDataTable> list);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:23
     *描述:
     *      批量保存数据类别字段
     */
    void batchSaveDataField(Collection<NptLogicDataField> list);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:24
     *描述:
     *      批量更新数据类别
     */
    void batchUpdateDataTable(Collection<NptLogicDataTable> list);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:24
     *描述:
     *      批量更新数据类别字段
     */
    void batchUpdateDataField(Collection<NptLogicDataField> list);


    /**
     *作者：owen
     *时间：2017/1/17 12:18
     *描述:
     *      批量保存数据提供者
     */
    void batchSaveProvider(Collection<NptLogicDataProvider> c);

    /**
     *作者：owen
     *时间：2017/1/17 12:18
     *描述:
     *      批量更新数据提供者
     */
    void batchUpdateProvider(Collection<NptLogicDataProvider> c);
}
