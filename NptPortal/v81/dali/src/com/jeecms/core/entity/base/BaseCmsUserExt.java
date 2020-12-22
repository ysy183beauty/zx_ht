package com.jeecms.core.entity.base;

import javax.persistence.Transient;
import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_user_ext table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_user_ext"
 */

public abstract class BaseCmsUserExt  implements Serializable {

	public static String REF = "CmsUserExt";
	public static String PROP_MSN = "msn";
	public static String PROP_BIRTHDAY = "birthday";
	public static String PROP_GENDER = "gender";
	public static String PROP_MOBILE = "mobile";
	public static String PROP_COMEFROM = "comefrom";
	public static String PROP_USER = "user";
	public static String PROP_INTRO = "intro";
	public static String PROP_REALNAME = "realname";
	public static String PROP_QQ = "qq";
	public static String PROP_ID = "id";
	public static String PROP_PHONE = "phone";
    public static String PROP_TYPE_PERSON = "个人账户";
    public static String PROP_TYPE_COMPANY = "公司账户";
    public static String PROP_FLAG_1 = "未实名认证";
    public static String PROP_FLAG_2 = "实名认证中";
    public static String PROP_FLAG_3 = "实名认证成功";
    public static String PROP_FLAG_4 = "实名认证失败";



	// constructors
	public BaseCmsUserExt () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsUserExt (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String realname;
	private java.lang.Boolean gender;
	private java.util.Date birthday;
	private java.lang.String intro;
	private java.lang.String comefrom;
	private java.lang.String qq;
	private java.lang.String msn;
	private java.lang.String phone;
	private java.lang.String mobile;
	private java.lang.String userImg;
	private java.lang.String userSignature;
    private java.lang.String idCard;
    private java.lang.String type;
    private java.lang.String flag;
    private java.lang.String idCardImg;
    private java.lang.String failComment;
    private java.lang.String syncFlag;
    private java.lang.String syncTime;

    private java.lang.String flagValue;
    private java.lang.String typeValue;

	// one to one
	private com.jeecms.core.entity.CmsUser user;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="foreign"
     *  column="user_id"
     */
	public java.lang.Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: realname
	 */
	public java.lang.String getRealname () {
		return realname;
	}

	/**
	 * Set the value related to the column: realname
	 * @param realname the realname value
	 */
	public void setRealname (java.lang.String realname) {
		this.realname = realname;
	}


	/**
	 * Return the value associated with the column: gender
	 */
	public java.lang.Boolean getGender () {
		return gender;
	}

	/**
	 * Set the value related to the column: gender
	 * @param gender the gender value
	 */
	public void setGender (java.lang.Boolean gender) {
		this.gender = gender;
	}


	/**
	 * Return the value associated with the column: birthday
	 */
	public java.util.Date getBirthday () {
		return birthday;
	}

	/**
	 * Set the value related to the column: birthday
	 * @param birthday the birthday value
	 */
	public void setBirthday (java.util.Date birthday) {
		this.birthday = birthday;
	}


	/**
	 * Return the value associated with the column: intro
	 */
	public java.lang.String getIntro () {
		return intro;
	}

	/**
	 * Set the value related to the column: intro
	 * @param intro the intro value
	 */
	public void setIntro (java.lang.String intro) {
		this.intro = intro;
	}


	/**
	 * Return the value associated with the column: comefrom
	 */
	public java.lang.String getComefrom () {
		return comefrom;
	}

	/**
	 * Set the value related to the column: comefrom
	 * @param comefrom the comefrom value
	 */
	public void setComefrom (java.lang.String comefrom) {
		this.comefrom = comefrom;
	}


	/**
	 * Return the value associated with the column: qq
	 */
	public java.lang.String getQq () {
		return qq;
	}

	/**
	 * Set the value related to the column: qq
	 * @param qq the qq value
	 */
	public void setQq (java.lang.String qq) {
		this.qq = qq;
	}


	/**
	 * Return the value associated with the column: msn
	 */
	public java.lang.String getMsn () {
		return msn;
	}

	/**
	 * Set the value related to the column: msn
	 * @param msn the msn value
	 */
	public void setMsn (java.lang.String msn) {
		this.msn = msn;
	}


	/**
	 * Return the value associated with the column: phone
	 */
	public java.lang.String getPhone () {
		return phone;
	}

	/**
	 * Set the value related to the column: phone
	 * @param phone the phone value
	 */
	public void setPhone (java.lang.String phone) {
		this.phone = phone;
	}


	/**
	 * Return the value associated with the column: mobile
	 */
	public java.lang.String getMobile () {
		return mobile;
	}

	/**
	 * Set the value related to the column: mobile
	 * @param mobile the mobile value
	 */
	public void setMobile (java.lang.String mobile) {
		this.mobile = mobile;
	}
	public java.lang.String getUserImg() {
		return userImg;
	}

	public void setUserImg(java.lang.String userImg) {
		this.userImg = userImg;
	}

	public java.lang.String getUserSignature() {
		return userSignature;
	}

	public void setUserSignature(java.lang.String userSignature) {
		this.userSignature = userSignature;
	}

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getIdCardImg() {
        return idCardImg;
    }

    public void setIdCardImg(String idCardImg) {
        this.idCardImg = idCardImg;
    }

    public String getFailComment() {
        return failComment;
    }

    public void setFailComment(String failComment) {
        this.failComment = failComment;
    }

    @Transient
    public String getFlagValue(){
	    if("".equals(flag) || "1".equals(flag)){
            return PROP_FLAG_1;
        }else if("2".equals(flag)){
            return PROP_FLAG_2;
        }else if("3".equals(flag)){
            return PROP_FLAG_3;
        }else if("4".equals(flag)){
            return PROP_FLAG_4;
        }

        return PROP_FLAG_1;
    }
    public void setFlagValue(String flagValue){
        this.flagValue=flagValue;
    }


    @Transient
    public String getTypeValue(){
        if("company".equals(type)){
            return PROP_TYPE_COMPANY;
        }else if("person".equals(type)){
            return PROP_TYPE_PERSON;
        }

        return "";
    }

    public void setTypeValue(String typeValue){
        this.typeValue=typeValue;
    }

    public String getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(String syncFlag) {
        this.syncFlag = syncFlag;
    }

    public String getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(String syncTime) {
        this.syncTime = syncTime;
    }

    /**
	 * Return the value associated with the column: user
	 */
	public com.jeecms.core.entity.CmsUser getUser () {
		return user;
	}

	/**
	 * Set the value related to the column: user
	 * @param user the user value
	 */
	public void setUser (com.jeecms.core.entity.CmsUser user) {
		this.user = user;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.core.entity.CmsUserExt)) return false;
		else {
			com.jeecms.core.entity.CmsUserExt cmsUserExt = (com.jeecms.core.entity.CmsUserExt) obj;
			if (null == this.getId() || null == cmsUserExt.getId()) return false;
			else return (this.getId().equals(cmsUserExt.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}