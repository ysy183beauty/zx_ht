package com.npt.bridge.model.manager.impl;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.service.NptArchService;
import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.base.manager.NptBaseManagerImpl;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.NptBaseModelPoolIndex;
import com.npt.bridge.model.dao.NptBaseModelPoolIndexDao;
import com.npt.bridge.model.manager.NptBaseModelPoolIndexManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 作者: owen
 * 时间: 2017/3/15 下午3:08
 * 描述:
 */
@Service
@Transactional
public class NptBaseModelPoolIndexManagerImpl extends NptBaseManagerImpl<NptBaseModelPoolIndex,Long> implements NptBaseModelPoolIndexManager{

    @Autowired
    private NptBaseModelPoolIndexDao indexDao;
    @Autowired
    private NptArchService archService;

    @Override
    public NptBaseDao<NptBaseModelPoolIndex, Long> getThisDao() {
        return indexDao;
    }


    /**
     * 作者：owen
     * 时间：2017/3/16 下午8:35
     * 描述：
     * 获取模型主数据池的索引字段
     *
     * @param model
     */
    @Override
    public Collection<NptBaseModelPoolIndex> getBaseModelIndex(NptBaseModel model) {
        return null;
    }

    /**
     * 作者: owen
     * 时间: 2017/3/15 下午3:13
     * 描述:
     * 获取模型数据池的索引字段
     *
     * @param pool
     */
    @Override
    public Collection<NptBaseModelPoolIndex> getBaseModelPoolIndex(NptBaseModelPool pool) {
        return null;
    }

    /**
     * 作者：owen
     * 日期：2016/10/20 12:18
     * 备注：
     * 获取字段详情
     * 参数：
     * 返回：
     *
     * @param id
     */
    @Override
    public NptLogicDataField getBaseModelGrouPoolFieldById(Long id) {
        return archService.fastFindDataFieldById(id);
    }
}
