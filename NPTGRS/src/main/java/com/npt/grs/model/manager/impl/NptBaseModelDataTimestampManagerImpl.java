package com.npt.grs.model.manager.impl;

import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModelPoolStamp;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.grs.model.manager.NptBaseModelDataTimestampManager;
import com.npt.rms.util.NptRmsUtil;
import org.springframework.stereotype.Service;
import org.summer.extend.manager.BaseManagerImpl;
import org.summer.extend.orm.condition.Conditions;

import java.sql.Timestamp;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2016/12/14 11:43
 * 描述:
 */
@Service
public class NptBaseModelDataTimestampManagerImpl extends BaseManagerImpl<NptBaseModelPoolStamp> implements NptBaseModelDataTimestampManager {
    /**
     * 作者：owen
     * 时间：2016/12/14 11:50
     * 描述:
     * 获取指定用途的数据池的最后修改时间以便进行增量提取
     *
     * @param poolId
     * @param ub
     */
    @Override
    public NptBaseModelPoolStamp getUniqueTimestamp(Long poolId, NptDict ub) {
        if(null == poolId || null == ub){
            return null;
        }
        return  this.findUniqueByCondition(
                Conditions.eq(NptBaseModelPoolStamp.POOL_ID,poolId),
                Conditions.eq(NptBaseModelPoolStamp.USE_BY,ub.getCode()));
    }

    /**
     * 作者：owen
     * 时间：2016/12/14 12:02
     * 描述:
     * 更新最后修改时间
     *
     * @param poolId
     * @param ub
     */
    @Override
    public void updateTimestamp(Long poolId, NptDict ub, Timestamp last) {
        if(null == poolId || null == ub || null == last){
            return;
        }
        NptBaseModelPoolStamp timestamp = this.getUniqueTimestamp(poolId,ub);
        if(null != timestamp){
            timestamp.setLastTime(last);
            this.update(timestamp);
        }else {
            timestamp = new NptBaseModelPoolStamp();
            timestamp.setLastTime(last);
            timestamp.setPoolId(poolId);
            timestamp.setUseBy(ub.getCode());
            timestamp.setCreatorId(NptRmsUtil.getCurrentUserId());
            timestamp.setCreateTime(NptCommonUtil.getCurrentSysDate());
            timestamp.setStatus(NptDict.IDS_ENABLED.getCode());
            this.save(timestamp);
        }
    }
}
