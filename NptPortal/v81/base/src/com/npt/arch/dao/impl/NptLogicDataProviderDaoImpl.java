package com.npt.arch.dao.impl;

import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.hibernate4.HibernateBaseDao;
import com.npt.arch.dao.NptLogicDataProviderDao;
import com.npt.arch.entity.NptLogicDataProvider;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2017/1/16 21:05
 * 描述:
 */
@Repository
public class NptLogicDataProviderDaoImpl extends HibernateBaseDao<NptLogicDataProvider,Long> implements NptLogicDataProviderDao{
    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<NptLogicDataProvider> getEntityClass() {
        return NptLogicDataProvider.class;
    }

    @Override
    public NptLogicDataProvider findById(Long id) {
        return this.get(id);
    }

    @Override
    public Collection<NptLogicDataProvider> getList() {
        Finder f = Finder.create("from NptLogicDataProvider bean");
        return find(f);
    }

    @Override
    public void saveAll(Collection<NptLogicDataProvider> list) {
        for (NptLogicDataProvider provider : list) {
            getSession().save(provider);
        }
        getSession().flush();
    }

    @Override
    public void update(NptLogicDataProvider org) {
        getSession().update(org);
        getSession().flush();
    }

    @Override
    public void updateAll(Collection<NptLogicDataProvider> list) {
        for (NptLogicDataProvider provider : list) {
            getSession().update(provider);
        }
        getSession().flush();
    }

    @Override
    public void save(NptLogicDataProvider model) {
        getSession().save(model);
        getSession().flush();
    }

    @Override
    public void delete(NptLogicDataProvider entity) {
        getSession().delete(entity);
        getSession().flush();
    }
}
