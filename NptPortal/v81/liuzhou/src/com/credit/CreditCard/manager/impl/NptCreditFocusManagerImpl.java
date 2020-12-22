package com.credit.CreditCard.manager.impl;

import com.credit.CreditCard.dao.NptCreditFocusDao;
import com.credit.CreditCard.entity.NptCreditFocus;
import com.credit.CreditCard.manager.NptCreditFocusManager;
import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.base.manager.NptBaseManagerImpl;
import com.npt.bridge.dict.NptDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 项目： NPTWebApp
 * 作者： zhanglei
 * 时间： 2017/06/13 11:53
 * 描述：
 */
@Service
@Transactional
public class NptCreditFocusManagerImpl extends NptBaseManagerImpl<NptCreditFocus, Integer> implements NptCreditFocusManager {

    @Autowired
    private NptCreditFocusDao dao;

    @Override
    public NptBaseDao<NptCreditFocus, Integer> getThisDao() {
        return dao;
    }

    @Override
    public Collection<NptCreditFocus> getCreditTalent(NptDict searchType, Integer limit) {
        return dao.getCreditTalent(searchType, limit);
    }

    @Override
    public boolean isFollowing(Integer fromUserId, Integer toUserId) {
        return dao.isFollowing(fromUserId, toUserId);
    }

    @Override
    public NptCreditFocus findFollowing(Integer fromUserId, Integer toUserId) {
        return dao.findFocus(fromUserId, toUserId);
    }

    @Override
    public long getFollowingCount(Integer fromUserId) {
        return dao.getFollowingCount(fromUserId);
    }

    @Override
    public Collection<NptCreditFocus> getFollowing(Integer fromUserId, Integer currentPage, Integer pageSize) {
        return dao.getFollowing(fromUserId, currentPage, pageSize);
    }

    @Override
    public long getFollowerCount(Integer toUserId) {
        return dao.getFollowerCount(toUserId);
    }

    @Override
    public Collection<NptCreditFocus> getFollower(Integer toUserId, Integer currentPage, Integer pageSize) {
        return dao.getFollower(toUserId, currentPage, pageSize);
    }
}
