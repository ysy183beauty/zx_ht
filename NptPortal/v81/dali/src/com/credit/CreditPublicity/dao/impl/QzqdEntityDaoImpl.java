package com.credit.CreditPublicity.dao.impl;

import com.credit.CreditPublicity.dao.QzqdEntityDao;
import com.credit.CreditPublicity.entity.QzqdEntity;
import com.npt.base.NptBaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/4/1
 * 备注:
 */
@Repository
public class QzqdEntityDaoImpl extends NptBaseDaoImpl<QzqdEntity, Long> implements QzqdEntityDao {
    @Override
    protected Class<QzqdEntity> getEntityClass() {
        return QzqdEntity.class;
    }
}
