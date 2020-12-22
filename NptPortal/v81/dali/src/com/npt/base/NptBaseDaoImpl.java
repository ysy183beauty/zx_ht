package com.npt.base;

import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.hibernate4.HibernateBaseDao;
import com.npt.bridge.base.NptBaseDao;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * 项目: zxcms
 * 作者: 张磊
 * 日期: 17/3/7 下午4:40
 * 备注:
 */
@Repository
public abstract class NptBaseDaoImpl<T, ID extends Serializable> extends HibernateBaseDao<T, ID> implements NptBaseDao<T, ID> {

    @Override
    public T findById(ID id) {
        return this.get(id);
    }

    @Override
    public Collection<T> getList() {
        Finder f = Finder.create("from " + this.getEntityClass().getSimpleName() + " bean");
        return find(f);
    }

    @Override
    public T save(T entity) {
        Assert.notNull(entity);
        getSession().save(entity);
        getSession().flush();
        return entity;
    }

    @Override
    public void saveAll(Collection<T> list) {
        for (T entity : list) {
            getSession().save(entity);
        }
        getSession().flush();
    }

    @Override
    public void update(T entity) {
        try {
            getSession().update(entity);
            getSession().flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateAll(Collection<T> list) {
        for (T entity : list) {
            getSession().update(entity);
        }
        getSession().flush();
    }

    @Override
    public void delete(T entity) {
        try {
            getSession().delete(entity);
            getSession().flush();
        }catch (Exception e){
            e.printStackTrace();
        }
     }

    @Override
    public Collection<T> findByIds(Collection<ID> ids) {
        StringBuilder selectSql = new StringBuilder();
        StringBuilder orderSql = new StringBuilder();
        selectSql.append("from " + this.getEntityClass().getSimpleName() + " bean where bean.id in (");
        orderSql.append(" order by case bean.id ");
        int index = 0;
        for (Iterator<ID> iterator = ids.iterator(); iterator.hasNext(); ) {
            ID id = iterator.next();
            if (id instanceof String) {
                selectSql.append(" '" + id + "'");
                orderSql.append(" when '" + id + "' then " + (++index) + " ");
            } else {
                selectSql.append(" " + id + "");
                orderSql.append(" when " + id + " then " + (++index) + " ");
            }

            if (iterator.hasNext()) {
                selectSql.append(" , ");
            }
        }
        selectSql.append(" ) ");
        orderSql.append(" end ");
        Finder f = Finder.create(selectSql.append(orderSql).toString());
        return find(f);
    }
}
