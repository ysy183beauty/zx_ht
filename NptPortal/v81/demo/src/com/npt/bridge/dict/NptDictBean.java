package com.npt.bridge.dict;

import java.io.Serializable;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2016/12/22 13:57
 * 描述:
 */
public class NptDictBean implements Serializable{
    private String name;
    private String title;
    private Integer code;

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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
