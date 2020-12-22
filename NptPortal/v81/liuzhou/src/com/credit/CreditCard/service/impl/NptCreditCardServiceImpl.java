package com.credit.CreditCard.service.impl;

import com.credit.CreditCard.entity.NptCreditCard;
import com.credit.CreditCard.entity.NptCreditFocus;
import com.credit.CreditCard.entity.NptCreditSearchLog;
import com.credit.CreditCard.manager.NptCreditCardManager;
import com.credit.CreditCard.manager.NptCreditFocusManager;
import com.credit.CreditCard.manager.NptCreditSearchLogManager;
import com.credit.CreditCard.service.NptCreditCardService;
import com.credit.CreditCard.user.NptCreditCardUser;
import com.npt.bridge.bean.NptPaginationData;
import com.npt.bridge.dict.NptDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 项目： NPTWebApp
 * 作者： zhanglei, owen
 * 时间： 2017/6/12 9:50
 * 描述：
 */
@Service
@Transactional
public class NptCreditCardServiceImpl implements NptCreditCardService{


    @Autowired
    private NptCreditCardManager creditCardManager;
    @Autowired
    private NptCreditFocusManager creditFocusManager;
    @Autowired
    private NptCreditSearchLogManager creditSearchLogManager;


    /**
     * 作者：owen
     * 时间: 2017/6/12 10:30
     * 描述:
     * 为当前已实名认证的用户新创建一个信用名片
     * <p>
     * 一个用户只能有一个信用名片
     *
     * @param user
     */
    @Override
    public NptDict newCard(NptCreditCardUser user) {
        if (user.isVerified()) {
            return creditCardManager.newCard(user);
        } else {
            return NptDict.RST_NOTVERIFIED;
        }
    }

    /**
     * 作者：owen
     * 时间: 2017/6/12 9:35
     * 描述:
     * 信用名片的查询操作
     * <p>
     *      查询关键字会保存到查询日志中
     *
     *      使用HIBERNATE标准查询实现
     *
     * @param searchType
     *          NPTDICT.PDM_ENTERPRISE或PDM_PERSONAL
     *          表示查询企业信用名片还是个人信用名片，若传NULL则表示同时查询企业和个人
     * @param key
     *          信用名片查询关键字，必须不为NULL
     *
     *          与NptCreditCard的cardTitle字段进行模糊比较
     */
    @Override
    public NptPaginationData<NptCreditCard> search(NptDict searchType, String key, Integer currentPage, Integer pageSize) {
        NptPaginationData<NptCreditCard> pagination = new NptPaginationData<>();
        pagination.setCurrentPage(currentPage);
        pagination.setPageSize(pageSize);
        if (key != null) {
            pagination.setTotalCount(creditCardManager.getTotolCount(searchType, key));
            pagination.setResults(creditCardManager.search(searchType, key, currentPage, pageSize));
            NptCreditSearchLog log = new NptCreditSearchLog(key, searchType == null ? NptDict.PDM_UNKNOW.getCode() : searchType.getCode());
            creditSearchLogManager.save(log);
        }
        return pagination;
    }

    /**
     * 作者：owen
     * 时间: 2017/6/12 9:45
     * 描述:
     * 获取过去指定days天的热搜关键字
     *
     * @param days
     *
     *      若days为Null则默认取30天
     *
     *      返回top 9 的热搜索关键字
     */
    @Override
    public Collection<NptCreditSearchLog> getCreditHotSearch(Integer days) {
        if (days == null) {
            days = 30;
        }
        return creditSearchLogManager.getCreditHotSearch(days, 9);
    }

