package com.npt.grs.apply.manager.impl;

import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.grs.apply.dao.NptResourceApplyDao;
import com.npt.grs.apply.entity.NptBusinessFlowLog;
import com.npt.grs.apply.entity.NptResourceApply;
import com.npt.grs.apply.entity.NptResourceApplyField;
import com.npt.grs.apply.manager.NptBusinessFlowLogManager;
import com.npt.grs.apply.manager.NptResourceApplyFieldsManager;
import com.npt.grs.apply.manager.NptResourceApplyManager;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.rms.auth.entity.NptSimpleUser;
import com.npt.rms.auth.service.NptRmsAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.manager.BaseManagerImpl;
import org.summer.extend.orm.Pagination;
import org.summer.extend.orm.condition.Conditions;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/10 15:49
 * 备注：
 */
@Service
@Transactional
public class NptResourceApplyManagerImpl extends BaseManagerImpl<NptResourceApply> implements NptResourceApplyManager {

    @Autowired
    private NptResourceApplyDao resourceApplyDao;

    @Autowired
    private NptResourceApplyFieldsManager applyFieldsManager;

    @Autowired
    private NptBusinessFlowLogManager applyLogManager;

    @Resource(name = "rmsArchService")
    private NptRmsArchService rmsArchService;

    @Resource(name = "rmsAuthService")
    private NptRmsAuthService rmsAuthService;

