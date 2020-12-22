package com.npt.bridge.model.manager;


import com.npt.bridge.base.NptBaseManager;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelGroup;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:00
 * 描述:
 */
public interface NptBaseModelGroupManager extends NptBaseManager<NptBaseModelGroup, Long> {
    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午11:51
     * 备注: 获取基础模型的所有分组
     */
    Collection<NptBaseModelGroup> getBaseModelGroups(NptBaseModel m);
}
