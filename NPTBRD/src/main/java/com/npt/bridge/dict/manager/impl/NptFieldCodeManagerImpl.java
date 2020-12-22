package com.npt.bridge.dict.manager.impl;

import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.base.manager.NptBaseManagerImpl;
import com.npt.bridge.dict.NptBusinessCode;
import com.npt.bridge.dict.dao.NptFieldCodeDao;
import com.npt.bridge.dict.manager.NptFieldCodeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by owen on 2017/3/21.
 */
@Service
@Transactional
public class NptFieldCodeManagerImpl extends NptBaseManagerImpl<NptBusinessCode,Long> implements NptFieldCodeManager{

    @Autowired
    private NptFieldCodeDao codeDao;

    /**
     * 作者：OWEN
     * 时间：2016/11/28 17:27
     * 描述:
     * 通过码类型与码值获取唯一的码信息
     *
     * @param codeValue
     * @param fieldId
     */
    @Override
    public NptBusinessCode getCode(String codeValue, Long fieldId) {
        return codeDao.getCode(fieldId,codeValue);
    }

    @Override
    public Collection<NptBusinessCode> listAll() {
        return codeDao.listAll();
    }

    @Override
    public NptBaseDao<NptBusinessCode, Long> getThisDao() {
        return codeDao;
    }
}
