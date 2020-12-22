package com.npt.bridge.dict;


import com.npt.bridge.util.NptCommonUtil;

/**
 * 作者：owen
 * 时间：2016/11/9 12:25
 * 描述:
 */
public enum NptDict {
    //系统处理结果标识
    RST_SUCCESS(0,"处理成功"),
    RST_ERROR(1,"处理失败"),
    RST_DUPLICATE_OPERATION(2,"请勿重复操作"),
    RST_INVALID_PARAMS(3,"非法的参数"),
    RST_UNKNOW(5,"未知"),
    RST_INVALIDE_STATUS(6,"目标当前状态异常"),
    RST_NOT_PARENT_SON(7,"非合法的父子关系"),
    RST_NOTALLOWED(8,"不被允许的操作"),
    RST_NOTVERIFIED(9, "未实名认证"),
    RST_EXCEPTION(-1,""),
    RST_TEST_SUCCESS(1,"测试成功"),

    //信息数据状态
    IDS_DISABLED(0,"禁用"),
    IDS_ENABLED(1,"启用"),
    IDS_LOCKED(2,"锁定"),
    IDS_DELETED(3,"废弃"),

    //资源申请状态
    RAS_WAITTING(0,"待受理"),
    RAS_PROCESSING(1,"受理中"),//冻结
    RAS_ACCEPTED(2,"已批准"),//办结
    RAS_REFUSED(3,"已拒绝"),
    RAS_EXPIRED(4,"已过期"),

    //资源申请的单位
    RAT_DATA_FIELD(0,"数据字段申请"),
    RAT_DATA_TYPE(1,"数据类别申请"),

    //SQL构建标题类型
    CST_ONLY_ENG(0,"只数据库英文字段名"),
    CST_ONLY_CHN(1,"只字段中文业务名"),
    CST_ENG_AS_CHN(2,"英文字段名中文业务名同时存在"),

    //字段显示类型
    FSS_COMMON_TEXT(0,"普通文本"),
    FSS_PARTHIDE_TEXT(1,"部分显示"),
    FSS_DATE(2,"日期类型"),
    FSS_CODE(3,"码表转换"),
    FSS_IMG_DATE(4,"图片数据类型"),
    FSS_IMG_PATH(5,"图片路径类型"),

    //字段访问级别
    FAL_AUTH(0,"授权访问"),
    FAL_GOPEN(1,"政务公开"),
    FAL_SOPEN(2,"社会公开"),
    /**
     * 权限列表,目前的权限分为两种，一类是功能菜单权限，由summer框架负责配置
     * 一类是数据权限，数据权限的定义是：
     * 针对每个数据类别需要授权才可查看的字段值，若配上“数据直视权限”，则不需要通过资源申请亦可查看字段值.
     * 若没有这个权限,凡是要看授权字段的值,必须走资源申请
     */
    PMS_DATA_PENETRATION(0,"数据直视权限"),

    //日志业务域
    LGB_APP(0,"系统域日志"),
    LGB_GRS(1,"模型域日志"),
    LGB_SYNC(2, "同步日志"),

