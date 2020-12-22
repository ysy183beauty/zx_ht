package com.npt.grs.query.customModel;

import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.grs.query.NptGRSQueryAction;
import org.springframework.stereotype.Controller;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/27 14:12
 * 备注：
 */
@Controller("npt.grs.query.custom")
public class NptCustomQueryAction extends NptGRSQueryAction {
    public String index(){
        Collection<NptBaseModel> models = baseModelService.listModels(null, NptDict.BMC_CUSTOM);
        if(null != models){
            for(NptBaseModel m:models){
                NptDict dict = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.BMH,m.getHostId());
                if(null != dict)
                    m.setHostTitle(dict.getTitle());
            }
        }
        this.setAttribute("_MODEL_LIST", models);
        return SUCCESS;
    }

}
