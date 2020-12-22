package com.npt.bridge.model;

import com.npt.bridge.arch.NptLogicDataField;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2016/12/19 13:46
 * 描述:
 */
public class NptBaseModelPoolRow implements Serializable{
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
