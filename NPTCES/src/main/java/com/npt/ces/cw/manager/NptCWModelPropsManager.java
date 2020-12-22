package com.npt.ces.cw.manager;

import com.npt.ces.cw.entity.NptCWModelProps;
import org.summer.extend.manager.BaseManager;

/**
 * 项目: NPTCES
 * 作者: 张磊
 * 日期: 2017/07/12 下午03:36
 * 备注:
 */
public interface NptCWModelPropsManager extends BaseManager<NptCWModelProps> {
    void updateModelProps(NptCWModelProps modelProps);
}

