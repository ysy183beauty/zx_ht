package com.npt.querier.impl;

import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.querier.NptCreditSpaqQuerier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 项目: 柳州征信门户
 * 作者: zhanglei
 * 日期: 2017/6/22
 * 备注:
 */
@Service
@Transactional
public class NptCreditSpaqQuerierBase extends NptAbstractCommonQuerier implements NptCreditSpaqQuerier {
    @Override
    public NptBaseModel getThisModel() {
        return getService().lookupModelByCategoryAndHost(NptDict.BMC_OUTSIDE, NptDict.BMH_SPAQ);
    }
}
