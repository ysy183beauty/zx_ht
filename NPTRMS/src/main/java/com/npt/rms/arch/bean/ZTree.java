package com.npt.rms.arch.bean;

/**
 * 项目: NPTRMS
 * 作者: 张磊
 * 日期: 16/9/27 下午9:40
 * 备注: 用于保存org树形结构
 */
public class ZTree {
    private String id;
    private String pId;
    private String name;
    private boolean isParent;
    private String iconSkin;
    private boolean open;

    public String getIconSkin() { return iconSkin;  }

    public void setIconSkin(String iconSkin) { this.iconSkin = iconSkin; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
