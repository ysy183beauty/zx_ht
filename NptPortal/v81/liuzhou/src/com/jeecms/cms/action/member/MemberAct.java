package com.jeecms.cms.action.member;

import com.alibaba.fastjson.JSON;
import com.jeecms.cms.entity.assist.CmsWebservice;
import com.jeecms.cms.manager.assist.CmsWebserviceMng;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.core.entity.*;
import com.jeecms.core.manager.CmsUserAccountMng;
import com.jeecms.core.manager.CmsUserExtMng;
import com.jeecms.core.manager.CmsUserMng;
import com.jeecms.core.web.WebErrors;
import com.jeecms.core.web.util.CmsUtils;
import com.jeecms.core.web.util.FrontUtils;
import com.npt.common.service.FileUploadService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.jeecms.cms.Constants.TPLDIR_ALONE;
import static com.jeecms.cms.Constants.TPLDIR_MEMBER;

/**
 * 会员中心Action
 */
@Controller
public class MemberAct {
	private static final Logger log = LoggerFactory.getLogger(MemberAct.class);

	public static final String MEMBER_CENTER = "tpl.memberCenter";
	public static final String MEMBER_PROFILE = "tpl.memberProfile";
	public static final String MEMBER_PORTRAIT = "tpl.memberPortrait";
	public static final String MEMBER_PASSWORD = "tpl.memberPassword";
    public static final String MEMBER_PHONE = "tpl.memberMobile";
    public static final String MEMBER_INFO = "tpl.memberInfo";
	public static final String MEMBER_ACCOUNT = "tpl.memberAccount";
    public static final String PATH="/r/cms/www/red/upload/";
    public static final String IMG_PATH_SPLIT = ",";
	
