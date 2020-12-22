package com.szbase.credit.entity.base;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;

public class CreditEntUser extends CreditBaseUser implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -6551789110047481257L;

	private String ENT_ID; // 单位信息id
	private String ENT_NAME; // 企业名称
	private String IS_AUTHENTICATE; // 是否认证: 0 未认证 1 已认证 2 认证审核中
	private String ORGANIZATION_CODE; // 企业组织机构代码
	private String YYZZH; // 营业执照号码
	private String entType; // 组织机构代码类型（企业单位，事业单位，社会团体，机关，公会，民办非企业）
	private String startTime; // 组织机构代码开始时间
	private String endTime; // 组织机构代码结束时间
	private String qy_phone; // 单位电话（非手机号）
	private String qy_email; // 单位邮箱
	private String img1; // 组织机构代码扫描件
	private String img2; // 加盖公章的副本
	private String img3; // 营业执照副本扫描件
	private String img5; // 法人身份证扫描件正面
	private String img6; // 法人身份证扫描件反面
	private String corporationName; // 法人姓名
	private String corporationId; // 法人身份证
	private String failReason; // 认证失败原因

	public String getENT_ID() {
		return ENT_ID;
	}

	public void setENT_ID(String eNT_ID) {
		ENT_ID = eNT_ID;
	}

	public String getENT_NAME() {
		return ENT_NAME;
	}

	public void setENT_NAME(String eNT_NAME) {
		ENT_NAME = eNT_NAME;
	}

	public String getIS_AUTHENTICATE() {
		return IS_AUTHENTICATE;
	}

	public void setIS_AUTHENTICATE(String iS_AUTHENTICATE) {
		IS_AUTHENTICATE = iS_AUTHENTICATE;
	}

	public String getORGANIZATION_CODE() {
		return ORGANIZATION_CODE;
	}

	public void setORGANIZATION_CODE(String oRGANIZATION_CODE) {
		ORGANIZATION_CODE = oRGANIZATION_CODE;
	}

	public String getYYZZH() {
		return YYZZH;
	}

	public void setYYZZH(String yYZZH) {
		YYZZH = yYZZH;
	}

	public String getEntType() {
		return entType;
	}

	public void setEntType(String entType) {
		this.entType = entType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getQy_phone() {
		return qy_phone;
	}

	public void setQy_phone(String qy_phone) {
		this.qy_phone = qy_phone;
	}

	public String getQy_email() {
		return qy_email;
	}

	public void setQy_email(String qy_email) {
		this.qy_email = qy_email;
	}

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public String getImg3() {
		return img3;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
	}

	public String getImg5() {
		return img5;
	}

	public void setImg5(String img5) {
		this.img5 = img5;
	}

	public String getImg6() {
		return img6;
	}

	public void setImg6(String img6) {
		this.img6 = img6;
	}

	public String getCorporationName() {
		return corporationName;
	}

	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}

	public String getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public void setEntvalues(JSONArray Entjson) {
		if (Entjson != null) {
            try {
                if (!Entjson.get(0).equals(null))
                    this.setENT_ID(Entjson.get(0).toString());
                else
                    this.setENT_ID("");
                if (!Entjson.get(1).equals(null))
                    this.setENT_NAME(Entjson.get(1).toString());
                else
                    this.setENT_NAME("");
                if (!Entjson.get(2).equals(null))
                    this.setIS_AUTHENTICATE(Entjson.get(2).toString());
                else
                    this.setIS_AUTHENTICATE("");
                if (!Entjson.get(3).equals(null))
                    this.setORGANIZATION_CODE(Entjson.get(3).toString());
                else
                    this.setORGANIZATION_CODE("");
                if (!Entjson.get(4).equals(null))
                    this.setYYZZH(Entjson.get(4).toString());
                else
                    this.setYYZZH("");
                if (!Entjson.get(5).equals(null))
                    this.setEntType(Entjson.get(5).toString());
                else
                    this.setEntType("");
                if (!Entjson.get(6).equals(null))
                    this.setStartTime(Entjson.get(6).toString());
                else
                    this.setStartTime("");
                if (!Entjson.get(7).equals(null))
                    this.setEndTime(Entjson.get(7).toString());
                else
                    this.setEndTime("");
                if (!Entjson.get(8).equals(null))
                    this.setQy_phone(Entjson.get(8).toString());
                else
                    this.setQy_phone("");
                if (!Entjson.get(9).equals(null))
                    this.setQy_email(Entjson.get(9).toString());
                else
                    this.setQy_email("");
                if (!Entjson.get(10).equals(null))
                    this.setImg1(Entjson.get(10).toString());
                else
                    this.setImg1("");
                if (!Entjson.get(11).equals(null))
                    this.setImg2(Entjson.get(11).toString());
                else
                    this.setImg2("");
                if (!Entjson.get(12).equals(null))
                    this.setImg3(Entjson.get(12).toString());
                else
                    this.setImg3("");
                if (!Entjson.get(13).equals(null))
                    this.setImg5(Entjson.get(13).toString());
                else
                    this.setImg5("");
                if (!Entjson.get(14).equals(null))
                    this.setImg6(Entjson.get(14).toString());
                else
                    this.setImg6("");
                if (!Entjson.get(15).equals(null))
                    this.setCorporationName(Entjson.get(15).toString());
                else
                    this.setCorporationName("");
                if (!Entjson.get(16).equals(null))
                    this.setCorporationId(Entjson.get(16).toString());
                else
                    this.setCorporationId("");
                if (!Entjson.get(17).equals(null))
                    this.setFailReason(Entjson.get(17).toString());
                else
                    this.setFailReason("");
            } catch (JSONException e) {
                e.printStackTrace();
            }

		} else {
			this.setFailReason("");
			this.setCorporationId("");			
			this.setCorporationName("");
			this.setImg6("");
			this.setImg5("");
			this.setImg3("");
			this.setImg2("");
			this.setImg1("");
			this.setQy_email("");
			this.setQy_phone("");
			this.setEndTime("");
			this.setStartTime("");
			this.setEntType("");
			this.setYYZZH("");
			this.setORGANIZATION_CODE("");
			this.setIS_AUTHENTICATE("");
			this.setENT_NAME("");
			this.setENT_ID("");
		}
	}

}
