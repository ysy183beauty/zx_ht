package com.credit.CreditPublicity.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.credit.CreditPublicity.entity.QzqdEntity;
import com.credit.CreditPublicity.service.NptCredit2PublicityService;
import com.credit.FTLBox.NPTCreditFTLBox;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.entity.CmsUserExt;
import com.jeecms.core.manager.CmsUserExtMng;
import com.jeecms.core.web.util.CmsUtils;
import com.npt.bridge.busiTag.NptFixedBusinessTags;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.dataBinder.NptWebFieldDataArray;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.querier.impl.Npt2PublicityQuerierBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 项目： zxcms
 * 作者： owen
 * 时间： 2017/3/2 14:03
 * 描述：
 *
 *      双公示控制器
 *
 *      基于NPT MODEL进行查询
 *
 *      AL：行政许可
 *      AP：行政处罚
 */
@Controller
@RequestMapping(value = "/pub/2pub")
public class NptCredit2PublicityController {

    @Autowired
    private Npt2PublicityQuerierBase querier;
    @Autowired
    private NptCredit2PublicityService nptCredit2PublicityService;
    @Autowired
    private CmsUserExtMng cmsUserExtMng;
    /**
     * 作者:owen
     * 时间:2017/3/9 上午11:33
     * 描述:
     *      行政处罚首页
     */
    @RequestMapping(value = "/{type}/index.do", method = RequestMethod.GET)
    public String apIndex(@PathVariable String type, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        NptBaseModel model = querier.getThisModel(type);
        if (model != null) {
            NptWebBridgeBean webBean = querier.constructOpenWebBridgeBean(model);
            webBean.setOrderType(NptDict.ORDER_FILTER.getCode());
            querier.loadBaseModelOpenList(webBean, model, false);
            modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), webBean);
        }
        modelMap.put("_type", type);
        return NPTCreditFTLBox.FTL_CREDIT_2PUB_INDEX;
    }
    /**
     * 作者:owen
     * 时间:2017/3/9 上午11:33
     * 描述:
     *      首页行政处罚
     */
    @RequestMapping(value = "/apList.do", method = RequestMethod.GET)
    public String apList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        if (user != null) {
            CmsUserExt ext = cmsUserExtMng.findById(user.getId());
            modelMap.put("userExt", ext);
        }
        modelMap.put("user", user);
        Map<String, Collection<NptBaseModelPool>> pools = querier.getBaseModelGroupProviderPoolsMap(NptDict.BMHG_2PUB_AP);
        modelMap.put("_POOLS", pools);
        return NPTCreditFTLBox.FTL_CREDIT_INDEX_AP_INDEX;
    }

    /**
     * 作者：owen
     * 时间：2017/3/16 下午5:48
     * 描述：
     *      行政许可首页
     */
    @RequestMapping(value = "/alIndex.do", method = RequestMethod.GET)

    public String alIndex(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Map<String, Collection<NptBaseModelPool>> pools = querier.getBaseModelGroupProviderPoolsMap(NptDict.BMHG_2PUB_AL);
        modelMap.put("_POOLS", pools);
        return NPTCreditFTLBox.FTL_CREDIT_2PUB_INDEX;
    }
    /**
     * 作者：owen
     * 时间：2017/3/16 下午5:48
     * 描述：
     *      首页行政许可
     */
    @RequestMapping(value = "/alList.do", method = RequestMethod.GET)
    public String alList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        if (user != null) {
            CmsUserExt ext = cmsUserExtMng.findById(user.getId());
            modelMap.put("userExt", ext);
        }
        modelMap.put("user", user);
        Map<String, Collection<NptBaseModelPool>> pools = querier.getBaseModelGroupProviderPoolsMap(NptDict.BMHG_2PUB_AL);
        modelMap.put("_POOLS", pools);
        return NPTCreditFTLBox.FTL_CREDIT_INDEX_AL_INDEX;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/13 下午05:57
     * 备注: 数据列表
     */
    @RequestMapping(value = "/{type}/list.do", method = RequestMethod.GET)
    public String list(@PathVariable String type, String webInvokeCondition, int currPage, int pageSize, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        JSONObject object = JSON.parseObject(webInvokeCondition);
        NptWebBridgeBean bean = JSONObject.toJavaObject(object, NptWebBridgeBean.class);
        bean.setOrderType(NptDict.ORDER_FILTER.getCode());
        bean.setCurrPage(currPage);
        bean.setPageSize(pageSize);
        NptBaseModel model = querier.getThisModel(type);
        NptDict result = querier.loadBaseModelOpenList(bean, model, false);
        modelMap.put("_RESULT", result);
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), bean);
        modelMap.put("_type", type);
        return NPTCreditFTLBox.FTL_CREDIT_PUB_2PUB_LIST;
    }

    /**
     * 作者: owen
     * 时间: 2017/3/10 下午4:29
     * 描述:
     *      双公示数据详情方法
     */
    @RequestMapping(value = "/{type}/detail.do", method = RequestMethod.GET)
    public String detail(@PathVariable String type, NptWebBridgeBean bean, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        modelMap.put("user", user);

        NptBaseModel model = querier.getThisModel(type);
        NptBaseModelPool pool = querier.getBaseModelGroupMainPool(model);
        NptWebFieldDataArray poolData = querier.getBaseModelPoolRowData(pool.getId(), bean.getPrimaryKeyValue());
        modelMap.put(NptCommonUtil.getActionReturnedAttributeName(), poolData);
        modelMap.put("_type", type);
        return NPTCreditFTLBox.FTL_CREDIT_PUB_2PUB_DETAIL;
    }
    
    /**
     * 作者: owen
     * 时间: 2017/3/10 下午4:30
     * 描述:
     *      双公示职权信息方法
     */
    @RequestMapping(value = "/province.do",method = RequestMethod.POST)
    public String province(Long poolId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        CmsUser user = CmsUtils.getUser(request);
        if (user != null) {
            CmsUserExt ext = cmsUserExtMng.findById(user.getId());
            modelMap.put("userExt", ext);
        }
        modelMap.put("user", user);
        QzqdEntity province = nptCredit2PublicityService.getProvince(poolId);
        modelMap.put("_QZQD", province);
        return NPTCreditFTLBox.FTL_CREDIT_PUB_2PUB_PROVINCE;
    }

    /**
     *  author: zhanglei
     *  date:   2017/06/19 10:11
     *  note:
     *          用于在门户首页显示的双公示列表
     */
    @RequestMapping(value = "/divIndex.do", method = RequestMethod.GET)
    public String divIndex(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        NptWebBridgeBean apBean = new NptWebBridgeBean();
        apBean.setOrderType(NptDict.ORDER_FILTER.getCode());
        NptBaseModel apModel = querier.getThisModel("ap");
        if (apModel != null) {
            querier.getBaseModelIndexFieldPaginationData(apModel, apBean, false);
            List<NptWebFieldDataArray> dataList = (List<NptWebFieldDataArray>) apBean.getDataList();
            if (dataList != null) {
                Collection<NptFixedBusinessTags> tags = NptCommonUtil.getNptFixedDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.SGS_XZCF);
                for (NptWebFieldDataArray dataArray : dataList) {
                    Collection<NptWebFieldDataArray.NptWebFieldData> dataArray1 = dataArray.getDataArray();
                    Collection<NptWebFieldDataArray.NptWebFieldData> newDataArray1 = new ArrayList<>();

                    newDataArray1.add(dataArray1.iterator().next());

                    for (NptFixedBusinessTags tag : tags) {
                        for (Iterator<NptWebFieldDataArray.NptWebFieldData> iterator = dataArray1.iterator(); iterator.hasNext(); ) {
                            NptWebFieldDataArray.NptWebFieldData fieldData = iterator.next();
                            if (fieldData.getTitle().equals(tag.getTitle())) {
                                newDataArray1.add(fieldData);
                                break;
                            }
                        }
                    }

                    dataArray.setDataArray(newDataArray1);
                }
            }
        }

        NptWebBridgeBean alBean = new NptWebBridgeBean();
        alBean.setOrderType(NptDict.ORDER_FILTER.getCode());
        NptBaseModel alModel = querier.getThisModel("al");
        if (alModel != null) {
            querier.getBaseModelIndexFieldPaginationData(alModel, alBean, false);
            List<NptWebFieldDataArray> dataList = (List<NptWebFieldDataArray>) alBean.getDataList();
            if (dataList != null) {
                Collection<NptFixedBusinessTags> tags = NptCommonUtil.getNptFixedDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.SGS_XZXK);
                for (NptWebFieldDataArray dataArray : dataList) {
                    Collection<NptWebFieldDataArray.NptWebFieldData> dataArray1 = dataArray.getDataArray();
                    Collection<NptWebFieldDataArray.NptWebFieldData> newDataArray1 = new ArrayList<>();

                    newDataArray1.add(dataArray1.iterator().next());

                    for (NptFixedBusinessTags tag : tags) {
                        for (Iterator<NptWebFieldDataArray.NptWebFieldData> iterator = dataArray1.iterator(); iterator.hasNext(); ) {
                            NptWebFieldDataArray.NptWebFieldData fieldData = iterator.next();
                            if (fieldData.getTitle().equals(tag.getTitle())) {
                                newDataArray1.add(fieldData);
                                break;
                            }
                        }
                    }

                    dataArray.setDataArray(newDataArray1);
                }
            }
        }


        modelMap.put("apBean", apBean);
        modelMap.put("alBean", alBean);

        return NPTCreditFTLBox.FTL_CREDIT_2PUB_DIVINDEX;
    }
}
