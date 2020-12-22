package com.npt.sync;

import com.alibaba.fastjson.JSON;
import com.credit.CreditQuery.entity.ApplyInfo;
import com.credit.CreditQuery.service.ApplyInfoService;
import com.credit.CreditServices.entity.CreditServiceInfo;
import com.credit.CreditServices.manager.CreditServiceInfoManager;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.core.entity.CmsUserExt;
import com.jeecms.core.manager.CmsUserExtMng;
import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.arch.service.NptArchService;
import com.npt.bridge.dict.NptBusinessCode;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.*;
import com.npt.bridge.model.service.NptBaseModelService;
import com.npt.bridge.sync.entity.CreditCmsUser;
import com.npt.bridge.sync.entity.CreditCmsUserResponse;
import com.npt.bridge.sync.entity.CreditServiceResponse;
import com.npt.bridge.sync.service.NptSyncService;
import com.npt.bridge.util.NptHttpDataPack;
import com.npt.bridge.util.NptHttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 11:50
 * 描述:
 */
@Controller
@RequestMapping(value = "/nptSync")
public class NptSyncAction {

    @Autowired
    private NptSyncService syncService;

    @Autowired
    private CmsUserExtMng cmsUserExtMng;

    @Autowired
    private ApplyInfoService applyInfoService;
    @Autowired
    private CreditServiceInfoManager serviceInfoManager;

    @Autowired
    private NptBaseModelService baseModelService;
    @Autowired
    private NptArchService archService;

    /**
     *作者：owen
     *时间：2017/1/17 15:57
     *描述:
     *      同步数据提供者
     */
    @RequestMapping(value = "/syncProvider.do")
    public void syncDataProvider(HttpServletRequest request,HttpServletResponse response){
        String jsonData = request.getParameter(NptHttpUtil.NPT_REMOTE_PARAM_NAME);

        NptDict result = NptDict.RST_SUCCESS;
        NptHttpDataPack pack = NptHttpUtil.unpack(jsonData);
        if(null != pack){
            NptBaseModelDependency dependency = JSON.parseObject(pack.getRealData(),NptBaseModelDependency.class);
            if(null != dependency){

                Collection<NptLogicDataProvider> providers = dependency.getProviders();
                Collection<NptBusinessCode> codes = dependency.getBusinessCodes();

                if(null != providers && !providers.isEmpty()) {
                    result = syncService.syncDataProvider(dependency.getProviders());
                }
                if(NptDict.RST_SUCCESS.equals(result) && null != codes && !codes.isEmpty()){
                    result = syncService.syncBusinessCodes(dependency.getBusinessCodes());
                    writeResponse(response,result);
                }else {
                    writeResponse(response,result);
                }
            }
        }else {
            writeResponse(response,result);
        }
    }

    /**
     *作者：owen
     *时间：2017/1/17 15:58
     *描述:
     *      同步模型结构
     */
    @RequestMapping(value = "/syncModelStructure.do")
    public void syncBaseModelStructure(HttpServletRequest request,HttpServletResponse response){
        String jsonData = request.getParameter(NptHttpUtil.NPT_REMOTE_PARAM_NAME);

        NptDict result = NptDict.RST_EXCEPTION;
        NptHttpDataPack pack = NptHttpUtil.unpack(jsonData);
        if(null != pack){
            NptBaseModelStructure structure = JSON.parseObject(pack.getRealData(),NptBaseModelStructure.class);
            result = syncService.syncBaseModelStructure(structure);
        }
        writeResponse(response,result);
    }

    /**
     *作者：owen
     *时间：2017/1/17 15:58
     *描述:
     *      同步模型增量数据
     */
    @RequestMapping(value = "/syncModelIncData.do")
    public void syncBaseModelIncData(HttpServletRequest request,HttpServletResponse response){
        String jsonData = request.getParameter(NptHttpUtil.NPT_REMOTE_PARAM_NAME);

        NptDict result = NptDict.RST_EXCEPTION;
        NptHttpDataPack pack = NptHttpUtil.unpack(jsonData);
        if(null != pack){
            NptBaseModelPoolRow poolData = JSON.parseObject(pack.getRealData(),NptBaseModelPoolRow.class);
            result = syncService.syncBaseModelData(poolData);
        }
        writeResponse(response,result);
    }

