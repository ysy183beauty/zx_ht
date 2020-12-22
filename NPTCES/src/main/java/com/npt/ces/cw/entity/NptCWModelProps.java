package com.npt.ces.cw.entity;

import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.base.NptEntitySerializable;
import com.npt.bridge.model.props.bean.NptBaseModelProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * author: owen
 * date:   2017/7/10 上午10:54
 * note:
 *      预警模型属性
 */
@Entity
@Table(name = "NPT_CW_PROP")
public class NptCWModelProps extends NptEntitySerializable implements NptBaseModelProperties{

    public static final String PROPERTY_MODEL_ID = "modelId";

    //基础模型ID
    private Long modelId;
    //预警上限值
    private Integer upLimit;
    //预警下限值
    private Integer lowLimit;

    private String lastBatch;

    private String currentBatch;

    private String cIT;

    private String cTT;

    @Column(name = "MODEL_ID",nullable = false)
    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
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

    @Column(name = "LAST_BATCH")
    public String getLastBatch() {
        return lastBatch;
    }

    public void setLastBatch(String lastBatch) {
        this.lastBatch = lastBatch;
    }

    @Column(name = "CURRENT_BATCH")
    public String getCurrentBatch() {
        return currentBatch;
    }

    public void setCurrentBatch(String currentBatch) {
        this.currentBatch = currentBatch;
    }

    @Column(name = "CREDIT_IT")
    public String getcIT() {
        return cIT;
    }

    public void setcIT(String cIT) {
        this.cIT = cIT;
    }

    @Column(name = "CREDIT_TT")
    public String getcTT() {
        return cTT;
    }

    public void setcTT(String cTT) {
        this.cTT = cTT;
    }

    @Override
    public void copyTo(NptBaseEntity entity) {
        super.copyTo(entity);
        NptCWModelProps that = (NptCWModelProps) entity;
        that.setModelId(this.getModelId());
        that.setUpLimit(this.getUpLimit());
        that.setLowLimit(this.getLowLimit());
        that.setLastBatch(this.getLastBatch());
        that.setCurrentBatch(this.getCurrentBatch());
        that.setcIT(this.getcIT());
        that.setcTT(this.getcTT());
    }
}
