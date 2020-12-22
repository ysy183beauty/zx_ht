package com.npt.common.service;

import com.npt.bridge.dict.NptDict;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SecurityService {

    String LAST_SEARCH_KEYWORD = "SEARCH_VALIDE_INFO";

    /**
     *作者：owen
     *时间: 2017/6/19 19:02
     *描述:
     *      通用查询验证方法
     */
    NptDict classicSearchValidate(String keyword, String captcha, HttpServletRequest request, HttpServletResponse response);
}
