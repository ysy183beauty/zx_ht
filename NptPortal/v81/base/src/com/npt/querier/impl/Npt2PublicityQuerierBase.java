package com.npt.querier.impl;

import com.npt.dict.NptDict;
import com.npt.model.entity.NptBaseModel;
import com.npt.querier.Npt2PublicityQuerier;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/2/13 14:52
 * 描述:
 */
public class Npt2PublicityQuerierBase extends NptAbstractCommonQuerier implements Npt2PublicityQuerier {

    /**
     * 作者：owen
     * 时间: 2017/2/20 11:17
     * 描述:
     * 获取某一专题的模型的基础信息
     */
    @Override
    public NptBaseModel getThisModel() {
        return getService().lookupModelByCategoryAndHost(NptDict.BMC_OUTSIDE, NptDict.BMH_2PUB);
    }
}
