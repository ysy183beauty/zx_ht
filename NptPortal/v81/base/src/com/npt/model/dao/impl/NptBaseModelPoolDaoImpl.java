package com.npt.model.dao.impl;

import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.hibernate4.HibernateBaseDao;
import com.npt.dict.NptDict;
import com.npt.model.dao.NptBaseModelPoolDao;
import com.npt.model.entity.NptBaseModel;
import com.npt.model.entity.NptBaseModelGroup;
import com.npt.model.entity.NptBaseModelPool;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:06
 * 描述:
 */
@Repository
public class NptBaseModelPoolDaoImpl extends HibernateBaseDao<NptBaseModelPool,Long> implements NptBaseModelPoolDao{
    @Override
    public NptBaseModelPool findById(Long id) {
        return this.get(id);
    }

    @Override
    public Collection<NptBaseModelPool> getList() {
        Finder f = Finder.create("from NptBaseModelPool bean");
        return find(f);
    }

    @Override
    public void saveAll(Collection<NptBaseModelPool> list) {
        for (NptBaseModelPool entity : list) {
            getSession().save(entity);
        }
        getSession().flush();
    }

    @Override
    public void update(NptBaseModelPool org) {
        getSession().update(org);
        getSession().flush();
    }

    @Override
    public void updateAll(Collection<NptBaseModelPool> list) {
        for (NptBaseModelPool entity : list) {
            getSession().update(entity);
        }
        getSession().flush();
    }

    @Override
    public void save(NptBaseModelPool model) {
        getSession().save(model);
        getSession().flush();
    }

    @Override
    public void delete(NptBaseModelPool entity) {
        getSession().delete(entity);
        getSession().flush();
    }

    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<NptBaseModelPool> getEntityClass() {
        return NptBaseModelPool.class;
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/16 上午11:57
     * 备注: 获取模型的主数据池
     */
    @Override
    public NptBaseModelPool getBaseModelGroupMainPool(NptBaseModel m) {
        Criteria criteria = getSession().createCriteria(NptBaseModelPool.class);
        criteria.add(Restrictions.eq(NptBaseModelPool.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        criteria.add(Restrictions.eq(NptBaseModelPool.PROPERTY_MODEL_ID, m.getId()));
        criteria.add(Restrictions.eq(NptBaseModelPool.PROPERTY_POOL_WEIGHT, NptDict.CLD_MAIN.getCode()));
        return (NptBaseModelPool) criteria.uniqueResult();
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/17 下午1:34
     * 备注: 获取分组下的所有数据池
     */
    @Override
    public Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModelGroup g) {
        Criteria criteria = getSession().createCriteria(NptBaseModelPool.class);
        criteria.add(Restrictions.eq(NptBaseModelPool.PROPERTY_GROUP_ID, g.getId()));
        criteria.add(Restrictions.ne(NptBaseModelPool.PROPERTY_STATUS, NptDict.IDS_DELETED.getCode()));
        criteria.addOrder(Order.asc(NptBaseModelPool.PROPERTY_POOL_WEIGHT));
        return criteria.list();
    }
}
