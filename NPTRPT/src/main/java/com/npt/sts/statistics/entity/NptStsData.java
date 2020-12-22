package com.npt.sts.statistics.entity;

import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.base.NptEntitySerializable;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * 项目: NPTSTS
 * 作者: 张磊
 * 日期: 16/12/6 下午5:33
 * 备注:
 */
@Entity
@Table(name = "NPT_STS_DATA")
public class NptStsData extends NptEntitySerializable {

    public static final String PROPERTY_STS_TYPE_ID = "stsTypeId";
    public static final String PROPERTY_SJL = "sjl";

    private Long stsTypeId;
    private Integer creditType;
    private String stsCode;
    private Long sjl;
    private NptLogicDataProvider org;
    private NceQyZxxZuzb qy;
    private NceRkZxxZuzb rk;
    private Timestamp dataTime;

    public NptStsData() {
    }

    public NptStsData(Long stsTypeId, Long sjl) {
        this.stsTypeId = stsTypeId;
        this.sjl = sjl;
        this.dataTime = NptCommonUtil.getCurrentSysTimestamp();
        this.setStatus(NptDict.IDS_DISABLED.getCode());
    }

    @Basic
    @Column(name = "STS_TYPE_ID", nullable = true, precision = 0)
    public Long getStsTypeId() {
        return stsTypeId;
    }

    public void setStsTypeId(Long stsTypeId) {
        this.stsTypeId = stsTypeId;
    }

    @Basic
    @Column(name = "CREDIT_TYPE", nullable = true, precision = 0)
    public Integer getCreditType() {
        return creditType;
    }

    public void setCreditType(Integer creditType) {
        this.creditType = creditType;
    }

    @Basic
    @Column(name = "STS_CODE")
    public String getStsCode() {
        return stsCode;
    }

    public void setStsCode(String stsCode) {
        this.stsCode = stsCode;
    }

    @Basic
    @Column(name = "SJL", nullable = true, precision = 0)
    public Long getSjl() {
        return sjl;
    }

    public void setSjl(Long sjl) {
        this.sjl = sjl;
    }

    @ManyToOne
    @JoinColumn(name = "ORG_ID", referencedColumnName = "ID")
    public NptLogicDataProvider getOrg() {
        return org;
    }

    public void setOrg(NptLogicDataProvider org) {
        this.org = org;
    }

    @ManyToOne
    @JoinColumn(name = "QY_ID", referencedColumnName = "ID")
    public NceQyZxxZuzb getQy() {
        return qy;
    }

    public void setQy(NceQyZxxZuzb qy) {
        this.qy = qy;
    }

    @ManyToOne
    @JoinColumn(name = "RK_ID", referencedColumnName = "ID")
    public NceRkZxxZuzb getRk() {
        return rk;
    }

    public void setRk(NceRkZxxZuzb rk) {
        this.rk = rk;
    }

    @Basic
    @Column(name = "DATA_TIME", nullable = true)
    public Timestamp getDataTime() {
        return dataTime;
    }

    public void setDataTime(Timestamp dataTime) {
        this.dataTime = dataTime;
    }
}
