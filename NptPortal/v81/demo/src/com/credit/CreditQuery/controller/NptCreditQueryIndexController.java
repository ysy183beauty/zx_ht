package com.credit.CreditQuery.controller;

import com.credit.FTLBox.NPTCreditFTLBox;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.web.util.CmsUtils;
import com.npt.querier.impl.NptEnterpriseQuerierBase;
import com.npt.querier.impl.NptPersonalQuerierBase;
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
    @RequestMapping(value = "/indexMobile.do",method = RequestMethod.GET)
    public String indexMobile(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        CmsUser user = CmsUtils.getUser(request);
        //当前登录的用户信息
        modelMap.addAttribute("user",user);
        //当前用户是否已实名认证
        modelMap.addAttribute("user_authed",CmsUtils.checkUserIsAuthentification(request));

        Long bsCount = bsQuerier.getBaseModelEntityCount();

        //当前已收录的企业数量
        modelMap.addAttribute("bsCount",bsCount);

        return NPTCreditFTLBox.FTL_CREDIT_QUERY_KC_INDEX_WX;
    }
}
