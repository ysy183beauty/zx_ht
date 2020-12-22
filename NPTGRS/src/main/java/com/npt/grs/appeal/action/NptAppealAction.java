package com.npt.grs.appeal.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.sync.entity.NptSyncBase;
import com.npt.bridge.sync.entity.NptUserAppeal;
import com.npt.bridge.sync.entity.NptUserAppealDetail;
import com.npt.bridge.util.FileUtils;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.grs.appeal.manager.NptAppealDetailManager;
import com.npt.grs.appeal.manager.NptAppealInfoManager;
import com.npt.grs.apply.manager.NptBusinessFlowLogManager;
import com.npt.grs.credit.manager.CreditCmsUserManager;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.rms.auth.entity.NptSimpleUser;
import com.npt.rms.auth.service.NptRmsAuthService;
import com.npt.rms.base.NptRmsCommonService;
import com.npt.rms.base.action.NptRMSAction;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.summer.extend.orm.Condition;
import org.summer.extend.orm.Pagination;
import org.summer.extend.orm.condition.Conditions;
import org.summer.extend.security.PlatformSecurityContext;
import org.summer.upload.po.FileObject;
import org.summer.upload.utils.CommonUtil;
import org.summer.upload.utils.UploadPreferencesUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 项目: NPTGRS
 * 作者: 张磊
 * 日期: 16/11/2 上午11:4
 * 备注: 异议处理
 */
@Controller("npt.grs.appeal")
public class NptAppealAction extends NptRMSAction<NptUserAppeal>{

    private static final Logger log = LoggerFactory
            .getLogger(NptAppealAction.class);

    @Autowired
    private NptAppealInfoManager appealInfoManager;
    @Autowired
    private NptAppealDetailManager appealDetailManager;
    @Autowired
    private NptBusinessFlowLogManager applyLogManager;
    @Autowired
    private CreditCmsUserManager creditCmsUserManager;

    @Resource(name = "rmsAuthService")
    private NptRmsAuthService authService;

    @Resource(name = "rmsArchService")
    private NptRmsArchService rmsArchService;

    @Resource(name = "rmsCommonService")
    protected NptRmsCommonService commonService;

    private NptUserAppeal appeal;

    public NptUserAppeal getAppeal() {
        return appeal;
    }

