package org.summer.springsecurity.authentication.handler;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.summer.core.context.PlatformInitializingBean;
import org.summer.security.SecurityCorePreferenceUtils;
import org.summer.springsecurity.authentication.PlatformLoginRequestSecurityUtils;
import org.summer.springsecurity.authentication.resolver.UserpasswordAuthenticationTokenResolver;
public class PlatformAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler implements PlatformInitializingBean {
    public PlatformAuthenticationFailureHandler() {
    }

    public void afterLoaderPlatformContext() throws Exception {
        String authenticationFailureUrl = SecurityCorePreferenceUtils.getAuthenticationFailureUrl();
        super.setDefaultFailureUrl(authenticationFailureUrl);
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Integer errorCount=0;
        int securityLevel = UserpasswordAuthenticationTokenResolver.getLoginSecurityLevel(request);
        Map<String, String> publicKey = Collections.emptyMap();
        if (securityLevel > 0) {
            publicKey = PlatformLoginRequestSecurityUtils.createHttpSessionKeyPair(request);
        }
        if (PlatformLoginRequestSecurityUtils.isAjaxLoginRequest(request)) {
            String message = "登录失败！";
            if (exception != null) {
                if (!(exception instanceof BadCredentialsException) && !(exception instanceof UsernameNotFoundException)) {
                    message = exception.getMessage();
                    if (message == null) {
                        message = exception.toString();
                    }
                } else {
                    message = "用户名或密码错误，请重新输入！";
                    errorCount=1;
                }
            }

            JSONObject loginResult = new JSONObject();
            loginResult.put("result", false);
            loginResult.put("message", message);
            loginResult.put("errorCount",errorCount);
            loginResult.put("useJcaptcha", UserpasswordAuthenticationTokenResolver.useJcaptcha(request));
            loginResult.putAll(publicKey);
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            String contentType = "application/json";
            response.setContentType(contentType);
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(loginResult);
            out.flush();
            out.close();
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
