package com.npt.grs.query.nativeModel;

import com.alibaba.fastjson.JSONObject;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.database.manager.NptDatabaseManager;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.map.bean.NptBaseMap;
import com.npt.bridge.map.bean.NptRecursionMap;
import com.npt.bridge.map.graph.NptGraphData;
import com.npt.bridge.map.graph.NptMapUIConverter;
import com.npt.bridge.map.service.NptMapService;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.NptBaseModelPoolCdt;
import com.npt.bridge.model.NptBaseModelPoolStatistic;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.bridge.util.NptScoreUtil;
import com.npt.grs.model.service.NptGRSBaseModelService;
import com.npt.grs.query.NptGRSQueryAction;
import com.npt.rms.arch.service.NptRmsArchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.*;

/**
 * author: owen
 * date:   2017/4/11 下午5:38
 * note:
 */
@Controller("npt.grs.query.bsmap")
public class NptBusinessMapQueryAction extends NptGRSQueryAction{

    public enum BM_TYPE{
        ECHARTV2,
        ECHARTV3,
        D3
    }


    @Autowired
    private NptMapService mapService;
    @Resource(name = "nptDssOracleManager")
    protected NptDatabaseManager databaseManager;
    @Resource(name = "rmsArchService")
    private NptRmsArchService rmsArchService;
    @Autowired
    private NptGRSBaseModelService baseModeService;
    /**
     * 作者：owen
     * 日期：2016/10/20 14:19
     * 备注：
     * 获取当前的基础模型信息
     * 若子类重写之，则返回子类自身控制的模型信息
     * 若子类不重写之，则从页面接收模型ID并加载之
     * 参数：
     * 返回：
     */
    @Override
    public NptBaseModel getCurrentBaseModel() {
        return baseModelService.getBaseModelHostNativeModel(NptDict.BMH_BSMAP);
    }

    /**
     *  author: owen
     *  date:   2017/4/19 下午3:35
     *  note:
     *          图谱首页
     */
    public String mapIndex(){

        NptBaseModel mapModel = getCurrentBaseModel();

        Collection<NptBaseModelPoolCdt> cdts = mapService.loadModelSearchConditions(mapModel);

        String placeHolder = "";

        if(null != cdts && !cdts.isEmpty()){
            Iterator<NptBaseModelPoolCdt> iteCdt = cdts.iterator();
            while (iteCdt.hasNext()){
                placeHolder += iteCdt.next().getFieldTitle();
                if(iteCdt.hasNext()){
                    placeHolder += "、";
                }
            }
        }

        this.setAttribute("_PLACE_HOLDER",placeHolder);

        return SUCCESS;
    }

    /**
     *  author: owen
     *  date:   2017/4/19 下午3:35
     *  note:
     *          图谱模糊查询
     */
    public String search(){

        String searchKeyValue = getParameter("searchKeyValue");

        NptBaseModel bmMapModel = getCurrentBaseModel();

        if(null != bmMapModel && !StringUtils.isBlank(searchKeyValue)){

            NptWebBridgeBean bean = new NptWebBridgeBean();
            NptDict result = mapService.fuzzySearch(bmMapModel,searchKeyValue,bean);

            this.setAttribute("_RESULT",result);
            this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(),bean);
        }

