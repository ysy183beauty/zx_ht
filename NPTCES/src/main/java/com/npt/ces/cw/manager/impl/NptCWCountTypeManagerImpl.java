package com.npt.ces.cw.manager.impl;

import com.npt.ces.cw.entity.NptCWCountType;
import com.npt.ces.cw.manager.NptCWCountTypeManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.manager.BaseManagerImpl;

/**
 * 项目: NPTCES
 * 作者: 张磊
 * 日期: 2017/07/18 下午03:27
 * 备注:
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NptCWCountTypeManagerImpl extends BaseManagerImpl<NptCWCountType> implements NptCWCountTypeManager {
    @Override
    public void updateCountType(NptCWCountType countType) {
        NptCWCountType result = this.findById(countType.getId());
        countType.copyTo(result);
        this.update(result);
    }
}

