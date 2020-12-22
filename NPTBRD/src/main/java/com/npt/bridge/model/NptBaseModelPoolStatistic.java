package com.npt.bridge.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.npt.bridge.util.NptCommonUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目： dlcms
 * 作者： owen
 * 时间： 2017/5/4 16:51
 * 描述：
 */
public class NptBaseModelPoolStatistic implements Serializable{
    public NptBaseModelPoolStatistic() {
    }

    public NptBaseModelPoolStatistic(String name){
        this.poolName = name;
        this.dataCount = 0L;
        poolId = NptCommonUtil.getDefaultParentId();
        searchKey = StringUtils.EMPTY;
        this.hotDataTitles = new ArrayList<>();
    }

    @JSONField(name="text")
    private String poolName;
    @JSONField(name="count")
    private Long dataCount;

    private Long poolId;
    private String searchKey;

    private Integer poolScore;

    private List<String> hotDataTitles;

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public Integer getPoolScore() {
        return poolScore;
    }

    public void setPoolScore(Integer poolScore) {
        this.poolScore = poolScore;
    }

    public Long getDataCount() {
        return dataCount;
    }

    public void setDataCount(Long dataCount) {
        this.dataCount = dataCount;
    }

    public List<String> getHotDataTitles() {
        return hotDataTitles;
    }

    public void setHotDataTitles(List<String> hotDataTitles) {
        this.hotDataTitles = hotDataTitles;
    }

    public Long getPoolId() {
        return poolId;
    }

    public void setPoolId(Long poolId) {
        this.poolId = poolId;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}
