package com.npt.grs.appeal.manager;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.sync.entity.NptUserAppeal;
import com.npt.bridge.sync.entity.NptUserAppealDetail;
import org.summer.extend.manager.BaseManager;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：97175
 * 日期：2016/11/7 12:10
 * 备注：
 */
public interface NptAppealDetailManager extends BaseManager<NptUserAppealDetail>{

    /**
     *作者: xuqinyuan
     *时间: 2016/11/24 19:12
     *备注: 根据编号获取异议字段集合
     *@param appealNo
     * @return
     */
    Collection<NptUserAppealDetail> getAppealDetailByNo(String appealNo);

    /**
     *作者: xuqinyuan
     *时间: 2016/11/24 19:20
     *备注: 根据编号获取字段详情
     *
     * @param appealNo
     * @return
     */
    Collection<NptLogicDataField> getFieldByNo(String appealNo);

    /**
     *作者: xuqinyuan
     *时间: 2016/11/24 19:33
     *备注: 根据编号查询异议详情
     *
     * @param appeal
     * @return
     */
    Object queryAppealFieldData(NptUserAppeal appeal);
}
