package com.npt.model;

import com.npt.base.NptBaseDaoImpl;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.dao.NptBaseModelPoolDao;
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
public class NptBaseModelPoolDaoImpl extends NptBaseDaoImpl<NptBaseModelPool, Long> implements NptBaseModelPoolDao {

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
    public Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModelGroup g,NptDict lockLevel) {
        Criteria criteria = getSession().createCriteria(NptBaseModelPool.class);
        criteria.add(Restrictions.eq(NptBaseModelPool.PROPERTY_GROUP_ID, g.getId()));
        criteria.add(Restrictions.eq(NptBaseModelPool.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        if(null != lockLevel) {
            criteria.add(Restrictions.le(NptBaseModelPool.PROPERTY_LOCK_LEVEL, lockLevel.getCode()));
        }
        criteria.addOrder(Order.asc(NptBaseModelPool.PROPERTY_POOL_WEIGHT));
        return criteria.list();
    }

    @Override
    public Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModel model,NptDict lockLevel) {
        Criteria criteria = getSession().createCriteria(NptBaseModelPool.class);
        criteria.add(Restrictions.eq(NptBaseModelPool.PROPERTY_MODEL_ID, model.getId()));
        criteria.add(Restrictions.eq(NptBaseModelPool.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        if(null != lockLevel) {
            criteria.add(Restrictions.le(NptBaseModelPool.PROPERTY_LOCK_LEVEL, lockLevel.getCode()));
        }
        criteria.addOrder(Order.asc(NptBaseModelPool.PROPERTY_POOL_WEIGHT));
        return criteria.list();
    }
}
