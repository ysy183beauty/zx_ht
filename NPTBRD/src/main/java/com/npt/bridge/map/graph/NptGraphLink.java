package com.npt.bridge.map.graph;

import java.io.Serializable;

/**
 * author: owen
 * date:   2017/4/12 下午5:36
 * note:
 */
public class NptGraphLink implements Serializable{

    /**
     * 以下为ECHART依赖的连接线属性
     */
    private String source;
    private String target;
    private Integer weight;
    private String text;
    private Boolean show;
    private String srcUniqueId;
    private String tarUniqueId;


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public String getSrcUniqueId() {
        return srcUniqueId;
    }

    public void setSrcUniqueId(String srcUniqueId) {
        this.srcUniqueId = srcUniqueId;
    }

    public String getTarUniqueId() {
        return tarUniqueId;
    }

    public void setTarUniqueId(String tarUniqueId) {
        this.tarUniqueId = tarUniqueId;
    }

    @Override
    public int hashCode() {
        return (source+"-"+target).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NptGraphLink && ((NptGraphLink) obj).getSource().equals(source) && ((NptGraphLink) obj).getTarget().equals(target);
    }
}
