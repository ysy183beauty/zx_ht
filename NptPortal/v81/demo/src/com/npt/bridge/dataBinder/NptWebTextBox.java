package com.npt.bridge.dataBinder;

import java.io.Serializable;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/12 15:13
 * 备注：
 *      文本编辑框
 */
public class NptWebTextBox implements Serializable{

    //内部名称
    private String name;
    //显示标题
    private String title;
    //属性值
    private String value;

    public NptWebTextBox(String name,String title){
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
