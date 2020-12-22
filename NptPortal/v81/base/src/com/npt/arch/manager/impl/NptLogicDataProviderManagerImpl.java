package com.npt.arch.manager.impl;

import com.npt.arch.dao.NptLogicDataProviderDao;
import com.npt.arch.entity.NptLogicDataProvider;
import com.npt.arch.manager.NptLogicDataProviderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2017/1/16 16:57
 * 描述:
 */
@Service
@Transactional
public class NptLogicDataProviderManagerImpl implements NptLogicDataProviderManager {

    @Autowired
    private NptLogicDataProviderDao providerDao;

    /**
     * 作者：owen
     * 时间：2017/1/16 20:59
     * 描述:
     * 依据ID查找
     *
     * @param id
     */
    @Override
    public NptLogicDataProvider findById(Long id) {
        return this.providerDao.findById(id);
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
    public NptLogicDataProvider fastFindById(Long id) {
        return providerDao.findById(id);
    }

    /**
     * 作者：owen
     * 时间：2017/1/16 21:18
     * 描述:
     * 加载列表
     */
    @Override
    public Collection<NptLogicDataProvider> getList() {
        return providerDao.getList();
    }

    @Override
    public void save(NptLogicDataProvider model) {
        providerDao.save(model);
    }

    @Override
    public void delete(NptLogicDataProvider entity) {
        providerDao.delete(entity);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/16 下午3:52
     * 备注: 批量保存数据提供者
     */
    @Override
    public void saveAll(Collection<NptLogicDataProvider> list) {
        providerDao.saveAll(list);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/16 下午4:4
     * 备注: 更新机构信息
     */
    @Override
    public void update(NptLogicDataProvider org) {
        providerDao.update(org);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午10:41
     * 备注: 批量更新数据提供者
     */
    @Override
    public void updateAll(Collection<NptLogicDataProvider> list) {
        providerDao.updateAll(list);
    }
}
