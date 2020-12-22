package com.credit.CreditQuery.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author jiaoss
 * @date 2017年3月2日16:30:38
 * 备注：
 *      用户基础模型信息
 */
@Entity
@Table(name = "JC_USER_APPLY_INFO")
public class ApplyInfo implements Serializable {

    private int id;
    private int userId;
    private String applyTime;
    /**
     * @see com.npt.bridge.dict.NptDict#USER_APPLY_SUBMIT
     */
    private String applyFlag;
    private String replyTime;
    private String applyType;
    /**
     * @see com.npt.bridge.dict.NptDict#RCS_NOTSYNED
     */
    private String syncFlag;
    private Timestamp syncTime;
    private String applyFlagValue;

    public static final String APPLY_FLAG_1="申请提交";
    public static final String APPLY_FLAG_2="申请处理中";
    public static final String APPLY_FLAG_3="申请成功";
    public static final String APPLY_FLAG_4="申请失败";
    public static final String APPLY_FLAG_5="申请过期";



    public ApplyInfo(){

    }

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="JC_USER_APPLY_INFO")
    @SequenceGenerator(name="JC_USER_APPLY_INFO", sequenceName="S_JC_USER_APPLY_INFO")
    @Column(name="ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Column(name="USER_ID")
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name="APPLY_TIME",length = 60)
    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }


    @Column(name="APPLY_FLAG",length = 20)
    public String getApplyFlag() {
        return applyFlag;
    }

    public void setApplyFlag(String applyFlag) {
        this.applyFlag = applyFlag;
    }

    @Column(name="APPLY_TYPE",length = 20)
    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    @Column(name="REPLY_TIME",length = 60)
    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    @Column(name="SYNC_FLAG",length = 20)
    public String getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(String syncFlag) {
        this.syncFlag = syncFlag;
    }

    @Column(name = "SYNC_TIME")
    public Timestamp getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Timestamp syncTime) {
        this.syncTime = syncTime;
    }

    public void setApplyFlagValue(String applyFlagValue) {
        this.applyFlagValue = applyFlagValue;
    }
    @Transient
    public String  getApplyFlagValue(){
        if(this.applyFlag.equals("1")){
            return APPLY_FLAG_1;
        }else  if(this.applyFlag.equals("2")){
            return APPLY_FLAG_2;
        }else  if(this.applyFlag.equals("3")){
            return APPLY_FLAG_3;
        }else  if(this.applyFlag.equals("4")){
            return APPLY_FLAG_4;
        }else if(this.applyFlag.equals("5")){
            return APPLY_FLAG_5;
        }
        return APPLY_FLAG_1;
    }

}
