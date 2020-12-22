package com.jeecms.core.manager.impl;

import com.jeecms.cms.entity.main.Channel;
import com.jeecms.cms.manager.main.ChannelMng;
import com.jeecms.cms.manager.main.ContentMng;
import com.jeecms.common.email.EmailSender;
import com.jeecms.common.email.MessageTemplate;
import com.jeecms.common.hibernate4.Updater;
import com.jeecms.common.page.Pagination;
import com.jeecms.core.dao.CmsUserDao;
import com.jeecms.core.entity.*;
import com.jeecms.core.manager.*;
import com.npt.bridge.util.NptHttpUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
@Transactional
public class CmsUserMngImpl implements CmsUserMng {
	@Transactional(readOnly = true)
	public Pagination getPage(String username, String email, Integer siteId,
			Integer groupId, Boolean disabled, Boolean admin, Integer rank,
			String realName,Integer departId,Integer roleId,
			Boolean allChannel,Boolean allControlChannel,
			int pageNo, int pageSize,String queryFlag) {
		Pagination page = dao.getPage(username, email, siteId, groupId,
				disabled, admin, rank,realName,departId,roleId, 
				allChannel,allControlChannel,pageNo, pageSize,queryFlag);

		List<CmsUser> list=(List<CmsUser>) page.getList();

       List<CmsUser> list2= DecryptUserList(list);//解密

       page.setList(list2);

		return page;
	}
	
	@Transactional(readOnly = true)
	public List<CmsUser> getList(String username, String email, Integer siteId,
			Integer groupId, Boolean disabled, Boolean admin, Integer rank) {
		List<CmsUser> list = dao.getList(username, email, siteId, groupId,
				disabled, admin, rank);
		return DecryptUserList(list);//解密
	}

	@Transactional(readOnly = true)
	public List<CmsUser> getAdminList(Integer siteId, Boolean allChannel,
			Boolean disabled, Integer rank) {
		return DecryptUserList(dao.getAdminList(siteId, allChannel, disabled, rank));
	}
	
	@Transactional(readOnly = true)
	public Pagination getAdminsByDepartId(Integer id, int pageNo,int pageSize){
		Pagination page= dao.getAdminsByDepartId(id, pageNo, pageSize);
        List<CmsUser> list=(List<CmsUser>) page.getList();

        List<CmsUser> list2= DecryptUserList(list);//解密

        page.setList(list2);

        return page;
	}
	
	@Transactional(readOnly = true)
	public Pagination getAdminsByRoleId(Integer roleId, int pageNo, int pageSize){
        Pagination page= dao.getAdminsByRoleId(roleId, pageNo, pageSize);
        List<CmsUser> list=(List<CmsUser>) page.getList();

        List<CmsUser> list2= DecryptUserList(list);//解密

        page.setList(list2);

        return page;
	}

	@Transactional(readOnly = true)
	public CmsUser findById(Integer id) {
		CmsUser entity = dao.findById(id);
		return DecryptUser(entity);
	}

	@Transactional(readOnly = true)
	public CmsUser findByUsername(String username) {
		CmsUser entity = dao.findByUsername(username);
		return DecryptUser(entity);
	}

	public CmsUser registerMember(String username, String email,
			String password, String ip, Integer groupId,Integer grain,boolean disabled,CmsUserExt userExt,Map<String,String>attr){
		UnifiedUser unifiedUser = unifiedUserMng.save(username, email,
				password, ip);
		CmsUser user = new CmsUser();
		user.forMember(unifiedUser);
		user.setGrain(grain);
		user.setAttr(attr);
		user.setDisabled(disabled);
		CmsGroup group = null;
		if (groupId != null) {
			group = cmsGroupMng.findById(groupId);
		} else {
			group = cmsGroupMng.getRegDef();
		}
		if (group == null) {
			throw new RuntimeException(
					"register default member group not found!");
		}
		user.setGroup(group);
		user.init();
		user=encryptUser(user);
		dao.save(user);
		user=DecryptUser(user);

		cmsUserExtMng.save(userExt, user);//存在加解密

		return user;
	}

	
	public CmsUser registerMember(String username, String email,
			String password, String ip, Integer groupId, boolean disabled,CmsUserExt userExt,Map<String,String>attr,
			Boolean activation, EmailSender sender, MessageTemplate msgTpl)throws UnsupportedEncodingException, MessagingException{
		UnifiedUser unifiedUser = unifiedUserMng.save(username, email,
				password, ip, activation, sender, msgTpl);
		CmsUser user = new CmsUser();

		user.forMember(unifiedUser);
		user.setAttr(attr);
		user.setDisabled(disabled);

		CmsGroup group = null;
		if (groupId != null) {
			group = cmsGroupMng.findById(groupId);
		} else {
			group = cmsGroupMng.getRegDef();
		}
		if (group == null) {
			throw new RuntimeException(
					"register default member group not found!");
		}
		user.setGroup(group);
		user.init();
        user=encryptUser(user);
        userExt=encryptUserExt(userExt);
		dao.save(user);
		cmsUserExtMng.save(userExt, user);
		return DecryptUser(user);
	}

