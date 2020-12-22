package com.npt.grs.model.manager;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.rms.base.manager.NptCachedManager;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/10 20:21
 * 备注：
 */
public interface NptBaseModelGrouPoolManager extends NptCachedManager<NptBaseModelPool> {

    Integer DEFAULT_POOL_LOADED_COUNT = 50;

    /**
     * 作者：owen
     * 日期：2016/10/20 12:09
     * 备注：
     *      获取模型组的所有数据池
     * 参数：
     * 返回：
     */
    Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModelGroup group);


    /**
     * 作者：owen
     * 日期：2016/10/20 12:10
     * 备注：
     *      获取模型组数据池的所有字段
     * 参数：
     * 返回：
     */
    Collection<NptLogicDataField> getBaseModelGrouPoolFields(NptBaseModelPool p, NptDict status);


    /**
     * 作者：owen
     * 日期：2016/10/31 14:36
     * 备注：
     *      加载默认不可视的字段集
     * 参数：
     * 返回：
     */
    Collection<NptLogicDataField> getBaseModelGrouPoolDefaultHiddenFields(NptBaseModelPool p);

    /**
     * 作者：owen
     * 日期：2016/10/20 12:11
     * 备注：
     *      获取模型组数据池的物理表详情
     * 参数：
     * 返回：
     */
    NptLogicDataType getBaseModelGrouPoolDataType(NptBaseModelPool p);

    /**
     * 作者：owen
     * 日期：2016/10/20 12:13
     * 备注：
     *      获取模型组数据池物理表的提供者详情
     * 参数：
     * 返回：
     */
    NptLogicDataProvider getBaseModelGrouPoolDataTypeProvider(NptBaseModelPool p);

    /**
     * 作者：owen
     * 日期：2016/10/26 13:57
     * 备注：
     *      获取指定的OGR信息
     * 参数：
     * 返回：
     */
    NptLogicDataProvider getBaseModelGrouPoolDataTypeProviderById(Long id);

    /**
     * 作者：owen
     * 日期：2016/10/20 13:59
     * 备注：
     *      获取基本模型的主数据池
     * 参数：
     * 返回：
     */
    NptBaseModelPool getBaseModelMainGrouPool(NptBaseModel m);
}
