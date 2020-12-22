package com.npt.bridge.model.dao;

import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.NptBaseModelPoolIndex;

import java.util.Collection;

/**
 * 作者: owen
 * 时间: 2017/3/15 下午3:09
 * 描述:
 */
public interface NptBaseModelPoolIndexDao extends NptBaseDao<NptBaseModelPoolIndex,Long>{

    /**
     * 作者：owen
     * 时间：2017/3/16 下午8:36
     * 描述：
     *      获取数据池的索引字段
     */
    Collection<NptBaseModelPoolIndex> getBaseModelPoolIndex(NptBaseModelPool pool);
}
