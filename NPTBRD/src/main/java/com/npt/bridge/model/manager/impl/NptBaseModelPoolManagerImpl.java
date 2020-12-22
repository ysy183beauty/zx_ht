package com.npt.bridge.model.manager.impl;

import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.base.manager.NptBaseManagerImpl;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.dao.NptBaseModelPoolDao;
import com.npt.bridge.model.manager.NptBaseModelPoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:10
 * 描述:
 */
@Service
@Transactional
public class NptBaseModelPoolManagerImpl extends NptBaseManagerImpl<NptBaseModelPool, Long> implements NptBaseModelPoolManager {

    @Autowired
    private NptBaseModelPoolDao poolDao;

    /**
     * 作者: 张磊
     * 日期: 17/2/16 上午11:52
     * 备注: 获取模型的主数据池
     */
    @Override
    public NptBaseModelPool getBaseModelGroupMainPool(NptBaseModel m) {
        if (null == m) return null;
        return poolDao.getBaseModelGroupMainPool(m);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/17 下午1:33
     * 备注: 获取分组下的所有数据池
     */
    @Override
    public Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModelGroup group, NptDict lockLv) {
        return poolDao.getBaseModelGrouPools(group,lockLv);
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/13 下午01:48
     * 备注: 获取模型下的所有数据池
     */
    @Override
    public Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModel model,NptDict lockLv) {
        return poolDao.getBaseModelGrouPools(model,lockLv);
    }

    @Override
    public NptBaseDao<NptBaseModelPool, Long> getThisDao() {
        return poolDao;
    }
}
