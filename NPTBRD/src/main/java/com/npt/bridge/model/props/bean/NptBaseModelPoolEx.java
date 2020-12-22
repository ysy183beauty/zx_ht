package com.npt.bridge.model.props.bean;

import com.npt.bridge.model.NptBaseModelPool;

import java.io.Serializable;
import java.util.Collection;

/**
 * author: owen
 * date:   2017/7/11 上午11:28
 * note:
 */
public class NptBaseModelPoolEx<P extends NptBaseModelPoolProperties> implements Serializable{

    private NptBaseModelPool modelPool;
    private P poolProperties;

    public NptBaseModelPool getModelPool() {
        return modelPool;
    }

    public void setModelPool(NptBaseModelPool modelPool) {
        this.modelPool = modelPool;
    }

    public P getPoolProperties() {
        return poolProperties;
    }

    public void setPoolProperties(P poolProperties) {
        this.poolProperties = poolProperties;
    }
}
