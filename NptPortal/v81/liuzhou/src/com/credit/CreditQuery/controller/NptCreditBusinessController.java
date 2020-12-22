package com.credit.CreditQuery.controller;

import com.credit.FTLBox.NPTCreditFTLBox;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.web.util.CmsUtils;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.querier.impl.NptEnterpriseQuerierBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


    @RequestMapping(value = "/listMobile.do", method = RequestMethod.GET)
    public String listMobile(NptWebBridgeBean bean, ModelMap modelMap) {
        NptBaseModel model = querier.getThisModel();
        NptDict result = querier.getBaseModelIndexFieldPaginationData(model, bean,false);
        modelMap.put("_RESULT", result);
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
        return NPTCreditFTLBox.FTL_CREDIT_QUERY_BS_INDEX_M;
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
        modelMap.put("user", user);

        NptWebBridgeBean bean = new NptWebBridgeBean();
        bean.setPrimaryKeyValue(ukValue);

        querier.loadBaseModelAuthGroupsByUK(bean,querier.getThisModel(),false);

        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);

        return NPTCreditFTLBox.FTL_CREDIT_QUERY_BS_DETAIL;
    }

    /**
     *  author: owen
     *  date:   2017/3/23 下午1:56
     *  note:
     *          依据关键字对企业信息进行模糊查询
     */
    @RequestMapping(value = "/fuzzySearch.do")
    public String fuzzySearch(HttpServletRequest request,NptWebBridgeBean bean,String keyword,ModelMap modelMap){
        CmsUser user = CmsUtils.getUser(request);
        modelMap.put("user", user);

        NptDict result = querier.fuzzySearch(keyword, bean);
        modelMap.addAttribute("_RESULT_",result);
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
        return NPTCreditFTLBox.FTL_CREDIT_SEARCH_BUS_DETAIL;
    }

    @RequestMapping(value = "/bsSearch.do")
    public String bsSearch(NptWebBridgeBean bean, String keyword,String index, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        modelMap.put("user", user);
        String ftl = NPTCreditFTLBox.FTL_CREDIT_QUERY_SEARCH_BS_LIST;
        if (index != null) {
            //当前用户是否已实名认证
            modelMap.addAttribute("user_authed", CmsUtils.checkUserIsAuthentification(request));
            //当前已收录的企业数量
            modelMap.addAttribute("bsCount", querier.getBaseModelEntityCount());
            ftl = NPTCreditFTLBox.FTL_CREDIT_INDEX_SEARCH_BS_LIST;
        }

        NptDict result = querier.fuzzySearch(keyword, bean);
        modelMap.put("keyword", keyword);
        modelMap.put("_RESULT_", result);
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
        return ftl;
    }

}
