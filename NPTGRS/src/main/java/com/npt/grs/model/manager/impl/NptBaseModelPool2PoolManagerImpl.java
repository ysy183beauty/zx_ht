package com.npt.grs.model.manager.impl;

import com.npt.grs.model.dao.NptBaseModelPool2GroupDao;
import com.npt.bridge.model.NptBaseModelLink;
import com.npt.grs.model.manager.NptBaseModelPool2PoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.summer.extend.manager.BaseManagerImpl;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/24 21:14
 * 备注：
 */
@Service
public class NptBaseModelPool2PoolManagerImpl extends BaseManagerImpl<NptBaseModelLink> implements NptBaseModelPool2PoolManager {
    @Autowired
    private NptBaseModelPool2GroupDao baseModelPool2GroupDao;
}
