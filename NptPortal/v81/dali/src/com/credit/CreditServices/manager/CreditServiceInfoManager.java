package com.credit.CreditServices.manager;

import com.credit.CreditServices.entity.CreditServiceInfo;
import com.jeecms.common.page.Pagination;
import com.npt.bridge.base.NptBaseManager;
import com.npt.bridge.dict.NptDict;

import java.util.Collection;

/**
 * 项目: zxcms
 * 作者: 张磊
 * 日期: 17/3/7 下午4:54
 * 备注:
 */
public interface CreditServiceInfoManager extends NptBaseManager<CreditServiceInfo, Long> {
    /**
     * 作者: 张磊
     * 日期: 17/3/7 下午8:44
     * 备注: 查看信用投诉/咨询列表
     */
    Pagination getPage(int userId, String flag, int pageNo, int pageSize);

    /**
     * 作者: 张磊
     * 日期: 2017/03/09 下午10:58
     * 备注: 查看信用投诉/咨询详情
     */
    CreditServiceInfo detail(int userId, Long id);

    /**
     * 作者: 张磊
     * 日期: 2017/03/22 上午11:06
     * 备注: 根据状态查
     */
    Collection<CreditServiceInfo> findByStatus(NptDict code);

    /**
     * 作者: 张磊
     * 日期: 2017/04/01 下午09:56
     * 备注: 查询附件路径
     */
    String getAttach(Integer userId, Long infoId);
}
