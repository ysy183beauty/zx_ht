package com.npt.rms.util.listener;

import com.npt.bridge.dict.NptDict;
import com.npt.rms.log.entity.NptLog;
import com.npt.rms.log.manager.NptLogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.summer.core.context.PlatformEvent;
import org.summer.core.context.PlatformListener;
import org.summer.extend.security.PlatformSessionInformation;
import org.summer.extend.security.UserLoginEvent;
import org.summer.extend.security.UserLogoutEvent;
import org.summer.springsecurity.details.impl.PlatformUserDetailsImpl;

/**
 * 项目: NPTRMS
 * 作者: 赵虎
 * 日期: 16/9/30 上午11:45
 * 备注: 获取事件
 */
@Component
public class NptApplicationListener implements PlatformListener{

    @Autowired
    private NptLogManager logManager;


    @Override
    public void onPlatformEvent(PlatformEvent platformEvent) {

        if(platformEvent instanceof UserLoginEvent){
            NptLog log = logManager.makeLog();
            UserLoginEvent le = (UserLoginEvent) platformEvent;

            PlatformUserDetailsImpl userDetails = (PlatformUserDetailsImpl) le.getPlatformSessionInformation().getPrincipal();

            String ipAddress = le.getPlatformSessionInformation().getRemovteHost();
            if(null != ipAddress && ipAddress.length() > 0){
                log.setAlias("用户[" + userDetails.getUsername() + "]已登录");

                log.setInvokeIP(ipAddress);

                log.setBusinessName(NptDict.LGB_APP.getTitle());
                log.setActionName(NptDict.LGA_LOGIN.getTitle());
                log.setBusinessType(NptDict.LGB_APP.getCode());
                log.setActionType(NptDict.LGA_LOGIN.getCode());
                log.setCreatorId((Long) userDetails.getOperatorUser().getOperatorId());

                logManager.save(log);
            }
        }else if(platformEvent instanceof UserLogoutEvent){

            NptLog log = logManager.makeLog();
            UserLogoutEvent le = (UserLogoutEvent) platformEvent;

            PlatformUserDetailsImpl userDetails = (PlatformUserDetailsImpl) le.getSessionInformation().getPrincipal();

            PlatformSessionInformation psi = (PlatformSessionInformation) le.getSessionInformation();

            String ipAddress = psi.getRemovteHost();

            if(null == ipAddress){
                log.setBusinessName(NptDict.LGB_APP.getTitle());
                log.setActionName(NptDict.LGA_LOGOUT.getTitle());
                log.setBusinessType(NptDict.LGB_APP.getCode());
                log.setActionType(NptDict.LGA_LOGOUT.getCode());
                log.setAlias("用户[" + userDetails.getUsername() + "]已登出");
                log.setCreatorId((Long) userDetails.getOperatorUser().getOperatorId());

                logManager.save(log);
            }
        }
    }
}
