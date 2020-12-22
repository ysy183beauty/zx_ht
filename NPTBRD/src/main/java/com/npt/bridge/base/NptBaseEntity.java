package com.npt.bridge.base;


import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/26 12:20
 * 备注：
 *      通用数据实体，具备通用字段
 */
@MappedSuperclass
public class NptBaseEntity {

    @Override
    public boolean equals(Object obj) {
        if(null != obj && null != this && obj instanceof NptBaseEntity){
            NptBaseEntity that = (NptBaseEntity) obj;
            return this.getId().equals(that.getId());
        }
        return false;
    }

    public static final String PROPERTY_ID = "id";
    public static final String PROPERTY_ALIAS = "alias";
    public static final String PROPERTY_PARENT_ID = "parentId";
    public static final String PROPERTY_CREATOR_ID = "creatorId";
    public static final String PROPERTY_CREATE_TIME = "createTime";
    public static final String PROPERTY_STATUS = "status";
    public static final String PROPERTY_DISPLAY_ORDER = "displayOrder";
    public static final Long DEFAULT_PARENT_ID_0L = 0L;
    public static final Integer ALIAS_MAX_LENGTH = 15;

    //实体ID，通用，以Sequence为生成策略
    private Long id;

    //别名
    private String alias;

    //父ID，可用可不用，随业务情况而定
    private Long parentId;

    //父名称
    private String parentName;

    //实体创建人
    private Long creatorId;

    //实体创建时间
    private Date createTime;

    //修改人
    private Long modifyId;

    //最后修改时间
    private Date lastModifyTime;

    //实体状态
    private Integer status;

    //显示顺序
    private Integer displayOrder;

    private Boolean bFlag;

    private Integer iFlag;

    private String sFlag;

    private String reservedField01;
    private String reservedField02;
    private String reservedField03;
    private String reservedField04;
    private String reservedField05;
    private String reservedField06;

    public NptBaseEntity() {
        this.setCreateTime(NptCommonUtil.getCurrentSysTimestamp());
        this.setStatus(NptDict.IDS_ENABLED.getCode());
    }

    @Id
    @Column(name = "ID",nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    @Column(name = "CREATOR_ID")
    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "STATUS",nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "PARENT_ID")
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 作者：owen
     * 日期：2016/10/26 16:34
     * 备注：
     *      将其长度设置为15是ORACLE数据库对字段别名的长度限制为15个汉字
     * 参数：
     * 返回：
     */
    @Column(name = "OBJ_ALIAS",length = 15)
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Column(name = "DISPLAY_ORDER")
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Transient
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Column(name = "RESERVED01",length = 100)
    public String getReservedField01() {
        return reservedField01;
    }

    public void setReservedField01(String reservedField01) {
        this.reservedField01 = reservedField01;
    }

    @Column(name = "RESERVED02",length = 100)
    public String getReservedField02() {
        return reservedField02;
    }

    public void setReservedField02(String reservedField02) {
        this.reservedField02 = reservedField02;
    }

    @Column(name = "RESERVED03",length = 512)
    public String getReservedField03() {
        return reservedField03;
    }

    public void setReservedField03(String reservedField03) {
        this.reservedField03 = reservedField03;
    }

    @Column(name = "RESERVED04",length = 512)
    public String getReservedField04() {
        return reservedField04;
    }

    public void setReservedField04(String reservedField04) {
        this.reservedField04 = reservedField04;
    }

    @Column(name = "RESERVED05",length = 1024)
    public String getReservedField05() {
        return reservedField05;
    }

    public void setReservedField05(String reservedField05) {
        this.reservedField05 = reservedField05;
    }

    @Column(name = "RESERVED06",length = 1024)
    public String getReservedField06() {
        return reservedField06;
    }

    public void setReservedField06(String reservedField06) {
        this.reservedField06 = reservedField06;
    }

    @Transient
    public Boolean getbFlag() {
        return bFlag;
    }

    public void setbFlag(Boolean bFlag) {
        this.bFlag = bFlag;
    }

    @Transient
    public Integer getiFlag() {
        return iFlag;
    }

    public void setiFlag(Integer iFlag) {
        this.iFlag = iFlag;
    }

    @Transient
    public String getsFlag() {
        return sFlag;
    }

    public void setsFlag(String sFlag) {
        this.sFlag = sFlag;
    }

    @Column(name = "MODIFIER_ID")
    public Long getModifyId() {
        return modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public void copyTo(NptBaseEntity entity){

        if(null == entity){
            return;
        }

        entity.setParentId(this.getParentId());
        entity.setAlias(this.getAlias());
        entity.setCreateTime(this.getCreateTime());
        entity.setCreatorId(this.getCreatorId());
        entity.setDisplayOrder(this.getDisplayOrder());
        entity.setbFlag(this.getbFlag());
        entity.setiFlag(this.getiFlag());
        entity.setModifyId(this.getModifyId());
        entity.setStatus(this.getStatus());
        entity.setLastModifyTime(this.getLastModifyTime());
        entity.setReservedField01(this.getReservedField01());
        entity.setReservedField02(this.getReservedField02());
        entity.setReservedField03(this.getReservedField03());
        entity.setReservedField04(this.getReservedField04());
        entity.setReservedField05(this.getReservedField05());
        entity.setReservedField06(this.getReservedField06());
    }


    public Boolean checkIsEnable(){
        return this.getStatus().equals(NptDict.IDS_ENABLED.getCode());
    }
}
