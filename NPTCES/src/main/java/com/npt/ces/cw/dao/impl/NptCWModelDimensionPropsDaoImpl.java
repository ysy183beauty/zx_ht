package com.npt.ces.cw.dao.impl;

import com.npt.ces.cw.dao.NptCWModelDimensionPropsDao;
import com.npt.ces.cw.entity.NptCWModelDmsProps;
import org.springframework.stereotype.Repository;
import org.summer.extend.orm.hibernate.HibernateSequenceBaseDaoImpl;

/**
 * 项目: NPTCES
 * 作者: 张磊
 * 日期: 2017/07/12 下午03:36
 * 备注:
 */
@Repository
public class NptCWModelDimensionPropsDaoImpl extends HibernateSequenceBaseDaoImpl<NptCWModelDmsProps>
        implements NptCWModelDimensionPropsDao {
}

