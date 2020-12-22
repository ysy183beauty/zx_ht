package com.npt.rms.base.manager;

import com.npt.bridge.base.NptEntitySerializable;
import org.summer.extend.manager.BaseManager;

import java.io.Serializable;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/28 21:24
 * 描述:
 *      基于ehcache的实体管理器
 */
public interface NptCachedManager<T extends NptEntitySerializable> extends BaseManager<T>{

    /**
     *作者：OWEN
     *时间：2016/12/7 23:24
     *描述:
     *      从缓存中快速获取实体
     *      若取得实体是为了更新，不要使用改方法，请使用findById
     */
    T fastFindById(Serializable id);
}
