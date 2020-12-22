package com.npt.bridge.dataBinder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/12 15:06
 * 备注：
 *      下拉列表
 */
public class NptWebDropBox<T> implements Serializable{

    private String title;
    private String name;

    //下拉元素集合
    private List<T> orderedElements;


    public NptWebDropBox(String name,String title){
        this.name = name;
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getOrderedElements() {
        return orderedElements;
    }

    public void setOrderedElements(List<T> orderedElements) {
        this.orderedElements = orderedElements;
    }
}
