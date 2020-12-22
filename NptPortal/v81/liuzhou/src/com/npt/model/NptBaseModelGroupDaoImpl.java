package com.npt.model;

import com.npt.base.NptBaseDaoImpl;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.model.dao.NptBaseModelGroupDao;
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
public class NptBaseModelGroupDaoImpl extends NptBaseDaoImpl<NptBaseModelGroup, Long> implements NptBaseModelGroupDao {

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
