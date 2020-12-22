package com.npt.grs.apply.manager;

import com.npt.bridge.sync.entity.NptUserAppeal;
import com.npt.grs.apply.entity.NptBusinessFlowLog;
import com.npt.grs.apply.entity.NptResourceApply;
import org.summer.extend.manager.BaseManager;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/31 11:46
 * 备注：
 */
public interface NptBusinessFlowLogManager extends BaseManager<NptBusinessFlowLog>{

    /**
     * 作者：owen
     * 日期：2016/10/31 11:50
     * 备注：
     *      获取申请的所有申请日志
     * 参数：
     * 返回：
     */
    Collection<NptBusinessFlowLog> getResourceApplyLogs(NptResourceApply apply);

    /**
     * 作者：owen
     * 日期：2016/10/31 11:50
     * 备注：
     *      获取申请的所有申请日志
     * 参数：
     * 返回：
     */
    Collection<NptBusinessFlowLog> getResourceApplyLogs(String no);

    /**
     * 作者：owen
     * 日期：2016/10/31 11:52
     * 备注：
     *      获取某次申请指定状态的申请日志
     * 参数：
     * 返回：
     */
    NptBusinessFlowLog getResourceApplyLog(String no, Integer status);

    /**
     * 作者：owen
     * 日期：2016/10/31 14:25
     * 备注：
     *      添加申请日志
     * 参数：
     * 返回：
     */
    void addApplyLog(NptResourceApply apply,String remark);

    /**
     *作者: xuqinyuan
     *时间: 2016/11/28 21:36
     *备注: 添加异议日志
     *
     * @param appeal
     * @param remark
     */
    void addAppealLog(NptUserAppeal appeal,String remark);
}
