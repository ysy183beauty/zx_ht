package com.npt.bridge.sync.service;

import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.dict.NptBusinessCode;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModelPoolRow;
import com.npt.bridge.model.NptBaseModelStructure;

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

    NptDict syncBusinessCodes(Collection<NptBusinessCode> codes);

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
    NptDict syncBaseModelData(NptBaseModelPoolRow data);

    /**
     * 作者: 张磊
     * 日期: 2017/03/26 下午05:54
     * 备注: 执行sql
     */
    void execute(String sql);
}
