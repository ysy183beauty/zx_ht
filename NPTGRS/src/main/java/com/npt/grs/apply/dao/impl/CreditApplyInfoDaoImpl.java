package com.npt.grs.apply.dao.impl;

import com.npt.bridge.sync.entity.CreditApplyInfo;
import com.npt.grs.apply.dao.CreditApplyInfoDao;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateBaseDaoImpl;

/**
 * 项目: NPTGRS
 * 作者: 张磊
 * 日期: 2017/03/25 下午03:26
 * 备注:
 */
@Repository
public class CreditApplyInfoDaoImpl extends HibernateBaseDaoImpl<CreditApplyInfo>
        implements CreditApplyInfoDao {
}

