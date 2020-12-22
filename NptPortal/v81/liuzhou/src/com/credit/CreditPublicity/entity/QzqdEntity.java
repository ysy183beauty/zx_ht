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
@Table(name = "QZQD")
public class QzqdEntity implements Serializable {
    private String xxl;
    private String xh;
    private String xszt;
    private String zxmc;
    private String sdyj;
    private String cnsx;
    private String zrsx;
    private String zzqx;
    private String zzyj;
    private String jdfs;
    private String jjtj;
    private String bz;
    private String wbj;
    private String qzqdlx;
    private Long id;

    @Basic
    @Column(name = "XXL", nullable = true, length = 4000)
    public String getXxl() {
        return xxl;
    }

    public void setXxl(String xxl) {
        this.xxl = xxl;
    }

    @Basic
    @Column(name = "XH", nullable = true, length = 4000)
    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    @Basic
    @Column(name = "XSZT", nullable = true, length = 4000)
    public String getXszt() {
        return xszt;
    }

    public void setXszt(String xszt) {
        this.xszt = xszt;
    }

    @Basic
    @Column(name = "ZXMC", nullable = true, length = 4000)
    public String getZxmc() {
        return zxmc;
    }

    public void setZxmc(String zxmc) {
        this.zxmc = zxmc;
    }

    @Basic
    @Column(name = "SDYJ", nullable = true, length = 4000)
    public String getSdyj() {
        return sdyj;
    }

    public void setSdyj(String sdyj) {
        this.sdyj = sdyj;
    }

    @Basic
    @Column(name = "CNSX", nullable = true, length = 4000)
    public String getCnsx() {
        return cnsx;
    }

    public void setCnsx(String cnsx) {
        this.cnsx = cnsx;
    }

    @Basic
    @Column(name = "ZRSX", nullable = true, length = 4000)
    public String getZrsx() {
        return zrsx;
    }

    public void setZrsx(String zrsx) {
        this.zrsx = zrsx;
    }

    @Basic
    @Column(name = "ZZQX", nullable = true, length = 4000)
    public String getZzqx() {
        return zzqx;
    }

    public void setZzqx(String zzqx) {
        this.zzqx = zzqx;
    }

    @Basic
    @Column(name = "ZZYJ", nullable = true, length = 4000)
    public String getZzyj() {
        return zzyj;
    }

    public void setZzyj(String zzyj) {
        this.zzyj = zzyj;
    }

    @Basic
    @Column(name = "JDFS", nullable = true, length = 4000)
    public String getJdfs() {
        return jdfs;
    }

    public void setJdfs(String jdfs) {
        this.jdfs = jdfs;
    }

    @Basic
    @Column(name = "JJTJ", nullable = true, length = 4000)
    public String getJjtj() {
        return jjtj;
    }

    public void setJjtj(String jjtj) {
        this.jjtj = jjtj;
    }

    @Basic
    @Column(name = "BZ", nullable = true, length = 4000)
    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Basic
    @Column(name = "WBJ", nullable = true, length = 4000)
    public String getWbj() {
        return wbj;
    }

    public void setWbj(String wbj) {
        this.wbj = wbj;
    }

    @Basic
    @Column(name = "QZQDLX", nullable = true, length = 4000)
    public String getQzqdlx() {
        return qzqdlx;
    }

    public void setQzqdlx(String qzqdlx) {
        this.qzqdlx = qzqdlx;
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
