package com.npt.bridge.map.bean;

import com.npt.bridge.model.NptBaseModelLink;

import java.io.Serializable;

/**
 * author: owen
 * date:   2017/4/10 下午6:09
 * note:
 *      图谱连接线
 */
public class NptMapRecursionLink implements Serializable{

    /**
     * 连接线名称
     */
    private String title;

    /**
     * 基于模型链接的连接线
     */
    private NptBaseModelLink link;

    /**
     * 关系下线结点集合
     */
    private NptMapRecursionNode toNode;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NptBaseModelLink getLink() {
        return link;
    }

    public void setLink(NptBaseModelLink link) {
        this.link = link;
    }

    public NptMapRecursionNode getToNode() {
        return toNode;
    }

    public void setToNode(NptMapRecursionNode toNode) {
        this.toNode = toNode;
    }

    public NptMapLine convertToLine(){
        NptMapLine line = new NptMapLine();

        line.setTitle(this.getTitle());

        return line;
    }
}
