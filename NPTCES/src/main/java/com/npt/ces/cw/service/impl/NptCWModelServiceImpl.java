package com.npt.ces.cw.service.impl;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.dataBinder.NptWebFieldDataArray;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.props.bean.NptBaseModelEx;
import com.npt.bridge.model.props.bean.NptBaseModelExMetaData;
import com.npt.bridge.model.props.bean.NptBaseModelGroupEx;
import com.npt.bridge.model.props.bean.NptBaseModelPoolEx;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.ces.cw.anylize.NptCreditWarningEntityData;
import com.npt.ces.cw.bean.NptCWAnalyzerIntervals;
import com.npt.ces.cw.bean.NptCWResultDetailBox;
import com.npt.ces.cw.entity.*;
import com.npt.ces.cw.manager.NptCWModelDimensionPropsManager;
import com.npt.ces.cw.manager.NptCWModelPropsManager;
import com.npt.ces.cw.manager.NptCWModelSubDmsPropsManager;
import com.npt.ces.cw.service.NptCWModelService;
import com.npt.ces.utils.NptCesUtil;
import com.npt.ces.utils.NptExcelPercentile;
import com.npt.grs.model.service.NptGRSBaseModelService;
import com.npt.rms.arch.service.NptRmsArchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.summer.extend.orm.condition.Conditions;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;

/**
 * author: owen
 * date:   2017/7/11 下午12:00
 * note:
 */
@Service
@Transactional
public class NptCWModelServiceImpl implements NptCWModelService{

    @Resource(name = "rmsArchService")
    private NptRmsArchService rmsArchService;
    @Resource(name = "nptGrsModelService")
    private NptGRSBaseModelService baseModelService;

    @Autowired
    private NptCWModelPropsManager modelPropsManager;
    @Autowired
    private NptCWModelDimensionPropsManager modelDimensionPropsManager;
    @Autowired
    private NptCWModelSubDmsPropsManager modelSubDmsPropsManager;


