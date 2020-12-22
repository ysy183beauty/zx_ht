package com.npt.sts.statistics.manager.impl;

import com.npt.sts.statistics.entity.NptStsIndex;
import com.npt.sts.statistics.manager.NptStsIndexManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.manager.BaseManagerImpl;

/**
 * 项目: NPTSTS
 * 作者: 张磊
 * 日期: 2016/12/06 下午05:37
 * 备注:
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NptStsIndexManagerImpl extends BaseManagerImpl<NptStsIndex> implements NptStsIndexManager {
}

