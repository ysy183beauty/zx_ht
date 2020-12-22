package com.npt.model.dao;

import com.npt.arch.entity.NptLogicDataField;
import com.npt.base.NptBaseDao;
import com.npt.model.entity.NptBaseModel;
import com.npt.model.entity.NptBaseModelMainField;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:02
 * 描述:
 */
public interface NptBaseModelMainFieldDao extends NptBaseDao<NptBaseModelMainField>{
    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午3:10
     * 备注: 获取字段详情
     */
    NptLogicDataField getBaseModelGrouPoolFieldById(Long id);

    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午11:42
     * 备注: 确定某个模型的主字段列表
     */
    Collection<NptBaseModelMainField> getBaseModelMainFields(NptBaseModel m);
}
