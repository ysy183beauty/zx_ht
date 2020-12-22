package com.npt.bridge.arch;

import com.npt.bridge.base.NptAuthorityEntity;
import com.npt.bridge.base.NptBaseEntity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "NPT_ORGANIZATION")
public class NptLogicDataProvider extends NptAuthorityEntity{
    public static final String PROPERTY_ORG_NAME = "orgName";
    private String orgName;
    private String orgCode;
    private String orgNote;
    private String orgAddr;
    private String orgTel;
    private String orgMail;
    private String orgSite;
    private String orgWechart;

    @Basic
    @Column(name = "ORG_NAME", nullable = false, length = 256)
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Column(name = "ORG_CODE", nullable = false, length = 64)
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Column(name = "ORG_NOTE", nullable = true, length = 2048)
    public String getOrgNote() {
        return orgNote;
    }

    public void setOrgNote(String orgNote) {
        this.orgNote = orgNote;
    }

    @Basic
    @Column(name = "ORG_ADDR", nullable = true, length = 512)
    public String getOrgAddr() {
        return orgAddr;
    }

    public void setOrgAddr(String orgAddr) {
        this.orgAddr = orgAddr;
    }

    @Basic
    @Column(name = "ORG_TEL", nullable = true, length = 128)
    public String getOrgTel() {
        return orgTel;
    }

    public void setOrgTel(String orgTel) {
        this.orgTel = orgTel;
    }

    @Basic
    @Column(name = "ORG_MAIL", nullable = true, length = 128)
    public String getOrgMail() {
        return orgMail;
    }

    public void setOrgMail(String orgMail) {
        this.orgMail = orgMail;
    }

    @Basic
    @Column(name = "ORG_SITE", nullable = true, length = 128)
    public String getOrgSite() {
        return orgSite;
    }

    public void setOrgSite(String orgSite) {
        this.orgSite = orgSite;
    }

    @Basic
    @Column(name = "ORG_WECHART", nullable = true, length = 128)
    public String getOrgWechart() {
        return orgWechart;
    }

    public void setOrgWechart(String orgWechart) {
        this.orgWechart = orgWechart;
    }

    @Override
    public void copyTo(NptBaseEntity entity) {
        super.copyTo(entity);
        NptLogicDataProvider _entity = (NptLogicDataProvider) entity;

        _entity.setOrgWechart(this.getOrgWechart());
        _entity.setOrgTel(this.getOrgTel());
        _entity.setOrgSite(this.getOrgSite());
        _entity.setOrgNote(this.getOrgNote());
        _entity.setOrgAddr(this.getOrgAddr());
        _entity.setOrgCode(this.getOrgCode());
        _entity.setOrgMail(this.getOrgName());
    }


}
