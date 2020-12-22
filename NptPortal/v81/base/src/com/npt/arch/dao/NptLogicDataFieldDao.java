package com.npt.arch.dao;

import com.npt.arch.entity.NptLogicDataField;
import com.npt.base.NptBaseDao;
import com.npt.dict.NptDict;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 16:50
 * 描述:
 */
public interface NptLogicDataFieldDao extends NptBaseDao<NptLogicDataField>{
    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午5:51
     * 备注: 加载指定数据类别下挂的所有指定状态的数据字段
     */
    Collection<NptLogicDataField> listDataField(Long typeId, NptDict pubLevel, NptDict status);
}
