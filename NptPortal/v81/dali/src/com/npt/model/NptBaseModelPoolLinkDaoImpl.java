package com.npt.model;

import com.npt.base.NptBaseDaoImpl;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModelLink;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.dao.NptBaseModelPoolLinkDao;
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
public class NptBaseModelPoolLinkDaoImpl extends NptBaseDaoImpl<NptBaseModelLink, Long> implements NptBaseModelPoolLinkDao {

    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<NptBaseModelLink> getEntityClass() {
        return NptBaseModelLink.class;
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午5:32
     * 备注: 检测当前数据池是否有关联的数据池
     */
    @Override
    public Integer getBaseModelGrouPoolLinkCount(NptBaseModelPool p, NptDict status) {
        Criteria criteria = getSession().createCriteria(NptBaseModelLink.class);
        criteria.add(Restrictions.eq(NptBaseModelLink.PROPERTY_FROM_POOL_ID, p.getId()));
        if(null != status) {
            criteria.add(Restrictions.eq(NptBaseModelLink.PROPERTY_STATUS, status.getCode()));
        }
        return criteria.list().size();
    }

    @Override
    public Collection<NptBaseModelLink> getBaseModelGroupoolLinkedPools(NptBaseModelPool p, NptDict status) {
        Criteria criteria = getSession().createCriteria(NptBaseModelLink.class);
        criteria.add(Restrictions.eq(NptBaseModelLink.PROPERTY_FROM_POOL_ID, p.getId()));
        if (null != status) {
            criteria.add(Restrictions.eq(NptBaseModelLink.PROPERTY_STATUS, status.getCode()));
        }
        return criteria.list();
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/17 下午2:54
     * 备注: 查询关联向指定数据池的其它数据池, status为null则表示全部状态
     */
    @Override
    public Collection<NptBaseModelLink> getBaseModelGroupoolLinkedMePools(NptBaseModelPool p, NptDict status) {
        Criteria criteria = getSession().createCriteria(NptBaseModelLink.class);
        if (null != status) {
            criteria.add(Restrictions.eq(NptBaseModelLink.PROPERTY_TO_POOL_ID, p.getId()));
            criteria.add(Restrictions.eq(NptBaseModelLink.PROPERTY_STATUS, status.getCode()));
        } else {
            criteria.add(Restrictions.eq(NptBaseModelLink.PROPERTY_TO_POOL_ID, p.getId()));
        }
        return criteria.list();
    }
}
