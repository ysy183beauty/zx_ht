package com.npt.model.manager;

import com.npt.arch.entity.NptLogicDataField;
import com.npt.base.NptBaseManager;
import com.npt.model.entity.NptBaseModel;
import com.npt.model.entity.NptBaseModelMainField;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:00
 * 描述:
 */
public interface NptBaseModelMainFieldManager extends NptBaseManager<NptBaseModelMainField>{

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午3:8
     * 备注: 获取字段详情
     */
    NptLogicDataField getBaseModelGrouPoolFieldById(Long id);

    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午11:41
     * 备注: 确定某个模型的主字段列表
     */
    Collection<NptBaseModelMainField> getBaseModelMainFields(NptBaseModel m);
}
