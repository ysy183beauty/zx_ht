package com.npt.rms.base.dao;

import com.npt.bridge.base.NptEntitySerializable;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/12/1 20:24
 * 描述:
 *      NptEntitySerializable实体类的保存逻辑:
 *
 *      若实体的ID为NULL,则为其生成一个ID,若实体已有指定的ID,则使用指定的ID.
 *
 *
 */
@Repository
public abstract class NptHibernateBaseDaoImpl<T extends NptEntitySerializable> extends HibernateSequenceBaseDaoImpl<T>{

    @Override
    protected void initEntityKey(T entity, Session session) {
        if(null == entity.getId() || 0L == entity.getId()) {
            super.initEntityKey(entity, session);
        }
    }
}
