package com.npt.rms.auth.manager;

import com.npt.rms.auth.entity.NptDataRole;
import com.npt.rms.base.manager.NptCachedManager;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/27 21:35
 * 备注：
 */
public interface NptDataRoleManager extends NptCachedManager<NptDataRole> {


    /**
     * 作者：owen
     * 日期：2016/10/31 21:36
     * 备注：
     *      加载所有可用的角色
     * 参数：
     * 返回：
     */
    Collection<NptDataRole> listRoles();

    /**
     *作者: xuqinyuan
     *时间: 2016/12/1 16:05
     *备注: 检查角色名是否可用
     *
     * @param roleName
     * @return
     */
    Boolean checkRole(String roleName);
}
