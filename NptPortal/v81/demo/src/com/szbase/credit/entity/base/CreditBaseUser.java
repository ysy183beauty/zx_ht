/**
 * @Title: CreditBaseUser.java
 * @Package com.szbase.credit.entity.base
 * @Description: TODO
 * @author Owen Szbase
 * @date 2015年7月16日 下午8:19:07
 * @version V1.0
 */
package com.szbase.credit.entity.base;

import com.jeecms.core.entity.UnifiedUser;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @ClassName: CreditBaseUser
 * @Description: TODO
 * @author Owen Szbase
 * @date 2015年7月16日 下午8:19:07
 *
 */
public class CreditBaseUser extends UnifiedUser implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -6551789110047481257L;

	private String user_id;

	private String login;
	
	private String pwd;

	private String password;

	private String mobilenum;

	private String mobileisbound;

	private String email;

	private String emailisbound;

	private String idcardcode;

	private String spassword;

	private String updatetime;

	private String flag;

	private String cardnum;

	private String pwdstrength;

	private String accessTicket;

	private String name;

	private String level;

	private String headphotourl;

	private String state;

	private String siteid;

	private String sex;

	private String birthday;

	private String channelid;

	private String marry;

	private String registertime;

	private String modifytime;

	private String lastlogintime;

	private String isfirstlogin;
	
	private String exist ;
	
	

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getExist() {
		return exist;
	}

	public void setExist(String exist) {
		this.exist = exist;
	}

	public String getIsfirstlogin() {
		return isfirstlogin;
	}

	public void setIsfirstlogin(String isfirstlogin) {
		this.isfirstlogin = isfirstlogin;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getHeadphotourl() {
		return headphotourl;
	}

	public void setHeadphotourl(String headphotourl) {
		this.headphotourl = headphotourl;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getMarry() {
		return marry;
	}

	public void setMarry(String marry) {
		this.marry = marry;
	}

	public String getRegistertime() {
		return registertime;
	}

	public void setRegistertime(String registertime) {
		this.registertime = registertime;
	}

	public String getModifytime() {
		return modifytime;
	}

	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}

	public String getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(String lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public String getAccessTicket() {
		return accessTicket;
	}

	public void setAccessTicket(String accessTicket) {
		this.accessTicket = accessTicket;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String userid) {
		this.user_id = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobilenum() {
		return mobilenum;
	}

	public void setMobilenum(String mobilenum) {
		this.mobilenum = mobilenum;
	}

	public String getMobileisbound() {
		return mobileisbound;
	}

	public void setMobileisbound(String mobileisbound) {
		this.mobileisbound = mobileisbound;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailisbound() {
		return emailisbound;
	}

	public void setEmailisbound(String emailisbound) {
		this.emailisbound = emailisbound;
	}

	public String getIdcardcode() {
		return idcardcode;
	}

	public void setIdcardcode(String idcardcode) {
		this.idcardcode = idcardcode;
	}

	public String getSpassword() {
		return spassword;
	}

	public void setSpassword(String spassword) {
		this.spassword = spassword;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

	public String getPwdstrength() {
		return pwdstrength;
	}

	public void setPwdstrength(String pwdstrength) {
		this.pwdstrength = pwdstrength;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}


	

	public void setUservalues(JSONObject json, JSONObject body,
			JSONObject json2) {
        try {
            if (!json.get("userid").equals(null))
                this.setUser_id(json.getString("userid"));
            else
                this.setUser_id("");
            if (!json.get("login").equals(null))
                this.setLogin(json.getString("login"));
            else
                this.setLogin("");
            if (!json.get("password").equals(null))
                this.setPassword(json.getString("password"));
            else
                this.setPassword("");
            if (!json.get("mobilenum").equals(null))
                this.setMobilenum(json.getString("mobilenum"));
            else
                this.setMobilenum("");
            if (!json.get("mobileisbound").equals(null))
                this.setMobileisbound(json.getString("mobileisbound"));
            else
                this.setMobileisbound("");
            if (!json.get("email").equals(null))
                this.setEmail(json.getString("email"));
            else
                this.setEmail("");
            if (!json.get("emailisbound").equals(null))
                this.setEmailisbound(json.getString("emailisbound"));
            else
                this.setEmailisbound("");
            if (!json.get("idcardcode").equals(null))
                this.setIdcardcode(json.getString("idcardcode"));
            else
                this.setIdcardcode("");
            if (!json.get("spassword").equals(null))
                this.setSpassword(json.getString("spassword"));
            else
                this.setSpassword("");
            if (!json.get("updatetime").equals(null))
                this.setUpdatetime(json.get("updatetime").toString());
            else
                this.setUpdatetime("");
            if (!json.get("flag").equals(null))
                this.setFlag(json.getString("flag"));
            else
                this.setFlag("");
            if (!json.get("cardnum").equals(null))
                this.setCardnum(json.getString("cardnum"));
            else
                this.setCardnum("");
            if (!json.get("pwdstrength").equals(null))
                this.setPwdstrength(json.getString("pwdstrength"));
            else
                this.setPwdstrength("");
            if(body!=null){
                if (!body.get("accessTicket").equals(null))
                    this.setAccessTicket(body.getString("accessTicket"));
                else
                    this.setAccessTicket("");
            }
            if (!json2.get("name").equals(null))
                this.setName(json2.getString("name"));
            else
                this.setName("");
            if (!json2.get("level").equals(null))
                this.setLevel(json2.getString("level"));
            else
                this.setLevel("");
            if (!json2.get("headphotourl").equals(null))
                this.setHeadphotourl(json2.getString("headphotourl"));
            else
                this.setHeadphotourl("");
            if (!json2.get("state").equals(null))
                this.setState(json2.getString("state"));
            else
                this.setState("");
            if (!json2.get("siteid").equals(null))
                this.setSiteid(json2.getString("siteid"));
            else
                this.setSiteid("");
            if (!json2.get("channelid").equals(null))
                this.setChannelid(json2.getString("channelid"));
            else
                this.setChannelid("");
            if (!json2.get("sex").equals(null))
                this.setSex(json2.getString("sex"));
            else
                this.setSex("");
            if (!json2.get("birthday").equals(null))
                this.setBirthday(json2.get("birthday").toString());
            else
                this.setBirthday("");
            if (!json2.get("marry").equals(null))
                this.setMarry(json2.getString("marry"));
            else
                this.setMarry("");
            if (!json2.get("registertime").equals(null))
                this.setRegistertime(json2.get("registertime").toString());
            else
                this.setRegistertime("");
            if (!json2.get("modifytime").equals(null))
                this.setModifytime(json2.get("modifytime").toString());
            else
                this.setModifytime("");
            if (!json2.get("lastlogintime").equals(null))
                this.setLastlogintime(json2.get("lastlogintime").toString());
            else
                this.setLastlogintime("");
            if (!json2.get("isfirstlogin").equals(null))
                this.setIsfirstlogin(json2.get("isfirstlogin").toString());
            else
                this.setIsfirstlogin("");
        } catch (JSONException e) {
            e.printStackTrace();
        }
	}

}
