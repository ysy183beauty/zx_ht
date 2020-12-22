package com.npt.sts.statistics.entity;

import javax.persistence.*;

/**
 * 项目: NPTSTS
 * 作者: 张磊
 * 日期: 16/12/6 下午5:42
 * 备注: 个人实体
 */
@Entity
@Table(name = "NPT_STS_RK")
public class NceRkZxxZuzb implements java.io.Serializable {
    private Long id;
    private String xm;

    @Id
    @Column(name = "ID", nullable = true, precision = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "XM", nullable = true, length = 200)
    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }
}
