package com.npt.grs.model.dao.impl;

import com.npt.grs.model.dao.NptBaseModelDao;
import com.npt.bridge.model.NptBaseModel;
import com.npt.rms.base.dao.NptHibernateBaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/10 20:24
 * 备注：
 */
@Repository
public class NptBaseModelDaoImpl
        extends NptHibernateBaseDaoImpl<NptBaseModel>
        implements NptBaseModelDao {
}
