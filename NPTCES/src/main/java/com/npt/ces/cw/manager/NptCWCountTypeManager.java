package com.npt.ces.cw.manager;

import com.npt.ces.cw.entity.NptCWCountType;
import org.summer.extend.manager.BaseManager;

/**
 * 项目: NPTCES
 * 作者: 张磊
 * 日期: 2017/07/18 下午03:27
 * 备注:
 */
public interface NptCWCountTypeManager extends BaseManager<NptCWCountType> {
    void updateCountType(NptCWCountType countType);
}

