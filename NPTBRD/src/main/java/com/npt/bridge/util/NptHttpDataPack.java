package com.npt.bridge.util;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/28 14:33
 * 描述:
 */
public class NptHttpDataPack implements Serializable{

    private String nptSecurityFlag;
    private Date sysDate;
    private String realData;

    public String getNptSecurityFlag() {
        return nptSecurityFlag;
    }

    public void setNptSecurityFlag(String nptSecurityFlag) {
        this.nptSecurityFlag = nptSecurityFlag;
    }

    public Date getSysDate() {
        return sysDate;
    }

    public void setSysDate(Date sysDate) {
        this.sysDate = sysDate;
    }

    public String getRealData() {
        return realData;
    }

    public void setRealData(String realData) {
        this.realData = realData;
    }
}
