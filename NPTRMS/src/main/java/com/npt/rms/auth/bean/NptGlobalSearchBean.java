package com.npt.rms.auth.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/11/2 16:47
 * 备注：
 */
public class NptGlobalSearchBean implements Serializable, Comparable {

    public class NptSearchDataList {
        private String pkValue;
        private Long pkFieldId;
        private List<Object> dataList;
        private Integer searchCount;

        public String getPkValue() {
            return pkValue;
        }

        public void setPkValue(String pkValue) {
            this.pkValue = pkValue;
        }

        public Long getPkFieldId() {
            return pkFieldId;
        }

        public void setPkFieldId(Long pkFieldId) {
            this.pkFieldId = pkFieldId;
        }

        public List<Object> getDataList() {
            return dataList;
        }

        public void setDataList(List<Object> dataList) {
            this.dataList = dataList;
        }

        public Integer getSearchCount() {
            return searchCount;
        }

        public void setSearchCount(Integer searchCount) {
            this.searchCount = searchCount;
        }
    }

    public NptSearchDataList newNptSearchDataList() {
        return new NptSearchDataList();
    }

    private String pkValue;
    private Long pkFieldId;
    private String orgTitle;
    private Long dataTypeId;
    private String dataTypeTitle;
    private List<NptSearchDataList> searchDataList;

    public String getOrgTitle() {
        return orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public Long getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(Long dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public String getDataTypeTitle() {
        return dataTypeTitle;
    }

    public void setDataTypeTitle(String dataTypeTitle) {
        this.dataTypeTitle = dataTypeTitle;
    }

    public List<NptSearchDataList> getSearchDataList() {
        return searchDataList;
    }

    public void setSearchDataList(List<NptSearchDataList> searchDataList) {
        this.searchDataList = searchDataList;
    }

    public Long getPkFieldId() {
        return pkFieldId;
    }

    public void setPkFieldId(Long pkFieldId) {
        this.pkFieldId = pkFieldId;
    }

    public String getPkValue() {
        return pkValue;
    }

    public void setPkValue(String pkValue) {
        this.pkValue = pkValue;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof NptGlobalSearchBean) {
            NptGlobalSearchBean b = (NptGlobalSearchBean) o;
            int thisCount = 0;
            int thatCount = 0;

            if (null != this.searchDataList && this.searchDataList.size() > 0) {
                for (int i = 0; i < this.searchDataList.size(); i++) {
                    thisCount += this.searchDataList.get(i).getSearchCount();
                }
            }

            if (null != b.getSearchDataList() && b.getSearchDataList().size() > 0) {
                for (int i = 0; i < b.getSearchDataList().size(); i++) {
                    thatCount += b.getSearchDataList().get(i).getSearchCount();
                }
            }
            return thatCount - thisCount;
        }
        return -1;
    }
}
