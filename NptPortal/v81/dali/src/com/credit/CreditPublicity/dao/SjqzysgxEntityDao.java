package com.credit.CreditPublicity.dao;

import com.credit.CreditPublicity.entity.SjqzysgxEntity;
import com.npt.bridge.base.NptBaseDao;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/4/1
 * 备注:
 */
public interface SjqzysgxEntityDao extends NptBaseDao<SjqzysgxEntity, Long> {
    /**
     * 作者: 张磊
     * 日期: 2017/04/01 上午11:59
     * 备注:
     */
    SjqzysgxEntity findByPoolId(Long poolId);
}
