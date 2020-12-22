package com.npt.bridge.model;

import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2016/12/14 11:28
 * 描述:
 */
@Entity
@Table(name = "NPT_BMPOOL_TIMESTAMP")
public class NptBaseModelPoolStamp extends NptEntitySerializable {

    public static final String POOL_ID = "poolId";
    public static final String USE_BY = "useBy";
    public static final String LAST_TIME = "lastTime";

    /**
     * 这地方的POOLID其实是数据池对应的数据类别的ID
     */
    private Long poolId;
    private Timestamp lastTime;
    private Integer useBy;

    @Column(name = "POOL_ID",nullable = false)
    public Long getPoolId() {
        return poolId;
    }

    public void setPoolId(Long poolId) {
        this.poolId = poolId;
    }

    @Column(name = "LAST_TIME",nullable = false)
    public Timestamp getLastTime() {
        return lastTime;
    }

    public void setLastTime(Timestamp lastTime) {
        this.lastTime = lastTime;
    }

    @Column(name = "USE_BY",nullable = false)
    public Integer getUseBy() {
        return useBy;
    }

    public void setUseBy(Integer useBy) {
        this.useBy = useBy;
    }
}
