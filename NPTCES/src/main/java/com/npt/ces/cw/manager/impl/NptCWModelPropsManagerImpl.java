package com.npt.ces.cw.manager.impl;

import com.npt.ces.cw.entity.NptCWModelProps;
import com.npt.ces.cw.manager.NptCWModelPropsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.manager.BaseManagerImpl;

/**
 * 项目: NPTCES
 * 作者: 张磊
 * 日期: 2017/07/12 下午03:36
 * 备注:
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NptCWModelPropsManagerImpl extends BaseManagerImpl<NptCWModelProps> implements NptCWModelPropsManager {
    @Override
    public void updateModelProps(NptCWModelProps modelProps) {
        NptCWModelProps result = this.findById(modelProps.getId());
        modelProps.copyTo(result);
        this.update(result);
    }
}

