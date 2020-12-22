package com.npt.bridge.sync.entity;

import java.io.Serializable;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/3/22
 * 备注:
 */
public class CreditCmsUserResponse implements Serializable {
    private Integer id;
    private String flag;
    private String failComment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFailComment() {
        return failComment;
    }

    public void setFailComment(String failComment) {
        this.failComment = failComment;
    }
}
