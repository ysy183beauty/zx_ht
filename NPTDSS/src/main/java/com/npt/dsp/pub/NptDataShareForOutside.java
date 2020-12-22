package com.npt.dsp.pub;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/28 11:24
 * 备注：
 */
public interface NptDataShareForOutside {

    /**
     *作者: owen
     *时间: 2016/9/28 11:35
     *备注:
     *      对外部系统提供的HTTP协议的查询接口，JSON串返回
     */
    String outsideQuery(String authKey,int start,int end,int dataNo);
}
