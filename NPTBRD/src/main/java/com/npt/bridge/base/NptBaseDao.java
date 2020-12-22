package com.npt.bridge.base;


import java.io.Serializable;
import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2017/1/16 21:07
 * 描述:
 */
public interface NptBaseDao<T, ID extends Serializable> {

    T findById(ID id);

    Collection<T> getList();

    T save(T entity);

    void saveAll(Collection<T> list);

    void update(T entity);

    void updateAll(Collection<T> list);

    void delete(T entity);

    Collection<T> findByIds(Collection<ID> ids);
}
