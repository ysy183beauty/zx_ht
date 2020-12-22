package com.npt.bridge.model.props.bean;

import com.npt.bridge.model.NptBaseModel;

import java.io.Serializable;

/**
 * author: owen
 * date:   2017/7/11 上午11:27
 * note:
 */
public class NptBaseModelEx<T extends NptBaseModelProperties> implements Serializable{

    private NptBaseModel baseModel;
    private T modelProperties;

    public NptBaseModel getBaseModel() {
        return baseModel;
    }

    public void setBaseModel(NptBaseModel baseModel) {
        this.baseModel = baseModel;
    }

    public T getModelProperties() {
        return modelProperties;
    }

    public void setModelProperties(T modelProperties) {
        this.modelProperties = modelProperties;
    }
}
