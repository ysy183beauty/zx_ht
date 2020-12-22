package com.npt.bridge.sync.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/3/22
 * 备注: 信用服务回复
 */
public class CreditServiceResponse implements Serializable {
    private Long id;
    private String response;//处理结果
    private Timestamp responseTime;//处理时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Timestamp getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Timestamp responseTime) {
        this.responseTime = responseTime;
    }
}
