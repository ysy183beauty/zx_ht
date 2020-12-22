package com.npt.bridge.dataBinder;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/14 12:20
 * 备注：
 */
public class NptWebDetailBlock<T,C,O> implements Serializable{

    private O parentInfo;
    private T blockInfo;
    private Collection<C> columnList;
    private Integer linkedPoolCount;
    private Collection<Long> columnHasLink;
    private Object dataArray;
    private Integer dataCount;
    private String strTempOne;
    private String strTempTwo;
    private String strTempThree;
    private Integer readOnly;
    private Date startTime;
    private Date endTime;

    public NptWebDetailBlock(){
        parentInfo = null;
        blockInfo = null;
        columnList = null;
        linkedPoolCount = 0;
        dataArray = null;
        dataCount = 0;
    }


    public T getBlockInfo() {
        return blockInfo;
    }

    public void setBlockInfo(T blockInfo) {
        this.blockInfo = blockInfo;
    }

    public Collection<C> getColumnList() {
        return columnList;
    }

    public void setColumnList(Collection<C> list) {
        this.columnList = list;
    }

    public Object getDataArray() {
        return dataArray;
    }

    public void setDataArray(Object dataArray) {
        this.dataArray = dataArray;
    }

    public O getParentInfo() {
        return parentInfo;
    }

    public void setParentInfo(O parentInfo) {
        this.parentInfo = parentInfo;
    }

    public Integer getDataCount() {
        return dataCount;
    }

    public void setDataCount(Integer dataCount) {
        this.dataCount = dataCount;
    }

    public Integer getLinkedPoolCount() {
        return linkedPoolCount;
    }

    public void setLinkedPoolCount(Integer linkedPoolCount) {
        this.linkedPoolCount = linkedPoolCount;
    }

    public String getStrTempOne() {
        return strTempOne;
    }

    public void setStrTempOne(String strTempOne) {
        this.strTempOne = strTempOne;
    }

    public String getStrTempTwo() {
        return strTempTwo;
    }

    public void setStrTempTwo(String strTempTwo) {
        this.strTempTwo = strTempTwo;
    }

    public String getStrTempThree() {
        return strTempThree;
    }

    public void setStrTempThree(String strTempThree) {
        this.strTempThree = strTempThree;
    }

    public Integer getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Integer readOnly) {
        this.readOnly = readOnly;
    }

    public Date getStartTime(){ return  startTime; }

    public void setStartTime(Date startTime){ this.startTime = startTime; }

    public Date getEndTime(){ return  endTime; }

    public void setEndTime(Date endTime){ this.endTime = endTime; }

    public Collection<Long> getColumnHasLink() {
        return columnHasLink;
    }

    public void setColumnHasLink(Collection<Long> columnHasLink) {
        this.columnHasLink = columnHasLink;
    }
}
