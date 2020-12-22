package com.npt.model.dao.impl;

import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.hibernate4.HibernateBaseDao;
import com.npt.dict.NptDict;
import com.npt.model.dao.NptBaseModelGroupDao;
import com.npt.model.entity.NptBaseModel;
import com.npt.model.entity.NptBaseModelGroup;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:04
 * 描述:
 */
@Repository
public class NptBaseModelGroupDaoImpl extends HibernateBaseDao<NptBaseModelGroup,Long> implements NptBaseModelGroupDao{
    @Override
    public NptBaseModelGroup findById(Long id) {
        return this.get(id);
    }

    @Override
    public Collection<NptBaseModelGroup> getList() {
        Finder f = Finder.create("from NptBaseModelGroup bean");
        return find(f);
    }

    @Override
    public void saveAll(Collection<NptBaseModelGroup> list) {
        for (NptBaseModelGroup entity : list) {
            getSession().save(entity);
        }
        getSession().flush();
    }

    @Override
    public void update(NptBaseModelGroup org) {
        getSession().update(org);
        getSession().flush();
    }

    @Override
    public void updateAll(Collection<NptBaseModelGroup> list) {
        for (NptBaseModelGroup entity : list) {
            getSession().update(entity);
        }
        getSession().flush();
    }

    @Override
    public void save(NptBaseModelGroup model) {
        getSession().save(model);
        getSession().flush();
    }

    @Override
    public void delete(NptBaseModelGroup entity) {
        getSession().delete(entity);
        getSession().flush();
    }

    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<NptBaseModelGroup> getEntityClass() {
        return NptBaseModelGroup.class;
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午11:52
     * 备注: 获取基础模型的所有分组
     */
    @Override
    public Collection<NptBaseModelGroup> getBaseModelGroups(NptBaseModel m) {
        Criteria criteria = getSession().createCriteria(NptBaseModelGroup.class);
        criteria.add(Restrictions.eq(NptBaseModelGroup.PROPERTY_MODEL_ID, m.getId()));
        criteria.add(Restrictions.ne(NptBaseModelGroup.PROPERTY_STATUS, NptDict.IDS_DELETED.getCode()));
        criteria.addOrder(Order.asc(NptBaseModelGroup.PROPERTY_DISPLAY_ORDER));
        return criteria.list();
    }
}
