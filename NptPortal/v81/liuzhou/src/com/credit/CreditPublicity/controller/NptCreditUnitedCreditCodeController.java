package com.credit.CreditPublicity.controller;

import com.credit.FTLBox.NPTCreditFTLBox;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.dataBinder.NptWebFieldDataArray;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.querier.impl.NptUnitedCreditCodeQuerierBase;
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
 * 时间： 2017/3/2 14:07
 * 描述：
 *
 *      统一信用代码控制器
 */
@Controller
@RequestMapping(value = "/pub/ucc")
public class NptCreditUnitedCreditCodeController {

    @Autowired
    private NptUnitedCreditCodeQuerierBase querier;

    /**
     * 作者: owen
     * 时间: 2017/3/10 下午6:08
     * 描述:
     *      统一信用代码首页
     */
    @RequestMapping(value = "/index.do",method = RequestMethod.GET)
    public String index(NptWebBridgeBean bean,HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){

        NptBaseModel model = querier.getThisModel();
        if (model == null) {
            return NPTCreditFTLBox.FTL_CREDIT_MODEL_NOT_FOUND;
        }
        NptDict result = querier.getBaseModelIndexFieldPaginationData(model, bean,false);
        modelMap.put("_RESULT", result);
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);

        return NPTCreditFTLBox.FTL_CREDIT_PUB_UCC_INDEX;
    }


    /**
     * 作者: owen
     * 时间: 2017/3/12 下午4:53
     * 描述:
     *      用于在门户显示的统一信用代码块
     */
    @RequestMapping(value = "/divIndex.do",method = RequestMethod.GET)
    public String divIndex(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){

        NptWebBridgeBean bean = new NptWebBridgeBean();
        NptBaseModel model = querier.getThisModel();
        if (model == null) {
            return NPTCreditFTLBox.FTL_CREDIT_MODEL_NOT_FOUND;
        }
        NptDict result = querier.getBaseModelIndexFieldPaginationData(model, bean,false);
        modelMap.put("_RESULT", result);
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
        return NPTCreditFTLBox.FTL_CREDIT_PUB_UCC_DIVINDEX;
    }

    @RequestMapping(value = "/detail.do", method = RequestMethod.GET)
    public String detail(String ukValue,HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {

        NptWebFieldDataArray poolData = querier.getBaseModelMainPoolRowData(ukValue);
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), poolData);
        return NPTCreditFTLBox.FTL_CREDIT_PUB_2PUB_DETAIL;
    }
}
