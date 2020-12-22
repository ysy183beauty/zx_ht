package com.credit.CreditServices.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.credit.CreditServices.entity.AppealField;
import com.credit.CreditServices.entity.CreditServiceInfo;
import com.credit.CreditServices.manager.CreditServiceInfoManager;
import com.credit.FTLBox.NPTCreditFTLBox;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.entity.CmsUserExt;
import com.jeecms.core.manager.CmsUserExtMng;
import com.jeecms.core.web.util.CmsUtils;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.arch.service.NptArchService;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.sync.entity.CreditAppealInfo;
import com.npt.bridge.sync.entity.NptUserAppeal;
import com.npt.bridge.sync.entity.NptUserAppealDetail;
import com.npt.common.service.FileUploadService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


/**
 * 作者：owen
 * 创建时间：2017/3/8 下午2:24
 * 描述：
 * <p>
 * 信用服务首页类
 */
@Controller
@RequestMapping(value = "/srv")
public class NptCreditServiceIndexController {

    private static final Logger log = LoggerFactory
            .getLogger(NptCreditServiceIndexController.class);

    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private CreditServiceInfoManager serviceInfoManager;
    @Autowired
    private CmsUserExtMng cmsUserExtMng;

    @Autowired
    private NptArchService archService;

    private static String NULL_STR = "-";
    private static String PATH = "/WEB-INF/attachments/creditService";

    /**
     * 作者:owen
     * 时间:2017/3/8 下午2:27
     * 描述:
     * 模块首页方法
     */
    @RequestMapping(value = "/index.do", method = RequestMethod.GET)
    public String index(String source, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        modelMap.put("user", CmsUtils.getUser(request));
        // 默认进入异议处理
        modelMap.put("_FLAG", String.valueOf(NptDict.CSF_OBJECTION.getCode()));
        modelMap.put("_FLAG_NAME", NptDict.CSF_OBJECTION.getTitle());
        modelMap.put("_SOURCE", source);
        return NPTCreditFTLBox.FTL_CREDIT_SERVICE_INDEX;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/09 下午09:27
     * 备注: 异议处理
     */
    @RequestMapping(value = "/objection.do", method = RequestMethod.GET)
    public String objection(String source, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        if (user != null) {
            CmsUserExt ext = cmsUserExtMng.findById(user.getId());
            modelMap.put("userExt", ext);
        }
        modelMap.put("user", user);

        modelMap.put("_FLAG", String.valueOf(NptDict.CSF_OBJECTION.getCode()));
        modelMap.put("_FLAG_NAME", NptDict.CSF_OBJECTION.getTitle());
        modelMap.put("_SOURCE", source);
        return NPTCreditFTLBox.FTL_CREDIT_SERVICE_OBJECTION;
    }

    @RequestMapping(value = "/objectionMobile.do", method = RequestMethod.GET)
    public String objectionMobile(String source, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        modelMap.put("user", CmsUtils.getUser(request));
        modelMap.put("_FLAG", "0");
        modelMap.put("_FLAG_NAME", "异议处理");
        modelMap.put("_SOURCE", source);
        return NPTCreditFTLBox.FTL_CREDIT_SERVICE_OBJECTION_WX;
    }
    /**
     * 作者: 张磊
     * 日期: 2017/03/09 下午09:28
     * 备注: 信用投诉
     */
    @RequestMapping(value = "/complain.do", method = RequestMethod.GET)
    public String complain(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        if (user != null) {
            CmsUserExt ext = cmsUserExtMng.findById(user.getId());
            modelMap.put("userExt", ext);
        }
        modelMap.put("user", user);

        modelMap.put("_FLAG", String.valueOf(NptDict.CSF_COMPLAIN.getCode()));
        modelMap.put("_FLAG_NAME", NptDict.CSF_COMPLAIN.getTitle());
        return NPTCreditFTLBox.FTL_CREDIT_SERVICE_OBJECTION;
    }

    @RequestMapping(value = "/complainMobile.do",method = RequestMethod.GET)
    public String complainMobile(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        modelMap.put("user", CmsUtils.getUser(request));
        modelMap.put("_FLAG", "1");
        modelMap.put("_FLAG_NAME", "信用投诉");
        return NPTCreditFTLBox.FTL_CREDIT_SERVICE_OBJECTION_WX;
    }


    /**
     * 作者: 张磊
     * 日期: 2017/03/09 下午09:28
     * 备注: 信用咨询
     */
    @RequestMapping(value = "/advice.do", method = RequestMethod.GET)
    public String advice(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        modelMap.put("user", user);
        return NPTCreditFTLBox.FTL_CREDIT_SERVICE_ADVICE;
    }

    @RequestMapping(value = "/adviceMobile.do",method = RequestMethod.GET)
    public String adviceMobile(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        modelMap.put("user", CmsUtils.getUser(request));
        return NPTCreditFTLBox.FTL_CREDIT_SERVICE_ADVICE_WX;
    }


    /**
     * 作者: 张磊
     * 日期: 2017/03/09 下午09:28
     * 备注: 我的消息
     */
    @RequestMapping(value = "/massage.do", method = RequestMethod.GET)
    public String massage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        if (user != null) {
            CmsUserExt ext = cmsUserExtMng.findById(user.getId());
            modelMap.put("userExt", ext);
        }
        modelMap.put("user", user);
        return NPTCreditFTLBox.FTL_CREDIT_SERVICE_MESSAGE;
    }

