package com.npt.rms.auth.action;

import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.auth.entity.NptDataPermission;
import com.npt.rms.auth.entity.NptDataRole;
import com.npt.rms.auth.manager.NptDataRoleManager;
import com.npt.rms.auth.service.NptRmsAuthService;
import com.npt.rms.base.action.NptRMSAction;
import com.npt.rms.util.NptRmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/27 16:46
 * 备注：
 */
@Controller("npt.rms.datarole")
public class    NptDataRoleAction extends NptRMSAction<NptDataRole> {

    @Autowired
    NptRmsAuthService authService;

    @Autowired
    NptDataRoleManager roleManager;

    /**
     * 作者：owen
     * 日期：2016/10/31 21:35
     * 备注：
     *
     * 参数：
     * 返回：
     */
    public String index(){
        return list();
    }

    /**
     * 作者：owen
     * 日期：2016/10/31 21:35
     * 备注：
     *      加载角色列表
     * 参数：
     * 返回：
     */
    public String list(){
        Collection<NptDataRole> result = authService.listRoles();
        this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(),result);
        return SUCCESS;
    }


    /**
     * 作者: owen
     * 时间: 2016/9/27 20:50
     * 备注:
     * 新增页面
     */
    public String addPage(){
        Collection<NptDataPermission> permissionList = authService.listPermissions(NptDict.IDS_ENABLED,getOperatorId());
        this.setAttribute("_PERMISSION_LIST",permissionList);
        return SUCCESS;
    }

    /**
     *作者：OWEN
     *时间：2016/11/21 17:44
     *描述:
     *      添加角色
     */
    public void addRole(){
        String roleName = getParameter("roleName");
        String roleNote = getParameter("roleNote");
        String rolePermissions = getParameter("rolePermissions");

        if(null == roleName || roleName.isEmpty()){
            this.outputErrorResult(NptDict.RST_INVALID_PARAMS.getTitle());
            return;
        }
        if (!roleManager.checkRole(roleName)){
            this.outputErrorResult(NptDict.RST_DUPLICATE_OPERATION.getTitle());
            return;
        }
        NptDataRole role = new NptDataRole();
        role.setRoleName(roleName);
        role.setRoleNote(roleNote);
        role.setStatus(NptDict.IDS_ENABLED.getCode());
        role.setCreateTime(NptCommonUtil.getCurrentSysDate());
        role.setCreatorId(NptRmsUtil.getCurrentUserId());

        authService.add(role);

        List<Long> permissions = NptCommonUtil.getOrderObjectArrayBySeprator(rolePermissions,",");
        if(null != permissions && !permissions.isEmpty()){
            Collection<NptDataPermission> permissionList = new ArrayList<>();
            for(Long pid:permissions){
                NptDataPermission p = authService.findPermissionByID(pid);
                if(null != p && p.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
                    permissionList.add(p);
                }
            }
            authService.addPermissionForRole(role,permissionList);
        }
        this.outputSuccessResult();
    }

    /**
     *作者：OWEN
     *时间：2016/11/21 18:03
     *描述:
     *      编辑角色
     */
    public String editPage(){
        Long roleId = getLongParameter("roleId");
        NptDataRole role = authService.findRoleById(roleId);
        if(null == role || !role.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
            this.setAttribute("_ERROR_RESULT", NptDict.RST_INVALID_PARAMS.getTitle());
        }else {
            Collection<NptDataPermission> permissionList = authService.listRolePermission(role);
            this.setAttribute("_ROLE_INFO",role);
            this.setAttribute("_ROLE_PERMISSION_LIST",permissionList);

            Collection<NptDataPermission> allPermissionList = authService.listPermissions(NptDict.IDS_ENABLED,getOperatorId());
            this.setAttribute("_PERMISSION_LIST",allPermissionList);
            Collection<NptDataPermission> otherPermissionList = new ArrayList<>();
            otherPermissionList.addAll(allPermissionList);
            otherPermissionList.removeAll(permissionList);
            this.setAttribute("_OTHER_PERMISSION_LIST",otherPermissionList);
        }
        return SUCCESS;
    }

    /**
     *作者：OWEN
     *时间：2016/11/21 18:10
     *描述:
     *      编辑角色
     */
    public void editRole(){
        Long roleId = getLongParameter("roleId");
        String roleName = getParameter("roleName");
        String roleNote = getParameter("roleNote");
        String rolePermissions = getParameter("rolePermissions");

        NptDataRole role = authService.findRoleById(roleId);
        if(null == role || null == roleName || roleName.isEmpty()){
            this.outputErrorResult(NptDict.RST_INVALID_PARAMS.getTitle());
            return;
        }

        role.setRoleName(roleName);
        role.setRoleNote(roleNote);
        authService.update(role);

        List<Long> permissions = NptCommonUtil.getOrderObjectArrayBySeprator(rolePermissions, ",");
        Collection<NptDataPermission> permissionList = new ArrayList<>();
        for (Long pid : permissions) {
            NptDataPermission p = authService.findPermissionByID(pid);
            if (null != p && p.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {
                permissionList.add(p);
            }
        }
        authService.updatePermissionForRole(role, permissionList);
        this.outputSuccessResult();
    }

    /**
     *作者: xuqinyuan
     *时间: 2016/11/22 14:51
     *备注: 启用角色
     */
    public void enabled() {
        Long roleId = getLongParameter("roleId");
        authService.enableRoleById(roleId);
    }

    /**
     *作者: xuqinyuan
     *时间: 2016/11/22 15:00
     *备注: 禁用角色
     */
    public void disabled(){
        Long roleId = getLongParameter("roleId");
        authService.disableRoleById(roleId);
    }
}
