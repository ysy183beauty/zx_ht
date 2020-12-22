package com.npt.bridge.model.dao;

import com.npt.bridge.dict.NptBusinessCode;

import java.util.Collection;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/3/28
 * 备注:
 */
public interface NptBusinessCodeDao {
    /**
     *作者：OWEN
     *时间：2016/11/28 17:27
     *描述:
     *      通过码类型与码值获取唯一的码信息
     */
    NptBusinessCode getCode(String codeValue, Long fieldId);

    /**
     *作者：OWEN
     *时间：2016/11/28 17:28
     *描述:
     *      加载指定码类型的所有码信息
     */
    Collection<NptBusinessCode> listCode(Long fieldId);

}
