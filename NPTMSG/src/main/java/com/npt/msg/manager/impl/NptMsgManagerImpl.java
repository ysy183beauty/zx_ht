package com.npt.msg.manager.impl;

import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.base.manager.NptBaseManagerImpl;
import com.npt.msg.dao.NptMsgDao;
import com.npt.msg.entity.NptMsgEntity;
import com.npt.msg.manager.NptMsgManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 项目： NPTWebApp
 * 作者： owen
 * 时间： 2017/6/6 17:24
 * 描述：
 */
@Service
@Transactional
public class NptMsgManagerImpl extends NptBaseManagerImpl<NptMsgEntity,Long> implements NptMsgManager{

    @Autowired
    private NptMsgDao msgDao;

    public NptBaseDao<NptMsgEntity, Long> getThisDao() {
        return msgDao;
    }
}
