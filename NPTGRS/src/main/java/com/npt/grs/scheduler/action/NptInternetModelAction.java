package com.npt.grs.scheduler.action;

import com.alibaba.fastjson.JSON;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.dict.NptBusinessCode;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.*;
import com.npt.bridge.util.NptAjaxTask;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.bridge.util.NptHttpUtil;
import com.npt.bridge.util.NptSessionTaskHelper;
import com.npt.grs.model.manager.NptBaseModelDataTimestampManager;
import com.npt.grs.query.NptGRSQueryAction;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.rms.base.NptRmsCommonService;
import com.npt.rms.log.entity.NptLog;
import com.npt.rms.remote.entity.NptRemoteSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.summer.extend.orm.condition.Conditions;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2016/12/14 21:22
 * 描述:
 */
@Controller("npt.grs.internet")
public class NptInternetModelAction extends NptGRSQueryAction{

    @Resource(name = "rmsArchService")
    private NptRmsArchService archService;
    @Resource(name = "rmsCommonService")
    protected NptRmsCommonService commonService;
    @Autowired
    private NptBaseModelDataTimestampManager baseModelDataTimestampManager;

    public String index(){
        Collection<NptBaseModel> models = baseModelService.listModels(null, NptDict.BMC_OUTSIDE);
        if(null != models){
            for(NptBaseModel m:models){
                NptDict dict = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.BMH,m.getHostId());
                NptDict result = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.BMC,m.getCateId());
                if (null != dict)  m.setHostTitle(dict.getTitle());
                if (null != result) m.setCateTitle(result.getTitle());
            }
        }
        this.setAttribute("_MODEL_LIST", models);
        return SUCCESS;
    }

    /**
     *  author: zhanglei
     *  date:   2017/06/02 15:38
     *  note:
     *          列出 model下所有 pool
     */
    public String listPools() {
        NptBaseModel model = baseModelService.fastFindBaseModelById(getWebId());
        Collection<NptBaseModelPool> pools = baseModelService.getBaseModelGrouPools(model, NptDict.IDS_ENABLED, false);

        List<NptBaseModelPoolStamp> poolStamps = baseModelDataTimestampManager.findByCondition(Conditions.in(NptBaseModelPoolStamp.POOL_ID, pools.stream().map(NptBaseModelPool::getDataTypeId).collect(Collectors.toList())));

        this.setAttribute("pools", pools);
        this.setAttribute("poolStamps", poolStamps);
        return SUCCESS;
    }

    /**
     *作者：OWEN
     *时间：2016/11/28 16:02
     *描述:
     *      向外部子系统同步基础模型的信息
     */
    public void synchronizeBaseModel() {

        NptSessionTaskHelper.removeTask(getSession(),SYNC_MODEL_STRC_TASKID);

        /**
         * 创建反馈任务，默认结果正常，不结束
         */
        NptAjaxTask task = new NptAjaxTask("开始进行模型结构的同步");
        task.setResult(true);
        task.setStop(false);
        NptSessionTaskHelper.updateTask(getSession(),SYNC_MODEL_STRC_TASKID,task);


        NptLog log = commonService.makeLog();
        log.setBusinessName(NptDict.LGB_SYNC.getTitle());
        log.setBusinessType(NptDict.LGB_SYNC.getCode());
        log.setResultCode(NptDict.RST_ERROR.getCode());
        log.setActionName(NptDict.LGA_SYNC_STRUCTURE.getTitle());
        log.setActionType(NptDict.LGA_SYNC_STRUCTURE.getCode());

        Long modelId = getLongParameter("modelId");
        if(null == modelId || modelId.equals(NptCommonUtil.getDefaultParentId())){
            /**
             * 更新反馈任务
             */
            task.setStop(true);
            task.setResult(false);
            task.setTextInfo("指定的模型ID不存在!");
            NptSessionTaskHelper.updateTask(getSession(),SYNC_MODEL_STRC_TASKID,task);
        }else {

            NptBaseModelStructure modelStructure = baseModelService.loadBaseModelStructure(modelId);

            NptDict checker = beforeBaseModelSync(modelStructure);

            if(NptDict.RST_SUCCESS.equals(checker)){
                String actionUrl = getHttpUrl(NptDict.RMT_SYNC_STRUCTURE);
                if(null != actionUrl){
                    String encodedJsonData = NptHttpUtil.pack(JSON.toJSONString(modelStructure));
                    NptDict result = NptHttpUtil.httpPost(actionUrl, encodedJsonData);

                    task.setTextInfo("模型已完成向外系统的同步，同步结果为[" + result.getTitle() + "]");
                    NptSessionTaskHelper.updateTask(getSession(),SYNC_MODEL_STRC_TASKID,task);

                    log.setResultCode(result.getCode());
                }else {
                     task.setTextInfo("未设置同步模型的外系统链接，停止同步");
                    NptSessionTaskHelper.updateTask(getSession(),SYNC_MODEL_STRC_TASKID,task);
                }
            }
        }
        commonService.save(log);
    }

    public static final String SYNC_MODEL_STRC_TASKID = "SYNC_MODEL_STRC_TASKID";
    public static final String SYNC_MODEL_DATA_TASKID = "SYNC_MODEL_DATA_TASKID";
    public static final String SYNC_POOL_DATA_TASKID = "SYNC_POOL_DATA_TASKID";
    public static final String SYNC_MODEL_DEPE_TASKID = "SYNC_MODEL_DEPE_TASKID";

    /**
     *作者：owen
     *时间：2016/12/13 20:31
     *描述:
     *      同步模型数据到外部查询系统
     */
    public void synchronizeBaseModelData(){

        NptSessionTaskHelper.removeTask(getSession(),SYNC_MODEL_DATA_TASKID);

        /**
         * 创建反馈任务，默认结果正常，不结束
         */
        NptAjaxTask task = new NptAjaxTask("开始进行模型数据的同步");
        task.setResult(true);
        task.setStop(false);

        NptSessionTaskHelper.putTask(getSession(),SYNC_MODEL_DATA_TASKID,task);

        NptLog log = commonService.makeLog();
        log.setBusinessName(NptDict.LGB_SYNC.getTitle());
        log.setBusinessType(NptDict.LGB_SYNC.getCode());
        log.setResultCode(NptDict.RST_ERROR.getCode());
        log.setActionName(NptDict.LGA_SYNC_INCDATA.getTitle());
        log.setActionType(NptDict.LGA_SYNC_INCDATA.getCode());

        Long modelId = getLongParameter("modelId");
        if(null == modelId || modelId.equals(NptCommonUtil.getDefaultParentId())){

            /**
             * 更新反馈任务
             */
            task.setStop(true);
            task.setResult(false);
            task.setTextInfo("指定的模型ID不存在!");
            NptSessionTaskHelper.updateTask(getSession(),SYNC_MODEL_DATA_TASKID,task);
        }else {
            NptBaseModelStructure modelStructure = baseModelService.loadBaseModelStructure(modelId);
            Boolean stop = stopForSecurity(modelStructure);
            if(stop){
                /**
                 * 更新反馈任务
                 */
                task.setStop(true);
                task.setResult(true);
                task.setTextInfo("鉴于安全考虑，个人信息不允许同步到其它系统中!");
                NptSessionTaskHelper.updateTask(getSession(),SYNC_MODEL_DATA_TASKID,task);
            }else {
                NptDict checker = beforeBaseModelSync(modelStructure);
                if (NptDict.RST_SUCCESS.equals(checker)) {
                    String actionUrl = getHttpUrl(NptDict.RMT_SYNC_INCDATA);
                    if (null == actionUrl) {
                        /**
                         * 更新反馈任务
                         */
                        task.setStop(true);
                        task.setResult(false);
                        task.setTextInfo("远程系统配置信息不存在!");
                        NptSessionTaskHelper.updateTask(getSession(), SYNC_MODEL_DATA_TASKID, task);
                    } else {
                        Collection<NptBaseModelPool> pools = new ArrayList<>();
                        Set<Long> groupIdSet = modelStructure.getGrouPoolMap().keySet();
                        for (Long gid : groupIdSet) {
                            Collection<NptBaseModelPool> gps = modelStructure.getGrouPoolMap().get(gid);
                            if (null != gps && !gps.isEmpty()) {
                                for (NptBaseModelPool p : gps) {
                                    if (!pools.contains(p)) {
                                        pools.add(p);
                                    }
                                }
                            }
                        }
                        NptDict result = syncPoolsData(pools, actionUrl);
                        log.setResultCode(result.getCode());

                        /**
                         * 更新反馈任务
                         */
                        task.setStop(true);
                        task.setResult(true);
                        task.setTextInfo(result.getTitle());
                        NptSessionTaskHelper.updateTask(getSession(), SYNC_MODEL_DATA_TASKID, task);
                    }
                } else {
                    /**
                     * 更新反馈任务
                     */
                    task.setStop(true);
                    task.setResult(false);
                    task.setTextInfo(checker.getTitle());
                    NptSessionTaskHelper.updateTask(getSession(), SYNC_MODEL_DATA_TASKID, task);
                }
            }
        }
        commonService.save(log);
    }

    /**
     *  author: zhanglei
     *  date:   2017/06/02 10:13
     *  note: 
     *          同步指定数据池
     */
    public void synchronizeSelectedPoolData() {

        NptSessionTaskHelper.removeTask(getSession(), SYNC_POOL_DATA_TASKID);

        /**
         * 创建反馈任务，默认结果正常，不结束
         */
        NptAjaxTask task = new NptAjaxTask("开始进行数据池数据的同步");
        task.setResult(true);
        task.setStop(false);

        NptSessionTaskHelper.putTask(getSession(), SYNC_POOL_DATA_TASKID, task);

        NptLog log = commonService.makeLog();
        log.setBusinessName(NptDict.LGB_SYNC.getTitle());
        log.setBusinessType(NptDict.LGB_SYNC.getCode());
        log.setResultCode(NptDict.RST_ERROR.getCode());
        log.setActionName(NptDict.LGA_SYNC_INCDATA.getTitle());
        log.setActionType(NptDict.LGA_SYNC_INCDATA.getCode());

        String[] poolIds = getParameterValues("poolId[]");
        for (int i = 0; i < poolIds.length; i++) {
            Long poolId = Long.valueOf(poolIds[i]);

            NptBaseModelPool pool = baseModelService.fastFindBaseModelGrouPoolById(poolId);

            if (null == pool) {

                /**
                 * 更新反馈任务
                 */
                task.setStop(true);
                task.setResult(false);
                task.setTextInfo("指定的数据池ID[" + poolId + "]不存在!");
                NptSessionTaskHelper.updateTask(getSession(), SYNC_POOL_DATA_TASKID, task);
            } else {
                NptBaseModelStructure modelStructure = baseModelService.loadBaseModelStructure(pool);
                Boolean stop = stopForSecurity(modelStructure);
                if (stop) {
                    /**
                     * 更新反馈任务
                     */
                    task.setStop(true);
                    task.setResult(true);
                    task.setTextInfo("鉴于安全考虑，个人信息不允许同步到其它系统中!");
                    NptSessionTaskHelper.updateTask(getSession(), SYNC_POOL_DATA_TASKID, task);
                } else {
                    NptDict checker = beforeBaseModelSync(modelStructure);
                    if (NptDict.RST_SUCCESS.equals(checker)) {
                        String actionUrl = getHttpUrl(NptDict.RMT_SYNC_INCDATA);
                        if (null == actionUrl) {
                            /**
                             * 更新反馈任务
                             */
                            task.setStop(true);
                            task.setResult(false);
                            task.setTextInfo("远程系统配置信息不存在!");
                            NptSessionTaskHelper.updateTask(getSession(), SYNC_POOL_DATA_TASKID, task);
                        } else {
                            Collection<NptBaseModelPool> pools = new ArrayList<>();
                            Set<Long> groupIdSet = modelStructure.getGrouPoolMap().keySet();
                            for (Long gid : groupIdSet) {
                                Collection<NptBaseModelPool> gps = modelStructure.getGrouPoolMap().get(gid);
                                if (null != gps && !gps.isEmpty()) {
                                    for (NptBaseModelPool p : gps) {
                                        if (!pools.contains(p)) {
                                            pools.add(p);
                                        }
                                    }
                                }
                            }
                            NptDict result = syncPoolsData(pools, actionUrl);
                            log.setResultCode(result.getCode());

                            /**
                             * 更新反馈任务
                             */
                            task.setStop(true);
                            task.setResult(true);
                            task.setTextInfo(result.getTitle());
                            NptSessionTaskHelper.updateTask(getSession(), SYNC_POOL_DATA_TASKID, task);
                        }
                    } else {
                        /**
                         * 更新反馈任务
                         */
                        task.setStop(true);
                        task.setResult(false);
                        task.setTextInfo(checker.getTitle());
                        NptSessionTaskHelper.updateTask(getSession(), SYNC_POOL_DATA_TASKID, task);
                    }
                }
            }
        }
        commonService.save(log);
    }

    private NptDict beforeBaseModelSync(NptBaseModelStructure modelStructure){
        NptAjaxTask task = NptSessionTaskHelper.getTask(getSession(), SYNC_MODEL_STRC_TASKID);
        if(null == task){
            task = new NptAjaxTask("");
        }

        if (null == modelStructure) {
            return NptDict.RST_EXCEPTION("模型结构不存在！");
        }else if(null == modelStructure.getModelGroups() || modelStructure.getModelGroups().isEmpty()){
            return NptDict.RST_EXCEPTION("指定的模型不存在模型分组!");
        }else if(null == modelStructure.getGrouPoolMap() || modelStructure.getGrouPoolMap().isEmpty()){
            return NptDict.RST_EXCEPTION("指定的模型不存在任何数据池!");
        }else {

            task.setTextInfo("模型已通过同步前的基本检测--结构正常，存在分组，存在数据池，开始检测模型内置状态");
            NptSessionTaskHelper.updateTask(getSession(),SYNC_MODEL_STRC_TASKID,task);

            NptDict result = baseModelService.statusFilter(modelStructure);

            task.setTextInfo("模型内置状态检测结果为[" + result.getTitle() + "]");
            NptSessionTaskHelper.updateTask(getSession(),SYNC_MODEL_STRC_TASKID,task);
            return result;
        }
    }

    private Boolean stopForSecurity(NptBaseModelStructure structure){
        if(null != structure){
            NptBaseModel model = structure.getModel();
            if(NptDict.BMH_PERSONAL.getCode() == model.getHostId()){
                return true;
            }
        }
        return false;
    }

    private String getHttpUrl(NptDict type){
        Collection<NptRemoteSystem> remoteSystems = commonService.getRemoteSystemByActionType(type);
        if (null != remoteSystems && !remoteSystems.isEmpty()) {
            return remoteSystems.iterator().next().getActionUrl();
        }
        return null;
    }

    private NptDict syncPoolsData(Collection<NptBaseModelPool> pools, String actionUrl){
        if(null != pools && !pools.isEmpty()){
            for(NptBaseModelPool p:pools) {
                if(p.getLockLevel().equals(NptDict.MPL_LEVEL_0.getCode())) {
                    NptDict result = syncSinglePoolData(p, actionUrl);
                    if (!NptDict.RST_SUCCESS.equals(result)) {
                        return result;
                    }
                }
            }
        }
        return NptDict.RST_SUCCESS;
    }

    private NptDict syncSinglePoolData(NptBaseModelPool pool, String actionUrl){

        NptAjaxTask task = new NptAjaxTask("开始同步数据池[" + pool.getPoolTitle() + "]的实体数据.");

        Timestamp upLimit = NptCommonUtil.getCurrentSysTimestamp();
        Integer start = 1;
        Integer end = start + NptHttpUtil.HTTP_TRANS_ROWS;
        Boolean haveData = false;

        String leadInfo = "开始同步数据池[" + pool.getPoolTitle() + "]的实体数据.";
        while (start < end) {

            NptBaseModelPoolRow poolData = baseModelService.loadBaseModelGrouPoolIncrementData(pool, upLimit, start, end,false);
            if(NptCommonUtil.IntegerZero().equals(poolData.getRowCount())){
                task.setTextInfo("数据池[" + pool.getPoolTitle() + "]暂时没有增量数据需要同步");
                task.setStop(false);
                task.setResult(true);
                NptSessionTaskHelper.updateTask(getSession(),SYNC_MODEL_DATA_TASKID,task);

                break;
            }

            String rtInfo = "\n当前正在同步的数据为第[" + start + "]-->[" + end + "]条";
            task.setTextInfo(leadInfo + rtInfo);
            task.setResult(true);
            task.setStop(false);
            NptSessionTaskHelper.updateTask(getSession(),SYNC_MODEL_DATA_TASKID,task);

            String encodedJsonData = NptHttpUtil.pack(JSON.toJSONString(poolData));
            NptDict result = NptHttpUtil.httpPost(actionUrl, encodedJsonData);


            String resultInfo = "同步结果:";

            if(NptDict.RST_SUCCESS.equals(result)){
                start = end;
                end = start + NptHttpUtil.HTTP_TRANS_ROWS;
                haveData = true;

                resultInfo += "\n" + NptDict.RST_SUCCESS.getTitle();
                task.setTextInfo(rtInfo + resultInfo);
                task.setStop(false);
                task.setResult(true);

                NptSessionTaskHelper.updateTask(getSession(),SYNC_MODEL_DATA_TASKID,task);
            }else {
                resultInfo += "\n" + result.getTitle();
                task.setTextInfo(rtInfo + resultInfo);
                task.setStop(true);
                task.setResult(false);

                NptSessionTaskHelper.updateTask(getSession(),SYNC_MODEL_DATA_TASKID,task);

                return result;
            }

        }
        if(haveData) {
            baseModelService.updateBaseModelPoolDataTimestamp(pool.getId(), NptDict.DUB_OUT_SYNC, upLimit);
        }
        return NptDict.RST_SUCCESS;
    }

    /**
     *作者：jss
     *时间：2018年4月23日11:05:52
     *描述:
     *      测试同步功能
     */
    public String syncTest() {

            String actionUrl = getHttpUrl(NptDict.RMT_SYNC_TEST_REP);
            NptDict result = NptHttpUtil.httpPost(actionUrl, "测试");
            this.setAttribute("result",result.getTitle());
            return SUCCESS;
    }

    /**
     *作者：OWEN
     *时间：2016/11/29 19:27
     *描述:
     *      向外部系统同步组织机构
     */
    public void synchronizeDependency() {

        NptSessionTaskHelper.removeTask(getSession(),SYNC_MODEL_DEPE_TASKID);

        /**
         * 创建反馈任务，默认结果正常，不结束
         */
        NptAjaxTask task = new NptAjaxTask("开始进行模型依赖的同步");
        task.setResult(true);
        task.setStop(false);

        NptSessionTaskHelper.putTask(getSession(),SYNC_MODEL_DEPE_TASKID,task);

        NptLog log = commonService.makeLog();
        log.setBusinessName(NptDict.LGB_SYNC.getTitle());
        log.setBusinessType(NptDict.LGB_SYNC.getCode());
        log.setResultCode(NptDict.RST_ERROR.getCode());
        log.setActionName(NptDict.LGA_SYNC_DEPENDENCY.getTitle());
        log.setActionType(NptDict.LGA_SYNC_DEPENDENCY.getCode());

        Date thisData = new Date();

        List<NptLogicDataProvider> orgList = (List<NptLogicDataProvider>) archService.listAllEnabledOrg();
        List<NptBusinessCode> codeList = (List<NptBusinessCode>) commonService.listIncrementCode(thisData);

        if(null != orgList && null != codeList) {
            task.setTextInfo("要同步的机构数据量为[" + orgList.size() + "];要同步的码表数量为[" + codeList.size() + "]");
            NptSessionTaskHelper.updateTask(getSession(), SYNC_MODEL_DEPE_TASKID, task);
        }

        String actionUrl = getHttpUrl(NptDict.RMT_SYNC_DEPENDENCY);
        if(null != actionUrl){

            Map<Integer,List<NptLogicDataProvider>> batchProvider = new HashMap<>();

            if(null != orgList) {
                int k = 0;
                for (int i = 1; i <= orgList.size(); i++) {
                    List newlist = batchProvider.get(k);
                    if(null == newlist){
                        newlist = new ArrayList();
                    }
                    newlist.add(orgList.get(i-1));
                    batchProvider.put(k, newlist);
                    if(i % NptHttpUtil.HTTP_TRANS_ROWS == 0) {
                        k++;
                    }
                }
            }

            Map<Integer,List<NptBusinessCode>> batchCode = new HashMap<>();

            if(null != codeList) {
                int k = 0;
                for (int i = 1; i <= codeList.size(); i++) {
                    List newlist = batchCode.get(k);
                    if(null == newlist){
                        newlist = new ArrayList();
                    }
                    newlist.add(codeList.get(i-1));
                    batchCode.put(k, newlist);
                    if(i % NptHttpUtil.HTTP_TRANS_ROWS == 0) {
                        k++;
                    }
                }
            }

            int loop = batchProvider.size() > batchCode.size()?batchProvider.size():batchCode.size();

            NptDict result = NptDict.RST_SUCCESS;
            for(int i = 0;i < loop;i++){
                NptBaseModelDependency dependency = new NptBaseModelDependency();
                Collection<NptLogicDataProvider> providers = batchProvider.get(i);
                Collection<NptBusinessCode> codes = batchCode.get(i);

                if(null != providers && !providers.isEmpty()){
                    dependency.setProviders(providers);
                }
                if(null != codes && !codes.isEmpty()){
                    dependency.setBusinessCodes(codes);
                }


                String encodedJsonData = NptHttpUtil.pack(JSON.toJSONString(dependency));
                result = NptHttpUtil.httpPost(actionUrl, encodedJsonData);

                task.setTextInfo("总同步批次为[" + loop + "].本批次为[" + i + "],同步的机构数量为[" + (null == providers?0:providers.size()) + "] + 同步的码表数量为[" + (null == codes?0:codes.size()) + "].\n同步结果为[" + result.getTitle() + "]");
                NptSessionTaskHelper.updateTask(getSession(),SYNC_MODEL_DEPE_TASKID,task);

                if (!NptDict.RST_SUCCESS.equals(result)) {
                    log.setResultCode(result.getCode());
                    log.setResults(result.getTitle());
                    this.outputErrorResult(result.getTitle());
                    break;
                }
            }

            if(NptDict.RST_SUCCESS.equals(result)) {
                commonService.updateBusinessCodeIncrementStamp(thisData);
            }
            log.setResultCode(result.getCode());
            log.setResults(result.getTitle());
        }else {
        }
        commonService.save(log);
    }


    /**
     *作者：owen
     *时间：2016/12/15 11:00
     *描述:
     *      更新外部查询系统的IP的端口号
     */
    public void updateRemoteServer(){
        String ip = getParameter("remoteIp");
        String port = getParameter("remotePort");

        if(!NptHttpUtil.IPV4_PATTERN.matcher(ip).matches()){
            this.outputErrorResult("[" + ip + "]不是有效的IPV4!");
        }else{
            try {
                Integer iPort = Integer.parseInt(port);
                if(iPort < 1025 || iPort >= 65535){
                    this.outputErrorResult("操作系统中用户可用的端口号区间为[1025,65534],请重新设置!");
                }else {
                    commonService.updateRemoteSystem(ip, iPort);
                    this.outputSuccessResult(NptDict.RST_SUCCESS.getTitle());
                }
            }catch (NumberFormatException e){
                this.outputErrorResult("[" + port + "]系统端口号必须为数字!");
            }
        }
    }

    /**
     *作者：owen
     *时间：2016/12/15 15:25
     *描述:
     *      编辑外部查询系统的服务器地址信息
     */
    public String editRemoteServer(){
        Collection<NptRemoteSystem> server = commonService.getRemoteSystemByActionType(NptDict.RMT_SYNC_DEPENDENCY);
        if(null != server && !server.isEmpty()){
            this.setAttribute("REMOTE_IP",server.iterator().next().getSiteIp());
            this.setAttribute("REMOTE_PORT",server.iterator().next().getSitePort());
        }
        return SUCCESS;
    }

    public void ajaxTaskLoader(){
        String taskId = getParameter("taskId");

        NptAjaxTask task = NptSessionTaskHelper.getTask(getSession(),taskId);

        this.ajaxOutPutJson(JSON.toJSON(task));
    }
}
