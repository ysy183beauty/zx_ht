package com.npt.ces.cw.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author: owen
 * date:   2017/7/7 下午3:38
 * note:
 */
public class NptCWModel implements Serializable{

    public static final String PROPERTY_NAME_MODEL_ID = "modelId";

    private Long modelId;
    private String modelTitle;
    private Integer upLimit;
    private Integer lowLimit;

    List<NptCWDimension> dimensions;

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getModelTitle() {
        return modelTitle;
    }

    public void setModelTitle(String modelTitle) {
        this.modelTitle = modelTitle;
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

    public List<NptCWDimension> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<NptCWDimension> dimensions) {
        this.dimensions = dimensions;
    }
}
