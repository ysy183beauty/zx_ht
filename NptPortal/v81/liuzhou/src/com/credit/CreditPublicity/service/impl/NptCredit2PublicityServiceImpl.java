package com.credit.CreditPublicity.service.impl;

import com.credit.CreditPublicity.dao.QzqdEntityDao;
import com.credit.CreditPublicity.dao.SjqzysgxEntityDao;
import com.credit.CreditPublicity.entity.QzqdEntity;
import com.credit.CreditPublicity.entity.SjqzysgxEntity;
import com.credit.CreditPublicity.service.NptCredit2PublicityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/4/1
 * 备注:
 */
@Service
@Transactional
public class NptCredit2PublicityServiceImpl implements NptCredit2PublicityService {
    @Autowired
    private QzqdEntityDao qzqdEntityDao;
    @Autowired
    private SjqzysgxEntityDao sjqzysgxEntityDao;


    /**
     * 作者: 张磊
     * 日期: 2017/04/01 下午12:06
     * 备注:
     */
    @Override
    public QzqdEntity getProvince(Long poolId) {
        SjqzysgxEntity sjqzysgxEntity = sjqzysgxEntityDao.findByPoolId(poolId);
        if (sjqzysgxEntity == null || sjqzysgxEntity.getQzqdxh() == null) {
            return null;
        }
        return qzqdEntityDao.findById(sjqzysgxEntity.getQzqdxh());
    }
}
