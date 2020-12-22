package com.szbase.credit.controller;

import com.szbase.credit.rsc.EPSPService;
import com.szbase.credit.util.Base64Code;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * @ClassName: CreditPublityController
 * @Description: TODO
 * @author Owen Szbase
 * @date 2015年8月25日 下午3:14:16
 *
 */
@Controller
@RequestMapping(value = "/Enterprise")
public class EnterpriseController{

	@Autowired
	private Properties creditResources;

	@Autowired
	private EPSPService ecspservice;
	
	/**
	 * 企业名称检测
	 * @param request
	 * @param response
	 * @param QYMC
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/entNameCheck")
	public String entNameCheck(HttpServletRequest request, HttpServletResponse response,
									String QYMC) {
		System.out.println(QYMC);
        String str = ecspservice.entNameCheck(creditResources.getProperty("entNameCheck_URL"), QYMC);
       
        str="{\"responseData\":"+str+"}";
        System.out.println(str);
		return str;
	}
	
	/**
	 * 企业机构代码检测
	 * @param request
	 * @param response
	 * @param ZZJGDM
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/entNumCheck")
	public String entNumCheck(HttpServletRequest request, HttpServletResponse response,
									String ZZJGDM) {
        String str = ecspservice.entNumCheck(creditResources.getProperty("entNumCheck_URL"), ZZJGDM);
        JSONObject json = null;
        try {
            json = new JSONObject(str);
            int count = json.getJSONObject("head").getInt("count");
            if(count!=0){
                str = "true";
            }else{
                str = "false";
            }
            str="{\"responseData\":"+str+"}";
            System.out.println(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }

		return str;
	}
	
	/**
	 * 法人姓名检测
	 * @param request
	 * @param response
	 * @param FRDBXM
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/PeoNameCheck")
	public String PeoNameCheck(HttpServletRequest request, HttpServletResponse response,
									String FRDBXM) {
        String str = ecspservice.PeoNameCheck(creditResources.getProperty("PeoNameCheck_URL"), FRDBXM);
       
        str="{\"responseData\":"+str+"}";
      		System.out.println(str);
		return str;
	}
	
	/**
	 * 法人身份证号检测
	 * @param request
	 * @param response
	 * @param FRDBZJH
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/PeoNumCheck")
	public String PeoNumCheck(HttpServletRequest request, HttpServletResponse response,
									String FRDBZJH) {
        String str = ecspservice.PeoNumCheck(creditResources.getProperty("PeoNumCheck_URL"), FRDBZJH);
        str="{\"responseData\":"+str+"}";
      	System.out.println(str);
		return str;
	}
	
	/**
	 * 营业执照号检测
	 * @param request
	 * @param response
	 * @param YYZZH
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/businessNumCheck")
	public String businessNumCheck(HttpServletRequest request, HttpServletResponse response,
									String YYZZH) {
        String str = ecspservice.businessNumCheck(creditResources.getProperty("businessNumCheck_URL"), YYZZH);
        str="{\"responseData\":"+str+"}";
  		System.out.println(str);
		return str;
	}
	
	/**
	 * 企业登陆接口
	 * @param request
	 * @param response
	 * @param loginName
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/getUserEntStatus")
	public String getUserEntStatus(HttpServletRequest request, HttpServletResponse response,
									String loginName ,String password ,String flag) {
        String str = ecspservice.getUserEntStatus(creditResources.getProperty("getUserEntStatus_URL"), loginName, password, flag);
		return str;
	}
	
	/**
	 *企业实名认证接口
	 * @param request
	 * @param response
	 * @param userName
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/entApprove")
	public String entApprove(HttpServletRequest request, HttpServletResponse response,
									String userName ,String entName ,String organizationCode ,String entType,String startTime
									,String endTime ,String phone ,String email ,String img1 ,String img2,String img3,String img5
									,String img6 ,String corporationName ,String corporationId ,String YYZZH) {
        String str = ecspservice.entApprove(creditResources.getProperty("entApprove_URL"), userName, entName, organizationCode, entType, startTime, endTime, phone, email, img1, img2, img3, img5, img6, corporationName, corporationId, YYZZH);
        System.out.println(str);
		return str;
	}
	
	/**
	 * 企业名称查询企业信息
	 * @param request
	 * @param response
	 * @param QYMC
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/entNameSelect")
	public String entNameSelect(HttpServletRequest request, HttpServletResponse response,
									String QYMC) {
        String str = ecspservice.entNameSelect(creditResources.getProperty("entNameSelect_URL"), QYMC);
        System.out.println(str);
		return str;
	}
	/**
	 * 企业组织机构代码查询企业信息
	 * @param request
	 * @param response
	 * @param ZZJGDM
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/entNumSelect")
	public String entNumSelect(HttpServletRequest request, HttpServletResponse response,
									String ZZJGDM) {
        String str = ecspservice.entNumSelect(creditResources.getProperty("entNumSelect_URL"), ZZJGDM);
		return str;
	}
	/**
	 * 查询企业认证信息
	 * @param request
	 * @param response
	 * @param entId
	 * @return str
	 */
	@ResponseBody
	@RequestMapping(value="/selectEntInfo")
	public String selectEntInfo(HttpServletRequest request, HttpServletResponse response,
									String entId ) {
        String str = ecspservice.selectEntInfo(creditResources.getProperty("selectEntInfo_URL"), entId   );
		return str;
	}
	/**
	 * 企业上传图片
	 * @param request
	 * @param response
	 * @param Filedata
	 * @return str
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="/UploadImage")
	public void UploadImage(HttpServletRequest request, HttpServletResponse response,
			MultipartFile Filedata ) throws IOException {
		System.out.println("图片大小："+Filedata.getSize());
		System.out.println("图片原名："+Filedata.getOriginalFilename());
        String str = ecspservice.UploadImage_ent(creditResources.getProperty("UploadImage_entURL"),  Base64Code.Encode(Filedata.getBytes()) , Filedata.getOriginalFilename().split("\\.")[1]  );
        JSONObject json = null;
        try {
            json = new JSONObject(str);
            String imgUrl = json.getString("imgUrl");
            String serverData = "{\"rtnCode\":\""+"000000"+"\",\"fileURL\":\"http://"+imgUrl+"\",\"fileName\":\""+imgUrl+"\",\"originalFileName\":\""+Filedata.getOriginalFilename()+"\",\"fileSize\":"+Filedata.getSize()+"}";
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