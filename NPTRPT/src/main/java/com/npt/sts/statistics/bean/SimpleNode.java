package com.npt.sts.statistics.bean;

/**
 * 项目: NPTSTS
 * 作者: 张磊
 * 日期: 16/12/2 下午3:35
 * 备注:
 */
public class SimpleNode implements Comparable<SimpleNode> {
    private String id;
    private String name;
    private Number value;

    public SimpleNode(String id, String name, Number value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public SimpleNode(String name, Number value) {
        this.name = name;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getValue() {
        return value;
    }

    public void setValue(Number value) {
        this.value = value;
    }

    @Override
    public int compareTo(SimpleNode o) {
        return -(this.getValue().intValue() - o.getValue().intValue());
    }
}
