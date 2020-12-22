package com.npt.rms.log.manager.impl;

import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.base.manager.impl.NptCachedManagerImpl;
import com.npt.rms.log.dao.NptLogDao;
import com.npt.rms.log.entity.NptLog;
import com.npt.rms.log.manager.NptLogManager;
import com.npt.rms.util.NptRmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.summer.extend.cache.ICache;

import javax.annotation.Resource;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/26 20:35
 * 备注：
 */
@Service("nptLogManager")
public class NptLogManagerImpl extends NptCachedManagerImpl<NptLog> implements NptLogManager{

    @Autowired
    private NptLogDao logDao;
    @Resource(name = "nptLogCache")
    private ICache cache;

    public ICache getCache() {
        return cache;
    }

    public void setCache(ICache cache) {
        this.cache = cache;
    }

    @Override
    public NptLog makeLog() {
        NptLog log = new NptLog();

        log.setParentId(NptCommonUtil.getDefaultParentId());
        log.setCreateTime(NptCommonUtil.getCurrentSysDate());
        log.setResultCode(NptDict.RST_SUCCESS.getCode());
        log.setStatus(NptDict.IDS_ENABLED.getCode());
        log.setCreatorId(NptRmsUtil.getCurrentUserId());
        log.setUserName(NptRmsUtil.getCurrentUserName());
        return log;
    }

    @Override
    protected ICache getLocalEhcache() {
        return getCache();
    }
}
