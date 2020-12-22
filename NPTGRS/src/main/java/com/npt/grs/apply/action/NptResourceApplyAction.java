package com.npt.grs.apply.action;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.grs.apply.entity.NptBusinessFlowLog;
import com.npt.grs.apply.entity.NptResourceApply;
import com.npt.grs.apply.entity.NptResourceApplyField;
import com.npt.grs.apply.manager.NptBusinessFlowLogManager;
import com.npt.grs.apply.manager.NptResourceApplyFieldsManager;
import com.npt.grs.apply.manager.NptResourceApplyManager;
import com.npt.rms.auth.entity.NptSimpleUser;
import com.npt.rms.auth.service.NptRmsAuthService;
import com.npt.rms.base.action.NptRMSAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.summer.extend.orm.Pagination;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/10 18:52
 * 备注：
 */
@Controller("npt.grs.apply")
public class NptResourceApplyAction extends NptRMSAction<NptResourceApply> {

    @Autowired
    private NptResourceApplyManager applyManager;

    @Autowired
    private NptResourceApplyFieldsManager applyFieldsManager;

    @Autowired
    private NptBusinessFlowLogManager applyLogManager;

    @Resource(name = "rmsAuthService")
    private NptRmsAuthService rmsAuthService;

    /**
     * 作者：owen
     * 日期：2016/11/1 13:51
     * 备注：
     *      个人申请首页
     * 参数：
     * 返回：
     */
    public String listPage() {

        return listByCondition();
    }

    /**
     * 作者：owen
     * 日期：2016/11/1 20:01
     * 备注：
     *      依据状态加载
     * 参数：
     * 返回：
     */
    public String listByCondition(){

        Integer beginIndex = getPageBeginIndex();
        Integer currPage = getPageCurrentPage();
        Integer pageSize = getPageSize();
        Long orgId = getLongParameter("orgId");
        String poolTitle = getParameter("poolTitle");
        String businessKey = getParameter("businessKey");
        String applyStatus = getParameter("applyStatus");
        NptDict status = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.RAS,applyStatus);

        Pagination<NptResourceApply> dataPagination =
                applyManager.getPaganitionUserApply(getOperatorId(),orgId,poolTitle,businessKey,status,beginIndex,pageSize);

        this.setAttribute("orgList",applyManager.getOrgList());
        if(null != dataPagination){
            this.setAttribute("dataPagination", createPaginationResult(dataPagination, currPage, pageSize));
        }else {
            this.setAttribute("dataPagination", createEmptyPaginationResult());
        }

