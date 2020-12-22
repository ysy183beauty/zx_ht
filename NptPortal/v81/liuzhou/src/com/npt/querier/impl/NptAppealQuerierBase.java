package com.npt.querier.impl;

import com.npt.bridge.model.NptBaseModel;
import com.npt.querier.NptAppealQuerier;
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
public class NptAppealQuerierBase extends NptAbstractCommonQuerier implements NptAppealQuerier{
    @Override
    public NptBaseModel getThisModel() {
        return null;
    }
}
