package com.credit.SystemManager.controller;

import com.alibaba.fastjson.JSON;
import com.credit.FTLBox.NPTCreditFTLBox;
import com.credit.SystemManager.entity.CreditUser;
import com.credit.SystemManager.service.UserManagerService;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.common.web.session.SessionProvider;
import com.npt.bridge.util.StringUtils;
import com.npt.common.service.FileUploadService;
import com.octo.captcha.service.image.ImageCaptchaService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目： zxcms
 * 作者： owen
 * 时间： 2017/3/2 14:17
 * 描述：
 *
 *      用户管理控制器
 */
@Controller
@RequestMapping(value = "/sys/user", method = RequestMethod.GET)
public class NptCreditUserController {


    private static final Logger log = LoggerFactory
            .getLogger(NptCreditUserController.class);

    @Autowired
    private ImageCaptchaService imageCaptchaService;

    @Autowired
    private SessionProvider session;

    @Autowired
    private UserManagerService userService;
    @Autowired
    private FileUploadService fileUploadService;

    private static String PATH="/r/cms/www/red/upload/";

    //用户注册
    @RequestMapping(value = "/userRegister.do")
    public void userRegister(HttpServletRequest request,
                             HttpServletResponse response,ModelMap modelMap) {
        Map<String,Object> map = new HashMap<String, Object>();
        String loginName = request.getParameter("loginname");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String safeCode = request.getParameter("safecode");
        if (!imageCaptchaService.validateResponseForID(session.getSessionId(request, response), safeCode)) {
            map.put("flag", false);
            map.put("msg", "注册失败！验证码错误。");
        }else {
            CreditUser user = new CreditUser(loginName, password, email);
            List<CreditUser> list = userService.getUserList("loginName", loginName);
            if (list.size() > 0) {//电话名已存在
                map.put("flag", false);
                map.put("msg", "用户名已存在");
            } else {
                boolean registerFlag = userService.userRegister(user);
                if (registerFlag) {
                    map.put("flag", true);
                    map.put("msg", "注册成功！");
                    List<CreditUser> listUser = userService.getUserList("loginName", loginName);
                    if (listUser.size() > 0) {
                        user = listUser.get(0);
                        request.getSession().setAttribute("credit_user", user);
                    }
                } else {
                    map.put("flag", false);
                    map.put("msg", "注册失败！稍后请重新尝试。");
                }
            }
        }
        String s= JSON.toJSONString(map);

        ResponseUtils.renderJson(response,s);
    }


    //获取用户列表
    @RequestMapping(value = "/userList.do",method = {RequestMethod.GET,RequestMethod.POST})
    public String userList(HttpServletRequest request,
                             HttpServletResponse response, ModelMap modelMap) {

        response.setCharacterEncoding("UTF-8");
        CreditUser user=(CreditUser) request.getSession().getAttribute("credit_user");
        //获取查询信息以及分页信息
        String currentPage=request.getParameter("currentPage");
        String prePageNum=request.getParameter("pageNum");
        //分页初始化
        int preNum = 10;
        if(!"".equals(prePageNum) && prePageNum!=null){
            preNum=Integer.parseInt(prePageNum);
        }
        int current=1;
        if(!"".equals(currentPage) && prePageNum!=null){
            current=Integer.parseInt(currentPage);
        }

        String flag=request.getParameter("flag");
        String type=request.getParameter("type");
        String loginName=request.getParameter("loginName");
        String userName=request.getParameter("userName");
        String phone=request.getParameter("phone");
        String idCard=request.getParameter("idCard");

        CreditUser quser=new CreditUser();
        quser.setPhone(phone);
        quser.setFlag(flag);
        quser.setLoginName(loginName);
        quser.setUserName(userName);
        quser.setType(type);
        quser.setIdCard(idCard);
        modelMap.put("quser",quser);


        if(user!=null && "system".equals(user.getType())){
            Pagination page= userService.getUserList(current,preNum,quser);
            modelMap.addAttribute("page",page);
        }

        return NPTCreditFTLBox.FTL_CREDIT_SYSTEM_USER_LIST;
    }

    //禁用用户
    @RequestMapping(value = "/disableUser.do",method = RequestMethod.GET)
    public void disableUser(HttpServletRequest request,
                               HttpServletResponse response, ModelMap modelMap) {

        String id=request.getParameter("id");

        Map<String,Object> map = new HashMap<String, Object>();
        if(!"".equals(id)){
            Long ID = Long.parseLong(id);
            Boolean flag= userService.disableUser(ID);
            if (flag) {
                map.put("flag", true);
                map.put("msg", "禁用用户成功！");
            } else {
                map.put("flag", false);
                map.put("msg", "禁用用户失败！。");
            }
        }else {
            map.put("flag", false);
            map.put("msg", "禁用用户失败！。");
        }

        String s= JSON.toJSONString(map);

        ResponseUtils.renderJson(response,s);
    }

