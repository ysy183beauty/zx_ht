package com.npt.bridge.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目： dlcms
 * 作者： owen
 * 时间： 2017/5/4 16:46
 * 描述：
 *      模型统计信息
 */
public class NptBaseModelStatistic implements Serializable{


    public NptBaseModelStatistic(String name){
        this.modelName = name;
        this.dataCount = 0L;
        this.groupStatistics = new ArrayList<>();
    }

    private String modelName;


    private Long dataCount;


    private List<NptBaseModelGroupStatistic> groupStatistics;


    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getDataCount() {
        return dataCount;
    }

    public void setDataCount(Long dataCount) {
        this.dataCount = dataCount;
    }

    public List<NptBaseModelGroupStatistic> getGroupStatistics() {
        return groupStatistics;
    }

    public void setGroupStatistics(List<NptBaseModelGroupStatistic> groupStatistics) {
        this.groupStatistics = groupStatistics;
    }

    public void countSum(){
        for(NptBaseModelGroupStatistic gs:groupStatistics){
            this.dataCount += gs.getDataCount();
        }
    }
}
