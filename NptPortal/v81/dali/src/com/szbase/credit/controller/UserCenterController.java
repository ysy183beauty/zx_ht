package com.szbase.credit.controller;

import com.szbase.credit.entity.base.CreditEntUser;

import com.szbase.credit.rsc.EPSPService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Properties;

/**
 * @ClassName: UserAuthController
 * @Description: TODO
 * @author zhangfeng Szbase
 * @date 2015年11月08日 下午3:14:16
 *
 */
@Controller
@RequestMapping(value = "/User_Center", method = RequestMethod.POST)
public class UserCenterController{

	@Autowired
	private Properties creditResources;

	@Autowired
	private EPSPService epspService;
	
	
	/**
	 * 姓名实时验证
	 * @param request
	 * @param response
	 * @param LOGIN
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/LoginNameVerification")
	public String LoginNameVerification(HttpServletRequest request, HttpServletResponse response,
									String LOGIN) {
        String str = epspService.LoginNameVerification(creditResources.getProperty("LoginName_Realtimeverification_URL"), LOGIN);
        JSONObject json = null;
        try {
            json = new JSONObject(str);
            str="{\"responseData\":"+json.get("body")+"}";
        } catch (JSONException e) {
            e.printStackTrace();
        }

		return str;
	}
	
	/**
	 * 手机号实时校验
	 * @param request
	 * @param response
	 * @param MOBILENUM
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/PhoneNumVerification")
	public String PhoneNumVerification(HttpServletRequest request, HttpServletResponse response,
									String MOBILENUM) {
		
        String str = epspService.PhoneNumVerification(creditResources.getProperty("PhoneNum_Realtimeverification_URL"), MOBILENUM);
        JSONObject json = null;
        try {
            json = new JSONObject(str);
            str="{\"responseData\":"+json.get("body")+"}";
        } catch (JSONException e) {
            e.printStackTrace();
        }

		return str;
	}

	/**
	 * 发送短信
	 * @param request
	 * @param response
	 * @param MOBILENUM,BUSINESSTYPE,SOURCE
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/SendMsg")
	public String SendMsg(HttpServletRequest request, HttpServletResponse response,
									String MOBILENUM,String BUSINESSTYPE,String SOURCE) {
		
        String str = epspService.SendMsg(creditResources.getProperty("SendMsg_URL"),MOBILENUM, BUSINESSTYPE, SOURCE);
		return str;
	}
	
	/**
	 * 短信验证码实时校验
	 * @param request
	 * @param response
	 * @param MOBILENUM,BUSINESSTYPE,VCODE
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/MsgcodeVerification")
	public String MsgcodeVerification(HttpServletRequest request, HttpServletResponse response,
									String MOBILENUM,String BUSINESSTYPE,String VCODE) {
		
        String str = epspService.MsgcodeVerification(creditResources.getProperty("Msgcode_Realtimeverification_URL"),MOBILENUM, BUSINESSTYPE, VCODE);
        JSONObject json = null;
        try {
            json = new JSONObject(str);
            str="{\"responseData\":"+json.get("body")+"}";
        } catch (JSONException e) {
            e.printStackTrace();
        }

		return str;
	}
	
	/**
	 * 点击注册
	 * @param request
	 * @param response
	 * @param LOGIN,PASSWORD,SITEID,MOBILENUM,CODE,PWDSTRENGTH
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/Register")
	public String Register(HttpServletRequest request, HttpServletResponse response,
									String LOGIN,String PASSWORD,String SITEID,String MOBILENUM,String CODE,String PWDSTRENGTH,String EMAIL) {
		
        String str = epspService.Register(creditResources.getProperty("Register_URL"), LOGIN, PASSWORD, SITEID, MOBILENUM, CODE, PWDSTRENGTH,EMAIL);
		return str;
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @param LOGIN,PASSWORD,SITEID,MOBILENUM,CODE,PWDSTRENGTH
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/UserLogin")
	public String UserLogin(HttpServletRequest request, HttpServletResponse response,
									String LOGIN,String PASSWORD) {
       String loginstr = epspService.UserLogin(creditResources.getProperty("UserLogin_URL"), LOGIN, PASSWORD);
       CreditEntUser user = new CreditEntUser();
        JSONObject jsonlogin = null;
        try {
            jsonlogin = new JSONObject(loginstr);
            JSONObject body = (JSONObject) jsonlogin.get("body");
            JSONObject head = (JSONObject) jsonlogin.get("head");
            //登陆成功

            if("000000".equals(head.getString("rtnCode"))){
                //写入明文密码
                user.setPwd(PASSWORD);
                //获取用户基本信息
                String UserDetailstr = epspService.UserDetail(creditResources.getProperty("UserDetail_URL"), LOGIN);
                JSONObject json= new JSONObject(UserDetailstr);
                JSONObject jsonuserdetail = (JSONObject) json.get("body");
                //获取基本信息
                String basestr = epspService.GetUserBasicInfo(creditResources.getProperty("GetUserBasicInfo_URL"),jsonuserdetail.getString("userid"));
                JSONObject json2= new JSONObject(basestr);
                JSONObject jsonbasedetail = (JSONObject) json2.get("body");
                user.setUservalues(jsonuserdetail,body,jsonbasedetail);
                //获取企业信息
                String qystr = epspService.getUserEntStatus(creditResources.getProperty("getUserEntStatus_URL"), LOGIN, PASSWORD, "2");
                System.out.println("企业登陆："+qystr);
                JSONObject qyjson = new JSONObject(qystr);
                user.setExist(qyjson.getString("exist"));
                if("000000".equals(qyjson.getString("exist"))){
                    JSONArray qydetail = qyjson.getJSONArray("entList").getJSONArray(0);
                    String ENT_ID = qydetail.get(0).toString();
                    //获取企业认证信息
                    String EntInfo = epspService.selectEntInfo(creditResources.getProperty("selectEntInfo_URL"), ENT_ID);
                    JSONObject qyjson2 = 	new JSONObject(EntInfo);
                    if("000000".equals(qyjson2.getString("exist"))){
                        JSONArray EntInfoJson  = qyjson2.getJSONArray("entList").getJSONArray(0);
                        System.out.println(EntInfo);
                        user.setEntvalues(EntInfoJson);
                    }

                }else{
                    user.setEntvalues(null);
                }
                //存放到jeecms session中
                request.getSession().setAttribute("rdp_user", user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

		return loginstr;
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/loginout")
	public void loginout(HttpServletRequest request, HttpServletResponse response) {		
        	request.getSession().removeAttribute("rdp_user");	
	}
	
	/**
	 * 个人中心信息查询
	 * @param request
	 * @param response
	 * @param LOGIN
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/UserDetail")
	public String UserDetail(HttpServletRequest request, HttpServletResponse response,
									String LOGIN) {		
        String str = epspService.UserDetail(creditResources.getProperty("UserDetail_URL"), LOGIN);
		return str;
	}
	
	/**
	 * 用户名实时校验(找回密码)
	 * @param request
	 * @param response
	 * @param LOGIN,PASSWORD,SITEID,MOBILENUM,CODE,PWDSTRENGTH
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/UserNameverification")
	public String UserNameVerification(HttpServletRequest request, HttpServletResponse response,
									String LOGIN) {		
        String str = epspService.UserNameVerification(creditResources.getProperty("UserName_Realtimeverification_URL"), LOGIN);
		return str;
	}
	
	/**
	 * 重置密码(找回密码)
	 * @param request
	 * @param response
	 * @param LOGIN,PASSWORD,SITEID,MOBILENUM,CODE,PWDSTRENGTH
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/Resetpassword")
	public String Resetpassword(HttpServletRequest request, HttpServletResponse response,
			String LOGIN,String PASSWORD,String CODE,String FINDTYPE,String PWDSTRENGTH) {		
        String str = epspService.Resetpassword(creditResources.getProperty("Resetpassword_URL"), LOGIN,PASSWORD,CODE,FINDTYPE,PWDSTRENGTH);
		return str;
	}
	/**
	 * 修改绑定手机
	 * @param request
	 * @param response
	 * @param MOBILENUM,CODE,accessTicket
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/ChangePhone")
	public String ChangePhone(HttpServletRequest request, HttpServletResponse response,
			String MOBILENUM,String CODE,String accessTicket) {
        String str = epspService.ChangePhone(creditResources.getProperty("ChangePhone_URL"), MOBILENUM,CODE,accessTicket);
		return str;
	}
}
