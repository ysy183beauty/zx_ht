package com.jeecms.core.web.util;

import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提供一些CMS系统中使用到的共用方法
 *
 * 比如获得会员信息,获得后台站点信息
 */
public class CmsUtils {
	/**
	 * 用户KEY
	 */
	public static final String USER_KEY = "_user_key";

    /**
     * 用户KEY
     */
    public static final String USER = "_user";
	/**
	 * 站点KEY
	 */
	public static final String SITE_KEY = "_site_key";

	/**
	 * 获得用户
	 *
	 * @param request
	 * @return
	 */
	public static CmsUser getUser(HttpServletRequest request) {
	    CmsUser user= (CmsUser) request.getAttribute(USER_KEY);
	    if(user==null){
           user = (CmsUser) request.getSession().getAttribute(USER);
        }

		return user;
	}


    /**
     * session移除用户
     *
     * @param request
     * @return
     */
    public static void removeSesisonUser(HttpServletRequest request) {

            request.getSession().removeAttribute(USER);

        return ;
    }

    /**
     * session移除用户
     *
     * @param request
     * @return
     */
    public static void removeUser(HttpServletRequest request) {

        request.getSession().removeAttribute(USER);
        request.removeAttribute(USER_KEY);

        return ;
    }

    /**
     * session移除用户
     *
     * @param request
     * @return
     */
    public static void addSesisonUser(HttpServletRequest request,CmsUser user) {

        request.getSession().setAttribute(USER, user);

        return ;
    }

    /**
     * 判断用户是否存在
     */
    public static Boolean checkUserIsLogin(HttpServletRequest request) {
        CmsUser user=getUser(request);
        if(user==null){
            return false;
        }
        return true;
    }

    /**
     * 是否认证成功
     */
    public static Boolean checkUserIsAuthentification(HttpServletRequest request) {
        CmsUser user=getUser(request);
        if(user==null){
            return false;
        }else{
            String flag=user.getFlag();
            String type=user.getType();
            String idCard=user.getIdCard();
            String realname = user.getRealname();
            if(!"3".equals(flag)){//认证成功
                return false;
            }
            if("".equals(realname)){//真实姓名不能空
                return false;
            }
            if("company".equals(type)){
                if(!"".equals(idCard)){
                    return true;
                }
            }else {
                if (checkIdCard(idCard)) {//身份证号码
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkIdCard(String idCard){

        if(null == idCard || StringUtils.isBlank(idCard)){
            return false;
        }
            //定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
            Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
            //通过Pattern获得Matcher
            Matcher idNumMatcher = idNumPattern.matcher(idCard);
            //判断用户输入是否为身份证号
            if(idNumMatcher.matches()){
                if(checkIdCardDate(idCard)){//时间判断

                    return true;
                }else{
                    return false;
                }
            }else{
                //如果不是，输出信息提示用户
                return false;
            }
    }

    //判断身份证时间合法性
    private static boolean checkIdCardDate(final String idCard){
        String dateStr = idCard.substring(6,6+8);
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMdd" );
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return false;
        }
    }
	/**
	 * 获得用户ID
	 * 
	 * @param request
	 * @return
	 */
	public static Integer getUserId(HttpServletRequest request) {
		CmsUser user = getUser(request);
		if (user != null) {
			return user.getId();
		} else {
			return null;
		}
	}

	/**
	 * 设置用户
	 * 
	 * @param request
	 * @param user
	 */
	public static void setUser(HttpServletRequest request, CmsUser user) {
		request.setAttribute(USER_KEY, user);
        request.getSession().setAttribute(USER, user);
	}

	/**
	 * 获得站点
	 * 
	 * @param request
	 * @return
	 */
	public static CmsSite getSite(HttpServletRequest request) {
		return (CmsSite) request.getAttribute(SITE_KEY);
	}

	/**
	 * 设置站点
	 * 
	 * @param request
	 * @param site
	 */
	public static void setSite(HttpServletRequest request, CmsSite site) {
		request.setAttribute(SITE_KEY, site);
	}

	/**
	 * 获得站点ID
	 * 
	 * @param request
	 * @return
	 */
	public static Integer getSiteId(HttpServletRequest request) {
		return getSite(request).getId();
	}
}