        return SUCCESS;
    }

    /**
     *  author: owen
     *  date:   2017/4/19 下午3:35
     *  note:   
     *          图谱详情
     */
    public String mapDetail(){

        String ukValue = getParameter("mapUKValue");

        NptBaseModel bsMapModel = getCurrentBaseModel();

        prepareMap(bsMapModel,ukValue,BM_TYPE.ECHARTV2,true);

        return SUCCESS;
    }

    /**
     *  author: zhanglei
     *  date:   2017/05/23 16:02
     *  note:
     *          d3图谱详情
     */
    public String mapDetailD3() {
        String ukValue = getParameter("mapUKValue");

        NptBaseModel bsMapModel = getCurrentBaseModel();

        /**
         *  构建图谱
         *
         */
        prepareMap(bsMapModel,ukValue,BM_TYPE.D3,false);

        /**
         *  构建概要信息
         *
         */
        prepareSummary(bsMapModel,ukValue);
        /**
         *  构建失信统计数据
         *
         */
        List<NptBaseModelPoolStatistic> badSts = prepareModelGroupStatistic(bsMapModel, ukValue, NptDict.STS_NEG);
        this.setAttribute("badSts", JSONObject.toJSON(badSts));
        this.setAttribute("badStsCount",countStatistic(badSts));
        /**
         *  构建诚信统计数据
         *
         */
        List<NptBaseModelPoolStatistic> goodSts = prepareModelGroupStatistic(bsMapModel, ukValue, NptDict.STS_POS);
        this.setAttribute("goodSts", JSONObject.toJSON(goodSts));
        this.setAttribute("goodStsCount",countStatistic(goodSts));
        /**
         *  构建企业评分
         *
         */
        List<NptBaseModelPoolStatistic> allSts = new ArrayList<>();
        allSts.addAll(badSts);
        allSts.addAll(goodSts);
        prepareScore(allSts);

        return SUCCESS;
    }


    /**
     *作者：owen
     *时间: 2017/5/24 15:16
     *描述:
     *      构建图谱
     */
    private void prepareMap(NptBaseModel model,String ukValue,BM_TYPE bmType,Boolean meargeLine){
        if(null != model && !StringUtils.isBlank(ukValue)){
            NptRecursionMap mapData = mapService.instance(model,ukValue);

            NptBaseMap bm = mapService.toBaseMap(mapData);

            mapService.prepareForShow(bm,meargeLine);

            if(null != mapData && mapData.getResult().equals(NptDict.RST_SUCCESS)){
                NptGraphData graphData = new NptGraphData();

                if(BM_TYPE.ECHARTV2.equals(bmType)){
                    NptMapUIConverter.convertForEchart2(bm,graphData);
                }else if(BM_TYPE.ECHARTV3.equals(bmType)){

                }else if(BM_TYPE.D3.equals(bmType)) {
                    NptMapUIConverter.convertForD3(bm, graphData);
                }

                if(null != graphData) {
                    this.setAttribute("uiNodes", graphData.getNodesJSON());
                    this.setAttribute("uiLinks", graphData.getLinksJSON());
                }
            }
        }
    }

    /**
     *作者：owen
     *时间: 2017/5/24 15:22
     *描述:
     *      构建概要信息
     */
    private void prepareSummary(NptBaseModel model,String ukValue){

    }

    private Integer countStatistic(Collection<NptBaseModelPoolStatistic> ss){
        Integer count = 0;
        if(null != ss && !ss.isEmpty()){
            for(NptBaseModelPoolStatistic s:ss){
                count += s.getDataCount().intValue();
            }
        }
        return count;
    }
    /**
     *作者：owen, zhanglei
     *时间: 2017/5/24 15:22
     *描述:
     *      构建模型分组的统计信息
     */
    private List<NptBaseModelPoolStatistic> prepareModelGroupStatistic(NptBaseModel model,String ukValue, NptDict dict){

        /**
         *      1,通过上面两个参数获取业务主键值
         *
         *      2，获取系统原生企业模型
         *
         *      3，失信数据池 SCORE <0
         *          诚信数据池 SCORE >0
         *
         *      4, 编辑一个方法  loadBaseMdoelPoolStatistic(NptbaseModePool,String pkValue)
         *                      这里边在databaseManager里添加一个方法  getRowCount()
         *
         */
        List<NptBaseModelPoolStatistic> result = new ArrayList<>();
        Map<String, Object> tyValues = baseModelService.getModelMainPoolTypicalValueByUKValue(model, ukValue);
        if (null != tyValues && !tyValues.values().isEmpty()) {
            List<Object> valueList = new ArrayList<>(tyValues.values());
            String pkValue = String.valueOf(valueList.get(0));

            NptBaseModel businessNativeModel = baseModelService.getBaseModelHostNativeModel(NptDict.BMH_BUSINESS);
            Integer startScore = null;
            Integer endScore = null;
            if (dict.getCode() == NptDict.STS_POS.getCode()) {
                startScore = 1;
            } else {
                endScore = -1;
            }
            Collection<NptBaseModelPool> pools = baseModeService.getBaseModelPools(businessNativeModel, startScore, endScore);
            for (NptBaseModelPool pool : pools) {
                NptBaseModelPoolStatistic sts = loadBaseMdoelPoolStatistic(pool, pkValue);
                if (sts.getDataCount() != 0) {
                    result.add(sts);
                }
            }
        }
        return result;
    }


    /**
     *  author: zhanglei
     *  date:   2017/05/24 17:15
     *  note:
     *          在数据池中对业务主键进行分析
     */
    private NptBaseModelPoolStatistic loadBaseMdoelPoolStatistic(NptBaseModelPool pool, String pkValue) {
        NptBaseModelPoolStatistic statistic = new NptBaseModelPoolStatistic();
        statistic.setPoolName(pool.getPoolTitle());
        statistic.setPoolScore(pool.getScoreWeight());
        statistic.setDataCount(databaseManager.getRowCount(rmsArchService.fastFindDataTypeById(pool.getDataTypeId()), rmsArchService.fastFindDataFieldById(pool.getPrimaryFieldId()), pkValue));
        statistic.setPoolId(pool.getId());
        statistic.setSearchKey(pkValue);
        return statistic;
    }

    /**
     *作者：owen
     *时间: 2017/5/24 15:23
     *描述:
     *      构建评分
     */
    private void prepareScore(Collection<NptBaseModelPoolStatistic> poolStatistics){

        Integer finalScore = NptScoreUtil.businessSimpleScore(poolStatistics);

        NptScoreUtil.BS_GRADE grade = NptScoreUtil.businessSimpleGrade(finalScore);

        this.setAttribute("creditScore",finalScore);
        this.setAttribute("creditGrade",grade.name());

    }

    /**
     *  author: owen
     *  date:   2017/4/19 下午3:54
     *  note:
     *          加载图谱结点的基本信息
     */
    public String nodeDetail(){
        Long poolId = getLongParameter("poolId");
        String ukValue = getParameter("ukValue");

        NptWebBridgeBean bean = new NptWebBridgeBean();

        NptDict result = mapService.loadNodeDetail(poolId,ukValue,bean);
        if(NptDict.RST_SUCCESS.equals(result)){
            this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(),bean);
        }
        this.setAttribute("_RESULT",result);

        return SUCCESS;
    }
}
