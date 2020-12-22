package com.credit.CreditPublicity.controller;

import com.credit.FTLBox.NPTCreditFTLBox;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.querier.impl.NptDutyListQuerierBase;
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
 * 时间： 2017/3/2 14:05
 * 描述：
 *          责任清单
 */
@Controller
@RequestMapping(value = "/pub/dl")
public class NptCreditDutyListController {

    @Autowired
    private NptDutyListQuerierBase querier;

    /**
     * 作者: owen
     * 时间: 2017/3/10 下午6:08
     * 描述:
     *      责任清单首页
     */
    @RequestMapping(value = "/index.do",method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        NptBaseModel model = querier.getThisModel();
        modelMap.put("_MODEL", model);
        return NPTCreditFTLBox.FTL_CREDIT_PUB_DL_INDEX;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/13 下午05:57
     * 备注: 数据列表
     */
    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    public String list(NptWebBridgeBean bean, Long modelId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        NptDict result = querier.getBaseModelIndexFieldPaginationData(modelId, bean,false);
        modelMap.put("_RESULT", result);
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
        return NPTCreditFTLBox.FTL_CREDIT_PUB_DL_LIST;
    }
}