    //日志行为
    LGA_LOGIN(0,"用户登录"),
    LGA_LOGOUT(1,"用户登出"),
    LGA_OPEN_INDEX(2,"模型开放列表查询"),
    LGA_OPEN_INDEX_NEXT(3,"模型开放列表分页查询"),
    LGA_AUTH_INDEX(4,"模型组详情查询"),
    LGA_AUTH_BLOCK_LASTED(5,"模型块首条详情查询"),
    LGA_AUTH_BLOCK_MORE(6,"模型块更多详情查询"),
    LGA_AUTH_ADDITIONAL_POOL(7,"模型组下拉块详情查询"),
    LGA_AUTH_POOL_LINKED(8,"模型块跨模型详情查询"),
    LGA_SYNC_DEPENDENCY(9,"同步基础"),
    LGA_SYNC_STRUCTURE(10,"同步模型"),
    LGA_SYNC_INCDATA(11,"同步行数据"),
    LGA_SYNC_CREDITSERVICE(12,"同步信用服务"),
    LGA_SYNC_CREDITSERVICE_OK(13, "同步信用服务返回OK"),
    LGA_SYNC_CREDITSERVICE_REP(14, "同步信用服务回复"),
    LGA_SYNC_CMSUSER(15, "同步cms用户"),
    LGA_SYNC_CMSUSER_OK(16, "同步cms用户返回OK"),
    LGA_SYNC_CMSUSER_REP(17, "同步cms用户回复"),
    LGA_SYNC_APPLY(18, "同步申请"),
    LGA_SYNC_APPLY_OK(19, "同步申请返回OK"),
    LGA_SYNC_APPLY_REP(20, "同步申请回复"),
    LGA_OPEN_POOL_INDEX(21,"模型数据池开放列表查询"),
    LGA_AUTH_POOL_DETAIL(22,"模型数据池列表详情查询"),
    LAG_AUTH_RN(23,"实名认证查询"),

    //基础模型实体
    BMH_BUSINESS(0,"企业信息"),
    BMH_PERSONAL(1,"个人信息"),
    BMH_NONLEGAL(2,"非企业法人"),

    BMH_FINANCIAL(4,"金融行业"),
    BMH_HEALTH(5,"医疗卫生"),
    BMH_ENVIRONMENT(6,"环境保护"),

    BMH_BMDS(7,"图谱辅助"),
    BMH_BSMAP(8,"企业图谱"),
    BMH_PSMAP(9,"个人图谱"),

    BMH_BSCARD(10,"企业信用名片"),
    BMH_PSCARD(11,"个人信用名片"),

    //双公示
    BMH_2PUBAP(12,"行政处罚"),
    BMH_2PUBAL(13,"行政许可"),

    BMH_GCJS(14,"工程建设"),

    BMH_WARN_BS(15,"企业信用预警"),
    BMH_WARN_PS(16,"个人信用预警"),

    BMH_BLACKRED(NptCommonUtil.BMH_SPECIAL_MIN + 1,"红黑榜"),
    BMHG_BLACKRED_BLACK(BMH_BLACKRED.code * 10 + 1,"黑榜"),
    BMHG_BLACKRED_RED(BMH_BLACKRED.code * 10 + 2,"红榜"),


    BMH_2PUB(NptCommonUtil.BMH_SPECIAL_MIN + 2,"双公示"),
    BMHG_2PUB_AP(BMH_2PUB.code * 10 + 1,"行政处罚"),
    BMHG_2PUB_AL(BMH_2PUB.code * 10 + 2,"行政许可"),

    BMH_UCC(NptCommonUtil.BMH_SPECIAL_MIN + 3,"社会统一信用代码公示"),
    BMHG_UCC_NEW(BMH_UCC.code * 10 + 1,"新赋码公示"),
    BMHG_UCC_OLD(BMH_UCC.code * 10 + 2,"旧码转换公示"),

    BMH_TE(NptCommonUtil.BMH_SPECIAL_MIN + 4,"典型事例"),
    BMHG_TE_PUB(BMH_TE.code * 10 + 1,"典型事例"),

    BMH_DL(NptCommonUtil.BMH_SPECIAL_MIN + 5,"责任清单"),
    BMHG_DL_PUB(BMH_TE.code * 10 + 1,"责任清单"),

    BMH_IMPERSON(NptCommonUtil.BMH_SPECIAL_MIN + 6,"重点人群"),
    BMHG_IMPERSON_PRO(BMH_IMPERSON.code * 10 + 1,"职业角度"),
    BMHG_IMPERSON_IND(BMH_IMPERSON.code * 10 + 2,"行业角度"),

