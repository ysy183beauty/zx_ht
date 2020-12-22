package com.npt.ces.cw.entity;

import com.npt.bridge.base.NptEntitySerializable;
import com.sun.org.apache.regexp.internal.RE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * author: owen
 * date:   2017/7/17 下午7:41
 * note:
 *      信用风险指数表
 */
@Entity
@Table(name = "NPT_CW_RISK_INDEX")
public class NptCWRiskIndex extends NptEntitySerializable{

    public static final String PROPERTY_BATCH_NO = "batchNO";
    public static final String PROPERTY_COMPUTE_DAY = "computeDay";
    public static final String PROPERTY_CENTITY_TYPE = "creditEntityType";
    public static final String PROPERTY_RISK_INDEX = "riskIndex";


    //批次号
    private String batchNO;

    //计算日期
    private Date computeDay;

    //信用实体类型
    private Integer creditEntityType;

    //日均指数
    private Integer riskIndex;

    private String year;

    private String weekNo;

    @Column(name = "COM_DAY",nullable = false)
    public Date getComputeDay() {
        return computeDay;
    }

    public void setComputeDay(Date computeDay) {
        this.computeDay = computeDay;
    }

    @Column(name = "CENTITY_TYPE",nullable = false)
    public Integer getCreditEntityType() {
        return creditEntityType;
    }

    public void setCreditEntityType(Integer creditEntityType) {
        this.creditEntityType = creditEntityType;
    }

    @Column(name = "RISK_INDEX",nullable = false)
    public Integer getRiskIndex() {
        return riskIndex;
    }

    public void setRiskIndex(Integer riskIndex) {
        this.riskIndex = riskIndex;
    }

    @Column(name = "BATCH_NO",nullable = false)
    public String getBatchNO() {
        return batchNO;
    }

    public void setBatchNO(String batchNO) {
        this.batchNO = batchNO;
    }

    @Transient
    public String getYear() {
        if(null != batchNO){
            year = batchNO.split("#")[0];
            return year;
        }
        return "";
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Transient
    public String getWeekNo() {
        if(null != batchNO){
            weekNo = batchNO.split("#")[1];
            return weekNo;
        }
        return "";
    }

    public void setWeekNo(String weekNo) {
        this.weekNo = weekNo;
    }

    public void makeYearAndWeekNO(){
        if(null != batchNO){
            year = batchNO.split("#")[0];
            weekNo = batchNO.split("#")[1];
        }
    }

    public NptCWRiskIndex clone(){
        NptCWRiskIndex temp = new NptCWRiskIndex();
        temp.setRiskIndex(this.riskIndex);
        temp.setBatchNO(this.batchNO);
        temp.setWeekNo(this.weekNo);
        temp.setYear(this.year);
        temp.setComputeDay(this.computeDay);
        temp.setCreditEntityType(this.creditEntityType);

        return temp;
    }
}
