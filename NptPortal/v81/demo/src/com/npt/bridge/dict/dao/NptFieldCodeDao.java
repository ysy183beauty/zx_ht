package com.npt.bridge.dict.dao;

import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.dict.NptBusinessCode;

import java.util.Collection;

/**
 * Created by owen on 2017/3/21.
 */
public interface NptFieldCodeDao extends NptBaseDao<NptBusinessCode,Long>{

    NptBusinessCode getCode(Long fieldId,String codeValue);

    Collection<NptBusinessCode> listAll();
}
