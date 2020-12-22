package com.npt.internet.model.action;

import com.alibaba.fastjson.JSON;
import com.npt.bridge.model.NptBaseModelPoolRow;
import com.npt.bridge.model.NptBaseModelStructure;
import com.npt.internet.model.service.NptInternetSyncService;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.rms.base.action.NptRMSAction;
import com.npt.bridge.dict.NptRmsDict;
import com.npt.bridge.util.NptHttpDataPack;
import com.npt.bridge.util.NptHttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/29 15:08
 * 描述:
 *      内部系统向外部系统的数据同步接口
 *
 *      宗旨：外部系统的一切表现由内部系统配置并同步到外部系统
 *
 *      外部系统主要是作数据的查询功能，而查询的组织架构是以内部系统的基础模型为模板，
 *      而基础模型又有一些依赖项，故每次同步分两个批次：
 *      【先同步依赖项，再同步基础模型信息】
 *
 */
@Controller("npt.internet.sync")
public class NptInternetSyncAction extends NptRMSAction{

    @Autowired
    private NptInternetSyncService syncService;
    /**
     *作者：OWEN
     *时间：2016/12/1 22:15
     *描述:
     *      同步数据提供单位
     */
    public void syncDataProvider(){
        String jsonData = getParameter(NptHttpUtil.NPT_REMOTE_PARAM_NAME);

        NptRmsDict result = NptRmsDict.RST_EXCEPTION;
        NptHttpDataPack pack = NptHttpUtil.unpack(jsonData);
        if(null != pack){
            Collection<NptLogicDataProvider> orgList = JSON.parseArray(pack.getRealData(),NptLogicDataProvider.class);
            result = syncService.syncDataProvider(orgList);
        }
        writeResponse(this.getResponse(),result);
    }

    /**
     *作者：owen
     *时间：2016/12/14 14:42
     *描述:
     *      同步基础模型
     */
    public void syncBaseModel(){
        String jsonData = getParameter(NptHttpUtil.NPT_REMOTE_PARAM_NAME);

        NptRmsDict result = NptRmsDict.RST_EXCEPTION;
        NptHttpDataPack pack = NptHttpUtil.unpack(jsonData);
        if(null != pack){
            NptBaseModelStructure structure = JSON.parseObject(pack.getRealData(),NptBaseModelStructure.class);
            result = syncService.syncBaseModel(structure);
        }
        writeResponse(this.getResponse(),result);
    }

    /**
     *作者：owen
     *时间：2016/12/14 14:42
     *描述:
     *      同步基础模型数据
     */
    public void syncBaseModelData(){
        String jsonData = getParameter(NptHttpUtil.NPT_REMOTE_PARAM_NAME);

        NptRmsDict result = NptRmsDict.RST_EXCEPTION;
        NptHttpDataPack pack = NptHttpUtil.unpack(jsonData);
        if(null != pack){
            NptBaseModelPoolRow poolData = JSON.parseObject(pack.getRealData(),NptBaseModelPoolRow.class);
            result = syncService.syncBaseModelData(poolData);
        }
        writeResponse(this.getResponse(),result);
    }

    private void writeResponse(HttpServletResponse response,NptRmsDict result){
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
}
