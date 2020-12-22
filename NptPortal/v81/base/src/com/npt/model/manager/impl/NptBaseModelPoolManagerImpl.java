package com.npt.model.manager.impl;

import com.npt.model.dao.NptBaseModelPoolDao;
import com.npt.model.entity.NptBaseModel;
import com.npt.model.entity.NptBaseModelGroup;
import com.npt.model.entity.NptBaseModelPool;
import com.npt.model.manager.NptBaseModelPoolManager;
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
public class NptBaseModelPoolManagerImpl implements NptBaseModelPoolManager{

    @Autowired
    private NptBaseModelPoolDao poolDao;

    /**
     * 作者：owen
     * 时间：2017/1/16 20:59
     * 描述:
     * 依据ID查找
     *
     * @param id
     */
    @Override
    public NptBaseModelPool findById(Long id) {
        return poolDao.findById(id);
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
    public NptBaseModelPool fastFindById(Long id) {
        return poolDao.findById(id);
    }

    /**
     * 作者：owen
     * 时间：2017/1/16 21:18
     * 描述:
     * 加载列表
     */
    @Override
    public Collection<NptBaseModelPool> getList() {
        return poolDao.getList();
    }

    @Override
    public void save(NptBaseModelPool model) {
        poolDao.save(model);
    }

    @Override
    public void delete(NptBaseModelPool entity) {
        poolDao.delete(entity);
    }

    @Override
    public void update(NptBaseModelPool entity) {
        poolDao.update(entity);
    }

    @Override
    public void updateAll(Collection<NptBaseModelPool> list) {
        poolDao.updateAll(list);
    }

    @Override
    public void saveAll(Collection<NptBaseModelPool> list) {
        poolDao.saveAll(list);
    }

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
    public Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModelGroup group) {
        return poolDao.getBaseModelGrouPools(group);
    }
}