    public void setAppeal(NptUserAppeal appeal) {
        this.appeal = appeal;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/07 上午10:29
     * 备注:
     */
    public String index() {
        this.setAttribute("_ORG_LIST", rmsArchService.listAllOrg());
        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/07 上午11:35
     * 备注:
     */
    public String list() {
        Integer beginIndex = getPageBeginIndex();
        Integer currPage = getPageCurrentPage();
        Integer pageSize = getPageSize();

        Collection<Condition> conditions = new ArrayList<>();
        conditions.add(Conditions.eq(NptBaseEntity.PROPERTY_CREATOR_ID, (Long) PlatformSecurityContext.getCurrentOperator().getOperatorId()));
//        conditions.add(Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));

        String appealBusinessKey = this.getParameter("appealBusinessKey");
        if (StringUtils.isNotEmpty(appealBusinessKey)) {
            conditions.add(Conditions.like(NptUserAppeal.PROPERTY_APPEAL_BUSINESS_KEY, appealBusinessKey));
        }
        String appealDTTitle = this.getParameter("appealDTTitle");
        if (StringUtils.isNotEmpty(appealDTTitle)) {
            conditions.add(Conditions.like(NptUserAppeal.PROPERTY_APPEAL_DT_TITLE, appealDTTitle));
        }
        Long appealProviderId = this.getLongParameter("appealProviderId");
        if (appealProviderId != null) {
            conditions.add(Conditions.eq(NptUserAppeal.PROPERTY_APPEAL_PROVIDER, appealProviderId));
        }
        String appealPKTitle = this.getParameter("appealPKTitle");
        if (StringUtils.isNotEmpty(appealPKTitle)) {
            Collection<?> pkIds = appealInfoManager.findByHql("select appealPKID from NptUserAppeal where creatorId=" + PlatformSecurityContext.getCurrentOperator().getOperatorId());
            for (Iterator<?> iterator = pkIds.iterator(); iterator.hasNext(); ) {
                Long pkId = Long.parseLong(String.valueOf(iterator.next()));
                if (!rmsArchService.findDataFieldById(pkId).getAlias().contains(appealPKTitle)) {
                    iterator.remove();
                }
            }
            conditions.add(Conditions.in(NptUserAppeal.PROPRTTY_APPEAL_PK_ID, pkIds));
        }
        conditions.add(Conditions.desc(NptBaseEntity.PROPERTY_CREATE_TIME));

        Pagination<NptUserAppeal> dataPagination = appealInfoManager.findAll(beginIndex, pageSize, conditions.toArray(new Condition[conditions.size()]));
        this.setAttribute("dataPagination", createPaginationResult(dataPagination, currPage, pageSize));
        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/07 上午11:26
     * 备注:
     */
    @Override
    protected JSONObject toJSON(NptUserAppeal data) {
        JSONObject result = new JSONObject();
        result.put("id", data.getId());
        result.put("source", NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.SOURCE, data.getSource()).getTitle());
        result.put("appealUser", data.getAppealUser());
        result.put("appealUserTel", data.getAppealUserTel());
        result.put("appealNo", data.getAppealNo());
        result.put("appealBusinessKey", data.getAppealBusinessKey());
        result.put("appealDTTitle", data.getAppealDTTitle());
        result.put("appealPKTitle", rmsArchService.findDataFieldById(data.getAppealPKID()).getAlias());
        result.put("appealProviderTitle", data.getAppealProviderTitle());
        result.put("createTime", data.getCreateTime());
        result.put("appealStatus", data.getAppealStatus());
        result.put("appealStatusTitle", NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.RAS, data.getAppealStatus()).getTitle());
        return result;
    }

    public String cdcIndex() {
         return listCDCByCondition();
    }

    public String prdIndex(){
        return listPRDByCondition();
    }

    /**
     *作者: xuqinyuan
     *时间: 2016/11/24 15:12
     *备注: 根据状态加载审核异议信息
     */
    public String listCDCByCondition(){
        Integer beginIndex = getPageBeginIndex();
        Integer currPage = getPageCurrentPage();
        Integer pageSize = getPageSize();

        String dataTypeTitle = getParameter("dataTypeTitle");
        String appealStatus = getParameter("appealStatus");
        Long appealProvider = getLongParameter("appealProvider");

        NptDict dict = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.RAS,appealStatus);

        Pagination<NptUserAppeal> dataPagination =
                appealInfoManager.getAppealByCondition(null,appealProvider,dataTypeTitle,dict,beginIndex,pageSize);

        this.setAttribute("orgList",rmsArchService.listAllOrg());
        if (null != dataPagination){
            this.setAttribute("dataPagination",createPaginationResult(dataPagination, currPage, pageSize));
        }else{
            this.setAttribute("dataPagination",createEmptyPaginationResult());
        }

        return SUCCESS;
    }

    public String listPRDByCondition(){
        Integer beginIndex = getPageBeginIndex();
        Integer currPage = getPageCurrentPage();
        Integer pageSize = getPageSize();

        String dataTypeTitle = getParameter("dataTypeTitle");
        String appealStatus = getParameter("appealStatus");

        NptSimpleUser user = authService.getUserById(getOperatorId());

        NptDict dict = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.RAS,appealStatus);

        Pagination<NptUserAppeal> dataPagination =
                appealInfoManager.getAppealByCondition(NptDict.RAS_WAITTING.getCode(),user.getUserOrgId(),dataTypeTitle,dict,beginIndex,pageSize);

        this.setAttribute("orgList",rmsArchService.listAllOrg());
        if (null != dataPagination){
            this.setAttribute("dataPagination",createPaginationResult(dataPagination,currPage,pageSize));
        }else {
            this.setAttribute("dataPagination",createEmptyPaginationResult());
        }

        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/07 下午03:36
     * 备注: 查看我的异议详情
     */
    public String showMyAppeal() {
        return showAppealDetail();
    }

    /**
     *作者: xuqinyuan
     *时间: 2016/11/24 15:15
     *备注: 查看审核信息
     */
    public String showCDCAppeal(){
        return showAppealDetail();
    }

    public String showPRDAppeal(){
        return showAppealDetail();
    }

