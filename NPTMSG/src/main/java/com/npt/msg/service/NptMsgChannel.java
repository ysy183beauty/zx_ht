package com.npt.msg.service;

import com.npt.bridge.dict.NptDict;
import com.npt.msg.entity.NptMsgEntity;

/**
 * 项目： NPTWebApp
 * 作者： owen
 * 时间： 2017/6/6 15:49
 * 描述：
 */
public interface NptMsgChannel{

    NptDict sentMsg(NptMsgEntity msg);
}