    @RequestMapping(value = "/massageMobile.do",method = RequestMethod.GET)
    public String massageMobile(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        modelMap.put("user", CmsUtils.getUser(request));
        return NPTCreditFTLBox.FTL_CREDIT_SERVICE_MESSAGE_WX;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/09 下午09:28
     * 备注: 服务机构
     */
    @RequestMapping(value = "/organization.do", method = RequestMethod.GET)
    public String organization(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        return NPTCreditFTLBox.FTL_CREDIT_SERVICE_ORGANIZATION;
    }


    /**
     * 作者: 张磊
     * 日期: 2017/03/08 下午01:46
     * 备注: 附件上传
     */
    @RequestMapping(value = "/file/o_swfupload.do", method = RequestMethod.POST)
    public void swfUpload(
            String root,
            @RequestParam(value = "qqfile", required = false) MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
        String fileName = fileUploadService.saveFile(request, PATH, file);
        log.info("file upload seccess: {}, size:{}.", fileName, file.getSize());
        ResponseUtils.renderText(response, "{\"success\":true, \"newUuid\": \""+fileName+"\"}");
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/08 下午02:45
     * 备注: 保存信用投诉/咨询
     */
    @RequestMapping(value = "/objection/add.do", method = RequestMethod.POST)
    public void objectionAdd(
            CreditServiceInfo info,
            HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        modelMap.put("user", user);
        if (user == null) {
            return;
        }
        String attach = info.getAttach();
        if (StringUtils.isNotBlank(info.getText())) {
            info.setUser(user);
            info.setAttach(StringUtils.isNotBlank(attach) ? PATH + "/" + attach : null);
            try {
                serviceInfoManager.save(info);
                ResponseUtils.renderText(response, "success");
            } catch (Exception e) {
                log.error("保存信用服务失败");
                ResponseUtils.renderText(response, "error");
                e.printStackTrace();
            }
        } else {
            ResponseUtils.renderText(response, "error");
        }
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/09 下午10:34
     * 备注: 查看信用投诉/咨询列表
     */
    @RequestMapping(value = "/objection/list.do", method = RequestMethod.GET)
    public String objectionList(
            String flag,
            int pageNo, int pageSize,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        modelMap.put("user", user);
        if (user != null) {
            Pagination pagination = serviceInfoManager.getPage(user.getId(), flag, pageNo, pageSize);
            modelMap.put("_PAGINATION", pagination);
            modelMap.put("_FLAG", flag);
            if (flag.equals(String.valueOf(NptDict.CSF_OBJECTION.getCode()))) {
                modelMap.put("_FLAG_NAME", NptDict.CSF_OBJECTION.getTitle());
            } else {
                modelMap.put("_FLAG_NAME", NptDict.CSF_COMPLAIN.getTitle());
            }
        }
        return NPTCreditFTLBox.FTL_CREDIT_SERVICE_OBJECTION_LIST;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/09 下午10:56
     * 备注: 查看信用投诉/咨询详情
     */
    @RequestMapping(value = "/objection/detail.do", method = RequestMethod.GET)
    public String objectionDetail(
            Long id,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap modelMap) throws Exception {
        CmsUser user = CmsUtils.getUser(request);
        if (user != null) {
            CmsUserExt ext = cmsUserExtMng.findById(user.getId());
            modelMap.put("userExt", ext);
        }
        modelMap.put("user", user);
        if (user != null) {
            CreditServiceInfo info = serviceInfoManager.detail(user.getId(), id);
            info.setUser(user);
            modelMap.put("_DATA", info);

            if (info.getFlag().equals(String.valueOf(NptDict.CSF_SELF.getCode()))) {
                CreditAppealInfo appealInfo = JSON.parseObject(info.getText(), CreditAppealInfo.class);
                modelMap.put("_FIELD_LIST", archService.listDataField(appealInfo.getUserAppeal().getAppealDTID(), null, null));
                modelMap.put("_TEXT", appealInfo);
                if (info.getResponse() != null) {
                    modelMap.put("_RESPONSE", JSON.parseObject(info.getResponse(), CreditAppealInfo.class));
                }
            }

        }
        return NPTCreditFTLBox.FTL_CREDIT_SERVICE_OBJECTION_DETAIL;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/10 下午02:20
     * 备注: 添加信用咨询
     */
    @RequestMapping(value = "/advice/add.do", method = RequestMethod.POST)
    public void adviceAdd(
            CreditServiceInfo info,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        modelMap.put("user", user);
        if (user == null) {
            return;
        }
        if (StringUtils.isNotBlank(info.getText())) {
            info.setUser(user);
            info.setTel(user.getPhone() == null ? NULL_STR : user.getPhone());
            info.setEmail(user.getEmail() == null ? NULL_STR : user.getEmail());
            info.setTitle(NULL_STR);
            info.setFlag(String.valueOf(NptDict.CSF_ADVICE.getCode()));
            try {
                serviceInfoManager.save(info);
                ResponseUtils.renderText(response, "success");
            } catch (Exception e) {
                log.error("保存信用咨询失败");
                ResponseUtils.renderText(response, "error");
                e.printStackTrace();
            }
        } else {
            ResponseUtils.renderText(response, "error");
        }
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/10 下午02:11
     * 备注: 信用咨询列表
     */
    @RequestMapping(value = "/advice/list.do", method = RequestMethod.GET)
    public String adviceList(
            int pageNo, int pageSize,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        modelMap.put("user", user);
        if (user != null) {
            Pagination pagination = serviceInfoManager.getPage(user.getId(), String.valueOf(NptDict.CSF_ADVICE.getCode()), pageNo, pageSize);
            modelMap.put("_PAGINATION", pagination);
        }
        return NPTCreditFTLBox.FTL_CREDIT_SERVICE_ADVICE_LIST;
    }

    @RequestMapping(value = "/advice/listMobile.do", method = RequestMethod.GET)
    public String adviceListMobile(
            int pageNo, int pageSize,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        modelMap.put("user", user);
        if (user != null) {
            Pagination pagination = serviceInfoManager.getPage(user.getId(), "3", pageNo, pageSize);
            modelMap.put("_PAGINATION", pagination);
        }
        return NPTCreditFTLBox.FTL_CREDIT_SERVICE_ADVICE_LIST_WX;
    }


    /**
     * 作者: 张磊
     * 日期: 2017/04/01 下午09:53
     * 备注: 下载附件
     */
    @RequestMapping(value = "/download.do", method = RequestMethod.GET)
    public void download(
            Long id,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        modelMap.put("user", user);
        if (user != null) {
            String attach = serviceInfoManager.getAttach(user.getId(), id);
            fileUploadService.downloadFile(response, attach, attach.substring(attach.lastIndexOf("/") + 1));
        }
    }

    /**
     * 作者: 柏伟
     * 时间: 2017/3/15 下午4:30
     * 描述:
     * 服务机构
     */
    @RequestMapping(value = "/srvGover.do", method = RequestMethod.GET)
    public String province(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        return NPTCreditFTLBox.FTL_CREDIT_INDEX_SRV_INDEX;
    }

    /**
     * 作者：97175
     * 日期：2016/11/7 11:52
     * 备注：
     * 新增异议
     * 参数：
     * 返回：
     */
    @RequestMapping(value = "/addAppeal.do", method = RequestMethod.POST)
    public void addAppeal(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        JSONObject result = new JSONObject();
        result.put("result", false);

        CmsUser user = CmsUtils.getUser(request);
        if (user == null) {
            result.put("message", "登录异常");
            ResponseUtils.renderJson(response, JSON.toJSONString(result));
            return;
        }

        CmsUserExt ext = cmsUserExtMng.findById(user.getId());
        if(ext==null || !ext.getFlag().equals(String.valueOf(NptDict.USER_AUTH_OK.getCode()))){
            result.put("message", "实名认证异常");
            ResponseUtils.renderJson(response, JSON.toJSONString(result));
            return;
        }

        modelMap.put("userExt", ext);
        modelMap.put("user", user);


        CreditAppealInfo appealInfo = new CreditAppealInfo();

        String appealUser = ext.getRealname();
        String appealUserTel = ext.getMobile();
        String appealUserEmail = user.getEmail();
        String appealFieldInfo = request.getParameter("appealFields");

        List<AppealField> appealFields = JSON.parseArray(appealFieldInfo, AppealField.class);
        AppealField appealField = appealFields.get(0);
        NptLogicDataType dataType = archService.findDataTypeById(archService.findDataFieldById(appealField.getId()).getParentId());

        String dtTitle = dataType.getAlias();
        Long orgId = dataType.getParentId();
        String orgName = archService.findProviderById(orgId).getAlias();

        String key = request.getParameter("key");
        Long dataTypeId = dataType.getId();
        Long byFieldId = Long.valueOf(request.getParameter("byPKFieldId"));

        String attach = request.getParameter("attach");
        String source = request.getParameter("source");

        NptUserAppeal userAppeal = new NptUserAppeal();
        String appealNO = UUID.randomUUID().toString();
        userAppeal.setAppealDTID(dataTypeId);
        userAppeal.setAppealNo(appealNO);
        userAppeal.setAppealPKID(byFieldId);
        userAppeal.setAppealUser(appealUser);
        userAppeal.setAppealUserTel(appealUserTel);
        userAppeal.setAppealUserEmail(appealUserEmail);
        userAppeal.setCreateTime(new Date());
        userAppeal.setCreatorId(Long.valueOf(user.getId()));
        userAppeal.setStatus(NptDict.IDS_ENABLED.getCode());
        userAppeal.setAppealStatus(NptDict.RAS_WAITTING.getCode());
        userAppeal.setAppealProviderId(orgId);
        userAppeal.setAppealDTTitle(dtTitle);
        userAppeal.setAppealProviderTitle(orgName);
        userAppeal.setAppealBusinessKey(key);
        userAppeal.setStepUserId(Long.valueOf(user.getId()));

        try {
            appealInfo.setUserAppeal(userAppeal);

            List<NptUserAppealDetail> appealDetails = new ArrayList<>();

            if (null != appealFieldInfo) {
                List<Object> fieldList = JSON.parseArray(appealFieldInfo);

                if (null != fieldList && fieldList.size() > 0) {
                    for (Object object : fieldList) {
                        Map<String, String> oneField = (Map<String, String>) object;

                        if (null != oneField) {

                            Long fieldId = Long.valueOf(oneField.get("id"));
                            String value = oneField.get("value");
                            String defaultValue = oneField.get("defaultValue");
                            NptUserAppealDetail appealDetail = new NptUserAppealDetail();
                            appealDetail.setFieldId(fieldId);
                            appealDetail.setAppealValue(value);
                            appealDetail.setAppealNo(appealNO);
                            appealDetail.setCreatorId(Long.valueOf(user.getId()));
                            appealDetail.setCreateTime(new Date());
                            appealDetail.setStatus(NptDict.IDS_DISABLED.getCode());
                            appealDetail.setDefaultValue(defaultValue);
                            appealDetails.add(appealDetail);
                        }
                    }
                }
            }
            if (appealDetails.size() > 0) {
                appealInfo.setAppealDetails(appealDetails);
            }

            CreditServiceInfo info = new CreditServiceInfo();
            info.setAttach(StringUtils.isNotBlank(attach) ? PATH + "/" + attach : null);
            info.setSource(source);
            info.setUser(user);
            info.setTel(appealUserTel);
            info.setEmail(appealUserEmail);
            info.setTitle(NULL_STR);
            info.setFlag(String.valueOf(NptDict.CSF_SELF.getCode()));
            info.setText(JSON.toJSONString(appealInfo));
            info.setAppealNo(appealNO);
            serviceInfoManager.save(info);

            result.put("result", true);
            result.put("message", NptDict.RST_SUCCESS.getTitle());
            ResponseUtils.renderJson(response, JSON.toJSONString(result));
            return;
        } catch (Exception e) {
            result.put("message", NptDict.RST_ERROR.getTitle());
            ResponseUtils.renderJson(response, JSON.toJSONString(result));
            return;
        }

    }

}
