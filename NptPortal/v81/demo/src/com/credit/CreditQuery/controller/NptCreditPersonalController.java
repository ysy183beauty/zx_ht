package com.credit.CreditQuery.controller;

import com.alibaba.fastjson.JSON;
import com.credit.CreditQuery.entity.ApplyInfo;
import com.credit.CreditQuery.service.ApplyInfoService;
import com.credit.FTLBox.NPTCreditFTLBox;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.entity.CmsUserExt;
import com.jeecms.core.manager.CmsUserExtMng;
import com.jeecms.core.web.util.CmsUtils;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.service.NptBaseModelService;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.querier.impl.NptEnterpriseQuerierBase;
import com.npt.querier.impl.NptPersonalQuerierBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 项目： zxcms
 * 作者： owen
 * 时间： 2017/3/2 14:13
 * 描述：
 *
 *      个人信用查询控制器
 *      基于NPT MODEL进行查询
 */
@Controller
@RequestMapping(value = "/query/ps")
public class NptCreditPersonalController {

    @Autowired
    private NptPersonalQuerierBase psQuerier;
    @Autowired
    private NptEnterpriseQuerierBase bsQuerier;
    @Autowired
    private NptBaseModelService baseModelService;
    @Autowired
    private ApplyInfoService applyInfoService;

    @Autowired
    private CmsUserExtMng cmsUserExtMng;
    @Autowired
    private Properties creditResources;
    /**
     * 作者: owen
     * 时间: 2017/3/13 上午10:38
     * 描述:
     *      个人信用查询首页
     */
    @RequestMapping(value = "/index.do")
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        CmsUser user = CmsUtils.getUser(request);
        if (user != null) {
            CmsUserExt ext = cmsUserExtMng.findById(user.getId());
            modelMap.put("userExt", ext);
        }
        modelMap.put("user", user);
        NptDict result = NptDict.RST_EXCEPTION("用户未登录");

        if (user != null) {
            if (CmsUtils.checkUserIsAuthentification(request)) {
                NptWebBridgeBean bean = new NptWebBridgeBean();
                modelMap.addAttribute("_IDCARD", user.getIdCard());
                modelMap.addAttribute("_MOBILE", user.getMobile());
                modelMap.addAttribute("_REAL_NAME", user.getRealname());

                NptBaseModel searchModel = null;
                String userType = user.getType();
                if ("company".equals(userType)) {
                    searchModel = bsQuerier.getThisModel();
                } else if ("person".equals(userType)) {
                    searchModel = psQuerier.getThisModel();
                }

                NptBaseModelPool mp = baseModelService.getBaseModelGroupMainPool(searchModel);
                if(null != mp){
                    modelMap.put("byPKFieldId", mp.getPrimaryFieldId());
                    modelMap.put("key", user.getIdCard());
                    result = psQuerier.loadAuthedInfo(searchModel, bean, user, true);
                    if (NptDict.RST_SUCCESS.equals(result)) {
                        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
                    }
                }else {
                    result = NptDict.RST_EXCEPTION("当前查询服务暂不可用");
                }
            } else {
                result = NptDict.RST_EXCEPTION("您未进行实名认证，暂无法查询个人信用信息");
            }
        }

