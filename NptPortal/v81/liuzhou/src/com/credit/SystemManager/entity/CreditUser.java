package com.credit.SystemManager.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jiaoss
 * @date 2017年3月2日16:30:38
 * 备注：
 *      用户基础模型信息
 */
@Entity
@Table(name = "MH_USER")
public class CreditUser implements Serializable {

    private Long id;
    private String phone;
    private String userName;
    private String loginName;
    private String passWord;
    private String email;
    private String type;  //用户类型：个人、公司
    private String flag;  //状态：1.没有认证，2.审核中，3.认证成功，4.禁用
    private String idCard; //身份证
    private String openId;			//微信号
    private String nickName;		//微信昵称
    private String sex;	            //微信性别
    private String language;		//微信语言
    private String city	;	        //微信城市
    private String province;		//微信省份
    private String country;			//微信国家
    private String idCardPath;    //身份证照片路径
    private String registerTime;    //注册时间
    private String modifyTime;     //上次修改时间
    private String certificationTime;   //认证时间
    private String registerWay;     //注册方式：1.web,2.微信
    private String certificationMsg;   //认证时间
    private String backFlag; //备份认证状态

    public CreditUser(){

    }

    //web端用户注册
   public CreditUser(String loginName, String passWord, String email){
       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.loginName=loginName;
        this.passWord=passWord;
        this.email=email;
        this.flag="1";//未实名认证
        this.registerWay="1";//注册方式
        this.registerTime=formatter.format(new Date());
    }

    //微信端用户注册
    public CreditUser(String openId, String nickName, String sex, String language, String city,
                      String province, String country){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.openId=openId;
        this.nickName=nickName;
        this.sex=sex;
        this.language=language;
        this.city=city;
        this.province=province;
        this.country=country;
        this.registerTime=formatter.format(new Date());
        this.flag="1";//未实名认证
        this.registerWay="2";//注册方式
    }

    public void addAuthentificationInfo(String userName, String idCard, String phone,String type){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.userName=userName;
        this.idCard=idCard;
        this.phone=phone;
        this.type=type;
        this.flag="2";
        this.modifyTime=formatter.format(new Date());

    }


    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="S_USER")
    @SequenceGenerator(name="S_USER", sequenceName="S_MH_USER")
    @Column(name="ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @Column(name="ID_CARD",length = 50)
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }


    @Column(name="USER_NAME",length = 200)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name="PHONE",length = 11)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name="LOGIN_NAME",length = 200)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Column(name="PASS_WORD",length = 200)
    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Column(name="EMAIL",length = 200)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name="TYPE",length = 11)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name="FLAG",length = 2)
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Column(name="OPEN_ID",length = 200)
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Column(name="NICK_NAME",length = 200)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Column(name="SEX",length = 2)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name="LANGUAGE",length = 80)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Column(name="CITY",length = 100)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name="PROVINCE",length = 100)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name="COUNTRY",length = 100)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name="REGISTER_WAY",length = 1)
    public String getRegisterWay() {
        return registerWay;
    }

    public void setRegisterWay(String registerWay) {
        this.registerWay = registerWay;
    }

    @Column(name="ID_CARD_PATH",length = 1)

    public String getIdCardPath() {
        return idCardPath;
    }

    public void setIdCardPath(String idCardPath) {
        this.idCardPath = idCardPath;
    }

    @Column(name="REGISTER_TIME")
    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    @Column(name="MODIFY_TIME")

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Column(name="CERTIFICATION_TIME")
    public String getCertificationTime() {
        return certificationTime;
    }

    public void setCertificationTime(String certificationTime) {
        this.certificationTime = certificationTime;
    }

    @Column(name="CERTIFICATION_MSG")
    public String getCertificationMsg() {
        return certificationMsg;
    }

    public void setCertificationMsg(String certificationMsg) {
        this.certificationMsg = certificationMsg;
    }

    @Column(name="BACK_FLAG",length = 2)
    public String getBackFlag() {
        return backFlag;
    }

    public void setBackFlag(String backFlag) {
        this.backFlag = backFlag;
    }
    //formula里面的参数是必须要有 () 小括号的  里面的参数是sql语句、 而你要动态传参可以使用当前实体类的某个属性、比如下面teacherId  就是User类里面的属性、
    //@Formula("( select t.name from teacherTable as t where  t.id = teacherId  )")
    //private String teacherName;//这个字段在数据库中是不存在的
}
