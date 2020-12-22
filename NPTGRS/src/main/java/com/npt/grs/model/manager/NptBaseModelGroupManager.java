package com.npt.grs.model.manager;

import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.rms.base.manager.NptCachedManager;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/20 11:34
 * 备注：
 */
public interface NptBaseModelGroupManager extends NptCachedManager<NptBaseModelGroup> {

    /**
     * 作者：owen
     * 日期：2016/10/20 11:54
     * 备注：
     *      获取模型下的所有分组
     * 参数：
     * 返回：
     */
    Collection<NptBaseModelGroup> getBaseModelGroups(NptBaseModel m);

}
