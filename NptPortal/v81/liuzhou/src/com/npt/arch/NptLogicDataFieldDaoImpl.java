package com.npt.arch;

import com.npt.bridge.arch.dao.NptLogicDataFieldDao;
import com.npt.base.NptBaseDaoImpl;
import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.dict.NptDict;
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
public class NptLogicDataFieldDaoImpl extends NptBaseDaoImpl<NptLogicDataField, Long> implements NptLogicDataFieldDao {

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

        }
        criteria.add(Restrictions.eq(NptLogicDataField.PROPERTY_PARENT_ID, typeId));
        criteria.addOrder(Order.asc(NptLogicDataField.PROPERTY_DISPLAY_ORDER));

        return criteria.list();
    }
}
