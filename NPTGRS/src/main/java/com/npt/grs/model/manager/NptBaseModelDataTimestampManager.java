package com.npt.grs.model.manager;

import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModelPoolStamp;
import org.summer.extend.manager.BaseManager;

import java.sql.Timestamp;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2016/12/14 11:42
 * 描述:
 */
public interface NptBaseModelDataTimestampManager extends BaseManager<NptBaseModelPoolStamp>{

    /**
     *作者：owen
     *时间：2016/12/14 11:50
     *描述:
     *      获取指定用途的数据池的最后修改时间以便进行增量提取
     */
    NptBaseModelPoolStamp getUniqueTimestamp(Long poolId, NptDict ub);

    /**
     *作者：owen
     *时间：2016/12/14 12:02
     *描述:
     *      更新最后修改时间
     */
    void updateTimestamp(Long poolId, NptDict ub, Timestamp timestamp);
}
