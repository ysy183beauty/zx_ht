package com.npt.grs.appeal.manager;

import com.npt.bridge.dict.NptDict;
import com.npt.bridge.sync.entity.NptUserAppeal;
import org.summer.extend.manager.BaseManager;
import org.summer.extend.orm.Pagination;

import java.util.Date;
import java.util.List;

/**
 * 项目: NPTGRS
 * 作者: 张磊
 * 日期: 16/11/2 下午3:57
 * 备注:
 */
public interface NptAppealInfoManager extends BaseManager<NptUserAppeal> {

    /**
     * 作者: xuqinyuan
     * 时间: 2016/11/24 15:53
     * 备注: 根据状态加载审批列表
     *
     *
     * @param dataTypeTitle
     * @param dict
     * @param beginIndex
     * @param pageSize
     * @return
     */
    Pagination<NptUserAppeal> getAppealByCondition(Integer role,Long approvalOrgId,String dataTypeTitle,NptDict dict,
                                                   int beginIndex,int pageSize);

    /**
     * 作者: xuqinyuan
     * 时间: 2016/11/24 15
     * 备注: 根据编号获取异议记录
     *
     * @param appealNo
     * @return
     */
    NptUserAppeal getAppealByNo(String appealNo);

    /**
     *作者: xuqinyuan
     *时间: 2016/11/25 13:50
     *备注: 管理中心处理异议信息
     *
     * @param userId
     * @param appealNo
     * @param appealStatus
     * @return
     */
    NptDict handleAppealByCDC(Long userId, String appealNo, Integer appealStatus, Date[] frozenDate, String appealResult);

    /**
     *作者: xuqinyuan
     *时间: 2016/11/25 13:50
     *备注: 提供方处理异议信息
     *
     * @param userId
     * @param appealNo
     * @param frozenDate
     * @return
     */
    NptDict handleAppealByPRD(Long userId, String appealNo, Date frozenDate, List<Object> fields, List<Object> unPassFields);
}