	/**
	 * 会员中心页
	 * 
	 * 如果没有登录则跳转到登陆页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/index.jspx", method = RequestMethod.GET)
	public String index(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_ALONE, MEMBER_CENTER);
	}


	/**
	 * 更换头像
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/portrait.jspx", method = RequestMethod.GET)
	public String portrait(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, MEMBER_PORTRAIT);
	}

    /**
     * 个人资料输入页
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/profile.jspx", method = RequestMethod.GET)
    public String profileInput(HttpServletRequest request,
                               HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }
        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }
        CmsUserExt ext=cmsUserExtMng.findById(user.getId());
        model.addAttribute("userExt",ext);

        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_ALONE, MEMBER_PROFILE);
    }

	/**
	 * 个人资料提交页
     * 现在改为实名认证
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/member/profile.jspx", method = RequestMethod.POST)
	public String profileSubmit(CmsUserExt ext, String nextUrl,String captcha,
			HttpServletRequest request, HttpServletResponse response, ModelMap model,
            @RequestParam(value = "idCardImg1", required = false)MultipartFile file1,
            @RequestParam(value = "idCardImg2", required = false)MultipartFile file2,
            @RequestParam(value = "idCardImg3", required = false)MultipartFile file3,
            @RequestParam(value = "idCardImg4", required = false)MultipartFile file4) throws IOException {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
        CmsUserExt cmsExt = cmsUserExtMng.findById(user.getId());
        String idCard = ext.getIdCard();
        String mobile = ext.getMobile();
		if("4".equals(cmsExt.getFlag())) {//注册失败后再次提交数据时校验

            if (user.getType().equals("person") && !cmsUserExtMng.mobileExist(mobile, user.getId())) {
                model.addAttribute("addSuccess",false);
                model.addAttribute("msg","电话号码已存在");
                model.addAttribute("userExt",ext);

                return FrontUtils.getTplPath(request, site.getSolutionPath(),
                        TPLDIR_ALONE, MEMBER_PROFILE);
            }

            if (!cmsUserExtMng.idCardExist(idCard,user.getId())) {
                model.addAttribute("addSuccess",false);
                model.addAttribute("userExt",ext);

                if("company".equals(ext.getType())){
                    model.addAttribute("msg","工商注册号已存在");
                }else{
                    model.addAttribute("msg","身份证号码已存在");
                }

                return FrontUtils.getTplPath(request, site.getSolutionPath(),
                        TPLDIR_ALONE, MEMBER_PROFILE);
            }
        }else{

            if (user.getType().equals("person") && !cmsUserExtMng.mobileExist(mobile)) {
                model.addAttribute("addSuccess",false);
                model.addAttribute("msg","电话号码已存在");
                model.addAttribute("userExt",ext);

                return FrontUtils.getTplPath(request, site.getSolutionPath(),
                        TPLDIR_ALONE, MEMBER_PROFILE);
            }

            if (!cmsUserExtMng.idCardExist(idCard)) {
                model.addAttribute("addSuccess",false);
                model.addAttribute("userExt",ext);

                if("company".equals(ext.getType())){
                    model.addAttribute("msg","工商注册号已存在");
                }else{
                    model.addAttribute("msg","身份证号码已存在");
                }

                return FrontUtils.getTplPath(request, site.getSolutionPath(),
                        TPLDIR_ALONE, MEMBER_PROFILE);
            }
        }
		String type=ext.getType();
		String imgNames="";
		//保存图片
        Boolean isSave = true;
        try {
            if(!file1.isEmpty()) {
                String img1 = file1.getOriginalFilename().substring(file1.getOriginalFilename().length() - 4);
                 fileUploadService.saveFile(request, PATH, file1, idCard + "_1" + img1);
                imgNames = "_1" + img1;
            }
            if (!file2.isEmpty()) {
                String img2 = file2.getOriginalFilename().substring(file2.getOriginalFilename().length() - 4);
                fileUploadService.saveFile(request, PATH, file2, idCard + "_2" + img2);
                imgNames=imgNames+IMG_PATH_SPLIT+"_2" + img2;
            }
            if (!file3.isEmpty()) {
                String img3 = file3.getOriginalFilename().substring(file3.getOriginalFilename().length() - 4);
                fileUploadService.saveFile(request, PATH, file3, idCard + "_3" + img3);
                imgNames=imgNames+IMG_PATH_SPLIT+"_3" + img3;
            }
            if("company".equals(type)) {
                if (!file4.isEmpty()) {
                    String img4 = file4.getOriginalFilename().substring(file4.getOriginalFilename().length() - 4);
                    fileUploadService.saveFile(request, PATH, file4, idCard + "_4" + img4);
                    imgNames=imgNames+IMG_PATH_SPLIT+"_4" + img4;
                }
            }

        } catch (Exception e) {
            isSave = false;
            e.printStackTrace();
        }

		if(isSave){//保存用户
            ext.setId(user.getId());
            ext.setFlag("2");//提交之后修改状态
            if(!"".equals(imgNames)){
                ext.setIdCardImg(imgNames);
            }else {
                ext.setIdCardImg(cmsExt.getIdCardImg());
            }

            model.addAttribute("userExt",ext);
             ext=cmsUserExtMng.updateUser(ext, user);
            cmsWebserviceMng.callWebService("false",user.getUsername(), null,
                    null, ext,CmsWebservice.SERVICE_TYPE_UPDATE_USER);
            log.info("update CmsUserExt success. id={}", user.getId());
            if(ext==null){
                model.addAttribute("addSuccess",false);
                model.addAttribute("msg","认证数据提交失败");
                ext.setFlag("1");
                model.addAttribute("userExt",ext);
            }else{
                model.addAttribute("userExt",ext);
                model.addAttribute("addSuccess",true);
                model.addAttribute("msg","认证数据提交成功");
                CmsUtils.addSesisonUser(request,ext.getUser());
            }
        }else{
		    model.addAttribute("addSuccess",isSave);
            model.addAttribute("msg","认证数据提交失败");
            ext.setIdCardImg(cmsExt.getIdCardImg());
            ext.setType(cmsExt.getType());
            model.addAttribute("userExt",ext);
        }

		return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_ALONE, MEMBER_PROFILE);
	}


    /**
     * 作者: 张磊
     * 日期: 2017/03/31 上午10:41
     * 备注: 检查手机号等是否已存在
     */
    @RequestMapping(value = "/member/check.jspx", method = RequestMethod.POST)
    public void profileCheck(CmsUserExt ext, String nextUrl, String captcha,
                             HttpServletRequest request, HttpServletResponse response, ModelMap model,
                             @RequestParam(value = "idCardImg1", required = false) MultipartFile file1,
                             @RequestParam(value = "idCardImg2", required = false) MultipartFile file2,
                             @RequestParam(value = "idCardImg3", required = false) MultipartFile file3,
                             @RequestParam(value = "idCardImg4", required = false) MultipartFile file4) throws IOException {
        Boolean success = true;
        String msg = "";
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);
        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            success = false;
            msg = "没有开启注册功能";
        }
        if (user == null) {
            success = false;
            msg = "没有登录";
        } else {
            CmsUserExt cmsExt = cmsUserExtMng.findById(user.getId());
            String idCard = ext.getIdCard();
            if(idCard!=null&&!idCard.isEmpty()) {
                if ("4".equals(cmsExt.getFlag())) {//注册失败后再次提交数据时校验
                    if (!cmsUserExtMng.idCardExist(idCard, user.getId())) {
                        success = false;
                        if ("company".equals(user.getType())) {
                            msg = "工商注册号已存在";
                        } else {
                            msg = "身份证号码已存在";
                        }
                    }
                } else {
                    if (!cmsUserExtMng.idCardExist(idCard)) {
                        success = false;
                        if ("company".equals(ext.getType())) {
                            msg = "工商注册号已存在";
                        } else {
                            msg = "身份证号码已存在";
                        }
                    }
                }
            }
            String mobile = ext.getMobile();
            if(mobile!=null&&!mobile.isEmpty()) {
                if ("4".equals(cmsExt.getFlag())) {//注册失败后再次提交数据时校验
                    if (user.getType().equals("person") && !cmsUserExtMng.mobileExist(mobile, user.getId())) {
                        success = false;
                        msg = "电话号码已存在";
                    }
                } else {
                    if (user.getType().equals("person") && !cmsUserExtMng.mobileExist(mobile)) {
                        success = false;
                        msg = "电话号码已存在";
                    }
                }
            }
            String realname = ext.getRealname();
            if (realname != null && !realname.isEmpty()) {
                if ("4".equals(cmsExt.getFlag())) {//注册失败后再次提交数据时校验
                    if (user.getType().equals("company") && !cmsUserExtMng.realNameExist(realname, user.getId())) {
                        success = false;
                        msg = "企业名称已存在";
                    }
                } else {
                    if (user.getType().equals("company") && !cmsUserExtMng.realNameExist(realname)) {
                        success = false;
                        msg = "企业名称已存在";
                    }
                }
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("msg", msg);
        ResponseUtils.renderJson(response, JSON.toJSONString(result));
    }

    /**
     * 手机号码修改输入页
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/changeMobile.jspx", method = RequestMethod.GET)
    public String getChangeMobile(HttpServletRequest request,
                                HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);


        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }


       CmsUserExt ext= cmsUserExtMng.findById(user.getId());

       model.addAttribute("oldmobile",ext.getMobile());

        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_ALONE, MEMBER_PHONE);
    }

    /**
     * 手机号码修改输入页
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/changeMobile.jspx", method = RequestMethod.POST)
    public String changeMobile(HttpServletRequest request,String mobile,String oldmobile,
                               HttpServletResponse response, ModelMap model,String msgcode) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);


        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }

        CmsUserExt ext= cmsUserExtMng.findById(user.getId());
        if (ext.getMobile() != null && !ext.getMobile().equals(oldmobile)) {//旧手机号码不正确
            model.addAttribute("message","旧手机号码不正确");
            model.addAttribute("mobile",mobile);
            model.addAttribute("oldmobile",oldmobile);
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_ALONE, MEMBER_PHONE);
        }

        if(!cmsUserExtMng.mobileExist(mobile)){//校验新手机号码不存在
            model.addAttribute("message","新手机号码已存在");
            model.addAttribute("mobile",mobile);
            model.addAttribute("oldmobile",oldmobile);
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_ALONE, MEMBER_PHONE);
        }

        boolean isupdate = cmsUserExtMng.changeMobile(mobile,user.getId());

        if(isupdate){
            model.addAttribute("message","修改成功");
            CmsUtils.addSesisonUser(request,user);
        }else{
            model.addAttribute("message","修改失败");
        }

        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_ALONE, MEMBER_PHONE);
    }

    /**
     * 个人信息首页
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/member/userInfo.jspx", method = RequestMethod.GET)
    public String userInfo(HttpServletRequest request,
                                  HttpServletResponse response, ModelMap model) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        FrontUtils.frontData(request, model, site);

        MemberConfig mcfg = site.getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return FrontUtils.showMessage(request, model, "member.memberClose");
        }

        if (user == null) {
            return FrontUtils.showLogin(request, model, site);
        }

        CmsUserExt ext= cmsUserExtMng.findById(user.getId());

        model.addAttribute("ext",ext);
        model.addAttribute("user",user);

        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_ALONE, MEMBER_INFO);
    }

	/**
	 * 密码修改输入页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/pwd.jspx", method = RequestMethod.GET)
	public String passwordInput(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_ALONE, MEMBER_PASSWORD);
	}

	/**
	 * 密码修改提交页
	 * 
	 * @param origPwd
	 *            原始密码
	 * @param newPwd
	 *            新密码
	 * @param email
	 *            邮箱
	 * @param nextUrl
	 *            下一个页面地址
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/member/pwd.jspx", method = RequestMethod.POST)
	public String passwordSubmit(String origPwd, String newPwd, String email,
			String nextUrl, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IOException {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);

		if(email==null || "".equals(email)){
		    email=user.getEmail();
        }
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		WebErrors errors = validatePasswordSubmit(user.getId(), origPwd,
				newPwd, email, request);

		if (errors.hasErrors()) {
            errors.toModel(model);
            return FrontUtils.getTplPath(request, site.getSolutionPath(),
                    TPLDIR_ALONE, MEMBER_PASSWORD);
		}
		cmsUserMng.updatePwdEmail(user.getId(), newPwd, email);
		cmsWebserviceMng.callWebService("false",user.getUsername(), newPwd,
				email, null,CmsWebservice.SERVICE_TYPE_UPDATE_USER);
		model.addAttribute("msg","修改成功");
		//添加session
        CmsUtils.addSesisonUser(request,cmsUserMng.findById(user.getId()));

        return FrontUtils.getTplPath(request, site.getSolutionPath(),
                TPLDIR_ALONE, MEMBER_PASSWORD);
	}

    @RequestMapping(value = "/authSuccess.jspx", method = RequestMethod.POST)
    public void userAuthentificationSuccess(HttpServletRequest request,
                             HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String id = RequestUtils.getQueryParam(request, "id");
        String msg = RequestUtils.getQueryParam(request, "msg");
        // 电话为空，返回false。
        if (StringUtils.isBlank(id)) {
            ResponseUtils.renderJson(response, "false");
            return;
        }
        int uId=Integer.parseInt(id);

       boolean flag=cmsUserExtMng.changeflag(uId,"3",msg);//成功


        ResponseUtils.renderJson(response, String.valueOf(flag));
    }

    @RequestMapping(value = "/authfail.jspx", method = RequestMethod.POST)
    public void userAuthentificationfail(HttpServletRequest request,
                                            HttpServletResponse response) {
        String id = RequestUtils.getQueryParam(request, "id");
        String msg = RequestUtils.getQueryParam(request, "msg");
        // 电话为空，返回false。
        if (StringUtils.isBlank(id)) {
            ResponseUtils.renderJson(response, "false");
            return;
        }
        int uId=Integer.parseInt(id);

        boolean flag=cmsUserExtMng.changeflag(uId,"4",msg);//失败


        ResponseUtils.renderJson(response, String.valueOf(flag));
    }


    @RequestMapping(value = "/mobile_unique.jspx")
    public void mobileUnique(HttpServletRequest request,
                               HttpServletResponse response) {
        String mobile = RequestUtils.getQueryParam(request, "mobile");
        // 电话为空，返回false。
        if (StringUtils.isBlank(mobile)) {
            ResponseUtils.renderJson(response, "false");
            return;
        }
        CmsSite site = CmsUtils.getSite(request);
        CmsConfig config = site.getConfig();

        // 电话存在，返回false。
        if (cmsUserExtMng.mobileExist(mobile)) {
            ResponseUtils.renderJson(response, "false");
            return;
        }
        ResponseUtils.renderJson(response, "true");
    }

    @RequestMapping(value = "/idcard_unique.jspx")
    public void idCardUnique(HttpServletRequest request,
                             HttpServletResponse response) {
        String idCard = RequestUtils.getQueryParam(request, "idCard");
        // 空，返回false。
        if (StringUtils.isBlank(idCard)) {
            ResponseUtils.renderJson(response, "false");
            return;
        }
        CmsSite site = CmsUtils.getSite(request);
        CmsConfig config = site.getConfig();

        // 工商注册号已存在，返回false。
        if (cmsUserExtMng.idCardExist(idCard)) {
            ResponseUtils.renderJson(response, "false");
            return;
        }
        ResponseUtils.renderJson(response, "true");
    }

	
	/**
	 * 完善账户资料
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/account.jspx", method = RequestMethod.GET)
	public String accountInput(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, MEMBER_ACCOUNT);
	}
	
	//完善用户账户资料
	@RequestMapping(value = "/member/account.jspx", method = RequestMethod.POST)
	public String accountSubmit(String accountWeiXin,String  accountAlipy,
			Short drawAccount,String nextUrl,HttpServletRequest request, 
			HttpServletResponse response,ModelMap model) throws IOException {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}

		WebErrors errors=WebErrors.create(request);
		if(drawAccount==null)
		{
			errors.addErrorCode("error.needParams");
		}else{
			if(!(drawAccount==0&&StringUtils.isNotBlank(accountWeiXin)
					||drawAccount==1&&StringUtils.isNotBlank(accountAlipy))){
				errors.addErrorCode("error.needParams");
			}
		}
		if(errors.hasErrors()){
			return FrontUtils.showError(request, response, model, errors);
		}
		cmsUserAccountMng.updateAccountInfo(accountWeiXin, accountAlipy, drawAccount,user);
		log.info("update CmsUserExt success. id={}", user.getId());
		return FrontUtils.showSuccess(request, model, nextUrl);
	}
	
	

	/**
	 * 验证密码是否正确
	 * 
	 * @param origPwd
	 *            原密码
	 * @param request
	 * @param response
	 */
	@RequestMapping("/member/checkPwd.jspx")
	public void checkPwd(String origPwd, HttpServletRequest request,
			HttpServletResponse response) {
		CmsUser user = CmsUtils.getUser(request);
		boolean pass = cmsUserMng.isPasswordValid(user.getId(), origPwd);
		ResponseUtils.renderJson(response, pass ? "true" : "false");
	}

	private WebErrors validatePasswordSubmit(Integer id, String origPwd,
			String newPwd, String email, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifBlank(origPwd, "origPwd", 100)) {
			return errors;
		}
		if (errors.ifMaxLength(newPwd, "newPwd", 100)) {
			return errors;
		}
		if (!cmsUserMng.isPasswordValid(id, origPwd)) {
			errors.addErrorCode("member.origPwdInvalid");
			return errors;
		}
		return errors;
	}

	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private CmsUserExtMng cmsUserExtMng;
	@Autowired
	private CmsUserAccountMng cmsUserAccountMng;
	@Autowired
	private CmsWebserviceMng cmsWebserviceMng;
    @Autowired
    private FileUploadService fileUploadService;

}
