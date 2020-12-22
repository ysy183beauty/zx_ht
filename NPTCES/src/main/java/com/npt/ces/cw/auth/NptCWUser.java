package com.npt.ces.cw.auth;

import java.io.Serializable;

/**
 * author: owen
 * date:   2017/7/17 下午5:26
 * note:
 */
public class NptCWUser implements Serializable{

    private String userName;

    private Long userOrgId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserOrgId() {
        return userOrgId;
    }

    public void setUserOrgId(Long userOrgId) {
        this.userOrgId = userOrgId;
    }
}
