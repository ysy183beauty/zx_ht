package com.credit.FTLBox;

/**
 * 作者：owen
 * 创建时间：2017/3/8 下午2:20
 * 描述：
 */
public final class NPTCreditFTLBox {

    /**
     * 作者:owen
     * 时间:2017/3/8 下午2:21
     * 描述:
     *      信用公示FTL模板的名称
     */
    public static final String FTL_CREDIT_PUBLICITY_INDEX = "CREDIT_PUBLICITY_INDEX";

    /**
     * 作者:owen
     * 时间:2017/3/9 上午11:32
     * 描述:
     *      双公示首页模板
     */
    public static final String FTL_CREDIT_2PUB_INDEX = "CREDIT_PUB_2PUB_INDEX";
    /**
     * 作者:owen
     * 时间:2017/3/9 上午11:32
     * 描述:
     *      首页行政许可模板
     */
    public static final String FTL_CREDIT_INDEX_AL_INDEX= "CREDIT_INDEX_AL_INDEX";
    /**
     * 作者:owen
     * 时间:2017/3/9 上午11:32
     * 描述:
     *      首页行政处罚模板
     */
    public static final String FTL_CREDIT_INDEX_AP_INDEX= "CREDIT_INDEX_AP_INDEX";
    /**
     * 作者: owen
     * 时间: 2017/3/10 下午4:24
     * 描述:
     *      双公示数据详情模板
     */
    public static final String FTL_CREDIT_PUB_2PUB_DETAIL = "CREDIT_PUB_2PUB_DETAIL";

    /**
     * 作者: owen
     * 时间: 2017/3/10 下午4:25
     * 描述:
     *      双公示职权信息模板
     */
    public static final String FTL_CREDIT_PUB_2PUB_PROVINCE = "CREDIT_PUB_2PUB_PROVINCE";

    /**
     * 作者: owen
     * 时间: 2017/3/10 下午4:26
     * 描述:
     *      公示信息典型事例首页模板
     */
    public static final String FTL_CREDIT_PUB_TE_INDEX = "CREDIT_PUB_TE_INDEX";

    /**
     * 作者: owen
     * 时间: 2017/3/10 下午4:27
     * 描述:
     *      公示信息典型事例详情模板
     */
    public static final String FTL_CREDIT_PUB_TE_DETAIL = "CREDIT_PUB_TE_DETAIL";

    /**
     * 作者: owen
     * 时间: 2017/3/10 下午6:09
     * 描述:
     *      红黑榜首页模板
     */
    public static final String FTL_CREDIT_PUB_RBLIST_INDEX ="CREDIT_PUB_RBLIST_INDEX";

    public static final String FTL_CREDIT_PUB_RBLIST_DETAIL ="CREDIT_PUB_RBLIST_DETAIL";

    /**
     * 作者: owen
     * 时间: 2017/3/12 下午2:55
     * 描述:
     *      用于在首页显示的查询
     */
    public static final String FTL_CREDIT_SEARCH_BUS_DETAIL= "CREDIT_SEARCH_BUS_DETAIL";
    public static final String FTL_CREDIT_SEARCH_KC_DETAIL= "CREDIT_SEARCH_KC_DETAIL";
    /**
     * 作者: owen
     * 时间: 2017/3/12 下午2:55
     * 描述:
     *      用于在首页显示的红黑榜
     */
    public static final String FTL_CREDIT_PUB_RBLIST_DIVINDEX = "CREDIT_PUB_RBLIST_DIVINDEX";
    public static final String FTL_CREDIT_PUB_RBLIST_DIVLIST = "CREDIT_PUB_RBLIST_DIVLIST";


    /**
     * 作者: owen
     * 时间: 2017/3/10 下午6:12
     * 描述:
     *      统一信用代码首页
     */
    public static final String FTL_CREDIT_PUB_UCC_INDEX = "CREDIT_PUB_UCC_INDEX";

    /**
     * 作者: owen
     * 时间: 2017/3/12 下午4:52
     * 描述:
     *      在首页显示的统一信用代码
     */
    public static final String FTL_CREDIT_PUB_UCC_DIVINDEX = "CREDIT_PUB_UCC_DIVINDEX";

    /**
     * 作者: owen
     * 时间: 2017/3/10 下午6:18
     * 描述:
     *      职责清单
     */
    public static final String FTL_CREDIT_PUB_DL_INDEX = "CREDIT_PUBLICITY_DL_INDEX";


