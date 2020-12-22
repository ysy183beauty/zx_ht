package com.npt.msg.service;

/**
 * 项目： NPTWebApp
 * 作者： owen
 * 时间： 2017/6/12 16:13
 * 描述：
 */
public interface NptMsgChannelFactory {

    enum MSG_CHANNEL_TYPE{
        //短信
        SM,
        //邮件
        MAIL,
        //微信
        WECHAT
    }


    /**
     *作者：owen
     *时间: 2017/6/12 16:18
     *描述:
     *      依据消息渠道创建消息渠道实体
     */
    NptMsgChannel createChannel(MSG_CHANNEL_TYPE type);
}
