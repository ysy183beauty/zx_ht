package com.credit.CreditCard.entity;

import com.npt.bridge.base.NptEntitySerializableNoID;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;

import javax.persistence.*;

/**
 * 项目： NPTWebApp
 * 作者： owen
 * 时间： 2017/6/11 15:18
 * 描述：
 */
@Entity
@Table(name = "NPT_CREDIT_FOCUS")
public class NptCreditFocus extends NptEntitySerializableNoID {

    public static final String PROPERTY_FROM_CARD_ID = "fromCardId";
    public static final String PROPERTY_TO_CARD_ID = "toCardId";
    public static final String PROPERTY_TO_CARD_TYPE = "toCardType";
    public static final String PROPERTY_HOT_VALUE = "hotValue";

    private Integer id;

    //关注者的信用名片ID
    private Integer fromCardId;

    //被关注者的信用名片ID
    private Integer toCardId;

    private Integer toCardType;

    private Long hotValue;

    public NptCreditFocus() {
        this.setStatus(NptDict.IDS_ENABLED.getCode());
        this.setCreateTime(NptCommonUtil.getCurrentSysTimestamp());
    }

    public NptCreditFocus(Integer fromUserId, Integer toUserId) {
        this();
        this.fromCardId = fromUserId;
        this.toCardId = toUserId;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NPT_CREDIT_FOCUS")
    @SequenceGenerator(name="NPT_CREDIT_FOCUS", sequenceName="S_NPT_CREDIT_FOCUS", allocationSize = 1)
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "FROM_CARD_ID")
    public Integer getFromCardId() {
        return fromCardId;
    }

    public void setFromCardId(Integer fromCardId) {
        this.fromCardId = fromCardId;
    }

    @Column(name = "TO_CARD_ID")
    public Integer getToCardId() {
        return toCardId;
    }

    public void setToCardId(Integer toCardId) {
        this.toCardId = toCardId;
    }

    @Column(name = "TO_CARD_TYPE")
    public Integer getToCardType() {
        return toCardType;
    }

    public void setToCardType(Integer toCardType) {
        this.toCardType = toCardType;
    }

    @Transient
    public Long getHotValue() {
        return hotValue;
    }

    public void setHotValue(Long hotValue) {
        this.hotValue = hotValue;
    }
}
