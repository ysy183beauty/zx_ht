package com.npt.model;

import com.npt.base.NptBaseDaoImpl;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.NptBaseModelPoolIndex;
import com.npt.bridge.model.dao.NptBaseModelPoolIndexDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:07
 * 描述:
 */
@Repository
public class NptBaseModelPoolIndexDaoImpl extends NptBaseDaoImpl<NptBaseModelPoolIndex, Long> implements NptBaseModelPoolIndexDao {

    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<NptBaseModelPoolIndex> getEntityClass() {
        return NptBaseModelPoolIndex.class;
    }

    /**
     * 作者：owen
     * 时间：2017/3/16 下午8:36
     * 描述：
     * 获取数据池的索引字段
     *
     * @param pool
     */
    @Override
    public Collection<NptBaseModelPoolIndex> getBaseModelPoolIndex(NptBaseModelPool pool) {

        Collection<NptBaseModelPoolIndex> result = new ArrayList<>();

        if(null != pool){
            Criteria criteria = getSession().createCriteria(NptBaseModelPoolIndex.class);
            criteria.add(Restrictions.eq(NptBaseModelPoolIndex.PROPERTY_POOL_ID, pool.getId()));
            criteria.add(Restrictions.eq(NptBaseModelPoolIndex.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
            criteria.addOrder(Order.asc(NptBaseModelPoolIndex.PROPERTY_DISPLAY_ORDER));
            return criteria.list();
        }
        return result;
    }

}
