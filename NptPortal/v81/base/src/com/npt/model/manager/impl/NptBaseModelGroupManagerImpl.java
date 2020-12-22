package com.npt.model.manager.impl;

import com.npt.model.dao.NptBaseModelGroupDao;
import com.npt.model.entity.NptBaseModel;
import com.npt.model.entity.NptBaseModelGroup;
import com.npt.model.manager.NptBaseModelGroupManager;
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
public class NptBaseModelGroupManagerImpl implements NptBaseModelGroupManager{

    @Autowired
    private NptBaseModelGroupDao groupDao;

    /**
     * 作者：owen
     * 时间：2017/1/16 20:59
     * 描述:
     * 依据ID查找
     *
     * @param id
     */
    @Override
    public NptBaseModelGroup findById(Long id) {
        return groupDao.findById(id);
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
    public NptBaseModelGroup fastFindById(Long id) {
        return groupDao.findById(id);
    }

    /**
     * 作者：owen
     * 时间：2017/1/16 21:18
     * 描述:
     * 加载列表
     */
    @Override
    public Collection<NptBaseModelGroup> getList() {
        return groupDao.getList();
    }

    @Override
    public void save(NptBaseModelGroup model) {
        groupDao.save(model);
    }

    @Override
    public void delete(NptBaseModelGroup entity) {
        groupDao.delete(entity);
    }

    @Override
    public void update(NptBaseModelGroup entity) {
        groupDao.update(entity);
    }

    @Override
    public void updateAll(Collection<NptBaseModelGroup> list) {
        groupDao.updateAll(list);
    }

    @Override
    public void saveAll(Collection<NptBaseModelGroup> list) {
        groupDao.saveAll(list);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午11:52
     * 备注: 获取基础模型的所有分组
     */
    @Override
    public Collection<NptBaseModelGroup> getBaseModelGroups(NptBaseModel m) {
        return groupDao.getBaseModelGroups(m);
    }
}
