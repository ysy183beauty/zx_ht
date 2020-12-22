package com.szbase.credit.controller;

import com.alibaba.fastjson.JSONObject;
import com.szbase.credit.rsc.PCIPService;
import com.szbase.credit.service.CreditInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * 企业信用 Created by zhanglei on 15/10/12.
 */
@Controller
@RequestMapping(value = "/credit_company", method = RequestMethod.GET)
public class CreditInfoController {
	@Autowired
	private Properties creditResources;

	@Autowired
	private CreditInfoService creditInfoService;

    @Autowired
    private PCIPService pcipService;

	@RequestMapping(value = "/baseCompanyList")
	public String baseCompanyList(HttpServletRequest request, HttpServletResponse response) {
        String qymc=request.getParameter("qymc");
        String gszch=request.getParameter("gszch");
        int start=Integer.parseInt(request.getParameter("start"));
        int limit= Integer.parseInt(request.getParameter("limit"));

            if(limit>0){
                Map<String,Object> companyList = creditInfoService.baseCompanyList(qymc, gszch, start, limit);
                request.setAttribute("content", companyList);
            }else{
                Map<String,Object> map =new HashMap<String, Object>();
                map.put("list",new ArrayList<Object>());
                map.put("count",0);
                request.setAttribute("content", map);
            }

           // System.out.println("企业基础信息列表：" + companyList.toString());

		return "baseCompanyList";
	}

	@RequestMapping(value = "/baseCompanyDetail")
	public String baseCompanyDetail(HttpServletRequest request,
			HttpServletResponse response) {
        String qybs=request.getParameter("qybs");
        Map<String,Object> detail = creditInfoService.baseCompanyDetail(qybs);
		request.setAttribute("content", detail);
		System.out.println("企业基础信息详情："+detail);
		return "baseCompanyDetail";
	}

	// 企业信用查询委办局资源信息
	@RequestMapping(value = "/creditCompanyTypeDetail")
	public String creditCompanyTypeDetail(HttpServletRequest request,
			HttpServletResponse response) {

        String mc=request.getParameter("mc");
			// 获取企业标示
        Map<String,Object> detail = creditInfoService.creditCompanyTypeDetail(mc);
        Map<String,Object> main =( Map<String,Object>)detail.get("main");
        detail.remove("main");
		request.setAttribute("content", detail);
        request.setAttribute("base", main);

		return "creditCompanyTypeDetail";
	}

    // 企业信用查询委办局资源信息
    @RequestMapping(value = "/creditCompanyDetail")
    public String creditCompanyDetail(HttpServletRequest request,
                                      HttpServletResponse response) {

        String mc=request.getParameter("mc");
        String type=request.getParameter("type");
        String name="";
        String types="";
        try {
            name = URLDecoder.decode(mc, "utf-8");
            types = URLDecoder.decode(type, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 获取企业标示
        Map<String,Object> detail = creditInfoService.creditCompanyDetail(name,types);

        request.setAttribute("head", detail.get("head"));
        request.setAttribute("data", detail.get("data"));
        request.setAttribute("mc", name);
        request.setAttribute("type", types);
        System.out.println(detail.toString());

        return "creditCompanyDetail";
    }

	@RequestMapping(value = "/creditCompanyList")
	public String creditCompanyList(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
        String param_bs=request.getParameter("param_bs");
        String param_type=request.getParameter("param_type");
        String tablename=request.getParameter("tablename");
        String wbjmc=request.getParameter("wbjmc");
        String resoucename=request.getParameter("resoucename");
        int start=Integer.parseInt(request.getParameter("start"));
        int limit= Integer.parseInt(request.getParameter("limit"));

        request.setAttribute("wbjmc", URLDecoder.decode(wbjmc,"UTF-8"));
		request.setAttribute("resoucename", URLDecoder.decode(resoucename,"UTF-8"));
		tablename =URLDecoder.decode(tablename,"UTF-8");
		String str = pcipService.getQyZyPageList(
				creditResources.getProperty("CREDIT_COMPANY_ZY_URL"), param_bs,
				param_type, tablename, start, limit);
		request.setAttribute("content", JSONObject.parse(str));
		System.out.println(JSONObject.parse(str));
		return "creditCompanyList";
	}

    @RequestMapping(value = "/creditPersonList")
    public String creditPersonList(HttpServletRequest request,
                                   HttpServletResponse response) {

        return "creditPersonList";
    }

    @RequestMapping(value = "/creditPersonDetail")
    public String basePersonDetail(HttpServletRequest request,
                                      HttpServletResponse response) {
        String qybs=request.getParameter("qybs");

        String str = pcipService.getQyJcxxDetail(
                creditResources.getProperty("CREDIT_COMPANY_DETAIL_URL"), qybs);
        request.setAttribute("content", JSONObject.parse(str));
        System.out.println("企业基础信息详情："+str);
        return "creditPersonDetail";
    }
}
