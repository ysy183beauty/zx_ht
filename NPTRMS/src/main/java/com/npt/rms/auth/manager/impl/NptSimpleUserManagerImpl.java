package com.npt.rms.auth.manager.impl;

import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.auth.dao.NptSimpleUserDao;
import com.npt.rms.auth.entity.NptSimpleUser;
import com.npt.rms.auth.manager.NptSimpleUserManager;
import com.npt.rms.util.NptRmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.summer.extend.cache.ICache;
import org.summer.extend.cache.IElement;
import org.summer.extend.manager.BaseManagerImpl;
import org.summer.extend.orm.condition.Conditions;
import org.summer.security.entity.User;
import org.summer.security.service.UserService;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/11 15:41
 * 描述:
 */
@Service
public class NptSimpleUserManagerImpl extends BaseManagerImpl<NptSimpleUser> implements NptSimpleUserManager{
    @Autowired
    private NptSimpleUserDao userDao;
    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "nptSimpleUserCache")
    private ICache cache;

    public ICache getCache() {
        return cache;
    }

    public void setCache(ICache cache) {
        this.cache = cache;
    }

    @Override
    public NptSimpleUser save(NptSimpleUser entity) {
        if(null != entity){
            User suser = basicConvert(entity);

            suser.setCreateStamp(NptCommonUtil.getCurrentSysDate().getTime());
            suser.setCreator(String.valueOf(NptRmsUtil.getCurrentUserId()));

            User saved = userService.addUserContactGroup(suser);
            entity.setUserId(saved.getId());

            return entity;
        }
        return null;
    }

    @Override
    public NptSimpleUser update(NptSimpleUser entity) {
        if(null != entity){
            User suser = basicConvert(entity);

            suser.setModifyStamp(NptCommonUtil.getCurrentSysDate().getTime());
            suser.setModifier(String.valueOf(NptRmsUtil.getCurrentUserId()));

            userService.updateUser(suser);

            this.getCache().remove(getCacheKey(entity));
            this.getCache().put(getCacheKey(entity),entity);

            return entity;
        }
        return null;
    }

    private User basicConvert(NptSimpleUser entity){
        if(null != entity) {
            User suser;
            if (null ==entity.getUserId()){
             suser = new User();
            }
            else {
                suser = userService.findUserById(entity.getUserId());
            }
            suser.setField01(entity.getOrgId());
            suser.setUsername(entity.getLoginName());
            suser.setName(entity.getUserName());
            suser.setEmail(entity.getEmail());
            suser.setMobile(entity.getMobile());
            suser.setPassword(entity.getPassword());
            suser.setDescription(entity.getRemark());
            suser.setSex(entity.getSex());
            return suser;
        }
        return null;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/11 15:44
     * 描述:
     * 通过登录名查询用户基本信息
     *
     * @param name
     */
    @Override
    public Collection<NptSimpleUser> findByLoginName(String name) {
        return findByCondition(Conditions.eq(NptSimpleUser.PROPERTY_USER_LOGIN_NAME,name));
    }

    @Override
    public NptSimpleUser findById(Serializable id) {
        IElement elem = this.getCache().get(id);
        NptSimpleUser t = null;
        if(elem == null) {
            t = super.findById(id);
            this.getCache().put(id, t);
        } else {
            t = (NptSimpleUser) elem.getValue();
        }
        return t;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/11 15:54
     * 描述:
     * 禁用用户
     *
     * @param userId
     */
    @Override
    public void disable(Long userId) {
        if(userService.execDisabledUser(userId)){
            getCache().remove(userId);
            getCache().put(userId,findById(userId));
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/11 15:54
     * 描述:
     * 启用用户
     *
     * @param userId
     */
    @Override
    public void enable(Long userId) {
        if(userService.execEnabledUser(userId)){
            getCache().remove(userId);
            getCache().put(userId,findById(userId));
        }
    }

    /**
     *  author: zhanglei
     *  date:   2017/06/28 16:31
     *  note:
     *          创建sso用户
     */
    @Override
    public void createSsoUser(String loginName, String userName) {
        if (userService.findUserByUsername(loginName) != null) {
            return;
        }
        User sso = userService.findUserByUsername("sso");
        if (sso != null) {
            User newSso = new User();
            newSso.setName(userName);
            newSso.setUsername(loginName);
            Collection<String> roles = userService.searchUserRoles(sso.getId());

            userService.addUserContactRoleIds(newSso);
            userService.updateUserRoleName(newSso.getId(), roles.toArray(new String[roles.size()]));
            userService.execEnabledUser(newSso.getId());
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/28 21:43
     * 描述:
     * 若T是NptEntitySerializable的子类，则不需要实现该方法
     * <p>
     * 若不是，则必须要实现改方法
     *
     * @param var1
     */
    protected Serializable getCacheKey(NptSimpleUser var1) {
        return var1.getUserId();
    }
}
