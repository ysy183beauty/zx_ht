package com.npt.grs.model.dao.impl;

import com.npt.grs.model.dao.NptBaseModelGroupDao;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.rms.base.dao.NptHibernateBaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/20 11:56
 * 备注：
 */
@Repository
public class NptBaseModelGroupDaoImpl
        extends NptHibernateBaseDaoImpl<NptBaseModelGroup>
        implements NptBaseModelGroupDao{
}
