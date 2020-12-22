package com.npt.bridge.dataBinder;

import java.io.Serializable;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/12 15:03
 * 备注：
 *      下拉列表元素
 */
public class NptWebDropBoxElement implements Serializable{

    //id
    private Long id;

    //显示名称
    private String title;

    public NptWebDropBoxElement(Long id,String title){
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