    /**
     * 作者：owen
     * 时间: 2017/6/12 9:48
     * 描述:
     * 获取企业或个人的信用达人
     *
     * @param searchType
     *          NPTDICT.PDM_ENTERPRISE或PDM_PERSONAL
     *          表示查询企业信用名片还是个人信用名片，若传NULL则表示同时查询企业和个人
     * @param limit
     *          取值在[10,100]之间
     *
     *          若为NULL则取20
     *          若小于10则取10
     *          若大于100则取100
     *
     *
     */
    @Override
    public Collection<NptCreditCard> getCreditTalent(NptDict searchType, Integer limit) {
        if (limit == null) {
            limit = 20;
        } else if (limit < 10) {
            limit = 10;
        } else if (limit > 100) {
            limit = 100;
        }
        Collection<NptCreditFocus> creditTalent = creditFocusManager.getCreditTalent(searchType, limit);
        Collection<NptCreditCard> result = new ArrayList<>();
        if (creditTalent != null && creditTalent.size() > 0) {
            List<Integer> collect = creditTalent.stream().map(NptCreditFocus::getToCardId).collect(Collectors.toList());
            result = creditCardManager.listCardInfo(collect);
            for (NptCreditCard card : result) {
                for (NptCreditFocus focus : creditTalent) {
                    if(Objects.equals(card.getUserId(), focus.getToCardId())){
                        card.setHotValue(focus.getHotValue());
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public NptDict getRelation(Integer fromUserId, Integer toUserId) {
        boolean following = creditFocusManager.isFollowing(fromUserId, toUserId);
        if (following) {
            boolean follower = creditFocusManager.isFollowing(toUserId, fromUserId);
            if (follower) {
                return NptDict.CARD_DUPFOLLOW;
            } else {
                return NptDict.CARD_FOLLOWING;
            }
        } else {
            return NptDict.CARD_NOTFOLLOW;
        }
    }

    @Override
    public NptPaginationData<NptCreditCard> getFollowing(Integer fromUserId, Integer currentPage, Integer pageSize) {
        NptPaginationData<NptCreditCard> pagination = new NptPaginationData<>();
        pagination.setCurrentPage(currentPage);
        pagination.setPageSize(pageSize);
        if (fromUserId != null) {
            pagination.setTotalCount(creditFocusManager.getFollowingCount(fromUserId));
            Collection<NptCreditFocus> following = creditFocusManager.getFollowing(fromUserId, currentPage, pageSize);
            if(following != null && following.size() > 0) {
                List<Integer> collect = following.stream().map(NptCreditFocus::getToCardId).collect(Collectors.toList());
                Collection<NptCreditCard> result = creditCardManager.listCardInfo(collect);
                for (NptCreditCard card : result) {
                    card.setHotValue(this.getHotValue(card.getId()));
                }
                pagination.setResults(result);
            }
        }
        return pagination;
    }

    @Override
    public NptPaginationData<NptCreditCard> getFollower(Integer toUserId, Integer currentPage, Integer pageSize) {
        NptPaginationData<NptCreditCard> pagination = new NptPaginationData<>();
        pagination.setCurrentPage(currentPage);
        pagination.setPageSize(pageSize);
        if (toUserId != null) {
            pagination.setTotalCount(creditFocusManager.getFollowerCount(toUserId));
            Collection<NptCreditFocus> follower = creditFocusManager.getFollower(toUserId, currentPage, pageSize);
            if(follower != null && follower.size() > 0) {
                List<Integer> collect = follower.stream().map(NptCreditFocus::getFromCardId).collect(Collectors.toList());
                Collection<NptCreditCard> result = creditCardManager.listCardInfo(collect);
                for (NptCreditCard card : result) {
                    card.setHotValue(this.getHotValue(card.getId()));
                }
                pagination.setResults(result);
            }
        }
        return pagination;
    }

    @Override
    public Long getHotValue(Integer toUserId) {
        return creditFocusManager.getFollowerCount(toUserId);
    }

    @Override
    public NptCreditCard findCreditCardById(Integer userId) {
        return creditCardManager.findById(userId);
    }

    @Override
    public NptDict follow(Integer fromUserId, Integer toUserId) {
        NptCreditCard fromCard = creditCardManager.findById(fromUserId);
        NptCreditCard toCard = creditCardManager.findById(toUserId);
        if (fromCard != null && toCard != null && !Objects.equals(fromUserId, toUserId)) {
            try {
                NptCreditFocus focus = new NptCreditFocus(fromUserId, toUserId);
                focus.setToCardType(toCard.getCardType());
                if (!creditFocusManager.isFollowing(fromUserId, toUserId)) {
                    creditFocusManager.save(focus);
                }
                return NptDict.RST_SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                return NptDict.RST_ERROR;
            }
        } else {
            return NptDict.RST_ERROR;
        }
    }

    @Override
    public NptDict unFollow(Integer fromUserId, Integer toUserId) {
        NptCreditCard fromCard = creditCardManager.findById(fromUserId);
        NptCreditCard toCard = creditCardManager.findById(toUserId);
        if (fromCard != null && toCard != null && !Objects.equals(fromUserId, toUserId)) {
            try {
                NptCreditFocus focus = creditFocusManager.findFollowing(fromUserId, toUserId);
                if (focus != null) {
                    creditFocusManager.delete(focus);
                }
                return NptDict.RST_SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                return NptDict.RST_ERROR;
            }
        } else {
            return NptDict.RST_SUCCESS;
        }
    }
}
