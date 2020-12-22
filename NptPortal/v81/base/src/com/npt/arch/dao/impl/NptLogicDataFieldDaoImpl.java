package com.npt.arch.dao.impl;

import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.hibernate4.HibernateBaseDao;
import com.npt.arch.dao.NptLogicDataFieldDao;
import com.npt.arch.entity.NptLogicDataField;
import com.npt.dict.NptDict;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 16:51
 * 描述:
 */
@Repository
public class NptLogicDataFieldDaoImpl extends HibernateBaseDao<NptLogicDataField,Long> implements NptLogicDataFieldDao{
    @Override
    public NptLogicDataField findById(Long id) {
        return this.get(id);
    }

    @Override
    public Collection<NptLogicDataField> getList() {
        Finder f = Finder.create("from NptLogicDataField bean");
        return find(f);
    }

    @Override
    public void saveAll(Collection<NptLogicDataField> list) {
        for (NptLogicDataField entity : list) {
            getSession().save(entity);
        }
        getSession().flush();
    }

    @Override
    public void update(NptLogicDataField org) {
        getSession().update(org);
        getSession().flush();
    }

    @Override
    public void updateAll(Collection<NptLogicDataField> list) {
        for (NptLogicDataField entity : list) {
            getSession().update(entity);
        }
        getSession().flush();
    }

    @Override
    public void save(NptLogicDataField model) {
        getSession().save(model);
        getSession().flush();
    }

    @Override
    public void delete(NptLogicDataField entity) {
        getSession().delete(entity);
        getSession().flush();
    }

    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<NptLogicDataField> getEntityClass() {
        return NptLogicDataField.class;
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午5:51
     * 备注: 加载指定数据类别下挂的所有指定状态的数据字段
     */
    @Override
    public Collection<NptLogicDataField> listDataField(Long typeId, NptDict pubLevel, NptDict status) {
        Criteria criteria = getSession().createCriteria(NptLogicDataField.class);
        if (null == status) {
            criteria.add(Restrictions.isNotNull(NptLogicDataField.PROPERTY_ID));
        } else {
            criteria.add(Restrictions.eq(NptLogicDataField.PROPERTY_STATUS, status.getCode()));
        }

        if (null == pubLevel) {
            criteria.add(Restrictions.isNotNull(NptLogicDataField.PROPERTY_ID));
        } else {
            criteria.add(Restrictions.eq(NptLogicDataField.PROPERTY_PUBLISH_LEVEL, pubLevel.getCode()));
            criteria.add(Restrictions.eq(NptLogicDataField.PROPERTY_PARENT_ID, typeId));
            criteria.addOrder(Order.asc(NptLogicDataField.PROPERTY_DISPLAY_ORDER));
        }
        return criteria.list();
    }
}
