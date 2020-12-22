package com.npt.grs.query;

import com.alibaba.fastjson.JSON;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.dataBinder.NptWebDetailBlock;
import com.npt.bridge.dataBinder.NptWebDropBox;
import com.npt.bridge.dataBinder.NptWebDropBoxElement;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.NptBaseModelTree;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.grs.model.service.NptGRSBaseModelService;
import com.npt.rms.base.action.NptRMSAction;
import com.npt.rms.log.entity.NptLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 作者：owen
 * 日期：2016/10/20 14:13
 * 备注：
 *      信用基础模型查询的基本响应类
 * 参数：
 * 返回：
 */
public abstract class NptGRSQueryAction extends NptRMSAction {

    @Autowired
    protected NptGRSBaseModelService baseModelService;

    /***************************************供子类使用或重写的方法集*************************************/
    /**
     * 作者：owen
     * 日期：2016/10/20 14:16
     * 备注：
     *      构建公开可视的默认信息
     * 参数：
     * 返回：
     */
    public NptWebBridgeBean constructOpenWebBridgeBean(NptBaseModel m) {

        NptWebBridgeBean bean = new NptWebBridgeBean();

        baseModelService.loadBaseModelConditions(m,bean);

        if (m != null) {
            NptBaseModelTree tree = baseModelService.loadBaseModelTree(m.getId(), null);
            bean.setModelTree(tree);
        }

        return bean;
    }

    /**
     *作者：OWEN
     *时间：2016/11/18 15:31
     *描述:
     *      构建详细信息的默认信息
     */
    public NptWebBridgeBean constructAuthWebBridgeBean(NptBaseModel m,NptWebBridgeBean bean) {
        loadBaseModelGroupDropboxCondition(m,bean);
        return bean;
    }


    /**
     *作者：OWEN
     *时间：2016/11/18 15:26
     *描述:
     *      加载模型分组的下拉列表查询条件
     */
    private void loadBaseModelGroupDropboxCondition(NptBaseModel model,NptWebBridgeBean bean){
        Collection<NptBaseModelGroup> groups = baseModelService.getBaseModelGroups(model);
        if(null != groups && null != bean) {
            NptWebDropBox groupDropBox = new NptWebDropBox(NptWebBridgeBean.GROUP_QUERY_CONDITION_NAME,NptCommonUtil.getBaseModelGroupConditionTitle());
            NptWebDropBox providerDropBox = new NptWebDropBox(NptWebBridgeBean.PROVIDER_QUERY_CONDITION_NAME,NptCommonUtil.getBaseModelProviderConditionTitle());
            List<NptWebDropBoxElement> groupElements = new ArrayList<>();
            for (NptBaseModelGroup group : groups) {
                groupElements.add(new NptWebDropBoxElement(group.getId(), group.getGroupTitle()));
            }
            groupDropBox.setOrderedElements(groupElements);
            bean.getDropBoxList().add(groupDropBox);

            Collection<NptBaseModelPool> poolList = baseModelService.getBaseModelGrouPools(model,NptDict.IDS_ENABLED,false);
            if(null != poolList){
                List<Long> addedProviderIds = new ArrayList<>();
                List<NptWebDropBoxElement> providerElements = new ArrayList<>();
                for(NptBaseModelPool p:poolList){
                    NptLogicDataProvider provider = baseModelService.getBaseModelGrouPoolProvider(p);
                    if(null != provider && !addedProviderIds.contains(provider.getId())){
                        providerElements.add(new NptWebDropBoxElement(provider.getId(),provider.getOrgName()));
                        addedProviderIds.add(provider.getId());
                    }
                }
                providerDropBox.setOrderedElements(providerElements);
                bean.getDropBoxList().add(providerDropBox);
            }
        }
    }