    BMH_RNAUTH(NptCommonUtil.BMH_SPECIAL_MIN + 7,"实名认证"),
    BMHG_RNAUTH_BS(BMH_RNAUTH.code * 10 + 1,"企业认证"),
    BMHG_RNAUTH_PS(BMH_RNAUTH.code * 10 + 2,"个人认证"),

    BMH_TRAVEL(NptCommonUtil.BMH_SPECIAL_MIN + 8, "信用旅游"),
    BMHG_TRAVEL_BS(BMH_TRAVEL.code * 10 + 1, "信用旅游企业"),
    BMHG_TRAVEL_PS(BMH_TRAVEL.code * 10 + 2, "信用旅游个人"),
    BMHG_TRAVEL_AG(BMH_TRAVEL.code * 10 + 3, "信用旅游旅行社"),

    BMH_SPAQ(NptCommonUtil.BMH_SPECIAL_MIN + 9, "食品安全"),
    BMHG_SPAQ_AQDJ(BMH_SPAQ.code * 10 + 1, "安全等级"),
    BMHG_SPAQ_QYHMD(BMH_SPAQ.code * 10 + 2, "企业黑名单"),
    BMHG_SPAQ_ZRXKZ(BMH_SPAQ.code * 10 + 3, "准入许可证"),

    BMH_BZXRXYJG(NptCommonUtil.BMH_SPECIAL_MIN + 10, "被执行人信用监管"),
    BMHG_BZXRXYJG_BZXR(BMH_BZXRXYJG.code * 10 + 1, "被执行人"),
    BMHG_BZXRXYJG_SXBZXR(BMH_BZXRXYJG.code * 10 + 2, "失信被执行人"),

    //基础模型系统类别
    BMC_NATIVE(0,"系统原生模型"),
    BMC_CUSTOM(1,"数据专题模型"),
    BMC_OUTSIDE(2,"外部查询模型"),

    //子结点类别
    CLD_MAIN(0,"主子结点"),
    CLD_ADDITION(1,"附加结点"),

    //主字段查询条件类型
    SCT_NONE(0,"非查询条件"),
    SCT_TEXTBOX(1,"文本框"),
    SCT_DROPBOX(2,"下拉框"),

    //远程系统操作类型
    RMT_SYNC_DEPENDENCY(0,"同步基础"),
    RMT_SYNC_STRUCTURE(1,"同步模型"),
    RMT_SYNC_INCDATA(2,"同步行数据"),
    RMT_SYNC_CREDITSERVICE(3,"同步信用服务"),
    RMT_SYNC_CREDITSERVICE_OK(4, "同步信用服务返回OK"),
    RMT_SYNC_CREDITSERVICE_REP(5, "同步信用服务回复"),
    RMT_SYNC_CMSUSER(6, "同步cms用户"),
    RMT_SYNC_CMSUSER_OK(7, "同步cms用户返回OK"),
    RMT_SYNC_CMSUSER_REP(8, "同步cms用户回复"),
    RMT_SYNC_APPLY(9, "同步申请"),
    RMT_SYNC_APPLY_OK(10, "同步申请返回OK"),
    RMT_SYNC_APPLY_REP(11, "同步申请回复"),
    RMT_SYNC_TEST_REP(12, "测试同步回复"),

    //实体数据的LAST_MODIFY_TIME的用途
    DUB_OUT_SYNC(0,"对外同步"),

    //报表类型
    RPC_REPORT(0,"信用报告"),
    RPC_STATISTIC(1,"信用报表"),
    RPC_DECLARE(2,"信用申报"),

    //报表主体
    RPH_BUSINESS(0,"企业"),
    RPH_PERSONAL(1,"个人"),

    //模型数据池的加锁级别
    MPL_LEVEL_0(0,"无限制"),
    MPL_LEVEL_1(1,"一级锁"),
    MPL_LEVEL_2(2,"二级锁"),
    MPL_LEVEL_3(3,"三级锁"),

