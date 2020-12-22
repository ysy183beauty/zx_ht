package com.npt.sync.action;

import com.alibaba.fastjson.JSON;
import com.npt.arch.entity.NptLogicDataProvider;
import com.npt.dict.NptDict;
import com.npt.model.bean.NptBaseModelIncData;
import com.npt.model.bean.NptBaseModelStructure;
import com.npt.sync.service.NptSyncService;
import com.npt.util.NptHttpDataPack;
import com.npt.util.NptHttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

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


    /**
     *作者：owen
     *时间：2017/1/17 15:57
     *描述:
     *      同步数据提供者
     */
    @RequestMapping(value = "/syncProvider.jspx")
    public void syncDataProvider(HttpServletRequest request,HttpServletResponse response){
        String jsonData = request.getParameter(NptHttpUtil.NPT_REMOTE_PARAM_NAME);

        NptDict result = NptDict.RST_EXCEPTION;
        NptHttpDataPack pack = NptHttpUtil.unpack(jsonData);
        if(null != pack){
            Collection<NptLogicDataProvider> orgList = JSON.parseArray(pack.getRealData(),NptLogicDataProvider.class);
            result = syncService.syncDataProvider(orgList);
        }
        writeResponse(response,result);
    }

    /**
     *作者：owen
     *时间：2017/1/17 15:58
     *描述:
     *      同步模型结构
     */
    @RequestMapping(value = "/syncModelStructure.jspx")
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
    @RequestMapping(value = "/syncModelIncData.jspx")
    public void syncBaseModelIncData(HttpServletRequest request,HttpServletResponse response){
        String jsonData = request.getParameter(NptHttpUtil.NPT_REMOTE_PARAM_NAME);

        NptDict result = NptDict.RST_EXCEPTION;
        NptHttpDataPack pack = NptHttpUtil.unpack(jsonData);
        if(null != pack){
            NptBaseModelIncData poolData = JSON.parseObject(pack.getRealData(),NptBaseModelIncData.class);
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
}