    /**
     *作者: xuqinyuan
     *时间: 2016/11/24 15:17
     *备注: 查看异议详情
     */
    public String showAppealDetail(){
        String appealNo = getPrimaryKeyValue();

        Collection<NptUserAppealDetail> appealFields = null;
        NptUserAppeal appeal = null;
        Object appealData = null;

        if (null != appealNo && !appealNo.isEmpty()) {
            appeal = appealInfoManager.getAppealByNo(appealNo);
            appealFields = appealDetailManager.getAppealDetailByNo(appealNo);
            if (null != appeal) {
                appealData = appealDetailManager.queryAppealFieldData(appeal);
                this.setAttribute("_APPEAL_LOG", applyLogManager.getResourceApplyLogs(appealNo));
                if (appeal.getSource() == NptDict.SOURCE_CMS.getCode() && appeal.getAppealStatus() == NptDict.RAS_WAITTING.getCode()) {
                    this.setAttribute("_STEP_USER", creditCmsUserManager.findById(appeal.getStepUserId().intValue()));
                } else {
                    this.setAttribute("_STEP_USER", authService.getUserById(appeal.getStepUserId()));
                }
            }
        }

        this.setAttribute("_APPEAL_DATA",appealData);
        this.setAttribute("_APPEAL",appeal);
        this.setAttribute("_APPEAL_FIELDS",appealFields);
        return SUCCESS;
    }

    /**
     *作者: xuqinyuan
     *时间: 2016/11/24 15:19
     *备注: 审核异议
     */
    public void appealByCDC(){
        try {
            Long userId = getOperatorId();
            String appealNo = getPrimaryKeyValue();
            Integer appealStatus = getIntParameter("appealStatus");
            String frozenStartDate = getParameter("frozenStartDate");
            String frozenEndtDate = getParameter("frozenEndDate");
            String appealResult = getParameter("appealResult");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date frozenDate[] = new Date[2];
            frozenDate[0] = sdf.parse(frozenStartDate);
            frozenDate[1] = sdf.parse(frozenEndtDate);


            NptDict dict = appealInfoManager.handleAppealByCDC(userId,appealNo,appealStatus,frozenDate,appealResult);
            if(dict == NptDict.RST_SUCCESS){
                this.outputSuccessResult(dict.getTitle());
            }else {
                this.outputErrorResult(dict.getTitle());
            }

        }catch (NumberFormatException e){
            this.outputErrorResult(NptDict.RST_INVALID_PARAMS.getTitle());
        }catch (ParseException e) {
            this.outputErrorResult(NptDict.RST_ERROR.getTitle());
        }

    }

    public void appealByPRD(){
        try {
            Long userId = getOperatorId();
            String appealNo = getPrimaryKeyValue();
            String passFields = getParameter("passFields");
            String unPassFields = getParameter("unPassFields");

            List<Object> fieldList = JSON.parseArray(passFields);
            List<Object> unPassFieldList = JSON.parseArray(unPassFields);
            NptDict dict = appealInfoManager.handleAppealByPRD(userId,appealNo,new Date(),fieldList,unPassFieldList);
            if(dict == NptDict.RST_SUCCESS){
                this.outputSuccessResult(dict.getTitle());
            }else {
                this.outputErrorResult(dict.getTitle());
            }
        }catch (NumberFormatException e){
            this.outputErrorResult(NptDict.RST_INVALID_PARAMS.getTitle());
        }

    }


