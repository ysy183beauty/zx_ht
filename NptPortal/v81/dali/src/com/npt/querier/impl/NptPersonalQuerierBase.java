package com.npt.querier.impl;

import com.jeecms.core.entity.CmsUser;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.querier.NptPersonalQuerier;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/3/15
 * 备注:
 */
@Service
@Transactional
public class NptPersonalQuerierBase extends NptAbstractCommonQuerier implements NptPersonalQuerier {
    @Override
    public NptBaseModel getThisModel() {
        return getService().lookupModelByCategoryAndHost(NptDict.BMC_OUTSIDE, NptDict.BMH_PERSONAL);
    }
}
