package com.npt.ces.cw.manager.impl;

import com.npt.ces.cw.entity.NptCWModelSubDmsProps;
import com.npt.ces.cw.manager.NptCWModelSubDmsPropsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.manager.BaseManagerImpl;

/**
 * 项目: NPTCES
 * 作者: 张磊
 * 日期: 2017/07/13 上午10:26
 * 备注:
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NptCWModelSubDmsPropsManagerImpl extends BaseManagerImpl<NptCWModelSubDmsProps> implements NptCWModelSubDmsPropsManager {
    @Override
    public void updateSubDmsProps(NptCWModelSubDmsProps subDmsProps) {
        NptCWModelSubDmsProps result = this.findById(subDmsProps.getId());
        subDmsProps.copyTo(result);
        this.update(result);
    }
}