        return  SUCCESS;
    }

    /**
     * 作者：owen
     * 日期：2016/11/1 14:34
     * 备注：
     *      查看个人申请的详情及进度
     * 参数：
     * 返回：
     */
    public String showDetail(){
        String applyNo = getPrimaryKeyValue();

        Collection<NptLogicDataField> applyFields = null;
        NptResourceApply apply = null;
        NptBusinessFlowLog applyLog = null;
        if(null != applyNo && !applyNo.isEmpty()){
            apply = applyManager.getResourceApplyByNO(applyNo);
            applyFields = applyFieldsManager.getApplyFieldsDetailByApplyNO(applyNo);
            applyLog = applyLogManager.getResourceApplyLog(applyNo,apply.getApplyStatus());
        }

        this.setAttribute("applyInfo",apply);
        this.setAttribute("applyFields",applyFields);
        this.setAttribute("applyLog",applyLog);

        return SUCCESS;
    }

    /**
     * 作者：owen
     * 日期：2016/10/31 10:49
     * 备注：
     *      添加字段申请
     * 参数：
     * 返回：
     */
    public void addFieldApply() {

        try {
            Long userId = getOperatorId();
            Long poolId = getLongParameter("applyPoolId");
            String businessKey = getParameter("applyBusinessKey");

            Collection<NptDict> status = new ArrayList<>();
            status.add(NptDict.RAS_ACCEPTED);
            status.add(NptDict.RAS_REFUSED);
            Collection<NptResourceApply> applyList = applyManager.getUserApply(userId,poolId,businessKey,status);
            if(null != applyList && applyList.size() > NptCommonUtil.IntegerZero()){
                this.outputErrorResult(NptDict.RST_DUPLICATE_OPERATION.getTitle());
            }else {
                NptResourceApply apply = addNewFieldApply(userId,poolId,businessKey);
                if(null != apply){
                    applyLogManager.addApplyLog(apply,null);
                    this.outputSuccessResult(NptDict.RST_SUCCESS.getTitle());
                }else {
                    this.outputErrorResult(NptDict.RST_INVALID_PARAMS.getTitle());
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.outputErrorResult(e.getMessage());
        }
    }

    /**
     * 作者：owen
     * 日期：2016/10/31 14:11
     * 备注：
     *      添加一个新的申请
     * 参数：
     * 返回：
     */
    private NptResourceApply addNewFieldApply(Long userId,Long poolId,String businessKey){

        String[] fieldIds = getParameter("fieldIds").split(",");
        if(null == fieldIds || 0 == fieldIds.length ){
            return null;
        }

        NptResourceApply nptResourceApply = new NptResourceApply();
        nptResourceApply.setParentId(NptCommonUtil.getDefaultParentId());
        nptResourceApply.setStatus(NptDict.IDS_ENABLED.getCode());
        nptResourceApply.setCreateTime(new Date());
        nptResourceApply.setCreatorId(userId);

        String applyNO = UUID.randomUUID().toString();

        nptResourceApply.setApplyNo(applyNO);
        nptResourceApply.setApplyUserId(userId);
        nptResourceApply.setApplyUserName(getOperator().getName());
        nptResourceApply.setApplyUserTel(getParameter("applyUserTel"));

        NptSimpleUser user = rmsAuthService.getUserById(userId);
        nptResourceApply.setApplyUserOrg(user.getUserOrgId());

        nptResourceApply.setApplyProviderTitle(getParameter("providerTitle"));
        nptResourceApply.setApplyGrouPoolTitle(getParameter("applyPoolTitle"));
        nptResourceApply.setApplyProviderId(getLongParameter("providerId"));
        nptResourceApply.setApplyReason(getParameter("applyReason"));
        nptResourceApply.setApplyGrouPoolId(poolId);
        nptResourceApply.setApplyBusinessKey(businessKey);
        nptResourceApply.setApplyType(NptDict.RAT_DATA_FIELD.getCode());
        String sd=getParameter("startDate");
        String ed=getParameter("endDate");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = sdf.parse(sd);
            Date endDate = sdf.parse(ed);
            nptResourceApply.setApplyedStartDate(startDate);
            nptResourceApply.setApplyedEndDate(endDate);
        } catch (ParseException e) {
            Date current = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(current);
            calendar.add(calendar.DATE,NptCommonUtil.getApplyStandardFinishDays());
            nptResourceApply.setApplyedStartDate(calendar.getTime());

            calendar.add(calendar.DATE,NptCommonUtil.getApplyStandardDuringDays());
            nptResourceApply.setApplyedEndDate(calendar.getTime());
        }

        nptResourceApply.setStepUserId(userId);
        nptResourceApply.setApplyStatus(NptDict.RAS_WAITTING.getCode());
        nptResourceApply.setApplyStatusTitle(NptDict.RAS_WAITTING.getTitle());

        applyManager.save(nptResourceApply);

        List<NptResourceApplyField> applyFieldList = new ArrayList<>();

        for(String fid:fieldIds){
            try {
                Long id = Long.parseLong(fid);
                NptResourceApplyField field = new NptResourceApplyField();
                field.setFieldId(id);
                field.setStatus(NptDict.IDS_ENABLED.getCode());
                field.setParentId(NptCommonUtil.getDefaultParentId());
                field.setApplyNo(applyNO);
                field.setCreateTime(new Date());

                applyFieldList.add(field);
            }catch (NumberFormatException e){
                continue;
            }
        }
        applyFieldsManager.saveAll(applyFieldList);

        return nptResourceApply;
    }
}
