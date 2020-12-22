package com.npt.bridge.base;

import java.io.Serializable;

/**
 * 项目： NPTWebApp
 * 作者： owen
 * 时间： 2017/6/13 10:19
 * 描述：
 */
public class NptEntitySerializableNoID extends NptBaseEntityNoID implements Serializable {
    @Override
    public void copyTo(NptBaseEntity entity) {
        super.copyTo(entity);
    }
}
