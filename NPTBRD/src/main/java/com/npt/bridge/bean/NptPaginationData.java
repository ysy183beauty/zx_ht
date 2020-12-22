package com.npt.bridge.bean;

import java.io.Serializable;
import java.util.Collection;

/**
 * 项目： NPTWebApp
 * 作者： owen
 * 时间： 2017/6/12 9:41
 * 描述：
 */
public class NptPaginationData<T> implements Serializable {

    private int pageSize;
    private int currentPage;
    private long totalCount;
    private Collection<T> results;

    public NptPaginationData() {
        this.pageSize = 10;
        this.totalCount = 0L;
        this.results = null;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public Collection<T> getResults() {
        return results;
    }

    public void setResults(Collection<T> results) {
        this.results = results;
    }
}
