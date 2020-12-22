package com.npt.querier.impl;

import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.querier.NptCreditTravelQuerier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/4/13
 * 备注:
 */
@Service
@Transactional
public class NptCreditTravelQuerierBase extends NptAbstractCommonQuerier implements NptCreditTravelQuerier {

    @Override
    public NptBaseModel getThisModel() {
        return getService().lookupModelByCategoryAndHost(NptDict.BMC_OUTSIDE, NptDict.BMH_TRAVEL);
    }
}
