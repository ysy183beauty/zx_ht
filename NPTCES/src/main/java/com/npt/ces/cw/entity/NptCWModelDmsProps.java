package com.npt.ces.cw.entity;

import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.base.NptEntitySerializable;
import com.npt.bridge.model.props.bean.NptBaseModelGroupProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * author: owen
 * date:   2017/7/10 上午10:55
 * note:
 *      预警模型维度属性
 */
@Entity
@Table(name = "NPT_CWDMS_PROP")
public class NptCWModelDmsProps extends NptEntitySerializable implements NptBaseModelGroupProperties{

    public static final String PROPERTY_GROUP_ID = "groupId";

    //模型分组ID
    private Long groupId;
    //预警上限值
    private Integer upLimit;
    //预警下限值
    private Integer lowLimit;
    //预警值折扣
    private Integer disCount;
    //维度被指
    private String note;

    @Column(name = "GROUP_ID",nullable = false)
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Column(name = "UP_LIMIT",nullable = false)
    public Integer getUpLimit() {
        return upLimit;
    }

    public void setUpLimit(Integer upLimit) {
        this.upLimit = upLimit;
    }

    @Column(name = "LOW_LIMIT",nullable = false)
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

    @Column(name = "NOTE")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public void copyTo(NptBaseEntity entity) {
        super.copyTo(entity);
        NptCWModelDmsProps that = (NptCWModelDmsProps) entity;
        that.setGroupId(this.getGroupId());
        that.setUpLimit(this.getUpLimit());
        that.setLowLimit(this.getLowLimit());
        that.setDisCount(this.getDisCount());
        that.setNote(this.getNote());
    }
}
