package com.credit.CreditQuery.controller;

import com.credit.FTLBox.NPTCreditFTLBox;
import com.jeecms.common.web.session.SessionProvider;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.entity.CmsUserExt;
import com.jeecms.core.manager.CmsUserExtMng;
import com.jeecms.core.web.util.CmsUtils;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.querier.impl.NptEnterpriseQuerierBase;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 项目： zxcms
 * 作者： owen
 * 时间： 2017/3/2 14:09
 * 描述：
 *
 *      企业信用查询控制器
 *      基于NPT MODEL进行查询
 */
@Controller
@RequestMapping(value = "/query/bs")
public class NptCreditBusinessController {

    @Autowired
    private NptEnterpriseQuerierBase querier;
    @Autowired
    private CmsUserExtMng cmsUserExtMng;

    @Autowired
    private ImageCaptchaService imageCaptchaService;

    @Autowired
    private SessionProvider session;

    @Autowired
    private NptEnterpriseQuerierBase bsQuerier;
    /**
     * 作者: owen
     * 时间: 2017/3/13 上午10:32
     * 描述:
     *
     *      企业信用查询首页
     */
    @RequestMapping(value = "/index.do")
    public String index(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return NPTCreditFTLBox.FTL_CREDIT_QUERY_BS_INDEX;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/13 下午05:57
     * 备注: 数据列表
     */
    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    public String list(NptWebBridgeBean bean, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        NptBaseModel model = querier.getThisModel();
        NptDict result = querier.getBaseModelIndexFieldPaginationData(model, bean,false);
        modelMap.put("_RESULT", result);
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
        return NPTCreditFTLBox.FTL_CREDIT_QUERY_BS_LIST;
    }


    @RequestMapping(value = "/indexMobile.do", method = RequestMethod.GET)
    public String indexMobile(NptWebBridgeBean bean, ModelMap modelMap) {
        NptBaseModel model = querier.getThisModel();
        NptDict result = querier.getBaseModelIndexFieldPaginationData(model, bean,false);
        modelMap.put("_RESULT", result);
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
        return NPTCreditFTLBox.FTL_CREDIT_QUERY_BS_INDEX_WX;
    }

    @RequestMapping(value = "/listMobile.do", method = RequestMethod.GET)
    public String listMobile(NptWebBridgeBean bean, ModelMap modelMap) {
        NptBaseModel model = querier.getThisModel();
        NptDict result = querier.getBaseModelIndexFieldPaginationData(model, bean,false);
        modelMap.put("_RESULT", result);
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
        return NPTCreditFTLBox.FTL_CREDIT_QUERY_BS_LIST_WX;
    }
    /**
     * 作者: owen
     * 时间: 2017/3/13 下午7:56
     * 描述:
     *      企业查询详情
     */
    @RequestMapping(value = "/detail.do")
    public String detail(String ukValue,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        CmsUser user = CmsUtils.getUser(request);
        if (user != null) {
            CmsUserExt ext = cmsUserExtMng.findById(user.getId());
            modelMap.put("userExt", ext);
        }
        modelMap.put("user", user);

        NptWebBridgeBean bean = new NptWebBridgeBean();
        bean.setPrimaryKeyValue(ukValue);

        querier.loadBaseModelAuthGroupsByUK(bean,querier.getThisModel(),false);

        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);

        return NPTCreditFTLBox.FTL_CREDIT_QUERY_BS_DETAIL;
    }

    @RequestMapping(value = "/detailMobile.do")
    public String detailMobile(String ukValue,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){

        NptWebBridgeBean bean = new NptWebBridgeBean();
        bean.setPrimaryKeyValue(ukValue);

        querier.loadBaseModelAuthGroupsByUK(bean,querier.getThisModel(),false);

        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);

