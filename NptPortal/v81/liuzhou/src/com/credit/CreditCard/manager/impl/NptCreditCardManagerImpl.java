package com.credit.CreditCard.manager.impl;

import com.credit.CreditCard.dao.NptCreditCardDao;
import com.credit.CreditCard.entity.NptCreditCard;
import com.credit.CreditCard.manager.NptCreditCardManager;
import com.credit.CreditCard.user.NptCreditCardUser;
import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.base.manager.NptBaseManagerImpl;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.service.NptBaseModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 项目： NPTWebApp
 * 作者： zhanglei
 * 时间： 2017/06/13 11:53
 * 描述：
 */
@Service
@Transactional
public class NptCreditCardManagerImpl extends NptBaseManagerImpl<NptCreditCard, Integer> implements NptCreditCardManager {

    @Autowired
    private NptCreditCardDao dao;
    @Autowired
    private NptBaseModelService baseModelService;

    @Override
    public NptBaseDao<NptCreditCard, Integer> getThisDao() {
        return dao;
    }

    @Override
    public NptDict newCard(NptCreditCardUser user) {
        if (user == null) {
            return NptDict.RST_INVALID_PARAMS;
        }
        try {
            NptCreditCard creditCard = dao.findById(user.getUserId());
            if (creditCard == null) {
                creditCard = new NptCreditCard(user.getUserId());
                fillCard(creditCard, user);
                dao.save(creditCard);
            } else {
                fillCard(creditCard, user);
                dao.update(creditCard);
            }
            return NptDict.RST_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return NptDict.RST_ERROR;
        }
    }

    private void fillCard(NptCreditCard creditCard, NptCreditCardUser user){
        creditCard.setCardType(user.getUserType());
        creditCard.setIdCard(user.getUserIdCard());
        NptBaseModel model;
        if (user.getUserType() == NptDict.PDM_ENTERPRISE.getCode()) {
            model = baseModelService.lookupModelByCategoryAndHost(NptDict.BMC_OUTSIDE, NptDict.BMH_BSCARD);
        } else {
            model = baseModelService.lookupModelByCategoryAndHost(NptDict.BMC_OUTSIDE, NptDict.BMH_PSCARD);
        }

        Map<String, Object> tyValues = baseModelService.getModelMainPoolTypicalValueByPKValue(model, user.getUserIdCard());
        if (null != tyValues && !tyValues.values().isEmpty()) {
            List<Object> valueList = new ArrayList<>(tyValues.values());
            creditCard.setUkValue(Long.valueOf(String.valueOf(valueList.get(0))));
            creditCard.setCardTitle(String.valueOf(valueList.get(1)));

        }
//        creditCard.setImgPath();
    }

    @Override
    public List<NptCreditCard> search(NptDict searchType, String key, Integer currentPage, Integer pageSize) {
        return dao.search(searchType, key, currentPage, pageSize);
    }

    @Override
    public Collection<NptCreditCard> listCardInfo(List<Integer> ids) {
        return dao.findByIds(ids);
    }

    @Override
    public long getTotolCount(NptDict searchType, String key) {
        return dao.getTotolCount(searchType, key);
    }
}
