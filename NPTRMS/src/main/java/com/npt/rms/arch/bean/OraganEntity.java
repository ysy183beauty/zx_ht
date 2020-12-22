package com.npt.rms.arch.bean;

public class OraganEntity {
    private  Integer ID;
    private  String ORG_ADDR;
    private String ORG_CODE;
    private String ORG_NAME;
    private boolean IS_OPEN;
    private Integer PARENT_ID;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public boolean isIS_OPEN() {
        return IS_OPEN;
    }

    public void setIS_OPEN(boolean IS_OPEN) {
        this.IS_OPEN = IS_OPEN;
    }

    public String getORG_ADDR() {
        return ORG_ADDR;
    }

    public void setORG_ADDR(String ORG_ADDR) {
        this.ORG_ADDR = ORG_ADDR;
    }

    public String getORG_CODE() {
        return ORG_CODE;
    }

    public void setORG_CODE(String ORG_CODE) {
        this.ORG_CODE = ORG_CODE;
    }

    public String getORG_NAME() {
        return ORG_NAME;
    }

    public void setORG_NAME(String ORG_NAME) {
        this.ORG_NAME = ORG_NAME;
    }

    public Integer getPARENT_ID() {
        return PARENT_ID;
    }

    public void setPARENT_ID(Integer PARENT_ID) {
        this.PARENT_ID = PARENT_ID;
    }
}
