package com.credit.CreditPublicity.service;

import com.credit.CreditPublicity.entity.QzqdEntity;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/4/1
 * 备注:
 */
public interface NptCredit2PublicityService {
    /**
     * 作者: 张磊
     * 日期: 2017/04/01 上午11:55
     * 备注: 获取pool的权责详情
     */
    QzqdEntity getProvince(Long poolId);
}
