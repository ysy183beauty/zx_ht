package com.npt.msg.service.impl;

import com.npt.msg.service.NptMailChannel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 项目： NPTWebApp
 * 作者： owen
 * 时间： 2017/6/6 16:04
 * 描述：
 */
public class NptMailChannelImpl extends NptMsgOSBase implements NptMailChannel{

    @Autowired
    private NptMailChannel mailChannel;
}
