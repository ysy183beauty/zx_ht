package com.npt.bridge.dataBinder;

import java.io.Serializable;
import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/10/18 17:11
 * 备注：
 */
public class NptWebDetailGroup implements Serializable{

    private Long groupCode;
    private String groupName;
    private String groupTitle;
    private Collection<NptWebDetailBlock> blockList;

    public Long getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(Long groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public Collection<NptWebDetailBlock> getBlockList() {
        return blockList;
    }

    public void setBlockList(Collection<NptWebDetailBlock> blockList) {
        this.blockList = blockList;
    }
}
