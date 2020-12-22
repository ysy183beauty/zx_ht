package com.npt.rms.auth.manager;

import com.npt.rms.auth.entity.NptSimpleUser;
import org.summer.extend.manager.BaseManager;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/11 15:41
 * 描述:
 */
public interface NptSimpleUserManager extends BaseManager<NptSimpleUser> {

    /**
     *作者：OWEN
     *时间：2016/11/11 15:44
     *描述:
     *      通过登录名查询用户基本信息
     */
    Collection<NptSimpleUser> findByLoginName(String name);

    /**
     *作者：OWEN
     *时间：2016/11/11 15:54
     *描述:
     *      禁用用户
     */
    void disable(Long userId);

    /**
     *作者：OWEN
     *时间：2016/11/11 15:54
     *描述:
     *      启用用户
     */
    void enable(Long userId);

    /**
     *  author: zhanglei
     *  date:   2017/06/28 16:31
     *  note:
     *          创建sso用户
     */
    void createSsoUser(String loginName, String userName);
}
