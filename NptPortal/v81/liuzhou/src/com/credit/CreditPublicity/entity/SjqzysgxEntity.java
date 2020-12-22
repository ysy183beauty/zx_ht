package com.credit.CreditPublicity.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/4/1
 * 备注:
 */
@Entity
@Table(name = "SJQZYSGX")
public class SjqzysgxEntity implements Serializable {
    private String qh;
    private String wbj;
    private String sjlmc;
    private Long poolid;
    private Long qzqdxh;
    private String qzqdmc;
    private String xszt;
    private String xh;
    private Long id;

    @Basic
    @Column(name = "QH", nullable = true, length = 2000)
    public String getQh() {
        return qh;
    }

    public void setQh(String qh) {
        this.qh = qh;
    }

    @Basic
    @Column(name = "WBJ", nullable = true, length = 2000)
    public String getWbj() {
        return wbj;
    }

    public void setWbj(String wbj) {
        this.wbj = wbj;
    }

    @Basic
    @Column(name = "SJLMC", nullable = true, length = 2000)
    public String getSjlmc() {
        return sjlmc;
    }

    public void setSjlmc(String sjlmc) {
        this.sjlmc = sjlmc;
    }

    @Basic
    @Column(name = "POOLID", nullable = true, length = 2000)
    public Long getPoolid() {
        return poolid;
    }

    public void setPoolid(Long poolid) {
        this.poolid = poolid;
    }

    @Basic
    @Column(name = "QZQDXH", nullable = true, length = 2000)
    public Long getQzqdxh() {
        return qzqdxh;
    }

    public void setQzqdxh(Long qzqdxh) {
        this.qzqdxh = qzqdxh;
    }

    @Basic
    @Column(name = "QZQDMC", nullable = true, length = 2000)
    public String getQzqdmc() {
        return qzqdmc;
    }

    public void setQzqdmc(String qzqdmc) {
        this.qzqdmc = qzqdmc;
    }

    @Basic
    @Column(name = "XSZT", nullable = true, length = 2000)
    public String getXszt() {
        return xszt;
    }

    public void setXszt(String xszt) {
        this.xszt = xszt;
    }

    @Basic
    @Column(name = "XH", nullable = true, length = 2000)
    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
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
