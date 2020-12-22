package com.npt.bridge.model;

import com.npt.bridge.dataBinder.NptWebFieldDataArray;

import java.io.Serializable;
import java.util.Collection;

/**
 * author: ${user}
 * date:   ${date} ${time}
 * note:
 */
public class NptBaseModelPoolRowsData implements Serializable{

    private NptBaseModelPool pool;
    private Collection<NptWebFieldDataArray> titleValues;


    public NptBaseModelPool getPool() {
        return pool;
    }

    public void setPool(NptBaseModelPool pool) {
        this.pool = pool;
    }

    public Collection<NptWebFieldDataArray> getTitleValues() {
        return titleValues;
    }

    public void setTitleValues(Collection<NptWebFieldDataArray> titleValues) {
        this.titleValues = titleValues;
    }
}
