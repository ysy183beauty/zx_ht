package com.npt.rms.remote.manager;

import com.npt.bridge.dict.NptDict;
import com.npt.rms.remote.entity.NptRemoteSystem;
import org.summer.extend.manager.BaseManager;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/28 16:21
 * 描述:
 */
public interface NptRemoteSystemManager extends BaseManager<NptRemoteSystem>{

    /**
     *作者：OWEN
     *时间：2016/11/28 16:36
     *描述:
     *      通过操作类型获取远程系统信息
     */
    Collection<NptRemoteSystem> getRemoteSystemByActionType(NptDict type);

    /**
     *作者：owen
     *时间：2016/12/15 11:25
     *描述:
     *      初始化外部查询系统的信息
     */
    void resetRemoteSystem(String ip,Integer port);

}
