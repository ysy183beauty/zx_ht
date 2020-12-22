package com.npt.bridge.model.manager.impl;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.dataBinder.NptWebFieldDataArray;
import com.npt.bridge.dict.NptBusinessCode;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.manager.NptEntityDataShowStyleController;
import com.npt.bridge.model.manager.NptRmsCommonService;
import com.npt.bridge.util.NptCommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/3/28
 * 备注:
 */
@Service
public class NptEntityDataShowStyleControllerImpl implements NptEntityDataShowStyleController {

    @Autowired
    private NptRmsCommonService commonService;

    @Override
    public void makeValueShowStyle(Object value, boolean authed, NptLogicDataField field, NptWebFieldDataArray.NptWebFieldData fieldData) {
        Assert.notNull(fieldData);
        Assert.notNull(field);
        //默认不可视且未申请过或当前申请无效
        Integer iAuth = new Integer(NptDict.FAL_AUTH.getCode());
        if (iAuth.equals(field.getPubLevel()) && !authed) {
            fieldData.setValue("***");
            fieldData.setValueNote(NptDict.FAL_AUTH.getTitle());
            fieldData.setLinked(NptCommonUtil.IntegerZero());
            return;
        }
        if (NptDict.FSS_DATE.name().equals(field.getShowStyle())) {
            //时间类型
            fieldData.setValue(formatDateTime(value));
        } else if (NptDict.FSS_CODE.name().equals(field.getShowStyle())) {
            //码表类型
            fieldData.setValue(loadCodeTitle(value,field.getId()));
        } else if (NptDict.FSS_PARTHIDE_TEXT.name().equals(field.getShowStyle())) {
            //部分显示
            if (authed) {
                fieldData.setValue(String.valueOf(value));
            } else {
                fieldData.setValue(hideLastPart(value));
                fieldData.setLinked(NptCommonUtil.IntegerZero());
                fieldData.setValueNote(NptDict.FSS_PARTHIDE_TEXT.getTitle());
            }
        } else if (NptDict.FSS_IMG_PATH.name().equals(field.getShowStyle())) {
            //图片路径方式
            return;
        } else if(NptDict.FSS_IMG_DATE.name().equals(field.getShowStyle())){
            //图片字段方式
            return;
        } else {
            //原值无限制
            fieldData.setValue(String.valueOf(value));
        }
    }


    /**
     * 作者: 张磊
     * 日期: 2017/03/28 下午09:27
     * 备注: 格式化时间类型的值
     */
    private String formatDateTime(Object dataObj) {

        String dateStr = String.valueOf(dataObj).replaceAll("\\s*", ""); ;
        SimpleDateFormat stdFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = (Date) dataObj;

            return stdFormat.format(date);
        }catch (Exception e){
        }

        for(String pattern:NptCommonUtil.TIME_PATTERN_LIST){
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

                Date date = dateFormat.parse(dateStr);

                return stdFormat.format(date);
            }catch (Exception e){
            }
        }

        return dateStr;
    }

    /**
     * 作者：owen
     * 日期：2016/10/24 10:49
     * 备注：
     *      码值转换
     * 参数：
     * 返回：
     */
    private String loadCodeTitle(Object v, Long fieldId) {

        String codeValue = String.valueOf(v);
        NptBusinessCode code = commonService.getBusinessCode(codeValue, fieldId);
        if (null != code) {
            return code.getCodeName();
        }

        return String.valueOf(v);
    }

    /**
     * 作者：owen
     * 日期：2016/10/24 10:49
     * 备注：
     *      隐藏后半部分
     * 参数：
     * 返回：
     */
    private String hideLastPart(Object v){
        String value = String.valueOf(v);
        if(!StringUtils.isBlank(value)) {
            if (1 == value.length()) {
                return "***";
            } else if (value.length() >= 2 && value.length() < 6) {
                return value.substring(0, value.length() / 2) + "***";
            } else if (value.length() >= 6 && value.length() < 15) {
                return value.substring(0, value.length() / 3) + "***" + value.substring(value.length() * 2 / 3);
            } else {
                return value.substring(0, 6) + "***";
            }
        }
        return "";
    }
}