    /**
     *作者：owen
     *时间：2017/1/17 15:59
     *描述:
     *      回写同步结果
     */
    private void writeResponse(HttpServletResponse response, NptDict result){
        if(null != response){
            try {
                OutputStream outputStream = response.getOutputStream();
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("text/plain;charset=UTF-8");
                byte[] dataByte = result.getTitle().getBytes("UTF-8");
                outputStream.write(dataByte);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 作者: jiaoss
     * 日期: 2017年3月22日22:46:37
     * 备注: 同步用户
     */
    @RequestMapping(value = "/syncUser.do")
    public void syncUser(HttpServletRequest request, HttpServletResponse response) {
        List<CreditCmsUser> list = cmsUserExtMng.getSyncUserList();
        String s = JSON.toJSONString(list);

        ResponseUtils.renderText(response, NptHttpUtil.encode(s));
    }

    /**
     * 作者: jiaoss
     * 日期: 2017年3月22日22:46:44
     * 备注: 用户同步OK
     */
    @RequestMapping(value = "/syncOk.do")
    public void syncOk(String ids,
                       HttpServletRequest request, HttpServletResponse response) {
        List<Integer> idList = JSON.parseArray(ids, Integer.class);
        for (Integer  id : idList) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            boolean flag = cmsUserExtMng.updateSyncUser(id,formatter.format(new java.util.Date()),
                    NptDict.RCS_SYNED.getCode()+"");
        }
        ResponseUtils.renderText(response, "success");
    }

    /**
     * 作者: jiaoss
     * 日期: 2017年3月22日22:46:44
     * 备注: 用户认证更新
     */
    @RequestMapping(value = "/update.do")
    public void update(String res,
                       HttpServletRequest request, HttpServletResponse response) {
        List<CreditCmsUserResponse> idList = JSON.parseArray(res, CreditCmsUserResponse.class);
        for (CreditCmsUserResponse req : idList) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            boolean flag = cmsUserExtMng.updateSyncUserResult(req.getId(),formatter.format(new java.util.Date()),
                    NptDict.RCS_SYNED.getCode()+"",req.getFlag(),req.getFailComment());
        }
        ResponseUtils.renderText(response, "success");
    }

    /**
     * 作者: jiaoss
     * 日期: 2017年3月23日21:52:08
     * 备注: 同步个人信息申请查看权限的申请
     */
    @RequestMapping(value = "/syncApplyInfo.do")
    public void syncApplyInfo(HttpServletRequest request, HttpServletResponse response) {
        List<ApplyInfo> list = applyInfoService.getActiveApplyInfo(String.valueOf(NptDict.RCS_NOTSYNED.getCode()));
        String s = JSON.toJSONString(list);

        ResponseUtils.renderText(response, NptHttpUtil.encode(s));
    }

    /**
     * 作者: jiaoss
     * 日期: 2017年3月23日21:52:17
     * 备注: 同步个人信息申请查看权限OK
     */
    @RequestMapping(value = "/synApplyInfocOk.do")
    public void synApplyInfocOk(String ids,
                                HttpServletRequest request, HttpServletResponse response) {
        List<Integer> idList = JSON.parseArray(ids, Integer.class);
        for (Integer id : idList) {
            boolean flag = applyInfoService.updateAppInfo(id, String.valueOf(NptDict.USER_APPLY_PROCESSING.getCode()), String.valueOf(NptDict.RCS_SYNED.getCode()));
        }
        ResponseUtils.renderText(response, "success");
    }

    /**
     * 作者: jiaoss
     * 日期: 2017年3月22日22:46:44
     * 参数：List<String>
     * 备注: 同步个人信息信用信息
     */
    @RequestMapping(value = "/updateApplyData.do")
    public void updateApplyData(String res,
                                HttpServletRequest request, HttpServletResponse response) {
        Map<Integer, List<String>> resps = JSON.parseObject(res, Map.class);
        for (Map.Entry<Integer, List<String>> entry : resps.entrySet()) {
            for (String sql : entry.getValue()) {
                syncService.execute(sql);
            }
            applyInfoService.updateAppInfo(entry.getKey(), String.valueOf(NptDict.USER_APPLY_SUCCESS.getCode()), String.valueOf(NptDict.RCS_SYNED.getCode()));
        }

        ResponseUtils.renderText(response, "success");
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/11 上午11:11
     * 备注: 定时删除实名数据
     */
    @RequestMapping(value = "/deleteApplyInfo.do")
    public void deleteApplyInfo() {

        List<ApplyInfo> outDateInfo = applyInfoService.getOutDateInfo();
        if (outDateInfo.size() == 0) {
            return;
        }
        CmsUserExt userExt;
        List<String> companySqlList = getDeleteSql(NptDict.BMH_BUSINESS, NptDict.BMC_OUTSIDE);
        List<String> personSqlList = getDeleteSql(NptDict.BMH_PERSONAL, NptDict.BMC_OUTSIDE);
        for (ApplyInfo info : outDateInfo) {
            userExt = cmsUserExtMng.findById(info.getUserId());
            assert userExt != null;
            if (userExt.getType().equals("company")) {
                for (String sql : companySqlList) {
                    syncService.execute(sql.replace(":idCard", userExt.getIdCard()));
                }
            } else if (userExt.getType().equals("person")) {
                for (String sql : personSqlList) {
                    syncService.execute(sql.replace(":idCard", userExt.getIdCard()));
                }
            }
            info.setApplyFlag(String.valueOf(NptDict.USER_APPLY_OUTDATE.getCode()));
            applyInfoService.update(info);
        }
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/11 下午05:04
     * 备注: 生成删除sql
     */
    private List<String> getDeleteSql(NptDict host, NptDict cate) {
        List<String> sqlList = new ArrayList<>();
        //获取当前的模型信息
        NptBaseModel model = baseModelService.listModels(host, cate).iterator().next();

        Collection<NptBaseModelPool> pools = baseModelService.getBaseModelGrouPools(model,null);
        if (pools == null) {
            return sqlList;
        }
        Collection<NptLogicDataField> dataFields;
        NptLogicDataType dataType;
        NptLogicDataField dataField;
        String fieldDbName = null;
        StringBuilder sql;
        for (NptBaseModelPool pool : pools) {

            // 企业只处理LockLevel大于0的数据
            if (host.equals(NptDict.BMH_BUSINESS) && pool.getLockLevel().equals(NptDict.MPL_LEVEL_0.getCode())) {
                continue;
            }

            dataType = archService.findDataTypeById(pool.getDataTypeId());
            if (dataType.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {
                dataFields = archService.listDataField(dataType.getId(),null,null);
                if (dataFields.size() > 0) {
                    sql = new StringBuilder();
                    sql.append("delete ");
                    for (Iterator<NptLogicDataField> iterator = dataFields.iterator(); iterator.hasNext(); ) {
                        dataField = iterator.next();
                        if (dataField.getId().equals(pool.getPrimaryFieldId())) {
                            fieldDbName = dataField.getFieldDbName();
                        }
                    }
                    assert fieldDbName != null;
                    sql.append(" from ").append(dataType.getTypeDbName())
                            .append(" where ")
                            .append(fieldDbName).append(" = '").append(":idCard").append("'");

                    sqlList.add(sql.toString());
                }
            }

        }

        return sqlList;
    }

    /*************************************************************************************
     *
     *                               同步信用服务
     *
     *************************************************************************************/

    /**
     * 作者: 张磊
     * 日期: 2017/03/21 下午08:50
     * 备注: 同步
     */
    @RequestMapping(value = "/syncService.do")
    public void syncService(HttpServletRequest request, HttpServletResponse response) {
        Collection<CreditServiceInfo> list = serviceInfoManager.findByStatus(NptDict.RCS_NOTSYNED);
        String s = JSON.toJSONString(list);
        ResponseUtils.renderText(response, NptHttpUtil.encode(s));
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/21 下午08:50
     * 备注: 同步OK
     */
    @RequestMapping(value = "/syncServiceOk.do")
    public void synServicecOk(String ids,
                       HttpServletRequest request, HttpServletResponse response) {
        List<Long> idList = JSON.parseArray(ids, Long.class);
        for (Long id : idList) {
            CreditServiceInfo info = serviceInfoManager.findById(id);
            if (info == null) {
                ResponseUtils.renderText(response, "failed");
                return;
            }
            info.setSyncStatus(NptDict.RCS_SYNED.getCode());
            info.setSyncTime(new java.sql.Timestamp(new java.util.Date().getTime()));
            serviceInfoManager.update(info);
        }
        ResponseUtils.renderText(response, "success");
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/21 下午08:50
     * 备注: 更新回复
     */
    @RequestMapping(value = "/updateService.do")
    public void updateService(String res,
                       HttpServletRequest request, HttpServletResponse response) {
        List<CreditServiceResponse> resps = JSON.parseArray(res, CreditServiceResponse.class);
        for (CreditServiceResponse resp : resps) {
            CreditServiceInfo info = serviceInfoManager.findById(resp.getId());
            if (info == null) {
                ResponseUtils.renderText(response, "failed");
                return;
            }
            info.setSyncStatus(NptDict.RCS_SYNED.getCode());
            info.setSyncTime(new java.sql.Timestamp(new java.util.Date().getTime()));
            info.setResponse(resp.getResponse());
            info.setResponseTime(resp.getResponseTime());
            serviceInfoManager.update(info);
        }
        ResponseUtils.renderText(response, "success");
    }

}
