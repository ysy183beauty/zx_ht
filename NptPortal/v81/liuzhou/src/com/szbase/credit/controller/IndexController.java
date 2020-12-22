/**
* @Title: IndexController.java
* @Package com.szbase.credit.handlers
* @Description: TODO
* @author Owen Szbase
* @date 2015年8月25日 下午3:12:10
* @version V1.0
*/
package com.szbase.credit.controller;

import com.alibaba.fastjson.JSONObject;
import com.szbase.credit.rsc.PCIPService;
import com.szbase.credit.util.lucenesearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName: IndexController
 * @Description: TODO
 * @author Owen Szbase
 * @date 2015年8月25日 下午3:12:10
 *
 */
@Controller
@RequestMapping(value="/index")
public class IndexController {

	@Autowired
	private Properties creditResources;

	@Autowired
	private PCIPService pcipService;

	//首页初始化
	@RequestMapping(value="/dynamicList")
	@ResponseBody
	public String dynamicList(HttpServletRequest request, HttpServletResponse response, int start, int limit) {
		String g = pcipService.getHhbxxPageList(creditResources.getProperty("LIST_URL"), "G", start, limit);
		String r = pcipService.getHhbxxPageList(creditResources.getProperty("LIST_URL"), "R", start, limit);
		String b = pcipService.getHhbxxPageList(creditResources.getProperty("LIST_URL"), "B", start, limit);

		Map<String, String> map = new HashMap<String, String>();
		map.put("g", g);
		map.put("r", r);
		map.put("b", b);

		return JSONObject.toJSONString(map);
	}
	
	//个人基础信息查询详细信息接口
	@RequestMapping(value="/getGrJcxxDetail")
	public String getGrJcxxDetail(HttpServletRequest request, HttpServletResponse response, String sfzh) {
		String str = pcipService.getGrJcxxDetail(creditResources.getProperty("CREDIT_PEO_DETAIL_URL"),sfzh);

		request.setAttribute("content", JSONObject.parse(str));
		 System.out.println(str);
		return "creditPersionalDetail";
	}
	
	//红黑榜索引查询
		@RequestMapping(value="/indexghb")
		@ResponseBody
		public String indexghb(HttpServletRequest request, HttpServletResponse response, String querystr) throws Exception {
			String json = lucenesearch.search(querystr);
			System.out.println("返回结果："+json);
			return json;
		}
	
	
}
