package com.npt.querier.impl;

import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelTree;
import com.npt.querier.NptRBPublicityQuerier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/2/13 14:52
 * 描述:
 */
@Service
@Transactional
public class NptRBPublicityQuerierBase extends NptAbstractCommonQuerier implements NptRBPublicityQuerier {

    /**
     * 作者：owen
     * 时间: 2017/2/20 11:17
     * 描述:
     * 获取某一专题的模型的基础信息
     */
    @Override
    public NptBaseModel getThisModel() {
        return getService().lookupModelByCategoryAndHost(NptDict.BMC_OUTSIDE, NptDict.BMH_BLACKRED);
    }


    /**
     * 作者: owen
     * 时间: 2017/3/12 下午1:08
     * 描述:
     * 加载指定数目的最新的红黑榜列表
     *
     * @param num
     */
    @Override
    public NptBaseModelTree loadLasted(Integer num) {

        NptBaseModel thisModel = getThisModel();
        if(null != thisModel) {
            NptBaseModelTree modelTree = getService().loadBaseModelTree(thisModel);

            return modelTree;
        }else {
            return new NptBaseModelTree();
        }
    }
}
