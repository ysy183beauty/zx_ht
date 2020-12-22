package com.npt.internet.query;

import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.model.NptBaseModel;
import com.npt.internet.model.service.NptInternetModelQuerier;
import com.npt.rms.base.action.NptRMSAction;
import com.npt.bridge.dict.NptRmsDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.web.dataBinder.NptWebBridgeBean;
import com.npt.web.dataBinder.NptWebModelTree;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2016/12/14 19:33
 * 描述:
 */
public abstract class NptInternetQueryAction extends NptRMSAction{

    @Autowired
    protected NptInternetModelQuerier modelQuerier;

    /**
     *作者：owen
     *时间：2016/12/14 19:34
     *描述:
     *      获取每个业务域自身的模型信息
     */
    public abstract NptBaseModel getMyQueryModel();

    /**
     *作者：owen
     *时间：2016/12/14 20:01
     *描述:
     *      获取模型的前台展示树
     *                      --POOL
     *              --GROUP --POOL
     *      MODEL           --POOL
     *              --GROUP
     */
    public String getWebModelTree(){

        NptBaseModel model = getMyQueryModel();
        NptWebModelTree<NptBaseModel,NptBaseModelGroup,NptBaseModelPool> tree = new NptWebModelTree();
        if(null != model){
            tree.setRoot(model);
            Collection<NptBaseModelGroup> groups = modelQuerier.listModelGroups(model);
            if(null != groups){
                for(NptBaseModelGroup g:groups){
                    NptWebModelTree.NptWebModelSketelon sketelon = tree.instanceSkeleton();
                    sketelon.setSketeton(g);
                    Collection<NptBaseModelPool> pools = modelQuerier.listModelGrouPools(g);
                    if(null != pools){
                        sketelon.getLeafs().addAll(pools);
                    }
                    tree.getSkeletons().add(sketelon);
                }
            }
        }

        this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(),tree);
        return SUCCESS;
    }

    /**
     *作者：owen
     *时间：2016/12/14 20:03
     *描述:
     *      获取某一数据池的可分布查询的起始数据
     */
    public String paginationPoolDataIndex(){
        NptWebBridgeBean bean = new NptWebBridgeBean();
        Long poolId = getLongParameter("poolId");

        NptRmsDict result = modelQuerier.getBaseModelGroupoolPaginationData(poolId,bean);

        if(!NptRmsDict.RST_SUCCESS.equals(result)){
            this.outputErrorResult(result.getTitle());
        }
        this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), bean);
        return SUCCESS;
    }

    /**
     *作者：owen
     *时间：2016/12/14 20:27
     *描述:
     *      获取某一数据池的可分页查询的分页数据
     */
    public String paginationPoolDataAjax(){
        NptWebBridgeBean bean = this.getWebConditions();
        NptRmsDict result = modelQuerier.getBaseModelGroupoolPaginationData(getPrimaryKeyId(),bean);

        if(!NptRmsDict.RST_SUCCESS.equals(result)){
            this.outputErrorResult(result.getTitle());
        }
        this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), bean);
        return SUCCESS;
    }

    /**
     *作者：owen
     *时间：2016/12/16 14:55
     *描述:
     *      经典模式的加载模型主字段列表的第一页
     */
    public String paginationModelMainDataIndex(){
        NptBaseModel model = this.getMyQueryModel();

        NptWebBridgeBean bean = new NptWebBridgeBean();
        String busName = this.getParameter("busName");
        String codeName = this.getParameter("codeName");

        NptRmsDict result = modelQuerier.getModelMainFieldPaginationData(model,bean);
        if(!NptRmsDict.RST_SUCCESS.equals(result)){
            this.outputErrorResult(result.getTitle());
        }
        this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), bean);
        return SUCCESS;
    }

    /**
     *作者：owen
     *时间：2016/12/16 14:55
     *描述:
     *      经典模式的加载模型主字段列表的指定页
     */
    public String paginationModelMainDataAjax(){
        NptBaseModel model = this.getMyQueryModel();
        NptWebBridgeBean bean = getWebConditions();

        NptRmsDict result = modelQuerier.getModelMainFieldPaginationData(model,bean);
        if(!NptRmsDict.RST_SUCCESS.equals(result)){
            this.outputErrorResult(result.getTitle());
        }
        this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), bean);
        return SUCCESS;
    }

    /**
     *作者：owen
     *时间：2016/12/16 15:00
     *描述:
     *      加载模型的详细信息块
     */
    public String listModelDetailBlocks(){

        //获取当前的模型信息
        NptBaseModel model = getMyQueryModel();

        //获取页面上传递的详细入口信息，特别是某条记录的业务主键值
        NptWebBridgeBean bean = getWebConditions();

        //加载指定指定指定宿主实例(比如某个具体的企业)的模型信息
        NptRmsDict result = modelQuerier.getModelGroupDetailList(model,bean);
        if(!NptRmsDict.RST_SUCCESS.equals(result)){
            this.outputErrorResult(result.getTitle());
        }
        this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), bean);
        return SUCCESS;
    }

}
