package com.npt.grs.scheduler.inside;

import com.npt.bridge.dict.NptDict;
import com.npt.grs.apply.entity.NptResourceApply;
import com.npt.grs.apply.manager.NptBusinessFlowLogManager;
import com.npt.grs.apply.manager.NptResourceApplyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2016/12/13 20:09
 * 描述:
 */
@Component
public class NptResourceApplyExpiredChecker{

    @Autowired
    private NptResourceApplyManager applyManager;
    @Autowired
    private NptBusinessFlowLogManager applyLogManager;

    /**
     * 作者：owen
     * 时间：2016/12/13 20:11
     * 描述:
     * 每一个定时任务的执行入口
     */
    @Scheduled(cron = "0 0 23 * * ?")
    public void execute() {
        Collection<NptResourceApply> applyList = applyManager.getResourceAcceptedApply();
        if(null != applyList && !applyList.isEmpty()){
            for(NptResourceApply apply:applyList){
                if(isExpired(apply)){
                    apply.setApplyStatus(NptDict.RAS_EXPIRED.getCode());

                    applyManager.update(apply);
                    applyLogManager.addApplyLog(apply,null);

                    System.out.println("资源申请号：[" + apply.getApplyNo() + "]已过期...");
                }
            }
        }
    }

     private boolean isExpired(NptResourceApply apply){
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
}
