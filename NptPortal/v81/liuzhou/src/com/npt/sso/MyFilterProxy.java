package com.npt.sso;

import com.minstone.cas.client.util.HttpLoginForm;
import com.minstone.cas.client.util.UuDelegatingFilterProxy;
import com.minstone.cas.client.util.UuTool;
import com.minstone.cas.client.validation.Assertion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class MyFilterProxy extends UuDelegatingFilterProxy {

	/**
	 * 用户登录统一认证平台后，会调用该方法
	 */
	protected void onSuccessfulValidation(HttpServletRequest request,
										  HttpServletResponse response, Assertion assertion) {

		System.out.println("onSuccessfulValidation");
		loginForm(request, response);
	}

	/* 模拟后台表单提交登录，通过J2EE安全认证 */
	private void loginForm(HttpServletRequest request,
						   HttpServletResponse response) {
		// 获取当前应用系统绑定统一认证平台的用户名
		Map map = UuTool.getUuAttributes(request);
		String loginurl = this.getServerUrl(request) + "/login.jspx";
		request.getSession().setAttribute("userinfo",map);
		String sessionId = request.getSession().getId();
		HttpLoginForm form = new HttpLoginForm();
		form.setFormAction(loginurl);
		String phoneNumber=map.get("PHONENUMBER")==null?"":map.get("PHONENUMBER")+"";//登陆名与密码都使用电话号码
		form.addParameter("username",phoneNumber);
		form.addParameter("password", phoneNumber);
		form.addParameter("sessionId",sessionId);
		form.setSessionId(sessionId);
		form.submit();

	}

	private String getPassword(String userCode){
		// 开发人员实现
		return null;
	}

	private String getServerUrl(HttpServletRequest request) {
		StringBuffer serverUrl = new StringBuffer();
		serverUrl.append(request.getScheme()).append("://")
				.append(request.getServerName()).append(":")
				.append(request.getServerPort())
				.append(request.getContextPath());
		return serverUrl.toString();
	}

	protected boolean whichUrlSkip(HttpServletRequest request) {
		if (request.getRequestURI().contains("nptSync") || request.getRequestURI().contains("jeeadmin")) {
			return true;
		}
		return super.whichUrlSkip(request);
	}

}
