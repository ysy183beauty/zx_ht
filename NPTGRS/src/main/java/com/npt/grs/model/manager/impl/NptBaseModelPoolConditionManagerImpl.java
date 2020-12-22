package com.npt.grs.model.manager.impl;

import com.npt.bridge.model.NptBaseModelPoolCdt;
import com.npt.grs.model.dao.NptBaseModelPoolConditionDao;
import com.npt.grs.model.manager.NptBaseModelPoolConditionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.summer.extend.manager.BaseManagerImpl;

/**
 * 项目: NPTWebApp
 * 作者: zhanglei
 * 日期: 2017/3/14
 * 备注:
 */
@Service
public class NptBaseModelPoolConditionManagerImpl extends BaseManagerImpl<NptBaseModelPoolCdt> implements NptBaseModelPoolConditionManager {
    @Autowired
    private NptBaseModelPoolConditionDao baseModelPoolConditionDao;
}
