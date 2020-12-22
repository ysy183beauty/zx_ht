package com.credit.CreditCard.entity;

import com.npt.bridge.base.NptEntitySerializableNoID;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;

import javax.persistence.*;

/**
 * 项目： NPTWebApp
 * 作者： owen
 * 时间： 2017/6/11 15:19
 * 描述：
 */
@Entity
@Table(name = "NPT_CREDIT_SCHLOG")
public class NptCreditSearchLog extends NptEntitySerializableNoID {

    public static final String PROPERTY_SEARCH_KEY = "searchKey";
    public static final String PROPERTY_SEARCH_TYPE = "searchType";
    public static final String PROPERTY_HOT_VALUE = "hotValue";

    private Integer id;

    //查询关键字
    private String searchKey;

    //查询类别
    private Integer searchType;

    private Long hotValue;

    public NptCreditSearchLog() {
        this.setStatus(NptDict.IDS_ENABLED.getCode());
        this.setCreateTime(NptCommonUtil.getCurrentSysTimestamp());
    }

    public NptCreditSearchLog(String searchKey, Integer searchType) {
        this();
        this.searchKey = searchKey;
        this.searchType = searchType;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NPT_CREDIT_SCHLOG")
    @SequenceGenerator(name="NPT_CREDIT_SCHLOG", sequenceName="S_NPT_CREDIT_SCHLOG", allocationSize = 1)
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "SEARCH_KEY")
    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    @Column(name = "SEARCH_TYPE")
    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    @Transient
    public Long getHotValue() {
        return hotValue;
    }

    public void setHotValue(Long hotValue) {
        this.hotValue = hotValue;
    }
}
