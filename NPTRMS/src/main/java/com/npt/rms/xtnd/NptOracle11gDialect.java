package com.npt.rms.xtnd;

import org.hibernate.Hibernate;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.metamodel.binding.HibernateTypeDescriptor;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/15 10:22
 * 描述:
 */
public class NptOracle11gDialect extends Oracle10gDialect{
    public NptOracle11gDialect(){
        super();
        registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
    }
}
