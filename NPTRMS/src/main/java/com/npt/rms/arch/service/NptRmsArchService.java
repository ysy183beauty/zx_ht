package com.npt.rms.arch.service;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.dict.NptDict;
import org.summer.extend.orm.Condition;

import java.util.Collection;
import java.util.List;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/9 23:05
 * 描述:
 */
public interface NptRmsArchService {

    /**
     *作者：OWEN
     *时间：2016/11/10 0:04
     *描述:
     *      获取根机构
     */
    NptLogicDataProvider getRootOrg();

    /**
     *作者：OWEN
     *时间：2016/11/9 23:52
     *描述:
     *      加载指定机构下挂的所有子机构
     */
    Collection<NptLogicDataProvider> listOrg(Long orgId);

    /**
     *作者：OWEN
     *时间：2016/11/10 16:14
     *描述:
     *      加载所有机构
     */
    Collection<NptLogicDataProvider> listAllOrg();

    /**
     *作者：OWEN
     *时间：2016/11/10 16:21
     *描述:
     *      加载所有正常启用的机构
     */
    Collection<NptLogicDataProvider> listAllEnabledOrg();

    /**
     *作者：OWEN
     *时间：2016/11/9 23:53
     *描述:
     *      加载指定机构下挂的所有直属数据类别
     */
    Collection<NptLogicDataType> listLinealDataType(Long orgId,NptDict status);

    /**
     *作者：OWEN
     *时间：2016/11/14 19:43
     *描述:
     *      加载指定机构及其所有子机构的数据类别
     */
    void listAvailableDataTypeForBaseModel(NptLogicDataProvider org, Collection<NptLogicDataType> result);

    /**
     *作者：OWEN
     *时间：2016/11/10 16:16
     *描述:
     *      加载所有数据类别
     */
    Collection<NptLogicDataType> listAllDataType();

    /**
     *作者：OWEN
     *时间：2016/11/10 16:21
     *描述:
     *      加载所有正常启用的数据字段
     */
    Collection<NptLogicDataType> listAllEnabledDataType();

    /**
     *作者：OWEN
     *时间：2016/11/9 23:54
     *描述:
     *      加载指定数据类别下挂的所有指定状态的数据字段
     */
    Collection<NptLogicDataField> listDataField(Long typeId, NptDict pubLevel, NptDict status);

    /**
     *作者：OWEN
     *时间：2016/12/2 18:05
     *描述:
     *      加载指定数据类别下挂的所有非指定状态的字段
     */
    Collection<NptLogicDataField> listDataFieldEx(Long typeId, NptDict status);

    /**
     *作者：OWEN
     *时间：2016/11/10 11:46
     *描述:
     *      依据数据字段ID列表批量进行加载
     */
    Collection<NptLogicDataField> listDataField(Collection<Long> idList);

    /**
     *作者：OWEN
     *时间：2016/11/10 16:17
     *描述:
     *      加载所有数据字段
     */
    Collection<NptLogicDataField> listAllDataField();

    /**
     *作者：OWEN
     *时间：2016/11/10 16:22
     *描述:
     *      加载所有正常启用的数据字段
     */
    Collection<NptLogicDataField> listAllEnabledDataField();

    /**
     *作者：OWEN
     *时间：2016/11/10 23:31
     *描述:
     *      多条件查询机构列表
     */
    Collection<NptLogicDataProvider> listOrganizationByConditions(Condition... conditionses);

    /**
     *作者：OWEN
     *时间：2016/11/10 23:32
     *描述:
     *      多条件查询数据类别
     */
    Collection<NptLogicDataType> listDataTypeByConditions(Condition... conditions);

    /**
     *作者：OWEN
     *时间：2016/11/10 23:33
     *描述:
     *      多条件查询数据类别字段
     */
    Collection<NptLogicDataField> listDataFieldByConditions(Condition... conditions);

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
    NptLogicDataProvider findParent(NptLogicDataType type);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:57
     *描述:
     *      依据数据字段ID获取数据字段所属的数据类别
     */
    NptLogicDataType findParent(NptLogicDataField field);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:06
     *描述:
     *      通过ID获取机构信息
     */
    NptLogicDataProvider findOrgById(Long id);
    NptLogicDataProvider fastFindOrgById(Long id);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:07
     *描述:
     *      通过ID获取数据类别
     */
    NptLogicDataType findDataTypeById(Long id);
    NptLogicDataType fastFindDataTypeById(Long id);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:07
     *描述:
     *      通过ID获取数据类别字段
     */
    NptLogicDataField findDataFieldById(Long id);
    NptLogicDataField fastFindDataFieldById(Long id);

    /**
     * 作者: 张磊
     * 日期: 17/2/24 上午9:57
     * 备注: 批量获取数据类别字段
     */
    Collection<NptLogicDataField> fastFindDataFieldById(Collection<Long> ids);

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
    void save(NptLogicDataType type);

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
    void update(NptLogicDataType type);

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
    void delete(NptLogicDataType type);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:13
     *描述:
     *      逻辑删除数据类别字段
     */
    void delete(NptLogicDataField field);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:25
     *描述:
     *      禁用机构
     */
    void disable(NptLogicDataProvider org);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:25
     *描述:
     *      禁用数据类别
     */
    void disable(NptLogicDataType type);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:25
     *描述:
     *      禁用数据类别字段
     */
    void disable(NptLogicDataField field);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:26
     *描述:
     *      启用机构
     */
    void enable(NptLogicDataProvider org);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:26
     *描述:
     *      启用数据类别
     */
    void enable(NptLogicDataType type);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:26
     *描述:
     *      启用数据类别字段
     */
    void enable(NptLogicDataField field);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:13
     *描述:
     *      批量保存机构
     */
    void batchSaveOrg(Collection<NptLogicDataProvider> list);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:22
     *描述:
     *      批量保存数据类别
     */
    void batchSaveDataType(Collection<NptLogicDataType> list);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:23
     *描述:
     *      批量保存数据类别字段
     */
    void batchSaveDataField(Collection<NptLogicDataField> list);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:23
     *描述:
     *      批量更新机构
     */
    void batchUpdateOrg(Collection<NptLogicDataProvider> list);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:24
     *描述:
     *      批量更新数据类别
     */
    void batchUpdateDataType(Collection<NptLogicDataType> list);

    /**
     *作者：OWEN
     *时间：2016/11/9 23:24
     *描述:
     *      批量更新数据类别字段
     */
    void batchUpdateDataField(Collection<NptLogicDataField> list);

    /**
     *作者：OWEN
     *时间：2016/11/25 17:21
     *描述:
     *      更新字段的显示顺序
     */
    void updateFieldDisplayOrder(List<Long> fieldIds);

    /**
     *作者：owen
     *时间：2016/12/15 13:49
     *描述:
     *      设置数据类别的数据主键
     */
    NptDict setDataTypeUField(Long typeId, Long fieldId);

    /**
     * 作者: 张磊
     * 日期: 16/12/20 上午12:36
     * 备注: 新增机构前，检查是否可用, true可用
     */
    boolean checkOrg(NptLogicDataProvider organization);
}
