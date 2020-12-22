package com.npt.ces.cw.manager.impl;

import com.npt.ces.cw.entity.NptCWModelDmsProps;
import com.npt.ces.cw.manager.NptCWModelDimensionPropsManager;
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
public class NptCWModelDimensionPropsManagerImpl extends BaseManagerImpl<NptCWModelDmsProps> implements NptCWModelDimensionPropsManager {
    @Override
    public void updateDimensionProps(NptCWModelDmsProps dimensionProps) {
        NptCWModelDmsProps result = this.findById(dimensionProps.getId());
        dimensionProps.copyTo(result);
        this.update(result);
    }
}

