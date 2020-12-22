package com.npt.ces.cw.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author: owen
 * date:   2017/7/7 下午3:35
 * note:
 */
public class NptCWDimension implements Serializable{

    private Long dmsId;
    private String dmsTitle;
    private Float dmsDiscount;
    private Integer upLimit;
    private Integer lowLimit;

    private List<NptCWSubDimension> subDimensions;

    public Long getDmsId() {
        return dmsId;
    }

    public void setDmsId(Long dmsId) {
        this.dmsId = dmsId;
    }

    public String getDmsTitle() {
        return dmsTitle;
    }

    public void setDmsTitle(String dmsTitle) {
        this.dmsTitle = dmsTitle;
    }

    public Float getDmsDiscount() {
        return dmsDiscount;
    }

    public void setDmsDiscount(Float dmsDiscount) {
        this.dmsDiscount = dmsDiscount;
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

    public List<NptCWSubDimension> getSubDimensions() {
        return subDimensions;
    }

    public void setSubDimensions(List<NptCWSubDimension> subDimensions) {
        this.subDimensions = subDimensions;
    }
}
