package com.npt.model.bean;

import com.npt.arch.entity.NptLogicDataField;
import com.npt.model.entity.NptBaseModelPool;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 12:00
 * 描述:
 */
public class NptBaseModelIncData implements Serializable{
    private NptBaseModelPool pool;
    private Collection<NptLogicDataField> fields;
    private List poolData;
    private Integer rowCount;

    public NptBaseModelPool getPool() {
        return pool;
    }

    public void setPool(NptBaseModelPool pool) {
        this.pool = pool;
    }

    public List getPoolData() {
        return poolData;
    }

    public void setPoolData(List poolData) {
        this.poolData = poolData;
    }

    public Collection<NptLogicDataField> getFields() {
        return fields;
    }

    public void setFields(Collection<NptLogicDataField> fields) {
        this.fields = fields;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }
}
