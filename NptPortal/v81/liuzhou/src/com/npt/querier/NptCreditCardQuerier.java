package com.npt.querier;

import com.npt.bridge.model.NptBaseModel;

/**
 * 项目: 柳州征信门户
 * 作者: zhanglei
 * 日期: 2017/6/14
 * 备注: 信用名片查询器
 */
public interface NptCreditCardQuerier {
    NptBaseModel getThisModel(Integer userType);
}
