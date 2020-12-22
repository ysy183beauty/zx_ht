package com.npt.grs.apply.manager;

import com.npt.grs.apply.entity.NptResourceApply;
import com.npt.grs.apply.entity.NptResourceApplyField;
import com.npt.bridge.arch.NptLogicDataField;
import org.summer.extend.manager.BaseManager;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/10 15:52
 * 备注：
 */
public interface NptResourceApplyFieldsManager extends BaseManager<NptResourceApplyField>{

    /**
     * 作者：owen
     * 日期：2016/10/30 17:46
     * 备注：
     *      根据申请号查询当前批次所申请的字段集合
     * 参数：
     * 返回：
     */
    Collection<NptResourceApplyField> getApplyFieldsByApplyNO(String no);

    /**
     * 作者：owen
     * 日期：2016/11/1 18:16
     * 备注：
     *      获取申请的字段详情
     * 参数：
     * 返回：
     */
    Collection<NptLogicDataField> getApplyFieldsDetailByApplyNO(String no);


    /**
     * 作者：owen
     * 日期：2016/11/2 14:43
     * 备注：
     *      以申请编号查询申请的字段的详细数据
     * 参数：
     * 返回：
     */
    Object queryApplyFieldData(NptResourceApply apply);
}
