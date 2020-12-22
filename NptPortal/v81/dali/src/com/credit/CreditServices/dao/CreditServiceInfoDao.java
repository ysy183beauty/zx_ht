package com.credit.CreditServices.dao;

import com.credit.CreditServices.entity.CreditServiceInfo;
import com.jeecms.common.page.Pagination;
import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.dict.NptDict;

import java.util.Collection;

/**
 * 项目: zxcms
 * 作者: 张磊
 * 日期: 17/3/7 下午4:31
 * 备注:
 */
public interface CreditServiceInfoDao extends NptBaseDao<CreditServiceInfo, Long> {
    /**
     * 作者: 张磊
     * 日期: 17/3/7 下午8:44
     * 备注:
     */
    Pagination getPage(int userId, String flag, int pageNo, int pageSize);

    CreditServiceInfo detail(int userId, Long id);

    /**
     * 作者: 张磊
     * 日期: 2017/03/22 上午11:06
     * 备注: 根据状态查
     */
    Collection<CreditServiceInfo> findByStatus(NptDict code);
}
