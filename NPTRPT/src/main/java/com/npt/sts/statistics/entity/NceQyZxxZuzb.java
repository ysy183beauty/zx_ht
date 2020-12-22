package com.npt.sts.statistics.entity;

import javax.persistence.*;

/**
 * 项目: NPTSTS
 * 作者: 张磊
 * 日期: 16/12/6 下午5:42
 * 备注: 企业实体
 */
@Entity
@Table(name = "NPT_STS_QY")
public class NceQyZxxZuzb implements java.io.Serializable {
    private String jgmc;
    private Long id;

    @Basic
    @Column(name = "JGMC", nullable = true, length = 200)
    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    @Id
    @Column(name = "ID", nullable = true, precision = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
