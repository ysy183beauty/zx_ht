package com.npt.bridge.base.manager;


import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.base.NptBaseManager;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 项目: zxcms
 * 作者: 张磊
 * 日期: 17/3/7 下午4:59
 * 备注:
 */
public abstract class NptBaseManagerImpl<T, ID extends Serializable> implements NptBaseManager<T, ID> {

    public abstract NptBaseDao<T,ID> getThisDao();

    @Override
    public T findById(ID id) {
        return getThisDao().findById(id);
    }

    @Override
    public T fastFindById(ID id) {
        return getThisDao().findById(id);
    }

    @Override
    public Collection<T> getList() {
        return getThisDao().getList();
    }

    @Override
    public T save(T entity) {
        return getThisDao().save(entity);
    }

    @Override
    public void saveAll(Collection<T> list) {
        getThisDao().saveAll(list);
    }

    @Override
    public void update(T entity) {
        getThisDao().update(entity);
    }

    @Override
    public void updateAll(Collection<T> list) {
        getThisDao().updateAll(list);
    }

    @Override
    public void delete(T entity) {
        getThisDao().delete(entity);
    }
}
