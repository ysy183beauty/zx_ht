package com.npt.grs.credit.manager.impl;

import com.npt.bridge.sync.entity.CreditCmsUser;
import com.npt.grs.credit.manager.CreditCmsUserManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.manager.BaseManagerImpl;

/**
 * 项目: NPTGRS
 * 作者: 张磊
 * 日期: 2017/03/23 上午12:24
 * 备注:
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CreditCmsUserManagerImpl extends BaseManagerImpl<CreditCmsUser> implements CreditCmsUserManager {
}

