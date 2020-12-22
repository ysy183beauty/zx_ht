package com.credit.CreditPublicity.controller;

import com.credit.FTLBox.NPTCreditFTLBox;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 项目： dlcms
 * 作者： owen
 * 时间： 2017/5/8 20:34
 * 描述：
 *      信用业务功能点的通用控制器
 */
@Controller
@RequestMapping(value = "/cp")
public class NptCreditPointController {


    @RequestMapping(value = "/enforced.do")
    public String enforcedIndex(){

        return NPTCreditFTLBox.FTL_CREDIT_ENFORCED_INDEX;
    }

}
