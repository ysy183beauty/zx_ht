package com.npt.ces.cw.dao.impl;

import com.npt.ces.cw.dao.NptCWModelSubDmsPropsDao;
import com.npt.ces.cw.entity.NptCWModelSubDmsProps;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

/**
 * 项目: NPTCES
 * 作者: 张磊
 * 日期: 2017/07/13 上午10:26
 * 备注:
 */
@Repository
public class NptCWModelSubDmsPropsDaoImpl extends HibernateSequenceBaseDaoImpl<NptCWModelSubDmsProps>
        implements NptCWModelSubDmsPropsDao {
}

