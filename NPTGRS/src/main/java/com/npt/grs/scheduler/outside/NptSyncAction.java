package com.npt.grs.scheduler.outside;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.sync.entity.NptSyncBase;
import com.npt.bridge.util.ExceptionUtil;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.bridge.util.NptHttpUtil;
import com.npt.rms.base.NptRmsCommonService;
import com.npt.rms.log.entity.NptLog;
import com.npt.web.action.NptPaginationAction;
import org.springframework.scheduling.annotation.Scheduled;
import org.summer.extend.manager.BaseManager;
import org.summer.extend.orm.condition.Conditions;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/3/25
 * 备注:
 */
public abstract class NptSyncAction<T extends NptSyncBase, ID extends Serializable> extends NptPaginationAction<T> {

    protected NptRmsCommonService commonService;

    protected BaseManager<T> manager;

    protected abstract Class<T> getEntityClass();

    protected abstract NptDict getSyncDict();

    protected abstract NptDict getSyncOkDict();

    protected abstract NptDict getSyncResponseDict();

    protected abstract Object generateResponse(List<T> infos);

    protected abstract NptDict getSyncLogDict();

    protected abstract NptDict getSyncResponseLogDict();

    /**
     * 作者: 张磊
     * 日期: 2017/03/25 下午03:59
     * 备注:
     */
    @Scheduled(cron = "0 0 */2 * * ?")
    public void sync() {
        NptLog log = commonService.makeLog();
        log.setBusinessName(NptDict.LGB_SYNC.getTitle());
        log.setBusinessType(NptDict.LGB_SYNC.getCode());
        log.setActionName(this.getSyncLogDict().getTitle());
        log.setActionType(this.getSyncLogDict().getCode());
        try {
            String result = "";
            result = NptHttpUtil.readRdpInterfacePost(commonService.getRemoteSystemByActionType(this.getSyncDict()).iterator().next().getActionUrl(), new JSONObject());
            String decode = NptHttpUtil.decode(result);
            log.setResults(decode);
            List<T> infos = JSON.parseArray(decode, this.getEntityClass());
            if (infos.size() > 0) {
                Collection<?> exists = manager.findByHql("select id from " + this.getEntityClass().getSimpleName());

                JSONObject response = new JSONObject();
                List<Object> callbackList = new ArrayList<>();
                for (T info : infos) {
                    if (exists.contains(info.getId())) {
                        //更新
                        T old = manager.findById(info.getId());
                        if(this.updateInfo(old, info)){
                            callbackList.add(info.getId());
                        }
                    } else {
                        //保存
                        if (this.saveInfo(info)) {
                            callbackList.add(info.getId());
                        }
                    }
                }
                response.put("ids", JSON.toJSONString(callbackList));

                NptHttpUtil.readRdpInterfacePost(commonService.getRemoteSystemByActionType(this.getSyncOkDict()).iterator().next().getActionUrl(), response);
            }
            log.setResultCode(NptDict.RST_SUCCESS.getCode());
        } catch (IOException e) {
            log.setResultCode(NptDict.RST_ERROR.getCode());
            log.setResults(ExceptionUtil.stackTraceToString(e, "com.npt"));
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            commonService.save(log);
        }
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/05 上午10:31
     * 备注: 更新
     */
    protected boolean updateInfo(T old, T info) {
        try {
            old.update(info);
            old.setSyncStatus(NptDict.RCS_NOTSYNED.getCode());
            old.setSyncTime(NptCommonUtil.getCurrentSysTimestamp());
            manager.update(old);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected boolean saveInfo(T info) {
        try {
            Serializable id = info.getId();
            T save = manager.save(info);
            if (save.getId().equals(id)) {
                return true;
            } else {
                manager.delete(save);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 作者: 张磊
     * 日期: 2017/03/25 下午04:00
     * 备注:
     */
    @Scheduled(cron = "0 0 */2 * * ?")
    public void syncResponse() {
        NptLog log = commonService.makeLog();
        log.setBusinessName(NptDict.LGB_SYNC.getTitle());
        log.setBusinessType(NptDict.LGB_SYNC.getCode());
        log.setActionName(this.getSyncResponseLogDict().getTitle());
        log.setActionType(this.getSyncResponseLogDict().getCode());
        try {
            //获取需要同步的数据
            List<T> infos = manager.findByCondition(Conditions.eq(NptSyncBase.PROPERTY_SYNC_STATUS, NptDict.RCS_NEEDSYNC.getCode()));
            if (infos.size() > 0) {
                JSONObject jsonObject = new JSONObject();
                //获取回复信息
                jsonObject.put("res", JSON.toJSONString(this.generateResponse(infos)));
                String s = NptHttpUtil.readRdpInterfacePost(commonService.getRemoteSystemByActionType(this.getSyncResponseDict()).iterator().next().getActionUrl(), jsonObject);
                log.setResults(s);
                //如果同步成功，则更新为已同步状态
                if (s.equals("success")) {
                    for (T info : infos) {
                        info.setSyncStatus(NptDict.RCS_SYNED.getCode());
                        info.setSyncTime(NptCommonUtil.getCurrentSysTimestamp());
                        manager.update(info);
                    }
                }
            }
            log.setResultCode(NptDict.RST_SUCCESS.getCode());
        } catch (IOException e) {
            log.setResultCode(NptDict.RST_ERROR.getCode());
            log.setResults(ExceptionUtil.stackTraceToString(e, "com.npt"));
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            commonService.save(log);
        }
    }
}
