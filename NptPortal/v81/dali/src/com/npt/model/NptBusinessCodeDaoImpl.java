package com.npt.model;

import com.npt.base.NptBaseDaoImpl;
import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dict.NptBusinessCode;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.dao.NptBusinessCodeDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/3/28
 * 备注:
 */
@Repository
public class NptBusinessCodeDaoImpl extends NptBaseDaoImpl<NptBusinessCode, Long> implements NptBusinessCodeDao {

    /**
     * 作者: 张磊
     * 日期: 2017/03/28 下午08:31
     * 备注:
     */
    @Override
    public NptBusinessCode getCode(String codeValue, Long fieldId) {
        Criteria criteria = getSession().createCriteria(NptBusinessCode.class);
        criteria.add(Restrictions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        criteria.add(Restrictions.eq(NptBusinessCode.PROPERTY_CODE_VALUE, codeValue));
        criteria.add(Restrictions.eq(NptBusinessCode.PROPERTY_CODE_FIELDID, fieldId));
        return (NptBusinessCode) criteria.uniqueResult();
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/28 下午08:31
     * 备注:
     */
    @Override
    public Collection<NptBusinessCode> listCode(Long fieldId) {
        Criteria criteria = getSession().createCriteria(NptBusinessCode.class);
        criteria.add(Restrictions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        criteria.add(Restrictions.eq(NptBusinessCode.PROPERTY_CODE_FIELDID, fieldId));
        criteria.addOrder(Order.asc(NptBaseEntity.PROPERTY_DISPLAY_ORDER));
        return criteria.list();
    }

    @Override
    protected Class<NptBusinessCode> getEntityClass() {
        return NptBusinessCode.class;
    }
}
