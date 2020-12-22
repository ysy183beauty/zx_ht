package com.npt.bridge.model.manager.impl;

import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.base.manager.NptBaseManagerImpl;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModelLink;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.dao.NptBaseModelPoolLinkDao;
import com.npt.bridge.model.manager.NptBaseModelPoolLinkManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:11
 * 描述:
 */
@Service
@Transactional
public class NptBaseModelPoolLinkManagerImpl extends NptBaseManagerImpl<NptBaseModelLink, Long> implements NptBaseModelPoolLinkManager {

    @Autowired
    private NptBaseModelPoolLinkDao poolLinkDao;

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午5:30
     * 备注: 检测当前数据池是否有关联的数据池
     */
    @Override
    public Integer getBaseModelGrouPoolLinkCount(NptBaseModelPool p, NptDict status) {
        if (p == null) return 0;
        return poolLinkDao.getBaseModelGrouPoolLinkCount(p, status);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/16 上午10:9
     * 备注:
     */
    @Override
    public Collection<NptBaseModelLink> getBaseModelGroupoolLinkedPools(NptBaseModelPool pool, NptDict idsEnabled) {
        if (pool == null) return null;
        return poolLinkDao.getBaseModelGroupoolLinkedPools(pool, idsEnabled);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/17 下午2:52
     * 备注: 查询关联向指定数据池的其它数据池, status为null则表示全部状态
     */
    @Override
    public Collection<NptBaseModelLink> getBaseModelGroupoolLinkedMePools(NptBaseModelPool p, NptDict status) {
        return poolLinkDao.getBaseModelGroupoolLinkedMePools(p, status);
    }

    @Override
    public NptBaseDao<NptBaseModelLink, Long> getThisDao() {
        return poolLinkDao;
    }
}