    //启用用户
    @RequestMapping(value = "/enableUser.do",method = RequestMethod.GET)
    public void enableUser(HttpServletRequest request,
                            HttpServletResponse response, ModelMap modelMap) {

        String id=request.getParameter("id");
        Map<String,Object> map = new HashMap<String, Object>();
        if(!"".equals(id)){
            Long ID = Long.parseLong(id);
            Boolean flag= userService.enableUser(ID);
            if (flag) {
                CreditUser user = userService.getUserById(ID);
                map.put("userFlag",user.getFlag());
                map.put("flag", true);
                map.put("msg", "启用用户成功！");
            } else {
                map.put("flag", false);
                map.put("msg", "启用用户失败！");
            }
        }else {
            map.put("flag", false);
            map.put("msg", "启用用户失败！。");
        }

        String s= JSON.toJSONString(map);

        ResponseUtils.renderJson(response,s);
    }


    //用户认证
    @RequestMapping(value = "/userAuthentification.do", method = RequestMethod.POST)
    public void userAuthentification(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(value = "img1", required = false)MultipartFile file1,
                             @RequestParam(value = "img2", required = false)MultipartFile file2,
                             @RequestParam(value = "img3", required = false)MultipartFile file3) {

        response.setCharacterEncoding("UTF-8");
        Map<String,Object> map = new HashMap<String, Object>();
        String idCard = request.getParameter("idCard");
        String usertype = request.getParameter("type");
        String phone  = request.getParameter("phone");
        String msgcode= request.getParameter("msgcode");
        String userName = request.getParameter("userName");
        String safeCode= request.getParameter("safecode");

        if (!imageCaptchaService.validateResponseForID(session.getSessionId(request, response), safeCode)) {
            map.put("flag", false);
            map.put("msg", "注册失败！图片验证码错误。");
        }else {
            CreditUser user = (CreditUser) request.getSession().getAttribute("credit_user");
            user.addAuthentificationInfo(userName, idCard, phone, usertype);

            Boolean flag = userService.checkMessage(msgcode, phone);
            if (flag) {
                List<CreditUser> list = userService.getUserList("phone", phone);
                if (list.size() > 0) {//电话号码已存在
                    map.put("flag", false);
                    map.put("msg", "电话号码已存在");
                } else {
                    List<CreditUser> list1 = userService.getUserList("idCard", idCard);
                    if (list1.size() > 0) {//电话号码已存在
                        map.put("flag", false);
                        map.put("msg", "身份证已存在");
                    } else {
                        Boolean isSave = true;
                        try {
                            String img1 = file1.getOriginalFilename().substring(file1.getOriginalFilename().length() - 4);
                            String fileName1 = fileUploadService.saveFile(request, PATH, file1, idCard + "_1" + img1);
                            if (file2 != null) {
                                String img2 = file2.getOriginalFilename().substring(file2.getOriginalFilename().length() - 4);
                                String fileName2 = fileUploadService.saveFile(request, PATH, file2, idCard + "_2" + img2);
                            }
                            if (file3 != null) {
                                String img3 = file3.getOriginalFilename().substring(file3.getOriginalFilename().length() - 4);
                                String fileName3 = fileUploadService.saveFile(request, PATH, file3, idCard + "_3" + img3);
                            }

                        } catch (IOException e) {
                            isSave = false;
                            e.printStackTrace();
                        }

                        if (isSave == true) {//文件保存失败
                            Boolean registerFlag = userService.userAuthentification(user);
                            if (registerFlag) {
                                map.put("flag", true);
                                map.put("msg", "认证已经提交成功，等待审核！");

                                CreditUser newUser = userService.getUserById(user.getId());
                                request.getSession().setAttribute("credit_user", newUser);

                            } else {
                                map.put("flag", false);
                                map.put("msg", "认证提交失败失败！稍后请重新尝试。");
                            }
                        } else {
                            map.put("flag", false);
                            map.put("msg", "认证提交失败失败！稍后请重新尝试。");
                        }
                    }
                }
            } else {
                map.put("flag", "false");
                map.put("msg", "短信验证码不正确");
            }
        }
        String s= JSON.toJSONString(map);

        ResponseUtils.renderJson(response,s);
    }

    //用户登录
    @RequestMapping(value = "/userLogin.do")
    public void userLogin(HttpServletRequest request,
                          HttpServletResponse response) {
        String password = request.getParameter("password");
        String loginName = request.getParameter("loginname");
        Boolean flag= userService.userLogin(loginName,password);

        Map<String,Object> map = new HashMap<String, Object>();
        if(flag){
            CreditUser user= userService.queryUser(loginName);
            map.put("flag",true);
            map.put("msg","登录成功");
            map.put("user",user);
            request.getSession().setAttribute("credit_user",user);
            request.getSession().removeAttribute("changPwd_Phone");
        }else {
            map.put("flag",false);
            map.put("msg","登录失败");
        }
        String s= JSON.toJSONString(map);

        ResponseUtils.renderJson(response,s);
    }

    //是否登录
    @RequestMapping(value = "/checkLogin.do")
    public void checkLogin(HttpServletRequest request,
                           HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        CreditUser user = (CreditUser) request.getSession().getAttribute("credit_user");
        String s= JSON.toJSONString(user);

        ResponseUtils.renderJson(response,s);
    }


