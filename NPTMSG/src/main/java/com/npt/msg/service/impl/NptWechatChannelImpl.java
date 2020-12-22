package com.npt.msg.service.impl;

import com.npt.msg.service.NptWechatChannel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 项目： NPTWebApp
 * 作者： owen
 * 时间： 2017/6/6 16:05
 * 描述：
 */
public class NptWechatChannelImpl extends NptMsgOSBase implements NptWechatChannel{

    @Autowired
    private NptWechatChannel wechatChannel;
}
