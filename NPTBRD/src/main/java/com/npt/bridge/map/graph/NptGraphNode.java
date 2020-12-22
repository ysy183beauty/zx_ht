package com.npt.bridge.map.graph;

import java.io.Serializable;

/**
 * author: owen
 * date:   2017/4/12 下午5:32
 * note:
 */
public class NptGraphNode implements Serializable{

    /**
     * 以下属性为echart依赖的结点属性
     */
    private String id;
    private Integer category;
    private String name;
    private String label;
    private Integer weight;
    private Integer symbolSize;
    private Boolean ignore;
    private Boolean flag;
    private String content;
    private String dataHost;

    private Long poolId;
    private String ukValue;
    private String uniqueId;
    /**
     * 以下为自定义属性
     */


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getSymbolSize() {
        return symbolSize;
    }

    public void setSymbolSize(Integer symbolSize) {
        this.symbolSize = symbolSize;
    }

    public Boolean getIgnore() {
        return ignore;
    }

    public void setIgnore(Boolean ignore) {
        this.ignore = ignore;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPoolId() {
        return poolId;
    }

    public void setPoolId(Long poolId) {
        this.poolId = poolId;
    }

    public String getUkValue() {
        return ukValue;
    }

    public void setUkValue(String ukValue) {
        this.ukValue = ukValue;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NptGraphNode && id.equals(((NptGraphNode) obj).getId());
    }

    public String getDataHost() {
        return dataHost;
    }

    public void setDataHost(String dataHost) {
        this.dataHost = dataHost;
    }
}
