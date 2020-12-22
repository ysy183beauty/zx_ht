package com.npt.dict;

import com.npt.util.NptUtil;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 11:57
 * 描述:
 */
public enum  NptDict {
    //系统处理结果标识
    RST_SUCCESS(0,"处理成功"),
    RST_ERROR(1,"处理失败"),
    RST_DUPLICATE_OPERATION(2,"请勿重复操作"),
    RST_INVALID_PARAMS(3,"非法的参数"),
    RST_UNKNOW(5,"未知"),
    RST_INVALIDE_STATUS(6,"目标当前状态异常"),
    RST_NOT_PARENT_SON(7,"非合法的父子关系"),
    RST_NOTALLOWED(8,"不被允许的操作"),
    RST_EXCEPTION(-1,""),

    //信息数据状态
    IDS_DISABLED(0,"禁用"),
    IDS_ENABLED(1,"启用"),
    IDS_LOCKED(2,"锁定"),
    IDS_DELETED(3,"废弃"),

    //资源申请状态
    RAS_WAITTING(0,"待受理"),
    RAS_PROCESSING(1,"受理中"),
    RAS_ACCEPTED(2,"已批准"),
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


    //基础模型实体
    BMH_BUSINESS(0,"企业信息"),
    BMH_PERSONAL(1,"个人信息"),
    BMH_NONLEGAL(2,"非法人单位"),
    BMH_IMPERSON(3,"重点人群"),
    BMH_FINANCIAL(4,"金融行业"),
    BMH_HEALTH(5,"医疗卫生"),
    BMH_ENVIRONMENT(6,"环境保护"),


    BMH_BLACKRED(NptUtil.BMH_SPECIAL_MIN + 1,"红黑榜"),
    BMHG_BLACKRED_BLACK(BMH_BLACKRED.code * 10 + 1,"黑榜"),
    BMHG_BLACKRED_RED(BMH_BLACKRED.code * 10 + 2,"红榜"),

    BMH_2PUB(NptUtil.BMH_SPECIAL_MIN + 2,"双公示"),
    BMHG_2PUB_XZCF(BMH_2PUB.code * 10 + 1,"行政处罚"),
    BMHG_2PUB_XZXK(BMH_2PUB.code * 10 + 2,"行政许可"),

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

    //实体数据的LAST_MODIFY_TIME的用途
    DUB_OUT_SYNC(0,"对外同步"),

    //报表类型
    RPC_REPORT(0,"信用报告"),
    RPC_STATISTIC(1,"信用报表"),
    RPC_DECLARE(2,"信用申报"),

    //报表主体
    RPH_BUSINESS(0,"企业"),
    RPH_PERSONAL(1,"个人"),

    //报表访问模式
    RPP_ANONYMOUS(0,"匿名访问"),
    RPP_LOGIN(1,"登录访问"),
    RPP_AUTH(2,"授权访问"),

    // 展示方式
    RPS_NORMAL(0,"普通页面显示"),
    RPS_MENU(1,"菜单独立显示"),
    ;















    private int code;
    private String title;

    NptDict(int _code,String _title){
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
