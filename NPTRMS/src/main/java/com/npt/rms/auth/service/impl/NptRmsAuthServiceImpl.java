package com.npt.rms.auth.service.impl;

import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.rms.auth.entity.*;
import com.npt.rms.auth.manager.*;
import com.npt.rms.auth.service.NptRmsAuthService;
import com.npt.rms.util.NptRmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.summer.core.context.module.dependent.PublicBean;
import org.summer.extend.orm.condition.Conditions;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/10 12:16
 * 描述:
 */
@Service("rmsAuthService")
@Transactional
public class NptRmsAuthServiceImpl implements NptRmsAuthService,PublicBean{

    @Autowired
    private NptSimpleUserManager userManager;
    @Autowired
    private NptDataRoleManager roleManager;
    @Autowired
    private NptDataUserRoleManager userRoleManager;
    @Autowired
    private NptDataRolePermissionManager rolePermissionManager;
    @Autowired
    private NptDataPermissionManager permissionManager;
    @Autowired
    private NptRmsArchService archService;

    /**
     * 作者：OWEN
     * 时间：2016/11/10 15:50
     * 描述:
     * 检测用户是否存在
     *
     * @param name
     */
    @Override
    public boolean checkUserName(String name) {
        Collection<NptSimpleUser> simpleUsers = userManager.findByLoginName(name);
        if (null != simpleUsers && simpleUsers.size() >= NptCommonUtil.IntegerOne()){
            return true;
        } else{
            return false;
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 15:36
     * 描述:
     * 加载所有用户
     */
    @Override
    public Collection<NptSimpleUser> listUser() {
        return userManager.findAll();
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 15:37
     * 描述:
     * 加载指定机构下的所有用户
     *
     * @param org
     */
    @Override
    public Collection<NptSimpleUser> listUser(NptLogicDataProvider org) {
        Collection<NptSimpleUser> result = new ArrayList<>();
        if(null != org){
            Collection<NptSimpleUser> searchResult = userManager.findByCondition(
                    Conditions.eq(NptSimpleUser.PROPERTY_USER_ORG_ID,org.getId().toString()));
            if(null != searchResult){
                result.addAll(searchResult);
            }
        }
        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 12:09
     * 描述:
     * 通过ID获取用户信息
     *
     * @param id
     */
    @Override
    public NptSimpleUser getUserById(Long id) {
        return userManager.findById(id);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 12:45
     * 描述:
     * 通过用户名查询用户
     *
     * @param name
     */
    @Override
    public Collection<NptSimpleUser> getUserByName(String name) {
        return userManager.findByLoginName(name);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 12:10
     * 描述:
     * 获取用户拥有的角色列表
     *
     * @param user
     */
    @Override
    public Collection<NptDataRole> listUserRoles(NptSimpleUser user) {
        Collection<NptDataRole> result = new ArrayList<>();
        if(null != user){
            Collection<NptDataUserRole> userRoles = userRoleManager.findByCondition(
                    Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()),
                    Conditions.eq(NptDataUserRole.PROPERTY_USER_ID,user.getUserId()));

            if(null != userRoles){
                Collection<Long> idList = new ArrayList<>();
                for(NptDataUserRole ur:userRoles){
                    idList.add(ur.getRoleId());
                }
                result.addAll(listRoles(idList));
            }
        }
        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 12:15
     * 描述:
     * 添加用户
     *
     * @param user
     */
    @Override
    public void add(NptSimpleUser user) {
        userManager.save(user);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/22 15:11
     * 描述:
     * 添加权限
     *
     * @param p
     */
    @Override
    public void add(NptDataPermission p) {
        permissionManager.save(p);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/22 15:11
     * 描述:
     * 更新权限
     *
     * @param p
     */
    @Override
    public void udpate(NptDataPermission p) {
        permissionManager.update(p);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 15:16
     * 描述:
     * 为用户添加角色
     *
     * @param user
     * @param list
     */
    @Override
    public void addRoleForUser(NptSimpleUser user, Collection<NptDataRole> list) {
        Collection<NptDataUserRole> insertList = new ArrayList<>();
        Collection<NptDataUserRole> updateList = new ArrayList<>();

        if(null != user && null != list){
            for(NptDataRole r:list){
                NptDataUserRole ur = userRoleManager.findUniqueByCondition(
                        Conditions.eq(NptDataUserRole.PROPERTY_USER_ID,user.getUserId()),
                        Conditions.eq(NptDataUserRole.PROPERTY_ROLE_ID,r.getId()));
                if(null != ur){
                    ur.setStatus(NptDict.IDS_ENABLED.getCode());
                    ur.setCreatorId(NptRmsUtil.getCurrentUserId());
                    ur.setCreateTime(NptCommonUtil.getCurrentSysDate());
                    updateList.add(ur);
                }else {
                    ur = new NptDataUserRole();
                    ur.setRoleId(r.getId());
                    ur.setUserId(user.getUserId());
                    ur.setParentId(NptCommonUtil.getDefaultParentId());
                    ur.setCreateTime(NptCommonUtil.getCurrentSysDate());
                    ur.setCreatorId(NptRmsUtil.getCurrentUserId());
                    ur.setStatus(NptDict.IDS_ENABLED.getCode());
                    insertList.add(ur);
                }
            }
        }
        batchSaveUserRole(insertList);
        batchUpdateUserRole(updateList);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 15:17
     * 描述:
     * 为用户删除角色
     *
     * @param user
     * @param list
     */
    @Override
    public void deleteRoleForUser(NptSimpleUser user, Collection<NptDataRole> list) {
        Collection<NptDataUserRole> updateList = new ArrayList<>();

        if(null != user && null != list) {
            for (NptDataRole r : list) {
                NptDataUserRole ur = userRoleManager.findUniqueByCondition(
                        Conditions.eq(NptDataUserRole.PROPERTY_USER_ID, user.getUserId()),
                        Conditions.eq(NptDataUserRole.PROPERTY_ROLE_ID, r.getId()));
                if (null != ur) {
                    ur.setStatus(NptDict.IDS_DELETED.getCode());
                    updateList.add(ur);
                }
            }
        }
        batchUpdateUserRole(updateList);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 15:18
     * 描述:
     * 为角色添加权限
     *
     * @param role
     * @param list
     */
    @Override
    public void addPermissionForRole(NptDataRole role, Collection<NptDataPermission> list) {
        Collection<NptDataRolePermission> insertList = new ArrayList<>();
        Collection<NptDataRolePermission> updateList = new ArrayList<>();
        if(null != role && null != list){
            for(NptDataPermission p:list){
                NptDataRolePermission rp = rolePermissionManager.findUniqueByCondition(
                        Conditions.eq(NptDataRolePermission.PROPERTY_ROLE_ID,role.getId()),
                        Conditions.eq(NptDataRolePermission.PROPERTY_PERMISSION_ID,p.getId()));
                if(null != rp){
                    rp.setStatus(NptDict.IDS_ENABLED.getCode());
                    rp.setCreateTime(NptCommonUtil.getCurrentSysDate());
                    rp.setCreatorId(NptRmsUtil.getCurrentUserId());
                    updateList.add(rp);
                }else {
                    rp = new NptDataRolePermission();
                    rp.setRoleId(role.getId());
                    rp.setPermissionId(p.getId());
                    rp.setStatus(NptDict.IDS_ENABLED.getCode());
                    rp.setCreateTime(NptCommonUtil.getCurrentSysDate());
                    rp.setCreatorId(NptRmsUtil.getCurrentUserId());
                    insertList.add(rp);
                }
            }
        }
        batchSaveRolePermission(insertList);
        batchUpdateRolePermission(updateList);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 15:18
     * 描述:
     * 为角色删除权限
     *
     * @param role
     * @param list
     */
    @Override
    public void deletePermissionForRole(NptDataRole role, Collection<NptDataPermission> list) {
        Collection<NptDataRolePermission> updateList = new ArrayList<>();
        if(null != role && null != list) {
            for (NptDataPermission p : list) {
                NptDataRolePermission rp = rolePermissionManager.findUniqueByCondition(
                        Conditions.eq(NptDataRolePermission.PROPERTY_ROLE_ID, role.getId()),
                        Conditions.eq(NptDataRolePermission.PROPERTY_PERMISSION_ID, p.getId()));
                if (null != rp) {
                    rp.setStatus(NptDict.IDS_DELETED.getCode());
                    updateList.add(rp);
                }
            }
        }
        batchUpdateRolePermission(updateList);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/21 18:15
     * 描述:
     * 为角色更新权限
     *
     * @param role
     * @param list
     */
    @Override
    public void updatePermissionForRole(NptDataRole role, Collection<NptDataPermission> list) {
        if (null != role && null != list) {
            Collection<NptDataPermission> currentPermissions = this.listRolePermission(role);
            if (null != currentPermissions) {
                Collection<NptDataPermission> insertList = new ArrayList<>();
                Collection<NptDataPermission> deleteList = new ArrayList<>();
                for (NptDataPermission p : currentPermissions) {
                    /**
                     * 新权限列表中不包含旧的，则要删除之
                     */
                    if (!list.contains(p)) {
                        deleteList.add(p);
                    }
                }
                for (NptDataPermission p : list) {
                    /**
                     * 旧权限列表中不包含新的，则要新增
                     */
                    if (!currentPermissions.contains(p)) {
                        insertList.add(p);
                    }
                }

                this.addPermissionForRole(role, insertList);
                this.deletePermissionForRole(role, deleteList);
            }
        }
    }


    /*****************************************************关系表的维护方法，不暴露给外部**********************************/
    private void save(NptDataUserRole ur){
        userRoleManager.save(ur);
    }

    private void batchSaveUserRole(Collection<NptDataUserRole> list){
        userRoleManager.saveAll(list);
    }

    private void update(NptDataUserRole ur){
        userRoleManager.update(ur);
    }

    private void batchUpdateUserRole(Collection<NptDataUserRole> list){
        if(null != list){
            for(NptDataUserRole ur:list){
                update(ur);
            }
        }
    }

    private void save(NptDataRolePermission rp){
        rolePermissionManager.save(rp);
    }

    private void batchSaveRolePermission(Collection<NptDataRolePermission> list){
        rolePermissionManager.saveAll(list);
    }

    private void update(NptDataRolePermission rp){
        rolePermissionManager.update(rp);
    }

    private void batchUpdateRolePermission(Collection<NptDataRolePermission> list){
        if(null != list){
            for(NptDataRolePermission rp:list){
                update(rp);
            }
        }
    }


    /*****************************************************关系表的维护方法，不暴露给外部**********************************/


    /**
     * 作者：OWEN
     * 时间：2016/11/10 12:15
     * 描述:
     * 添加角色
     *
     * @param role
     */
    @Override
    public void add(NptDataRole role) {
        roleManager.save(role);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 12:51
     * 描述:
     * 加载所有角色
     */
    @Override
    public Collection<NptDataRole> listRoles() {
        Collection<NptDataRole> result = new ArrayList<>();
        Collection<NptDataRole> searchResult = roleManager.findAll();
        if(null != searchResult){
            result.addAll(searchResult);
        }
        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 12:52
     * 描述:
     * 依据ID列表加载指定的角色
     *
     * @param idList
     */
    @Override
    public Collection<NptDataRole> listRoles(Collection<Long> idList) {
        Collection<NptDataRole> result = new ArrayList<>();
        if(null != idList && !idList.isEmpty()){
            Collection<NptDataRole> searchResult = roleManager.findByCondition(
                    Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()),
                    Conditions.in(NptBaseEntity.PROPERTY_ID,idList));
            if(null != searchResult){
                result.addAll(searchResult);
            }
        }
        return result;
    }

    @Override
    public Collection<NptDataRole> listRoles(NptDict status) {
        if(null == status) {
            return roleManager.findAll();
        }else {
            return roleManager.findByCondition(Conditions.eq(NptBaseEntity.PROPERTY_STATUS,status.getCode()));
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 14:23
     * 描述:
     * 加载所有权限
     */
    @Override
    public Collection<NptDataPermission> listPermissions(NptDict status) {
        if(null == status) {
            return permissionManager.findAll();
        }else {
            return permissionManager.findByCondition(Conditions.eq(NptBaseEntity.PROPERTY_STATUS,status.getCode()));
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/22 20:03
     * 描述:
     * 加载指定状态及用户直接子机构的所有权限
     *
     * @param status
     * @param userId
     */
    @Override
    public Collection<NptDataPermission> listPermissions(NptDict status, Long userId) {
        Collection<NptDataPermission> result = new ArrayList<>();
        NptSimpleUser user = this.getUserById(userId);
        if(null != user){
            NptLogicDataProvider userOrg = archService.fastFindOrgById(user.getUserOrgId());
            if(null != userOrg){
                Collection<NptLogicDataProvider> subOrgList = archService.listOrg(userOrg.getId());
                if(null != subOrgList && !subOrgList.isEmpty()) {
                    Collection<Long> subOrgIdList = new ArrayList<>();
                    for(NptLogicDataProvider o:subOrgList){
                        subOrgIdList.add(o.getId());
                    }
                    if (null == status) {
                        Collection<NptDataPermission> searchResult = permissionManager.findByCondition(
                                Conditions.in(NptDataPermission.PROPERTY_ORG_ID, subOrgIdList));
                        if (null != searchResult && !searchResult.isEmpty()) {
                            result.addAll(searchResult);
                        }
                    } else {
                        Collection<NptDataPermission> searchResult = permissionManager.findByCondition(
                                Conditions.eq(NptBaseEntity.PROPERTY_STATUS, status.getCode()),
                                Conditions.in(NptDataPermission.PROPERTY_ORG_ID, subOrgIdList));
                        if (null != searchResult && !searchResult.isEmpty()) {
                            result.addAll(searchResult);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 16:37
     * 描述:
     * 获取角色下挂的权限列表
     *
     * @param role
     */
    @Override
    public Collection<NptDataPermission> listRolePermission(NptDataRole role) {
        Collection<NptDataPermission> result = new ArrayList<>();
        if(null != role){
            Collection<NptDataRolePermission> rpList = rolePermissionManager.findByCondition(
                    Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()),
                    Conditions.eq(NptDataRolePermission.PROPERTY_ROLE_ID,role.getId()));
            if(null != rpList){
                for(NptDataRolePermission rp:rpList){
                    NptDataPermission permission = permissionManager.fastFindById(rp.getPermissionId());
                    if(null != permission){
                        result.add(permission);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 15:57
     * 描述:
     * 更新用户
     *
     * @param user
     */
    @Override
    public void update(NptSimpleUser user) {
        userManager.update(user);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/21 18:13
     * 描述:
     * 更新角色基本信息
     *
     * @param role
     */
    @Override
    public void update(NptDataRole role) {
        roleManager.update(role);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 16:00
     * 描述:
     * 禁用启用
     *
     * @param user
     */
    @Override
    public void disableUser(NptSimpleUser user) {
        if(null != user) {
            disableUserById(user.getUserId());
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 16:00
     * 描述:
     * 启用用户
     *
     * @param user
     */
    @Override
    public void enableUser(NptSimpleUser user) {
        if(null != user) {
            enableUserById(user.getUserId());
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 16:03
     * 描述:
     * 禁用用户
     *
     * @param id
     */
    @Override
    public void disableUserById(Long id) {
        userManager.disable(id);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 16:03
     * 描述:
     * 启用用户
     *
     * @param id
     */
    @Override
    public void enableUserById(Long id) {
        userManager.enable(id);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/10 16:32
     * 描述:
     * 检测用户是否拥有指定的权限
     *
     * @param user
     * @param permission
     */
    @Override
    public Boolean havePermission(NptSimpleUser user,NptDataPermission permission) {
        Boolean result = false;
        if (null != user && null != permission) {
            Collection<NptDataRole> dataRoles = listUserRoles(user);
            if (null != dataRoles && !dataRoles.isEmpty()) {
                for (NptDataRole r : dataRoles) {
                    Collection<NptDataPermission> rpList = listRolePermission(r);
                    if (null != rpList) {
                        for (NptDataPermission p : rpList) {
                            if (p.getStatus().equals(NptDict.IDS_ENABLED.getCode())
                                    && p.getOrgId().equals(permission.getOrgId())
                                    && p.getAction().equals(permission.getAction())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/21 17:47
     * 描述:
     * 通过ID查询数据角色
     *
     * @param id
     */
    @Override
    public NptDataRole findRoleById(Long id) {
        return roleManager.findById(id);
    }

    @Override
    public NptDataRole fastFindRolebyId(Long id) {
        return roleManager.fastFindById(id);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/22 15:45
     * 描述:
     * 通过ID查询权限
     *
     * @param id
     */
    @Override
    public NptDataPermission findPermissionByID(Long id) {
        return permissionManager.findById(id);
    }

    @Override
    public NptDataPermission fastFindPermissionById(Long id) {
        return permissionManager.fastFindById(id);
    }

    /**
     * 作者: xuqinyuan
     * 时间: 2016/11/22 14:58
     * 备注: 启用角色
     * @param id
     */
    @Override
    public void enableRoleById(Long id) {
        NptDataRole dataRole = findRoleById(id);
        dataRole.setStatus(NptDict.IDS_ENABLED.getCode());
        roleManager.update(dataRole);
    }

    /**
     *作者: xuqinyuan
     *时间: 2016/11/22 14:58
     *备注: 禁用角色
     * @param id
     */
    @Override
    public void disableRoleById(Long id) {
        NptDataRole dataRole = findRoleById(id);
        dataRole.setStatus(NptDict.IDS_DISABLED.getCode());
        roleManager.update(dataRole);
    }

    /**
     * 作者: 张磊
     * 日期: 16/12/19 下午10:57
     * 备注: 设置用户角色
     */
    @Override
    public void setRoleForUser(NptSimpleUser user, Collection<NptDataRole> roleList) {
        // 获取用户已有角色
        Collection<NptDataRole> addedRoleList = this.listUserRoles(user);

        // 删除角色
        Collection<NptDataRole> dList = new ArrayList<>();
        dList.addAll(addedRoleList);
        dList.removeAll(roleList);
        this.deleteRoleForUser(user, dList);

        // 添加角色
        Collection<NptDataRole> aList = new ArrayList<>();
        aList.addAll(roleList);
        aList.removeAll(addedRoleList);
        this.addRoleForUser(user, aList);
    }

    /**
     *  author: zhanglei
     *  date:   2017/06/28 16:42
     *  note:
     *          创建sso用户
     */
    @Override
    public void createSsoUser(String loginName, String userName) {
        userManager.createSsoUser(loginName, userName);
    }
}
