package com.npt.bridge.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2017/1/16 16:56
 * 描述:
 */
public interface NptBaseManager<T, ID extends Serializable> {

    /**
     *作者：owen
     *时间：2017/1/16 20:59
     *描述:
     *      依据ID查找
     */
    T findById(ID id);

    /**
     *作者：owen
     *时间：2017/1/16 21:00
     *描述:
     *      优先从缓存中查找
     */
    T fastFindById(ID id);

    /**
     *作者：owen
     *时间：2017/1/16 21:18
     *描述:
     *      加载列表
     */
    Collection<T> getList();

    T save(T entity);

    void saveAll(Collection<T> list);

    void update(T entity);

    void updateAll(Collection<T> list);

    void delete(T entity);

}
