package com.npt.sync;


import com.npt.bridge.util.NptHttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 *  author: owen
 *  date:   2017/4/26 14:07
 *  note:
 *          门户中需要定时处理的任务都写在此类中
 */
@Component
public class NptScheduler {

    @Autowired
    private Properties creditResources;


    /**
     *  author: owen
     *  date:   2017/4/26 14:07
     *  note:
     *          每天定时检测用户申请的个人实名数据是否已过期，若过期则删除之
     *
     *          每天23点执行
     *          0 0 23 * * ?
     */
    @Scheduled(cron = "0 0 23 * * ?")
    public void checkExpiredAuthData(){


        String hostIP = creditResources.getProperty("CPC_LOCAL_IP");
        String hostPort = creditResources.getProperty("CPC_ACCESS_PORT");

        String actionUrl= creditResources.getProperty("CPC_CHECK_EXPIRED_AUTHDATA");

        String url = "http://" + hostIP + ":" + hostPort + actionUrl;

        NptHttpUtil.httpPost(url,"");
    }
}
