package com.npt.rms.auth.service;

import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.dict.NptDict;
import com.npt.rms.auth.entity.NptDataPermission;
import com.npt.rms.auth.entity.NptDataRole;
import com.npt.rms.auth.entity.NptSimpleUser;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/10 12:07
 * 描述:
 */
public interface NptRmsAuthService {

    /**
     *作者：OWEN
     *时间：2016/11/10 15:50
     *描述:
     *      检测用户是否存在
     */
    boolean checkUserName(String name);

    /**
     *作者：OWEN
     *时间：2016/11/10 15:36
     *描述:
     *      加载所有用户
     */
    Collection<NptSimpleUser> listUser();

    /**
     *作者：OWEN
     *时间：2016/11/10 15:37
     *描述:
     *      加载指定机构下的所有用户
     */
    Collection<NptSimpleUser> listUser(NptLogicDataProvider org);

    /**
     *作者：OWEN
     *时间：2016/11/10 12:09
     *描述:
     *      通过ID获取用户信息
     */
    NptSimpleUser getUserById(Long id);

    /**
     *作者：OWEN
     *时间：2016/11/10 12:45
     *描述:
     *      通过用户名查询用户
     */
    Collection<NptSimpleUser> getUserByName(String name);

    /**
     *作者：OWEN
     *时间：2016/11/10 12:10
     *描述:
     *      获取用户拥有的角色列表
     */
    Collection<NptDataRole> listUserRoles(NptSimpleUser user);

    /**
     *作者：OWEN
     *时间：2016/11/10 12:15
     *描述:
     *      添加用户
     */
    void add(NptSimpleUser user);

    /**
     *作者：OWEN
     *时间：2016/11/22 15:11
     *描述:
     *      添加权限
     */
    void add(NptDataPermission p);

    /**
     *作者：OWEN
     *时间：2016/11/22 15:11
     *描述:
     *      更新权限
     */
    void udpate(NptDataPermission p);

    /**
     *作者：OWEN
     *时间：2016/11/10 15:16
     *描述:
     *      为用户添加角色
     */
    void addRoleForUser(NptSimpleUser user,Collection<NptDataRole> list);

    /**
     *作者：OWEN
     *时间：2016/11/10 15:17
     *描述:
     *      为用户删除角色
     */
    void deleteRoleForUser(NptSimpleUser user,Collection<NptDataRole> list);

    /**
     *作者：OWEN
     *时间：2016/11/10 15:18
     *描述:
     *      为角色添加权限
     */
    void addPermissionForRole(NptDataRole role,Collection<NptDataPermission> list);

    /**
     *作者：OWEN
     *时间：2016/11/10 15:18
     *描述:
     *      为角色删除权限
     */
    void deletePermissionForRole(NptDataRole role,Collection<NptDataPermission> list);

    /**
     *作者：OWEN
     *时间：2016/11/21 18:15
     *描述:
     *      为角色更新权限
     */
    void updatePermissionForRole(NptDataRole role,Collection<NptDataPermission> list);

    /**
     *作者：OWEN
     *时间：2016/11/10 12:15
     *描述:
     *      添加角色
     */
    void add(NptDataRole role);

    /**
     *作者：OWEN
     *时间：2016/11/10 12:51
     *描述:
     *      加载所有角色
     */
    Collection<NptDataRole> listRoles();

    /**
     *作者：OWEN
     *时间：2016/11/10 12:52
     *描述:
     *      依据ID列表加载指定的角色
     */
    Collection<NptDataRole> listRoles(Collection<Long> idList);

    /**
     * 作者: 张磊
     * 日期: 16/12/19 下午11:23
     * 备注: 加载指定状态的角色
     */
    Collection<NptDataRole> listRoles(NptDict status);

    /**
     *作者：OWEN
     *时间：2016/11/10 14:23
     *描述:
     *      加载指定状态所有权限
     */
    Collection<NptDataPermission> listPermissions(NptDict status);

    /**
     *作者：OWEN
     *时间：2016/11/22 20:03
     *描述:
     *      加载指定状态及用户直接子机构的所有权限
     */
    Collection<NptDataPermission> listPermissions(NptDict status, Long userId);

    /**
     *作者：OWEN
     *时间：2016/11/10 16:37
     *描述:
     *      获取角色下挂的权限列表
     */
    Collection<NptDataPermission> listRolePermission(NptDataRole role);

    /**
     *作者：OWEN
     *时间：2016/11/10 15:57
     *描述:
     *      更新用户
     */
    void update(NptSimpleUser user);

    /**
     *作者：OWEN
     *时间：2016/11/21 18:13
     *描述:
     *      更新角色基本信息
     */
    void update(NptDataRole role);

    /**
     *作者：OWEN
     *时间：2016/11/10 16:00
     *描述:
     *      禁用启用
     */
    void disableUser(NptSimpleUser user);

    /**
     *作者：OWEN
     *时间：2016/11/10 16:00
     *描述:
     *      启用用户
     */
    void enableUser(NptSimpleUser user);

    /**
     *作者：OWEN
     *时间：2016/11/10 16:03
     *描述:
     *      禁用用户
     */
    void disableUserById(Long id);

    /**
     *作者：OWEN
     *时间：2016/11/10 16:03
     *描述:
     *      启用用户
     */
    void enableUserById(Long id);

    /**
     *作者：OWEN
     *时间：2016/11/10 16:32
     *描述:
     *      检测用户是否拥有指定的权限
     */
    Boolean havePermission(NptSimpleUser user,NptDataPermission permission);

    /**
     *作者：OWEN
     *时间：2016/11/21 17:47
     *描述:
     *      通过ID查询数据角色
     */
    NptDataRole findRoleById(Long id);
    NptDataRole fastFindRolebyId(Long id);

    /**
     *作者：OWEN
     *时间：2016/11/22 15:45
     *描述:
     *      通过ID查询权限
     */
    NptDataPermission findPermissionByID(Long id);
    NptDataPermission fastFindPermissionById(Long id);

    /**
     * 作者: xuqinyuan
     * 时间: 2016/11/22 14:58
     * 备注: 启用角色
     * @param id
     */
    void enableRoleById(Long id);

    /**
     *作者: xuqinyuan
     *时间: 2016/11/22 14:58
     *备注: 禁用角色
     * @param id
     */
    void disableRoleById(Long id);

    /**
     * 作者: 张磊
     * 日期: 16/12/19 下午10:57
     * 备注: 设置用户角色
     */
    void setRoleForUser(NptSimpleUser user, Collection<NptDataRole> roleList);

    /**
     *  author: zhanglei
     *  date:   2017/06/28 16:42
     *  note:
     *          创建sso用户
     */
    void createSsoUser(String loginName, String userName);
}
