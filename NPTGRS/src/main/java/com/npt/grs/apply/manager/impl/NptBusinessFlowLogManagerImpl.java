package com.npt.grs.apply.manager.impl;

import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.sync.entity.NptUserAppeal;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.grs.apply.dao.NptBusinessFlowLogDao;
import com.npt.grs.apply.entity.NptBusinessFlowLog;
import com.npt.grs.apply.entity.NptResourceApply;
import com.npt.grs.apply.manager.NptBusinessFlowLogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.manager.BaseManagerImpl;
import org.summer.extend.orm.condition.Conditions;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/31 11:46
 * 备注：
 */
@Service
@Transactional
public class NptBusinessFlowLogManagerImpl extends BaseManagerImpl<NptBusinessFlowLog> implements NptBusinessFlowLogManager {

    @Autowired
    private NptBusinessFlowLogDao applyLogDao;

    /**
     * 作者：owen
     * 日期：2016/10/31 11:50
     * 备注：
     * 获取申请的所有申请日志
     * 参数：
     * 返回：
     *
     * @param apply
     */
    @Override
    public Collection<NptBusinessFlowLog> getResourceApplyLogs(NptResourceApply apply) {
        if(null != apply){
            String no = apply.getApplyNo();
            return getResourceApplyLogs(no);
        }
        return null;
    }

    /**
     * 作者：owen
     * 日期：2016/10/31 11:50
     * 备注：
     * 获取申请的所有申请日志
     * 参数：
     * 返回：
     *
     * @param no
     */
    @Override
    public Collection<NptBusinessFlowLog> getResourceApplyLogs(String no) {
        return this.findByCondition(
                Conditions.eq(NptBusinessFlowLog.PROPERTY_FLOW_NO,no),
                Conditions.asc(NptBaseEntity.PROPERTY_CREATE_TIME));
    }

    /**
     * 作者：owen
     * 日期：2016/10/31 11:52
     * 备注：
     * 获取某次申请指定状态的申请日志
     * 参数：
     * 返回：
     *
     * @param no
     * @param status
     */
    @Override
    public NptBusinessFlowLog getResourceApplyLog(String no, Integer status) {
        List<NptBusinessFlowLog> logs = this.findByCondition(
                Conditions.eq(NptBusinessFlowLog.PROPERTY_FLOW_NO,no),
                Conditions.eq(NptBusinessFlowLog.PROPERTY_APPLY_LOG_STATUS,status),
                Conditions.desc(NptBaseEntity.PROPERTY_CREATE_TIME));

        if(null != logs && !logs.isEmpty()){
            return logs.get(0);
        }
        return null;
    }

    /**
     * 作者：owen
     * 日期：2016/10/31 14:25
     * 备注：
     * 添加申请日志
     * 参数：
     * 返回：
     *
     * @param apply
     */
    @Override
    public void addApplyLog(NptResourceApply apply,String remark) {
        if(null != apply){
            NptBusinessFlowLog log = new NptBusinessFlowLog();
            log.setStatus(NptDict.IDS_ENABLED.getCode());
            log.setFlowNo(apply.getApplyNo());
            log.setCreateTime(new Date());
            log.setResult(apply.getApplyStatus());

            String statusName = "";
            Collection<NptDict> applyStatusList = NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.RAS);
            if(null != applyStatusList){
                for(NptDict d:applyStatusList){
                    if(apply.getApplyStatus().equals(d.getCode())){
                        statusName = d.getTitle();
                        break;
                    }
                }
            }

            if(null != remark){
                log.setRemark(remark);
            }else {
                log.setRemark(statusName);
            }
            this.save(log);
        }
    }

    @Override
    public void addAppealLog(NptUserAppeal appeal, String remark) {
        if (null != appeal){
            NptBusinessFlowLog log = new NptBusinessFlowLog();
            log.setStatus(NptDict.IDS_ENABLED.getCode());
            log.setFlowNo(appeal.getAppealNo());
            log.setCreateTime(new Date());
            log.setResult(appeal.getAppealStatus());

            String statusName = "";
            Collection<NptDict> applyStatusList = NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.RAS);
            if(null != applyStatusList){
                for(NptDict d:applyStatusList){
                    if(appeal.getAppealStatus().equals(d.getCode())){
                        statusName = d.getTitle();
                        break;
                    }
                }
            }

            if(null != remark){
                log.setRemark(remark);
            }else {
                log.setRemark(statusName);
            }
            this.save(log);
        }
    }
}
