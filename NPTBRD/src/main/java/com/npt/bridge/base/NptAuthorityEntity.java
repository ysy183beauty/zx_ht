package com.npt.bridge.base;



import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/26 12:31
 * 备注：
 *      需要权限验证的数据实体基类
 */
@MappedSuperclass
public class NptAuthorityEntity extends NptEntitySerializable {

    public static final String PROPERTY_PUBLISH_LEVEL = "pubLevel";

    private Integer pubLevel;

    private Integer display;

    private String showStyle;

    private String styleFlag;


   @Column(name = "PUBLISH_LEVEL",nullable = false)
   public Integer getPubLevel() {
        return pubLevel;
    }

    public void setPubLevel(Integer pubLevel) {
        this.pubLevel = pubLevel;
    }

    @Transient
    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    @Column(name = "SHOW_STYLE",length = 128)
    public String getShowStyle() {
        return showStyle;
    }

    public void setShowStyle(String showStyle) {
        this.showStyle = showStyle;
    }

    @Column(name = "STYLE_FLAG",length = 128)
    public String getStyleFlag() {
        return styleFlag;
    }

    public void setStyleFlag(String styleFlag) {
        this.styleFlag = styleFlag;
    }


    @Override
    public void copyTo(NptBaseEntity entity) {
        super.copyTo(entity);
        NptAuthorityEntity _entity = (NptAuthorityEntity) entity;
        _entity.setPubLevel(this.getPubLevel());
        _entity.setDisplay(this.getDisplay());
        _entity.setShowStyle(this.getShowStyle());
        _entity.setStyleFlag(this.getStyleFlag());
    }
}
