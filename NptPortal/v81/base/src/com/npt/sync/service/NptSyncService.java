package com.npt.sync.service;

import com.npt.arch.entity.NptLogicDataProvider;
import com.npt.dict.NptDict;
import com.npt.model.bean.NptBaseModelIncData;
import com.npt.model.bean.NptBaseModelStructure;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 11:57
 * 描述:
 */
public interface NptSyncService {
    /**
     *作者：OWEN
     *时间：2016/12/1 22:19
     *描述:
     *      同步组织机构信息
     */
    NptDict syncDataProvider(Collection<NptLogicDataProvider> orgList);

    /**
     *作者：OWEN
     *时间：2016/12/1 22:19
     *描述:
     *      同步基础模型
     */
    NptDict syncBaseModelStructure(NptBaseModelStructure structure);

    /**
     *作者：owen
     *时间：2016/12/14 14:43
     *描述:
     *      同步基础模型的增量数据
     */
    NptDict syncBaseModelData(NptBaseModelIncData data);
}
