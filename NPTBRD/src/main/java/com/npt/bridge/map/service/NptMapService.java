package com.npt.bridge.map.service;

import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.map.bean.NptBaseMap;
import com.npt.bridge.map.bean.NptRecursionMap;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelPoolCdt;

import java.util.Collection;

/**
 * author: owen
 * date:   2017/4/10 下午6:27
 * note:
 */
public interface NptMapService {

    /**
     *  author: owen
     *  date:   2017/4/10 下午6:28
     *  note:
     *          基于模型创建图谱实体
     */
    NptRecursionMap instance(NptBaseModel model, String ukValue);


    /**
     *作者：owen
     *时间: 2017/4/28 15:37
     *描述:
     *
     */
    NptBaseMap toBaseMap(NptRecursionMap map);


    void prepareForShow(NptBaseMap map,Boolean meargeLine);

    /**
     *  author: owen
     *  date:   2017/4/11 下午8:17
     *  note:
     *          加载模型的查询条件
     */
    Collection<NptBaseModelPoolCdt> loadModelSearchConditions(NptBaseModel model);

    /**
     *  author: owen
     *  date:   2017/4/11 下午9:16
     *  note:
     *          通过关键字模糊查询
     */
    NptDict fuzzySearch(NptBaseModel model, String searchValue, NptWebBridgeBean bean);

    /**
     *  author: owen
     *  date:   2017/4/19 下午3:44
     *  note:
     *          加载图谱结点的详细信息
     */
    NptDict loadNodeDetail(Long poolId,String ukValue,NptWebBridgeBean bean);
}
