package com.npt.bridge.sync.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/4/26
 * 备注:
 */
public class CreditAppealInfo implements Serializable {
    NptUserAppeal userAppeal;
    List<NptUserAppealDetail> appealDetails;

    public NptUserAppeal getUserAppeal() {
        return userAppeal;
    }

    public void setUserAppeal(NptUserAppeal userAppeal) {
        this.userAppeal = userAppeal;
    }

    public List<NptUserAppealDetail> getAppealDetails() {
        return appealDetails;
    }

    public void setAppealDetails(List<NptUserAppealDetail> appealDetails) {
        this.appealDetails = appealDetails;
    }
}
