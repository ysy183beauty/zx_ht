package com.npt.grs.model.manager;

import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.model.NptBaseModelPoolIndex;
import com.npt.rms.base.manager.NptCachedManager;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/11 15:38
 * 备注：
 */
public interface NptBaseModelPoolIndexManager extends NptCachedManager<NptBaseModelPoolIndex> {

    /**
     * 作者：owen
     * 日期：2016/10/20 12:17
     * 备注：
     *      获取模型组的列表主字段
     * 参数：
     * 返回：
     */
    Collection<NptLogicDataField> getBaseModelGroupMFields(NptBaseModelGroup group);

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