    //退出登录
    @RequestMapping(value = "/userLogout.do")
    public void userLogout(HttpServletRequest request,
                           HttpServletResponse response) {
        request.getSession().removeAttribute("credit_user");
        String s= JSON.toJSONString(true);

        ResponseUtils.renderJson(response,s);
    }

    //修改密码
    @RequestMapping(value = "/changePassword.do")
    public void changePassword(HttpServletRequest request,
                               HttpServletResponse response) {
        CreditUser user = (CreditUser) request.getSession().getAttribute("credit_user");
        String s="";
        if(user==null){
            s = JSON.toJSONString("请登录");
        }else {
            String phone = user.getPhone();
            String pwd = request.getParameter("pwd");
            Boolean flag = userService.changePassword(phone, pwd);
            if(flag==true){
                user.setPassWord(pwd);
                request.getSession().setAttribute("credit_user",user);
            }
             s = JSON.toJSONString(flag);
        }
        ResponseUtils.renderJson(response,s);
    }

    //修改电话号码
    @RequestMapping(value = "/changePhone.do")
    public void changePhone(HttpServletRequest request,
                            HttpServletResponse response) {
        CreditUser user = (CreditUser) request.getSession().getAttribute("credit_user");
        String yzm = request.getParameter("yzm");
        String phone = request.getParameter("phone");
        String oldphone = request.getParameter("oldphone");//用户天下的旧电话号码

        String s = "";
        List<CreditUser> list=userService.getUserList("phone", phone);
        if(list.size()>0) {//电话号码已存在
            s = JSON.toJSONString("您输入的新电话号码已存在");
        }else {
            if (user == null) {
                s = JSON.toJSONString("请登录");
            } else {
                Boolean result = userService.checkMessage(yzm, phone);
                if (result) {
                    String oldphone2 = user.getPhone();//数据库实际电话
                    if(oldphone2.equals(oldphone)){
                        Boolean flag = userService.changePhoneByPhone(oldphone, phone);
                        if(flag==true){
                            user.setPhone(phone);
                            request.getSession().setAttribute("credit_user",user);
                        }
                        s = JSON.toJSONString(flag);
                    }else {
                        s = JSON.toJSONString("您输入的旧电话不正确");
                    }

                } else {
                    s = JSON.toJSONString("验证码错误请重试！");
                }
            }
        }
        ResponseUtils.renderJson(response,s);
    }


    //发送短信验证码
    @RequestMapping(value = "/sendMessage.do")
    public void sendMessage(HttpServletRequest request,
                            HttpServletResponse response) {

        String phone =  StringUtils.getStr(request.getSession().getAttribute("changPwd_Phone"));
        String s= JSON.toJSONString("ok");

        ResponseUtils.renderJson(response,s);
    }



    // 校验短信验证码
    @RequestMapping(value = "/checkMessage.do")
    public void checkMessage(HttpServletRequest request,
                             HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        String jym = request.getParameter("jym");
        String phone =  StringUtils.getStr(request.getSession().getAttribute("changPwd_Phone"));
        Boolean flag = userService.checkMessage(jym,phone);
        String s= JSON.toJSONString(flag);
        ResponseUtils.renderJson(response,s);
    }

    // 修改密码
    @RequestMapping(value = "/getBackPassword.do")
    public void getBackPassword(HttpServletRequest request,
                                HttpServletResponse response) {
        String password = request.getParameter("pwd");
        String phone =  StringUtils.getStr(request.getSession().getAttribute("changPwd_Phone"));
        Boolean flag=false;
        if(!"".equals(phone)){
            flag=userService.changePassword(phone,password);
        }

        String s= JSON.toJSONString(flag);

        ResponseUtils.renderJson(response,s);
    }

    // 用户名或者电话核对
    @RequestMapping(value = "/checkUser.do")
    public void checkUser(HttpServletRequest request,
                          HttpServletResponse response) {
        String type = request.getParameter("type");
        String value = request.getParameter("value");
        List<CreditUser> list=userService.getUserList(type,value);
        CreditUser user=null;
        if(list.size()>0){
            user=list.get(0);
            request.getSession().setAttribute("changPwd_Phone", user.getPhone());
            String phone =user.getPhone();
            user.setPhone(phone.substring(0,4)+"****"+phone.substring(8,11));
        }
        String s= JSON.toJSONString(user);

        ResponseUtils.renderJson(response,s);
    }

    //校验用户是否登录
    public static Boolean checkUserIsLogin(HttpServletRequest request){

        CreditUser user = (CreditUser) request.getSession().getAttribute("credit_user");
        if(user==null){
            return false;
        }
        return true;
    }

    //是否认证成功
    public static Boolean checkUserIsAuthentification(HttpServletRequest request){

        CreditUser user = (CreditUser) request.getSession().getAttribute("credit_user");
        if(user==null){
            return false;
        }else {
            if("3".equals(user.getFlag())){
                return true;
            }
        }

        return false;
    }
}
