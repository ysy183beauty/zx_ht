package com.npt.grs.appeal.manager.impl;

import com.alibaba.fastjson.JSON;
import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.database.manager.NptDatabaseManager;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.sync.entity.CreditAppealInfo;
import com.npt.bridge.sync.entity.NptUserAppeal;
import com.npt.bridge.sync.entity.NptUserAppealDetail;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.grs.appeal.dao.NptAppealInfoDao;
import com.npt.grs.appeal.entity.NptCreditServiceInfo;
import com.npt.grs.appeal.manager.NptAppealDetailManager;
import com.npt.grs.appeal.manager.NptAppealInfoManager;
import com.npt.grs.appeal.manager.NptCreditServiceInfoManager;
import com.npt.grs.apply.manager.NptBusinessFlowLogManager;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.rms.auth.entity.NptSimpleUser;
import com.npt.rms.auth.service.NptRmsAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.manager.BaseManagerImpl;
import org.summer.extend.orm.Pagination;
import org.summer.extend.orm.condition.Conditions;

import javax.annotation.Resource;
import java.util.*;

/**
 * 项目: NPTGRS
 * 作者: 张磊
 * 日期: 16/11/2 下午3:56
 * 备注:
 */
@Service
@Transactional
public class NptAppealInfoManagerImpl extends BaseManagerImpl<NptUserAppeal> implements NptAppealInfoManager {
    @Autowired
    private NptAppealInfoDao appealDao;

    @Autowired
    private NptAppealDetailManager detailManager;
    @Autowired
    private NptCreditServiceInfoManager creditServiceInfoManager;

    @Resource(name = "nptDssOracleManager")
    protected NptDatabaseManager databaseManager;

    @Resource(name = "rmsArchService")
    private NptRmsArchService rmsArchService;

    @Resource(name = "rmsAuthService")
    private NptRmsAuthService rmsAuthService;

    @Autowired
    private NptBusinessFlowLogManager applyLogManager;

    @Override
    public Pagination<NptUserAppeal> getAppealByCondition(Integer role, Long approvalOrgId, String dataTypeTitle,
                                                          NptDict dict, int beginIndex, int pageSize) {
        Pagination<NptUserAppeal> appealPagination = this.findAll(
                beginIndex,
                pageSize,
                null == approvalOrgId ? Conditions.isNotNull(NptBaseEntity.PROPERTY_ID) : Conditions.eq(NptUserAppeal.PROPERTY_APPEAL_PROVIDER, approvalOrgId),
                null == dataTypeTitle ? Conditions.isNotNull(NptBaseEntity.PROPERTY_ID) : Conditions.like(NptUserAppeal.PROPERTY_APPEAL_DT_TITLE, dataTypeTitle),
                null == dict ? Conditions.isNotNull(NptBaseEntity.PROPERTY_ID) : Conditions.eq(NptUserAppeal.PROPERTY_APPEAL_STATUS, dict.getCode()),
                null == role ? Conditions.isNotNull(NptBaseEntity.PROPERTY_ID) : Conditions.ne(NptUserAppeal.PROPERTY_APPEAL_STATUS, role),
                Conditions.desc(NptBaseEntity.PROPERTY_CREATE_TIME)
        );

        if (null != appealPagination) {
            transformStatus(appealPagination.getResults());
        }

        return appealPagination;
    }

    @Override
    public NptUserAppeal getAppealByNo(String appealNo) {
        NptUserAppeal appeal = appealDao.findUniqueByCondition(
                Conditions.eq(NptUserAppeal.PROPERTY_APPEAL_NO, appealNo));

        if (null != appeal) {
            ArrayList<NptUserAppeal> list = new ArrayList<>();
            list.add(appeal);
            transformStatus(list);
        }
        return appeal;
    }



