package com.credit.CreditPublicity.dao.impl;

import com.credit.CreditPublicity.dao.SjqzysgxEntityDao;
import com.credit.CreditPublicity.entity.SjqzysgxEntity;
import com.npt.base.NptBaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/4/1
 * 备注:
 */
@Repository
public class SjqzysgxEntityDaoImpl extends NptBaseDaoImpl<SjqzysgxEntity, Long> implements SjqzysgxEntityDao {
    @Override
    protected Class<SjqzysgxEntity> getEntityClass() {
        return SjqzysgxEntity.class;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/01 下午12:02
     * 备注:
     */
    @Override
    public SjqzysgxEntity findByPoolId(Long poolId) {
        List<SjqzysgxEntity> list = getSession().createQuery("from SjqzysgxEntity entity where poolid=:poolid").setParameter("poolid", poolId).list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
