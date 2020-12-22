package com.credit.CreditPublicity.controller;

import com.credit.FTLBox.NPTCreditFTLBox;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.web.util.CmsUtils;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.dataBinder.NptWebFieldDataArray;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.querier.impl.NptCreditGcjsQuerierBase;
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
 * 备注: 工程建设
 */
@Controller
@RequestMapping(value = "/pub/gcjs")
public class NptCreditGcjsController {

    @Autowired
    private NptCreditGcjsQuerierBase querier;

    /**
     * 作者: 张磊
     * 日期: 2017/04/13 上午11:13
     * 备注:
     */
    @RequestMapping(value = "/index.do", method = RequestMethod.GET)
    public String index(NptWebBridgeBean bean, int flag, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        modelMap.put("user", user);
        NptBaseModel model = querier.getThisModel();
        if (model == null) {
            return NPTCreditFTLBox.FTL_CREDIT_MODEL_NOT_FOUND;
        }
        Collection<NptBaseModelGroup> groups = querier.getBaseModelGroups(model);
        modelMap.put("groups", groups);
        list(bean, flag, request, response, modelMap);
        return NPTCreditFTLBox.FTL_CREDIT_SPAQ_INDEX;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/13 上午11:13
     * 备注:
     */
    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    public String list(NptWebBridgeBean bean, int flag, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        modelMap.put("flag", flag);
        NptBaseModel model = querier.getThisModel();
        if (model == null) {
            return NPTCreditFTLBox.FTL_CREDIT_MODEL_NOT_FOUND;
        }
        Collection<NptBaseModelPool> pools = querier.getBaseModelPools(model);
        if (pools.size() > 0) {
            NptBaseModelPool pool = pools.iterator().next();
            NptDict result = querier.getBaseModelPoolPaginationData(pool.getId(), bean, true, false);
            modelMap.put("pool", pool);
            modelMap.put("_RESULT", result);
            modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
        }

        return NPTCreditFTLBox.FTL_CREDIT_SPAQ_LIST;

    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/13 上午11:28
     * 备注: 数据详情方法
     */
    @RequestMapping(value = "/detail.do", method = RequestMethod.GET)
    public String detail(NptWebBridgeBean bean, Long poolId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        modelMap.put("user", user);
        NptWebFieldDataArray poolData = querier.getBaseModelPoolRowData(poolId, bean.getPrimaryKeyValue());
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), poolData);
        return NPTCreditFTLBox.FTL_CREDIT_SPAQ_DETAIL;
    }
}
