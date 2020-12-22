package com.npt.rms.base.service;

import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dict.NptBusinessCode;
import com.npt.bridge.dict.NptDict;
import com.npt.rms.base.NptRmsCommonService;
import com.npt.rms.dict.manager.NptBusinessCodeManager;
import com.npt.rms.log.entity.NptLog;
import com.npt.rms.log.manager.NptLogManager;
import com.npt.rms.remote.entity.NptRemoteSystem;
import com.npt.rms.remote.manager.NptRemoteSystemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.summer.core.context.module.dependent.PublicBean;
import org.summer.extend.orm.Condition;
import org.summer.extend.orm.Pagination;
import org.summer.extend.orm.condition.Conditions;

import java.util.Collection;
import java.util.Date;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/28 16:32
 * 描述:
 */
@Service("rmsCommonService")
@Transactional
public class NptRmsCommonServiceImpl implements NptRmsCommonService,PublicBean{
    @Autowired
    private NptLogManager logManager;
    @Autowired
    private NptRemoteSystemManager remoteSystemManager;
    @Autowired
    private NptBusinessCodeManager businessCodeManager;

    @Override
    public NptLog makeLog() {
        return logManager.makeLog();
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/28 16:49
     * 描述:
     * 保存日志信息
     *
     * @param log
     */
    @Override
    public void save(NptLog log) {
        logManager.save(log);
    }

    @Override
    public Pagination<NptLog> findAllLog(Integer beginIndex, Integer pageSize, Condition... conditions) {
        return logManager.findAll(beginIndex, pageSize, conditions);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/28 16:50
     * 描述:
     * 通过操作类别获取远程系统信息
     *
     * @param type
     */
    @Override
    public Collection<NptRemoteSystem> getRemoteSystemByActionType(NptDict type) {
        return remoteSystemManager.getRemoteSystemByActionType(type);
    }

    /**
     * 作者：owen
     * 时间：2016/12/15 11:24
     * 描述:
     * 获取外部查询系统的信息
     *
     * @param ip
     * @param port
     */
    @Override
    public void updateRemoteSystem(String ip, Integer port) {
        remoteSystemManager.resetRemoteSystem(ip,port);
    }


    /**
     * 作者：OWEN
     * 时间：2016/11/28 17:02
     * 描述:
     * 通过码类型获取码集合
     *
     * @param codeType
     */
    @Override
    public Collection<NptBusinessCode> getBusinessCodeByType(Long codeType) {
        return businessCodeManager.listCode(codeType);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/28 17:03
     * 描述:
     * 获取某一类型下的指定码值的码信息
     *
     * @param codeValue
     * @param codeType
     */
    @Override
    public NptBusinessCode getBusinessCode(String codeValue, Long codeType) {
        return businessCodeManager.getCode(codeValue,codeType);
    }

    /**
     * author: owen
     * date:   2017/4/18 下午3:21
     * note:
     * 加载增量码信息
     */
    @Override
    public Collection<NptBusinessCode> listIncrementCode(Date date) {
        return businessCodeManager.listIncrement(date);
    }

    @Override
    public void updateBusinessCodeIncrementStamp(Date date) {
        businessCodeManager.updateSyncStamp(date);
    }

    /**
     * author: owen
     * date:   2017/4/24 13:34
     * note:
     * 加载字段的码表信息
     *
     * @param fieldId
     */
    @Override
    public Collection<NptBusinessCode> listFieldCodes(Long fieldId) {
        return businessCodeManager.findByCondition(
                Conditions.eq(NptBaseEntity.PROPERTY_STATUS,NptDict.IDS_ENABLED.getCode()),
                Conditions.eq(NptBusinessCode.PROPERTY_CODE_FIELDID,fieldId));
    }

    @Override
    public Collection<NptBusinessCode> listAllCode() {
        return businessCodeManager.listAll();
    }
}