    @Override
    public NptDict handleAppealByCDC(Long userId, String appealNo, Integer appealStatus, Date[] frozenDate, String appealResult) {
        NptUserAppeal appeal = this.getAppealByNo(appealNo);

        if (null == appeal){
            return NptDict.RST_INVALID_PARAMS;
        }

        NptSimpleUser user = rmsAuthService.getUserById(userId);
        if (null != user){
            String nextStatusTitle ;
            if (appealStatus == 1){
                nextStatusTitle = NptDict.RAS_PROCESSING.getTitle();
                appeal.setStatus(NptDict.IDS_LOCKED.getCode());
                appeal.setAppealStatus(appealStatus);
                appeal.setFrozenStartDate(frozenDate[0]);
                appeal.setFrozenEndDate(frozenDate[1]);
            } else {
                nextStatusTitle = NptDict.RAS_REFUSED.getTitle();
                appeal.setAppealStatus(NptDict.RAS_REFUSED.getCode());
                appeal.setAppealResult(appealResult);
            }

            appeal.setStepUserId(userId);
            this.update(appeal);
            applyLogManager.addAppealLog(appeal,nextStatusTitle);
        }

        Map<String,String> changeInfo = new HashMap<>();
        changeInfo.put("NCF_STATUS",String.valueOf(NptDict.IDS_LOCKED.getCode()));

        NptLogicDataType dataType = rmsArchService.findDataTypeById(appeal.getAppealDTID());
        NptLogicDataField pkField = rmsArchService.findDataFieldById(appeal.getAppealPKID());
        Map<String,String> condition = new HashMap<>();
        condition.put(pkField.getFieldDbName(),appeal.getAppealBusinessKey());

        Collection<NptLogicDataField> fields = databaseManager.getReservedDataField();
        databaseManager.updateData(dataType.getTypeDbName(), fields, changeInfo, condition);


        // 同步结果到信用服务
        if (appealStatus != 1 && appeal.getSource() == NptDict.SOURCE_CMS.getCode()) {
            syncToCreditService(appealNo);
        }

        return NptDict.RST_SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/28 下午02:24
     * 备注: 同步结果到信用服务
     */
    private void syncToCreditService(String appealNo) {
        NptCreditServiceInfo creditServiceInfo = creditServiceInfoManager.findUniqueByProperty(NptCreditServiceInfo.PROPERTY_APPEAL_NO, appealNo);
        CreditAppealInfo appealInfo = new CreditAppealInfo();
        appealInfo.setUserAppeal(getAppealByNo(appealNo));
        appealInfo.setAppealDetails(new ArrayList<>(detailManager.getAppealDetailByNo(appealNo)));
        creditServiceInfo.setResponse(JSON.toJSONString(appealInfo));
        creditServiceInfo.setResponseTime(NptCommonUtil.getCurrentSysTimestamp());
        creditServiceInfo.setSyncStatus(NptDict.RCS_NEEDSYNC.getCode());
        creditServiceInfoManager.update(creditServiceInfo);
    }

    @Override
    public NptDict handleAppealByPRD(Long userId, String appealNo, Date frozenDate, List<Object> fields, List<Object> unPassFields) {
        NptUserAppeal appeal = this.getAppealByNo(appealNo);
        if (null == appeal){
            return NptDict.RST_INVALID_PARAMS;
        }

        NptSimpleUser user = rmsAuthService.getUserById(userId);
        if (null != user){
            int nextStatus = NptDict.RAS_ACCEPTED.getCode();
            String nextStatusTitle = NptDict.RAS_ACCEPTED.getTitle();
            appeal.setStepUserId(userId);
            appeal.setAppealStatus(nextStatus);
            appeal.setFrozenEndDate(frozenDate);
            this.update(appeal);

            applyLogManager.addAppealLog(appeal,nextStatusTitle);
        }
        if (unPassFields.size() != 0){
            for (Object object : unPassFields){
                Map<String,String> unPassField = (Map<String, String>) object;
                if (null != unPassField){
                    Collection<NptUserAppealDetail> appealDetails = detailManager.getAppealDetailByNo(appealNo);
                    for (NptUserAppealDetail detail : appealDetails){
                        if (detail.getFieldId().toString().equals(unPassField.get("id"))){
                            detail.setDetailResult(unPassField.get("result"));
                            detailManager.update(detail);
                        }

                    }
                }
            }
        }
        if (fields.size() != 0){

            Map<String,String> changeInfo = new HashMap<>();

            for (Object object : fields){
                Map<String,String> field = (Map<String, String>) object;
                if (null != field){
                    Collection<NptUserAppealDetail> appealDetails = detailManager.getAppealDetailByNo(appealNo);
                    for (NptUserAppealDetail detail : appealDetails){
                        if (detail.getFieldId().toString().equals(field.get("id"))){
                            detail.setStatus(NptDict.IDS_ENABLED.getCode());
                            detail.setDetailResult(field.get("result"));
                            detailManager.update(detail);
                        }

                    }
                }
            }
        }

        // 同步结果到信用服务
        if (appeal.getSource() == NptDict.SOURCE_CMS.getCode()) {
            syncToCreditService(appealNo);
        }

        return NptDict.RST_SUCCESS;
    }

    /**
     * 作者: xuqinyuan
     * 时间: 2016/11/24 18:14
     * 备注:  转换状态值
     */
    private void transformStatus(Collection<NptUserAppeal> list) {
        if (null != list && !list.isEmpty()) {
            for (NptUserAppeal appeal : list) {
                Integer status = appeal.getAppealStatus();
                if (status.equals(NptDict.RAS_EXPIRED.getCode())) {
                    appeal.setExpiredTitle("已过期");
                } else {
                    appeal.setExpiredTitle("未过期");
                }

                NptDict dict = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.RAS, status);
                if (null != dict) {
                    appeal.setAppealStatusTitle(dict.getTitle());
                }

                NptLogicDataProvider org = rmsArchService.fastFindOrgById(appeal.getAppealProviderId());
                if (null != org) {
                    appeal.setAppealProviderTitle(org.getOrgName());
                }
            }
        }
    }
}
