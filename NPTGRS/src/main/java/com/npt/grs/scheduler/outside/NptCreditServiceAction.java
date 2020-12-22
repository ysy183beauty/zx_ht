package com.npt.grs.scheduler.outside;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.sync.entity.*;
import com.npt.bridge.util.FileUtils;
import com.npt.bridge.util.ImageBase64Utils;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.grs.appeal.entity.NptCreditServiceInfo;
import com.npt.grs.appeal.manager.NptAppealDetailManager;
import com.npt.grs.appeal.manager.NptAppealInfoManager;
import com.npt.grs.appeal.manager.NptCreditServiceInfoManager;
import com.npt.grs.apply.manager.NptBusinessFlowLogManager;
import com.npt.grs.credit.manager.CreditCmsUserManager;
import com.npt.rms.base.NptRmsCommonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.summer.extend.orm.Condition;
import org.summer.extend.orm.Pagination;
import org.summer.extend.orm.condition.Conditions;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/3/22
 * 备注:
 */
@Controller("npt.grs.scheduler.outside.creditService")
public class NptCreditServiceAction extends NptSyncAction<NptCreditServiceInfo, Long> {

    @Autowired
    private CreditCmsUserManager cmsUserManager;
    @Autowired
    private NptAppealInfoManager appealInfoManager;
    @Autowired
    private NptAppealDetailManager appealDetailManager;
    @Autowired
    private NptBusinessFlowLogManager applyLogManager;

    @Resource(name = "rmsCommonService")
    public void setCommonService(NptRmsCommonService commonService) {
        super.commonService = commonService;
    }

    @Autowired
    public void setManager(NptCreditServiceInfoManager manager) {
        super.manager = manager;
    }

    @Override
    protected Class<NptCreditServiceInfo> getEntityClass() {
        return NptCreditServiceInfo.class;
    }

    @Override
    protected NptDict getSyncDict() {
        return NptDict.RMT_SYNC_CREDITSERVICE;
    }

    @Override
    protected NptDict getSyncOkDict() {
        return NptDict.RMT_SYNC_CREDITSERVICE_OK;
    }

    @Override
    protected NptDict getSyncResponseDict() {
        return NptDict.RMT_SYNC_CREDITSERVICE_REP;
    }

    @Override
    protected NptDict getSyncLogDict() {
        return NptDict.LGA_SYNC_CREDITSERVICE;
    }

    @Override
    protected NptDict getSyncResponseLogDict() {
        return NptDict.LGA_SYNC_CREDITSERVICE_REP;
    }


    private NptCreditServiceInfo data;

    public NptCreditServiceInfo getData() {
        return data;
    }

    public void setData(NptCreditServiceInfo data) {
        this.data = data;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/07 下午04:35
     * 备注: 异议单独拿出来
     */
    public String appealIndex() {
        this.setAttribute(NptCreditServiceInfo.PROPERTY_FLAG, NptDict.CSF_OBJECTION.getCode());
        return SUCCESS;
    }

    public String index() {
        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/22 下午08:09
     * 备注:
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
                conditions.add(Conditions.ne(NptSyncBase.PROPERTY_SYNC_STATUS, NptDict.RCS_NOTSYNED.getCode()));
            }
        }
        String flag = this.getParameter("flag");
        if (StringUtils.isNotEmpty(flag)) {
            conditions.add(Conditions.eq(NptCreditServiceInfo.PROPERTY_FLAG, flag));
        } else {
            conditions.add(Conditions.ne(NptCreditServiceInfo.PROPERTY_FLAG, String.valueOf(NptDict.CSF_OBJECTION.getCode())));
            conditions.add(Conditions.ne(NptCreditServiceInfo.PROPERTY_FLAG, String.valueOf(NptDict.CSF_SELF.getCode())));
        }

