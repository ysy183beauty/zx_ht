package com.npt.querier.impl;

import com.npt.dict.NptDict;
import com.npt.model.entity.NptBaseModel;
import com.npt.querier.NptEnterpriseQuerier;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/2/13 14:51
 * 描述:
 */
public class NptEnterpriseQuerierBase extends NptAbstractCommonQuerier implements NptEnterpriseQuerier {
    /**
     * 作者：owen
     * 时间: 2017/2/20 11:17
     * 描述:
     * 获取某一专题的模型的基础信息
     */
    @Override
    public NptBaseModel getThisModel() {
        return getService().lookupModelByCategoryAndHost(NptDict.BMC_OUTSIDE, NptDict.BMH_BUSINESS);
    }
}
