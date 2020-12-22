/**
* @Title: ECSPService.java
* @Package com.szbase.credit.rsc
* @Description: TODO
* @author Owen
* @date 2015年7月15日 下午2:53:59
* @version V1.0
*/
package com.szbase.credit.rsc;

/**
 * @ClassName: ECSPService
 * @Description: TODO
 * 	公共服务平台
 * @author zhangfeng
 * @date 2015年7月15日 下午2:53:59
 *
 */
public interface EPSPService {

	
	/**
	* @Title: realameAuthentication
	* @Description:
	* 	实名认证接口
	* 
	* 	！！！！！！待确认！！！！！！
	* 
	* @return
	* @throws
	* @Date:2015年7月15日 下午4:15:44
	*/
	/**
	 * 用户名实时验证
	 */
	public String LoginNameVerification(String detailUrl,String LOGIN);
	/**
	 * 手机号实时验证
	 */
	public String PhoneNumVerification(String detailUrl,String MOBILENUM);
	/**
	 * 发送短信
	 */
	public String SendMsg(String detailUrl,String MOBILENUM,String BUSINESSTYPE,String SOURCE);
	/**
	 * 短信验证码实时校验
	 */
	public String MsgcodeVerification(String detailUrl,String MOBILENUM,String BUSINESSTYPE,String VCODE);
	/**
	 * 点击注册
	 */
	public String Register(String detailUrl,String LOGIN,String PASSWORD,String SITEID,String MOBILENUM,String CODE,String PWDSTRENGTH,String EMAIL);
	/**
	 * 用户登录
	 */
	public String UserLogin(String detailUrl,String LOGIN,String PASSWORD);
	/**
	 * 个人中心信息查询
	 */
	public String UserDetail(String detailUrl,String LOGIN);
	/**
	 * 用户名实时校验
	 */
	public String UserNameVerification(String detailUrl,String LOGIN);
	/**
	 * 重置密码
	 */
	public String Resetpassword(String detailUrl,String LOGIN,String PASSWORD,String CODE,String FINDTYPE,String PWDSTRENGTH);
	/**
	 *验证虞城通号是否存在
	 */
	public String YctNumverification(String detailUrl,String CARDNUM);
	/**
	 *根据虞城通号获取用户信息
	 */
	public String GetUserDetailByYct(String detailUrl,String CARDNUM,String accessTicket);
	/**
	 *根据虞城通号获取用户信息
	 */
	public String SFZHMRealtimeverification(String detailUrl,String IDCODE);

	/**
	 *新增市民卡认证信息
	 */
	public String CitizencardauthenticationInfo(String detailUrl,String CARDNUM,String NAME,String IDCARDCODE,String MOBILENUM,String CODE ,String accessTicket);
	/**
	 *新增身份证实名认证信息
	 */
	public String SFZHMauthenticationInfo(String detailUrl,String NAME,String IDCARDCODE,String FPICTURE,String BPICTURE,String MOBILENUM,String CODE,String accessTicket);
	/**
	 *获取用户基本信息
	 */
	public String GetUserBasicInfo(String detailUrl,String USERID);
	/**
	 *基本信息保存
	 */
	public String UserInfoSave(String detailUrl,String LOGIN,String NAME,String SEX,String BIRTHDAY,String MARRY,String RESIDENCE);
	/**
	 *修改密码
	 */
	public String Changepassword(String detailUrl,String PWDSTRENGTH,String OLDPWD,String NEWPWD,String accessTicket);
	/**
	 * --------------------------------企业接口开始--------------------------------------------------
	 *企业名称检测 
	 */
	public String entNameCheck(String detailUrl,String QYMC);
	/**
	 *企业机构代码检测
	 */
	public String entNumCheck(String detailUrl,String ZZJGDM);
	/**
	 *法人姓名检测
	 */
	public String PeoNameCheck(String detailUrl,String FRDBXM);
	/**
	 *法人身份证号检测
	 */
	public String PeoNumCheck(String detailUrl,String FRDBZJH);
	/**
	 *营业执照号检测
	 */
	public String businessNumCheck(String detailUrl,String YYZZH);
	/**
	 *企业登陆接口
	 */
	public String getUserEntStatus(String detailUrl,String loginName ,String password ,String flag);
	/**
	 *企业实名认证接口
	 */
	public String entApprove(String detailUrl,String userName ,String entName ,String organizationCode ,String entType,String startTime
			,String endTime ,String phone ,String email ,String img1 ,String img2,String img3,String img5
			,String img6 ,String corporationName ,String corporationId ,String YYZZH);	
	/**
	 *企业名称查询企业信息
	 */
	public String entNameSelect(String detailUrl,String QYMC);
	/**
	 *企业组织机构代码查询企业信息
	 */
	public String entNumSelect(String detailUrl,String ZZJGDM);
	/**
	 *查询企业认证信息
	 */
	public String selectEntInfo(String detailUrl,String ENT_ID);
	/**
	 *修改绑定手机
	 */
	public String ChangePhone(String detailUrl , String MOBILENUM,String CODE,String accessTicket);
	/**
	 *上传图片
	 */
	public String UploadImage(String detailUrl , String IMGSTR ,String accessTicket);
	/**
	 *上传图片企业
	 */
	public String UploadImage_ent(String detailUrl ,String imgStr ,String imgType );
}
