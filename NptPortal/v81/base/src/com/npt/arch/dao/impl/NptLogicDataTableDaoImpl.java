package com.npt.arch.dao.impl;

import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.hibernate4.HibernateBaseDao;
import com.npt.arch.dao.NptLogicDataTableDao;
import com.npt.arch.entity.NptLogicDataTable;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 16:51
 * 描述:
 */
@Repository
public class NptLogicDataTableDaoImpl extends HibernateBaseDao<NptLogicDataTable,Long> implements NptLogicDataTableDao{
    @Override
    public NptLogicDataTable findById(Long id) {
        return this.get(id);
    }

    @Override
    public Collection<NptLogicDataTable> getList() {
        Finder f = Finder.create("from NptLogicDataTable bean");
        return find(f);
    }

    @Override
    public void saveAll(Collection<NptLogicDataTable> list) {
        for (NptLogicDataTable entity : list) {
            getSession().save(entity);
        }
        getSession().flush();
    }

    @Override
    public void update(NptLogicDataTable org) {
        getSession().update(org);
        getSession().flush();
    }

    @Override
    public void updateAll(Collection<NptLogicDataTable> list) {
        for (NptLogicDataTable entity : list) {
            getSession().update(entity);
        }
        getSession().flush();
    }

    @Override
    public void save(NptLogicDataTable model) {
        getSession().save(model);
        getSession().flush();
    }

    @Override
    public void delete(NptLogicDataTable entity) {
        getSession().delete(entity);
        getSession().flush();
    }

    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<NptLogicDataTable> getEntityClass() {
        return NptLogicDataTable.class;
    }
}