        conditions.add(Conditions.desc(NptSyncBase.PROPERTY_CREATE_TIME));
        Pagination<NptCreditServiceInfo> dataPagination = manager.findAll(beginIndex, pageSize, conditions.toArray(new Condition[conditions.size()]));
        this.setAttribute("dataPagination", createPaginationResult(dataPagination, currPage, pageSize));
        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/31 下午02:42
     * 备注: 进入回复页面
     */
    public String editPage() {
        Long id = this.getLongParameter(NptSyncBase.PROPERTY_ID);
        this.data = manager.findById(id);
        CreditCmsUser cmsUser = cmsUserManager.findById(data.getUserId());
        this.setAttribute("cmsUser", cmsUser);
        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/22 下午09:51
     * 备注: 保存回复
     */
    public void edit() {
        try {
            Long id = this.getLongParameter(NptSyncBase.PROPERTY_ID);
            String response = this.getParameter("response");
            NptCreditServiceInfo info = manager.findById(id);
            info.setResponse(response);
            info.setResponseTime(NptCommonUtil.getCurrentSysTimestamp());
            info.setSyncStatus(NptDict.RCS_NEEDSYNC.getCode());
            manager.update(info);
            this.outputSuccessResult();
        } catch (Exception e) {
            this.outputErrorResult("保存失败");
            e.printStackTrace();
        }
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/23 下午02:54
     * 备注: 下载附件
     */
    public void downloadAttach() {
        Long id = this.getLongParameter(NptSyncBase.PROPERTY_ID);
        NptCreditServiceInfo info = manager.findById(id);
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

    /**
     * 作者: 张磊
     * 日期: 2017/03/21 下午08:42
     * 备注: 定时同步门户的信用服务数据
     */
    @Override
    protected boolean saveInfo(NptCreditServiceInfo info) {
        //保存
        boolean saveAttach = true;
        if (info.getAttach() != null) {
            try {
                ImageBase64Utils.base64ToImageFile(info.getAttachFile(), NptCreditServiceAction.class.getClassLoader().getResource("").getPath().replace("/WEB-INF/classes/", "") + info.getAttach());
            } catch (IOException e) {
                saveAttach = false;
                e.printStackTrace();
            }
        }

        // 将自身异议写入平台的异议表
        if (saveAttach && info.getFlag().equals(String.valueOf(NptDict.CSF_SELF.getCode()))) {
            CreditAppealInfo appealInfo = JSON.parseObject(info.getText(), CreditAppealInfo.class);
            NptUserAppeal userAppeal = appealInfo.getUserAppeal();
            userAppeal.setSource(NptDict.SOURCE_CMS.getCode());
            userAppeal.setAttach(info.getAttach());
            userAppeal.setDesc(info.getSource());
            if (appealInfoManager.getAppealByNo(userAppeal.getAppealNo()) == null) {
                appealInfoManager.save(userAppeal);
                appealDetailManager.saveAll(appealInfo.getAppealDetails());
                applyLogManager.addAppealLog(userAppeal, null);
            }
        }

        return saveAttach && super.saveInfo(info);
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/22 下午02:35
     * 备注: 同步信用服务回复
     */
    @Override
    protected Object generateResponse(List<NptCreditServiceInfo> infos) {
        List<CreditServiceResponse> responseList = new ArrayList<>();
        for (NptCreditServiceInfo info : infos) {
            CreditServiceResponse response = new CreditServiceResponse();
            response.setResponse(info.getResponse());
            response.setResponseTime(info.getResponseTime());
            response.setId(info.getId());
            responseList.add(response);
        }
        return responseList;
    }


    /**
     * 作者: 张磊
     * 日期: 2017/03/30 下午10:05
     * 备注:
     */
    @Override
    protected JSONObject toJSON(NptCreditServiceInfo data) {
        JSONObject result = new JSONObject();
        result.put("id", data.getId());
        result.put("flag", NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.CSF, Integer.parseInt(data.getFlag())).getTitle());

        CreditCmsUser cmsUser = cmsUserManager.findById(data.getUserId());
        if (cmsUser != null) {
            result.put("userName", cmsUser.getRealname());
            result.put("userType", cmsUser.getTypeName());
        } else {
            result.put("userName", "匿名");
            result.put("userType", "未知");
        }
        result.put("createTime", data.getCreateTime());
        result.put("syncStatus", data.getSyncStatus());
        result.put("response", data.getResponse());
        return result;
    }
}
