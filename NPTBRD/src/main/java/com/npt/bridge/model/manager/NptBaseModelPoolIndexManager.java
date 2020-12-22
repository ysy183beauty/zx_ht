package com.npt.bridge.model.manager;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.base.NptBaseManager;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.NptBaseModelPoolIndex;

import java.util.Collection;

/**
 * 作者: owen
 * 时间: 2017/3/15 下午3:08
 * 描述:
 */
public interface NptBaseModelPoolIndexManager extends NptBaseManager<NptBaseModelPoolIndex,Long>{

    /**
     * 作者：owen
     * 时间：2017/3/16 下午8:35
     * 描述：
     *      获取模型主数据池的索引字段
     */
    Collection<NptBaseModelPoolIndex> getBaseModelIndex(NptBaseModel model);


    /**
     * 作者: owen
     * 时间: 2017/3/15 下午3:13
     * 描述:
     *      获取模型数据池的索引字段
     */
    Collection<NptBaseModelPoolIndex> getBaseModelPoolIndex(NptBaseModelPool pool);

    /**
     * 作者：owen
     * 日期：2016/10/20 12:18
     * 备注：
     *      获取字段详情
     * 参数：
     * 返回：
     */
    NptLogicDataField getBaseModelGrouPoolFieldById(Long id);
}
