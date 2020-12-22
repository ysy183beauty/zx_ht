package com.npt.grs.model.manager.control;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.dataBinder.NptWebFieldDataArray;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2016/12/12 14:03
 * 描述:
 */
public interface NptEntityDataShowStyleController {
    /**
     *作者：owen
     *时间：2016/12/12 14:06
     *描述:
     *      依据字段的显示类别,字段是否已授权等控制信息,将字段的真实数据处理之后写入到字段数据实体类中
     */
    void makeValueShowStyle(Object value, boolean authed, NptLogicDataField field, NptWebFieldDataArray.NptWebFieldData fieldData);

    /**
     *作者：owen
     *时间：2016/12/12 15:10
     *描述:
     *      由于一些原因停止实体数据详情的查询,比如某数据已被锁定
     */
    Collection<NptWebFieldDataArray> stopLookingup(String title, Object value);
}
