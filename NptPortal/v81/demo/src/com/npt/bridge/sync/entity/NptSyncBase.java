package com.npt.bridge.sync.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/3/25
 * 备注:
 */
@MappedSuperclass
public abstract class NptSyncBase<ID extends Serializable> implements Serializable {

    public static final String PROPERTY_ID = "id";
    public static final String PROPERTY_SYNC_STATUS = "syncStatus";
    public static final String PROPERTY_CREATE_TIME = "createTime";

    private ID id;

    @JSONField(serialize = false)
    private Timestamp createTime;//创建时间
    @JSONField(serialize = false)
    private Integer syncStatus;//是否同步
    @JSONField(serialize = false)
    private Timestamp syncTime;//最后同步时间

    public NptSyncBase() {
        this.createTime = NptCommonUtil.getCurrentSysTimestamp();
        this.syncStatus = NptDict.RCS_NOTSYNED.getCode();
        this.syncTime = NptCommonUtil.getCurrentSysTimestamp();
    }

    @Id
    @Column(name = "ID")
    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CREATE_TIME", nullable = false)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "SYNC_STATUS", nullable = false)
    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    @Basic
    @Column(name = "SYNC_TIME")
    public Timestamp getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Timestamp syncTime) {
        this.syncTime = syncTime;
    }

    public abstract <T extends NptSyncBase> void update(T entity);
}