    /**
     * 作者：owen
     * 日期：2016/10/20 14:19
     * 备注：
     *      获取当前的基础模型信息
     *      若子类重写之，则返回子类自身控制的模型信息
     *      若子类不重写之，则从页面接收模型ID并加载之
     * 参数：
     * 返回：
     */
    public NptBaseModel getCurrentBaseModel() {
        Long mid = getWebParentId();
        if(null == mid){
            return null;
        }
        return baseModelService.findBaseModelById(mid);
    }


    /**
     * 作者：owen
     * 日期：2016/11/4 14:49
     * 备注：
     *      创建本业务域的日志信息
     * 参数：
     * 返回：
     */
    public NptLog getBusinessLog(){
        NptLog log = makeLog();
        log.setBusinessName(NptDict.LGB_GRS.getTitle());
        log.setBusinessType(NptDict.LGB_GRS.getCode());
        return log;
    }

    /***************************************页面响应的ACTION方法集*************************************/
    /**
     * 作者：owen
     * 日期：2016/10/20 16:10
     * 备注：
     *      首先加载指定模型的主数据列表的响应方法
     *
     *
     * 参数：
     * 返回：
     */
    public String openIndex() {

        NptLog log = getBusinessLog();
        log.setActionName(this.getClass().getSimpleName() + NptRMSAction.LOG_SEPRATOR + NptDict.LGA_OPEN_INDEX.getTitle());
        log.setActionType(NptDict.LGA_OPEN_INDEX.getCode());
        try {

            //获取当前的模型信息，或从页面指定，或由菜单绑定
            NptBaseModel model = getCurrentBaseModel();
            //构建默认情况下页面所需要的各类信息
            NptWebBridgeBean webBean = constructOpenWebBridgeBean(model);

            if(null != model) {
                NptBaseModelPool mainPool = baseModelService.getBaseModelGroupMainPool(model);
                if(null != mainPool) {
                    baseModelService.loadBaseModelOpenList(webBean, model, false);
                    //this.setAttribute("_RESULT_TYPE","MODEL_INDEX");
                    this.setAttribute("_RESULT_TYPE","MODEL_TREE");
                }else {
                    this.setAttribute("_RESULT_TYPE","MODEL_TREE");
                }
            }

            this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), webBean);

