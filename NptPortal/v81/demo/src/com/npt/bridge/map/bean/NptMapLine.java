package com.npt.bridge.map.bean;

import java.io.Serializable;

/**
 * 项目： NPTWebApp
 * 作者： owen
 * 时间： 2017/4/28 15:21
 * 描述：
 */
public class NptMapLine implements Serializable{

    public static final String FORWARD = "FORWARD";
    public static final String BACKWARD = "BACKWARD";
    public static final String DOUBLE = "DOUBLE";

    private String title;

    private String  direction;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
