package com.npt.bridge.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目： dlcms
 * 作者： owen
 * 时间： 2017/5/4 16:51
 * 描述：
 */
public class NptBaseModelGroupStatistic implements Serializable{

    public NptBaseModelGroupStatistic(String name){
        this.groupName = name;
        this.dataCount = 0L;
        this.poolStatistics = new ArrayList<>();
    }

    private String groupName;

    private Long dataCount;

    private List<NptBaseModelPoolStatistic> poolStatistics;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getDataCount() {
        return dataCount;
    }

    public void setDataCount(Long dataCount) {
        this.dataCount = dataCount;
    }

    public List<NptBaseModelPoolStatistic> getPoolStatistics() {
        return poolStatistics;
    }

    public void setPoolStatistics(List<NptBaseModelPoolStatistic> poolStatistics) {
        this.poolStatistics = poolStatistics;
    }

    public void countSum(){
        for(NptBaseModelPoolStatistic ps:poolStatistics){
            this.dataCount += ps.getDataCount();
        }
    }
}
