package com.szbase.credit.util;

import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class HttpClientJson {
	
	 public static String readInterfacePost(String detailUrl,String str){
		HttpClient client = new HttpClient();
		
		 final PostMethod post = new PostMethod(detailUrl);
		 try {
		// post.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0");
		 final RequestEntity re = new StringRequestEntity(str,
		 "application/json", "utf-8");
		 post.setRequestEntity(re);
		 System.out.println(new Date());
		 client.executeMethod(post);
		 System.out.println(new Date());
		 System.out.println(str);
		 System.out.println("Access System authenticate, Status: " +
		 post.getStatusCode());
		 System.out.println("Access System authenticate, Response: " +
		 post.getResponseBodyAsString());
		 return post.getResponseBodyAsString();
		 } catch (final Exception e) {
		 e.printStackTrace();
		 // 调用异常, 返回异常报文
		 } finally {
		 post.releaseConnection();
		 }
		return null;
	}
}
