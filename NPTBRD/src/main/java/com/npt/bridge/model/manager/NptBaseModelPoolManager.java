package com.npt.bridge.model.manager;


import com.npt.bridge.base.NptBaseManager;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.model.NptBaseModelPool;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:00
 * 描述:
 */
public interface NptBaseModelPoolManager extends NptBaseManager<NptBaseModelPool, Long> {
    Integer DEFAULT_POOL_LOADED_COUNT = 50;

    /**
     * 作者: 张磊
     * 日期: 17/2/16 上午11:52
     * 备注: 获取模型的主数据池
     */
    NptBaseModelPool getBaseModelGroupMainPool(NptBaseModel model);

    /**
     * 作者: 张磊
     * 日期: 17/2/17 下午1:33
     * 备注: 获取分组下的所有数据池
     */
    Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModelGroup group, NptDict lockLevel);

    /**
     * 作者: 张磊
     * 日期: 2017/03/13 下午01:48
     * 备注: 获取模型下的所有数据池
     */
    Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModel model,NptDict lockLevel);
}
