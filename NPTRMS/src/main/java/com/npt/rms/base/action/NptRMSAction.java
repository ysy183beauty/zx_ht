package com.npt.rms.base.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.base.NptRmsCommonService;
import com.npt.rms.log.entity.NptLog;
import com.npt.web.action.NptPaginationAction;
import com.npt.bridge.dataBinder.NptWebBridgeBean;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/27 17:51
 * 备注：
 */
public class NptRMSAction<T extends Serializable> extends NptPaginationAction<T>{

    public static final String LOG_SEPRATOR = "#";
    public static final String WEB_ID = "id";

    @Resource(name = "rmsCommonService")
    protected NptRmsCommonService commonService;


    /**
     * 作者：owen
     * 日期：2016/10/20 14:13
     * 备注：
     *      获取页面构建的JSON参数并将其实例化为页面与服务器的桥接实体类
     * 参数：
     * 返回：
     */
    public NptWebBridgeBean getWebConditions() {

        String strCondition = this.getWebCondition();
        if(null != strCondition){
            JSONObject object = JSON.parseObject(strCondition);

            NptWebBridgeBean bean = JSONObject.toJavaObject(object, NptWebBridgeBean.class);

            bean.setBeginIndex(this.getPageBeginIndex());
            bean.setPageSize(this.getPageSize());
            bean.setCurrPage(this.getPageCurrentPage());

            return bean;
        }else {
            NptWebBridgeBean bean = new NptWebBridgeBean();
            bean.setBeginIndex(this.getPageBeginIndex());
            bean.setPageSize(this.getPageSize());
            bean.setCurrPage(this.getPageCurrentPage());
            bean.setParentId(this.getWebParentId());

            return bean;
        }
    }

    /**
     *方法:
     *参数: 传递方法操作的含义，比如"新增""修改"等
     *返回:
     *作者: owen
     *时间: 2016/9/27 19:50
     *备注:
     *      构建日志实体，默认状态正常
     */
    public NptLog makeLog() {
        NptLog log = commonService.makeLog();

        return log;
    }

    /**
     *作者: owen
     *时间: 2016/9/28 11:00
     *备注:
     *      写日志
     */
    public void writeLog(NptLog log){
        commonService.save(log);
    }


    public Long getWebId(){
        return this.getLongParameter(WEB_ID);
    }

    public JSONObject createEmptyPaginationResult(){
        JSONObject datasObj = new JSONObject();
        JSONArray arrayData = new JSONArray();

        datasObj.put("list", arrayData);
        datasObj.put("totalCount", NptCommonUtil.IntegerZero());
        datasObj.put("currPage",getPageCurrentPage());
        datasObj.put("pageSize",getPageSize());
        datasObj.put("beginIndex", getPageBeginIndex());
        datasObj.put("totalPage", NptCommonUtil.IntegerZero());
        return datasObj;
    }
}
