package com.npt.grs.apply.manager.impl;

import com.npt.bridge.sync.entity.CreditApplyInfo;
import com.npt.grs.apply.manager.CreditApplyInfoManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.manager.BaseManagerImpl;

/**
 * 项目: NPTGRS
 * 作者: 张磊
 * 日期: 2017/03/25 下午03:26
 * 备注:
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CreditApplyInfoManagerImpl extends BaseManagerImpl<CreditApplyInfo> implements CreditApplyInfoManager {
}

