package com.npt.internet.model.service;

import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.model.NptBaseModel;
import com.npt.internet.query.bean.NptInternetModel;
import com.npt.bridge.dict.NptRmsDict;
import com.npt.web.dataBinder.NptWebBridgeBean;
import com.npt.web.dataBinder.NptWebModelTree;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/28 10:50
 * 描述:
 *      外部子系统模型查询器
 */
public interface NptInternetModelQuerier {

    /**
     *作者：owen
     *时间：2016/12/16 14:47
     *描述:
     *      加载模型的分组信息
     */
    Collection<NptBaseModelGroup> listModelGroups(NptBaseModel model);

    /**
     *作者：owen
     *时间：2016/12/16 14:48
     *描述:
     *      加载分组的数据池信息
     */
    Collection<NptBaseModelPool> listModelGrouPools(NptBaseModelGroup group);

    /**
     *作者：owen
     *时间：2016/12/16 14:50
     *描述:
     *      加载数据池的分页数据
     */
    NptRmsDict getBaseModelGroupoolPaginationData(Long poolId,NptWebBridgeBean bean);

    /**
     *作者：owen
     *时间：2016/12/16 14:58
     *描述:
     *      依据主体及类别加载模型
     */
    Collection<NptBaseModel> listModels(NptRmsDict host,NptRmsDict cate);

    /**
     *作者：owen
     *时间：2016/12/16 15:04
     *描述:
     *      获取模型的主数据池分页列表数据
     */
    NptRmsDict getModelMainFieldPaginationData(NptBaseModel model,NptWebBridgeBean bean);

    /**
     *作者：owen
     *时间：2016/12/16 15:19
     *描述:
     *      依据数据主键加载其在该模型中的详细数据
     */
    NptRmsDict getModelGroupDetailList(NptBaseModel model,NptWebBridgeBean bean);

    /**
     *作者：owen
     *时间：2016/12/21 13:13
     *描述:
     *      列表所有模型
     */
    Collection<NptInternetModel> listModels();
    
    /**
     *作者：owen
     *时间：2016/12/21 14:23
     *描述: 
     *      
     */
    NptBaseModel getBaseModelById(Long modelId);
    
    /**
     *作者：owen
     *时间：2016/12/21 14:25
     *描述: 
     *      
     */
    NptWebModelTree getBaseModelTree(NptBaseModel model);

    /**
     *作者：owen
     *时间：2016/12/21 14:31
     *描述:
     *
     */
    Boolean isModelHaveMainFields(NptBaseModel model);
}