    MLR_NORMAL(0,"普通链接"),
    MLR_RELATION(1,"关系链接"),

    //报表访问模式
    RPP_ANONYMOUS(0,"匿名访问"),
    RPP_LOGIN(1,"登录访问"),
    RPP_AUTH(2,"授权访问"),

    // 展示方式
    RPS_NORMAL(0,"普通页面显示"),
    RPS_MENU(1,"菜单独立显示"),

    //同步状态
    RCS_NOTSYNED(0, "未同步"),
    RCS_SYNED(1, "已同步"),
    RCS_NEEDSYNC(2, "需要同步"),
    RCS_FAILSYNC(3, "同步失败"),

    //认证状态
//    USER_AUTH_NOT(1, "未认证"),
//    USER_AUTH_ING(2, "认证中"),
    USER_AUTH_OK(3, "认证成功"),
    USER_AUTH_NG(4, "认证失败"),

    //用户申请
    USER_APPLY_SUBMIT(1,"申请提交"),
    USER_APPLY_PROCESSING(2,"申请处理中"),
    USER_APPLY_SUCCESS(3,"申请成功"),
    USER_APPLY_FAILURE(4,"申请失败"),
    USER_APPLY_OUTDATE(5,"申请已过期"),

    //信用服务类型
    CSF_OBJECTION(0, "异议处理"),
    CSF_COMPLAIN(1, "信用投诉"),
    CSF_ADVICE(3, "信用咨询"),
    CSF_SELF(4, "自身异议"),

    //每月统计
    STS_MONTH_DATA(11, "每月统计数据总量"),
    STS_MONTH_CREDIT_USER(12, "每月统计实名用户总量"),

    //来源
    SOURCE_NPT(0, "平台"),
    SOURCE_CMS(1, "门户"),

    //荣誉\失信
    STS_POS(0, "良好信息"),
    STS_NEG(1, "不良信息"),

    //数据池数据主类别
    PDM_UNKNOW(0,"未知"),
    PDM_ENTERPRISE(1,"企业"),
    PDM_PERSONAL(2,"自然人"),

    //信用名片
    CARD_NOTFOLLOW(0, "未关注"),
    CARD_FOLLOWING(1, "已关注"),
    CARD_DUPFOLLOW(2, "互相关注"),

    //排序
    ORDER_SORT(0, "时间排序"),
    ORDER_FILTER(1, "时间筛选"),




    //=============信用评估系统专用
    CES_M_WARN(0,"信用预警"),
    CES_M_RATE(1,"信用评级"),


    CES_DMS_BUSINESS(0,"行业维度"),
    CES_DMS_REGION(1,"地域维度"),
    CES_DMS_DYNAMIC(99,"动态维度"),

    CES_CPM_DX_COMPUTE(0,"区间计算"),


    CES_MU_YUAN(1,"元"),
    CES_MU_BAIYUAN(100,"百元"),
    CES_MU_QIANYUAN(1000,"千元"),
    CES_MU_WANYUAN(10000,"万元"),
    CES_MU_YIYUAN(100000000,"亿元")



    //=============信用评估系统专用













    ;

    private int code;
    private String title;

    NptDict(int _code, String _title){
        this.code = _code;
        this.title = _title;
    }

    public int getCode(){
        return this.code;
    }
    public String getTitle(){
        return this.title;
    }

    public static NptDict RST_EXCEPTION(String str){
        NptDict result = NptDict.RST_EXCEPTION;
        result.title = str;
        return result;
    }

    public static NptDict RST_SUCCESS(String str){
        NptDict result = NptDict.RST_SUCCESS;
        result.title = str;
        return result;
    }

    public NptDictBean castToBean(){
        NptDictBean db = new NptDictBean();
        db.setCode(this.getCode());
        db.setName(this.name());
        db.setTitle(this.getTitle());
        return db;
    }
}
