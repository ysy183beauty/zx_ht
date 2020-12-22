package com.npt.grs.model.manager;

import com.npt.bridge.model.NptBaseModel;
import com.npt.rms.base.manager.NptCachedManager;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/10 20:21
 * 备注：
 */
public interface NptBaseModelManager extends NptCachedManager<NptBaseModel> {

    /**
     * 作者：owen
     * 日期：2016/10/20 11:45
     * 备注：
     *      加载所有模型实体
     * 参数：
     * 返回：
     */
    Collection<NptBaseModel> listAll();

    /**
     * 作者：owen
     * 日期：2016/10/20 11:45
     * 备注：
     *      根据宿主加载模型实体
     * 参数：
     * 返回：
     */
    Collection<NptBaseModel> listByHost(Long id);
}
