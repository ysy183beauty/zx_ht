package com.npt.rms.arch.manager;


import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.dict.NptDict;
import com.npt.rms.arch.bean.ZTree;
import com.npt.rms.base.manager.NptCachedManager;

import java.util.List;
import java.util.Map;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/26 15:52
 * 备注：
 */
public interface NptOrganizationManager extends NptCachedManager<NptLogicDataProvider> {
    /**
     * 查询单位信息下拉框
     */
    List<Map> selectUnitInfo(String sql);
    /**
     * 查询机构组织树信息
     */
    List<ZTree> selectOrganTreeList(Long organId);

    /**
     * 查询模型信息
     */
    Integer selectCurrentModelId(NptDict host);

    /**
     * 查询连接池的id
     */
    List<Map> selectpoolId(String sql);

    /**
     * 修改查询条件信息
     */
    Integer updateCondition(String sql);
}