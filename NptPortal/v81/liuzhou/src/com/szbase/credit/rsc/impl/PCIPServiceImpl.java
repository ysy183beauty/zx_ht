/**
* @Title: PCIPServiceImpl.java
* @Package com.szbase.credit.rsc.impl
* @Description: TODO
* @author Owen Szbase
* @date 2015年7月15日 下午2:55:08
* @version V1.0
*/
package com.szbase.credit.rsc.impl;

import com.szbase.credit.rsc.PCIPService;
import com.szbase.credit.util.Base64Code;
import com.szbase.credit.util.HttpClientUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: PCIPServiceImpl
 * @Description: TODO
 * @author Owen Szbase
 * @date 2015年7月15日 下午2:55:08
 *
 */
@Service
public class PCIPServiceImpl implements PCIPService {

	@Override
	public String lookupEnterpriseCredit(String eName, String icrNum,
			String orgCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String lookupPersonalCredit(String name, String idCard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String lookupFocusGroupCredit(String name, String idCard,
			int focusType, String qcNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String lookupupSelfCredit(String selfType, String jsonParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void pushAppeal(String id, String cnt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String lookupAppealStauts(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> lookupOrgList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHhbxxPageList(String listUrl, String param_xxlb, int start, int limit) {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("param_isvalid", "Y"));
		params.add(new BasicNameValuePair("param_xxlb", param_xxlb));
		params.add(new BasicNameValuePair("start", String.valueOf(start)));
		params.add(new BasicNameValuePair("limit", String.valueOf(limit)));

		try {
			return HttpClientUtil.readInterfacePost(listUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getHhbxxDetail(String detailUrl, String paramId, int start, int limit,String first,String queryword) {

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("paramId", paramId));
		params.add(new BasicNameValuePair("start", String.valueOf(start)));
		params.add(new BasicNameValuePair("limit", String.valueOf(limit)));
		params.add(new BasicNameValuePair("first", String.valueOf(first)));
		if(queryword!=null&&!"".equals(queryword)){
			params.add(new BasicNameValuePair("queryword", String.valueOf(queryword)));	
		}
		try {
			return HttpClientUtil.readInterfacePost(detailUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getQyJcxxPageList(String listUrl, String qymc, String gszch, int start, int limit) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("qymc", qymc));
		params.add(new BasicNameValuePair("gszch", gszch));
		params.add(new BasicNameValuePair("start", String.valueOf(start)));
		params.add(new BasicNameValuePair("limit", String.valueOf(limit)));

		try {
			return HttpClientUtil.readInterfacePost(listUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getQyJcxxDetail(String detailUrl, String qybs) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("qybs", qybs));

		try {
			return HttpClientUtil.readInterfacePost(detailUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getQyxyxxDetail(String detailUrl, String param_bs,
			String param_type) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("param_bs", param_bs));
		params.add(new BasicNameValuePair("param_type", param_type));

		try {
			return HttpClientUtil.readInterfacePost(detailUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	@Override
	public String getQyZyPageList(String detailUrl, String param_bs,
			String param_type, String tablename, int start, int limit) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		try {
			tablename =  (new BASE64Encoder()).encodeBuffer(tablename.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(tablename);
		params.add(new BasicNameValuePair("param_bs", param_bs));
		params.add(new BasicNameValuePair("param_type", param_type));
		params.add(new BasicNameValuePair("tablename",tablename));
		params.add(new BasicNameValuePair("start",  String.valueOf(start)));
		params.add(new BasicNameValuePair("limit",  String.valueOf(limit)));
		try {
			System.out.println(new String((new BASE64Decoder()).decodeBuffer(tablename),"utf-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			return HttpClientUtil.readInterfacePost(detailUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getGrJcxxDetail(String detailUrl, String sfzh) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("sfzh", sfzh));
		try {
			return HttpClientUtil.readInterfacePost(detailUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getSGS(String detailUrl ,int start, int limit, String type, String queryday,
			String xzxdrm) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("queryday", queryday));
		params.add(new BasicNameValuePair("xzxdrm", xzxdrm));
		params.add(new BasicNameValuePair("type", type));
		params.add(new BasicNameValuePair("start",  String.valueOf(start)));
		params.add(new BasicNameValuePair("limit",  String.valueOf(limit)));

		try {
			return HttpClientUtil.readInterfacePost(detailUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
