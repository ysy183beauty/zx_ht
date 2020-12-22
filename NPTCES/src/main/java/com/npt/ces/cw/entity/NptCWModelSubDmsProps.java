package com.npt.ces.cw.entity;

import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.base.NptEntitySerializable;
import com.npt.bridge.model.props.bean.NptBaseModelPoolProperties;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * author: owen
 * date:   2017/7/10 上午10:56
 * note:
 *      预警模型单元属性
 */
@Entity
@Table(name = "NPT_CWSDMS_PROP")
public class NptCWModelSubDmsProps extends NptEntitySerializable implements NptBaseModelPoolProperties{

    public static final String PROPERTY_POOL_ID = "poolId";

    //数据池ID
    private Long poolId;
    //预警上限值
    private Integer upLimit;
    //预警下限值
    private Integer lowLimit;
    //预警值折扣
    private Integer disCount;
    //计算方式
    private Integer computeType;
    //数据类别数据主键ID
    private Long uFieldId;
    //事件时间,多个时间使用#隔离
    private String whenFieldId;
    //事件地点
    private Long whereFieldId;
    //事件内容
    private Long whatFieldId;
    //事件金额
    private String amountFieldId;
    //金额的计量单位
    private Integer amountMU;
    //定性计算数据条数区间及区间值  格式示例为    1#3#2,4#10#5,11#20#10    下限#上限#评分
    private String countIntervals;
    //定量计算功效标准值与阈值   格式示例为   239.4#232.,345.6#5435.2,3453.2#534.6,5654.7#65466.5,34535.9#345345.6
    //共计6档5区间
    private String amountGear;

    @Column(name = "POOL_ID",nullable = false)
    public Long getPoolId() {
        return poolId;
    }

    public void setPoolId(Long poolId) {
        this.poolId = poolId;
    }

    @Column(name = "UP_LIMIT")
    public Integer getUpLimit() {
        return upLimit;
    }

    public void setUpLimit(Integer upLimit) {
        this.upLimit = upLimit;
    }

    @Column(name = "LOW_LIMIT")
    public Integer getLowLimit() {
        return lowLimit;
    }

    public void setLowLimit(Integer lowLimit) {
        this.lowLimit = lowLimit;
    }

    @Column(name = "DISCOUNT",nullable = false)
    public Integer getDisCount() {
        return disCount;
    }

    public void setDisCount(Integer disCount) {
        this.disCount = disCount;
    }

    @Column(name = "CPT_TYPE")
    public Integer getComputeType() {
        return computeType;
    }

    public void setComputeType(Integer computeType) {
        this.computeType = computeType;
    }

    @Column(name = "WHEN_FIELD")
    public String getWhenFieldId() {
        return whenFieldId;
    }

    public void setWhenFieldId(String whenFieldId) {
        this.whenFieldId = whenFieldId;
    }

    @Column(name = "WHERE_FIELD")
    public Long getWhereFieldId() {
        return whereFieldId;
    }

    public void setWhereFieldId(Long whereFieldId) {
        this.whereFieldId = whereFieldId;
    }

    @Column(name = "WHAT_FIELD")
    public Long getWhatFieldId() {
        return whatFieldId;
    }

    public void setWhatFieldId(Long whatFieldId) {
        this.whatFieldId = whatFieldId;
    }

    @Column(name = "AMOUNT_FIELD")
    public String getAmountFieldId() {
        return amountFieldId;
    }

    public void setAmountFieldId(String amountFieldId) {
        this.amountFieldId = amountFieldId;
    }

    @Column(name = "AMOUNT_MU")
    public Integer getAmountMU() {
        return amountMU;
    }

    public void setAmountMU(Integer amountMU) {
        this.amountMU = amountMU;
    }


    @Column(name = "UFIELD_ID",nullable = false)
    public Long getuFieldId() {
        return uFieldId;
    }

    public void setuFieldId(Long uFieldId) {
        this.uFieldId = uFieldId;
    }

    @Column(name = "COUNT_INTERVALS",nullable = false,length = 1024)
    public String getCountIntervals() {
        return countIntervals;
    }

    public void setCountIntervals(String countIntervals) {
        this.countIntervals = countIntervals;
    }

    @Column(name = "AMOUNT_GEAR",length = 1024)
    public String getAmountGear() {
        return amountGear;
    }

    public void setAmountGear(String amountGear) {
        this.amountGear = amountGear;
    }



    @Override
    public void copyTo(NptBaseEntity entity) {
        super.copyTo(entity);
        NptCWModelSubDmsProps that = (NptCWModelSubDmsProps) entity;
        that.setPoolId(this.getPoolId());
        that.setUpLimit(this.getUpLimit());
        that.setLowLimit(this.getLowLimit());
        that.setDisCount(this.getDisCount());
        that.setComputeType(this.getComputeType());
        that.setuFieldId(this.getuFieldId());
        that.setWhenFieldId(this.getWhenFieldId());
        that.setWhereFieldId(this.getWhereFieldId());
        that.setWhatFieldId(this.getWhatFieldId());
        that.setAmountFieldId(this.getAmountFieldId());
        that.setAmountMU(this.getAmountMU());
        that.setCountIntervals(this.getCountIntervals());
        that.setAmountGear(this.getAmountGear());
    }

    public List<Long> spliteFieldsId(String prop){
        List<Long> ids = new ArrayList<>();
        if(!StringUtils.isEmpty(prop)){
            try {
                if(prop.contains("#")) {
                    String[] ida = prop.split("#");

                    if (ida.length > 0) {
                        for (String id : ida) {
                            Long idl = Long.parseLong(id);
                            if(idl > 0) {
                                ids.add(idl);
                            }
                        }
                    }
                }else {
                    Long idl = Long.parseLong(prop);
                    if(idl > 0) {
                        ids.add(idl);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return ids;
    }
}
