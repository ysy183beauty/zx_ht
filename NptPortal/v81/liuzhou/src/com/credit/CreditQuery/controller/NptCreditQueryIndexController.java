package com.credit.CreditQuery.controller;

import com.alibaba.fastjson.JSON;
import com.credit.FTLBox.NPTCreditFTLBox;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.web.util.CmsUtils;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.dict.NptDictBean;
import com.npt.common.service.SecurityService;
import com.npt.querier.impl.NptEnterpriseQuerierBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 作者：owen
 * 创建时间：2017/3/8 下午2:18
 * 描述：
 *
 *      信用查询首页类
 */
@Controller
@RequestMapping(value = "/query")
public class NptCreditQueryIndexController {

    @Autowired
    private NptEnterpriseQuerierBase bsQuerier;
    @Autowired
    private SecurityService securityService;

    /**
     * 作者:owen
     * 时间:2017/3/8 下午2:23
     * 描述:
     *      通过导航菜单中信用查询进入的首页面
     */
    @RequestMapping(value = "/index.do",method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        CmsUser user = CmsUtils.getUser(request);
        //当前登录的用户信息
        modelMap.addAttribute("user",user);
        //当前用户是否已实名认证
        modelMap.addAttribute("user_authed",CmsUtils.checkUserIsAuthentification(request));

        Long bsCount = bsQuerier.getBaseModelEntityCount();

        //当前已收录的企业数量
        modelMap.addAttribute("bsCount",bsCount);

        return NPTCreditFTLBox.FTL_CREDIT_QUERY_INDEX;
    }

    /**
     *  author: zhanglei
     *  date:   2017/06/19 14:04
     *  note:
     *          返回用户基本信息, 供首页用
     */
    @RequestMapping(value = "/basicInfo.do", method = RequestMethod.GET)
    public void basicInfo(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        //当前登录的用户信息
        modelMap.put("username", user.getUsername());
        modelMap.put("email", user.getEmail());
        modelMap.put("realname", user.getUserExt().getRealname());
        modelMap.put("idCard", user.getUserExt().getIdCard());
        modelMap.put("type", user.getUserExt().getType());
        modelMap.put("mobile", user.getUserExt().getMobile());

        //当前用户是否已实名认证
        modelMap.put("user_authed", CmsUtils.checkUserIsAuthentification(request));

        ResponseUtils.renderJson(response, JSON.toJSONString(modelMap));
    }

    @RequestMapping(value = "/validate.do")
    public void validate(String keyword, String captcha, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        NptDict result = securityService.classicSearchValidate(keyword, captcha, request, response);

        NptDictBean rb = result.castToBean();

        ResponseUtils.renderJson(response,JSON.toJSONString(rb));
    }
}
