package com.npt.grs.model.manager.control.impl;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.dataBinder.NptWebFieldDataArray;
import com.npt.bridge.dict.NptBusinessCode;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.dsp.manager.oracle.NptOracleManager;
import com.npt.grs.appeal.manager.NptAppealInfoManager;
import com.npt.grs.model.manager.control.NptEntityDataShowStyleController;
import com.npt.rms.base.NptRmsCommonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2016/12/12 14:07
 * 描述:
 */
@Service
public class NptEntityDataShowStyleControllerImpl implements NptEntityDataShowStyleController {

    @Resource(name = "rmsCommonService")
    private NptRmsCommonService commonService;

    @Autowired
    private NptAppealInfoManager appealInfoManager;
    /**
     * 作者：owen
     * 时间：2016/12/12 14:06
     * 描述:
     * 依据字段的显示类别,字段是否已授权等控制信息,将字段的真实数据处理之后写入到字段数据实体类中
     *
     * @param value
     *      字段真实值
     * @param authed
     *      字段已否已授权查询
     * @param field
     *      字段控制体
     * @param fieldData
     *      字段实体数据包装
     */
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
     * 作者：owen
     * 时间：2016/12/12 15:10
     * 描述:
     * 由于一些原因停止实体数据详情的查询,比如某数据已被锁定
     *
     */
    @Override
    public Collection<NptWebFieldDataArray> stopLookingup(String title,Object value) {

        if(NptOracleManager.RF_STATUS.equals(title)){
            try {
                Integer ncf_status = Integer.parseInt(String.valueOf(value));
                if(ncf_status.equals(NptDict.IDS_LOCKED.getCode())){
                    Collection<NptWebFieldDataArray> result = new ArrayDeque<>();
                    NptWebFieldDataArray dataArray = new NptWebFieldDataArray();
                    Collection<NptWebFieldDataArray.NptWebFieldData> fieldDatas = new ArrayDeque<>();

                    NptWebFieldDataArray.NptWebFieldData webFieldData = dataArray.instanceFieldData();
                    webFieldData.setTitle("提示");
                    webFieldData.setValue("信息主体在该业务类别中的数据已被锁定");
                    webFieldData.setLinked(NptCommonUtil.IntegerZero());
                    fieldDatas.add(webFieldData);

                    dataArray.setDataArray(fieldDatas);
                    result.add(dataArray);
                    return result;
                }
            }catch (NumberFormatException e){}
        }
        return null;
    }


    /**
     * 作者：owen
     * 日期：2016/10/24 10:41
     * 备注：
     *      格式化时间类型的值
     * 参数：
     * 返回：
     */
    private String formatDateTime(Object dataObj){

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
