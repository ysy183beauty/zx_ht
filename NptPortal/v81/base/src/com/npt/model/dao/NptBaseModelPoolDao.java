package com.npt.model.dao;

import com.npt.base.NptBaseDao;
import com.npt.model.entity.NptBaseModel;
import com.npt.model.entity.NptBaseModelGroup;
import com.npt.model.entity.NptBaseModelPool;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:02
 * 描述:
 */
public interface NptBaseModelPoolDao extends NptBaseDao<NptBaseModelPool>{
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
    Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModelGroup group);
}
