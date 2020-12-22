package com.credit.CreditPublicity.controller;

import com.credit.FTLBox.NPTCreditFTLBox;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.web.util.CmsUtils;
import com.npt.bridge.model.NptBaseModelStatistic;
import com.npt.querier.impl.Npt2PublicityQuerierBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 作者：owen
 * 创建时间：2017/3/8 下午2:14
 * 描述：
 *
 *      信用公示导航菜单入口类
 */
@Controller
@RequestMapping(value = "/pub")
public class NptCreditPublicityIndexController {

    private static final Logger logger = LoggerFactory.getLogger(NptCreditPublicityIndexController.class);

    @Autowired
    private Npt2PublicityQuerierBase querier;

    /**
     * 作者:owen
     * 时间:2017/3/8 下午2:16
     * 描述:
     *      信用公示导航菜单入口方法
     *
     *      在此方法中需要获取首次进入信用公示页面时所需的数据
     *
     *      并将此数据以属性的形式放置到modelMap中，以freemarker去解析
     */
    @RequestMapping(value = "/index.do",method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){

        CmsUser user = CmsUtils.getUser(request);
        modelMap.put("user", user);

        NptBaseModelStatistic ms = querier.loadBaseModelStatitic(querier.getThisModel());
        modelMap.put("_MS", ms);
        return NPTCreditFTLBox.FTL_CREDIT_PUBLICITY_INDEX;
    }

}
