package com.npt.grs.scheduler.inside;

import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dict.NptDict;
import com.npt.grs.apply.entity.NptResourceApply;
import com.npt.grs.apply.manager.NptBusinessFlowLogManager;
import com.npt.grs.apply.manager.NptResourceApplyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.summer.extend.orm.condition.Conditions;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/4/19
 * 备注:
 */
@Component
public class NptResourceApplyWaitChecker {
    @Autowired
    private NptResourceApplyManager applyManager;
    @Autowired
    private NptBusinessFlowLogManager applyLogManager;

    /**
     * 作者: 张磊
     * 日期: 2017/04/19 下午02:31
     * 备注: 自动拒绝7天未受理的申请
     */
    @Scheduled(cron = "0 */10 * * * ?")
    public void execute() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -7);
        List<NptResourceApply> applyList = applyManager.findByCondition(
                Conditions.eq(NptResourceApply.PROPERTY_APPLY_STATUS, NptDict.RAS_WAITTING.getCode()),
                Conditions.le(NptBaseEntity.PROPERTY_CREATE_TIME, new Timestamp(c.getTime().getTime())));
        for (NptResourceApply apply : applyList) {
            apply.setApplyStatus(NptDict.RAS_REFUSED.getCode());
            applyManager.update(apply);
            applyLogManager.addApplyLog(apply, "等待7天无人受理");
        }
    }
}
