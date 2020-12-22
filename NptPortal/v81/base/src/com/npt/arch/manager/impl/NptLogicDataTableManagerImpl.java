package com.npt.arch.manager.impl;

import com.npt.arch.dao.NptLogicDataTableDao;
import com.npt.arch.entity.NptLogicDataTable;
import com.npt.arch.manager.NptLogicDataTableManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 16:49
 * 描述:
 */
@Service
@Transactional
public class NptLogicDataTableManagerImpl implements NptLogicDataTableManager{

    @Autowired
    private NptLogicDataTableDao tableDao;


    /**
     * 作者：owen
     * 时间：2017/1/16 20:59
     * 描述:
     * 依据ID查找
     *
     * @param id
     */
    @Override
    public NptLogicDataTable findById(Long id) {
        return tableDao.findById(id);
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
    public NptLogicDataTable fastFindById(Long id) {
        return tableDao.findById(id);
    }

    /**
     * 作者：owen
     * 时间：2017/1/16 21:18
     * 描述:
     * 加载列表
     */
    @Override
    public Collection<NptLogicDataTable> getList() {
        return tableDao.getList();
    }

    @Override
    public void save(NptLogicDataTable model) {
        tableDao.save(model);
    }

    @Override
    public void delete(NptLogicDataTable entity) {
        tableDao.delete(entity);
    }

    @Override
    public void update(NptLogicDataTable entity) {
        tableDao.update(entity);
    }

    @Override
    public void updateAll(Collection<NptLogicDataTable> list) {
        tableDao.updateAll(list);
    }

    @Override
    public void saveAll(Collection<NptLogicDataTable> list) {
        tableDao.saveAll(list);
    }
}
