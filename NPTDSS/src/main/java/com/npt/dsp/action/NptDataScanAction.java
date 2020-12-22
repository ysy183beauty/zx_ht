package com.npt.dsp.action;

import com.alibaba.fastjson.JSON;
import com.npt.dsp.bean.NptDataScanResult;
import com.npt.dsp.manager.NptDataScanManager;
import com.npt.rms.base.action.NptRMSAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.Serializable;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/28 12:08
 * 备注：
 */
@Controller("npt.dsp.datascan")
public class NptDataScanAction extends NptRMSAction<Serializable>{

    @Autowired
    private NptDataScanManager scanManager;

    /**
     *作者: owen
     *时间: 2016/9/28 12:11
     *备注:
     *      扫描实体表并更新控制表
     */
    public void scanTables(){

        NptDataScanResult result = scanManager.mountTables();

        this.ajaxOutPutJson(JSON.toJSONString(result));
    }

    public void scanColumns(){

        NptDataScanResult result = scanManager.mountColumns();

        this.ajaxOutPutJson(JSON.toJSONString(result));
    }

    /**
     *作者: xuqinyuan
     *时间: 2016/10/11 15:01
     *备注: 默认首页
     */
    public String index(){
        return SUCCESS;
    }
}
