package com.npt.bridge.arch.manager.impl;

import com.npt.bridge.arch.dao.NptLogicDataTableDao;
import com.npt.bridge.arch.manager.NptLogicDataTableManager;
import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.base.manager.NptBaseManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 16:49
 * 描述:
 */
@Service
@Transactional
public class NptLogicDataTableManagerImpl extends NptBaseManagerImpl<NptLogicDataType, Long> implements NptLogicDataTableManager {

    @Autowired
    private NptLogicDataTableDao tableDao;

    @Override
    public NptBaseDao<NptLogicDataType, Long> getThisDao() {
        return tableDao;
    }
}
