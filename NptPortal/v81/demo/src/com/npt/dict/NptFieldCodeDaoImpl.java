package com.npt.dict;

import com.npt.base.NptBaseDaoImpl;
import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dict.NptBusinessCode;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.dict.dao.NptFieldCodeDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Collection;


/**
 * Created by owen on 2017/3/21.
 */
@Repository
public class NptFieldCodeDaoImpl extends NptBaseDaoImpl<NptBusinessCode,Long> implements NptFieldCodeDao{

    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<NptBusinessCode> getEntityClass() {
        return NptBusinessCode.class;
    }

    @Override
    public NptBusinessCode getCode(Long fieldId, String codeValue) {
        Criteria criteria = getSession().createCriteria(NptBusinessCode.class);
        criteria.add(Restrictions.eq(NptBusinessCode.PROPERTY_CODE_FIELDID,fieldId));
        criteria.add(Restrictions.eq(NptBusinessCode.PROPERTY_CODE_VALUE,codeValue));

        return (NptBusinessCode) criteria.uniqueResult();
    }

    @Override
    public Collection<NptBusinessCode> listAll() {

        Criteria criteria = getSession().createCriteria(NptBusinessCode.class);
        criteria.add(Restrictions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        return criteria.list();
    }
}
