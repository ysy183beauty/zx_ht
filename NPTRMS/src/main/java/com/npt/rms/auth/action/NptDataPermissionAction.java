package com.npt.rms.auth.action;

import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.rms.auth.entity.NptDataPermission;
import com.npt.rms.auth.entity.NptSimpleUser;
import com.npt.rms.auth.manager.NptDataPermissionManager;
import com.npt.rms.auth.service.NptRmsAuthService;
import com.npt.rms.base.action.NptRMSAction;
import com.npt.rms.util.NptRmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/22 15:09
 * 描述:
 */
@Controller("npt.rms.datapermission")
public class NptDataPermissionAction extends NptRMSAction<NptDataPermission>{

    @Autowired
    private NptRmsAuthService authService;
    @Autowired
    private NptRmsArchService archService;
    @Autowired
    private NptDataPermissionManager permissionManager;
    /**
     *作者：OWEN
     *时间：2016/11/22 15:31
     *描述:
     *      权限主页
     */
    public String index(){
        return list();
    }

    /**
     *作者：OWEN
     *时间：2016/11/22 15:32
     *描述:
     *      加载权限列表
     */
    public String list(){
        Collection<NptDataPermission> permissions = authService.listPermissions(null);
        this.setAttribute("_PERMISSION_LIST",permissions);

        Collection<NptLogicDataProvider> orgList = archService.listAllOrg();
        this.setAttribute("_ORG_LIST",orgList);

        Collection<NptDict> actionList = NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.PMS);
        this.setAttribute("_ACTION_LIST",actionList);

        return SUCCESS;
    }

    /**
     *作者：OWEN
     *时间：2016/11/22 15:12
     *描述:
     *      添加权限
     */
    public String addPage(){
        Long userId = getOperatorId();
        NptSimpleUser user = authService.getUserById(userId);
        if(null != user){
            Long orgId = user.getUserOrgId();
            Collection<NptLogicDataProvider> orgList = archService.listOrg(orgId);
            Collection<NptDict> actions = NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.PMS);
            this.setAttribute("_ORG_LIST",orgList);
            this.setAttribute("_ACTION_LIST",actions);
        }
        return SUCCESS;
    }

    /**
     *作者：OWEN
     *时间：2016/11/22 15:52
     *描述:
     *      添加权限
     */
    public void add(){
        Long orgId = getLongParameter("orgId");
        Integer action = getIntParameter("actionId");
        if (!permissionManager.checkPermission(action,orgId)){
            this.outputErrorResult(NptDict.RST_DUPLICATE_OPERATION.getTitle());
            return;
        }
        NptDict dict = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.PMS,action);
        if(null == dict){
            this.outputErrorResult(NptDict.RST_INVALID_PARAMS.getTitle());
            return;
        }
        NptLogicDataProvider org = archService.findOrgById(orgId);
        if(null == org){
            this.outputErrorResult(NptDict.RST_INVALID_PARAMS.getTitle());
            return;
        }

        NptDataPermission permission = new NptDataPermission();
        permission.setAction(action);
        permission.setOrgId(orgId);
        permission.setPermissionTitle("[" + org.getOrgName() + "]" + "-->[" + dict.getTitle() + "]");
        permission.setCreateTime(NptCommonUtil.getCurrentSysDate());
        permission.setCreatorId(NptRmsUtil.getCurrentUserId());
        permission.setStatus(NptDict.IDS_ENABLED.getCode());

        authService.add(permission);
        this.outputSuccessResult();
    }

    /**
     *作者：OWEN
     *时间：2016/11/22 15:55
     *描述:
     *      设置权限的状态
     */
    public void setPermissionStatus(){
        Long permissionId = getLongParameter("permissionId");
        Integer status = getIntParameter("status");

        NptDict dict = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.IDS,status);
        if(null == dict){
            this.outputErrorResult(NptDict.RST_INVALID_PARAMS.getTitle());
            return;
        }
        NptDataPermission p = authService.findPermissionByID(permissionId);
        if(null == p){
            this.outputErrorResult(NptDict.RST_INVALID_PARAMS.getTitle());
            return;
        }

        if(!p.getStatus().equals(dict.getCode())){
            p.setStatus(dict.getCode());
            authService.udpate(p);
        }
        this.outputSuccessResult();
    }
}
