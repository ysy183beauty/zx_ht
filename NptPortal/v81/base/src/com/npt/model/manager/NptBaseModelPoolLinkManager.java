package com.npt.model.manager;

import com.npt.base.NptBaseManager;
import com.npt.dict.NptDict;
import com.npt.model.entity.NptBaseModelPool;
import com.npt.model.entity.NptBaseModelPoolLink;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:01
 * 描述:
 */
public interface NptBaseModelPoolLinkManager extends NptBaseManager<NptBaseModelPoolLink>{

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午5:29
     * 备注: 检测当前数据池是否有关联的数据池
     */
    Integer getBaseModelGrouPoolLinkCount(NptBaseModelPool p, NptDict status);

    /**
     * 作者: 张磊
     * 日期: 17/2/16 上午10:9
     * 备注:
     */
    Collection<NptBaseModelPoolLink> getBaseModelGroupoolLinkedPools(NptBaseModelPool pool, NptDict idsEnabled);

    /**
     * 作者: 张磊
     * 日期: 17/2/17 下午2:51
     * 备注: 查询关联向指定数据池的其它数据池, status为null则表示全部状态
     */
    Collection<NptBaseModelPoolLink> getBaseModelGroupoolLinkedMePools(NptBaseModelPool p, NptDict status);
}