        return NPTCreditFTLBox.FTL_CREDIT_QUERY_BS_DETAIL_WX;
    }

    /**
     *  author: owen
     *  date:   2017/3/23 下午1:56
     *  note:
     *          依据关键字对企业信息进行模糊查询
     */
    @RequestMapping(value = "/fuzzySearch.do")
    public String fuzzySearch(HttpServletRequest request,HttpServletResponse response,NptWebBridgeBean bean,String keyword,ModelMap modelMap,String captcha) {

        Boolean captchaIsOk=true;
        try {
            if (!imageCaptchaService.validateResponseForID(session
                    .getSessionId(request, response), captcha)) {
                captchaIsOk=false;
            }
        } catch (CaptchaServiceException e) {
            e.printStackTrace();
            captchaIsOk=false;
        }
        if(captchaIsOk==true) {
            CmsUser user = CmsUtils.getUser(request);
            if (user != null) {
                CmsUserExt ext = cmsUserExtMng.findById(user.getId());
                modelMap.put("userExt", ext);
            }
            modelMap.put("user", user);

            NptDict result = querier.fuzzySearch(keyword, bean);
            modelMap.addAttribute("_RESULT_", result);
            modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
            return NPTCreditFTLBox.FTL_CREDIT_SEARCH_BUS_DETAIL;
        }else {//验证码不通过

//            try {
//                response.sendRedirect("/?captcha=error");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            String key="";
            try {
                key= URLEncoder.encode(keyword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "redirect:../../../index.jhtml?captcha=error&keyword="+key;
        }
    }

    /**
     *  author: jss
     *  date:   2017/11/02 下午10:20
     *  note:
     *          依据关键字对企业信息进行模糊查询_分页
     */
    @RequestMapping(value = "/fuzzySearchFy.do")
    public String fuzzySearchfy(HttpServletRequest request,HttpServletResponse response,NptWebBridgeBean bean,String keyword,ModelMap modelMap,String captcha){
        Boolean captchaIsOk=true;
        try {
            if (!imageCaptchaService.validateResponseForID(session
                    .getSessionId(request, response), captcha)) {
                captchaIsOk=false;
            }
        } catch (CaptchaServiceException e) {
            e.printStackTrace();
            captchaIsOk=false;
        }
        if(captchaIsOk==true) {
            CmsUser user = CmsUtils.getUser(request);
            if (user != null) {
                CmsUserExt ext = cmsUserExtMng.findById(user.getId());
                modelMap.put("userExt", ext);
            }
            modelMap.put("user", user);

            NptDict result = querier.fuzzySearch(keyword, bean);
            modelMap.addAttribute("_RESULT_", result);
            modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
            return NPTCreditFTLBox.FTL_CREDIT_SEARCH_BUS_DETAIL_FY;
        }else {//验证码不通过
            //return NPTCreditFTLBox.FTL_CREDIT_SEARCH_BUS_DETAIL_FY;

            String key="";
            try {
                key= URLEncoder.encode(keyword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "redirect:../../../index.jhtml?captcha=error&keyword="+key;
        }

    }

    @RequestMapping(value = "/bsSearch.do")
    public String bsSearch(HttpServletRequest request,HttpServletResponse response,NptWebBridgeBean bean,String keyword,ModelMap modelMap,String captcha){
        Boolean captchaIsOk=true;
        try {
            if (!imageCaptchaService.validateResponseForID(session
                    .getSessionId(request, response), captcha)) {
                captchaIsOk=false;
            }
        } catch (CaptchaServiceException e) {
            System.out.println("验证码错误！");
            captchaIsOk=false;
        }
        if(captchaIsOk==true) {
            CmsUser user = CmsUtils.getUser(request);

            if (user != null) {
                CmsUserExt ext = cmsUserExtMng.findById(user.getId());
                modelMap.put("userExt", ext);
            }
            modelMap.put("user", user);

            NptDict result = querier.fuzzySearch(keyword, bean);
            modelMap.addAttribute("_RESULT_", result);
            modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
            return NPTCreditFTLBox.FTL_CREDIT_QUERY_SEARCH_BS_LIST;
        }else {//验证码错误

            modelMap.addAttribute("captcha","error");
            return NPTCreditFTLBox.FTL_CREDIT_QUERY_INDEX_ERROR;
        }
    }

}
