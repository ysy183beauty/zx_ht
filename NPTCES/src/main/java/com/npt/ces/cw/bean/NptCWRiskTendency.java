package com.npt.ces.cw.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * author: owen
 * date:   2017/7/18 下午5:27
 * note:
 */
public class NptCWRiskTendency implements Serializable{

    private String batchNo;
    private Map<String,Integer> dsMap;


    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Map<String, Integer> getDsMap() {
        return dsMap;
    }

    public void setDsMap(Map<String, Integer> dsMap) {
        this.dsMap = dsMap;
    }

    public NptCWRiskTendency clone(){
        NptCWRiskTendency temp = new NptCWRiskTendency();
        temp.batchNo = this.batchNo;

        if(null != this.dsMap){
            temp.dsMap = new HashMap<>();
            temp.dsMap.putAll(dsMap);
        }

        return temp;
    }
}
