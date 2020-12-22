package com.npt.grs.apply.action;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.grs.apply.entity.NptResourceApply;
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
import java.util.Collection;
import java.util.Date;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/11/1 11:05
 * 备注：
 */
@Controller("npt.grs.approval")
public class NptResourceApprovalAction extends NptRMSAction<NptResourceApply>{

    @Autowired
    private NptResourceApplyManager applyManager;

    @Autowired
    private NptResourceApplyFieldsManager applyFieldsManager;

    @Autowired
    private NptBusinessFlowLogManager applyLogManager;

    @Resource(name = "rmsAuthService")
    private NptRmsAuthService authService;


    public String cdcIndex(){
        return listCDCByCondition();
    }

    public String prdIndex(){
        return listPRDByCondition();
    }

    /**
     * 作者：owen
     * 日期：2016/11/1 20:09
     * 备注：
     *      依据状态加载审批列表
     * 参数：
     * 返回：
     */
    public String listCDCByCondition(){
        Integer beginIndex = getPageBeginIndex();
        Integer currPage = getPageCurrentPage();
        Integer pageSize = getPageSize();
        Long applyOrgId = getLongParameter("orgId");
        String dataTypeTitle = getParameter("dataTypeTitle");
        String applyStatus = getParameter("applyStatus");

        NptDict status = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.RAS,applyStatus);

        Pagination<NptResourceApply> dataPagination =
                applyManager.getResourceApplyForEstablish(
                        null,
                        null,
                        applyOrgId,
                        null,
                        dataTypeTitle,
                        status,
                        beginIndex,
                        pageSize);

        this.setAttribute("orgList",applyManager.getOrgList());
        if(null != dataPagination){
            this.setAttribute("dataPagination", createPaginationResult(dataPagination, currPage, pageSize));
        }else {
            this.setAttribute("dataPagination",createEmptyPaginationResult());
        }
        return SUCCESS;
    }

    /**
     * 作者：owen
     * 日期：2016/11/1 20:09
     * 备注：
     *      依据状态加载审批列表
     * 参数：
     * 返回：
     */
    public String listPRDByCondition(){
        Integer beginIndex = getPageBeginIndex();
        Integer currPage = getPageCurrentPage();
        Integer pageSize = getPageSize();
        Long applyOrgId = getLongParameter("orgId");
        String dataTypeTitle = getParameter("dataTypeTitle");
        String applyStatus = getParameter("applyStatus");

        NptDict status = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.RAS,applyStatus);
        NptSimpleUser user = authService.getUserById(getOperatorId());
        if(null != user) {
            Pagination<NptResourceApply> dataPagination =
                    applyManager.getResourceApplyForEstablish(
                            NptDict.RAS_WAITTING.getCode(),
                            null,
                            applyOrgId,
                            user.getUserOrgId(),
                            dataTypeTitle,
                            status,
                            beginIndex,
                            pageSize);

            this.setAttribute("orgList", applyManager.getOrgList());
            if (null != dataPagination) {
                this.setAttribute("dataPagination", createPaginationResult(dataPagination, currPage, pageSize));
            } else {
                this.setAttribute("dataPagination", createEmptyPaginationResult());
            }
        }
        return SUCCESS;
    }

    /**
     *作者: xuqinyuan
     *时间: 2016/11/23 18:06
     *备注: 查看审批信息
     */
    public String showCDCApproval(){
       return showDetail();
    }

    public String showPRDApproval(){
        return  showDetail();
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
        Object applyData = null;
        if(null != applyNo && !applyNo.isEmpty()){
            apply = applyManager.getResourceApplyByNO(applyNo);
            applyFields = applyFieldsManager.getApplyFieldsDetailByApplyNO(applyNo);
            if(null != apply) {
                applyData = applyFieldsManager.queryApplyFieldData(apply);
            }
        }

        this.setAttribute("applyData",applyData);
        this.setAttribute("applyInfo",apply);
        this.setAttribute("applyFields",applyFields);
        return SUCCESS;
    }

    /**
     * 作者：owen
     * 日期：2016/11/1 15:02
     * 备注：
     *      审批
     * 参数：
     * 返回：
     */
    public void approvalByCDC(){
        try {
            String applyNo = getPrimaryKeyValue();
            Long userId = getOperatorId();
            Integer approvalResult = getIntParameter("approvalResult");
            String confirmStartDate = getParameter("confirmStartDate");
            String confirmEndDate = getParameter("confirmEndDate");
            String approvalNote = getParameter("approvalNote");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date confirmDate[] = new Date[2];
            confirmDate[0] = sdf.parse(confirmStartDate);
            confirmDate[1] = sdf.parse(confirmEndDate);

            NptDict result =
                applyManager.approvalApplyByCDC(userId,applyNo,approvalResult,approvalNote,confirmDate);
            if(result == NptDict.RST_SUCCESS){
                this.outputSuccessResult(result.getTitle());
            }else {
                this.outputErrorResult(result.getTitle());
            }

        }catch (NumberFormatException e){
            this.outputErrorResult(NptDict.RST_INVALID_PARAMS.getTitle());
        }catch (ParseException e){
            this.outputErrorResult(NptDict.RST_ERROR.getTitle());
        }
    }

    public void approvalByPRD(){
        try {
            String applyNo = getPrimaryKeyValue();
            Long userId = getOperatorId();
            Integer approvalResult = getIntParameter("approvalResult");
            String confirmStartDate = getParameter("confirmStartDate");
            String confirmEndDate = getParameter("confirmEndDate");
            String approvalNote = getParameter("approvalNote");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date confirmDate[] = new Date[2];
            confirmDate[0] = sdf.parse(confirmStartDate);
            confirmDate[1] = sdf.parse(confirmEndDate);

            NptDict result =
                    applyManager.approvalApplyByPRD(userId,applyNo,approvalResult,approvalNote,confirmDate);
            if(result == NptDict.RST_SUCCESS){
                this.outputSuccessResult(result.getTitle());
            }else {
                this.outputErrorResult(result.getTitle());
            }

        }catch (NumberFormatException e){
            this.outputErrorResult(NptDict.RST_INVALID_PARAMS.getTitle());
        }catch (ParseException e){
            this.outputErrorResult(NptDict.RST_ERROR.getTitle());
        }
    }
}
