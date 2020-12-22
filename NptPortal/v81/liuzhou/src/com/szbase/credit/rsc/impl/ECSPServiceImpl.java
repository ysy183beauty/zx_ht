/**
* @Title: ECSPServiceImpl.java
* @Package com.szbase.credit.rsc.impl
* @Description: TODO
* @author Owen Szbase
* @date 2015年7月15日 下午2:54:34
* @version V1.0
*/
package com.szbase.credit.rsc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digitalchina.test.util.DataUtil;
import com.digitalchina.test.util.SHA1;
import com.google.gson.Gson;
import com.szbase.credit.rsc.EPSPService;
import com.szbase.credit.util.HttpClientJson;
import com.szbase.credit.util.HttpClientJsonImage;
import com.szbase.credit.util.HttpClientUtil;

/**
 * @ClassName: ECSPServiceImpl
 * @Description: TODO
 * @author zhangfeng Szbase
 * @date 2015年7月15日 下午2:54:34
 *
 */
@Service
public class ECSPServiceImpl implements EPSPService {
		
	@Value("#{creditResources['appid']}")
	private String appid;
	@Value("#{creditResources['appkey']}")
	private String appkey;
	@Value("#{creditResources['version']}")
	private String version;
	@Override
	public String LoginNameVerification(String detailUrl, String LOGIN) {
		 Map<String, Object> head = new HashMap<String, Object>();
		 Map<String, Object> body = new HashMap<String, Object>();		 
		 //写入body字段
		 body.put("LOGIN", LOGIN);		
		//写入head字段
		 head.put("appid", appid);
		 try {
		 head.put("sign", DataUtil.signRequest(appid,body,appkey));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 head.put("version", version);
		 //将body和head一起放入req
		 final HashMap<String, Object> req = new HashMap<String, Object>();
		 req.put("head", head);
		 req.put("body", body);	
		 //将req集合转换成json格式发生post请求
		 final Gson gson = new Gson();
		 final String str = gson.toJson(req);
		return  HttpClientJson.readInterfacePost(detailUrl, str);
	}
	@Override
	public String PhoneNumVerification(String detailUrl, String MOBILENUM) {
		 Map<String, Object> head = new HashMap<String, Object>();
		 Map<String, Object> body = new HashMap<String, Object>();		 
		 //写入body字段
		 body.put("MOBILENUM", MOBILENUM);		
		//写入head字段
		 head.put("appid", appid);
		 try {
		 head.put("sign", DataUtil.signRequest(appid,body,appkey));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 head.put("version", version);
		 //将body和head一起放入req
		 final HashMap<String, Object> req = new HashMap<String, Object>();
		 req.put("head", head);
		 req.put("body", body);	
		 //将req集合转换成json格式发生post请求
		 final Gson gson = new Gson();
		 final String str = gson.toJson(req);
		return  HttpClientJson.readInterfacePost(detailUrl, str);
	}
	@Override
	public String SendMsg(String detailUrl, String MOBILENUM,
			String BUSINESSTYPE, String SOURCE) {
		 Map<String, Object> head = new HashMap<String, Object>();
		 Map<String, Object> body = new HashMap<String, Object>();		 
		 //写入body字段
		 body.put("MOBILENUM", MOBILENUM);	
		 body.put("BUSINESSTYPE", BUSINESSTYPE);
		 body.put("SOURCE", SOURCE);
		//写入head字段
		 head.put("appid", appid);
		 try {
		 head.put("sign", DataUtil.signRequest(appid,body,appkey));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 head.put("version", version);
		 //将body和head一起放入req
		 final HashMap<String, Object> req = new HashMap<String, Object>();
		 req.put("head", head);
		 req.put("body", body);	
		 //将req集合转换成json格式发生post请求
		 final Gson gson = new Gson();
		 final String str = gson.toJson(req);
		return  HttpClientJson.readInterfacePost(detailUrl, str);
	}
	@Override
	public String MsgcodeVerification(String detailUrl, String MOBILENUM,
			String BUSINESSTYPE, String VCODE) {
		 Map<String, Object> head = new HashMap<String, Object>();
		 Map<String, Object> body = new HashMap<String, Object>();		 
		 //写入body字段
		 body.put("MOBILENUM", MOBILENUM);	
		 body.put("BUSINESSTYPE", BUSINESSTYPE);
		 body.put("VCODE", VCODE);
		//写入head字段
		 head.put("appid", appid);
		 try {
		 head.put("sign", DataUtil.signRequest(appid,body,appkey));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 head.put("version", version);
		 //将body和head一起放入req
		 final HashMap<String, Object> req = new HashMap<String, Object>();
		 req.put("head", head);
		 req.put("body", body);	
		 //将req集合转换成json格式发生post请求
		 final Gson gson = new Gson();
		 final String str = gson.toJson(req);
		return  HttpClientJson.readInterfacePost(detailUrl, str);
	}
	@Override
	public String Register(String detailUrl, String LOGIN, String PASSWORD,
			String SITEID, String MOBILENUM, String CODE, String PWDSTRENGTH,String EMAIL) {
		 Map<String, Object> head = new HashMap<String, Object>();
		 Map<String, Object> body = new HashMap<String, Object>();	
		 SHA1 sha1=new SHA1();
		 String password=sha1.getDigestOfString(PASSWORD.getBytes());
		 //写入body字段
		 body.put("LOGIN", LOGIN);	
		 body.put("PASSWORD", password);
		 body.put("SITEID", SITEID);
		 body.put("MOBILENUM", MOBILENUM);
		 body.put("CODE", CODE);
		 body.put("EMAIL", EMAIL);
		 body.put("PWDSTRENGTH", PWDSTRENGTH);
		//写入head字段
		 head.put("appid", appid);
		 try {
		 head.put("sign", DataUtil.signRequest(appid,body,appkey));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 head.put("version", version);
		 //将body和head一起放入req
		 final HashMap<String, Object> req = new HashMap<String, Object>();
		 req.put("head", head);
		 req.put("body", body);	
		 //将req集合转换成json格式发生post请求
		 final Gson gson = new Gson();
		 final String str = gson.toJson(req);
		return  HttpClientJson.readInterfacePost(detailUrl, str);
	}
	@Override
	public String UserLogin(String detailUrl, String LOGIN, String PASSWORD) {
		 Map<String, Object> head = new HashMap<String, Object>();
		 Map<String, Object> body = new HashMap<String, Object>();		 
		 //写入body字段
		 body.put("username", LOGIN);	
		 SHA1 sha1=new SHA1();
		 String password=sha1.getDigestOfString(PASSWORD.getBytes());
		 body.put("password", password);
		//写入head字段
		 head.put("appid", appid);
		 try {
		 head.put("sign", DataUtil.signRequest(appid,body,appkey));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 head.put("version", version);
		 //将body和head一起放入req
		 final HashMap<String, Object> req = new HashMap<String, Object>();
		 req.put("head", head);
		 req.put("body", body);	
		 //将req集合转换成json格式发生post请求
		 final Gson gson = new Gson();
		 final String str = gson.toJson(req);
		return  HttpClientJson.readInterfacePost(detailUrl, str);
	}
	@Override
	public String UserDetail(String detailUrl, String LOGIN) {
		 Map<String, Object> head = new HashMap<String, Object>();
		 Map<String, Object> body = new HashMap<String, Object>();		 
		 //写入body字段
		 body.put("LOGIN", LOGIN);			
		//写入head字段
		 head.put("appid", appid);
		 try {
		 head.put("sign", DataUtil.signRequest(appid,body,appkey));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 head.put("version", version);
		 //将body和head一起放入req
		 final HashMap<String, Object> req = new HashMap<String, Object>();
		 req.put("head", head);
		 req.put("body", body);	
		 //将req集合转换成json格式发生post请求
		 final Gson gson = new Gson();
		 final String str = gson.toJson(req);
		return  HttpClientJson.readInterfacePost(detailUrl, str);
	}
	@Override
	public String UserNameVerification(String detailUrl, String LOGIN) {
		 Map<String, Object> head = new HashMap<String, Object>();
		 Map<String, Object> body = new HashMap<String, Object>();		 
		 //写入body字段
		 body.put("LOGIN", LOGIN);			
		//写入head字段
		 head.put("appid", appid);
		 try {
		 head.put("sign", DataUtil.signRequest(appid,body,appkey));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 head.put("version", version);
		 //将body和head一起放入req
		 final HashMap<String, Object> req = new HashMap<String, Object>();
		 req.put("head", head);
		 req.put("body", body);	
		 //将req集合转换成json格式发生post请求
		 final Gson gson = new Gson();
		 final String str = gson.toJson(req);
		return  HttpClientJson.readInterfacePost(detailUrl, str);
	}
	@Override
	public String Resetpassword(String detailUrl,String LOGIN,String PASSWORD,String CODE,String FINDTYPE,String PWDSTRENGTH) {
		 Map<String, Object> head = new HashMap<String, Object>();
		 Map<String, Object> body = new HashMap<String, Object>();		 
		 //写入body字段
		 body.put("LOGIN", LOGIN);	
		 SHA1 sha1=new SHA1();
		 String password=sha1.getDigestOfString(PASSWORD.getBytes());
		 body.put("PASSWORD", password);
		 body.put("CODE", CODE);
		 body.put("FINDTYPE", FINDTYPE);
		 body.put("PWDSTRENGTH", PWDSTRENGTH);
		//写入head字段
		 head.put("appid", appid);
		 try {
		 head.put("sign", DataUtil.signRequest(appid,body,appkey));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 head.put("version", version);
		 //将body和head一起放入req
		 final HashMap<String, Object> req = new HashMap<String, Object>();
		 req.put("head", head);
		 req.put("body", body);	
		 //将req集合转换成json格式发生post请求
		 final Gson gson = new Gson();
		 final String str = gson.toJson(req);
		return  HttpClientJson.readInterfacePost(detailUrl, str);
	}
	@Override
	public String YctNumverification(String detailUrl, String CARDNUM) {
		 Map<String, Object> head = new HashMap<String, Object>();
		 Map<String, Object> body = new HashMap<String, Object>();		 
		 //写入body字段
		 body.put("CARDNUM", CARDNUM);	
		//写入head字段
		 head.put("appid", appid);
		 try {
		 head.put("sign", DataUtil.signRequest(appid,body,appkey));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 head.put("version", version);
		 //将body和head一起放入req
		 final HashMap<String, Object> req = new HashMap<String, Object>();
		 req.put("head", head);
		 req.put("body", body);	
		 //将req集合转换成json格式发生post请求
		 final Gson gson = new Gson();
		 final String str = gson.toJson(req);
		return  HttpClientJson.readInterfacePost(detailUrl, str);
	}
	@Override
	public String GetUserDetailByYct(String detailUrl, String CARDNUM,String accessTicket) {
		 Map<String, Object> head = new HashMap<String, Object>();
		 Map<String, Object> body = new HashMap<String, Object>();		 
		 //写入body字段
		 body.put("CARDNUM", CARDNUM);	
		//写入head字段
		 head.put("appid", appid);
		 head.put("accessTicket", accessTicket);
		 try {
		 head.put("sign", DataUtil.signRequest(appid,body,appkey));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 head.put("version", version);
		 //将body和head一起放入req
		 final HashMap<String, Object> req = new HashMap<String, Object>();
		 req.put("head", head);
		 req.put("body", body);	
		 //将req集合转换成json格式发生post请求
		 final Gson gson = new Gson();
		 final String str = gson.toJson(req);
		return  HttpClientJson.readInterfacePost(detailUrl, str);
	}
	@Override
	public String SFZHMRealtimeverification(String detailUrl, String IDCODE) {
		 Map<String, Object> head = new HashMap<String, Object>();
		 Map<String, Object> body = new HashMap<String, Object>();		 
		 //写入body字段
		 body.put("IDCODE", IDCODE);	
		//写入head字段
		 head.put("appid", appid);
		 try {
		 head.put("sign", DataUtil.signRequest(appid,body,appkey));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 head.put("version", version);
		 //将body和head一起放入req
		 final HashMap<String, Object> req = new HashMap<String, Object>();
		 req.put("head", head);
		 req.put("body", body);	
		 //将req集合转换成json格式发生post请求
		 final Gson gson = new Gson();
		 final String str = gson.toJson(req);
		return  HttpClientJson.readInterfacePost(detailUrl, str);
	}
	@Override
	public String CitizencardauthenticationInfo(String detailUrl,
			String CARDNUM, String NAME, String IDCARDCODE, String MOBILENUM,
			String CODE ,String accessTicket) {
		 Map<String, Object> head = new HashMap<String, Object>();
		 Map<String, Object> body = new HashMap<String, Object>();		 
		 //写入body字段
		 body.put("CARDNUM", CARDNUM);	
		 body.put("NAME", NAME);	
		 body.put("IDCARDCODE", IDCARDCODE);	
		 body.put("MOBILENUM", MOBILENUM);	
		 body.put("CODE", CODE);	
		//写入head字段
		 head.put("appid", appid);
		 head.put("accessTicket", accessTicket);
		 try {
		 head.put("sign", DataUtil.signRequest(appid,body,appkey));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 head.put("version", version);
		 //将body和head一起放入req
		 final HashMap<String, Object> req = new HashMap<String, Object>();
		 req.put("head", head);
		 req.put("body", body);	
		 //将req集合转换成json格式发生post请求
		 final Gson gson = new Gson();
		 final String str = gson.toJson(req);
		return  HttpClientJson.readInterfacePost(detailUrl, str);
	}
	@Override
	public String SFZHMauthenticationInfo(String detailUrl, String NAME,
			String IDCARDCODE, String FPICTURE, String BPICTURE,
			String MOBILENUM, String CODE,String accessTicket) {
		 Map<String, Object> head = new HashMap<String, Object>();
		 Map<String, Object> body = new HashMap<String, Object>();		 
		 //写入body字段
		 body.put("NAME", NAME);	
		 body.put("IDCARDCODE", IDCARDCODE);	
		 body.put("FPICTURE", FPICTURE);	
		 body.put("BPICTURE", BPICTURE);	
		 body.put("MOBILENUM", MOBILENUM);	
		 body.put("CODE", CODE);	
		//写入head字段
		 head.put("appid", appid);
		 head.put("accessTicket", accessTicket);
		 try {
		 head.put("sign", DataUtil.signRequest(appid,body,appkey));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 head.put("version", version);
		 //将body和head一起放入req
		 final HashMap<String, Object> req = new HashMap<String, Object>();
		 req.put("head", head);
		 req.put("body", body);	
		 //将req集合转换成json格式发生post请求
		 final Gson gson = new Gson();
		 final String str = gson.toJson(req);
		return  HttpClientJson.readInterfacePost(detailUrl, str);
	}
	@Override
	public String GetUserBasicInfo(String detailUrl, String USERID) {
		 Map<String, Object> head = new HashMap<String, Object>();
		 Map<String, Object> body = new HashMap<String, Object>();		 
		 //写入body字段
		 body.put("USERID", USERID);	
		//写入head字段
		 head.put("appid", appid);
		 try {
		 head.put("sign", DataUtil.signRequest(appid,body,appkey));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 head.put("version", version);
		 //将body和head一起放入req
		 final HashMap<String, Object> req = new HashMap<String, Object>();
		 req.put("head", head);
		 req.put("body", body);	
		 //将req集合转换成json格式发生post请求
		 final Gson gson = new Gson();
		 final String str = gson.toJson(req);
		return  HttpClientJson.readInterfacePost(detailUrl, str);
	}
	@Override
	public String UserInfoSave(String detailUrl, String LOGIN, String NAME,
			String SEX, String BIRTHDAY, String MARRY, String RESIDENCE) {
		 Map<String, Object> head = new HashMap<String, Object>();
		 Map<String, Object> body = new HashMap<String, Object>();		 
		 //写入body字段
		 body.put("LOGIN", LOGIN);	
		 body.put("NAME", NAME);
		 body.put("SEX", SEX);
		 body.put("BIRTHDAY", BIRTHDAY);
		 body.put("MARRY", MARRY);
		 body.put("RESIDENCE", RESIDENCE);
		//写入head字段
		 head.put("appid", appid);
		 try {
		 head.put("sign", DataUtil.signRequest(appid,body,appkey));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 head.put("version", version);
		 //将body和head一起放入req
		 final HashMap<String, Object> req = new HashMap<String, Object>();
		 req.put("head", head);
		 req.put("body", body);	
		 //将req集合转换成json格式发生post请求
		 final Gson gson = new Gson();
		 final String str = gson.toJson(req);
		return  HttpClientJson.readInterfacePost(detailUrl, str);
	}
	@Override
	public String Changepassword(String detailUrl, String PWDSTRENGTH,
			String OLDPWD, String NEWPWD,String accessTicket) {
		 Map<String, Object> head = new HashMap<String, Object>();
		 Map<String, Object> body = new HashMap<String, Object>();		 
		 //写入body字段
		 SHA1 sha1=new SHA1();
		 OLDPWD=sha1.getDigestOfString(OLDPWD.getBytes());
		 NEWPWD=sha1.getDigestOfString(NEWPWD.getBytes());
		 body.put("PWDSTRENGTH", PWDSTRENGTH);	
		 body.put("OLDPASSWORD", OLDPWD);
		 body.put("NEWPASSWORD", NEWPWD);
		//写入head字段
		 head.put("appid", appid);
		 head.put("accessTicket", accessTicket);
		 try {
		 head.put("sign", DataUtil.signRequest(appid,body,appkey));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 head.put("version", version);
		 //将body和head一起放入req
		 final HashMap<String, Object> req = new HashMap<String, Object>();
		 req.put("head", head);
		 req.put("body", body);	
		 //将req集合转换成json格式发生post请求
		 final Gson gson = new Gson();
		 final String str = gson.toJson(req);
		return  HttpClientJson.readInterfacePost(detailUrl, str);
	}
	@Override
	public String entNameCheck(String detailUrl, String QYMC) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("QYMC", QYMC));
		try {
			return HttpClientUtil.readInterfacePost(detailUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String entNumCheck(String detailUrl, String ZZJGDM) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("ZZJGDM", ZZJGDM));
		try {
			return HttpClientUtil.readInterfacePost(detailUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String PeoNameCheck(String detailUrl, String FRDBXM) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("FRDBXM", FRDBXM));
		try {
			return HttpClientUtil.readInterfacePost(detailUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String PeoNumCheck(String detailUrl, String FRDBZJH) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("FRDBZJH", FRDBZJH));
		try {
			return HttpClientUtil.readInterfacePost(detailUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String businessNumCheck(String detailUrl, String YYZZH) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("YYZZH", YYZZH));
		try {
			return HttpClientUtil.readInterfacePost(detailUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String getUserEntStatus(String detailUrl, String loginName,
			String password, String flag) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("loginName", loginName));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("flag", flag));
		try {
			return HttpClientUtil.readInterfacePost(detailUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String entApprove(String detailUrl,String userName, String entName,
			String organizationCode, String entType, String startTime,
			String endTime, String phone, String email, String img1,
			String img2, String img3, String img5, String img6,
			String corporationName, String corporationId, String YYZZH) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userName", userName));
		params.add(new BasicNameValuePair("entName", entName));
		params.add(new BasicNameValuePair("organizationCode", organizationCode));
		params.add(new BasicNameValuePair("entType", entType));
		params.add(new BasicNameValuePair("startTime", startTime));
		params.add(new BasicNameValuePair("endTime", endTime));
		params.add(new BasicNameValuePair("phone", phone));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("img1", img1));
		params.add(new BasicNameValuePair("img2", img2));
		params.add(new BasicNameValuePair("img3", img3));
		params.add(new BasicNameValuePair("img5", img5));
		params.add(new BasicNameValuePair("img6", img6));
		params.add(new BasicNameValuePair("corporationName", corporationName));
		params.add(new BasicNameValuePair("corporationId", corporationId));
		params.add(new BasicNameValuePair("YYZZH", YYZZH));
		try {
			return HttpClientUtil.readInterfacePost(detailUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String entNameSelect(String detailUrl, String QYMC) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("QYMC", QYMC));
		try {
			return HttpClientUtil.readInterfacePost(detailUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String entNumSelect(String detailUrl, String ZZJGDM) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("ZZJGDM", ZZJGDM));
		try {
			return HttpClientUtil.readInterfacePost(detailUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String selectEntInfo(String detailUrl, String ENT_ID) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("entId", ENT_ID));
		try {
			return HttpClientUtil.readInterfacePost(detailUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String ChangePhone(String detailUrl ,String MOBILENUM, String CODE, String accessTicket) {
		Map<String, Object> head = new HashMap<String, Object>();
		 Map<String, Object> body = new HashMap<String, Object>();		 
		 //写入body字段
		 body.put("MOBILENUM", MOBILENUM);	
		 body.put("CODE", CODE);	
		//写入head字段
		 head.put("appid", appid);
		 head.put("accessTicket", accessTicket);
		 try {
		 head.put("sign", DataUtil.signRequest(appid,body,appkey));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 head.put("version", version);
		 //将body和head一起放入req
		 final HashMap<String, Object> req = new HashMap<String, Object>();
		 req.put("head", head);
		 req.put("body", body);	
		 //将req集合转换成json格式发生post请求
		 final Gson gson = new Gson();
		 final String str = gson.toJson(req);
		return  HttpClientJson.readInterfacePost(detailUrl, str);
	}
	@Override
	public String UploadImage(String detailUrl, String IMGSTR,
			String accessTicket) {
		Map<String, Object> head = new HashMap<String, Object>();
		 Map<String, Object> body = new HashMap<String, Object>();		 
		 //写入body字段
		 body.put("IMGSTR", IMGSTR);		
		//写入head字段
		 head.put("appid", appid);
		 head.put("accessTicket", accessTicket);
		 try {
		 head.put("sign", DataUtil.signRequest(appid,body,appkey));		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 head.put("version", version);
		 //将body和head一起放入req
		 final HashMap<String, Object> req = new HashMap<String, Object>();
		 req.put("head", head);
		 req.put("body", body);	
		 //将req集合转换成json格式发生post请求
		 final Gson gson = new Gson();
		 final String str = gson.toJson(req);
		return  HttpClientJsonImage.readInterfacePost(detailUrl, str);
	}
	@Override
	public String UploadImage_ent(String detailUrl, String imgStr ,String imgType ) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("imgStr", imgStr));
		params.add(new BasicNameValuePair("imgType", imgType));
		try {
			return HttpClientUtil.readInterfacePost(detailUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	

}
