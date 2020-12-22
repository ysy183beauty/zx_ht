package com.npt.bridge.map.bean;

import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.util.NptCommonUtil;

import java.io.Serializable;
import java.util.List;

/**
 * author: owen
 * date:   2017/4/10 下午6:10
 * note:
 *      图谱结点
 */
public class NptMapRecursionNode implements Serializable{

    /**
     * 结点的逻辑ID
     */
    private String nodeId;
    /**
     * 是否是根结点
     */
    private Boolean isRoot;

    /**
     * 结点标题
     */
    private String title;

    /**
     * 基于数据池的图谱结点
     */
    private NptBaseModelPool node;

    /**
     * 当前结点的实体数据信息
     */
    private NptMapNodeRowData mapRow;

    /**
     * 结点的下线连接
     */
    private List<NptMapRecursionLink> links;

    /**
     * 数据主键的值
     */
    private String ukValue;


    public Boolean getRoot() {
        return isRoot;
    }

    public void setRoot(Boolean root) {
        isRoot = root;
    }

    public Integer getChildCount() {
        return null == links? NptCommonUtil.INTEGER_0:links.size();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NptBaseModelPool getNode() {
        return node;
    }

    public void setNode(NptBaseModelPool node) {
        this.node = node;
    }

    public List<NptMapRecursionLink> getLinks() {
        return links;
    }

    public void setLinks(List<NptMapRecursionLink> links) {
        this.links = links;
    }

    public NptMapNodeRowData getMapRow() {
        return mapRow;
    }

    public void setMapRow(NptMapNodeRowData mapRow) {
        this.mapRow = mapRow;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getUkValue() {
        return ukValue;
    }

    public void setUkValue(String ukValue) {
        this.ukValue = ukValue;
    }

    public NptMapPoint convertToPoint(){
        NptMapPoint point = new NptMapPoint();

        point.setMapRow(this.getMapRow());
        point.setPoolId(this.getNode().getId());
        point.setTitle(this.getTitle());
        point.setuId(this.getNodeId());
        point.setUkValue(this.getUkValue());
        point.setHide(false);
        point.setDataHost(this.getNode().getDataHost());

        return point;
    }
}
