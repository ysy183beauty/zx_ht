package com.npt.ces.cw.bean;

import com.npt.bridge.dataBinder.NptWebFieldDataArray;
import com.npt.ces.cw.anylize.NptCreditWarningEntityData;
import com.npt.ces.cw.entity.NptCWDmsResult;
import com.npt.ces.cw.entity.NptCWResult;
import com.npt.ces.cw.entity.NptCWSubDmsResult;
import com.npt.ces.cw.entity.NptCWSubDmsResultDetail;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: owen
 * date:   2017/7/7 下午3:17
 * note:
 *
 *      信用实体的预警结果实体数据集装箱
 */
public class NptCWResultDetailBox implements Serializable{

    //信用主体基本信息
    private NptWebFieldDataArray creditEntityBasicInfo;
    //信用主体总结果
    private NptCWResult cwResult;
    //信用主体维度结果
    private List<NptCWDmsResult> dmsResults;
    //信用主体子维度结果集  dmsId-->
    private Map<Long,List<NptCWSubDmsResult>> sdmsResultMap;
    //信用主体子维度结果详情  smdsId-->
    private Map<Long,List<NptCWSubDmsResultDetail>> sdmsRDetailMap;
    //结果是否已准备好
    private Boolean isReady;
    //结果准备情况描述
    private String readyNote;

    public NptCWResultDetailBox(){
        isReady = false;
        readyNote = StringUtils.EMPTY;
        dmsResults = new ArrayList<>();
        sdmsResultMap = new HashMap<>();
        sdmsRDetailMap = new HashMap<>();
    }

    public NptCWResultDetailBox(NptCreditWarningEntityData entityData){
        isReady = false;
        readyNote = StringUtils.EMPTY;
        dmsResults = new ArrayList<>();
        sdmsResultMap = new HashMap<>();
        sdmsRDetailMap = new HashMap<>();

        if(null != entityData && entityData.getDataLoaded()) {
            creditEntityBasicInfo = entityData.getCreditEntityBasicInfo();
        }
    }


    public NptCWResult getCwResult() {
        return cwResult;
    }

    public void setCwResult(NptCWResult cwResult) {
        this.cwResult = cwResult;
    }

    public List<NptCWDmsResult> getDmsResults() {
        return dmsResults;
    }

    public void setDmsResults(List<NptCWDmsResult> dmsResults) {
        this.dmsResults = dmsResults;
    }

    public Map<Long, List<NptCWSubDmsResult>> getSdmsResultMap() {
        return sdmsResultMap;
    }

    public void setSdmsResultMap(Map<Long, List<NptCWSubDmsResult>> sdmsResultMap) {
        this.sdmsResultMap = sdmsResultMap;
    }

    public Map<Long, List<NptCWSubDmsResultDetail>> getSdmsRDetailMap() {
        return sdmsRDetailMap;
    }

    public void setSdmsRDetailMap(Map<Long, List<NptCWSubDmsResultDetail>> sdmsRDetailMap) {
        this.sdmsRDetailMap = sdmsRDetailMap;
    }

    public NptWebFieldDataArray getCreditEntityBasicInfo() {
        return creditEntityBasicInfo;
    }

    public void setCreditEntityBasicInfo(NptWebFieldDataArray creditEntityBasicInfo) {
        this.creditEntityBasicInfo = creditEntityBasicInfo;
    }

    public Boolean getReady() {
        return isReady;
    }

    public void setReady(Boolean ready) {
        isReady = ready;
    }

    public String getReadyNote() {
        return readyNote;
    }

    public void setReadyNote(String readyNote) {
        this.readyNote = readyNote;
    }
}
