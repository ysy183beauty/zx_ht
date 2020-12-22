package com.npt.bridge.model;

import java.io.Serializable;
import java.util.Collection;

/**
 * 作者: owen
 * 时间: 2017/3/13 上午11:52
 * 描述:
 */
public class NptBaseModelTree implements Serializable{
    private NptBaseModel model;
    Collection<NptBaseModelGroupTree> groupTrees;

    public NptBaseModelTree() {
    }

    public NptBaseModel getModel() {
        return this.model;
    }

    public void setModel(NptBaseModel model) {
        this.model = model;
    }

    public Collection<NptBaseModelGroupTree> getGroupTrees() {
        return this.groupTrees;
    }

    public void setGroupTrees(Collection<NptBaseModelGroupTree> groupTrees) {
        this.groupTrees = groupTrees;
    }
}
