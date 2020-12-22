package com.npt.ces.cw.manager.impl;

import com.npt.ces.cw.entity.NptCWResult;
import com.npt.ces.cw.manager.NptCWResultManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.manager.BaseManagerImpl;

/**
 * 项目: NPTCES
 * 作者: 张磊
 * 日期: 2017/07/06 下午04:38
 * 备注:
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NptCWResultManagerImpl extends BaseManagerImpl<NptCWResult> implements NptCWResultManager {
}

