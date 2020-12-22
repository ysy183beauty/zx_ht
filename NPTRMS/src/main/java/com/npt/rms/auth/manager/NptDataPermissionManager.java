package com.npt.rms.auth.manager;

import com.npt.rms.auth.entity.NptDataPermission;
import com.npt.rms.base.manager.NptCachedManager;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/22 15:08
 * 描述:
 */
public interface NptDataPermissionManager extends NptCachedManager<NptDataPermission>{

    /**
     *作者: xuqinyuan
     *时间: 2016/11/29 20:23
     *备注: 检查是否重复给机构增加权限
     *
     * @param actionId
     * @param orgId
     * @return
     */
    boolean checkPermission(Integer actionId,Long orgId);
}