            log.setResultCode(NptDict.RST_SUCCESS.getCode());
            log.setResults(JSON.toJSONString(webBean.getDataList()));
        }catch (Exception e){
            log.setResultCode(NptDict.RST_ERROR.getCode());
            log.setCausedBy(e);
        }finally {
            commonService.save(log);
        }
        return SUCCESS;
    }

    /**
     * 作者：owen
     * 日期：2016/10/24 16:41
     * 备注：
     *      模型主数据列表的分页方法
     * 参数：
     * 返回：
     */
    public String openIndexAjax(){

        NptLog log = getBusinessLog();
        log.setActionName(this.getClass().getSimpleName() + NptRMSAction.LOG_SEPRATOR + NptDict.LGA_OPEN_INDEX_NEXT.getTitle());
        log.setActionType(NptDict.LGA_OPEN_INDEX_NEXT.getCode());
        try {
            /*注释原来的代码
            //获取当前的模型信息，或从页面指定，或由菜单绑定
            NptBaseModel model = getCurrentBaseModel();
            //构建默认情况下页面所需要的各类信息
            NptWebBridgeBean webBean = getWebConditions();
            //加载模型的主数据的可公开字段
            baseModelService.loadBaseModelOpenList(webBean,model,false);
             */
            //构建默认情况下页面所需要的各类信息
            NptWebBridgeBean webBean = getWebConditions();
            Long parentId=Long.parseLong(this.getRequest().getSession().getAttribute("parentId").toString());
            webBean.setParentId(parentId);
            NptWebBridgeBean bean=getNptWebBridgeBean(webBean);
            this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), bean);
            log.setResultCode(NptDict.RST_SUCCESS.getCode());
            log.setResults(JSON.toJSONString(bean.getDataList()));
        }catch (Exception e){
            log.setResultCode(NptDict.RST_ERROR.getCode());
            log.setCausedBy(e);
        }finally {
            commonService.save(log);
        }
        return SUCCESS;
    }


    /**
     *作者：owen
     *时间：2017/1/3 13:53
     *描述:
     *      报告的起始信息列表
     */
    public String reportIndex(){
        NptBaseModel model = getCurrentBaseModel();
        //构建默认情况下页面所需要的各类信息
        NptWebBridgeBean webBean = constructOpenWebBridgeBean(model);

        if(null != model) {
            //加载模型的主数据的可公开字段
            baseModelService.loadBaseModelOpenList(webBean, model,true);
        }
        this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), webBean);

        return SUCCESS;
    }

    /**
     *作者：owen
     *时间：2017/1/3 14:03
     *描述:
     *      报告的初始信息列表分页方法
     */
    public String reportIndexAjax(){
        //获取当前的模型信息，或从页面指定，或由菜单绑定
        NptBaseModel model = getCurrentBaseModel();

        //构建默认情况下页面所需要的各类信息
        NptWebBridgeBean webBean = getWebConditions();

        //加载模型的主数据的可公开字段
        baseModelService.loadBaseModelOpenList(webBean,model,true);

        this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), webBean);

        return SUCCESS;
    }

    /**
     * 作者：owen
     * 日期：2016/10/20 16:10
     * 备注：
     *      从openIndex返回的页面中的指定记录跳转到其详情页面的响应方法
     * 参数：
     * 返回：
     */
    public String authIndex() {

        NptLog log = getBusinessLog();
        log.setActionName(this.getClass().getSimpleName() + NptRMSAction.LOG_SEPRATOR + NptDict.LGA_AUTH_INDEX.getTitle());
        log.setActionType(NptDict.LGA_AUTH_INDEX.getCode());
        try {
            //获取当前的模型信息
            NptBaseModel model = getCurrentBaseModel();
            //获取页面上传递的详细入口信息，特别是某条记录的业务主键值
            NptWebBridgeBean bean = getWebConditions();
            //加载默认的查询条件
            constructAuthWebBridgeBean(model,bean);
            Long parentId=Long.parseLong(this.getRequest().getSession().getAttribute("parentId").toString());
            bean.setParentId(parentId);
            //加载指定指定指定宿主实例(比如某个具体的企业)的模型信息
             //baseModelService.loadBaseModelAuthGroupsByUK(bean,model);
            baseModelService.loadBaseModelDetailByUK(bean,model);
            this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), bean);
            log.setResultCode(NptDict.RST_SUCCESS.getCode());
            log.setResults(JSON.toJSONString(bean.getDataList()));
        }catch (Exception e){
            log.setResultCode(NptDict.RST_ERROR.getCode());
            log.setCausedBy(e);
        }finally {
            commonService.save(log);
        }
        return SUCCESS;
    }


    /**
     * 作者: 张磊
     * 日期: 2017/03/29 下午03:57
     * 备注: 通过pk查询数据
     */
    public String rnAuthLoader() {

        NptLog log = getBusinessLog();
        log.setActionName(this.getClass().getSimpleName() + NptRMSAction.LOG_SEPRATOR + NptDict.LAG_AUTH_RN.getTitle());
        log.setActionType(NptDict.LAG_AUTH_RN.getCode());
        try {
            Collection<NptBaseModel> models = baseModelService.listModels(NptDict.BMH_RNAUTH,NptDict.BMC_NATIVE);
            if(null != models && NptCommonUtil.IntegerOne().equals(models.size())){
                String rnType = getParameter("_rn_type");
                String pkValue = getPrimaryKeyValue();
                NptBaseModel rnModel = models.iterator().next();

                if(!StringUtils.isBlank(rnType) && !StringUtils.isBlank(pkValue)){
                    NptBaseModelGroup searchGroup = null;
                    if(rnType.equals(NptDict.BMHG_RNAUTH_BS.name())){
                        /**
                         * 企业认证信息查询
                          */
                        searchGroup = baseModelService.findBaseModelGroupByCode(rnModel.getId(),NptDict.BMHG_RNAUTH_BS);
                    }else if(rnType.equals(NptDict.BMHG_RNAUTH_PS.name())){
                        /**
                         * 个人认证信息查询
                         */
                        searchGroup = baseModelService.findBaseModelGroupByCode(rnModel.getId(),NptDict.BMHG_RNAUTH_PS);
                    }
                    if(null != searchGroup){
                        NptWebBridgeBean bean = new NptWebBridgeBean();
                        baseModelService.loadBaseModelGroupDataByUnitedPk(searchGroup,pkValue,bean);

                        this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), bean);

                        log.setResultCode(NptDict.RST_SUCCESS.getCode());
                        log.setResults(JSON.toJSONString(bean.getDataList()));
                    }
                }
            }
        }catch (Exception e){
            log.setResultCode(NptDict.RST_ERROR.getCode());
            log.setCausedBy(e);
        }finally {
            commonService.save(log);
        }
        return SUCCESS;
    }

    /**
     *作者：OWEN
     *时间：2016/11/20 14:22
     *描述:
     *      通过查询条件筛选数据池信息
     */
    public String authIndexAjax(){
        try {
            //获取当前的模型信息
            NptBaseModel model = getCurrentBaseModel();

            //获取页面上传递的详细入口信息，特别是某条记录的业务主键值
            NptWebBridgeBean bean = getWebConditions();

            //加载指定指定指定宿主实例(比如某个具体的企业)的模型信息
            baseModelService.loadBaseModelAuthGroupsByPK(bean,model);

            this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), bean);
        }catch (Exception e){
        }
        return SUCCESS;
    }

    /**
     * 作者：owen
     * 日期：2016/10/25 20:54
     * 备注：
     *      查看某个数据池的指定业务实体的多条记录
     * 参数：
     * 返回：
     */
    public String authBlockMore(){
        NptLog log = getBusinessLog();
        log.setActionName(this.getClass().getSimpleName() + NptRMSAction.LOG_SEPRATOR + NptDict.LGA_AUTH_BLOCK_MORE.getTitle());
        log.setActionType(NptDict.LGA_AUTH_BLOCK_MORE.getCode());
        try {
            //获取页面上传递的详细入口信息，特别是某条记录的业务主键值
            NptWebBridgeBean bean = getWebConditions();

            //获取数据池
            NptBaseModelPool pool = baseModelService.findBaseModelGrouPoolById(bean.getParentId());

            //加载指定指定指定宿主实例(比如某个具体的企业)的模型信息
            NptWebDetailBlock block = baseModelService.getBaseModelGrouPoolData(pool,bean.getPrimaryKeyValue(),false);
            Collection<NptWebDetailBlock> dataList = new ArrayList<>();
            dataList.add(block);
            bean.setDataList(dataList);
            this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), bean);

            log.setResultCode(NptDict.RST_SUCCESS.getCode());
            log.setResults(JSON.toJSONString(bean.getDataList()));
        }catch (Exception e){
            log.setResultCode(NptDict.RST_ERROR.getCode());
            log.setCausedBy(e);
        }finally {
            commonService.save(log);
        }
        return SUCCESS;
    }

    /**
     * 作者：owen
     * 日期：2016/10/25 22:15
     * 备注：
     *      加载附属级别的数据池的头条数据
     * 参数：
     * 返回：
     */
    public String authBlockLasted(){

        NptLog log = getBusinessLog();
        log.setActionName(this.getClass().getSimpleName() + NptRMSAction.LOG_SEPRATOR + NptDict.LGA_AUTH_BLOCK_LASTED.getTitle());
        log.setActionType(NptDict.LGA_AUTH_BLOCK_LASTED.getCode());
        getGroupoolBlockData(log);

        return SUCCESS;
    }

    /**
     * 作者：owen
     * 日期：2016/10/25 20:56
     * 备注：
     *      加载指定数据池的所有可申请字段及其状态
     * 参数：
     * 返回：
     */
    public String authLoadApplyFields(){
        //获取页面上传递的详细入口信息，特别是某条记录的业务主键值
        NptWebBridgeBean bean = getWebConditions();
        //获取数据池
        NptBaseModelPool pool = baseModelService.findBaseModelGrouPoolById(bean.getParentId());
        if(null != pool){
            NptWebDetailBlock block = baseModelService.getBaseModelGroupoolApplyFields(pool,bean.getPrimaryKeyValue());
            bean.setDataList(block);
        }
        this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), bean);

        return SUCCESS;
    }

    /**
     * 作者：owen
     * 日期：2016/10/24 21:04
     * 备注：
     *      加载附属数据池的详细数据
     * 参数：
     * 返回：
     */
    public String getAdditionalGroupoolData(){

        NptLog log = getBusinessLog();
        log.setActionName(this.getClass().getSimpleName() + NptRMSAction.LOG_SEPRATOR+ NptDict.LGA_AUTH_ADDITIONAL_POOL.getTitle());
        log.setActionType(NptDict.LGA_AUTH_ADDITIONAL_POOL.getCode());
        getGroupoolBlockData(log);

        return SUCCESS;
    }


    /**
     *作者：OWEN
     *时间：2016/11/11 17:50
     *描述:
     *      获取数据块的详细数据
     */
    private void getGroupoolBlockData(NptLog log){
        try {
            //获取页面上传递的详细入口信息，特别是某条记录的业务主键值
            NptWebBridgeBean bean = getWebConditions();

            NptBaseModelPool pool = baseModelService.findBaseModelGrouPoolById(bean.getParentId());

            NptWebDetailBlock block = baseModelService.getBaseModelGrouPoolData(pool,bean.getPrimaryKeyValue(),true);

            bean.setDataList(block);

            this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), bean);

            log.setResultCode(NptDict.RST_SUCCESS.getCode());
            log.setResults(JSON.toJSONString(bean.getDataList()));
        }catch (Exception e){
            log.setResultCode(NptDict.RST_ERROR.getCode());
            log.setCausedBy(e);
        }finally {
            commonService.save(log);
        }
    }
    /**
     * 作者：owen
     * 日期：2016/11/4 12:01
     * 备注：
     *      获取指定数据池关联的其它数据池的详细数据
     * 参数：
     * 返回：
     */
    public String getGroupoolLinkedPoolData(){

        NptLog log = getBusinessLog();
        log.setActionName(this.getClass().getSimpleName() + NptRMSAction.LOG_SEPRATOR + NptDict.LGA_AUTH_POOL_LINKED.getTitle());
        log.setActionType(NptDict.LGA_AUTH_POOL_LINKED.getCode());
        try {
            //获取页面上传递的详细入口信息，特别是某条记录的业务主键值
            NptWebBridgeBean bean = getWebConditions();
            //获取数据池
            Collection<NptWebDetailBlock> blocks =
                    baseModelService.getBaseModelGroupoolLinkedPoolData(bean.getParentId(),bean.getPrimaryKeyId(),bean.getPrimaryKeyValue());
            bean.setDataList(blocks);
            this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), bean);

            log.setResultCode(NptDict.RST_SUCCESS.getCode());
            log.setResults(JSON.toJSONString(bean.getDataList()));
        }catch (Exception e){
            log.setResultCode(NptDict.RST_ERROR.getCode());
            log.setCausedBy(e);
        }finally {
            commonService.save(log);
        }
        return SUCCESS;
    }


    /**
     *  author: owen
     *  date:   2017/3/27 21:52
     *  note:
     *          数据池索引字段列表
     */
    public String poolIndex(){

        NptLog log = getBusinessLog();
        log.setActionName(this.getClass().getSimpleName() + NptRMSAction.LOG_SEPRATOR + NptDict.LGA_OPEN_POOL_INDEX.getTitle());
        log.setActionType(NptDict.LGA_OPEN_POOL_INDEX.getCode());

        try {
            NptWebBridgeBean bean = getWebConditions();
            //存放点击模块的父id
            this.getRequest().getSession().setAttribute("parentId",bean.getParentId());
            /*原来的代码，注释掉
            NptBaseModelPool pool = baseModelService.fastFindBaseModelGrouPoolById(bean.getParentId());
            if(null != pool) {
                baseModelService.loadBaseModelPoolConditions(pool,bean);
                baseModelService.getBaseModelGroupoolPaginationData(pool.getId(), bean, true, false,false);
            }
             */
            NptWebBridgeBean nptBean=getNptWebBridgeBean(bean);
            this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), nptBean);

            log.setResultCode(NptDict.RST_SUCCESS.getCode());
            log.setResults(JSON.toJSONString(nptBean.getDataList()));
        }catch (Exception e){
            log.setResultCode(NptDict.RST_ERROR.getCode());
            log.setCausedBy(e);
        }finally {
            commonService.save(log);
        }
        return SUCCESS;
    }

    public String poolIndexAjax(){

        NptLog log = getBusinessLog();
        log.setActionName(this.getClass().getSimpleName() + NptRMSAction.LOG_SEPRATOR + NptDict.LGA_OPEN_POOL_INDEX.getTitle());
        log.setActionType(NptDict.LGA_OPEN_POOL_INDEX.getCode());

        try {
            NptWebBridgeBean bean = getWebConditions();
            Long poolId = getWebParentId();
            baseModelService.getBaseModelGroupoolPaginationData(poolId, bean, true, false,false);
            this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), bean);

            log.setResultCode(NptDict.RST_SUCCESS.getCode());
            log.setResults(JSON.toJSONString(bean.getDataList()));
        }catch (Exception e){
            log.setResultCode(NptDict.RST_ERROR.getCode());
            log.setCausedBy(e);
        }finally {
            commonService.save(log);
        }
        return SUCCESS;
    }

    public String poolDetail(){

        NptLog log = getBusinessLog();
        log.setActionName(this.getClass().getSimpleName() + NptRMSAction.LOG_SEPRATOR + NptDict.LGA_AUTH_POOL_DETAIL.getTitle());
        log.setActionType(NptDict.LGA_AUTH_POOL_DETAIL.getCode());
        try {
            //获取页面上传递的详细入口信息，特别是某条记录的业务主键值
            NptWebBridgeBean bean = getWebConditions();
            Long poolId = getWebParentId();

            NptBaseModelPool pool = baseModelService.findBaseModelGrouPoolById(poolId);

            baseModelService.loadBaseModelPoolDataByUK(bean,pool);

            this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(), bean);

            log.setResultCode(NptDict.RST_SUCCESS.getCode());
            log.setResults(JSON.toJSONString(bean.getDataList()));
        }catch (Exception e){
            log.setResultCode(NptDict.RST_ERROR.getCode());
            log.setCausedBy(e);
        }finally {
            commonService.save(log);
        }
        return SUCCESS;
    }

    /**
     * 设置NptWebBridgeBean获取
     */
    private NptWebBridgeBean getNptWebBridgeBean(NptWebBridgeBean bean){
        NptBaseModelPool pool = baseModelService.fastFindBaseModelGrouPoolById(bean.getParentId());
        if(null != pool) {
            baseModelService.loadBaseModelPoolConditions(pool,bean);
            baseModelService.getBaseModelGroupoolPaginationData(pool.getId(), bean, true, false,false);
        }
        return bean;
    }

}
