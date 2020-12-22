package com.npt.rms.auth.action;

import com.alibaba.fastjson.JSONObject;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.bridge.util.NptHttpUtil;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.rms.auth.entity.NptDataRole;
import com.npt.rms.auth.entity.NptSimpleUser;
import com.npt.rms.auth.service.NptRmsAuthService;
import com.npt.rms.base.action.NptRMSAction;
import com.npt.rms.util.NptRmsUtil;
import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.util.XmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.summer.security.entity.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/27 20:20
 * 备注：
 */
@Controller("npt.rms.user")
@Scope("prototype")
public class NptUserAction extends NptRMSAction<User> {

    @Autowired
    private NptRmsAuthService authService;
    @Autowired
    private NptRmsArchService archService;

    /**
     * 作者: xuqinyuan
     * 时间: 2016/9/27 21:14
     * 备注:
     * 加载主页
     */
    public String index() {
        return list();
    }

    /**
     * 作者: owen
     * 时间: 2016/9/27 20:39
     * 备注:
     * 加载用户列表
     */
    public String list() {
        Long orgId = getWebParentId();
        Collection<NptSimpleUser> userList;
        if(null != orgId){
            NptLogicDataProvider org = archService.findOrgById(orgId);
            userList = authService.listUser(org);
        }else {
            userList = authService.listUser();
        }
        Collection<NptLogicDataProvider> orgList = null;
        NptLogicDataProvider rootOrg = archService.getRootOrg();
        if (rootOrg != null) {
            orgList = archService.listOrg(rootOrg.getId());
        }
        this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(),userList);
        this.setAttribute("_ORG_LIST",orgList);
        return SUCCESS;
    }

    /**
     * 作者: owen
     * 时间: 2016/9/27 20:50
     * 备注:
     * 新增页面
     */
    public String addPage() {
        return SUCCESS;
    }

    /**
     * 作者: owen
     * 时间: 2016/9/27 20:50
     * 备注:
     * 新增用户
     */
    public void add() {

        NptSimpleUser user = newSimpleUserFromWeb();
        if (authService.checkUserName(user.getLoginName())) {
            this.outputErrorResult(NptDict.RST_DUPLICATE_OPERATION.getTitle());
        } else {
            try {
                authService.add(user);
                this.outputSuccessResult(NptDict.RST_SUCCESS.getTitle());
            } catch (Exception e) {
                this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
            }
        }
    }


    /**
     *  author: zhanglei
     *  date:   2017/06/01 14:24
     *  note:
     *          编辑用户
     */
    public String modifyPage() {
        try {
            Long id = this.getLongParameter("id");
            NptSimpleUser user = authService.getUserById(id);
            this.setAttribute("user",user);
            if (null != user) {
                NptLogicDataProvider org = archService.findOrgById(user.getUserOrgId());
                if (null != org) {
                    this.setAttribute("userOrgTitle", org.getOrgName());
                }
            }
            NptLogicDataProvider rootOrg = archService.getRootOrg();
            Collection<NptLogicDataProvider> ncmOrgs = archService.listOrg(rootOrg.getId());
            this.setAttribute("rootOrg", rootOrg);
            this.setAttribute("ncmOrgs", ncmOrgs);
        }catch (NumberFormatException e) {
            this.setAttribute("userOrgTitle", NptDict.RST_UNKNOW.getTitle());
        }

        return SUCCESS;
    }

    public void edit() {
        try {
            Long id = this.getLongParameter("id");
            NptSimpleUser user = newSimpleUserFromWeb(authService.getUserById(id));
            authService.update(user);

            this.outputSuccessResult();
        } catch (Exception e) {
            System.out.println(e.getMessage() == null ? e.toString() : e.getMessage());
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        }
    }

    private NptSimpleUser newSimpleUserFromWeb(){
        NptSimpleUser user = new NptSimpleUser();
        user.setLoginName(getParameter("user.loginName"));
        user.setUserName(this.getParameter("user.userName"));
        user.setPassword(this.getParameter("user.password"));
        user.setSex(this.getParameter("user.sex"));
        user.setUserOrgId(this.getLongParameter("user.department"));
        user.setMobile(this.getParameter("user.mobile"));
        user.setEmail(this.getParameter("user.email"));
        user.setRemark(this.getParameter("user.remark"));
        return user;
    }
    private NptSimpleUser newSimpleUserFromWeb(NptSimpleUser user){
        user.setLoginName(getParameter("user.loginName"));
        user.setUserName(this.getParameter("user.userName"));
        user.setSex(this.getParameter("user.sex"));
        user.setUserOrgId(this.getLongParameter("user.department"));
        user.setMobile(this.getParameter("user.mobile"));
        user.setEmail(this.getParameter("user.email"));
        user.setRemark(this.getParameter("user.remark"));
        return user;
    }
    /**
     * @throws Exception 检查用户名是否已经存在
     */
    public void checkUserName() throws Exception {
        String username = this.getParameter("user_username");

        if (StringUtils.isBlank(username)) {
            this.outputErrorResult("用户名为空，无法完成校验！");
            return;
        }

        if (authService.checkUserName(username)) {
            this.outputSuccessResult();
        } else {
            this.outputErrorResult("用户名已存在！");
        }
    }

    /**
     * 检查登录输错的次数信息
     * @param
     * @return
     */
    public void checkLoginErrorCount() throws Exception{
        String encodePassword = NptRmsUtil.encodePassword("123456");
        System.out.println(encodePassword);
        System.out.println("检查登录输错的次数信息");
        System.out.println("检查登录输错的次数信息");
    }

    protected Long[] getLongParametersByName(String name) {
        String idsString = this.getParameter(name);
        String[] idsArray = StringUtils.split(idsString, ',');
        Long[] ids = new Long[idsArray.length];

        int index = 0;
        for (String id : idsArray) {
            ids[index++] = Long.valueOf(id);
        }
        return ids;
    }

    public void disabled() {
        Long id = this.getLongParameter("id");
        authService.disableUserById(id);
    }

    public void enabled() {
        Long id = this.getLongParameter("id");
        authService.enableUserById(id);
    }

    @Override
    protected JSONObject toJSON(User user) {
        JSONObject result = new JSONObject();
        result.put("id", user.getId());
        result.put("username", user.getUsername());
        result.put("name", user.getName());
        result.put("sex", user.getSex());
        result.put("enable", user.getEnable());
        result.put("modifyStamp", user.getModifyStamp());
        return result;
    }

    /**
     * 作者: xuqinyuan
     * 时间: 2016/10/11 14:33
     * 备注:
     */
    public String listByOrg() {
        Collection<NptSimpleUser> result = new ArrayList<>();
        try {
            Long orgId = getLongParameter("orgId");
            NptLogicDataProvider org = archService.findOrgById(orgId);
            if(null != org){
                Collection<NptSimpleUser> searchResult = authService.listUser(org);
                if(null != searchResult){
                    result.addAll(searchResult);
                }
            }
            this.setAttribute("orgName",org.getOrgName());
        }catch (Exception e){
            this.outputErrorResult(NptDict.RST_INVALID_PARAMS.getTitle());
        }
//        this.ajaxOutPutJson(JSONObject.toJSONString(result));
        this.setAttribute("orgUser",result);

        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 16/12/19 下午8:52
     * 备注: 分配角色列表页面
     */
    public String setRolePage() {
        Long userId = this.getLongParameter("userId");
        NptSimpleUser user = authService.getUserById(userId);
        if (user != null) {
            Collection<NptDataRole> added = authService.listUserRoles(user);
            Collection<NptDataRole> roleList = authService.listRoles(NptDict.IDS_ENABLED);
            this.setAttribute("_ROLE_LIST", roleList);
            this.setAttribute("_ROLE_ADDED_LIST", added);
            return SUCCESS;
        }
        return ERROR;
    }

    /**
     * 作者: 张磊
     * 日期: 16/12/19 下午8:47
     * 备注: 给当前用户分配角色
     */
    public void setRole() {
        Long userId = this.getLongParameter("userId");
        String roles = this.getParameter("roles");
        NptSimpleUser user = authService.getUserById(userId);
        if (user == null) {
            this.outputErrorResult("分配角色失败");
            return;
        }
        Collection<NptDataRole> roleList = new ArrayList<>();
        if (!roles.isEmpty()) {
            for (String roleId : roles.split(",")) {
                NptDataRole role = authService.fastFindRolebyId(Long.parseLong(roleId));
                if (role != null) {
                    roleList.add(role);
                }
            }
        }

        authService.setRoleForUser(user, roleList);
        this.outputSuccessResult();
    }

    /**
     *  author: zhanglei
     *  date:   2017/06/28 14:33
     *  note:
     *          创建sso用户
     */
    public void createSsoUser() {
        String validationUrl = this.getParameter("validationUrl");

        String response = NptHttpUtil.httpGet(validationUrl);
        String sysusercode = XmlUtils.getTextForElement(response, "SYSUSERCODE");
        String username = XmlUtils.getTextForElement(response, "USERNAME");

        System.out.println("---------------------------");
        System.out.println(sysusercode);
        System.out.println(username);

        try {
            authService.createSsoUser(sysusercode, username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.ajaxOutPutXml(response);
    }

}
