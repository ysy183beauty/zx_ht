package com.credit.CreditQuery.controller;

import com.credit.FTLBox.NPTCreditFTLBox;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.entity.CmsUserExt;
import com.jeecms.core.manager.CmsUserExtMng;
import com.jeecms.core.web.util.CmsUtils;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.dataBinder.NptWebFieldDataArray;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.querier.impl.NptKeyCrowQuerierBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

/**
 * 项目： zxcms
 * 作者： owen
 * 时间： 2017/3/2 14:12
 * 描述：
 *
 *      重点人群查询控制器
 *      基于NPT MODEL进行查询
 */
@Controller
@RequestMapping(value = "/query/kc")
public class NptCreditKeyCrowdController {

    @Autowired
    private NptKeyCrowQuerierBase querier;
    @Autowired
    private CmsUserExtMng cmsUserExtMng;
    /**
     * 作者: owen
     * 时间: 2017/3/13 上午10:38
     * 描述:
     *      重点人群信用查询首页方法
     */
    @RequestMapping(value = "/index.do")
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        Map<String, Collection<NptBaseModelPool>> pools = querier.getBaseModelGroupProviderPoolsMap(NptDict.BMHG_IMPERSON_PRO);
        modelMap.put("_POOLS", pools);
        return NPTCreditFTLBox.FTL_CREDIT_QUERY_KC_INDEX;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/13 下午05:57
     * 备注: 数据列表
     */
    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    public String list(NptWebBridgeBean bean, Long poolId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        NptDict result = querier.getBaseModelPoolPaginationData(poolId, bean, true,false);
        modelMap.put("_RESULT", result);
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
        return NPTCreditFTLBox.FTL_CREDIT_QUERY_KC_LIST;
    }

    /**
     * 作者: owen
     * 时间: 2017/3/13 下午7:56
     * 描述:
     *      重点人群查询详情
     */
    @RequestMapping(value = "/detail.do")
    public String detail(NptWebBridgeBean bean, Long poolId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        if (user != null) {
            CmsUserExt ext = cmsUserExtMng.findById(user.getId());
            modelMap.put("userExt", ext);
        }
        modelMap.put("user", user);
        NptWebFieldDataArray poolData = querier.getBaseModelPoolRowData(poolId, bean.getPrimaryKeyValue());
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), poolData);
        return NPTCreditFTLBox.FTL_CREDIT_QUERY_KC_DETAIL;
    }

    @RequestMapping(value = "/listMobile.do")
    public String listMobile(HttpServletRequest request,NptWebBridgeBean bean,String keyword,ModelMap modelMap){
        CmsUser user = CmsUtils.getUser(request);
        if (user != null) {
            CmsUserExt ext = cmsUserExtMng.findById(user.getId());
            modelMap.put("userExt", ext);
        }
        modelMap.put("user", user);

        NptDict result = querier.fuzzySearch(keyword, bean);
        modelMap.addAttribute("_RESULT_",result);
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
        return NPTCreditFTLBox.FTL_CREDIT_QUERY_KC_LIST_WX;
    }

    @RequestMapping(value = "/detailMobile.do")
    public String detailMobile(NptWebBridgeBean bean, Long poolId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        if (user != null) {
            CmsUserExt ext = cmsUserExtMng.findById(user.getId());
            modelMap.put("userExt", ext);
        }
        modelMap.put("user", user);
        NptWebFieldDataArray poolData = querier.getBaseModelPoolRowData(poolId, bean.getPrimaryKeyValue());
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), poolData);
        return NPTCreditFTLBox.FTL_CREDIT_QUERY_KC_DETAIL_WX;
    }
    /**
     *  author: owen
     *  date:   2017/3/23 15:48
     *  note:
     *          重点人群模糊查询
     */
    @RequestMapping(value = "/fuzzySearch.do")
    public String fuzzySearch(HttpServletRequest request,NptWebBridgeBean bean,String keyword,ModelMap modelMap){
        CmsUser user = CmsUtils.getUser(request);
        if (user != null) {
            CmsUserExt ext = cmsUserExtMng.findById(user.getId());
            modelMap.put("userExt", ext);
        }
        modelMap.put("user", user);

        NptDict result = querier.fuzzySearch(keyword, bean);
        modelMap.addAttribute("_RESULT_",result);
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
        return NPTCreditFTLBox.FTL_CREDIT_SEARCH_KC_DETAIL;
    }

    @RequestMapping(value = "/kcSearch.do")
    public String kcSearch(HttpServletRequest request,NptWebBridgeBean bean,String keyword,ModelMap modelMap){
        CmsUser user = CmsUtils.getUser(request);
        if (user != null) {
            CmsUserExt ext = cmsUserExtMng.findById(user.getId());
            modelMap.put("userExt", ext);
        }
        modelMap.put("user", user);

        NptDict result = querier.fuzzySearch(keyword, bean);
        modelMap.addAttribute("_RESULT_",result);
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
        return NPTCreditFTLBox.FTL_CREDIT_QUERY_SEARCH_KC_LIST;
    }
}
