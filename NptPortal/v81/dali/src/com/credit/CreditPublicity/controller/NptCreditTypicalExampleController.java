package com.credit.CreditPublicity.controller;

import com.credit.FTLBox.NPTCreditFTLBox;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.dataBinder.NptWebDetailBlock;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.querier.impl.NptTypicalExampleQuerierBase;
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
 *          典型事例
 */
@Controller
@RequestMapping(value = "/pub/te")
public class NptCreditTypicalExampleController {

    @Autowired
    private NptTypicalExampleQuerierBase querier;

    /**
     * 作者: owen
     * 时间: 2017/3/10 下午6:08
     * 描述:
     *      典型事例首页
     */
    @RequestMapping(value = "/index.do",method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        NptBaseModel model = querier.getThisModel();
        modelMap.put("_MODEL", model);
        return NPTCreditFTLBox.FTL_CREDIT_PUB_TE_INDEX;
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
        return NPTCreditFTLBox.FTL_CREDIT_PUB_TE_LIST;
    }


    /**
     * 作者: bw
     * 时间: 2017/3/13 上午10:44
     * 描述:
     *      典型事例详情
     */
    @RequestMapping(value = "/detail.do",method = RequestMethod.GET)
    public String detail(NptWebBridgeBean bean, Long poolId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        NptWebDetailBlock poolData = querier.getBaseModelGrouPoolData(poolId, bean.getPrimaryKeyValue(), true,false);
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), poolData);
        return NPTCreditFTLBox.FTL_CREDIT_PUB_TE_DETAIL;
    }
}
