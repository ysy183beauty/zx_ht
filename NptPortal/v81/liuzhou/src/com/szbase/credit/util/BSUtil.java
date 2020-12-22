/**
* @Title: BSUtil.java
* @Package com.szbase.credit.util
* @Description: TODO
* @author Owen Szbase
* @date 2015年7月20日 下午1:41:43
* @version V1.0
*/
package com.szbase.credit.util;

import javax.servlet.http.HttpServletResponse;

import com.jeecms.common.web.ResponseUtils;

/**
 * @ClassName: BSUtil
 * @Description: TODO
 * @author Owen Szbase
 * @date 2015年7月20日 下午1:41:43
 *
 */
public class BSUtil {

	public static void response_error(HttpServletResponse response,String error){
		String responseJson = "{'success':false,'error':" + error + "}";
		ResponseUtils.renderJson(response, responseJson);
	}
	
	public static void response_success_Login(HttpServletResponse response,String userName){
		String responseJson = "{'success':true,'userName':" + userName + "}";
		ResponseUtils.renderJson(response, responseJson);
	}
}