    /**
     * 作者:owen
     * 时间:2017/3/8 下午2:22
     * 描述:
     *      信用查询FTL模板名称
     */
    public static final String FTL_CREDIT_QUERY_INDEX = "CREDIT_QUERY_INDEX";

    /**
     * 作者: owen
     * 时间: 2017/3/13 上午10:33
     * 描述:
     *      企业信用查询首页模板
     */
    public static final String FTL_CREDIT_QUERY_BS_INDEX = "CREDIT_QUERY_BS_INDEX";

    /**
     * 作者: owen
     * 时间: 2017/3/13 下午7:53
     * 描述:
     *      企业查询详情模板
     */
    public static final String FTL_CREDIT_QUERY_BS_DETAIL_DEFAULT = "CREDIT_QUERY_BS_DETAIL_DEFAULT";
    public static final String FTL_CREDIT_QUERY_BS_DETAUL_LOGIN = "CREDIT_QUERY_BS_DETAIL_LOGIN";

    /**
     * 作者: owen
     * 时间: 2017/3/13 上午10:34
     * 描述:
     *      个人信用查询
     *
     *      用户自身信用查询的页面分上下两部分：
     *              上半部分为INDEX，为一些帐户相关的信息
     *              下半部分为详细数据或提示信息，规则如下：
     *                      当用户已通过实名认证且已申请调用数据之后，由DETAIL页面负责显示详细的个人信用信息
     *                      当用户未通过实名认证时，显示UNREALNAME页面并引导用户进行实名认证
     *                      当用户已通过实名认证但未曾申请过个人信用数据，则显示APPLYINFO页面并引导用户进行个人信用数据的申请
     *
     */
    public static final String FTL_CREDIT_QUERY_PS_IDNEX = "CREDIT_QUERY_PS_INDEX";
    public static final String FTL_CREDIT_QUERY_PS_DETAIL = "CREDIT_QUERY_PS_DETAIL";
    public static final String FTL_CREDIT_QEURY_PS_UNREALNAME = "CREDIT_QEURY_PS_UNREALNAME";
    public static final String FTL_CREDIT_QEURY_PS_APPLYINFO = "CREDIT_QEURY_PS_APPLYINFO";

    /**
     *  author: owen
     *  date:   2017/3/21 下午9:56
     *  note:
     *          基于模型的查询过程中若因模型自身的原因查询失败，返回该页面提示用户相关服务暂未开放
     */
    public static final String FTL_CREDIT_MODEL_NOT_IN_SERVICE = "CREDIT_MODEL_NOT_IN_SERVICE";


    /**
     * 作者: owen
     * 时间: 2017/3/13 上午10:35
     * 描述:
     *      重点人群信用查询首页模板
     */
    public static final String FTL_CREDIT_QUERY_KC_INDEX = "CREDIT_QUERY_KC_INDEX";

    /**
     * 作者: owen
     * 时间: 2017/3/13 下午7:55
     * 描述:
     *      重点人群查询详情模板
     */
    public static final String FTL_CREDIT_QUERY_KC_DETAIL = "CREDIT_QUERY_KC_DETAIL";

    /**
     * 作者:owen
     * 时间:2017/3/8 下午2:25
     * 描述:
     *      信用服务FTL模板名称
     */
    public static final String FTL_CREDIT_SERVICE_INDEX = "CREDIT_SERVICE_INDEX";

    /**
     * 用户管理页面
     */
    public static final String FTL_CREDIT_SYSTEM_USER_LIST = "CREDIT_SYSTEM_USER_LIST";

