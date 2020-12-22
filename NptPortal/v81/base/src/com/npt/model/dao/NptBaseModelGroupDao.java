package com.npt.model.dao;

import com.npt.base.NptBaseDao;
import com.npt.model.entity.NptBaseModel;
import com.npt.model.entity.NptBaseModelGroup;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:02
 * 描述:
 */
public interface NptBaseModelGroupDao extends NptBaseDao<NptBaseModelGroup>{
    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午11:52
     * 备注: 获取基础模型的所有分组
     */
    Collection<NptBaseModelGroup> getBaseModelGroups(NptBaseModel m);
}