	public void updateLoginInfo(Integer userId, String ip,Date loginTime,String sessionId) {
		CmsUser user = findById(userId);
		user=encryptUser(user);
		if (user != null) {
			user.setLoginCount(user.getLoginCount() + 1);
			if(StringUtils.isNotBlank(ip)){
				user.setLastLoginIp(ip);
			}
			if(loginTime!=null){
				user.setLastLoginTime(loginTime);
			}
			user.setSessionId(sessionId);
            user=DecryptUser(user);
		}
	}

	public void updateUploadSize(Integer userId, Integer size) {
		CmsUser user = findById(userId);
		user.setUploadTotal(user.getUploadTotal() + size);
		if (user.getUploadDate() != null) {
			if (CmsUser.isToday(user.getUploadDate())) {
				size += user.getUploadSize();
			}
		}
		user.setUploadDate(new java.sql.Date(System.currentTimeMillis()));
		user.setUploadSize(size);
	}
	
	public void updateUser(CmsUser user){
	    user=encryptUser(user);
		Updater<CmsUser>updater=new Updater<CmsUser>(user);
		dao.updateByUpdater(updater);
	}

	public boolean isPasswordValid(Integer id, String password) {
		return unifiedUserMng.isPasswordValid(id, password);
	}

	public void updatePwdEmail(Integer id, String password, String email) {
		CmsUser user = findById(id);
        user=encryptUser(user);
		if (!StringUtils.isBlank(email)) {
			user.setEmail(email);
		} else {
			user.setEmail(null);
		}
		unifiedUserMng.update(id, password, email);
	}

	public CmsUser saveAdmin(String username, String email, String password,
			String ip, boolean viewOnly, boolean selfAdmin, int rank,
			Integer groupId,Integer departmentId, Integer[] roleIds,Integer[] channelIds,
			Integer[] siteIds, Byte[] steps, Boolean[] allChannels, Boolean[] allControlChannels,
			CmsUserExt userExt) {
	    userExt=encryptUserExt(userExt);
		UnifiedUser unifiedUser = unifiedUserMng.save(username, email,
				password, ip);
		CmsUser user = new CmsUser();
		user.forAdmin(unifiedUser, viewOnly, selfAdmin, rank);

		CmsGroup group = null;
		CmsDepartment department=null;
		if (groupId != null) {
			group = cmsGroupMng.findById(groupId);
		} else {
			group = cmsGroupMng.getRegDef();
		}
		if(departmentId!=null){
			department=cmsDepartmentMng.findById(departmentId);
		}
		if (group == null) {
			throw new RuntimeException(
					"register default member group not setted!");
		}
		/*
		if (department == null) {
			throw new RuntimeException(
					"register default admin department not setted!");
		}
		*/
		user.setGroup(group);
		user.setDepartment(department);
        user=encryptUser(user);
		user.init();
		dao.save(user);
		cmsUserExtMng.save(userExt, user);
		if (roleIds != null) {
			for (Integer rid : roleIds) {
				user.addToRoles(cmsRoleMng.findById(rid));
			}
		}
		if (channelIds != null) {
			Channel channel;
			for (Integer cid : channelIds) {
				channel = channelMng.findById(cid);
				channel.addToUsers(user);
			}
		}
		if (siteIds != null) {
			CmsSite site;
			for (int i = 0, len = siteIds.length; i < len; i++) {
				site = cmsSiteMng.findById(siteIds[i]);
				cmsUserSiteMng.save(site, user, steps[i], allChannels[i],allControlChannels[i]);
			}
		}
		return DecryptUser(user);
	}

	public void addSiteToUser(CmsUser user, CmsSite site, Byte checkStep) {
        user=encryptUser(user);
		cmsUserSiteMng.save(site, user, checkStep, true,true);
	}