    @Override
    public Collection<NptBaseModelEx> listModels() {
        Collection<NptBaseModelEx> results = new ArrayList<>();
        Collection<NptBaseModel> models = new ArrayList<>();

        NptBaseModel bsCWModel = this.getCreditWarningBaseModel(NptDict.BMH_WARN_BS);
        NptBaseModel psCWModel = this.getCreditWarningBaseModel(NptDict.BMH_WARN_PS);
        if(null != bsCWModel){
            models.add(bsCWModel);
        }
        if(null != psCWModel){
            models.add(psCWModel);
        }

        if (null != models) {
            for (NptBaseModel m : models) {
                NptBaseModelEx cwModel = new NptBaseModelEx();

                NptDict dict = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.BMH, m.getHostId());
                NptDict result = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.BMC, m.getCateId());
                if (null != dict) m.setHostTitle(dict.getTitle());
                if (null != result) m.setCateTitle(result.getTitle());

                NptCWModelProps props = modelPropsManager.findUniqueByProperty(NptCWModelProps.PROPERTY_MODEL_ID, m.getId());
                cwModel.setBaseModel(m);
                cwModel.setModelProperties(props);
                results.add(cwModel);
            }
        }
        return results;
    }

    @Override
    public Collection<NptBaseModelGroupEx> listGroups(Long modelId) {
        Collection<NptBaseModelGroupEx> results = new ArrayList<>();
        NptBaseModel model = baseModelService.findBaseModelById(modelId);
        if (null != model) {
            Collection<NptBaseModelGroup> groupList = baseModelService.getBaseModelGroups(model,NptDict.IDS_ENABLED);
            if (groupList != null) {
                for (NptBaseModelGroup group : groupList) {
                    NptBaseModelGroupEx cwGroup = new NptBaseModelGroupEx();
                    NptCWModelDmsProps props = modelDimensionPropsManager.findUniqueByProperty(NptCWModelDmsProps.PROPERTY_GROUP_ID, group.getId());
                    cwGroup.setModelGroup(group);
                    cwGroup.setGroupProperties(props);
                    results.add(cwGroup);
                }
            }
        }
        return results;
    }

    @Override
    public Collection<NptBaseModelPoolEx> listPools(Long groupId) {
        Collection<NptBaseModelPoolEx> results = new ArrayList<>();
        NptBaseModelGroup group = baseModelService.findBaseModelGroupById(groupId);
        if (null != group) {
            Collection<NptBaseModelPool> poolList = baseModelService.getBaseModelGrouPools(group, null, true);
            if (poolList != null) {
                for (NptBaseModelPool pool : poolList) {
                    NptBaseModelPoolEx cwPool = new NptBaseModelPoolEx();
                    NptCWModelSubDmsProps props = modelSubDmsPropsManager.findUniqueByProperty(NptCWModelSubDmsProps.PROPERTY_POOL_ID, pool.getId());
                    cwPool.setModelPool(pool);
                    cwPool.setPoolProperties(props);
                    results.add(cwPool);
                }
            }
        }
        return results;
    }

    @Override
    public Collection<NptLogicDataField> listDataTypeFields(Long poolId) {
        Collection<NptLogicDataField> results = new ArrayList<>();
        if (poolId != null) {
            NptBaseModelPool pool = baseModelService.findBaseModelGrouPoolById(poolId);
            if (pool != null) {
                results = rmsArchService.listDataField(pool.getDataTypeId(), null, NptDict.IDS_ENABLED);
            }
        }
        return results;
    }

    @Override
    public Long getUkFieldId(Long poolId) {
        NptBaseModelPool pool = baseModelService.findBaseModelGrouPoolById(poolId);
        NptLogicDataType dataType = rmsArchService.fastFindDataTypeById(pool.getDataTypeId());
        return dataType.getUkFieldId();
    }

    /**
     * author  : owen
     * date    : 2017/7/11 上午11:08
     * params  :
     * []:
     * []:
     * note    :
     * 获取基础模型的预警拓展属性
     *
     * @param baseModel
     */
    @Override
    public NptCWModelProps getCreditWarningModelProperties(NptBaseModel baseModel) {
        if(null != baseModel && baseModel.checkIsEnable()){
            return modelPropsManager.findUniqueByCondition(Conditions.eq(NptCWModelProps.PROPERTY_MODEL_ID,baseModel.getId()));
        }
        return null;
    }

    /**
     * author  : owen
     * date    : 2017/7/11 上午11:09
     * params  :
     * []:
     * []:
     * note    :
     * 获取基础模型分组的预警拓展属性
     *
     * @param group
     */
    @Override
    public NptCWModelDmsProps getCreditWarningModelDimensionProperties(NptBaseModelGroup group) {

        if(null != group && group.checkIsEnable()){
            return modelDimensionPropsManager.findUniqueByCondition(Conditions.eq(NptCWModelDmsProps.PROPERTY_GROUP_ID,group.getId()));
        }
        return null;
    }

    @Override
    public NptCWModelDmsProps getCreditWarningModelDimensionPropertiesById(Long groupId) {

        NptBaseModelGroup group = baseModelService.findBaseModelGroupById(groupId);

        return getCreditWarningModelDimensionProperties(group);
    }

    @Override
    public Collection<NptCWModelDmsProps> getCreditWarningModelDimensionProperties(Collection<NptBaseModelGroup> groups) {
        Collection<NptCWModelDmsProps> dmsPropList = new ArrayList<>();

        if(null != groups && !groups.isEmpty()){
            for(NptBaseModelGroup g:groups){
                NptCWModelDmsProps dmsProp = getCreditWarningModelDimensionProperties(g);
                if(null != dmsProp){
                    dmsPropList.add(dmsProp);
                }
            }
        }
        return dmsPropList;
    }

    @Override
    public Collection<NptCWModelDmsProps> getCreditWarningModelDimensionPropertiesById(Collection<Long> groupIds) {
        Collection<NptCWModelDmsProps> dmsPropList = new ArrayList<>();

        if(null != groupIds && !groupIds.isEmpty()){
            for(Long gid:groupIds){
                NptCWModelDmsProps dmsProp = getCreditWarningModelDimensionPropertiesById(gid);
                if(null != dmsProp){
                    dmsPropList.add(dmsProp);
                }
            }
        }
        return dmsPropList;
    }

    /**
     * author  : owen
     * date    : 2017/7/11 上午11:14
     * params  :
     * []:
     * []:
     * note    :
     * 获取基础模型数据池的预警拓展属性
     *
     * @param pool
     */
    @Override
    public NptCWModelSubDmsProps getCreditWarningModelSubDimensionProperties(NptBaseModelPool pool) {
        if(null != pool && pool.checkIsEnable()){
            return modelSubDmsPropsManager.findUniqueByCondition(Conditions.eq(NptCWModelSubDmsProps.PROPERTY_POOL_ID,pool.getId()));
        }
        return null;
    }

    @Override
    public NptCWModelSubDmsProps getCreditWarningModelSubDimensionPropertiesById(Long poolId) {
        NptBaseModelPool pool = baseModelService.findBaseModelGrouPoolById(poolId);

        return getCreditWarningModelSubDimensionProperties(pool);
    }

    @Override
    public Collection<NptCWModelSubDmsProps> getCreditWarningModelSubDimensionProperties(Collection<NptBaseModelPool> pools) {

        Collection<NptCWModelSubDmsProps> result = new ArrayList<>();

        if(null != pools && !pools.isEmpty()){
            for(NptBaseModelPool p:pools){
                NptCWModelSubDmsProps subDmsProps = getCreditWarningModelSubDimensionProperties(p);
                if(null != subDmsProps){
                    result.add(subDmsProps);
                }
            }
        }

        return result;
    }

    @Override
    public Collection<NptCWModelSubDmsProps> getCreditWarningModelSubDimensionPropertiesById(Collection<Long> poolIds) {
        Collection<NptCWModelSubDmsProps> result = new ArrayList<>();

        if(null != poolIds && !poolIds.isEmpty()){
            for(Long pId:poolIds){
                NptCWModelSubDmsProps subDmsProps = getCreditWarningModelSubDimensionPropertiesById(pId);
                if(null != subDmsProps){
                    result.add(subDmsProps);
                }
            }
        }
        return result;
    }

    @Override
    public NptDict init(Long modelId, NptBaseModelExMetaData result) {

        if(null == result){
            return NptDict.RST_EXCEPTION("信用预警模型元数据初始化参数错误");
        }

        //检测模型的有效性
        NptBaseModel baseModel = baseModelService.fastFindBaseModelById(modelId);
        if(null == baseModel){
            return NptDict.RST_EXCEPTION("通过模型ID[" + modelId + "]未找到预警模型");
        }
        if(!baseModel.checkIsEnable()){
            return NptDict.RST_EXCEPTION("信用预警模型[ID:" + modelId + "]已被禁用");
        }

        NptBaseModelPool mainPool = baseModelService.getBaseModelGroupMainPool(baseModel);
        if(null == mainPool || !mainPool.checkIsEnable()){
            return NptDict.RST_EXCEPTION("信用预警模型[ID:" + modelId + "]未配置核心数据池或已被禁用");
        }
        result.setMainPool(mainPool);

        NptLogicDataField pkField = baseModelService.getBaseModelGrouPoolPrimaryField(mainPool);
        if(null == pkField || !pkField.checkIsEnable()){
            return NptDict.RST_EXCEPTION("信用预警模型[ID:" + modelId + "]的业务主键不存在或已被禁用");
        }
        result.setPkField(pkField);

        NptLogicDataType pkType = baseModelService.getBaseModelGrouPoolDataType(mainPool);
        if(null == pkType || !pkType.checkIsEnable()){
            return NptDict.RST_EXCEPTION("信用预警模型[ID:" + modelId + "]的业务数据表不存在或已被禁用");
        }
        result.setPkDataType(pkType);

        NptLogicDataField ukField = baseModelService.getBaseModelGrouPoolFieldById(pkType.getUkFieldId());
        if(null == ukField || !ukField.checkIsEnable()){
            return NptDict.RST_EXCEPTION("信用预警模型[ID:" + modelId + "]的数据主键不存在或已被禁用");
        }
        result.setUkField(ukField);

        //创建扩展模型
        NptBaseModelEx<NptCWModelProps> modelEx = new NptBaseModelEx<>();
        modelEx.setBaseModel(baseModel);

        NptCWModelProps cwModelProps = getCreditWarningModelProperties(baseModel);
        if(null == cwModelProps){
            return NptDict.RST_EXCEPTION("信用预警模型[ID:" + modelId + "]未配置模型自身的预警扩展属性");
        }
        modelEx.setModelProperties(cwModelProps);

        result.setModelEx(modelEx);

        //创建扩展模型维度
        result.setGroupExes(new ArrayList<>());
        result.setPoolExMap(new HashMap<>());
        Collection<NptBaseModelGroup> groups = baseModelService.getBaseModelGroups(baseModel,NptDict.IDS_ENABLED);
        if(null == groups || groups.isEmpty()){
            return NptDict.RST_EXCEPTION("信用预警模型[ID:" + modelId + "]未配置任何评估维度");
        }
        for(NptBaseModelGroup g:groups){
            if(g.checkIsEnable()) {
                NptBaseModelGroupEx<NptCWModelDmsProps> gex = new NptBaseModelGroupEx<>();
                gex.setModelGroup(g);
                NptCWModelDmsProps dimensionProps = getCreditWarningModelDimensionProperties(g);
                if (null != dimensionProps) {
                    gex.setGroupProperties(dimensionProps);

                    Collection<NptBaseModelPool> pools = baseModelService.getBaseModelGrouPools(g,NptDict.IDS_ENABLED,false);
                    if(null != pools && !pools.isEmpty()) {
                        List<NptBaseModelPoolEx> sdpList = new ArrayList<>();
                        for(NptBaseModelPool p:pools){
                            if(p.checkIsEnable()){
                                NptCWModelSubDmsProps subDimensionProps = getCreditWarningModelSubDimensionProperties(p);
                                if(null != subDimensionProps){
                                    NptBaseModelPoolEx<NptCWModelSubDmsProps> poolEx = new NptBaseModelPoolEx<>();
                                    poolEx.setModelPool(p);
                                    poolEx.setPoolProperties(subDimensionProps);
                                    sdpList.add(poolEx);
                                }
                            }
                        }
                        if(!sdpList.isEmpty()) {
                            result.getGroupExes().add(gex);
                            result.getPoolExMap().put(g.getId(),sdpList);
                        }
                    }
                }
            }
        }

        if(!result.getPoolExMap().isEmpty()){
            result.setCompleted(true);
        }else {
            result.setCompleted(false);
            result.setCompleteNote("经检测，预警模型[ID:" + modelId + "]未配置任何用于评估的数据池[或维度或子维度的状态未被启用]！");
        }

        return NptDict.RST_SUCCESS;
    }







    @Override
    public List<Object> loadBaseModelMainPoolPFieldValueByUFieldTail(NptBaseModelExMetaData metaData, String ufTail) {

        if(null != metaData && metaData.getCompleted() && !StringUtils.isEmpty(ufTail)) {
            String uFieldWhere = metaData.getUkField().getFieldDbName() + " like '%" + ufTail + "'";

            List<Object> pFieldValues= baseModelService.getPoolPKFieldDataListByUKFieldTailNO(metaData.getMainPool(),uFieldWhere);

            return pFieldValues;
        }
        return null;
    }


    @Override
    public List<String> loadBaseModelCommonPoolDistinctPFieldValues(NptBaseModelExMetaData metaData) {

        List<String> result = new ArrayList<>();

        if (null != metaData && metaData.getCompleted()) {

            Map<Long, List<NptBaseModelPoolEx>> map = metaData.getPoolExMap();

            List<NptBaseModelPoolEx> range = new ArrayList<>();
            map.forEach((k, v) -> {
                if (null != v && !v.isEmpty()) {
                    range.addAll(v);
                }
            });

            for (NptBaseModelPoolEx pe : range) {
                if(!metaData.getMainPool().equals(pe.getModelPool())) {
                    List<String> poolDisPKV = baseModelService.getPoolPKFieldDistinctValues(pe.getModelPool());
                    if (null != poolDisPKV && !poolDisPKV.isEmpty()) {
                        poolDisPKV.removeAll(result);
                        result.addAll(poolDisPKV);
                    }
                }
            }



            List<String> mainNotExisted = new ArrayList<>();
            for(String ceId:result){
               Boolean existed = baseModelService.checkPKValueExisted(metaData.getMainPool(),ceId);
               if(!existed){
                   mainNotExisted.add(ceId);
               }
            }

            result.removeAll(mainNotExisted);
        }

        return result;
    }

    /**
     * author  : owen
     * date    : 2017/7/11 下午3:09
     * params  :
     * []:
     * []:
     * note    :
     * 拿实体数据依照元数据的配置信息进行预警评估
     *
     * @param metaData
     * @param entityData
     */
    @Override
    public NptCWResultDetailBox compute(String batchNo, NptBaseModelExMetaData metaData, NptCreditWarningEntityData entityData) {

        NptCWResult cwResult = initCWResult(batchNo, metaData, entityData);

        if (null != cwResult) {

            NptCWResultDetailBox resultDetailBox = new NptCWResultDetailBox(entityData);
            resultDetailBox.setCwResult(cwResult);
            Integer finalScore = 0;
            Collection<NptBaseModelGroupEx> groupExes = metaData.getGroupExes();
            if (null != groupExes && !groupExes.isEmpty()) {

                for (NptBaseModelGroupEx ge : groupExes) {

                    NptCWModelDmsProps dmsProps = (NptCWModelDmsProps) ge.getGroupProperties();

                    NptCWDmsResult dmsResult = new NptCWDmsResult();
                    dmsResult.setBatchNo(batchNo);
                    dmsResult.setBaseModelGroupId(ge.getModelGroup().getId());
                    dmsResult.setCreditEntityId(entityData.getCreditEntityId());
                    dmsResult.setCreditEntityTitle(entityData.getCreditEntityTitle());
                    dmsResult.setCreditEntityType(entityData.getCreditEntityType());
                    dmsResult.setDimensionName(ge.getModelGroup().getGroupName());
                    dmsResult.setRiskScore(NptCommonUtil.IntegerZero());
                    dmsResult.setRiskLevel(NptCesUtil.getRiskLevelByScore(NptCommonUtil.IntegerZero()));

                    resultDetailBox.getDmsResults().add(dmsResult);

                    Map<Long, List<NptBaseModelPoolEx>> groupPool = metaData.getPoolExMap();
                    Map<Long, Collection<NptWebFieldDataArray>> poolsData = entityData.getEntityDataMap();

                    if (null != groupPool && !groupPool.isEmpty() && null != poolsData && !poolsData.isEmpty()) {

                        List<NptBaseModelPoolEx> poolExes = groupPool.get(ge.getModelGroup().getId());
                        Integer dmsScore = 0;
                        if (null != poolExes && !poolExes.isEmpty()) {

                            List<NptCWSubDmsResult> subDmsResults = new ArrayList<>();
                            for (NptBaseModelPoolEx pex : poolExes) {

                                Collection<NptWebFieldDataArray> pd = poolsData.get(pex.getModelPool().getId());

                                NptCWModelSubDmsProps sdp = (NptCWModelSubDmsProps) pex.getPoolProperties();
                                if (null != sdp) {

                                    List<NptCWSubDmsResultDetail> resultDetails = initCWSDMSResultDetail(batchNo, pex, entityData, pd);

                                    Integer riskScore = computeSubDmsScore(sdp, resultDetails);

                                    if (riskScore > NptCommonUtil.IntegerZero()) {

                                        NptCWSubDmsResult subDmsResult = new NptCWSubDmsResult();
                                        subDmsResult.setBatchNo(batchNo);
                                        subDmsResult.setSubDimensionTitle(pex.getModelPool().getAlias());
                                        subDmsResult.setDataTypeId(pex.getModelPool().getDataTypeId());
                                        subDmsResult.setProviderId(pex.getModelPool().getProviderId());
                                        subDmsResult.setProviderTitle(pex.getModelPool().getProviderTitle());
                                        subDmsResult.setCreditEntityId(entityData.getCreditEntityId());
                                        subDmsResult.setCreditEntityTitle(entityData.getCreditEntityTitle());
                                        subDmsResult.setCreditEntityType(entityData.getCreditEntityType());
                                        subDmsResult.setDimensionRstId(pex.getModelPool().getId());
                                        String sdrTitle = pex.getModelPool().getAlias();
                                        if(StringUtils.isEmpty(sdrTitle)){
                                            sdrTitle = pex.getModelPool().getPoolTitle();
                                        }
                                        subDmsResult.setSubDimensionTitle(sdrTitle);
                                        subDmsResult.setRiskScore(riskScore);
                                        subDmsResult.setRiskLevel(NptCesUtil.getRiskLevelByScore(riskScore));

                                        subDmsResults.add(subDmsResult);


                                        resultDetailBox.getSdmsRDetailMap().put(pex.getModelPool().getId(), resultDetails);

                                        if(null != sdp.getDisCount() && sdp.getDisCount() > 0){
                                            int tempScore = (int)Math.rint(riskScore*sdp.getDisCount()/100.0);
                                            if(riskScore >=1 && tempScore < 1){
                                                tempScore = 1;
                                            }
                                            dmsScore += tempScore;
                                        }else {
                                            dmsScore += riskScore;
                                        }
                                    }
                                }
                            }
                            resultDetailBox.getSdmsResultMap().put(ge.getModelGroup().getId(), subDmsResults);
                        }

                        dmsResult.setRiskScore(dmsScore);
                        dmsResult.setRiskLevel(NptCesUtil.getRiskLevelByScore(dmsScore));

                        if(null != dmsProps.getDisCount() && dmsProps.getDisCount() > 0) {
                            int tempScore = (int)Math.rint(dmsScore * dmsProps.getDisCount()/100.0);
                            if(dmsScore >=1 && tempScore < 1){
                                tempScore = 1;
                            }
                            finalScore += tempScore;
                        }else {
                            finalScore += dmsScore;
                        }
                    }
                }
            }
            //计算总体评分
            cwResult.setRiskScore(finalScore);
            cwResult.setRiskLevel(NptCesUtil.getRiskLevelByScore(finalScore));

            if(finalScore > NptCommonUtil.IntegerZero()) {
                resultDetailBox.setReady(true);
                return resultDetailBox;
            }
        }
        return null;
    }


    /**
     *  author  : owen
     *  date    : 2017/7/14 下午2:27
     *  params  :
     *              []:
     *              []:
     *  note    :
     *          计算子维度评分
     *
     *          先定性计算，即存在多少条数据，再根据是否存在金额数据决定是否进行定量计算
     */
    private Integer computeSubDmsScore(NptCWModelSubDmsProps sdp,List<NptCWSubDmsResultDetail> sdprd){

        Integer score = 0;

        if(null != sdp && null != sdprd && !sdprd.isEmpty()){

            Integer countScore = 0;
            Integer amountScore = 0;

            //定性区间计算
            NptCWAnalyzerIntervals intervals = new NptCWAnalyzerIntervals();

            intervals.setFormattedData(sdp.getCountIntervals());
            if(intervals.getLoaded()){
                countScore = intervals.getScoreByValue((double) sdprd.size());
            }


            for (NptCWSubDmsResultDetail sdrd : sdprd) {

                if(!StringUtils.isEmpty(sdrd.getAffairAmount())) {
                    Double amount = -1d;
                    try {
                        amount = Double.parseDouble(sdrd.getAffairAmount());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    if (amount > 0d) {
                        intervals.setFormattedData(sdp.getAmountGear());
                        if (intervals.getLoaded()) {
                            amountScore = intervals.getScoreByValue(amount);
                        }
                    }
                }
            }

            if(amountScore > 0){
                score = (int)Math.rint(countScore * 0.2 + amountScore * 0.8);
            }else {
                score = countScore;
            }
        }

        return score;
    }


    /**
     *  author  : owen
     *  date    : 2017/7/14 下午2:28
     *  params  :
     *              []:
     *              []:
     *  note    :
     *          构建子维度结果详情
     */
    private List<NptCWSubDmsResultDetail> initCWSDMSResultDetail(String batchNo,
                                                                 NptBaseModelPoolEx poolEx,
                                                                 NptCreditWarningEntityData entityData,
                                                                 Collection<NptWebFieldDataArray> fieldsValue){

        List<NptCWSubDmsResultDetail> resultDetails = new ArrayList<>();

        if(null != entityData && entityData.getDataLoaded()
                && null != poolEx
                && null != fieldsValue && !fieldsValue.isEmpty()) {

            NptCWModelSubDmsProps sdp = (NptCWModelSubDmsProps) poolEx.getPoolProperties();

            for(NptWebFieldDataArray fv:fieldsValue){

                NptCWSubDmsResultDetail rd = new NptCWSubDmsResultDetail();
                rd.setBatchNo(batchNo);
                rd.setCreditEntityId(entityData.getCreditEntityId());
                rd.setCreditEntityType(entityData.getCreditEntityType());
                rd.setCreditEntityTitle(entityData.getCreditEntityTitle());
                rd.setDataTypeId(poolEx.getModelPool().getDataTypeId());

                List<Long> whenids = sdp.spliteFieldsId(sdp.getWhenFieldId());
                StringBuffer whenValue = new StringBuffer();
                if(1 == whenids.size()){
                    NptWebFieldDataArray.NptWebFieldData when = fv.getFieldDataByFieldId(whenids.iterator().next());
                    if(null != when){
                        whenValue.append(when.getValue());
                    }
                }else if(2 == whenids.size()){
                    NptWebFieldDataArray.NptWebFieldData whens = fv.getFieldDataByFieldId(whenids.iterator().next());
                    NptWebFieldDataArray.NptWebFieldData whene = fv.getFieldDataByFieldId(whenids.iterator().next());

                    if(null != whens){
                        whenValue.append("[").append(whens.getValue()).append("-->");
                    }else {
                        whenValue.append("[暂未提供-->");
                    }
                    if(null != whene){
                        whenValue.append(whene.getValue()).append("]");
                    }else {
                        whenValue.append("暂未提供]");
                    }
                }
                NptWebFieldDataArray.NptWebFieldData where = fv.getFieldDataByFieldId(sdp.getWhereFieldId());
                NptWebFieldDataArray.NptWebFieldData what = fv.getFieldDataByFieldId(sdp.getWhatFieldId());

                List<Long> amountIds = sdp.spliteFieldsId(sdp.getAmountFieldId());
                if(null != amountIds && !amountIds.isEmpty()) {
                    Double totalAmount = 0d;
                    for(Long id:amountIds) {
                        NptWebFieldDataArray.NptWebFieldData amount = fv.getFieldDataByFieldId(id);
                        if(null != amount){
                            totalAmount += NptCommonUtil.sumAllDoubleNumberFromString(amount.getValue());
                        }
                    }
                    Double unitAmount = 0d;
                    if(null != sdp.getAmountMU()){
                        unitAmount = totalAmount * sdp.getAmountMU();
                    }
                    rd.setAffairAmount(String.valueOf(unitAmount));
                }

                if(whenValue.length() > 0){
                    rd.setAffairWhen(whenValue.toString());
                }else {
                    rd.setAffairWhen(NptCWModelService.PROPERTY_UNKNOW);
                }
                if(null != where){
                    rd.setAffairWhere(where.getValue());
                }else {
                    rd.setAffairWhere(NptCWModelService.PROPERTY_UNKNOW);
                }
                if(null != what){
                    rd.setAffairWhat(what.getValue());
                }else {
                    rd.setAffairWhat(NptCWModelService.PROPERTY_UNKNOW);
                }

                if(!StringUtils.isEmpty(fv.getuFieldValue())){
                    rd.setuFieldValue(fv.getuFieldValue());
                }

                resultDetails.add(rd);
            }
        }
        return resultDetails;
    }

    /**
     *  author  : owen
     *  date    : 2017/7/14 下午2:28
     *  params  :
     *              []:
     *              []:
     *  note    :
     *          初始化总结果
     */
    private NptCWResult initCWResult(String batchNo,
                                     NptBaseModelExMetaData metaData,
                                     NptCreditWarningEntityData entityData){

        if(null != entityData && entityData.getDataLoaded()
                && null != metaData && metaData.getCompleted()){

            NptCWResult result = new NptCWResult();

            result.setBatchNo(batchNo);
            result.setCreditEntityId(entityData.getCreditEntityId());
            result.setBaseModelId(metaData.getModelEx().getBaseModel().getId());
            result.setCreditEntityTitle(entityData.getCreditEntityTitle());
            result.setCreditEntityType(entityData.getCreditEntityType());
            result.setModelMainPoolId(metaData.getMainPool().getId());
            result.setCreditEntityUFieldValue(entityData.getCreditEntityBasicInfo().getuFieldValue());
            result.setRiskScore(NptCommonUtil.IntegerZero());
            result.setRiskLevel(NptCesUtil.getRiskLevelByScore(NptCommonUtil.IntegerZero()));

            return result;
        }
        return null;
    }


    /**
     *  author  : owen
     *  date    : 2017/7/14 下午2:28
     *  params  :
     *              []:
     *              []:
     *  note    :
     *          初始化维度结果
     */
    private List<NptCWDmsResult> initCWDmsResult(String batchNo,
                                                 NptBaseModelExMetaData metaData,
                                                 NptCreditWarningEntityData entityData){

        List<NptCWDmsResult> result = new ArrayList<>();

        if(null != entityData && entityData.getDataLoaded()
                && null != metaData && metaData.getCompleted()){

            Collection<NptBaseModelGroupEx> groupExes = metaData.getGroupExes();
            if(null != groupExes && !groupExes.isEmpty()){
                for(NptBaseModelGroupEx ge:groupExes){
                    NptCWDmsResult dmsResult = new NptCWDmsResult();
                    dmsResult.setBatchNo(batchNo);
                    dmsResult.setBaseModelGroupId(ge.getModelGroup().getId());
                    dmsResult.setCreditEntityType(entityData.getCreditEntityType());
                    dmsResult.setDimensionName(ge.getModelGroup().getGroupName());
                    dmsResult.setRiskScore(NptCommonUtil.IntegerZero());
                    dmsResult.setRiskLevel(NptCesUtil.getRiskLevelByScore(NptCommonUtil.IntegerZero()));

                    result.add(dmsResult);
                }
            }

        }
        return result;
    }



    /**
     *  author  : owen
     *  date    : 2017/7/14 下午2:27
     *  params  :
     *              []:
     *              []:
     *  note    :
     *          加载信用实体实体数据
     */
    @Override
    public NptCreditWarningEntityData loadCWEntityData(NptBaseModelExMetaData metaData, String ceId) {

        NptCreditWarningEntityData entityData = new NptCreditWarningEntityData();

        if(null != metaData && metaData.getCompleted() && !StringUtils.isEmpty(ceId)){
            entityData.setCreditEntityId(ceId);
            entityData.setModelId(metaData.getModelEx().getBaseModel().getId());
            entityData.setCreditEntityType(metaData.getModelEx().getBaseModel().getHostId());


            Collection<NptWebFieldDataArray> basicInfo = baseModelService.getBaseModelGrouPoolDataList(metaData.getMainPool(),null,ceId,true);
            if(null == basicInfo || basicInfo.isEmpty()){
                entityData.setDataLoadedNote("通过业务主键取不到信用实体的基础信息！");
                entityData.setDataLoaded(false);
            }else {
                NptWebFieldDataArray basicRow = basicInfo.iterator().next();
                entityData.setCreditEntityBasicInfo(basicRow);
                Collection<NptWebFieldDataArray.NptWebFieldData> rowFV = basicRow.getDataArray();
                if(null != rowFV && !rowFV.isEmpty()){
                    entityData.setCreditEntityTitle(rowFV.iterator().next().getValue());
                }


                Map<Long,List<NptBaseModelPoolEx>> poolexMap = metaData.getPoolExMap();

                if(null != poolexMap && !poolexMap.isEmpty()){
                    Set<Long> ids = poolexMap.keySet();
                    for(Long id:ids){
                        List<NptBaseModelPoolEx> poolExes = poolexMap.get(id);
                        if(null != poolExes && !poolExes.isEmpty()){
                            for(NptBaseModelPoolEx pe:poolExes){

                                NptBaseModelPool pool = pe.getModelPool();
                                NptCWModelSubDmsProps sdp = (NptCWModelSubDmsProps) pe.getPoolProperties();

                                List<Long> searchField = new ArrayList<>();

                                if(null != sdp.getuFieldId()){
                                    searchField.add(sdp.getuFieldId());
                                }
                                searchField.addAll(sdp.spliteFieldsId(sdp.getWhenFieldId()));

                                if(null != sdp.getWhereFieldId()){
                                    searchField.add(sdp.getWhereFieldId());
                                }
                                if(null != sdp.getWhatFieldId()){
                                    searchField.add(sdp.getWhatFieldId());
                                }

                                searchField.addAll(sdp.spliteFieldsId(sdp.getAmountFieldId()));


                                Collection<NptWebFieldDataArray> poolInfo = baseModelService.getBaseModelGrouPoolDataList(pool,searchField,ceId,true);
                                if(null != poolInfo && !poolInfo.isEmpty()){
                                    entityData.getEntityDataMap().put(pe.getModelPool().getId(),poolInfo);
                                }
                            }
                        }
                    }
                }

                entityData.setDataLoaded(true);
                entityData.setDataLoadedNote("已成功通过业务主键取到了信用实体的基本信息，[" + entityData.getEntityDataMap().size() + "]个子维度也已取得相关信息");
            }
        }
        return entityData;
    }


    /**
     * author  : owen
     * date    : 2017/7/14 下午4:19
     * params  :
     * []:
     * []:
     * note    :
     * 为数据池的数值字段准备功效计算
     *
     * @param pool
     */
    @Override
    public NptDict prepareForEfficacyComputePerPool(NptBaseModelPool pool,Boolean forceUpdate) {
        if(null != pool && pool.checkIsEnable()){
            NptCWModelSubDmsProps sdps = getCreditWarningModelSubDimensionProperties(pool);
            if(null == sdps){
                return NptDict.RST_EXCEPTION("当前数据池暂未配置预警属性");
            }

            if(StringUtils.isEmpty(sdps.getAmountGear()) || forceUpdate) {
                List<Long> searchFieldIds = new ArrayList<>();
                searchFieldIds.addAll(sdps.spliteFieldsId(sdps.getAmountFieldId()));

                if(!searchFieldIds.isEmpty()) {
                    Collection<NptWebFieldDataArray> amountValues = baseModelService.getBaseModelGrouPoolDataList(pool, searchFieldIds, null, true);
                    if (null != amountValues && !amountValues.isEmpty()) {

                        List<Double> amounts = new ArrayList<>();
                        double maxValue = 0d, minValue = 0f;
                        for (NptWebFieldDataArray da : amountValues) {

                            Collection<NptWebFieldDataArray.NptWebFieldData> rows = da.getDataArray();
                            if (null != rows) {
                                Double allFieldAmount = 0d;
                                for (NptWebFieldDataArray.NptWebFieldData fd : rows) {
                                    allFieldAmount += NptCommonUtil.sumAllDoubleNumberFromString(fd.getValue());
                                }
                                Double unitAmount = 0d;
                                if (allFieldAmount > 0d) {
                                    if (null != sdps.getAmountMU()) {
                                        unitAmount = allFieldAmount * sdps.getAmountMU();
                                    }
                                    amounts.add(NptCommonUtil.formatDoubleValue(allFieldAmount));
                                    if (unitAmount > maxValue) {
                                        maxValue = unitAmount;
                                    }
                                }
                            }
                        }


                        double[] amountArray = new double[amounts.size()];
                        int i = 0;
                        for (Double dv : amounts) {
                            amountArray[i] = NptCommonUtil.formatDoubleValue(dv);
                            i++;
                        }

                        Arrays.sort(amountArray);
                        Percentile percentile = new NptExcelPercentile();
                        percentile.setData(amountArray);

                        double yz2 = percentile.evaluate(20);
                        double yz4 = percentile.evaluate(40);
                        double yz6 = percentile.evaluate(60);
                        double yz8 = percentile.evaluate(80);


                        List<Double> sec1 = new ArrayList<>();
                        List<Double> sec2 = new ArrayList<>();
                        List<Double> sec3 = new ArrayList<>();
                        List<Double> sec4 = new ArrayList<>();
                        List<Double> sec5 = new ArrayList<>();

                        double sec1Sum = 0d;
                        double sec2Sum = 0d;
                        double sec3Sum = 0d;
                        double sec4Sum = 0d;
                        double sec5Sum = 0d;

                        for (double dv : amounts) {
                            if (minValue <= dv && dv < yz2) {
                                sec1.add(dv);
                                sec1Sum += dv;
                            } else if (yz2 <= dv && dv < yz4) {
                                sec2.add(dv);
                                sec2Sum += dv;
                            } else if (yz4 <= dv && dv < yz6) {
                                sec3.add(dv);
                                sec3Sum += dv;
                            } else if (yz6 <= dv && dv < yz8) {
                                sec4.add(dv);
                                sec4Sum += dv;
                            } else if (yz8 <= dv && dv <= maxValue) {
                                sec5.add(dv);
                                sec5Sum += dv;
                            }
                        }

                        Double averageSec1 = NptCommonUtil.formatDoubleValue(sec1.size() > 0 ? sec1Sum / sec1.size() : minValue);
                        Double averageSec2 = NptCommonUtil.formatDoubleValue(sec2.size() > 0 ? sec2Sum / sec2.size() : averageSec1);
                        Double averageSec3 = NptCommonUtil.formatDoubleValue(sec3.size() > 0 ? sec3Sum / sec3.size() : averageSec2);
                        Double averageSec4 = NptCommonUtil.formatDoubleValue(sec4.size() > 0 ? sec4Sum / sec4.size() : averageSec3);
                        Double averageSec5 = NptCommonUtil.formatDoubleValue(sec5.size() > 0 ? sec5Sum / sec5.size() : averageSec4);


                        //组合标准值和阈值，格式为   标准值#阈值
                        //第一档的标准值为最小值，第六档的标准值为最大值
                        //第2-5档的标准值为上一档的平均值
                        StringBuffer sb = new StringBuffer();
                        sb.append(NptCommonUtil.formatDoubleValue(minValue))
                                .append("#")
                                .append(NptCommonUtil.formatDoubleValue(yz2))
                                .append(",");

                        sb.append(averageSec1)
                                .append("#")
                                .append(NptCommonUtil.formatDoubleValue(yz4))
                                .append(",");

                        sb.append(averageSec2)
                                .append("#").
                                append(NptCommonUtil.formatDoubleValue(yz6))
                                .append(",");

                        sb.append(averageSec3)
                                .append("#")
                                .append(NptCommonUtil.formatDoubleValue(yz8))
                                .append(",");

                        sb.append(averageSec4)
                                .append("#")
                                .append(NptCommonUtil.formatDoubleValue(maxValue));


                        sdps.setAmountGear(sb.toString());

                        modelSubDmsPropsManager.updateSubDmsProps(sdps);

                        return NptDict.RST_SUCCESS;
                    } else {
                        return NptDict.RST_SUCCESS("当前数据池未检测到任何数值信息，停止分析");
                    }
                }else {
                    return NptDict.RST_SUCCESS;
                }
            }else {
                return NptDict.RST_SUCCESS;
            }
        }else {
            return NptDict.RST_EXCEPTION("数据池不存在或已被禁用！");
        }
    }

    /**
     * author  : owen
     * date    : 2017/7/16 下午2:52
     * params  :
     * []:
     * []:
     * note    :
     * 加载模型的所有数据池
     *
     * @param modelId
     */
    @Override
    public Collection<NptBaseModelPool> listModelPools(Long modelId) {
        NptBaseModel baseModel = baseModelService.fastFindBaseModelById(modelId);
        if(null != baseModel) {
            return baseModelService.getBaseModelPools(baseModel,null,null);
        }else {
            return new ArrayList<>();
        }
    }


    /**
     * author  : owen
     * date    : 2017/7/16 下午3:52
     * params  :
     * []:
     * []:
     * note    :
     * 对模型的实体数据进行预处理
     *
     * @param modelId
     * @param forceUpdate
     */
    @Override
    public NptDict preTreatEntityData(Long modelId, Boolean forceUpdate) {
        NptBaseModel baseModel = baseModelService.fastFindBaseModelById(modelId);

        if(null != baseModel && baseModel.checkIsEnable()){
            Collection<NptBaseModelPool> pools = baseModelService.getBaseModelPools(baseModel,null,null);
            if(null != pools && !pools.isEmpty()){
                for(NptBaseModelPool p:pools){
                    this.prepareForEfficacyComputePerPool(p,forceUpdate);
                }
            }

            return NptDict.RST_SUCCESS;
        }else {
            return NptDict.RST_EXCEPTION("模型不存在或已被禁用");
        }
    }


    @Override
    public NptCWModelPropsManager getCWModelPropsManager() {
        return this.modelPropsManager;
    }

    @Override
    public NptCWModelDimensionPropsManager getCWModelDimensionPropsManager() {
        return this.modelDimensionPropsManager;
    }

    @Override
    public NptCWModelSubDmsPropsManager getCWModelSubDimensionPropsManager() {
        return this.modelSubDmsPropsManager;
    }

    @Override
    public NptGRSBaseModelService getGRSBaseModelService() {
        return baseModelService;
    }

    @Override
    public NptWebFieldDataArray getDataTypeRowDataByUKValue(Long dataTypeId, String ukValue) {
        return baseModelService.getDataTypeRowDataByUKValue(dataTypeId, ukValue);
    }

    @Override
    public NptBaseModel getCreditWarningBaseModel(NptDict type) {
        return baseModelService.getBaseModelHostNativeModel(type);
    }

}
