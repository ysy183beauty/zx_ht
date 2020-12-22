package com.npt.rms.base.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/27 21:18
 * 备注：
 */
public class NptHibernateDaoSupport extends HibernateDaoSupport{

    protected SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactoryOverride(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;

        super.setSessionFactory(sessionFactory);
    }
}
