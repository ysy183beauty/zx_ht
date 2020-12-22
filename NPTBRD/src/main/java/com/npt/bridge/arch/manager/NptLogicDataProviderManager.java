package com.npt.bridge.arch.manager;

import com.npt.bridge.base.NptBaseManager;
import com.npt.bridge.arch.NptLogicDataProvider;

import java.util.Collection;
import java.util.List;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2017/1/16 16:55
 * 描述:
 */
public interface NptLogicDataProviderManager extends NptBaseManager<NptLogicDataProvider, Long> {

    /**
     * 作者: 张磊
     * 日期: 17/2/16 下午3:51
     * 备注: 批量保存数据提供者
     */
    void saveAll(Collection<NptLogicDataProvider> list);

    /**
     * 作者: 张磊
     * 日期: 17/2/16 下午4:3
     * 备注: 更新机构信息
     */
    void update(NptLogicDataProvider org);

    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午10:40
     * 备注: 批量更新数据提供者
     */
    void updateAll(Collection<NptLogicDataProvider> list);

    /**
     * 作者: jss
     * 日期: 2018年3月1日16:09:04
     * 备注: 通过父code获得子部门数据
     */
    Collection<NptLogicDataProvider> listChild(Long fOrgId);

    public List listSectionCount(Long goupId) ;
}
