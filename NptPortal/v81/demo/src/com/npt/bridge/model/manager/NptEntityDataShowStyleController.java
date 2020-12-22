package com.npt.bridge.model.manager;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.dataBinder.NptWebFieldDataArray;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/3/28
 * 备注:
 */
public interface NptEntityDataShowStyleController {
    /**
     *作者：owen
     *时间：2016/12/12 14:06
     *描述:
     *      依据字段的显示类别,字段是否已授权等控制信息,将字段的真实数据处理之后写入到字段数据实体类中
     */
    void makeValueShowStyle(Object value, boolean authed, NptLogicDataField field, NptWebFieldDataArray.NptWebFieldData fieldData);
}
