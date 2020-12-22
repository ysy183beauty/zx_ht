package com.npt.common.service.impl;

import com.jeecms.common.web.session.SessionProvider;
import com.npt.bridge.dict.NptDict;
import com.npt.common.service.SecurityService;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private ImageCaptchaService imageCaptchaService;
    @Autowired
    private SessionProvider session;

    @Override
    public NptDict classicSearchValidate(String keyword,
                                         String captcha,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {

        try {
            if (!imageCaptchaService.validateResponseForID(session.getSessionId(request, response), captcha)) {
                return NptDict.RST_EXCEPTION("验证码错误");
            }
            if(StringUtils.isBlank(keyword)){
                return NptDict.RST_EXCEPTION("关键字不能为空");
            }
        } catch (CaptchaServiceException e) {
            return NptDict.RST_ERROR;
        }

        return NptDict.RST_SUCCESS;
    }
}
