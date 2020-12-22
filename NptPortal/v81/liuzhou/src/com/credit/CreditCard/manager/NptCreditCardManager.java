package com.credit.CreditCard.manager;

import com.credit.CreditCard.entity.NptCreditCard;
import com.credit.CreditCard.user.NptCreditCardUser;
import com.npt.bridge.base.NptBaseManager;
import com.npt.bridge.dict.NptDict;

import java.util.Collection;
import java.util.List;

/**
 * 项目： NPTWebApp
 * 作者： zhanglei
 * 时间： 2017/06/13 11:53
 * 描述：
 */
public interface NptCreditCardManager extends NptBaseManager<NptCreditCard, Integer>{
    /**
     * @author zhanglei
     * @date 2017/06/12 11:12
     * @param user
     * @return
     */
    NptDict newCard(NptCreditCardUser user);

    /**
     * 分页查询
     * @author zhanglei
     * @date 2017/06/12 11:21
     * @param searchType
     * @param key
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<NptCreditCard> search(NptDict searchType, String key, Integer currentPage, Integer pageSize);

    /**
     * @author zhanglei
     * @date 2017/06/12 17:03
     * @param ids
     * @return
     */
    Collection<NptCreditCard> listCardInfo(List<Integer> ids);

    /**
     * 获取总记录条数
     * @author zhanglei
     * @date 2017/06/13 11:49
     * @param searchType
     * @param key
     * @return
     */
    long getTotolCount(NptDict searchType, String key);
}
