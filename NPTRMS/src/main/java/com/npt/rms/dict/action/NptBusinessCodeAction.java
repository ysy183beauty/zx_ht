package com.npt.rms.dict.action;

import com.npt.bridge.dict.NptBusinessCode;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.base.action.NptRMSAction;
import com.npt.rms.dict.manager.NptBusinessCodeManager;
import com.npt.rms.util.NptRmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.summer.extend.orm.condition.Conditions;

import java.util.*;

/**
 * 项目: NPTWebApp
 * 作者: zhanglei
 * 日期: 2017/3/14
 * 备注:
 */
@Controller("npt.rms.dict")
public class NptBusinessCodeAction extends NptRMSAction<NptBusinessCode> {

    @Autowired
    private NptBusinessCodeManager manager;

    public String list() {
        Long fieldId = this.getLongParameter("fieldId");
        List<NptBusinessCode> codes = manager.findByCondition(Conditions.eq(NptBusinessCode.PROPERTY_CODE_FIELDID, fieldId));
        this.setAttribute("_CODES", codes);
        this.setAttribute("_FIELD_ID", fieldId);
        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/20 下午09:03
     * 备注: 保存码表
     */
    public void edit() {
        try {
            Long fieldId = getLongParameter("fieldId");
            String[] codeNames = getParameterValues("codeName[]");
            String[] codeValues = getParameterValues("codeValue[]");

            //利用hashmap去重
            Map<String, String> codes = new HashMap<>();
            for (int i = 0; i < codeNames.length; i++) {
                if (!codeValues[i].isEmpty()) {
                    codes.put(codeValues[i], codeNames[i]);
                }
            }

            Collection<NptBusinessCode> businessCodes = new ArrayList<>();
            for (Map.Entry<String, String> entry : codes.entrySet()) {
                NptBusinessCode businessCode = new NptBusinessCode();
                businessCode.setParentId(NptCommonUtil.getDefaultParentId());
                businessCode.setCreateTime(NptCommonUtil.getCurrentSysDate());
                businessCode.setCreatorId(NptRmsUtil.getCurrentUserId());
                businessCode.setStatus(NptDict.IDS_ENABLED.getCode());

                businessCode.setFieldId(fieldId);
                businessCode.setCodeName(entry.getValue());
                businessCode.setCodeTitle(entry.getValue());
                businessCode.setCodeValue(entry.getKey());

                businessCodes.add(businessCode);
            }

            manager.deleteAndSaveCode(fieldId, businessCodes);
            this.outputSuccessResult("保存成功");
        } catch (Exception e) {
            this.outputErrorResult("保存失败");
            e.printStackTrace();
        }
    }
}
