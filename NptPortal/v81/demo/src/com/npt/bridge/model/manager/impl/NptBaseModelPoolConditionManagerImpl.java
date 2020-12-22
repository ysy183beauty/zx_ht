package com.npt.bridge.model.manager.impl;

import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.base.manager.NptBaseManagerImpl;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.NptBaseModelPoolCdt;
import com.npt.bridge.model.dao.NptBaseModelPoolConditionDao;
import com.npt.bridge.model.manager.NptBaseModelPoolConditionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 作者: owen
 * 时间: 2017/3/13 下午9:50
 * 描述:
 */
@Service
@Transactional
public class NptBaseModelPoolConditionManagerImpl extends NptBaseManagerImpl<NptBaseModelPoolCdt, Long> implements NptBaseModelPoolConditionManager {

    @Autowired
    private NptBaseModelPoolConditionDao conditionDao;

    @Override
    public NptBaseDao<NptBaseModelPoolCdt, Long> getThisDao() {
        return conditionDao;
    }

    /**
     * 作者: owen
     * 时间: 2017/3/13 下午9:57
     * 描述:
     * 查询数据池的所有状态可用的查询字段
     *
     * @param p
     */
    @Override
    public Collection<NptBaseModelPoolCdt> getPoolSearchConditions(NptBaseModelPool p) {
        Collection<NptBaseModelPoolCdt> result = new ArrayList<>();
        Collection<NptBaseModelPoolCdt> searchR = conditionDao.getPoolSearchConditions(p);
        Collection<Long> fieldIds = new ArrayList<>();
        if(null != searchR && !searchR.isEmpty()){
            for(NptBaseModelPoolCdt c:searchR){
                if(!fieldIds.contains(c.getFieldId())){
                    result.add(c);
                    fieldIds.add(c.getFieldId());
                }
            }
        }
        return result;
    }
}
