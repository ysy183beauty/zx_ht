package com.npt.model.dao.impl;

import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.hibernate4.HibernateBaseDao;
import com.npt.arch.entity.NptLogicDataField;
import com.npt.dict.NptDict;
import com.npt.model.dao.NptBaseModelMainFieldDao;
import com.npt.model.entity.NptBaseModel;
import com.npt.model.entity.NptBaseModelMainField;
import com.npt.model.entity.NptBaseModelPool;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:07
 * 描述:
 */
@Repository
public class NptBaseModelMainFieldDaoImpl extends HibernateBaseDao<NptBaseModelMainField,Long> implements NptBaseModelMainFieldDao{
    @Override
    public NptBaseModelMainField findById(Long id) {
        return this.get(id);
    }

    @Override
    public Collection<NptBaseModelMainField> getList() {
        Finder f = Finder.create("from NptBaseModelMainField bean");
        return find(f);
    }

    @Override
    public void saveAll(Collection<NptBaseModelMainField> list) {
        for (NptBaseModelMainField entity : list) {
            getSession().save(entity);
        }
        getSession().flush();
    }

    @Override
    public void update(NptBaseModelMainField org) {
        getSession().update(org);
        getSession().flush();
    }

    @Override
    public void updateAll(Collection<NptBaseModelMainField> list) {
        for (NptBaseModelMainField entity : list) {
            getSession().update(entity);
        }
        getSession().flush();
    }

    @Override
    public void save(NptBaseModelMainField model) {
        getSession().save(model);
        getSession().flush();
    }

    @Override
    public void delete(NptBaseModelMainField entity) {
        getSession().delete(entity);
        getSession().flush();
    }

    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<NptBaseModelMainField> getEntityClass() {
        return NptBaseModelMainField.class;
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午3:10
     * 备注: 获取字段详情
     */
    @Override
    public NptLogicDataField getBaseModelGrouPoolFieldById(Long id) {
        Criteria criteria = getSession().createCriteria(NptLogicDataField.class);
        criteria.add(Restrictions.eq(NptLogicDataField.PROPERTY_ID, id));
        criteria.add(Restrictions.eq(NptBaseModelPool.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        List list =  criteria.list();
        if(!list.isEmpty()){
            return (NptLogicDataField)list.get(0);
        }else {
            return null;
        }
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午11:42
     * 备注: 确定某个模型的主字段列表
     */
    @Override
    public Collection<NptBaseModelMainField> getBaseModelMainFields(NptBaseModel mainPool) {
        Criteria criteria = getSession().createCriteria(NptBaseModelMainField.class);
        criteria.add(Restrictions.eq(NptBaseModelMainField.PROPERTY_POOL_ID, mainPool.getId()));
        criteria.add(Restrictions.eq(NptBaseModelMainField.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        criteria.addOrder(Order.asc(NptBaseModelMainField.PROPERTY_DISPLAY_ORDER));
        return criteria.list();
    }
}
