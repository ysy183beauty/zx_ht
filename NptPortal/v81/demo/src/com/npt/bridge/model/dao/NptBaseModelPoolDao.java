package com.npt.bridge.model.dao;

import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.model.NptBaseModelPool;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:02
 * 描述:
 */
public interface NptBaseModelPoolDao extends NptBaseDao<NptBaseModelPool, Long> {
    /**
     * 作者: 张磊
     * 日期: 17/2/16 上午11:55
     * 备注: 获取模型的主数据池
     */
    NptBaseModelPool getBaseModelGroupMainPool(NptBaseModel m);

    /**
     * 作者: 张磊
     * 日期: 17/2/17 下午1:33
     * 备注: 获取分组下的所有数据池
     */
    Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModelGroup group, NptDict ll);

    /**
     * 作者: 张磊
     * 日期: 2017/03/13 下午01:48
     * 备注: 获取模型下的所有数据池
     */
    Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModel model,NptDict ll);
}
