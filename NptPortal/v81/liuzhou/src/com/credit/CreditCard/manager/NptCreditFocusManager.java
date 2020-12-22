package com.credit.CreditCard.manager;

import com.credit.CreditCard.entity.NptCreditFocus;
import com.npt.bridge.base.NptBaseManager;
import com.npt.bridge.dict.NptDict;

import java.util.Collection;

/**
 * 项目： NPTWebApp
 * 作者： zhanglei
 * 时间： 2017/06/13 11:53
 * 描述：
 */
public interface NptCreditFocusManager extends NptBaseManager<NptCreditFocus, Integer>{
    /**
     * @author zhanglei
     * @date 2017/06/12 11:15
     * @param searchType
     * @param limit
     * @return
     */
    Collection<NptCreditFocus> getCreditTalent(NptDict searchType, Integer limit);

    /**
     *  author: zhanglei
     *  date:   2017/06/13 16:31
     *  note:
     *          是否关注
     */
    boolean isFollowing(Integer fromUserId, Integer toUserId);

    NptCreditFocus findFollowing(Integer fromUserId, Integer toUserId);

    long getFollowingCount(Integer fromUserId);

    Collection<NptCreditFocus> getFollowing(Integer fromUserId, Integer currentPage, Integer pageSize);

    long getFollowerCount(Integer toUserId);

    Collection<NptCreditFocus> getFollower(Integer toUserId, Integer currentPage, Integer pageSize);
}