     /**
       * 作者：97175
       * 日期：2016/11/7 11:52
       * 备注：
       *      新增异议
       * 参数：
       * 返回：
       */
     public void addAppeal(){

         String appealUser = getParameter("appealUser");
         String appealUserTel = getParameter("appealUserTel");
         String appealUserEmail = getParameter("appealUserEmail");
         String appealFieldInfo = getParameter("appealFields");
         String dtTitle = getParameter("dtTitle");
         String orgName = getParameter("orgName");
         String key = getParameter("key");
         Long orgId = getLongParameter("orgId");
         Long dataTypeId = NptCommonUtil.getDefaultParentId();
         Long byFieldId = NptCommonUtil.getDefaultParentId();


         try {
             dataTypeId = getLongParameter("appealDTID");
             byFieldId = getLongParameter("byPKFieldId");

         }catch (NumberFormatException e){
             this.outputErrorResult(NptDict.RST_INVALID_PARAMS.getTitle());
         }

         NptUserAppeal userAppeal = new NptUserAppeal();
         String appealNO = UUID.randomUUID().toString();
         userAppeal.setAppealDTID(dataTypeId);
         userAppeal.setAppealNo(appealNO);
         userAppeal.setAppealPKID(byFieldId);
         userAppeal.setAppealUser(appealUser);
         userAppeal.setAppealUserTel(appealUserTel);
         userAppeal.setAppealUserEmail(appealUserEmail);
         userAppeal.setCreateTime(new Date());
         userAppeal.setCreatorId(getOperatorId());
         userAppeal.setStatus(NptDict.IDS_ENABLED.getCode());
         userAppeal.setAppealStatus(NptDict.RAS_WAITTING.getCode());
         userAppeal.setAppealProviderId(orgId);
         userAppeal.setAppealDTTitle(dtTitle);
         userAppeal.setAppealProviderTitle(orgName);
         userAppeal.setAppealBusinessKey(key);
         userAppeal.setStepUserId(getOperatorId());
         userAppeal.setDesc(appeal.getDesc());
         userAppeal.setSource(NptDict.SOURCE_NPT.getCode());

         List<FileObject> newFiles = CommonUtil.getNewFiles(getRequest(), "upload_file");
         if (newFiles.size() > 0) {
             FileObject newFile = newFiles.get(0);
             String fileName = "/WEB-INF/attachments/creditService" + "/" + appealNO + newFile.getFileType();
             File destFile = new File(this.getSession().getServletContext().getRealPath("") + fileName);
             File srcFile = new File(UploadPreferencesUtils.getUploadRootPath() + newFile.getFileName());
             try {
                 org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
                 userAppeal.setAttach(fileName);
             } catch (IOException e) {
                 this.outputErrorResult(NptDict.RST_ERROR.getTitle());
                 e.printStackTrace();
             }
         }

         try {
             appealInfoManager.save(userAppeal);

             List<NptUserAppealDetail> appealDetails = new ArrayList<>();

             if(null != appealFieldInfo){
                 List<Object> fieldList = JSON.parseArray(appealFieldInfo);

                 if(null != fieldList && fieldList.size() > 0){
                     for(Object object:fieldList){
                         Map<String,String> oneField = (Map<String, String>) object;

                         if(null != oneField){

                             Long fieldId = Long.valueOf(oneField.get("id"));
                             String value = oneField.get("value");
                             String defaultValue = oneField.get("default");
                             NptUserAppealDetail appealDetail = new NptUserAppealDetail();
                             appealDetail.setFieldId(fieldId);
                             appealDetail.setAppealValue(value);
                             appealDetail.setAppealNo(appealNO);
                             appealDetail.setCreatorId(getOperatorId());
                             appealDetail.setCreateTime(new Date());
                             appealDetail.setStatus(NptDict.IDS_DISABLED.getCode());
                             appealDetail.setDefaultValue(defaultValue);
                             appealDetails.add(appealDetail);
                         }
                     }
                 }
             }
             if(appealDetails.size() > 0){
                 appealDetailManager.saveAll(appealDetails);
             }

             applyLogManager.addAppealLog(userAppeal,null);
             this.outputSuccessResult(NptDict.RST_SUCCESS.getTitle());
         }catch (Exception e){
             this.outputErrorResult(NptDict.RST_ERROR.getTitle());
         }

     }

    /**
     * 作者: 张磊
     * 日期: 2017/03/23 下午02:54
     * 备注: 下载附件
     */
    public void downloadAttach() {
        Long id = this.getLongParameter(NptSyncBase.PROPERTY_ID);
        NptUserAppeal info = appealInfoManager.findById(id);
        String attach = info.getAttach();
        if (attach != null) {
            String filePath = this.getSession().getServletContext().getRealPath("") + attach;
            if (new File(filePath).exists()) {
                FileUtils.download(this.getResponse(), filePath, attach.substring(attach.lastIndexOf("/") + 1));
            } else {
                this.outputErrorResult("附件不存在");
            }
        }
    }
}
