package com.npt.rms.remote.manager.impl;

import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dict.NptDict;
import com.npt.rms.remote.dao.NptRemoteSystemDao;
import com.npt.rms.remote.entity.NptRemoteSystem;
import com.npt.rms.remote.manager.NptRemoteSystemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.summer.extend.manager.BaseManagerImpl;
import org.summer.extend.orm.condition.Conditions;

import java.util.Collection;


/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/28 16:22
 * 描述:
 */
@Service
public class NptRemoteSystemManagerImpl extends BaseManagerImpl<NptRemoteSystem> implements NptRemoteSystemManager{
    @Autowired
    private NptRemoteSystemDao systemDao;

    /**
     * 作者：OWEN
     * 时间：2016/11/28 16:36
     * 描述:
     * 通过操作类型获取远程系统信息
     *
     * @param type
     */
    @Override
    public Collection<NptRemoteSystem> getRemoteSystemByActionType(NptDict type) {
        return this.findByCondition(
                Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()),
                Conditions.eq(NptRemoteSystem.PROPERTY_ACTION_TYPE,type.getCode()));
    }

    /**
     * 作者：owen
     * 时间：2016/12/15 11:25
     * 描述:
     * 初始化外部查询系统的信息
     */
    @Override
    public void resetRemoteSystem(String ip,Integer port) {
        Collection<NptRemoteSystem> existed = this.findAll();
        if (null != existed && !existed.isEmpty()) {
            for (NptRemoteSystem rs : existed) {
                rs.setSiteIp(ip);
                rs.setSitePort(port);
                rs.setStatus(NptDict.IDS_ENABLED.getCode());

                this.update(rs);
            }
        }else {
            this.save(new NptRemoteSystem(ip, port, "/credit/nptSync/syncProvider.do", NptDict.RMT_SYNC_DEPENDENCY.getCode()));
            this.save(new NptRemoteSystem(ip, port, "/credit/nptSync/syncModelStructure.do", NptDict.RMT_SYNC_STRUCTURE.getCode()));
            this.save(new NptRemoteSystem(ip, port, "/credit/nptSync/syncModelIncData.do", NptDict.RMT_SYNC_INCDATA.getCode()));
            this.save(new NptRemoteSystem(ip, port, "/credit/nptSync/syncService.do", NptDict.RMT_SYNC_CREDITSERVICE.getCode()));
            this.save(new NptRemoteSystem(ip, port, "/credit/nptSync/syncServiceOk.do", NptDict.RMT_SYNC_CREDITSERVICE_OK.getCode()));
            this.save(new NptRemoteSystem(ip, port, "/credit/nptSync/updateService.do", NptDict.RMT_SYNC_CREDITSERVICE_REP.getCode()));
            this.save(new NptRemoteSystem(ip, port, "/credit/nptSync/syncUser.do", NptDict.RMT_SYNC_CMSUSER.getCode()));
            this.save(new NptRemoteSystem(ip, port, "/credit/nptSync/syncOk.do", NptDict.RMT_SYNC_CMSUSER_OK.getCode()));
            this.save(new NptRemoteSystem(ip, port, "/credit/nptSync/update.do", NptDict.RMT_SYNC_CMSUSER_REP.getCode()));
            this.save(new NptRemoteSystem(ip, port, "/credit/nptSync/syncApplyInfo.do", NptDict.RMT_SYNC_APPLY.getCode()));
            this.save(new NptRemoteSystem(ip, port, "/credit/nptSync/synApplyInfocOk.do", NptDict.RMT_SYNC_APPLY_OK.getCode()));
            this.save(new NptRemoteSystem(ip, port, "/credit/nptSync/updateApplyData.do", NptDict.RMT_SYNC_APPLY_REP.getCode()));
            this.save(new NptRemoteSystem(ip, port, "/credit/nptSync/syncTest.do", NptDict.RMT_SYNC_TEST_REP.getCode()));
        }
    }
}
