package com.npt.internet.query;

import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.dict.NptRmsDict;
import com.npt.bridge.util.NptCommonUtil;
import org.springframework.stereotype.Controller;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2016/12/14 19:32
 * 描述:
 */
@Controller("npt.internet.blackred")
public class NptRedBlackQueryAction extends NptInternetQueryAction{


    /**
     * 作者：owen
     * 时间：2016/12/14 19:34
     * 描述:
     * 获取每个业务域自身的模型信息
     */
    @Override
    public NptBaseModel getMyQueryModel() {
        Collection<NptBaseModel> models = modelQuerier.listModels(NptRmsDict.BMH_BLACKRED,NptRmsDict.BMC_OUTSIDE);
        if(null != models && models.size() == NptCommonUtil.IntegerOne()){
            return models.iterator().next();
        }
        return null;
    }

    /**
     *作者：owen
     *时间：2016/12/20 6:47
     *描述:
     *      获取黑榜数据池
     */
    public String getBlackPoolList(){
        NptBaseModelGroup blackGroup = getModelGroupByCode(NptRmsDict.BMHG_BLACKRED_BLACK);
        if (null != blackGroup) {
            Collection<NptBaseModelPool> pools = modelQuerier.listModelGrouPools(blackGroup);
            this.setAttribute("_POOL_LIST", pools);
        }
        return SUCCESS;
    }

    /**
     *作者：owen
     *时间：2016/12/20 6:50
     *描述:
     *      获取红榜数据池列表
     */
    public String getRedPoolList(){
        NptBaseModelGroup redGroup = getModelGroupByCode(NptRmsDict.BMHG_BLACKRED_RED);
        if (null != redGroup) {
            Collection<NptBaseModelPool> pools = modelQuerier.listModelGrouPools(redGroup);
            this.setAttribute("_POOL_LIST", pools);
        }
        return SUCCESS;
    }

    private NptBaseModelGroup getModelGroupByCode(NptRmsDict gCode){
        NptBaseModel thisModel = getMyQueryModel();
        if(null != thisModel) {
            Collection<NptBaseModelGroup> groups = modelQuerier.listModelGroups(thisModel);
            if (null != groups && !groups.isEmpty()) {
                for (NptBaseModelGroup g : groups) {
                    if (null != g.getSpecialCode() && g.getSpecialCode().equals(gCode.getCode())) {
                        return g;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 作者：xuqinyuan
     * 时间：2016/12/21 10:09
     * 备注： iframe跨域处理页面
     */
    public String handleFrame(){ return SUCCESS; };
}
