package com.credit.CreditCard.user;

/**
 * 项目： NPTWebApp
 * 作者： owen
 * 时间： 2017/6/12 11:59
 * 描述：
 */
public class NptCreditCardUser {

    private Integer userId;

    private String userIdCard;

    private Integer userType;

    private boolean isVerified;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserIdCard() {
        return userIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
