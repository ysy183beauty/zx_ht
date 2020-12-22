package com.npt.bridge.map.bean;

import java.io.Serializable;

/**
 * 项目： NPTWebApp
 * 作者： owen
 * 时间： 2017/4/28 15:18
 * 描述：
 */
public class NptMapSegment implements Serializable{

    private NptMapPoint fromPoint;
    private NptMapPoint toPoint;
    private NptMapLine linkLine;

    public NptMapSegment(NptMapPoint f,NptMapLine l,NptMapPoint t){
        this.fromPoint = f;
        this.linkLine = l;
        this.toPoint = t;
    }


    public NptMapPoint getFromPoint() {
        return fromPoint;
    }

    public void setFromPoint(NptMapPoint fromPoint) {
        this.fromPoint = fromPoint;
    }

    public NptMapPoint getToPoint() {
        return toPoint;
    }

    public void setToPoint(NptMapPoint toPoint) {
        this.toPoint = toPoint;
    }

    public NptMapLine getLinkLine() {
        return linkLine;
    }

    public void setLinkLine(NptMapLine linkLine) {
        this.linkLine = linkLine;
    }
}
