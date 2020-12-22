package com.npt.querier;

import com.npt.bridge.model.NptBaseModel;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/2/13 14:49
 * 描述:
 *
 *      双公示查询器
 */
public interface Npt2PublicityQuerier{
    NptBaseModel getThisModel(String type);
}
