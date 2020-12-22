package com.npt.ces.cw.entity;

import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/7/18
 * 备注:
 */
@Entity
@Table(name = "NPT_CW_CPT")
public class NptCWCountType extends NptEntitySerializable {

    public static final String PROPERTY_COMPUTE_TYPE = "computeType";

    private String title;
    private Integer computeType;
    private String countIntervals;

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "CPT_TYPE")
    public Integer getComputeType() {
        return computeType;
    }

    public void setComputeType(Integer computeType) {
        this.computeType = computeType;
    }

    @Column(name = "COUNT_INTERVALS")
    public String getCountIntervals() {
        return countIntervals;
    }

    public void setCountIntervals(String countIntervals) {
        this.countIntervals = countIntervals;
    }

    @Override
    public void copyTo(NptBaseEntity entity) {
        super.copyTo(entity);
        NptCWCountType that = (NptCWCountType) entity;
        that.setTitle(this.getTitle());
        that.setComputeType(this.getComputeType());
        that.setCountIntervals(this.getCountIntervals());
    }
}
