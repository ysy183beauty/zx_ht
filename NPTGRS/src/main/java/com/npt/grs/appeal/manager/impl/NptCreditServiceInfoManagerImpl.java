package com.npt.grs.appeal.manager.impl;

import com.npt.grs.appeal.entity.NptCreditServiceInfo;
import com.npt.grs.appeal.manager.NptCreditServiceInfoManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.manager.BaseManagerImpl;

/**
 * 项目: NPTGRS
 * 作者: 张磊
 * 日期: 2017/03/21 下午11:28
 * 备注:
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NptCreditServiceInfoManagerImpl extends BaseManagerImpl<NptCreditServiceInfo> implements NptCreditServiceInfoManager {
}

