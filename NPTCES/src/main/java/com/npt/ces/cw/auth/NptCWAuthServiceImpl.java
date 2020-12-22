package com.npt.ces.cw.auth;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.security.PlatformSecurityContext;
import org.summer.security.entity.User;

/**
 * author: owen
 * date:   2017/7/17 下午5:28
 * note:
 */
@Service
@Transactional
public class NptCWAuthServiceImpl implements NptCWAuthSerivce{
    @Override
    public NptCWUser getCurrentUser() {
        User currentUser = (User) PlatformSecurityContext.getCurrentOperator().getProxy();
        if(null != currentUser && currentUser.getEnable()){
            try {
                NptCWUser cwUser = new NptCWUser();
                cwUser.setUserName(currentUser.getName());
                cwUser.setUserOrgId(Long.parseLong(currentUser.getField01()));
                return cwUser;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
