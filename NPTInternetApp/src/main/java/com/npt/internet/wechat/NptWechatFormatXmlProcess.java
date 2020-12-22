package com.npt.internet.wechat;

import java.util.Date;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/24 16:56
 * 描述:
 */
public class NptWechatFormatXmlProcess {
    public String formatXmlAnswer(String to, String from, String content) {
        StringBuffer sb = new StringBuffer();
        Date date = new Date();
        sb.append("<xml><ToUserName><![CDATA[");
        sb.append(to);
        sb.append("]]></ToUserName><FromUserName><![CDATA[");
        sb.append(from);
        sb.append("]]></FromUserName><CreateTime>");
        sb.append(date.getTime());
        sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
        sb.append(content);
        sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");
        return sb.toString();
    }
}
