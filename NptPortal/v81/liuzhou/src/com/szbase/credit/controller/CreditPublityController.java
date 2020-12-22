/**
* @Title: CreditPublityController.java
* @Package com.szbase.credit.controller
* @Description: TODO
* @author Owen Szbase
* @date 2015年8月25日 下午3:14:16
* @version V1.0
*/
package com.szbase.credit.controller;

import com.alibaba.fastjson.JSONObject;
import com.szbase.credit.rsc.PCIPService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * @ClassName: CreditPublityController
 * @Description: TODO
 * @author Owen Szbase
 * @date 2015年8月25日 下午3:14:16
 *
 */
@Controller
@RequestMapping(value = "/credit_publity", method = RequestMethod.GET)
public class CreditPublityController{

	@Autowired
	private Properties creditResources;

	@Autowired
	private PCIPService pcipService;

	@RequestMapping(value="/creditPublityView")
	public String creditPublityView(HttpServletRequest request, HttpServletResponse response,
									String paramId, int start, int limit,String first, String type ,String queryword) {
		if("1".equals(type)){
		request.setAttribute("wz", "公示信息");	
		}
		
		else	if("2".equals(type)){
		request.setAttribute("wz", "红榜信息");	
		}
		else{
			request.setAttribute("wz", "黑榜信息");		
		}
		request.setAttribute("type", type);
		request.setAttribute("queryword", queryword);
        String str = pcipService.getHhbxxDetail(creditResources.getProperty("PUBLITY_DETAIL_URL"), paramId, start, limit,first,queryword);
        request.setAttribute("content", JSONObject.parse(str));
		System.out.println(JSONObject.parse(str));
		return "creditPublityDetail";
	}

	@RequestMapping(value="/creditPublityList")
	public String dynamicList(HttpServletRequest request, HttpServletResponse response,
							  int start, int limit) {
        String str = pcipService.getHhbxxPageList(creditResources.getProperty("PUBLITY_LIST_URL"), "G", start, limit);
        request.setAttribute("content", JSONObject.parse(str));
        System.out.println(str);
		return "creditPublityList";
	}

	@RequestMapping(value="/creditRedList")
	public String creditRedList(HttpServletRequest request, HttpServletResponse response,
							  int start, int limit) {
        String str = pcipService.getHhbxxPageList(creditResources.getProperty("PUBLITY_LIST_URL"), "R", start, limit);
        request.setAttribute("content", JSONObject.parse(str));
		return "creditRedList";
	}

	@RequestMapping(value="/creditBlackList")
	public String creditBlackList(HttpServletRequest request, HttpServletResponse response,
							  int start, int limit) {
        String str = pcipService.getHhbxxPageList(creditResources.getProperty("PUBLITY_LIST_URL"), "B", start, limit);
        request.setAttribute("content", JSONObject.parse(str));
		return "creditBlackList";
	}

	@RequestMapping(value="/creditRedView")
	public String creditRedView(HttpServletRequest request, HttpServletResponse response,
									String paramId, int start, int limit,String first,
									String title) {
		  String str = pcipService.getHhbxxDetail(creditResources.getProperty("PUBLITY_DETAIL_URL"), paramId, start, limit,first,"");
        request.setAttribute("content", JSONObject.parse(str));
		request.setAttribute("title", title);
		return "creditRedDetail";
	}

	@RequestMapping(value="/creditBlackView")
	public String creditBlackView(HttpServletRequest request, HttpServletResponse response,
									String paramId, int start, int limit,String first,
									String title) {
		  String str = pcipService.getHhbxxDetail(creditResources.getProperty("PUBLITY_DETAIL_URL"), paramId, start, limit,first,"");
        request.setAttribute("content", JSONObject.parse(str));
		request.setAttribute("title", title);
		return "creditBlackDetail";
	}
	
	@RequestMapping(value="/creditSgsdetail")
	public String creditSgsdetail(HttpServletRequest request, HttpServletResponse response,
									String type, String xzxdrm) {
		if("1".equals(type)){
			return "creditSgscfdetail";
		}
		return "creditSgsxkdetail";
	}
	
	@ResponseBody
	@RequestMapping(value="/creditSGS")
	public String creditSGS(HttpServletRequest request, HttpServletResponse response,
									 int start, int limit,String type) {
		String queryday = request.getParameter("queryday");
		String xzxdrm = request.getParameter("xzxdrm");
		  String str = pcipService.getSGS(creditResources.getProperty("SGS_URL"),  start, limit,type,queryday,xzxdrm);
       
		return str;
	}
}
