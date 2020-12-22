package com.credit.CreditPublicity.controller;

import com.credit.FTLBox.NPTCreditFTLBox;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.web.util.CmsUtils;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.dataBinder.NptWebFieldDataArray;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.querier.impl.NptCreditTravelQuerierBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/4/13
 * 备注: 信用旅游
 */
@Controller
@RequestMapping(value = "/pub/travel")
public class NptCreditTravelController {

    @Autowired
    private NptCreditTravelQuerierBase querier;

    /**
     * 作者: 张磊
     * 日期: 2017/04/13 上午11:13
     * 备注: 信用旅游（企业）
     */
    @RequestMapping(value = "/index.do", method = RequestMethod.GET)
    public String index(NptWebBridgeBean bean, int flag, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        list(bean, flag, request, response, modelMap);
        return NPTCreditFTLBox.FTL_CREDIT_TRAVEL_INDEX;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/13 上午11:13
     * 备注: 信用旅游（个人）
     */
    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    public String list(NptWebBridgeBean bean, int flag, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        modelMap.put("flag", flag);
        NptDict gCode;
        if (flag == NptDict.BMHG_TRAVEL_BS.getCode()) {
            gCode = NptDict.BMHG_TRAVEL_BS;
        } else if (flag == NptDict.BMHG_TRAVEL_PS.getCode()) {
            gCode = NptDict.BMHG_TRAVEL_PS;
        } else {
            gCode = NptDict.BMHG_TRAVEL_AG;
        }
        Collection<NptBaseModelPool> pools = querier.getBaseModelGroupPools(gCode);
        if (pools.size() > 0) {
            NptDict result = querier.getBaseModelPoolPaginationData(pools.iterator().next().getId(), bean, true, false);
            modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
        }
        return NPTCreditFTLBox.FTL_CREDIT_TRAVEL_LIST;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/13 上午11:28
     * 备注: 数据详情方法
     */
    @RequestMapping(value = "/detail.do", method = RequestMethod.POST)
    public String detail(NptWebBridgeBean bean, int flag, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        modelMap.put("user", user);
        modelMap.put("flag", flag);
        NptDict gCode;
        if (flag == NptDict.BMHG_TRAVEL_BS.getCode()) {
            gCode = NptDict.BMHG_TRAVEL_BS;
        } else if (flag == NptDict.BMHG_TRAVEL_PS.getCode()) {
            gCode = NptDict.BMHG_TRAVEL_PS;
        } else {
            gCode = NptDict.BMHG_TRAVEL_AG;
        }
        Collection<NptBaseModelPool> pools = querier.getBaseModelGroupPools(gCode);
        if (pools.size() > 0) {
            NptWebFieldDataArray poolData = querier.getBaseModelPoolRowData(pools.iterator().next().getId(), bean.getPrimaryKeyValue());
            modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), poolData);
        }

        return NPTCreditFTLBox.FTL_CREDIT_TRAVEL_DETAIL;
    }
}
