package com.npt.grs.scheduler.outside;

import com.alibaba.fastjson.JSONObject;
import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.database.manager.NptDatabaseManager;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.sync.entity.CreditApplyInfo;
import com.npt.bridge.sync.entity.CreditApplyInfoResponse;
import com.npt.bridge.sync.entity.CreditCmsUser;
import com.npt.bridge.sync.entity.NptSyncBase;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.grs.apply.manager.CreditApplyInfoManager;
import com.npt.grs.credit.manager.CreditCmsUserManager;
import com.npt.grs.model.service.NptGRSBaseModelService;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.rms.base.NptRmsCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.summer.extend.orm.Condition;
import org.summer.extend.orm.Pagination;
import org.summer.extend.orm.condition.Conditions;

import javax.annotation.Resource;
import java.util.*;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/3/25
 * 备注:
 */
@Controller("npt.grs.scheduler.outside.creditApply")
public class NptCreditApplyInfoAction extends NptSyncAction<CreditApplyInfo, Integer> {

    @Autowired
    protected NptGRSBaseModelService baseModelService;

    @Resource(name = "rmsArchService")
    private NptRmsArchService archService;

    @Resource(name = "nptDssOracleManager")
    protected NptDatabaseManager databaseManager;

    @Autowired
    private CreditCmsUserManager creditCmsUserManager;

    @Resource(name = "rmsCommonService")
    public void setCommonService(NptRmsCommonService commonService) {
        super.commonService = commonService;
    }

    @Autowired
    public void setManager(CreditApplyInfoManager manager) {
        super.manager = manager;
    }

    @Override
    protected Class<CreditApplyInfo> getEntityClass() {
        return CreditApplyInfo.class;
    }

    @Override
    protected NptDict getSyncDict() {
        return NptDict.RMT_SYNC_APPLY;
    }

    @Override
    protected NptDict getSyncOkDict() {
        return NptDict.RMT_SYNC_APPLY_OK;
    }

    @Override
    protected NptDict getSyncResponseDict() {
        return NptDict.RMT_SYNC_APPLY_REP;
    }

    @Override
    protected NptDict getSyncLogDict() {
        return NptDict.LGA_SYNC_APPLY;
    }

    @Override
    protected NptDict getSyncResponseLogDict() {
        return NptDict.LGA_SYNC_APPLY_REP;
    }