	public CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
			Integer groupId,Integer departmentId, Integer[] roleIds, Integer[] channelIds,
			Integer siteId, Byte step, Boolean allChannel,Boolean allControlChannel) {
	    ext=encryptUserExt(ext);
        bean=encryptUser(bean);
		CmsUser user = updateAdmin(bean, ext, password, groupId,departmentId, roleIds,
				channelIds);
		// 更新所属站点
		cmsUserSiteMng.updateByUser(user, siteId, step, allChannel,allControlChannel);
		return DecryptUser(user);
	}

	public CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
			Integer groupId,Integer departmentId, Integer[] roleIds, Integer[] channelIds,
			Integer[] siteIds, Byte[] steps, Boolean[] allChannels,Boolean[] allControlChannels) {
        ext=encryptUserExt(ext);
        bean=encryptUser(bean);
		CmsUser user = updateAdmin(bean, ext, password, groupId,departmentId,roleIds,channelIds);
		// 更新所属站点
		cmsUserSiteMng.updateByUser(user, siteIds, steps, allChannels,allControlChannels);

        return DecryptUser(user);
	}

	private CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
			Integer groupId,Integer departmentId,Integer[] roleIds, Integer[] channelIds) {
	    bean=encryptUser(bean);
	    ext=encryptUserExt(ext);
		Updater<CmsUser> updater = new Updater<CmsUser>(bean);
		updater.include("email");
		CmsUser user = dao.updateByUpdater(updater);
		user.setGroup(cmsGroupMng.findById(groupId));
		if(departmentId!=null){
			user.setDepartment(cmsDepartmentMng.findById(departmentId));
		}
		cmsUserExtMng.update(ext, user);
		// 更新角色
		user.getRoles().clear();
		if (roleIds != null) {
			for (Integer rid : roleIds) {
				user.addToRoles(cmsRoleMng.findById(rid));
			}
		}
		/*
		// 更新栏目权限
		Set<Channel> channels = user.getChannels();
		// 清除
		for (Channel channel : channels) {
			channel.getUsers().remove(user);
		}
		user.getChannels().clear();
		// 添加
		if (channelIds != null) {
			Channel channel;
			for (Integer cid : channelIds) {
				channel = channelMng.findById(cid);
				channel.addToUsers(user);
			}
		}
		*/
		unifiedUserMng.update(bean.getId(), password, bean.getEmail());
        return DecryptUser(user);
	}

	public CmsUser updateMember(Integer id, String email, String password,
			Boolean isDisabled, CmsUserExt ext, Integer groupId,Integer grain,Map<String,String>attr) {
	    ext=encryptUserExt(ext);
		CmsUser entity = findById(id);
		entity.setEmail(email);
		/*
		if (!StringUtils.isBlank(email)) {
			entity.setEmail(email);
		}
		*/
		if (isDisabled != null) {
			entity.setDisabled(isDisabled);
		}
		if (groupId != null) {
			entity.setGroup(cmsGroupMng.findById(groupId));
		}
		if(grain!=null){
			entity.setGrain(grain);
		}
		// 更新属性表
		if (attr != null) {
            Map<String, String> attrOrig = entity.getAttr();
            attrOrig.clear();
            attrOrig.putAll(attr);
        }
		entity=encryptUser(entity);
		cmsUserExtMng.update(ext, entity);
		unifiedUserMng.update(id, password, email);
		return DecryptUser(entity);
	}
	
	public CmsUser updateMember(Integer id, String email, String password,Integer groupId,String realname,String mobile,Boolean sex) {
		CmsUser entity = findById(id);
		CmsUserExt ext =entity.getUserExt();
		if (!StringUtils.isBlank(email)) {
			entity.setEmail(email);
		}
		if (groupId != null) {
			entity.setGroup(cmsGroupMng.findById(groupId));
		}
		if (!StringUtils.isBlank(realname)) {
			ext.setRealname(realname);
		}
		if (!StringUtils.isBlank(mobile)) {
			ext.setMobile(mobile);
		}
		if (sex!=null) {
			ext.setGender(sex);
		}
		ext=encryptUserExt(ext);
		entity=encryptUser(entity);
		cmsUserExtMng.update(ext, entity);
		unifiedUserMng.update(id, password, email);
		return DecryptUser(entity);
	}
	
	public CmsUser updateUserConllection(CmsUser user,Integer cid,Integer operate){
	    user=encryptUser(user);
		Updater<CmsUser> updater = new Updater<CmsUser>(user);
		user = dao.updateByUpdater(updater);
		if (operate.equals(1)) {
			user.addToCollection(contentMng.findById(cid));
		}// 取消收藏
		else if (operate.equals(0)) {
			user.delFromCollection(contentMng.findById(cid));
		}
		return DecryptUser(user);
	}

	public CmsUser deleteById(Integer id) {
		//清除流程轨迹
		List<CmsWorkflowEvent>events=workflowEventMng.getListByUserId(id);
		for(CmsWorkflowEvent event:events){
			workflowEventMng.deleteById(event.getId());
		}
		unifiedUserMng.deleteById(id);
		CmsUser bean = dao.deleteById(id);
		//删除收藏信息
		bean.clearCollection();
		return DecryptUser(bean);
	}

	public CmsUser[] deleteByIds(Integer[] ids) {
		CmsUser[] beans = new CmsUser[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
            beans[i]=DecryptUser(beans[i]);
		}
        return beans;
	}

	public boolean usernameNotExist(String username) {
		return dao.countByUsername(username) <= 0;
	}

	public boolean mobileNotExist(String mobile){
		return dao.countByMobile(NptHttpUtil.decode(mobile)) <= 0;
	}
	
	public boolean usernameNotExistInMember(String username){
		return dao.countMemberByUsername(username)<= 0;
	}

	public boolean emailNotExist(String email) {
		return dao.countByEmail(email) <= 0;
	}

	private CmsUserSiteMng cmsUserSiteMng;
	private CmsSiteMng cmsSiteMng;
	private ChannelMng channelMng;
	private CmsRoleMng cmsRoleMng;
	private CmsDepartmentMng cmsDepartmentMng;
	private CmsGroupMng cmsGroupMng;
	private UnifiedUserMng unifiedUserMng;
	private CmsUserExtMng cmsUserExtMng;
	private CmsUserDao dao;
	@Autowired
	private ContentMng contentMng;
	@Autowired
	private CmsWorkflowEventMng workflowEventMng;

	@Autowired
	public void setCmsUserSiteMng(CmsUserSiteMng cmsUserSiteMng) {
		this.cmsUserSiteMng = cmsUserSiteMng;
	}

	@Autowired
	public void setCmsSiteMng(CmsSiteMng cmsSiteMng) {
		this.cmsSiteMng = cmsSiteMng;
	}

	@Autowired
	public void setChannelMng(ChannelMng channelMng) {
		this.channelMng = channelMng;
	}

	@Autowired
	public void setCmsRoleMng(CmsRoleMng cmsRoleMng) {
		this.cmsRoleMng = cmsRoleMng;
	}
	
	@Autowired
	public void setCmsDepartmentMng(CmsDepartmentMng cmsDepartmentMng) {
		this.cmsDepartmentMng = cmsDepartmentMng;
	}

	@Autowired
	public void setUnifiedUserMng(UnifiedUserMng unifiedUserMng) {
		this.unifiedUserMng = unifiedUserMng;
	}

	@Autowired
	public void setCmsUserExtMng(CmsUserExtMng cmsUserExtMng) {
		this.cmsUserExtMng = cmsUserExtMng;
	}

	@Autowired
	public void setCmsGroupMng(CmsGroupMng cmsGroupMng) {
		this.cmsGroupMng = cmsGroupMng;
	}

	@Autowired
	public void setDao(CmsUserDao dao) {
		this.dao = dao;
	}


    public static  List<CmsUser> DecryptUserList(List<CmsUser> list){
        List<CmsUser> list2 = new ArrayList<CmsUser>();
        int len = list.size();
        for (int i = 0; i < len; i++) {
            CmsUser user = list.get(i);
            list2.add(DecryptUser(user));
        }
        return list2;
    }
    public static  CmsUser DecryptUser(CmsUser user){//解密user扩展字段
        if(user==null){
            return user;
        }
        CmsUserExt ext=user.getUserExt();
        if(ext==null){
            return user;
        }
        //修改内部数据
        Set<CmsUserExt> set = user.getUserExtSet();
        set.clear();
        set.add(DecryptUserExt(ext));

        return user;
    }

    public static CmsUser encryptUser(CmsUser user){
        if(user==null){
            return user;
        }
        CmsUserExt ext=user.getUserExt();
        if(ext==null){
            return user;
        }

        //修改内部数据
        Set<CmsUserExt> set = user.getUserExtSet();
        set.clear();
        set.add(encryptUserExt(ext));

        return user;
    }

    private static  CmsUserExt DecryptUserExt(CmsUserExt userExt){

        if(userExt==null){
            return userExt;
        }

        CmsUserExt ext=userExt.copyToNewUserExt();
        String idCard = ext.getIdCard();
        String mobile = ext.getMobile();
        try {
            if(idCard!=null && !"".equals(idCard)){
                ext.setIdCard(NptHttpUtil.decode(idCard));
            }
            if(mobile!=null && !"".equals(mobile)){
                ext.setMobile(NptHttpUtil.decode(mobile));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ext;
    }


    private static CmsUserExt encryptUserExt(CmsUserExt userExt){

        if(userExt==null){
            return userExt;
        }
        CmsUserExt ext=userExt.copyToNewUserExt();
        String idCard = ext.getIdCard();
        String mobile = ext.getMobile();
        try {
            if(idCard!=null && !"".equals(idCard)){
                ext.setIdCard(NptHttpUtil.encode(idCard));
            }
            if(mobile!=null && !"".equals(mobile)){
                ext.setMobile(NptHttpUtil.encode(mobile));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ext;
    }
}