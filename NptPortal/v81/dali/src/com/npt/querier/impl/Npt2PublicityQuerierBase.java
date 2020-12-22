package com.npt.querier.impl;

import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.querier.Npt2PublicityQuerier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/2/13 14:52
 * 描述:
 */
@Service
@Transactional
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
