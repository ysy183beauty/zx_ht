package com.npt.bridge.arch.dao;

import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.dict.NptDict;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 16:50
 * 描述:
 */
public interface NptLogicDataFieldDao extends NptBaseDao<NptLogicDataField, Long> {
    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午5:51
     * 备注: 加载指定数据类别下挂的所有指定状态的数据字段
     */
    Collection<NptLogicDataField> listDataField(Long typeId, NptDict pubLevel, NptDict status);
}
