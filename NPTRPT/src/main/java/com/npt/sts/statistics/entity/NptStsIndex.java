package com.npt.sts.statistics.entity;

import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 项目: NPTSTS
 * 作者: 张磊
 * 日期: 16/12/6 下午5:32
 * 备注:
 */
@Entity
@Table(name = "NPT_STS_INDEX")
public class NptStsIndex extends NptEntitySerializable {
    private String name;
    private String title;
    private String url;

    @Basic
    @Column(name = "NAME", nullable = false, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "TITLE", nullable = false, length = 128)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "URL", nullable = false, length = 128)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
