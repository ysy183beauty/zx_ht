package com.npt.grs.credit.dao.impl;

import com.npt.bridge.sync.entity.CreditCmsUser;
import com.npt.grs.credit.dao.CreditCmsUserDao;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateBaseDaoImpl;

/**
 * 项目: NPTGRS
 * 作者: 张磊
 * 日期: 2017/03/23 上午12:24
 * 备注:
 */
@Repository
public class CreditCmsUserDaoImpl extends HibernateBaseDaoImpl<CreditCmsUser>
        implements CreditCmsUserDao {
}

