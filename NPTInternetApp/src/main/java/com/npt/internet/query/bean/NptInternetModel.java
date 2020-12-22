package com.npt.internet.query.bean;

import java.io.Serializable;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2016/12/21 13:10
 * 描述:
 */
public class NptInternetModel implements Serializable{

    private Long modelId;
    private String modelTitle;
    private String modelNote;
    private String modelCategory;

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

    public String getModelNote() {
        return modelNote;
    }

    public void setModelNote(String modelNote) {
        this.modelNote = modelNote;
    }

    public String getModelCategory() {
        return modelCategory;
    }

    public void setModelCategory(String modelCategory) {
        this.modelCategory = modelCategory;
    }
}
