package com.npt.msg.service.impl;

import com.npt.msg.service.NptMsgChannel;
import com.npt.msg.service.NptMsgChannelFactory;

/**
 * 项目： NPTWebApp
 * 作者： owen
 * 时间： 2017/6/12 16:20
 * 描述：
 */
public class NptMsgChannelFactoryBase implements NptMsgChannelFactory{
    /**
     * 作者：owen
     * 时间: 2017/6/12 16:18
     * 描述:
     * 依据消息渠道创建消息渠道实体
     *
     * @param type
     */
    public NptMsgChannel createChannel(MSG_CHANNEL_TYPE type) {

        if(type == MSG_CHANNEL_TYPE.SM){

        }else if(type == MSG_CHANNEL_TYPE.MAIL){

        }else if(type == MSG_CHANNEL_TYPE.WECHAT){

        }else {

        }

        return null;
    }
}
