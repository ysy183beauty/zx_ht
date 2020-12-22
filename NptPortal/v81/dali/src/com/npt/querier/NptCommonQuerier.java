package com.npt.querier;

import com.jeecms.core.entity.CmsUser;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.dataBinder.NptWebFieldDataArray;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.NptBaseModelStatistic;

import java.util.Collection;
import java.util.Map;

/**
 * 项目： CreditPortal
 * 作者： owen
 * 时间： 2017/2/20 11:15
 * 描述：
 *
 *      查询器通用接口集
 */
public interface NptCommonQuerier {

    /**
     *作者：owen
     *时间: 2017/2/20 11:17
     *描述:
     *      获取某一专题的模型的基础信息
     */
    NptBaseModel getThisModel();

    /**
     *作者：owen
     *时间: 2017/2/20 11:18
     *描述:
     *      获取某一专题的模型的分组信息
     */
    Collection<NptBaseModelGroup> getBaseModelGroups(NptBaseModel model);
    Collection<NptBaseModelGroup> getBaseModelGroups(Long modelId);

    /**
     *作者：owen
     *时间: 2017/2/20 11:19
     *描述:
     *      获取某一专题的模型的所有数据池信息
     */
    Collection<NptBaseModelPool> getBaseModelPools(NptBaseModel model);
    Collection<NptBaseModelPool> getBaseModelPools(Long modelId);

    /**
     * 作者：owen
     * 时间：2017/3/17 下午12:36
     * 描述：
     *      以数据提供者进行分组的数据池
     */
    Map<String, Collection<NptBaseModelPool>> getBaseModelProviderPoolsMap(NptBaseModel model);

    Map<String, Collection<NptBaseModelPool>> getBaseModelProviderPoolsMap(Long modelId);

    /**
     *作者：owen
     *时间: 2017/2/20 11:20
     *描述:
     *      获取某一模型分组的所有数据池
     */
    Collection<NptBaseModelPool> getBaseModelGroupPools(NptBaseModelGroup group);
    Collection<NptBaseModelPool> getBaseModelGroupPools(Long groupId);
    Collection<NptBaseModelPool> getBaseModelGroupPools(NptDict gCode);

    /**
     * 作者：owen
     * 时间：2017/3/17 下午12:41
     * 描述：
     *      以数据提供者进行分组的数据池
     */
    Map<String, Collection<NptBaseModelPool>> getBaseModelGroupProviderPoolsMap(NptBaseModelGroup group);

    Map<String, Collection<NptBaseModelPool>> getBaseModelGroupProviderPoolsMap(Long groupId);

    Map<String, Collection<NptBaseModelPool>> getBaseModelGroupProviderPoolsMap(NptDict gCode);


    /**
     *作者：owen
     *时间: 2017/2/20 11:24
     *描述:
     *      获取某一模型的主字段分页数据
     */
    NptDict getBaseModelIndexFieldPaginationData(NptBaseModel model, NptWebBridgeBean bean,Boolean exact);
    NptDict getBaseModelIndexFieldPaginationData(Long modelId, NptWebBridgeBean bean,Boolean exact);

    /**
     *作者：owen
     *时间: 2017/2/20 11:25
     *描述:
     *      获取某一数据池的分页数据
     */
    NptDict getBaseModelPoolPaginationData(NptBaseModelPool pool,NptWebBridgeBean bean,Boolean onlyIndex,Boolean exact);
    NptDict getBaseModelPoolPaginationData(Long poolId,NptWebBridgeBean bean,Boolean onlyIndex,Boolean exact);

    NptDict loadBaseModelAuthGroupsByUK(NptWebBridgeBean bean, NptBaseModel m,Boolean applyed);

    /**
     * 作者：owen
     * 时间：2017/3/17 下午3:55
     * 描述：
     *      获取指定数据池指定数据主键的唯一一行记录
     */
    NptWebFieldDataArray getBaseModelPoolRowData(Long poolId,String uFieldValue);

    /**
     *作者：owen
     *时间: 2017/5/3 10:22
     *描述:
     *      获取模型核心数据池的唯一一行记录
     */
    NptWebFieldDataArray getBaseModelMainPoolRowData(String uFieldValue);

    /**
     *  author: owen
     *  date:   2017/3/23 下午1:59
     *  note:
     *          在模型内部以关键字进行模糊查询，查询依据为模型中主数据池（若存在）或每个普通数据
     *          池的条件字段
     */
    NptDict fuzzySearch(String keyword,NptWebBridgeBean bean);


    /**
     *  author: owen
     *  date:   2017/4/9 下午3:09
     *  note:
     *          加载企业或个人的实名信用信息
     */
    NptDict loadAuthedInfo(NptBaseModel model,NptWebBridgeBean bean,CmsUser user,Boolean applyed);

    /**
     *  author: owen
     *  date:   2017/4/17 下午2:52
     *  note:
     *          获取链式模型（有核心数据池）的信息体量
     */
    Long getBaseModelEntityCount();

    /**
     *作者：owen
     *时间: 2017/5/4 17:27
     *描述:
     *      加载模型的基础统计信息
     */
    NptBaseModelStatistic loadBaseModelStatitic(NptBaseModel model);
}
