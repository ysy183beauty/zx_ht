package com.npt.internet.model.entity;

import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/12/1 14:03
 * 描述:
 */
@Entity
@Table(name = "NPT_DATA_PROVIDER")
public class NptDataProvider extends NptEntitySerializable{
    private String providerName;
    private String providerTitle;
    private String providerCode;
    private String providerNote;
    private String providerTel;
    private String providerSite;

    @Column(name = "PROVIDER_NAME",nullable = false,length = 128)
    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    @Column(name = "PROVIDER_TITLE",nullable = false,length = 128)
    public String getProviderTitle() {
        return providerTitle;
    }

    public void setProviderTitle(String providerTitle) {
        this.providerTitle = providerTitle;
    }

    @Column(name = "PROVIDER_CODE",length = 32)
    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    @Column(name = "PROVIDER_NOTE",length = 512)
    public String getProviderNote() {
        return providerNote;
    }

    public void setProviderNote(String providerNote) {
        this.providerNote = providerNote;
    }

    @Column(name = "PROVIDER_TEL")
    public String getProviderTel() {
        return providerTel;
    }

    public void setProviderTel(String providerTel) {
        this.providerTel = providerTel;
    }

    @Column(name = "PROVIDER_SITE")
    public String getProviderSite() {
        return providerSite;
    }

    public void setProviderSite(String providerSite) {
        this.providerSite = providerSite;
    }
}
