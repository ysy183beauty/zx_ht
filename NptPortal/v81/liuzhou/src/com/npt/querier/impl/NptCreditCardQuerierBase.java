package com.npt.querier.impl;

import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.querier.NptCreditCardQuerier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 项目: 柳州征信门户
 * 作者: zhanglei
 * 日期: 2017/6/14
 * 备注:
 */
@Service
@Transactional
public class NptCreditCardQuerierBase extends NptAbstractCommonQuerier implements NptCreditCardQuerier {
    @Override
    public NptBaseModel getThisModel() {
        return null;
    }

    /**
     *  author: zhanglei
     *  date:   2017/06/14 16:09
     *  note:
     *          根据用户类型加载企业/个人信用名片模型
     */
    @Override
    public NptBaseModel getThisModel(Integer userType) {
        NptBaseModel model;
        if (userType == NptDict.PDM_ENTERPRISE.getCode()) {
            model = getService().lookupModelByCategoryAndHost(NptDict.BMC_OUTSIDE, NptDict.BMH_BSCARD);
        } else {
            model = getService().lookupModelByCategoryAndHost(NptDict.BMC_OUTSIDE, NptDict.BMH_PSCARD);
        }
        return model;
    }
}
