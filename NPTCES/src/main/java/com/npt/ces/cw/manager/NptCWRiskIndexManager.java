package com.npt.ces.cw.manager;

import com.npt.ces.cw.entity.NptCWRiskIndex;
import org.summer.extend.manager.BaseManager;

import java.util.List;

/**
 * 项目: NPTCES
 * 作者: 张磊
 * 日期: 2017/07/17 下午07:46
 * 备注:
 */
public interface NptCWRiskIndexManager extends BaseManager<NptCWRiskIndex> {

    Integer LAST_DEFAULT = 24;

    void saveOrUpdate(NptCWRiskIndex riskIndex);


    List<NptCWRiskIndex> listLastRiskIndex(Integer num);

}

