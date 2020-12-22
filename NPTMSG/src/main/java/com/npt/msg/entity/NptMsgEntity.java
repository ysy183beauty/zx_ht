package com.npt.msg.entity;

import com.npt.bridge.base.NptEntitySerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 项目： NPTWebApp
 * 作者： owen
 * 时间： 2017/6/6 17:06
 * 描述：
 *
 *      消息体信息
 */
@Entity
@Table(name = "NPT_MSG")
public class NptMsgEntity extends NptEntitySerializable{

    //发送者
    private Long fromUserId;
    //接收者
    private Long toUserId;
    //消息体
    private String msgText;
    //发送时间
    private Date msgDateTime;
    //发送渠道
    private Integer msgChannel;
    //发送结果
    private Integer resultCode;
    //发送结果信息
    private String resultInfo;

    @Column(name = "FROM_USER_ID",nullable = false)
    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    @Column(name = "TO_USER_ID",nullable = false)
    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    @Column(name = "MSG_TEXT",nullable = false)
    public String getMsgText(){
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    @Column(name = "DATA_TIME",nullable = false)
    public Date getMsgDateTime() { return msgDateTime;
    }

    public void setMsgDateTime(Date msgDateTime) {
        this.msgDateTime = msgDateTime;
    }

    @Column(name = "MSG_CHANNEL",nullable = false)
    public Integer getMsgChannel() {
        return msgChannel;
    }

    public void setMsgChannel(Integer msgChannel) {
        this.msgChannel = msgChannel;
    }

    @Column(name = "RESULT_CODE",nullable = false)
    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    @Column(name = "RESULT_INFO")
    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }
}
