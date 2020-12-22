package com.npt.base;

import java.io.Serializable;
import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2017/1/16 16:56
 * 描述:
 */
public interface NptBaseManager<T extends Serializable>{

    /**
     *作者：owen
     *时间：2017/1/16 20:59
     *描述:
     *      依据ID查找
     */
    public T findById(Long id);

    /**
     *作者：owen
     *时间：2017/1/16 21:00
     *描述:
     *      优先从缓存中查找
     */
    public T fastFindById(Long id);

    /**
     *作者：owen
     *时间：2017/1/16 21:18
     *描述:
     *      加载列表
     */
    public Collection<T> getList();

    void save(T entity);

    void delete(T entity);

    void update(T entity);

    void updateAll(Collection<T> list);

    void saveAll(Collection<T> list);
}
