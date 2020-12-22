package com.npt.grs.apply.manager;

import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.dict.NptDict;
import com.npt.grs.apply.entity.NptBusinessFlowLog;
import com.npt.grs.apply.entity.NptResourceApply;
import com.npt.grs.apply.entity.NptResourceApplyField;
import org.summer.extend.manager.BaseManager;
import org.summer.extend.orm.Pagination;

import java.util.Collection;
import java.util.Date;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/10 15:48
 * 备注：
 */
public interface NptResourceApplyManager extends BaseManager<NptResourceApply>{
    /**
     * 作者：owen
     * 日期：2016/11/1 14:58
     * 备注：
     *      获取机构列表
     * 参数：
     * 返回：
     */
    Collection<NptLogicDataProvider> getOrgList();

    /**
     * 作者：owen
     * 日期：2016/10/30 17:34
     * 备注：
     *      获取指定用户对指定数据池行级(行级由业务主键值PKVALUE来确定)的基本申请情况
     * 参数：
     * 返回：
     */
    Collection<NptResourceApply> getUserApply(Long userId,Long poolId,String pkValue,Collection<NptDict> status);

    /**
     * 作者：owen
     * 日期：2016/11/3 17:23
     * 备注：
     *      检测用户是否有已通过的申请及其该申请目前是否正处于可视时间段
     * 参数：
     * 返回：
     */
    boolean isUserValideApplyEffective(Long userId,Long poolId,String pkValue);

    /**
     * 作者：owen
     * 日期：2016/11/1 18:11
     * 备注：
     *      根据编号获取唯一申请记录
     * 参数：
     * 返回：
     */
    NptResourceApply getResourceApplyByNO(String no);

    /**
     * 作者：owen
     * 日期：2016/11/1 14:17
     * 备注：
     *      根据条件获取用户自身的申请，但时间从新到旧排序
     * 参数：
     * 返回：
     */
    Pagination<NptResourceApply> getPaganitionUserApply(Long userId, Long orgId, String poolTitle, String businessKey, NptDict status, Integer begin, Integer pageSize);

    /**
     * 作者：owen
     * 日期：2016/10/30 17:38
     * 备注：
     *      获取指定用户对指定数据池行级的字段申请情况
     * 参数：
     * 返回：
     */
    Collection<NptResourceApplyField> getUserValideApplyedFields(Long userId, Long poolId, String pkValue);

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
     * 日期：2016/10/31 16:08
     * 备注：
     *      获取所有已通过的申请
     * 参数：
     * 返回：
     */
    Collection<NptResourceApply> getResourceAcceptedApply();


    /**
     * 作者：owen
     * 日期：2016/10/31 21:21
     * 备注：
     *      依据条件加载资源申请项
     * 参数：
     * 返回：
     */
    Pagination<NptResourceApply> getResourceApplyForEstablish(
            Integer role,
            Long applyUserId,
            Long applyOrgId,
            Long approvalOrgId,
            String poolTitle,
            NptDict status,
            Integer begin,
            Integer pageSize);

    /**
     * 作者：owen
     * 日期：2016/11/2 12:00
     * 备注：
     *      管理中心对资源申请的审批
     * 参数：
     * 返回：
     */
    NptDict approvalApplyByCDC(Long userId, String applyNo, Integer ar, String note, Date[] confirm);

    /**
     *作者：OWEN
     *时间：2016/11/22 14:31
     *描述:
     *      数据提供方对资源申请的审批
     */
    NptDict approvalApplyByPRD(Long userId, String applyNo, Integer ar, String note, Date[] confirm);
}
