package com.npt.internet.query;

import com.npt.bridge.model.NptBaseModel;
import com.npt.internet.model.service.NptInternetModelQuerier;
import com.npt.internet.query.bean.NptInternetModel;
import com.npt.rms.base.action.NptRMSAction;
import com.npt.bridge.dict.NptRmsDict;
import com.npt.bridge.dict.NptRmsDictBean;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.web.dataBinder.NptWebBridgeBean;
import com.npt.web.dataBinder.NptWebModelTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2016/12/19 23:11
 * 描述:
 */
@Controller("npt.global")
public class NptGlobalAction extends NptRMSAction{

    @Autowired
    private NptInternetModelQuerier modelQuerier;


    /**
     *作者：owen
     *时间：2016/12/19 23:10
     *描述:
     *      外部查询系统不登录即可访问的首页
     */
    public String index(){

        Collection<NptRmsDictBean> dbList = new ArrayList<>();
        Collection<NptRmsDict> modelCates = NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.BMH);
        if(null != modelCates && !modelCates.isEmpty()){
            for(NptRmsDict d:modelCates){
                dbList.add(d.castToBean());
            }
        }
        Collection<NptInternetModel> models = modelQuerier.listModels();
        this.setAttribute("_MODEL_LIST",models);
        this.setAttribute("_MODEL_CATES",dbList);
        return SUCCESS;
    }

    /**
     *作者：owen
     *时间：2016/12/21 14:28
     *描述:
     *      指定模型页面的初始化信息
     *
     *      _HAVE_MFIELD：表示模型是否存在主字段列表
     *      _MODEL_TREE：表示模型的  M-G-P树形结构
     *      _MODEL_MFIELD_DATA：模型存在主字段列表的情况下的主字段第一分布的数据
     */
    public String modelIndex(){
        Long modelId = getLongParameter("modelId");

        NptBaseModel model = modelQuerier.getBaseModelById(modelId);
        if(null == model){
            this.outputErrorResult("模型已不存在!");
        }else {
            NptWebModelTree modelTree = modelQuerier.getBaseModelTree(model);

            Boolean haveMField = modelQuerier.isModelHaveMainFields(model);
            this.setAttribute("_HAVE_MFIELDS",haveMField);
            this.setAttribute("_MODEL_TREE",modelTree);
        }
        return SUCCESS;
    }

    /**
     *作者：owen
     *时间：2016/12/21 14:39
     *描述:
     *      模型主字段的分页查询方法
     *
     *      将模型ID放置在NptWebBridgeBean的parentId中
     */
    public String paginationModelMainDataIndex(){
        Long modelId = getLongParameter("modelId");
        NptWebBridgeBean bean = new NptWebBridgeBean();
        NptBaseModel model = modelQuerier.getBaseModelById(modelId);
        if(null == model) {
            this.outputErrorResult("模型已不存在!");
        }else {
            NptRmsDict result = modelQuerier.getModelMainFieldPaginationData(model, bean);
            if (!NptRmsDict.RST_SUCCESS.equals(result)) {
                this.outputErrorResult(result.getTitle());
            }
            this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), bean);
        }
        return SUCCESS;
    }

    /**
     *作者：owen
     *时间：2016/12/21 14:39
     *描述:
     *      模型主字段的分页查询方法
     *
     *      将模型ID放置在NptWebBridgeBean的parentId中
     */
    public String paginationModelMainDataAjax(){
        NptWebBridgeBean bean = getWebConditions();
        NptBaseModel model = modelQuerier.getBaseModelById(bean.getParentId());
        if(null == model) {
            this.outputErrorResult("模型已不存在!");
        }else {
            NptRmsDict result = modelQuerier.getModelMainFieldPaginationData(model, bean);
            if (!NptRmsDict.RST_SUCCESS.equals(result)) {
                this.outputErrorResult(result.getTitle());
            }
            this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), bean);
        }
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
            return NONE;
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
     *时间：2016/12/21 14:42
     *描述:
     *      bean中的parentId存储的是模型id
     *      bean中的primaryKeyValue存储的是选中的主数据池的数据主键
     */
    public String getBaseModelGroupBlocks(){
        //获取页面上传递的详细入口信息，特别是某条记录的业务主键值
        NptWebBridgeBean bean = getWebConditions();
        NptBaseModel model = modelQuerier.getBaseModelById(bean.getParentId());
        if(null == model){
            this.outputErrorResult("模型已不存在");
        }else {
            //加载指定指定指定宿主实例(比如某个具体的企业)的模型信息
            NptRmsDict result = modelQuerier.getModelGroupDetailList(model,bean);
            if(!NptRmsDict.RST_SUCCESS.equals(result)){
                this.outputErrorResult(result.getTitle());
            }
            this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), bean);
        }
        return SUCCESS;
    }















    /**
     * 作者：xuqinyuan
     * 时间：2016/12/20 3:11
     * 备注： 加载企业查询列表
     */
    public String businessIndex(){
        this.setAttribute("busName",this.getParameter("busName"));
        this.setAttribute("codeName",this.getParameter("codeName"));
        return SUCCESS;
    }
    /**
     * 作者：xuqinyuan
     * 时间：2016/12/20 3:11
     * 备注： 加载g个人查询列表
     */
    public String personIndex(){

        return SUCCESS;
    }
    /**
     * 作者：xuqinyuan
     * 时间：2016/12/20 2:11
     * 备注：加载企业查询页面
     */
    public String businessSearch(){
        return SUCCESS;
    }

    /**
     * 作者：xuqinyuan
     * 时间：2016/12/20 2:11
     * 备注：加载个人查询压面
     */
    public String personSearch(){
        return SUCCESS;
    }

    /**
     * 作者：xuqinyuan
     * 时间：2016/12/20 2:11
     * 备注：加载红黑榜首页
     */
    public String redBlackIndex(){
        return SUCCESS;
    }
}
