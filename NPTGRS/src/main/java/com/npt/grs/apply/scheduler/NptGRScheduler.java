package com.npt.grs.apply.scheduler;

import com.npt.bridge.dict.NptDict;
import com.npt.grs.apply.entity.NptResourceApply;
import com.npt.grs.apply.manager.NptBusinessFlowLogManager;
import com.npt.grs.apply.manager.NptResourceApplyManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/10 19:08
 * 备注：
 */
//@Component
public class NptGRScheduler implements InitializingBean{

    @Autowired
    private NptResourceApplyManager applyManager;

    @Autowired
    private NptBusinessFlowLogManager applyLogManager;

    /**
     * 作者：owen
     * 日期：2016/10/31 16:00
     * 备注：
     *      每天凌晨0点确认资源申请是否过期
     * 参数：
     * 返回：
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void expireResourceApply(){

        Collection<NptResourceApply> applyList = applyManager.getResourceAcceptedApply();
        if(null != applyList && !applyList.isEmpty()){
            for(NptResourceApply apply:applyList){
                if(NptGRScheduler.isExpired(apply)){
                    apply.setApplyStatus(NptDict.RAS_EXPIRED.getCode());

                    applyManager.update(apply);
                    applyLogManager.addApplyLog(apply,null);
                }
            }
        }
    }

    /**
     * 作者：owen
     * 日期：2016/10/31 16:22
     * 备注：
     *      确认是否已过期
     * 参数：
     * 返回：
     */
     public static boolean isExpired(NptResourceApply apply){
        Date endData = apply.getConfirmedEndDate();
        if(null == endData){
            endData = apply.getApplyedEndDate();
        }

        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(endData);
        int limitDay = aCalendar.get(Calendar.DAY_OF_YEAR);

        aCalendar.setTime(new Date());
        int today = aCalendar.get(Calendar.DAY_OF_YEAR);

        return 1 == today - limitDay;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
