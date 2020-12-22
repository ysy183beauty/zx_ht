package com.npt.ces.cw.bean;

import java.io.Serializable;

/**
 * author: owen
 * date:   2017/7/7 下午3:39
 * note:
 */
public class NptCWSubDimension implements Serializable{
    private Long sdmsId;
    private String sdmsTitle;
    private Float sdmsDiscount;
    private Integer upLimit;
    private Integer lowLimit;

    public Long getSdmsId() {
        return sdmsId;
    }

    public void setSdmsId(Long sdmsId) {
        this.sdmsId = sdmsId;
    }

    public String getSdmsTitle() {
        return sdmsTitle;
    }

    public void setSdmsTitle(String sdmsTitle) {
        this.sdmsTitle = sdmsTitle;
    }

    public Float getSdmsDiscount() {
        return sdmsDiscount;
    }

    public void setSdmsDiscount(Float sdmsDiscount) {
        this.sdmsDiscount = sdmsDiscount;
    }

    public Integer getUpLimit() {
        return upLimit;
    }

    public void setUpLimit(Integer upLimit) {
        this.upLimit = upLimit;
    }

    public Integer getLowLimit() {
        return lowLimit;
    }

    public void setLowLimit(Integer lowLimit) {
        this.lowLimit = lowLimit;
    }
}
