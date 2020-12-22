package com.npt.dsp.bean;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/11 9:52
 * 备注：
 */
public class NptDataScanResult {

    private Integer insertCount;

    private Integer updateCount;

    private Integer deletedCount;

    private Integer unknowCount;

    public Integer getInsertCount() {
        return insertCount;
    }

    public void setInsertCount(Integer insertCount) {
        this.insertCount = insertCount;
    }

    public Integer getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(Integer updateCount) {
        this.updateCount = updateCount;
    }

    public Integer getDeletedCount() {
        return deletedCount;
    }

    public void setDeletedCount(Integer deletedCount) {
        this.deletedCount = deletedCount;
    }

    public Integer getUnknowCount() {
        return unknowCount;
    }

    public void setUnknowCount(Integer unknowCount) {
        this.unknowCount = unknowCount;
    }
}
