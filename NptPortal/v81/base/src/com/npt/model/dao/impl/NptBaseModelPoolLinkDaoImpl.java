package com.npt.model.dao.impl;

import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.hibernate4.HibernateBaseDao;
import com.npt.dict.NptDict;
import com.npt.model.dao.NptBaseModelPoolLinkDao;
import com.npt.model.entity.NptBaseModelPool;
import com.npt.model.entity.NptBaseModelPoolLink;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:08
 * 描述:
 */
@Repository
public class NptBaseModelPoolLinkDaoImpl extends HibernateBaseDao<NptBaseModelPoolLink,Long> implements NptBaseModelPoolLinkDao{
    @Override
    public NptBaseModelPoolLink findById(Long id) {
        return this.get(id);
    }

    @Override
    public Collection<NptBaseModelPoolLink> getList() {
        Finder f = Finder.create("from NptBaseModelPoolLink bean");
        return find(f);
    }

    @Override
    public void saveAll(Collection<NptBaseModelPoolLink> list) {
        for (NptBaseModelPoolLink entity : list) {
            getSession().save(entity);
        }
        getSession().flush();
    }

    @Override
    public void update(NptBaseModelPoolLink org) {
        getSession().update(org);
        getSession().flush();
    }

    @Override
    public void updateAll(Collection<NptBaseModelPoolLink> list) {
        for (NptBaseModelPoolLink entity : list) {
            getSession().update(entity);
        }
        getSession().flush();
    }

    @Override
    public void save(NptBaseModelPoolLink model) {
        getSession().save(model);
        getSession().flush();
    }

    @Override
    public void delete(NptBaseModelPoolLink entity) {
        getSession().delete(entity);
        getSession().flush();
    }

    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<NptBaseModelPoolLink> getEntityClass() {
        return NptBaseModelPoolLink.class;
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午5:32
     * 备注: 检测当前数据池是否有关联的数据池
     */
    @Override
    public Integer getBaseModelGrouPoolLinkCount(NptBaseModelPool p, NptDict status) {
        Criteria criteria = getSession().createCriteria(NptBaseModelPoolLink.class);
        criteria.add(Restrictions.eq(NptBaseModelPoolLink.PROPERTY_FROM_POOL_ID, p.getId()));
        if(null != status) {
            criteria.add(Restrictions.eq(NptBaseModelPoolLink.PROPERTY_STATUS, status.getCode()));
        }
        return criteria.list().size();
    }

    @Override
    public Collection<NptBaseModelPoolLink> getBaseModelGroupoolLinkedPools(NptBaseModelPool p, NptDict status) {
        Criteria criteria = getSession().createCriteria(NptBaseModelPoolLink.class);
        criteria.add(Restrictions.eq(NptBaseModelPoolLink.PROPERTY_FROM_POOL_ID, p.getId()));
        if (null != status) {
            criteria.add(Restrictions.eq(NptBaseModelPoolLink.PROPERTY_STATUS, status.getCode()));
        }
        return criteria.list();
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/17 下午2:54
     * 备注: 查询关联向指定数据池的其它数据池, status为null则表示全部状态
     */
    @Override
    public Collection<NptBaseModelPoolLink> getBaseModelGroupoolLinkedMePools(NptBaseModelPool p, NptDict status) {
        Criteria criteria = getSession().createCriteria(NptBaseModelPoolLink.class);
        if (null != status) {
            criteria.add(Restrictions.eq(NptBaseModelPoolLink.PROPERTY_TO_POOL_ID, p.getId()));
            criteria.add(Restrictions.eq(NptBaseModelPoolLink.PROPERTY_STATUS, status.getCode()));
        } else {
            criteria.add(Restrictions.eq(NptBaseModelPoolLink.PROPERTY_TO_POOL_ID, p.getId()));
        }
        return criteria.list();
    }
}
