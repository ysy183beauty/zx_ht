package com.npt.bridge.model.manager.impl;

import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.base.manager.NptBaseManagerImpl;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.model.dao.NptBaseModelGroupDao;
import com.npt.bridge.model.manager.NptBaseModelGroupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:09
 * 描述:
 */
@Service
@Transactional
public class NptBaseModelGroupManagerImpl extends NptBaseManagerImpl<NptBaseModelGroup, Long> implements NptBaseModelGroupManager {

    @Autowired
    private NptBaseModelGroupDao groupDao;

    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午11:52
     * 备注: 获取基础模型的所有分组
     */
    @Override
    public Collection<NptBaseModelGroup> getBaseModelGroups(NptBaseModel m) {
        return groupDao.getBaseModelGroups(m);
    }

    @Override
    public NptBaseDao<NptBaseModelGroup, Long> getThisDao() {
        return groupDao;
    }
}
