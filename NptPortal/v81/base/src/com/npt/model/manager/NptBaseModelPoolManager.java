package com.npt.model.manager;

import com.npt.base.NptBaseManager;
import com.npt.model.entity.NptBaseModel;
import com.npt.model.entity.NptBaseModelGroup;
import com.npt.model.entity.NptBaseModelPool;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:00
 * 描述:
 */
public interface NptBaseModelPoolManager extends NptBaseManager<NptBaseModelPool>{
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
    Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModelGroup group);
}
