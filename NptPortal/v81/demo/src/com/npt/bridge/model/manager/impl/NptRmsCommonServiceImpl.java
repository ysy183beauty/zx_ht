package com.npt.bridge.model.manager.impl;

import com.npt.bridge.dict.NptBusinessCode;
import com.npt.bridge.model.manager.NptBusinessCodeManager;
import com.npt.bridge.model.manager.NptRmsCommonService;
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
public class NptRmsCommonServiceImpl implements NptRmsCommonService {

    @Autowired
    private NptBusinessCodeManager businessCodeManager;

    /**
     * 作者：OWEN
     * 时间：2016/11/28 17:02
     * 描述:
     * 通过码类型获取码集合
     *
     * @param codeType
     */
    @Override
    public Collection<NptBusinessCode> getBusinessCodeByType(Long codeType) {
        return businessCodeManager.listCode(codeType);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/28 17:03
     * 描述:
     * 获取某一类型下的指定码值的码信息
     *
     * @param codeValue
     * @param codeType
     */
    @Override
    public NptBusinessCode getBusinessCode(String codeValue, Long codeType) {
        return businessCodeManager.getCode(codeValue,codeType);
    }

}
