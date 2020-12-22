package com.npt.querier;

import com.npt.bridge.model.NptBaseModelTree;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/2/13 14:51
 * 描述:
 *
 *      基于模型的红黑榜查询器
 */
public interface NptRBPublicityQuerier{

    /**
     * 作者: owen
     * 时间: 2017/3/12 下午1:08
     * 描述:
     *      加载指定数目的最新的红黑榜列表
     */
    NptBaseModelTree loadLasted(Integer num);
}
