package com.npt.bridge.arch.manager.impl;

import com.npt.bridge.arch.dao.NptLogicDataFieldDao;
import com.npt.bridge.arch.manager.NptLogicDataFieldManager;
import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.base.manager.NptBaseManagerImpl;
import com.npt.bridge.dict.NptDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 16:48
 * 描述:
 */
@Service
@Transactional
public class NptLogicDataFieldManagerImpl extends NptBaseManagerImpl<NptLogicDataField, Long> implements NptLogicDataFieldManager {

    @Autowired
    private NptLogicDataFieldDao fieldDao;

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午5:50
     * 备注: 加载指定数据类别下挂的所有指定状态的数据字段
     */
    @Override
    public Collection<NptLogicDataField> listDataField(Long typeId, NptDict pubLevel, NptDict status) {
        return fieldDao.listDataField(typeId, pubLevel, status);
    }

    @Override
    public NptBaseDao<NptLogicDataField, Long> getThisDao() {
        return fieldDao;
    }
}
