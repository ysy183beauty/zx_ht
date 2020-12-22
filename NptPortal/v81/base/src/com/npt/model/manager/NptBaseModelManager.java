package com.npt.model.manager;

import com.npt.base.NptBaseManager;
import com.npt.dict.NptDict;
import com.npt.model.entity.NptBaseModel;
import com.npt.model.entity.NptBaseModelGroup;
import com.npt.model.entity.NptBaseModelMainField;
import com.npt.model.entity.NptBaseModelPool;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 16:53
 * 描述:
 */
public interface NptBaseModelManager extends NptBaseManager<NptBaseModel>{


    /**
     *作者：owen
     *时间：2017/2/15 10:38
     *描述:
     *      依据模型类别和模型主体查询模型
     */
    Collection<NptBaseModel> findModelByCategoryAndHost(NptDict cate,NptDict host);

    /**
     * 作者: 张磊
     * 日期: 17/2/15 上午11:36
     * 备注: 查询模型的所有分组
     */
    Collection<NptBaseModelGroup> lookupModelGroups(NptBaseModel model);

    /**
     * 作者: 张磊
     * 日期: 17/2/15 上午11:41
     * 备注: 查询模型指定分组的所有数据池
     */
    Collection<NptBaseModelPool> lookupModelGroupPools(NptBaseModelGroup group);

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午2:39
     * 备注: 获取用户指定的数据池的主字段
     */
    Collection<NptBaseModelMainField> getBaseModelGrouPoolMainFields(NptBaseModelPool p);
}