    @Override
    protected boolean saveInfo(CreditApplyInfo info) {
        // 如果实名认证通过，就将状态改为需要同步
        try {
            String flag = creditCmsUserManager.findById(info.getUserId()).getFlag();
            if (Integer.parseInt(flag) == NptDict.USER_AUTH_OK.getCode()) {
                info.setSyncStatus(NptDict.RCS_NEEDSYNC.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.saveInfo(info);
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/10 下午02:18
     * 备注:
     */
    @Override
    protected Object generateResponse(List<CreditApplyInfo> infos) {
        Map<Integer, List<String>> result = new HashMap<>();
        CreditCmsUser cmsUser;
        List<CreditApplyInfo> companyList = new ArrayList<>();
        List<CreditApplyInfo> personList = new ArrayList<>();
        for (CreditApplyInfo info : infos) {
            cmsUser = creditCmsUserManager.findById(info.getUserId());
            assert cmsUser != null;
            if (cmsUser.getType().equals(CreditCmsUser.TYPE_COMPANY)) {
                companyList.add(info);
            } else if (cmsUser.getType().equals(CreditCmsUser.TYPE_PERSON)) {
                personList.add(info);
            }

        }
        if (personList.size() > 0) {
            result.putAll(generateResponse(personList, NptDict.BMH_PERSONAL, NptDict.BMC_OUTSIDE));
        }
        if (companyList.size() > 0) {
            result.putAll(generateResponse(companyList, NptDict.BMH_BUSINESS, NptDict.BMC_OUTSIDE));
        }
        return result;
    }

    private Map<Integer, List<String>> generateResponse(List<CreditApplyInfo> infos, NptDict host, NptDict cate) {
        //定义返回结果, key为申请id， value为sql
        Map<Integer, List<String>> responsesList = new HashMap<>();
        //获取当前的模型信息
        NptBaseModel model = baseModelService.listModels(host, cate).iterator().next();

        Collection<NptBaseModelPool> pools = baseModelService.getBaseModelGrouPools(model,NptDict.IDS_ENABLED,false);
        if (pools == null) {
            return responsesList;
        }
        Collection<NptLogicDataField> dataFields;
        NptLogicDataType dataType;
        NptLogicDataField pkFiled;
        String fieldDbName = null;
        StringBuilder sql;
        List<String> tbList = new ArrayList<>();
        List<String> sqlList = new ArrayList<>();
        List<Collection<NptLogicDataField>> dataFieldsList = new ArrayList<>();
        for (NptBaseModelPool pool : pools) {

            // 企业只处理LockLevel大于0的数据
            if (host.equals(NptDict.BMH_BUSINESS) && pool.getLockLevel().equals(NptDict.MPL_LEVEL_0.getCode())) {
                continue;
            }

            dataType = archService.findDataTypeById(pool.getDataTypeId());
            pkFiled = archService.fastFindDataFieldById(pool.getPrimaryFieldId());
            if (dataType != null && dataType.getStatus().equals(NptDict.IDS_ENABLED.getCode()) && pkFiled != null && pkFiled.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {

                fieldDbName = pkFiled.getFieldDbName();
                dataFields = archService.listDataField(dataType.getId(),null,NptDict.IDS_ENABLED);
                if (dataFields.size() > 0) {
                    sql = new StringBuilder();
                    sql.append("select ");
                    sql.append(NptCommonUtil.getFieldString(dataFields,",",NptDict.CST_ONLY_ENG));
                    assert fieldDbName != null;
                    sql.append(" from ").append(dataType.getTypeDbName())
                            .append(" where ")
                            .append(fieldDbName).append(" = '").append(":idCard").append("'");

                    tbList.add(dataType.getTypeDbName());
                    sqlList.add(sql.toString());
                    dataFieldsList.add(dataFields);
                }
            }

        }

        CreditApplyInfoResponse response;
        List<String> infoSqlList = null;
        for (CreditApplyInfo info : infos) {
            for (int i = 0, tbListSize = tbList.size(); i < tbListSize; i++) {
                response = new CreditApplyInfoResponse();
                response.setTbName(tbList.get(i));
                response.setTbColValues(databaseManager.queryList(sqlList.get(i).replace(":idCard", creditCmsUserManager.findById(info.getUserId()).getIdCard()), dataFieldsList.get(i)));
                if (response.getTbColValues().size() > 0) {
                    infoSqlList = responsesList.get(info.getId());
                    if (infoSqlList != null) {
                        infoSqlList.addAll(response.toInsertSql());
                        responsesList.put(info.getId(), infoSqlList);
                    } else {
                        responsesList.put(info.getId(), response.toInsertSql());
                    }
                }
            }
            responsesList.computeIfAbsent(info.getId(), k -> new ArrayList<>());
        }
        return responsesList;
    }

    /**
     *  author: zhanglei
     *  date:   2017/05/22 16:34
     *  note:
     *
     */
    public String index() {
        return SUCCESS;
    }

    /**
     *  author: zhanglei
     *  date:   2017/05/22 16:34
     *  note:
     *
     */
    public String list() {
        Integer beginIndex = getPageBeginIndex();
        Integer currPage = getPageCurrentPage();
        Integer pageSize = getPageSize();

        Collection<Condition> conditions = new ArrayList<>();
        String syncStatus = this.getParameter(NptSyncBase.PROPERTY_SYNC_STATUS);
        if (syncStatus != null && !syncStatus.isEmpty()) {
            if (Integer.parseInt(syncStatus) == NptDict.RCS_NOTSYNED.getCode()) {
                conditions.add(Conditions.eq(NptSyncBase.PROPERTY_SYNC_STATUS, NptDict.RCS_NOTSYNED.getCode()));
            } else {
                //conditions.add(Conditions.ne(NptSyncBase.PROPERTY_SYNC_STATUS, NptDict.RCS_NOTSYNED.getCode()));
                conditions.add(Conditions.eq(NptSyncBase.PROPERTY_SYNC_STATUS,Integer.parseInt(syncStatus)));
            }
        }

        conditions.add(Conditions.desc(NptSyncBase.PROPERTY_CREATE_TIME));
        Pagination<CreditApplyInfo> dataPagination = manager.findAll(beginIndex, pageSize, conditions.toArray(new Condition[conditions.size()]));
        this.setAttribute("dataPagination", createPaginationResult(dataPagination, currPage, pageSize));
        return SUCCESS;
    }

    /**
     *  author: zhanglei
     *  date:   2017/05/22 17:04
     *  note:
     *
     */
    @Override
    protected JSONObject toJSON(CreditApplyInfo info) {
        JSONObject result = new JSONObject();
        result.put("id", info.getId());

        CreditCmsUser user = creditCmsUserManager.findById(info.getUserId());
        if (user != null) {
            result.put("realname", user.getRealname());
            result.put("mobile", user.getMobile());
            result.put("idCard", user.getIdCard());
            result.put("type", user.getType().equals("company") ? "企业" : "个人");
        }

        result.put("createTime", info.getCreateTime());
        result.put("syncTime", info.getSyncTime());
        result.put("syncStatus", info.getSyncStatus());
        return result;
    }
}
