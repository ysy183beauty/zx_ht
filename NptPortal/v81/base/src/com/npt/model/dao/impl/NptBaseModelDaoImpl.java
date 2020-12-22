package com.npt.model.dao.impl;

import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.hibernate4.HibernateBaseDao;
import com.npt.dict.NptDict;
import com.npt.model.dao.NptBaseModelDao;
import com.npt.model.entity.NptBaseModel;
import com.npt.model.entity.NptBaseModelGroup;
import com.npt.model.entity.NptBaseModelMainField;
import com.npt.model.entity.NptBaseModelPool;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:03
 * 描述:
 */
@Repository
public class NptBaseModelDaoImpl extends HibernateBaseDao<NptBaseModel,Long> implements NptBaseModelDao{
    @Override
    public NptBaseModel findById(Long id) {
        return this.get(id);
    }

    @Override
    public Collection<NptBaseModel> getList() {
        Finder f = Finder.create("from NptBaseModel bean");
        return find(f);
    }

    @Override
    public void saveAll(Collection<NptBaseModel> list) {
        for (NptBaseModel entity : list) {
            getSession().save(entity);
        }
        getSession().flush();
    }

    @Override
    public void update(NptBaseModel org) {
        getSession().update(org);
        getSession().flush();
    }

    @Override
    public void updateAll(Collection<NptBaseModel> list) {
        for (NptBaseModel entity : list) {
            getSession().update(entity);
        }
        getSession().flush();
    }

    @Override
    public void save(NptBaseModel model) {
        getSession().save(model);
        getSession().flush();
    }

    @Override
    public void delete(NptBaseModel entity) {
        getSession().delete(entity);
        getSession().flush();
    }

    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<NptBaseModel> getEntityClass() {
        return NptBaseModel.class;
    }

    /**
     * 作者：owen
     * 时间：2017/2/15 10:46
     * 描述:
     * 依据模型类别和模型主体查询模型
     *
     * @param cate
     * @param host
     */
    @Override
    public Collection<NptBaseModel> findModelByCategoryAndHost(NptDict cate, NptDict host) {
        Criteria criteria = getSession().createCriteria(NptBaseModel.class);
        if(cate != null){
            criteria.add(Restrictions.eq(NptBaseModel.PROPERTY_CATE_ID, cate.getCode()));
        }
        if (host != null) {
            criteria.add(Restrictions.eq(NptBaseModel.PROPERTY_HOST_ID, host.getCode()));
        }
        criteria.add(Restrictions.eq(NptBaseModel.PROPERTY_STATUS,NptDict.IDS_ENABLED.getCode()));

        return criteria.list();
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 上午11:37
     * 备注: 查询模型的所有分组
     */
    @Override
    public Collection<NptBaseModelGroup> lookupModelGroups(NptBaseModel model) {
        Criteria criteria = getSession().createCriteria(NptBaseModelGroup.class);
        criteria.add(Restrictions.eq(NptBaseModelGroup.PROPERTY_MODEL_ID, model.getId()));
        criteria.add(Restrictions.eq(NptBaseModelGroup.PROPERTY_STATUS,NptDict.IDS_ENABLED.getCode()));
        return criteria.list();
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 上午11:42
     * 备注: 查询模型指定分组的所有数据池
     */
    @Override
    public Collection<NptBaseModelPool> lookupModelGroupPools(NptBaseModelGroup group) {
        Criteria criteria = getSession().createCriteria(NptBaseModelPool.class);
        criteria.add(Restrictions.eq(NptBaseModelPool.PROPERTY_GROUP_ID, group.getId()));
        criteria.add(Restrictions.eq(NptBaseModelPool.PROPERTY_STATUS,NptDict.IDS_ENABLED.getCode()));
        return criteria.list();
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午2:46
     * 备注: 获取用户指定的数据池的主字段
     */
    @Override
    public Collection<NptBaseModelMainField> getBaseModelGrouPoolMainFields(NptBaseModelPool p) {
        Criteria criteria = getSession().createCriteria(NptBaseModelMainField.class);
        criteria.add(Restrictions.eq(NptBaseModelMainField.PROPERTY_POOL_ID, p.getId()));
        criteria.add(Restrictions.eq(NptBaseModelPool.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        criteria.addOrder(Order.asc(NptBaseModelMainField.PROPERTY_DISPLAY_ORDER));
        return criteria.list();
    }
}
