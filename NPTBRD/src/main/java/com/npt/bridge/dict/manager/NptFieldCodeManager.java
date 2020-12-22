package com.npt.bridge.dict.manager;

import com.npt.bridge.base.NptBaseManager;
import com.npt.bridge.dict.NptBusinessCode;

import java.util.Collection;

/**
 * Created by owen on 2017/3/21.
 */
public interface NptFieldCodeManager extends NptBaseManager<NptBusinessCode,Long>{

    /**
     *作者：OWEN
     *时间：2016/11/28 17:27
     *描述:
     *      通过码类型与码值获取唯一的码信息
     */
    NptBusinessCode getCode(String codeValue, Long fieldId);


    Collection<NptBusinessCode> listAll();
}
