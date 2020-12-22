package com.npt.model;

import com.npt.base.NptBaseDaoImpl;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.NptBaseModelPoolCdt;
import com.npt.bridge.model.dao.NptBaseModelPoolConditionDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 作者: owen
 * 时间: 2017/3/13 下午9:53
 * 描述:
 */
@Repository
public class NptBaseModelPoolConditionDaoImpl extends NptBaseDaoImpl<NptBaseModelPoolCdt,Long> implements NptBaseModelPoolConditionDao{
    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<NptBaseModelPoolCdt> getEntityClass() {
        return NptBaseModelPoolCdt.class;
    }

    /**
     * 作者: owen
     * 时间: 2017/3/13 下午9:57
     * 描述:
     * 查询数据池的所有状态可用的查询字段
     *
     * @param p
     */
    @Override
    public Collection<NptBaseModelPoolCdt> getPoolSearchConditions(NptBaseModelPool p) {
        Criteria criteria = getSession().createCriteria(NptBaseModelPoolCdt.class);
        criteria.add(Restrictions.eq(NptBaseModelPoolCdt.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        criteria.add(Restrictions.eq(NptBaseModelPoolCdt.PROPERTY_POOL_ID, p.getId()));
        return criteria.list();
    }
}
