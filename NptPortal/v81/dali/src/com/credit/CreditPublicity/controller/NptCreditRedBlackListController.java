package com.credit.CreditPublicity.controller;

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
import com.npt.querier.impl.NptRBPublicityQuerierBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目： zxcms
 * 作者： owen
 * 时间： 2017/3/2 14:05
 * 描述：
 *
 *      红黑榜控制器
 *      基于NPT MODEL进行查询
 */
@Controller
@RequestMapping(value = "/pub/rbl")
public class NptCreditRedBlackListController {

    @Autowired
    private NptRBPublicityQuerierBase querier;
    @Autowired
    private CmsUserExtMng cmsUserExtMng;
    /**
     * 作者: owen
     * 时间: 2017/3/12 下午2:35
     * 描述:
     *      红黑榜模型树在MODELMAP中的全局名称
     */
    public static final String MP_ATTR_RBLIST = "MP_ATTR_RBLIST";

    /**
     * 作者: owen
     * 时间: 2017/3/10 下午6:08
     * 描述:
     *      红黑榜首页
     */
    @RequestMapping(value = "/red/index.do", method = RequestMethod.GET)
    public String redIndex(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Map<String, Collection<NptBaseModelPool>> pools = querier.getBaseModelGroupProviderPoolsMap(NptDict.BMHG_BLACKRED_RED);
        modelMap.put("_POOLS", pools);
        return NPTCreditFTLBox.FTL_CREDIT_PUB_RBLIST_INDEX;
    }

    @RequestMapping(value = "/red/indexMobile.do", method = RequestMethod.GET)
    public String redIndexMobile(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Map<String, Collection<NptBaseModelPool>> pools = querier.getBaseModelGroupProviderPoolsMap(NptDict.BMHG_BLACKRED_RED);
        modelMap.put("_POOLS", pools);
        return NPTCreditFTLBox.FTL_CREDIT_PUB_RBLIST_INDEX_WX;
    }

    @RequestMapping(value = "/black/index.do", method = RequestMethod.GET)
    public String blackIndex(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Map<String, Collection<NptBaseModelPool>> pools = querier.getBaseModelGroupProviderPoolsMap(NptDict.BMHG_BLACKRED_BLACK);
        modelMap.put("_POOLS", pools);
        return NPTCreditFTLBox.FTL_CREDIT_PUB_RBLIST_INDEX;
    }

    @RequestMapping(value = "/black/indexMobile.do", method = RequestMethod.GET)
    public String blackIndexMobile(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Map<String, Collection<NptBaseModelPool>> pools = querier.getBaseModelGroupProviderPoolsMap(NptDict.BMHG_BLACKRED_BLACK);
        modelMap.put("_POOLS", pools);
        return NPTCreditFTLBox.FTL_CREDIT_PUB_RBLIST_INDEX_WX;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/13 下午05:57
     * 备注: 数据列表
     */
    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    public String list(NptWebBridgeBean bean, Long poolId, Integer fromIndex,HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        if (user != null) {
            CmsUserExt ext = cmsUserExtMng.findById(user.getId());
            modelMap.put("userExt", ext);
        }
        modelMap.put("user", user);
        NptDict result = querier.getBaseModelPoolPaginationData(poolId, bean,true,false);
        modelMap.put("_RESULT", result);
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);

        /**
         * 1表示是从门户首页调用，则返回带有头和尾的模板
         *
         * 否则返回内容块模板
         */
        if(NptCommonUtil.INTEGER_1 == fromIndex){
            return NPTCreditFTLBox.FTL_CREDIT_PUB_RBLIST_DIVLIST;
        }else {
            return NPTCreditFTLBox.FTL_CREDIT_PUB_RBLIST_LIST;
        }
    }
    @RequestMapping(value = "/listMobile.do", method = RequestMethod.GET)
    public String listMobile(NptWebBridgeBean bean, Long poolId, Integer fromIndex,HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        NptDict result = querier.getBaseModelPoolPaginationData(poolId, bean,true,false);
        modelMap.put("_RESULT", result);
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);

        /**
         * 1表示是从门户首页调用，则返回带有头和尾的模板
         *
         * 否则返回内容块模板
         */
        if(NptCommonUtil.INTEGER_1 == fromIndex){
            return NPTCreditFTLBox.FTL_CREDIT_PUB_RBLIST_DIVLIST;
        }else {
            return NPTCreditFTLBox.FTL_CREDIT_PUB_RBLIST_LIST_WX;
        }
    }
    /**
     * 作者: owen
     * 时间: 2017/3/10 下午4:29
     * 描述:
     *      红黑榜数据详情方法
     */
    @RequestMapping(value = "/detail.do", method = RequestMethod.POST)
    public String detail(NptWebBridgeBean bean, Long poolId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        if (user != null) {
            CmsUserExt ext = cmsUserExtMng.findById(user.getId());
            modelMap.put("userExt", ext);
        }
        modelMap.put("user", user);
        NptWebFieldDataArray poolData = querier.getBaseModelPoolRowData(poolId, bean.getPrimaryKeyValue());
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), poolData);
        return NPTCreditFTLBox.FTL_CREDIT_PUB_RBLIST_DETAIL;
    }

    @RequestMapping(value = "/detailMobile.do", method = RequestMethod.GET)
    public String detailMobile(NptWebBridgeBean bean, Long poolId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        if (user != null) {
            CmsUserExt ext = cmsUserExtMng.findById(user.getId());
            modelMap.put("userExt", ext);
        }
        modelMap.put("user", user);
        NptWebFieldDataArray poolData = querier.getBaseModelPoolRowData(poolId, bean.getPrimaryKeyValue());
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), poolData);
        return NPTCreditFTLBox.FTL_CREDIT_PUB_RBLIST_DETAIL_WX;
    }
    /**
     * 作者: owen
     * 时间: 2017/3/12 下午2:56
     * 描述:
     *      用于在门户首页显示的红黑榜列表
     */
    @RequestMapping(value = "/divIndex.do",method = RequestMethod.GET)
    public String divIndex(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){

        Collection<NptBaseModelPool> redPools = querier.getBaseModelGroupPools(NptDict.BMHG_BLACKRED_RED);
        Collection<NptBaseModelPool> blackPools = querier.getBaseModelGroupPools(NptDict.BMHG_BLACKRED_BLACK);

        Map<String,Collection<NptBaseModelPool>> result = new HashMap<>();
        result.put("红名单",redPools);
        result.put("黑名单",blackPools);

        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), result);

        return NPTCreditFTLBox.FTL_CREDIT_PUB_RBLIST_DIVINDEX;
    }
}