    /**
     * 作者：owen
     * 日期：2016/11/1 14:58
     * 备注：
     * 获取机构列表
     * 参数：
     * 返回：
     */
    @Override
    public Collection<NptLogicDataProvider> getOrgList() {
        try {
            return rmsArchService.listOrg(null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 作者：owen
     * 日期：2016/10/30 17:34
     * 备注：
     * 获取指定用户对指定数据池行级(行级由业务主键值PKVALUE来确定)的字段申请情况
     * 参数：
     * 返回：
     *
     * @param userId
     * @param poolId
     * @param pkValue
     */
    @Override
    public Collection<NptResourceApply> getUserApply(Long userId, Long poolId, String pkValue, Collection<NptDict> status){
        if(null == status || (null != status && status.isEmpty())) {
            return this.findByCondition(
                    Conditions.eq(NptResourceApply.PROPERTY_APPLY_USER_ID, userId),
                    Conditions.eq(NptResourceApply.PROPERTY_APPLY_POOL_ID, poolId),
                    Conditions.eq(NptResourceApply.PROPERTY_APPLY_BUSINESS_KEY, pkValue));
        }else {
            Collection<Integer> statusIdList = new ArrayList<>();
            for(NptDict d:status){
                statusIdList.add(d.getCode());
            }
            return this.findByCondition(
                    Conditions.eq(NptResourceApply.PROPERTY_APPLY_USER_ID, userId),
                    Conditions.eq(NptResourceApply.PROPERTY_APPLY_POOL_ID, poolId),
                    Conditions.eq(NptResourceApply.PROPERTY_APPLY_BUSINESS_KEY, pkValue),
                    Conditions.in(NptResourceApply.PROPERTY_APPLY_STATUS,statusIdList));
        }
    }

    /**
     * 作者：owen
     * 日期：2016/11/3 17:23
     * 备注：
     * 检测用户是否有已通过的申请及其该申请目前是否正处于可视时间段
     * 参数：
     * 返回：
     *
     * @param userId
     * @param poolId
     * @param pkValue
     */
    @Override
    public boolean isUserValideApplyEffective(Long userId, Long poolId, String pkValue) {
        boolean effective = false;
        Collection<NptDict> status = new ArrayList<>();
        status.add(NptDict.RAS_ACCEPTED);
        Collection<NptResourceApply> applyList = getUserApply(userId, poolId, pkValue,status);
        if (null != applyList && applyList.size() == NptCommonUtil.IntegerOne()) {
            NptResourceApply apply = applyList.iterator().next();
            Date start = apply.getConfirmedStartDate();
            Date end = apply.getConfirmedEndDate();
            Date current = new Date();

            //当前时间处于确认可查看时间区间内
            if ((start.getTime() < current.getTime()) && current.getTime() < end.getTime()) {
                effective = true;
            }
        }
        return effective;
    }

    /**
     * 作者：owen
     * 日期：2016/11/1 18:11
     * 备注：
     * 根据编号获取唯一申请记录
     * 参数：
     * 返回：
     *
     * @param no
     */
    @Override
    public NptResourceApply getResourceApplyByNO(String no) {
        NptResourceApply apply = this.findUniqueByCondition(Conditions.eq(NptResourceApply.PROPERTY_APPLY_NO, no));
        if (null != apply) {
            transformStatus(new ArrayList<NptResourceApply>() {{
                add(apply);
            }});
        }
        return apply;
    }

    /**
     * 作者：owen
     * 日期：2016/11/1 14:17
     * 备注：
     * 获取用户的申请，但时间从新到旧排序
     * 参数：
     * 返回：
     *
     * @param userId
     * @param
     */
    @Override
    public Pagination<NptResourceApply> getPaganitionUserApply(Long userId, Long orgId, String poolTitle, String businessKey, NptDict status, Integer begin, Integer pageSize) {

        Pagination<NptResourceApply> p = this.findAll(
                begin,
                pageSize,
                Conditions.desc(NptBaseEntity.PROPERTY_CREATE_TIME),
                Conditions.eq(NptResourceApply.PROPERTY_APPLY_USER_ID, userId),
                null == orgId ? Conditions.isNotNull(NptBaseEntity.PROPERTY_ID) : Conditions.eq(NptResourceApply.PROPERTY_APPLY_PROVIDER,orgId),
                null == poolTitle ? Conditions.isNotNull(NptBaseEntity.PROPERTY_ID) : Conditions.like(NptResourceApply.PROPERTY_APPLY_POOL_TITLE,poolTitle),
                null == businessKey ? Conditions.isNotNull(NptBaseEntity.PROPERTY_ID) : Conditions.like(NptResourceApply.PROPERTY_APPLY_BUSINESS_KEY,businessKey),
                null == status ? Conditions.isNotNull(NptBaseEntity.PROPERTY_ID) : Conditions.eq(NptResourceApply.PROPERTY_APPLY_STATUS,status.getCode())
        );

        if (null != p) {
            transformStatus(p.getResults());
        }
        return p;
    }

    /**
     * 作者：owen
     * 日期：2016/10/31 21:21
     * 备注：
     * 依据指定用户的角色,加载其可受理的资源申请项
     * 参数：
     * 返回：
     *
     */
    @Override
    public Pagination<NptResourceApply> getResourceApplyForEstablish(
            Integer role,
            Long applyUserId,
            Long applyOrgId,
            Long approvalOrgId,
            String poolTitle,
            NptDict status,
            Integer begin,
            Integer pageSize) {

        Pagination<NptResourceApply> applies = null;

        applies = this.findAll(
                begin,
                pageSize,
                null == status ? Conditions.isNotNull(NptBaseEntity.PROPERTY_ID) : Conditions.eq(NptResourceApply.PROPERTY_APPLY_STATUS, status.getCode()),
                null == applyUserId?Conditions.isNotNull(NptBaseEntity.PROPERTY_ID) :Conditions.eq(NptResourceApply.PROPERTY_APPLY_USER_ID,applyUserId),
                null == applyOrgId ? Conditions.isNotNull(NptBaseEntity.PROPERTY_ID)  : Conditions.eq(NptResourceApply.PROPERTY_APPLY_USER_ORG, applyOrgId),
                null == approvalOrgId ? Conditions.isNotNull(NptBaseEntity.PROPERTY_ID)  : Conditions.eq(NptResourceApply.PROPERTY_APPLY_PROVIDER, approvalOrgId),
                null == poolTitle ? Conditions.isNotNull(NptBaseEntity.PROPERTY_ID)  : Conditions.like(NptResourceApply.PROPERTY_APPLY_POOL_TITLE, poolTitle),
                null == role ? Conditions.isNotNull(NptBaseEntity.PROPERTY_ID) : Conditions.ne(NptResourceApply.PROPERTY_APPLY_STATUS, role),
                Conditions.desc(NptBaseEntity.PROPERTY_CREATE_TIME));

        if (null != applies) {
            transformStatus(applies.getResults());
        }
        return applies;
    }

    /**
     * 作者：owen
     * 日期：2016/11/2 12:00
     * 备注：
     * 指定用户对指定的申请做出指定结果的审批
     * 参数：
     * 返回：
     *
     * @param userId
     * @param applyNo
     * @param ar
     * @param note
     * @param confirm
     */
    @Override
    public NptDict approvalApplyByCDC(Long userId, String applyNo, Integer ar, String note, Date[] confirm) {
        NptResourceApply apply = this.getResourceApplyByNO(applyNo);
        if (null == apply) {
            return NptDict.RST_INVALID_PARAMS;
        }

        NptSimpleUser user = rmsAuthService.getUserById(userId);
        if (null != user) {
            int nextStatus;
            String nextStatusTitle;
            if (!NptCommonUtil.IntegerOne().equals(ar)) {
                nextStatus = NptDict.RAS_REFUSED.getCode();
                nextStatusTitle = NptDict.RAS_REFUSED.getTitle();
            } else {
                nextStatus = NptDict.RAS_PROCESSING.getCode();
                nextStatusTitle = NptDict.RAS_PROCESSING.getTitle();
            }

            apply.setApplyStatus(nextStatus);
            apply.setStepUserId(userId);
            apply.setConfirmedStartDate(confirm[0]);
            apply.setConfirmedEndDate(confirm[1]);
            apply.setApplyStatusTitle(nextStatusTitle);
            this.update(apply);

            applyLogManager.addApplyLog(apply, note);
        }
        return NptDict.RST_SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/22 14:31
     * 描述:
     * 数据提供方对资源申请的审批
     *
     * @param userId
     * @param applyNo
     * @param ar
     * @param note
     * @param confirm
     */
    @Override
    public NptDict approvalApplyByPRD(Long userId, String applyNo, Integer ar, String note, Date[] confirm) {
        NptResourceApply apply = this.getResourceApplyByNO(applyNo);
        if (null == apply) {
            return NptDict.RST_INVALID_PARAMS;
        }

        NptSimpleUser user = rmsAuthService.getUserById(userId);
        if (null != user) {
            int nextStatus;
            if (!NptCommonUtil.IntegerOne().equals(ar)) {
                nextStatus = NptDict.RAS_REFUSED.getCode();
            } else {
                nextStatus = NptDict.RAS_ACCEPTED.getCode();
            }

            apply.setApplyStatus(nextStatus);
            apply.setStepUserId(userId);
            apply.setConfirmedStartDate(confirm[0]);
            apply.setConfirmedEndDate(confirm[1]);

            this.update(apply);

            applyLogManager.addApplyLog(apply, note);
        }
        return NptDict.RST_SUCCESS;
    }

    /**
     * 作者：owen
     * 日期：2016/11/1 17:01
     * 备注：
     * 处理申请中的状态值向状态名称的转换
     * 参数：
     * 返回：
     */
    private void transformStatus(Collection<NptResourceApply> list) {
        if (null != list && !list.isEmpty()) {
            for (NptResourceApply apply : list) {
                Integer status = apply.getApplyStatus();
                if (status.equals(NptDict.RAS_EXPIRED.getCode())) {
                    apply.setExpiredTitle("已过期");
                } else {
                    apply.setExpiredTitle("未过期");
                }
                NptDict dict = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.RAS,status);
                if(null != dict){
                    apply.setApplyStatusTitle(dict.getTitle());
                }
                NptLogicDataProvider org = rmsArchService.fastFindOrgById(apply.getApplyUserOrg());
                if (null != org) {
                    apply.setApplyUserOrgTitle(org.getOrgName());
                } else {
                    apply.setApplyUserOrgTitle(NptDict.RST_UNKNOW.getTitle());
                }
            }
        }
    }


    /**
     * 作者：owen
     * 日期：2016/10/30 17:38
     * 备注：
     * 获取指定用户对指定数据池行级的字段申请情况
     * 参数：
     * 返回：
     *
     * @param userId
     * @param poolId
     * @param pkValue
     */
    @Override
    public Collection<NptResourceApplyField> getUserValideApplyedFields(Long userId, Long poolId, String pkValue) {
        Collection<NptDict> status = new ArrayList<>();
        status.add(NptDict.RAS_ACCEPTED);
        status.add(NptDict.RAS_PROCESSING);
        status.add(NptDict.RAS_WAITTING);
        Collection<NptResourceApply> applyList = getUserApply(userId, poolId, pkValue,status);
        Collection<NptResourceApplyField> result = new ArrayList<>();
        if (null != applyList && applyList.size() == NptCommonUtil.IntegerOne()) {
            NptResourceApply apply = applyList.iterator().next();
            String applyNo = apply.getApplyNo();
            Collection<NptResourceApplyField> searchResult = applyFieldsManager.getApplyFieldsByApplyNO(applyNo);
            if(null != searchResult) {
                result.addAll(searchResult);
            }
        }
        return result;
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
        return applyLogManager.getResourceApplyLogs(no);
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
        return applyLogManager.getResourceApplyLog(no, status);
    }

    /**
     * 作者：owen
     * 日期：2016/10/31 16:08
     * 备注：
     * 获取所有已通过的申请
     * 参数：
     * 返回：
     */
    @Override
    public Collection<NptResourceApply> getResourceAcceptedApply() {
        return this.findByCondition(
                Conditions.eq(NptResourceApply.PROPERTY_APPLY_STATUS, NptDict.RAS_ACCEPTED.getCode()),
                Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
    }
}
