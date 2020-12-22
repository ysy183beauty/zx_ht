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
import java.util.Date;

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
                Pagination applyInfo = applyInfoService.getApplyInfo(user.getId(), "3", 1, 1);
                if (applyInfo != null && applyInfo.getList() != null && applyInfo.getList().size() > 0) {

                    NptBaseModel searchModel = null;
                    String userType = user.getType();
                    if ("company".equals(userType)) {
                        searchModel = bsQuerier.getThisModel();
                    } else if ("person".equals(userType)) {
                        searchModel = psQuerier.getThisModel();
                    }
                    modelMap.put("byPKFieldId", baseModelService.getBaseModelGroupMainPool(searchModel).getPrimaryFieldId());
                    modelMap.put("key", user.getIdCard());
                    result = psQuerier.loadAuthedInfo(searchModel, bean, user,true);
                    if (NptDict.RST_SUCCESS.equals(result)) {
                        modelMap.put("_START_DATE", ((ApplyInfo) applyInfo.getList().get(0)).getSyncTime());
                        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
                    }
                } else {
                    result = NptDict.RST_UNKNOW;
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

}
