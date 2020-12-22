package com.npt.bridge.model.manager;

import com.npt.bridge.dict.NptBusinessCode;

import java.util.Collection;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/3/28
 * 备注:
 */
public interface NptRmsCommonService {
    /**
     *作者：OWEN
     *时间：2016/11/28 17:02
     *描述:
     *      通过码类型获取码集合
     */
    Collection<NptBusinessCode> getBusinessCodeByType(Long codeType);

    /**
     *作者：OWEN
     *时间：2016/11/28 17:03
     *描述:
     *      获取某一类型下的指定码值的码信息
     */
    NptBusinessCode getBusinessCode(String codeValue,Long codeType);

}
