package com.npt.querier.impl;

import com.npt.dict.NptDict;
import com.npt.model.bean.NptWebBridgeBean;
import com.npt.model.entity.NptBaseModel;
import com.npt.model.entity.NptBaseModelGroup;
import com.npt.model.entity.NptBaseModelPool;
import com.npt.model.service.NptModelService;
import com.npt.querier.NptCommonQuerier;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 项目： CreditPortal
 * 作者： owen
 * 时间： 2017/2/20 11:28
 * 描述：
 */
public abstract class NptAbstractCommonQuerier implements NptCommonQuerier {

    @Autowired
    private NptModelService service;

    public NptModelService getService() {
        return service;
    }

    /**
     * 作者：owen
     * 时间: 2017/2/20 11:17
     * 描述:
     * 获取某一专题的模型的基础信息
     */
    @Override
    public abstract NptBaseModel getThisModel();

    /**
     * 作者：owen
     * 时间: 2017/2/20 11:18
     * 描述:
     * 获取某一专题的模型的分组信息
     *
     * @param model
     */
    @Override
    public Collection<NptBaseModelGroup> getBaseModelGroups(NptBaseModel model) {
        return service.getBaseModelGroups(model);
    }

    @Override
    public Collection<NptBaseModelGroup> getBaseModelGroups(Long modelId) {
        NptBaseModel model = service.fastFindBaseModelById(modelId);
        if (model != null) {
            return service.getBaseModelGroups(model);
        } else {
            return new ArrayList<NptBaseModelGroup>();
        }
    }

    /**
     * 作者：owen
     * 时间: 2017/2/20 11:19
     * 描述:
     * 获取某一专题的模型的所有数据池信息
     *
     * @param model
     */
    @Override
    public Collection<NptBaseModelPool> getBaseModelPools(NptBaseModel model) {
        Collection<NptBaseModelPool> result = new ArrayList<NptBaseModelPool>();
        Collection<NptBaseModelGroup> groups = service.getBaseModelGroups(model);
        for (NptBaseModelGroup group : groups) {
            Collection<NptBaseModelPool> pools = service.getBaseModelGrouPools(group);
            result.addAll(pools);
        }
        return result;
    }

    @Override
    public Collection<NptBaseModelPool> getBaseModelPools(Long modelId) {
        NptBaseModel model = service.fastFindBaseModelById(modelId);
        if (model != null) {
            return this.getBaseModelPools(model);
        } else {
            return new ArrayList<NptBaseModelPool>();
        }
    }

    /**
     * 作者：owen
     * 时间: 2017/2/20 11:20
     * 描述:
     * 获取某一模型分组的所有数据池
     *
     * @param group
     */
    @Override
    public Collection<NptBaseModelPool> getBaseModelGroupPools(NptBaseModelGroup group) {
        return service.getBaseModelGrouPools(group);
    }

    @Override
    public Collection<NptBaseModelPool> getBaseModelGroupPools(Long groupId) {
        NptBaseModelGroup group = service.fastFindBaseModelGroupById(groupId);
        if (group != null) {
            return service.getBaseModelGrouPools(group);
        } else {
            return new ArrayList<NptBaseModelPool>();
        }
    }

    /**
     * 作者：owen
     * 时间: 2017/2/20 11:24
     * 描述:
     * 获取某一模型的主字段分页数据
     *
     * @param model
     * @param bean
     */
    @Override
    public NptDict getBaseModelMainFieldPaginationData(NptBaseModel model, NptWebBridgeBean bean) {
        return service.getModelMainFieldPaginationData(model, bean);
    }

    @Override
    public NptDict getBaseModelMainFieldPaginationData(Long modelId, NptWebBridgeBean bean) {
        NptBaseModel model = service.fastFindBaseModelById(modelId);
        if (model != null) {
            return service.getModelMainFieldPaginationData(model, bean);
        } else {
            return NptDict.RST_EXCEPTION("指定的模型不存在:模型ID[" + modelId + "]");
        }
    }

    /**
     * 作者：owen
     * 时间: 2017/2/20 11:25
     * 描述:
     * 获取某一数据池的分页数据
     *
     * @param pool
     * @param bean
     */
    @Override
    public NptDict getBaseModelPoolPaginationData(NptBaseModelPool pool, NptWebBridgeBean bean) {
        return service.getBaseModelGroupoolPaginationData(pool.getId(), bean);
    }

    @Override
    public NptDict getBaseModelPoolPaginationData(Long poolId, NptWebBridgeBean bean) {
        return service.getBaseModelGroupoolPaginationData(poolId, bean);
    }
}
