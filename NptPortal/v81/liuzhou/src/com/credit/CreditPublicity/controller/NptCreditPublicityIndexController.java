package com.credit.CreditPublicity.controller;

import com.credit.FTLBox.NPTCreditFTLBox;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.web.util.CmsUtils;
import com.npt.bridge.dict.NptDict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

        NptDict mapLoader = loadPublicityMapData(modelMap);
        if(!NptDict.RST_SUCCESS.equals(mapLoader)){
            logger.info(mapLoader.getTitle());
        }

        NptDict stsLoader = loadPublicityStatisticData(modelMap);
        if(!NptDict.RST_SUCCESS.equals(stsLoader)){
            logger.info(stsLoader.getTitle());
        }

        return NPTCreditFTLBox.FTL_CREDIT_PUBLICITY_INDEX;
    }


    /**
     * 作者: owen
     * 时间: 2017/3/10 下午3:47
     * 描述:
     *      加载公示信息页面的地图数据，以JSON字符串的形式设置到map的属性中
     */
    public static final String PUBLICITY_MAP_JSON = "_PUB_MAP_DATA";
    protected NptDict loadPublicityMapData(ModelMap map){

        //todo 加载信用公示的地图数据
        String mapData = "";

        //将地图数据设置到map中
        map.addAttribute(PUBLICITY_MAP_JSON,mapData);

        return NptDict.RST_SUCCESS;
    }

    /**
     * 作者: owen
     * 时间: 2017/3/10 下午3:51
     * 描述:
     *      加载信用公示的统计数据
     */
    public static final String PUBLICITY_STS_DATA = "_PUB_STS_DATA";
    protected NptDict loadPublicityStatisticData(ModelMap map){

        //todo 加载信用公示的统计数据
        String stsData = "";

        //将统计数据设置到map中
        map.addAttribute(PUBLICITY_STS_DATA,stsData);

        return NptDict.RST_SUCCESS;
    }
}
