package com.npt.ces.cw.service.impl;

import com.npt.bridge.dataBinder.NptWebFieldDataArray;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.ces.cw.bean.NptCWResultDetailBox;
import com.npt.ces.cw.bean.NptCWRiskTendency;
import com.npt.ces.cw.entity.*;
import com.npt.ces.cw.manager.*;
import com.npt.ces.cw.service.NptCWModelService;
import com.npt.ces.cw.service.NptCWResultReader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.orm.Condition;
import org.summer.extend.orm.Pagination;
import org.summer.extend.orm.SimplePagination;
import org.summer.extend.orm.condition.Conditions;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/7/10
 * 备注:
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NptCWResultReaderImpl implements NptCWResultReader {

    @Autowired
    private NptCWModelService cwModelService;
    @Autowired
    private NptCWDmsResultManager cwDmsResultManager;
    @Autowired
    private NptCWProviderConcernManager cwProviderConcernManager;
    @Autowired
    private NptCWResultManager cwResultManager;
    @Autowired
    private NptCWSubDmsResultDetailManager cwSubDmsResultDetailManager;
    @Autowired
    private NptCWSubDmsResultManager cwSubDmsResultManager;
    @Autowired
    private NptCWRiskIndexManager riskIndexManager;
    @Autowired
    private NptCWModelPropsManager cwModelPropsManager;


    private String getSearchBatchNo(NptDict ceType){
        NptBaseModel baseModel = cwModelService.getCreditWarningBaseModel(ceType);
        NptCWModelProps modelProps = cwModelService.getCreditWarningModelProperties(baseModel);

        String searchBatch = StringUtils.EMPTY;
        if(null != modelProps){
            searchBatch = modelProps.getLastBatch();
        }
        if(StringUtils.isEmpty(searchBatch)){
            searchBatch = modelProps.getCurrentBatch();
        }

        return searchBatch;
    }


    @Override
    public Pagination<NptCWResult> listTopResults(NptDict ceType, Integer currentPage,Integer pageSize) {

        String searchBatch = getSearchBatchNo(ceType);
        if(!StringUtils.isEmpty(searchBatch)){
            if(null == pageSize || pageSize < 1){
                pageSize = NptCWResultReader.DEFAULT_TOP_NUM;
            }

            if(null == currentPage || currentPage < 1){
                currentPage = 1;
            }

            Pagination<NptCWResult> pagination = cwResultManager.findAll(
                    (currentPage - 1) * pageSize,
                    pageSize,
                    Conditions.eq(NptCWResult.PROPERTY_CREDIT_ENTITY_TYPE,ceType.getCode()),
                    Conditions.eq(NptCWResult.PROPERTY_BATCH_NO,searchBatch),
                    Conditions.desc(NptCWResult.PROPERTY_RISK_SCORE));

            return pagination;
        }
        return SimplePagination.emptySplitPageResult();
    }

    @Override
    public Pagination<NptCWResult> listTopConcernResults(NptDict ceType,Long orgId, Integer currPage, Integer pageSize) {

        String searchBatch = getSearchBatchNo(ceType);
        if(!StringUtils.isEmpty(searchBatch)) {

            Collection<Condition> conditions = new ArrayList<>();

            List<NptCWProviderConcern> concerns = cwProviderConcernManager.findByCondition(Conditions.eq(NptCWProviderConcern.PROPERTY_PROVIDER_ID, orgId), Conditions.eq(NptCWProviderConcern.PROPERTY_CREDIT_ENTITY_TYPE, ceType.getCode()));
            if (concerns.size() == 0) {
                return SimplePagination.emptySplitPageResult();
            }
            List<String> collect = concerns.stream().map(NptCWProviderConcern::getCreditEntityId).collect(Collectors.toList());
            conditions.add(Conditions.in(NptCWResult.PROPERTY_CREDIT_ENTITY_ID, collect));
            conditions.add(Conditions.eq(NptCWResult.PROPERTY_BATCH_NO,searchBatch));
            conditions.add(Conditions.desc(NptCWResult.PROPERTY_RISK_SCORE));

            if (null == pageSize || pageSize < 1) {
                pageSize = NptCWResultReader.DEFAULT_TOP_NUM;
            }

            if (null == currPage || currPage < 1) {
                currPage = 1;
            }

            Pagination<NptCWResult> pagination = cwResultManager.findAll((currPage - 1) * pageSize, pageSize, conditions.toArray(new Condition[]{}));
            return pagination;
        }
        return SimplePagination.emptySplitPageResult();
    }

    @Override
    public Map<String, Collection<NptCWDmsResult>> listTopDmsResults(Long dmsId,NptDict ceType, Integer num){
        Map<String, Collection<NptCWDmsResult>> result = new HashMap<>();


        if(null == ceType){
            ceType = NptDict.BMH_WARN_BS;
        }
        String searchBatch = getSearchBatchNo(ceType);
        if(!StringUtils.isEmpty(searchBatch)) {
            final Integer finalNum = null == num ? NptCWResultReader.DEFAULT_TOP_NUM : num;

            if (null != dmsId) {
                Pagination<NptCWDmsResult> all = cwDmsResultManager.findAll(
                        0,
                        finalNum,
                        Conditions.eq(NptCWDmsResult.PROPERTY_BASE_MODEL_GROUP_ID, dmsId),
                        Conditions.eq(NptCWDmsResult.PROPERTY_BATCH_NO,searchBatch),
                        Conditions.desc(NptCWResult.PROPERTY_RISK_SCORE));

                if (null != all.getResults() && !all.getResults().isEmpty()) {
                    result.put(all.getResults().iterator().next().getDimensionName(), all.getResults());
                }
            } else {

                StringBuffer sb = new StringBuffer("select distinct ");
                sb.append(NptCWDmsResult.PROPERTY_BASE_MODEL_GROUP_ID);
                sb.append(" from ");
                sb.append(NptCWDmsResult.class.getSimpleName());
                sb.append(" where ").append(NptCWDmsResult.PROPERTY_CREDIT_ENTITY_TYPE).append(" = ").append(ceType.getCode());

                Collection<?> byHql = cwDmsResultManager.findByHql(sb.toString());
                if (null != byHql && !byHql.isEmpty()) {

                    byHql.forEach(gid -> {
                        Pagination<NptCWDmsResult> all = cwDmsResultManager.findAll(
                                0,
                                finalNum,
                                Conditions.eq(NptCWDmsResult.PROPERTY_BASE_MODEL_GROUP_ID, Long.parseLong(String.valueOf(gid))),
                                Conditions.eq(NptCWDmsResult.PROPERTY_BATCH_NO,searchBatch),
                                Conditions.desc(NptCWResult.PROPERTY_RISK_SCORE));

                        if (null != all.getResults() && !all.getResults().isEmpty()) {
                            result.put(all.getResults().iterator().next().getDimensionName(), all.getResults());
                        }
                    });
                }
            }
        }

        return result;
    }

    @Override
    public Collection<NptCWDmsResult> listDimensions(Integer ceTypeCode) {
        StringBuffer sb = new StringBuffer("select distinct ");
        sb.append(NptCWDmsResult.PROPERTY_BASE_MODEL_GROUP_ID);
        sb.append(",");
        sb.append(NptCWDmsResult.PROPERTY_DIMENSION_NAME);
        sb.append(" from ");
        sb.append(NptCWDmsResult.class.getSimpleName());
        sb.append(" where 1=1 ");

        if (ceTypeCode != null) {
            sb.append(" and ").append(NptCWDmsResult.PROPERTY_CREDIT_ENTITY_TYPE).append(" = ").append(ceTypeCode);
        }

        Collection<?> byHql = cwDmsResultManager.findByHql(sb.toString());

        Collection<NptCWDmsResult> results = new ArrayList<>();
        byHql.forEach(objs -> {
            NptCWDmsResult dmsResult = new NptCWDmsResult();
            dmsResult.setBaseModelGroupId(Long.valueOf(String.valueOf(((Object[]) objs)[0])));
            dmsResult.setDimensionName(String.valueOf(((Object[]) objs)[1]));
            results.add(dmsResult);
        });
        return results;
    }

    @Override
    public Pagination<NptCWDmsResult> listTopDmsResults(Long dmsId, NptDict ceType, Integer currPage, Integer pageSize) {

        String searchBatch = getSearchBatchNo(ceType);
        if(!StringUtils.isEmpty(searchBatch)){
            Collection<Condition> conditions = new ArrayList<>();
            if (dmsId != null) {
                conditions.add(Conditions.eq(NptCWDmsResult.PROPERTY_BASE_MODEL_GROUP_ID, dmsId));
            }
            if (ceType != null) {
                conditions.add(Conditions.eq(NptCWDmsResult.PROPERTY_CREDIT_ENTITY_TYPE, ceType.getCode()));
            }
            conditions.add(Conditions.eq(NptCWDmsResult.PROPERTY_BATCH_NO,searchBatch));
            conditions.add(Conditions.desc(NptCWDmsResult.PROPERTY_RISK_SCORE));

            return cwDmsResultManager.findAll((currPage - 1) * pageSize, pageSize, conditions.toArray(new Condition[]{}));
        }
        return SimplePagination.emptySplitPageResult();

    }

    @Override
    public Map<String,Collection<NptCWSubDmsResult>> listTopSubDmsResults(Long sdmsId, NptDict ceType, Integer num) {
        Map<String, Collection<NptCWSubDmsResult>> result = new HashMap<>();

        if(null == ceType){
            ceType = NptDict.BMH_WARN_BS;
        }

        String searchBatch = getSearchBatchNo(ceType);
        if(!StringUtils.isEmpty(searchBatch)){
            final Integer finalNum = null == num?NptCWResultReader.DEFAULT_TOP_NUM:num;

            if(null != sdmsId){
                Pagination<NptCWSubDmsResult> all = cwSubDmsResultManager.findAll(
                        0,
                        finalNum,
                        Conditions.eq(NptCWSubDmsResult.PROPERTY_DIMENSION_RST_ID, sdmsId),
                        Conditions.eq(NptCWSubDmsResult.PROPERTY_BATCH_NO,searchBatch),
                        Conditions.desc(NptCWResult.PROPERTY_RISK_SCORE));

                if(null != all.getResults() && !all.getResults().isEmpty()){
                    result.put(all.getResults().iterator().next().getSubDimensionTitle(), all.getResults());
                }
            }else {

                StringBuffer sb = new StringBuffer("select distinct ");
                sb.append(NptCWSubDmsResult.PROPERTY_DIMENSION_RST_ID);
                sb.append(" from ");
                sb.append(NptCWSubDmsResult.class.getSimpleName());
                sb.append(" where ").append(NptCWSubDmsResult.PROPERTY_CREDIT_ENTITY_TYPE).append(" = ").append(ceType.getCode());

                Collection<?> byHql = cwSubDmsResultManager.findByHql(sb.toString());
                if (null != byHql && !byHql.isEmpty()) {

                    byHql.forEach(gid -> {
                        Pagination<NptCWSubDmsResult> all = cwSubDmsResultManager.findAll(
                                0,
                                finalNum,
                                Conditions.eq(NptCWSubDmsResult.PROPERTY_DIMENSION_RST_ID, Long.parseLong(String.valueOf(gid))),
                                Conditions.eq(NptCWSubDmsResult.PROPERTY_BATCH_NO,searchBatch),
                                Conditions.desc(NptCWResult.PROPERTY_RISK_SCORE));

                        if(null != all.getResults() && !all.getResults().isEmpty()){
                            result.put(all.getResults().iterator().next().getSubDimensionTitle(), all.getResults());
                        }
                    });
                }
            }
        }
        return result;
    }

    /**
     * author  : owen
     * date    : 2017/7/17 下午9:01
     * params  :
     * []:
     * []:
     * note    :
     * 加载最后指定数目的风险指数
     *
     * @param num
     */
    @Override
    public List<NptCWRiskIndex> listLastRiskIndexs(Integer num) {
        return riskIndexManager.listLastRiskIndex(num);
    }

    @Override
    public NptCWResultDetailBox getCreditEntityResultBox(String ceId,NptDict ceType) {

        NptCWResultDetailBox detailBox = new NptCWResultDetailBox();

        if(!StringUtils.isEmpty(ceId) && null != ceType) {

            Pagination<NptCWResult> searchResult = cwResultManager.findAll(
                    0, NptCWResultReader.LAST_DEFAULT_NUM,
                    Conditions.eq(NptCWResult.PROPERTY_CREDIT_ENTITY_ID, ceId),
                    Conditions.eq(NptCWResult.PROPERTY_CREDIT_ENTITY_TYPE, ceType.getCode()),
                    Conditions.desc(NptCWResult.PROPERTY_BATCH_NO));

            if (null != searchResult && !searchResult.getResults().isEmpty()) {

                NptCWResult lastResult = searchResult.getResults().iterator().next();
                detailBox.setCwResult(lastResult);
                detailBox.setCreditEntityBasicInfo(getCreditEntityBasicInfo(lastResult));

                Collection<NptCWDmsResult> dmsResults = cwDmsResultManager.findByCondition(Conditions.eq(NptCWDmsResult.PROPERTY_RESULT_ID, lastResult.getId()));
                if (null != dmsResults && !dmsResults.isEmpty()) {
                    dmsResults.forEach(dmsr->{
                        NptCWModelDmsProps props = cwModelService.getCreditWarningModelDimensionPropertiesById(dmsr.getBaseModelGroupId());
                        if(null != props){
                            dmsr.setDmsNote(props.getNote());
                            dmsr.setDmsWeight(props.getDisCount());
                        }
                        detailBox.getDmsResults().add(dmsr);
                    });
                }

                Collection<NptCWSubDmsResult> sdrs = cwSubDmsResultManager.findByCondition(Conditions.eq(NptCWSubDmsResult.PROPERTY_RESULT_ID, lastResult.getId()));
                if (null != dmsResults && !sdrs.isEmpty()) {
                    List<NptCWSubDmsResult> subDmsResults = new ArrayList<>();
                    subDmsResults.addAll(sdrs);
                    detailBox.getSdmsResultMap().put(NptCommonUtil.getDefaultParentId(), subDmsResults);

                    subDmsResults.forEach(sdr ->
                    {
                        Collection<NptCWSubDmsResultDetail> sdrds = cwSubDmsResultDetailManager.findByCondition(
                                Conditions.eq(NptCWSubDmsResultDetail.PROPERTY_SUB_DIMENSION_RST_ID, sdr.getId()));
                        if (null != sdrds && !sdrds.isEmpty()) {
                            List<NptCWSubDmsResultDetail> subDmsResultDetails = new ArrayList<>();
                            subDmsResultDetails.addAll(sdrds);
                            detailBox.getSdmsRDetailMap().put(sdr.getId(), subDmsResultDetails);
                        }
                    });
                }
                detailBox.setReady(true);
            }
        }

        return detailBox;
    }

    @Override
    public List<NptCWRiskTendency> getCreditEntityTendency(String ceId, NptDict ceType) {
        List<NptCWRiskTendency> result = new ArrayList<>();

        if(!StringUtils.isEmpty(ceId) && null != ceType) {
            Pagination<NptCWResult> searchResult = cwResultManager.findAll(
                    0, NptCWResultReader.LAST_DEFAULT_NUM,
                    Conditions.eq(NptCWResult.PROPERTY_CREDIT_ENTITY_ID, ceId),
                    Conditions.eq(NptCWResult.PROPERTY_CREDIT_ENTITY_TYPE, ceType.getCode()),
                    Conditions.desc(NptCWResult.PROPERTY_BATCH_NO));

            if(null != searchResult && null != searchResult.getResults() && !searchResult.getResults().isEmpty()) {
                List<NptCWRiskTendency> anaylizeList = getCreditEntityRiskTendency(searchResult.getResults());
                if(null != anaylizeList && !anaylizeList.isEmpty()){
                    result.addAll(anaylizeList);
                }
            }
        }

        return result;
    }

    private NptWebFieldDataArray getCreditEntityBasicInfo(NptCWResult result){

        NptWebFieldDataArray da = new NptWebFieldDataArray();

        if(null != result){
            NptBaseModelPool mainPool = cwModelService.getGRSBaseModelService().fastFindBaseModelGrouPoolById(result.getModelMainPoolId());
            if(null != mainPool && mainPool.checkIsEnable()){
                Map<String,Object> row = cwModelService.getGRSBaseModelService().getBaseModelPoolIndexValueByUKValue(mainPool,result.getCreditEntityUFieldValue());
                if(null != row && !row.isEmpty()){
                    da.resetData(row,result.getCreditEntityUFieldValue());
                }
            }
        }

        return da;
    }


    /**
     * author  : owen
     * date    : 18/07/2017 2:13 PM
     * params  :
     * []:
     * []:
     * note    :
     * 获取信用体的风险趋势
     */
    public List<NptCWRiskTendency> getCreditEntityRiskTendency(Collection<NptCWResult> cwResults) {

        List<Long> crOrder = new ArrayList<>();
        List<String> batchNos = new ArrayList<>();
        List<NptCWRiskTendency> result = new ArrayList<>();
        Map<Long,Map<String,Integer>> groupByDmsScore = new HashMap<>();
        if(null != cwResults && !cwResults.isEmpty()){
            cwResults.forEach(cr -> {
                Collection<NptCWDmsResult> dmsResults = cwDmsResultManager.findByCondition(Conditions.eq(NptCWDmsResult.PROPERTY_RESULT_ID, cr.getId()));
                if(null != dmsResults && !dmsResults.isEmpty()){

                    Map<String,Integer> dsMap = new HashMap<>();
                    dmsResults.forEach(dr -> {
                        dsMap.put(dr.getDimensionName(),dr.getRiskScore());
                    });
                    groupByDmsScore.put(cr.getId(),dsMap);

                }
                crOrder.add(cr.getId());
                batchNos.add(cr.getBatchNo());
            });
        }

        Collections.reverse(crOrder);
        Collections.reverse(batchNos);

        for(int i = 0;i < crOrder.size();i++){
            Map<String,Integer> dsMap = groupByDmsScore.get(crOrder.get(i));
            NptCWRiskTendency tendency = new NptCWRiskTendency();
            tendency.setBatchNo(batchNos.get(i));
            tendency.setDsMap(dsMap);

            result.add(tendency);
        }

        return result;
    }

    @Override
    public void star(Long providerId, String creditEntityId, Integer creditEntityType) {
        NptCWProviderConcern concern = cwProviderConcernManager.findUniqueByCondition(Conditions.eq(NptCWProviderConcern.PROPERTY_PROVIDER_ID, providerId), Conditions.eq(NptCWProviderConcern.PROPERTY_CREDIT_ENTITY_ID, creditEntityId), Conditions.eq(NptCWProviderConcern.PROPERTY_CREDIT_ENTITY_TYPE, creditEntityType));
        if (concern == null) {
            concern = new NptCWProviderConcern();
            concern.setProviderId(providerId);
            concern.setCreditEntityId(creditEntityId);
            concern.setCreditEntityType(creditEntityType);
            cwProviderConcernManager.save(concern);
        }
    }

    @Override
    public void unStar(Long providerId, String creditEntityId, Integer creditEntityType) {
        NptCWProviderConcern concern = cwProviderConcernManager.findUniqueByCondition(Conditions.eq(NptCWProviderConcern.PROPERTY_PROVIDER_ID, providerId), Conditions.eq(NptCWProviderConcern.PROPERTY_CREDIT_ENTITY_ID, creditEntityId), Conditions.eq(NptCWProviderConcern.PROPERTY_CREDIT_ENTITY_TYPE, creditEntityType));
        if (concern != null) {
            cwProviderConcernManager.delete(concern);
        }
    }

    @Override
    public Pagination<NptCWResult> search(NptDict ceType,String keyword, Integer currPage, Integer pageSize) {

        String searchBatch = getSearchBatchNo(ceType);

        if(!StringUtils.isEmpty(searchBatch)) {

            Collection<Condition> conditions = new ArrayList<>();
            if (keyword != null) {
                conditions.add(
                        Conditions.or(
                                Conditions.like(NptCWResult.PROPERTY_CREDIT_ENTITY_ID, keyword),
                                Conditions.like(NptCWResult.PROPERTY_CREDIT_ENTITY_TITLE, keyword)
                        )
                );
            }
            conditions.add(Conditions.desc(NptCWResult.PROPERTY_RISK_SCORE));

            return cwResultManager.findAll((currPage - 1) * pageSize, pageSize, conditions.toArray(new Condition[]{}));
        }
        return SimplePagination.emptySplitPageResult();
    }

    @Override
    public Boolean checkStar(Long providerId, String creditEntityId) {
        Boolean star = false;
        NptCWProviderConcern concern = cwProviderConcernManager.findUniqueByCondition(Conditions.eq(NptCWProviderConcern.PROPERTY_PROVIDER_ID, providerId), Conditions.eq(NptCWProviderConcern.PROPERTY_CREDIT_ENTITY_ID, creditEntityId));
        if (concern != null) {
            star = true;
        }
        return star;
    }

    @Override
    public String getCreditEntityIdTitle(NptDict ceType) {
        String title = "";
        NptBaseModel baseModel = cwModelService.getCreditWarningBaseModel(ceType);
        if (baseModel != null) {
            NptCWModelProps cwModelProps = cwModelPropsManager.findUniqueByProperty(NptCWModelProps.PROPERTY_MODEL_ID, baseModel.getId());
            if (cwModelProps != null) {
                title = cwModelProps.getcIT();
            }
        }
        return title;
    }

    @Override
    public String getCreditEntityTitle(NptDict ceType) {
        String title = "";
        NptBaseModel baseModel = cwModelService.getCreditWarningBaseModel(ceType);
        if (baseModel != null) {
            NptCWModelProps cwModelProps = cwModelPropsManager.findUniqueByProperty(NptCWModelProps.PROPERTY_MODEL_ID, baseModel.getId());
            if (cwModelProps != null) {
                title = cwModelProps.getcTT();
            }
        }
        return title;
    }

    @Override
    public NptWebFieldDataArray loadPoolDetail(Long dataTypeId, Long ukValue) {
        NptWebFieldDataArray dataArray = cwModelService.getDataTypeRowDataByUKValue(dataTypeId, String.valueOf(ukValue));
        return  dataArray;
    }
}
