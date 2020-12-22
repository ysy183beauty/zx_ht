package com.npt.bridge.model;

import java.io.Serializable;
import java.util.Collection;

/**
 * 作者: owen
 * 时间: 2017/3/13 上午11:52
 * 描述:
 */
public class NptBaseModelGroupTree implements Serializable{
    private NptBaseModelGroup group;
    private Collection<NptBaseModelPool> pools;

    public NptBaseModelGroupTree() {
    }

    public NptBaseModelGroup getGroup() {
        return this.group;
    }

    public void setGroup(NptBaseModelGroup group) {
        this.group = group;
    }

    public Collection<NptBaseModelPool> getPools() {
        return this.pools;
    }

    public void setPools(Collection<NptBaseModelPool> pools) {
        this.pools = pools;
    }
}
