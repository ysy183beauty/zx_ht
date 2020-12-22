package com.npt.grs.query.globalSearch.manager;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.rms.auth.bean.NptGlobalSearchBean;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/11/2 20:16
 * 备注：
 */
public interface NptGlobalSearchManager {


    /**
     * 作者：owen
     * 日期：2016/11/2 20:18
     * 备注：
     *      依据模糊字段fromField及其值pkValue模糊查询所有字段名跟toField匹配的字段及其业务值
     * 参数：
     * 返回：
     */
    Collection<NptGlobalSearchBean> search(String pkValue,String fromField,String toField);

     /**
       * 作者：97175
       * 日期：2016/11/7 20:45
       * 备注：
       *
       * 参数：
       * 返回：
       */
    Object queryUniqueData(NptLogicDataType type, NptLogicDataField byField, String pkValue);

}
