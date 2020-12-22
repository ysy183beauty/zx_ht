package com.npt.bridge.arch.dao;


import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.dict.NptDict;

import java.util.Collection;
import java.util.List;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2017/1/16 21:04
 * 描述:
 */
public interface NptLogicDataProviderDao extends NptBaseDao<NptLogicDataProvider, Long> {
    /**
     * 作者: jss
     * 日期: 2018年3月1日16:09:04
     * 备注: 通过父code获得子部门数据
     */
    List<NptLogicDataProvider> listChild(Long fOrgId);
    public List listSectionCount(Long goupId) ;

}
