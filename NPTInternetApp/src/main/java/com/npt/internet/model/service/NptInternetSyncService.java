package com.npt.internet.model.service;

import com.npt.bridge.model.NptBaseModelPoolRow;
import com.npt.bridge.model.NptBaseModelStructure;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.dict.NptRmsDict;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/12/1 17:30
 * 描述:
 */
public interface NptInternetSyncService {

    /**
     *作者：OWEN
     *时间：2016/12/1 22:19
     *描述:
     *      同步组织机构信息
     */
    NptRmsDict syncDataProvider(Collection<NptLogicDataProvider> orgList);

    /**
     *作者：OWEN
     *时间：2016/12/1 22:19
     *描述:
     *      同步基础模型
     */
    NptRmsDict syncBaseModel(NptBaseModelStructure structure);

    /**
     *作者：owen
     *时间：2016/12/14 14:43
     *描述:
     *      同步基础模型的增量数据
     */
    NptRmsDict syncBaseModelData(NptBaseModelPoolRow data);
}
