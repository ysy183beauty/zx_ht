package com.npt.rms.log.action;

import com.alibaba.fastjson.JSON;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dict.NptDict;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.rms.auth.entity.NptSimpleUser;
import com.npt.rms.auth.service.NptRmsAuthService;
import com.npt.rms.base.NptRmsCommonService;
import com.npt.rms.base.action.NptRMSAction;
import com.npt.rms.log.entity.NptLog;
import com.npt.rms.log.manager.NptLogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.summer.commons.lang.DateFormatUtils;
import org.summer.extend.orm.Condition;
import org.summer.extend.orm.Pagination;
import org.summer.extend.orm.condition.Conditions;

import javax.annotation.Resource;
import java.util.*;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/27 21:46
 * 备注：
 */
@Controller("npt.rms.log")
public class NptLogAction extends NptRMSAction<NptLog>{

    @Autowired
    private NptLogManager logManager;
    @Resource(name = "rmsAuthService")
    private NptRmsAuthService authService;
    @Autowired
    private NptRmsArchService archService;
    @Resource(name = "rmsCommonService")
    private NptRmsCommonService commonService;

    /**
     * 作者: 张磊
     * 日期: 16/12/19 下午4:25
     * 备注: 日志首页
     */
    public String index() {
        Collection<NptLogicDataProvider> orgList = null;
        NptLogicDataProvider rootOrg = archService.getRootOrg();
        if (rootOrg != null) {
            orgList = archService.listOrg(rootOrg.getId());
        }
        this.setAttribute("_ORG_LIST",orgList);
        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 16/12/19 下午4:26
     * 备注: 日志列表查询
     */
    public String list() {
        Long orgId = this.getLongParameter("orgId");
        Long userId = this.getLongParameter("userId");
        String userName = this.getParameter("userName");
        Date startDate = this.getDateTimeParameter("startDate", DateFormatUtils.D_yyyyMMdd);
        Date endDate = this.getDateTimeParameter("endDate", DateFormatUtils.D_yyyyMMdd);
        String actionName = this.getParameter("actionName");

        Collection<Condition> conditions = new ArrayList<>();
        if (orgId != null) {
            Collection<NptSimpleUser> nptSimpleUsers = authService.listUser(archService.findOrgById(orgId));
            if (!nptSimpleUsers.isEmpty()) {
                List<Long> userIdList = new ArrayList<>();
                nptSimpleUsers.forEach(user -> userIdList.add(user.getUserId()));
                conditions.add(Conditions.in(NptBaseEntity.PROPERTY_CREATOR_ID, userIdList));
            } else {
                return SUCCESS;
            }
        }
        if (userId != null) {
            conditions.add(Conditions.eq(NptBaseEntity.PROPERTY_CREATOR_ID, userId));
        }
        if (userName != null && !userName.isEmpty()) {
            conditions.add(Conditions.like("userName", userName));
        }
        if (startDate != null && endDate != null) {
            // 设置结束日期 = 结束日期+1天
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(endDate);
            endCal.set(Calendar.DAY_OF_MONTH, endCal.get(Calendar.DAY_OF_MONTH) + 1);

            conditions.add(Conditions.ge(NptBaseEntity.PROPERTY_CREATE_TIME, new java.sql.Timestamp(startDate.getTime())));
            conditions.add(Conditions.lt(NptBaseEntity.PROPERTY_CREATE_TIME, new java.sql.Timestamp(endCal.getTime().getTime())));
        }
        if (actionName != null && !actionName.isEmpty()) {
            conditions.add(Conditions.like("actionName", actionName));
        }
        conditions.add(Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        conditions.add(Conditions.desc(NptBaseEntity.PROPERTY_ID));

        Pagination<NptLog> dataPagination = commonService.findAllLog(getPageBeginIndex(), getPageSize(), conditions.toArray(new Condition[conditions.size()]));
        this.setAttribute("_LOG_Pagination", createPaginationResult(dataPagination, getPageCurrentPage(), getPageSize()));

        List<NptSimpleUser> userList = new ArrayList<>();
        List<NptLogicDataProvider> orgList = new ArrayList<>();
        NptSimpleUser user;
        NptLogicDataProvider org;
        for (NptLog log : dataPagination.getResults()) {
            if (log.getCreatorId() != null) {
                user = authService.getUserById(log.getCreatorId());
            } else {
                user = null;
            }
            if (user != null && user.getOrgId() != null) {
                org = archService.findOrgById(Long.parseLong(user.getOrgId()));
            } else {
                org = null;
            }

            userList.add(user);
            orgList.add(org);
        }
        this.setAttribute("_LOG_USER", userList);
        this.setAttribute("_LOG_ORG", orgList);

        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 16/12/19 下午4:26
     * 备注: 日志详情
     */
    public String detail() {
        Long id = this.getLongParameter("id");
        NptLog log = logManager.findById(id);

        String attributeName = "";
        Object attributeValue = "";
        if (log.getBusinessType() == NptDict.LGB_GRS.getCode()) {
            if (log.getActionType() == NptDict.LGA_OPEN_INDEX.getCode()) {
                attributeName = "grs_main_field_list";
            } else if (log.getActionType() == NptDict.LGA_AUTH_INDEX.getCode()) {
                attributeName = "grs_group_info_list";
            } else if (log.getActionType() == NptDict.LGA_AUTH_BLOCK_MORE.getCode()) {
                attributeName = "grs_block_info_list";
            }
            attributeValue = JSON.parse(log.getResults());
        }
//        if(log.getBusinessType() == NptDict.LGB_APP.getCode()){}
        this.setAttribute(attributeName, attributeValue);
        this.setAttribute("_LOG", log);
        return SUCCESS;
    }
}
