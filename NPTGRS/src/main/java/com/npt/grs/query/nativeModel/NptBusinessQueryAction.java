package com.npt.grs.query.nativeModel;

import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.grs.query.NptGRSQueryAction;
import org.springframework.stereotype.Controller;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/11 14:03
 * 备注：
 */
@Controller("npt.grs.query.business")
public class NptBusinessQueryAction extends NptGRSQueryAction {
    /**
     * 作者：owen
     * 日期：2016/10/20 14:19
     * 备注：
     * 获取当前的基础模型信息
     * 若子类重写之，则返回子类自身控制的模型信息
     * 若子类不重写之，则从页面接收模型ID并加载之
     * 参数：
     * 返回：
     */
    @Override
    public NptBaseModel getCurrentBaseModel() {
        return baseModelService.getBaseModelHostNativeModel(NptDict.BMH_BUSINESS);
    }
}
