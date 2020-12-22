package com.npt.ces.cw.manager.impl;

import com.npt.ces.cw.entity.NptCWRiskIndex;
import com.npt.ces.cw.manager.NptCWRiskIndexManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.manager.BaseManagerImpl;
import org.summer.extend.orm.Pagination;
import org.summer.extend.orm.condition.Conditions;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目: NPTCES
 * 作者: 张磊
 * 日期: 2017/07/17 下午07:46
 * 备注:
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NptCWRiskIndexManagerImpl extends BaseManagerImpl<NptCWRiskIndex> implements NptCWRiskIndexManager {
    @Override
    public List<NptCWRiskIndex> listLastRiskIndex(Integer num) {

        List<NptCWRiskIndex> riskIndices = new ArrayList<>();

        Pagination<NptCWRiskIndex> searchResult = findAll(0,null != num?num:NptCWRiskIndexManager.LAST_DEFAULT,Conditions.desc(NptCWRiskIndex.PROPERTY_COMPUTE_DAY));
        if(null != searchResult && null != searchResult.getResults()){

            searchResult.getResults().forEach(ri -> ri.makeYearAndWeekNO());
            riskIndices.addAll(searchResult.getResults());
        }

        return riskIndices;
    }

    @Override
    public void saveOrUpdate(NptCWRiskIndex riskIndex) {

        try {
            if(null != riskIndex) {
                NptCWRiskIndex ri = findUniqueByCondition(Conditions.eq(NptCWRiskIndex.PROPERTY_BATCH_NO, riskIndex.getBatchNO()));
                if(null == ri){
                    save(riskIndex);
                }else {
                    ri.setRiskIndex(ri.getRiskIndex() + riskIndex.getRiskIndex());
                    update(ri);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

