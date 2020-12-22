package com.npt.querier;

import com.npt.dict.NptDict;
import com.npt.model.bean.NptWebBridgeBean;
import com.npt.model.entity.NptBaseModel;
import com.npt.model.entity.NptBaseModelGroup;
import com.npt.model.entity.NptBaseModelPool;

import java.util.Collection;

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
     *作者：owen
     *时间: 2017/2/20 11:20
     *描述:
     *      获取某一模型分组的所有数据池
     */
    Collection<NptBaseModelPool> getBaseModelGroupPools(NptBaseModelGroup group);
    Collection<NptBaseModelPool> getBaseModelGroupPools(Long groupId);

    /**
     *作者：owen
     *时间: 2017/2/20 11:24
     *描述:
     *      获取某一模型的主字段分页数据
     */
    NptDict getBaseModelMainFieldPaginationData(NptBaseModel model, NptWebBridgeBean bean);
    NptDict getBaseModelMainFieldPaginationData(Long modelId,NptWebBridgeBean bean);

    /**
     *作者：owen
     *时间: 2017/2/20 11:25
     *描述:
     *      获取某一数据池的分页数据
     */
    NptDict getBaseModelPoolPaginationData(NptBaseModelPool pool,NptWebBridgeBean bean);
    NptDict getBaseModelPoolPaginationData(Long poolId,NptWebBridgeBean bean);
}
