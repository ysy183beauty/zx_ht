package com.npt.bridge.base;


import java.io.Serializable;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/26 12:30
 * 备注：
 *      可序列化的数据实体基类
 */
public class NptEntitySerializable extends NptBaseEntity implements Serializable{
    @Override
    public void copyTo(NptBaseEntity entity) {
        super.copyTo(entity);
    }
}
