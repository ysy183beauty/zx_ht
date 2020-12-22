package com.npt.base;

import java.io.Serializable;
import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2017/1/16 21:07
 * 描述:
 */
public interface NptBaseDao<T extends Serializable> {

    public T findById(Long id);

    public Collection<T> getList();

    void saveAll(Collection<T> list);

    void update(T org);

    void updateAll(Collection<T> list);

    void save(T entity);

    void delete(T entity);
}
