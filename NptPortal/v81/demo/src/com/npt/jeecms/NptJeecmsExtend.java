package com.npt.jeecms;

import com.google.zxing.BarcodeFormat;
import com.jeecms.common.util.ZXingCode;
import com.jeecms.common.util.ZxingLogoConfig;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.common.web.springmvc.RealPathResolver;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

/**
 * 项目: demo
 * 作者: zhanglei
 * 日期: 2017/5/31
 * 备注:
 */
@Controller
@RequestMapping(value = "/nptcms")
public class NptJeecmsExtend {

    @Autowired
    private RealPathResolver realPathResolver;

    @RequestMapping(value = "/v_create_dimensioncode.do", method = RequestMethod.POST)
    public void createCodeImg(String content,String logoPicPath
            ,String logoWord,Integer fontSize,Integer size,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        JSONObject json=new JSONObject();
        if(StringUtils.isNotBlank(content)){
//            CmsSite site= CmsUtils.getSite(request);
            if(size==null){
                size=100;
            }
            if(fontSize==null){
                fontSize=10;
            }
            String logoPic=null;
            if(StringUtils.isNotBlank(logoPicPath)){
//                if(StringUtils.isNotBlank(site.getContextPath())
//                        &&logoPicPath.startsWith(site.getContextPath())){
//                    logoPicPath=logoPicPath.substring(site.getContextPath().length());
//                }
                logoPic=realPathResolver.get(logoPicPath);
            }
            response.setContentType("image/png; charset=utf-8");
            try {

                ZXingCode zp = ZXingCode.getInstance();

                BufferedImage bim = zp.getQRCODEBufferedImage(content, BarcodeFormat.QR_CODE, size, size,
                        zp.getDecodeHintType());
                if(StringUtils.isNotBlank(logoPicPath)){
                    zp.addLogoQRCode(bim, new File(logoPic), new ZxingLogoConfig());
                }else if(StringUtils.isNotBlank(logoWord)){
                    zp.addLogoWordQRCode(bim, logoWord, fontSize,new ZxingLogoConfig());
                }
                String tempFileName = UUID.randomUUID().toString().replace("-", "") + ".png";
                File file=new File(realPathResolver.get(tempFileName));
                ImageIO.write(bim, "png", file);
//                if(StringUtils.isNotBlank(site.getContextPath())){
//                    tempFileName=site.getContextPath()+tempFileName;
//                }
                json.put("url", tempFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ResponseUtils.renderJson(response, json.toString());
    }

}
