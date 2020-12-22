package com.npt.bridge.model.manager.impl;

import com.npt.bridge.dict.NptBusinessCode;
import com.npt.bridge.model.dao.NptBusinessCodeDao;
import com.npt.bridge.model.manager.NptBusinessCodeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/3/28
 * 备注:
 */
@Service
public class NptBusinessCodeManagerImpl implements NptBusinessCodeManager {

    @Autowired
    private NptBusinessCodeDao codeTableDao;

    @Override
    public NptBusinessCode getCode(String codeValue, Long fieldId) {
        return codeTableDao.getCode(codeValue,fieldId);
    }

    @Override
    public Collection<NptBusinessCode> listCode(Long fieldId) {
        return codeTableDao.listCode(fieldId);
    }

}
