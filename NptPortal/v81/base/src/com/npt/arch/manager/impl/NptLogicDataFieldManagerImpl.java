package com.npt.arch.manager.impl;

import com.npt.arch.dao.NptLogicDataFieldDao;
import com.npt.arch.entity.NptLogicDataField;
import com.npt.arch.manager.NptLogicDataFieldManager;
import com.npt.dict.NptDict;
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
public class NptLogicDataFieldManagerImpl implements NptLogicDataFieldManager{

    @Autowired
    private NptLogicDataFieldDao fieldDao;


    /**
     * 作者：owen
     * 时间：2017/1/16 20:59
     * 描述:
     * 依据ID查找
     *
     * @param id
     */
    @Override
    public NptLogicDataField findById(Long id) {
        return fieldDao.findById(id);
    }

    /**
     * 作者：owen
     * 时间：2017/1/16 21:00
     * 描述:
     * 优先从缓存中查找
     *
     * @param id
     */
    @Override
    public NptLogicDataField fastFindById(Long id) {
        return fieldDao.findById(id);
    }

    /**
     * 作者：owen
     * 时间：2017/1/16 21:18
     * 描述:
     * 加载列表
     */
    @Override
    public Collection<NptLogicDataField> getList() {
        return fieldDao.getList();
    }

    @Override
    public void save(NptLogicDataField model) {
        fieldDao.save(model);
    }

    @Override
    public void delete(NptLogicDataField entity) {
        fieldDao.delete(entity);
    }

    @Override
    public void update(NptLogicDataField entity) {
        fieldDao.update(entity);
    }

    @Override
    public void updateAll(Collection<NptLogicDataField> list) {
        fieldDao.updateAll(list);
    }

    @Override
    public void saveAll(Collection<NptLogicDataField> list) {
        fieldDao.saveAll(list);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午5:50
     * 备注: 加载指定数据类别下挂的所有指定状态的数据字段
     */
    @Override
    public Collection<NptLogicDataField> listDataField(Long typeId, NptDict pubLevel, NptDict status) {
        return fieldDao.listDataField(typeId, pubLevel, status);
    }
}
