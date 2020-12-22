package com.npt.bridge.map.bean;

import java.io.Serializable;

/**
 * 项目： NPTWebApp
 * 作者： owen
 * 时间： 2017/4/28 15:20
 * 描述：
 */
public class NptMapPoint implements Serializable{

    private String uId;
    private String title;
    private NptMapNodeRowData mapRow;
    private Boolean hide;
    private Long poolId;
    private String ukValue;
    private Integer dataHost;


    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NptMapNodeRowData getMapRow() {
        return mapRow;
    }

    public void setMapRow(NptMapNodeRowData mapRow) {
        this.mapRow = mapRow;
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

    public Boolean getHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public Integer getDataHost() {
        return dataHost;
    }

    public void setDataHost(Integer dataHost) {
        this.dataHost = dataHost;
    }

    @Override
    public boolean equals(Object obj) {
        if(null != obj && obj instanceof NptMapPoint){
            NptMapPoint that = (NptMapPoint) obj;

            return this.getuId().equals(that.getuId());
        }else {
            return false;
        }
    }
}
