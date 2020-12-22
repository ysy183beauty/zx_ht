package com.npt.bridge.busiTag;

/**
 * author: owen
 * date:   2017/6/30 下午8:55
 * note:
 */
public enum  NptFixedBusinessTags {

    //行政许可
    SGS_XZXK_XZXDRMC(0,"行政相对人名称"),
    SGS_XZXK_XKNR(1,"许可内容"),

    //行政处罚
    SGS_XZCF_XZXDRMC(0,"行政相对人名称"),
    SGS_XZCF_XKNR(1,"处罚事由"),

    ;

    private int code;
    private String title;

    NptFixedBusinessTags(int _code, String _title){
        this.code = _code;
        this.title = _title;
    }

    public int getCode(){
        return this.code;
    }
    public String getTitle(){
        return this.title;
    }
}
