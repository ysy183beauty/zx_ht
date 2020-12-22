package com.szbase.credit.controller;

import com.szbase.credit.entity.base.CreditEntUser;
import com.szbase.credit.rsc.EPSPService;
import com.szbase.credit.util.Base64Code;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * @ClassName: UserAuthController
 * @Description: TODO
 * @author zhangfeng Szbase
 * @date 2015年11月08日 下午3:14:16
 *
 */
@Controller
@RequestMapping(value = "/User_RNAuth" ,method = RequestMethod.POST)
public class UserRealnameAuthController{

	@Autowired
	private Properties creditResources;

	@Autowired
	private EPSPService epspService;

	/**
	 * 验证虞城通号是否已实名认证 
	 * @param request
	 * @param response
	 * @param CARDNUM
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/YctNumverification")
	public String YctNumverification(HttpServletRequest request, HttpServletResponse response,
									String CARDNUM) {
        String str = epspService.YctNumverification(creditResources.getProperty("YctNum_verification_URL"), CARDNUM);
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
	 * 根据虞城通号获取用户信息
	 * @param request
	 * @param response
	 * @param CARDNUM
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/GetUserDetailByYct")
	public String GetUserDetailByYct(HttpServletRequest request, HttpServletResponse response,
									String CARDNUM ,String accessTicket) {
        String str = epspService.GetUserDetailByYct(creditResources.getProperty("UserDetailByYct_URL"), CARDNUM,accessTicket);
		return str;
	}
	
	/**
	 * 校验身份证号是否已实名认证
	 * @param request
	 * @param response
	 * @param IDCODE
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/SFZHMRealtimeverification")
	public String SFZHMRealtimeverification(HttpServletRequest request, HttpServletResponse response,
									String IDCODE) {
        String str = epspService.SFZHMRealtimeverification(creditResources.getProperty("SFZHM_Realtimeverification_URL"), IDCODE);
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
	 * 新增身份证实名认证信息
	 * @param request
	 * @param response
	 * @param relname
	 * @return str
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="/SFZHMauthenticationInfo")
	public String SFZHMauthenticationInfo(HttpServletRequest request, HttpServletResponse response,
			String relname,String sfznum,String FPICTURE,String BPICTURE,String phonenum,String dxcode,String accessTicket) throws IOException {
//		if (relname != null) {
//			relname = new String(relname.getBytes("iso8859-1"),"UTF-8");
//			request.setAttribute("relname", relname);
//		}
//		if(!FPICTURE.isEmpty()&&!BPICTURE.isEmpty()){
//		String fpic = Base64Code.Encode(FPICTURE.getBytes());
//		String bpic = Base64Code.Encode(BPICTURE.getBytes());	
//        String str = epspService.SFZHMauthenticationInfo(creditResources.getProperty("SFZHMauthenticationInfo_URL"), relname, sfznum, fpic, bpic, phonenum, dxcode,accessTicket);
//		return str;
//		}
//		System.out.println(FPICTURE.getOriginalFilename());
//		System.out.println(BPICTURE.getOriginalFilename());
	
		 String str = epspService.SFZHMauthenticationInfo(creditResources.getProperty("SFZHMauthenticationInfo_URL"), relname, sfznum, FPICTURE, BPICTURE, phonenum, dxcode,accessTicket);
			return str;


	}
	
	/**
	 *新增市民卡认证信息
	 * @param request
	 * @param response
	 * @param CARDNUM
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/CitizencardauthenticationInfo")
	public String CitizencardauthenticationInfo(HttpServletRequest request, HttpServletResponse response,
			String CARDNUM,String NAME,String IDCARDCODE,String MOBILENUM,String CODE ,String accessTicket) {
        String str = epspService.CitizencardauthenticationInfo(creditResources.getProperty("CitizencardauthenticationInfo_URL"), CARDNUM,NAME,IDCARDCODE,MOBILENUM,CODE,accessTicket);
		return str;
	}
	
	/**
	 *获取用户基本信息
	 * @param request
	 * @param response
	 * @param USERID
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/GetUserBasicInfo")
	public String GetUserBasicInfo(HttpServletRequest request, HttpServletResponse response,
			String USERID) {
        String str = epspService.GetUserBasicInfo(creditResources.getProperty("GetUserBasicInfo_URL"),USERID);
		return str;
	}
	
	/**
	 *基本信息保存
	 * @param request
	 * @param response
	 * @param LOGIN
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/UserInfoSave")
	public String UserInfoSave(HttpServletRequest request, HttpServletResponse response,
			String LOGIN,String NAME,String SEX,String BIRTHDAY,String MARRY,String RESIDENCE) {
        String str = epspService.UserInfoSave(creditResources.getProperty("UserInfoSave_URL"), LOGIN, NAME, SEX, BIRTHDAY, MARRY, RESIDENCE);
		return str;
	}
	
	/**
	 *修改密码
	 * @param request
	 * @param response
	 * @param OLDPWD
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/Changepassword")
	public String Changepassword(HttpServletRequest request, HttpServletResponse response,
			String PWDSTRENGTH,String OLDPWD,String NEWPWD,String accessTicket) {
		CreditEntUser user = (CreditEntUser) request.getSession().getAttribute("rdp_user");
		String loginstr = epspService.UserLogin(creditResources.getProperty("UserLogin_URL"), user.getLogin(), OLDPWD);
        JSONObject jsonlogin = null;
        try {
            jsonlogin = new JSONObject(loginstr);
            JSONObject head = (JSONObject) jsonlogin.get("head");
            //登陆失败
            if(!"000000".equals(head.getString("rtnCode"))){
                return  "{\"head\":{\"rtnMsg\":\"您输入的用户密码错误!\",\"rtnCode\":\"999999\"},\"body\":\"\"}";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String str = epspService.Changepassword(creditResources.getProperty("Changepassword_URL"), PWDSTRENGTH, OLDPWD, NEWPWD,accessTicket);
		return str;
	}
	
	/**
	 *上传图片 
	 * @param request
	 * @param response
	 * @param Filedata
	 * @return str
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="/UploadImage")
	public void UploadImage(HttpServletRequest request, HttpServletResponse response,
			MultipartFile Filedata,String accessTicket) throws IOException {
		System.out.println("图片大小："+Filedata.getSize());
		System.out.println("图片原名："+Filedata.getOriginalFilename());
		String str = epspService.UploadImage(creditResources.getProperty("UploadImage_URL"),  Base64Code.Encode(Filedata.getBytes()) ,accessTicket);
        JSONObject json = null;
        try {
            json = new JSONObject(str);
            String fileURL = json.getJSONObject("body").getString("fileURL");
            String fileName = json.getJSONObject("body").getString("fileName");
            System.out.println("图片路径："+fileURL);
            System.out.println("图片名称："+fileName);
            String serverData = "{\"rtnCode\":\""+json.getJSONObject("head").getString("rtnCode")+"\",\"fileURL\":\""+fileURL+"\",\"fileName\":\""+fileName+"\",\"originalFileName\":\""+Filedata.getOriginalFilename()+"\",\"fileSize\":"+Filedata.getSize()+"}";
            System.out.println(serverData);
            response.setContentType("text/html; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter out = response.getWriter();
            out.write(serverData);

            out.flush();
            out.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }


	}
}
