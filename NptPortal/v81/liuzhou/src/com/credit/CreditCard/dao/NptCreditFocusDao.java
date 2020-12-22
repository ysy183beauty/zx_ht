package com.credit.CreditCard.dao;

import com.credit.CreditCard.entity.NptCreditFocus;
import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.dict.NptDict;

import java.util.Collection;

/**
 * 项目： NPTWebApp
 * 作者： zhanglei
 * 时间： 2017/06/13 11:53
 * 描述：
 */
public interface NptCreditFocusDao extends NptBaseDao<NptCreditFocus, Integer>{
    /**
     * @see com.credit.CreditCard.manager.NptCreditFocusManager#getCreditTalent(com.npt.bridge.dict.NptDict, java.lang.Integer)
     */
    Collection<NptCreditFocus> getCreditTalent(NptDict searchType, Integer limit);

    boolean isFollowing(Integer fromUserId, Integer toUserId);

    NptCreditFocus findFocus(Integer fromUserId, Integer toUserId);

    long getFollowingCount(Integer fromUserId);

    Collection<NptCreditFocus> getFollowing(Integer fromUserId, Integer currentPage, Integer pageSize);

    long getFollowerCount(Integer toUserId);

    Collection<NptCreditFocus> getFollower(Integer toUserId, Integer currentPage, Integer pageSize);
}
