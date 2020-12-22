package com.npt.rms.log.manager;


import com.npt.rms.base.manager.NptCachedManager;
import com.npt.rms.log.entity.NptLog;
import org.summer.extend.manager.BaseManager;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/26 21:07
 * 备注：
 */
public interface NptLogManager extends NptCachedManager<NptLog> {
    /**
     *作者：OWEN
     *时间：2016/11/28 16:48
     *描述:
     *      创建日志类
     */
    NptLog makeLog();

}
