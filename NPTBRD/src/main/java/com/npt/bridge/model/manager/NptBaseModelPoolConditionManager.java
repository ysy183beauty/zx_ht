package com.npt.bridge.model.manager;

import com.npt.bridge.base.NptBaseManager;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.NptBaseModelPoolCdt;

import java.util.Collection;

/**
 * 作者: owen
 * 时间: 2017/3/13 下午9:49
 * 描述:
 */
public interface NptBaseModelPoolConditionManager extends NptBaseManager<NptBaseModelPoolCdt, Long> {

    /**
     * 作者: owen
     * 时间: 2017/3/13 下午9:57
     * 描述:
     *      查询数据池的所有状态可用的查询字段
     */
    Collection<NptBaseModelPoolCdt> getPoolSearchConditions(NptBaseModelPool p);
}
