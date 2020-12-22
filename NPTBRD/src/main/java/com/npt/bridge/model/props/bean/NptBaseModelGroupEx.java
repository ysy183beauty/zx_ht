package com.npt.bridge.model.props.bean;

import com.npt.bridge.model.NptBaseModelGroup;

import java.io.Serializable;

/**
 * author: owen
 * date:   2017/7/11 上午11:27
 * note:
 */
public class NptBaseModelGroupEx<T extends NptBaseModelGroupProperties> implements Serializable{

    private NptBaseModelGroup modelGroup;
    private T groupProperties;

    public NptBaseModelGroup getModelGroup() {
        return modelGroup;
    }

    public void setModelGroup(NptBaseModelGroup modelGroup) {
        this.modelGroup = modelGroup;
    }

    public T getGroupProperties() {
        return groupProperties;
    }

    public void setGroupProperties(T groupProperties) {
        this.groupProperties = groupProperties;
    }
}
