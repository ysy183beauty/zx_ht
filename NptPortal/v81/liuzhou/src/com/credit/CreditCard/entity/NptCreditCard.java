package com.credit.CreditCard.entity;

import com.npt.bridge.base.NptEntitySerializableNoID;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;

import javax.persistence.*;

/**
 * 项目： NPTWebApp
 * 作者： owen
 * 时间： 2017/6/11 15:16
 * 描述：
 *
 *      信用名片主体
 *
 *      信用名片需要用户确认开通，确认开通的用户会在此表中创建一条名片记录
 */
@Entity
@Table(name = "NPT_CREDIT_CARD")
public class NptCreditCard extends NptEntitySerializableNoID {

    public static final String PROPERTY_CARD_TYPE = "cardType";
    public static final String PROPERTY_CARD_TITLE = "cardTitle";
    public static final String PROPERTY_USER_ID = "userId";

    //主键，值同 userId
    private Integer id;

    //名片类别,由NPTDICT中的PDM确定
    private Integer cardType;

    //名片标题
    private String cardTitle;

    //用户ID
    private Integer userId;

    //信用体唯一标识
    private String idCard;

    //名片数据在模型数据池中的数据主键的值，即某行记录
    private Long ukValue;

    //名片头像路径
    private String imgPath;

    //关系
    private Integer relation;

    private Long hotValue;

    private String cardDesc;

    public NptCreditCard() {
        this.setStatus(NptDict.IDS_ENABLED.getCode());
        this.setCreateTime(NptCommonUtil.getCurrentSysTimestamp());
    }

    public NptCreditCard(Integer userId) {
        this();
        this.id = userId;
        this.userId = userId;
    }

    @Id
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "CARD_TYPE")
    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    @Column(name = "CARD_TITLE")
    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    @Column(name = "USER_ID")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "ID_CARD")
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Column(name = "UK_VALUE")
    public Long getUkValue() {
        return ukValue;
    }

    public void setUkValue(Long ukValue) {
        this.ukValue = ukValue;
    }

    @Column(name = "IMG_PATH")
    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Transient
    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

    @Transient
    public Long getHotValue() {
        return hotValue;
    }

    public void setHotValue(Long hotValue) {
        this.hotValue = hotValue;
    }

    @Transient
    public String getCardDesc() {
        return cardDesc;
    }

    public void setCardDesc(String cardDesc) {
        this.cardDesc = cardDesc;
    }
}