        modelMap.addAttribute("_RESULT_", result);
        return NPTCreditFTLBox.FTL_CREDIT_QUERY_PS_DETAIL;
    }

    /**
     *  author: owen
     *  date:   2017/3/21 下午10:22
     *  note:
     *          自然人申请加载自身的信用数据
     */
    @RequestMapping(value = "/apply.do")
    public void apply(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CmsUser user = CmsUtils.getUser(request);
        String msg = "用户没有登录";
        if (null != user) {
            if (CmsUtils.checkUserIsAuthentification(request)) {
                if (applyInfoService.isActiveApplyInfo(user.getId(), "3")) {
                    msg = "申请已通过，不能再提交";
                } else if (applyInfoService.isActiveApplyInfo(user.getId(), "2")) {
                    msg = "申请已经在处理中，不能再提交";
                } else if (applyInfoService.isActiveApplyInfo(user.getId(), "1")) {
                    msg = "申请已经在处理中，不能再提交";
                } else {
                    ApplyInfo info = new ApplyInfo();
                    info.setUserId(user.getId());
                    info.setApplyFlag("1");
                    info.setSyncFlag(String.valueOf(NptDict.RCS_NOTSYNED.getCode()));
                    info.setApplyTime(formatter.format(new Date()));
                    ApplyInfo backInfo = applyInfoService.saveApplyInfo(info);//申请提交
                    msg = "申请已经提交成功，等待审核";
                }

            } else {
                msg = "身份不合法";
            }
        } else {
            msg = "用户没有登录";
        }

        ResponseUtils.renderJson(response, JSON.toJSONString(msg));
    }

    /**
     *  author: jiaoss
     *  date:  2017年3月23日21:44:52
     *  note:
     *          自然人申请加载自身的信用数据的记录
     */
    @RequestMapping(value = "/getApplyInfo.do")
   public  void getApplyInfo(int pageNo,int pageSize,HttpServletRequest request,
                             HttpServletResponse response){
        if(pageSize==0){
            pageSize=20;
        }

        CmsUser user = CmsUtils.getUser(request);
        if(null != user){
            if(CmsUtils.checkUserIsAuthentification(request)){
               Pagination page= applyInfoService.getApplyInfo(user.getId(),"",pageNo,pageSize);
                ResponseUtils.renderJson(response, com.alibaba.fastjson.JSON.toJSONString(page));
            }
        }
    }


    /**
     * 作者: 张磊
     * 日期: 2017/05/04 下午03:55
     * 备注: 信用报告
     */
    @RequestMapping(value = "/getReport.do")
    public String getReport(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        if (user != null) {
            CmsUserExt ext = cmsUserExtMng.findById(user.getId());
            modelMap.put("userExt", ext);
            request.getSession().setAttribute("userId", ext.getId());
            request.getSession().setAttribute("realName", ext.getRealname());
            request.getSession().setAttribute("idCard", ext.getIdCard());
            request.getSession().setAttribute("userType", user.getType().equals("company") ? "c" : "p");

            Pagination page = applyInfoService.getApplyInfo(user.getId(), "", 1, 20);
            modelMap.put("_APPLY_LOG", page);
        }
        modelMap.put("user", user);

        request.getSession().setAttribute("hostIP", creditResources.getProperty("CPC_LOCAL_IP"));
        request.getSession().setAttribute("hostPort", creditResources.getProperty("CPC_ACCESS_PORT"));

        return NPTCreditFTLBox.FTL_CREDIT_QUERY_PS_REPORT;
    }

    /**
     * author: zhanglei
     * date:   2017/05/07 15:52
     * note:
     * valid credit report
     */
    @RequestMapping(value = "/validReport.do")
    public String validReport(String type, String key, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        NptBaseModel searchModel = null;
        if ("c".equals(type)) {
            searchModel = bsQuerier.getThisModel();
        } else if ("p".equals(type)) {
            searchModel = psQuerier.getThisModel();
        }

        Map<String, Object> tyValues = baseModelService.getModelMainPoolTypicalValueByUKValue(searchModel, key);

        Boolean isValid = true;
        if (null != tyValues && !tyValues.values().isEmpty()) {
            List<Object> valueList = new ArrayList<>(tyValues.values());
            String idCard = String.valueOf(valueList.get(0));
            //TODO: 根据idCard判断是否申请过信用报告，申请过 isValid = true
        }
        modelMap.put("_IS_VALID", isValid ? "真" : "假");

        NptWebBridgeBean bean = new NptWebBridgeBean();
        bean.setPrimaryKeyValue(key);
        NptDict result = baseModelService.loadBaseModelAuthGroupsByUK(bean, searchModel, false);
        modelMap.put("_RESULT", result);
        modelMap.put("_BEAN", bean);


        return NPTCreditFTLBox.FTL_CREDIT_QUERY_PS_REPORT_VALID;
    }
}
