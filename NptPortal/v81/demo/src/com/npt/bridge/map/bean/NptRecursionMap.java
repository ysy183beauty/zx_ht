package com.npt.bridge.map.bean;

import com.npt.bridge.dict.NptDict;

import java.io.Serializable;

/**
 * author: owen
 * date:   2017/4/10 下午6:20
 * note:
 *      图谱数据
 */
public class NptRecursionMap implements Serializable{

    /**
     * 根结点
     */
    private NptMapRecursionNode rootNode;

    private NptDict result;

    public NptMapRecursionNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(NptMapRecursionNode rootNode) {
        this.rootNode = rootNode;
    }

    public NptDict getResult() {
        return result;
    }

    public void setResult(NptDict result) {
        this.result = result;
    }
}