    /**
     * 作者: 张磊
     * 日期: 2017/03/09 下午07:28
     * 备注: 公示详情
     */
    public static final String FTL_CREDIT_SERVICE_OBJECTION = "CREDIT_SERVICE_OBJECTION";
    public static final String FTL_CREDIT_SERVICE_ADVICE = "CREDIT_SERVICE_ADVICE";
    public static final String FTL_CREDIT_SERVICE_MESSAGE = "CREDIT_SERVICE_MESSAGE";
    public static final String FTL_CREDIT_SERVICE_ORGANIZATION = "CREDIT_SERVICE_ORGANIZATION";
    public static final String FTL_CREDIT_SERVICE_OBJECTION_LIST = "CREDIT_SERVICE_OBJECTION_LIST";
    public static final String FTL_CREDIT_SERVICE_OBJECTION_DETAIL = "CREDIT_SERVICE_OBJECTION_DETAIL";
    public static final String FTL_CREDIT_INDEX_SRV_INDEX = "CREDIT_INDEX_SRV_INDEX";
    public static final String FTL_CREDIT_SERVICE_ADVICE_LIST = "CREDIT_SERVICE_ADVICE_LIST";
    public static final String FTL_CREDIT_PUB_2PUB_LIST = "CREDIT_PUB_2PUB_LIST";
    public static final String FTL_CREDIT_PUB_RBLIST_LIST = "CREDIT_PUB_RBLIST_LIST";
    public static final String FTL_CREDIT_PUB_UCC_LIST = "CREDIT_PUB_UCC_LIST";
    public static final String FTL_CREDIT_PUB_TE_LIST = "CREDIT_PUB_TE_LIST";
    public static final String FTL_CREDIT_PUB_DL_LIST = "CREDIT_PUB_DL_LIST";
    public static final String FTL_CREDIT_QUERY_BS_LIST = "CREDIT_QUERY_BS_LIST";
    public static final String FTL_CREDIT_QUERY_BS_INDEX_M = "CREDIT_QUERY_BS_INDEX_M";
    public static final String FTL_CREDIT_QUERY_BS_DETAIL = "CREDIT_QUERY_BS_DETAIL";
    public static final String FTL_CREDIT_QUERY_SEARCH_BS_LIST = "CREDIT_QUERY_SEARCH_BS_LIST";
    public static final String FTL_CREDIT_QUERY_KC_LIST = "CREDIT_QUERY_KC_LIST";
    public static final String FTL_CREDIT_QUERY_SEARCH_KC_LIST = "CREDIT_QUERY_SEARCH_KC_LIST";

    public static final String FTL_CREDIT_TRAVEL_DETAIL = "CREDIT_TRAVEL_DETAIL";
    public static final String FTL_CREDIT_TRAVEL_INDEX = "CREDIT_TRAVEL_INDEX";
    public static final String FTL_CREDIT_TRAVEL_LIST = "CREDIT_TRAVEL_LIST";
    public static final String FTL_APPEAL_HANDLE_APPLY = "CREDIT_APPEAL_HANDLE_APPLY";
    public static final String FTL_CREDIT_SERVICE_PROMISE_ADD = "CREDIT_SERVICE_PROMISE_ADD";
    public static final String FTL_CREDIT_SERVICE_PROMISE_LIST = "CREDIT_SERVICE_PROMISE_LIST";
    public static final String FTL_CREDIT_QUERY_PS_REPORT = "CREDIT_QUERY_PS_REPORT";
    public static final String FTL_CREDIT_QUERY_PS_REPORT_VALID = "CREDIT_QUERY_PS_REPORT_VALID";
    public static final String FTL_CREDIT_QUERY_KC_LIST_WX = "CREDIT_QUERY_KC_LIST_WX";
    public static final String FTL_CREDIT_QUERY_KC_INDEX_WX = "CREDIT_QUERY_KC_INDEX_WX";
    public static final String FTL_CREDIT_QUERY_KC_DETAIL_WX = "CREDIT_QUERY_KC_DETAIL_WX";

    public static final String FTL_CREDIT_QUERY_BS_INDEX_WX = "CREDIT_QUERY_BS_INDEX_WX";
    public static final String FTL_CREDIT_QUERY_BS_LIST_WX = "CREDIT_QUERY_BS_LIST_WX";
    public static final String FTL_CREDIT_QUERY_BS_DETAIL_WX = "CREDIT_QUERY_BS_DETAIL_WX";
    public static final String FTL_CREDIT_2PUB_INDEX_WX = "CREDIT_PUB_2PUB_INDEX_WX";
    public static final String FTL_CREDIT_PUB_2PUB_LIST_WX = "CREDIT_PUB_2PUB_LIST_WX";
    public static final String FTL_CREDIT_PUB_2PUB_DETAIL_WX = "CREDIT_PUB_2PUB_DETAIL_WX";
    public static final String FTL_CREDIT_PUB_RBLIST_INDEX_WX = "CREDIT_PUB_RBLIST_INDEX_WX";
    public static final String FTL_CREDIT_PUB_RBLIST_LIST_WX = "CREDIT_PUB_RBLIST_LIST_WX";
    public static final String FTL_CREDIT_PUB_RBLIST_DETAIL_WX = "CREDIT_PUB_RBLIST_DETAIL_WX";
    public static final String FTL_CREDIT_PUB_UCC_INDEX_WX = "CREDIT_PUB_UCC_INDEX_WX";
}
