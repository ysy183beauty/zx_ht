package com.credit.CreditCard.service;

import com.credit.CreditCard.entity.NptCreditCard;
import com.credit.CreditCard.entity.NptCreditSearchLog;
import com.credit.CreditCard.user.NptCreditCardUser;
import com.npt.bridge.bean.NptPaginationData;
import com.npt.bridge.dict.NptDict;

import java.util.Collection;

/**
 * 项目： NPTWebApp
 * 作者： zhanglei,owen
 * 时间： 2017/6/12 9:34
 * 描述：
 *
 *      信用名片的服务类，其内使用信用名片各实体类的manager进行各种业务操作
 */
public interface NptCreditCardService {


    /**
     *作者：owen
     *时间: 2017/6/12 10:30
     *描述:
     *      为当前已实名认证的用户新创建一个信用名片
     *
     *      一个用户只能有一个信用名片
     */
    NptDict newCard(NptCreditCardUser user);

    /**
     *作者：owen
     *时间: 2017/6/12 9:35
     *描述:
     *      信用名片的查询操作
     *
     *      查询关键字会保存到查询日志中
     */
    NptPaginationData<NptCreditCard> search(NptDict searchType, String key, Integer currentPage, Integer pageSize);

    /**
     *作者：owen
     *时间: 2017/6/12 9:45
     *描述:
     *      获取过去指定days天的热搜关键字
     */
    Collection<NptCreditSearchLog> getCreditHotSearch(Integer days);


    /**
     *作者：owen
     *时间: 2017/6/12 9:48
     *描述:
     *      获取企业或个人的信用达人
     */
    Collection<NptCreditCard> getCreditTalent(NptDict searchType,Integer num);

    /**
     *  author: zhanglei
     *  date:   2017/06/14 16:12
     *  note:
     *          获取两信用名片之间的关系：未关注、关注、互相关注
     */
    NptDict getRelation(Integer fromUserId, Integer toUserId);

    /**
     *  author: zhanglei
     *  date:   2017/06/14 16:13
     *  note:
     *          分页获取正在关注
     */
    NptPaginationData<NptCreditCard> getFollowing(Integer fromUserId, Integer currentPage, Integer pageSize);

    /**
     *  author: zhanglei
     *  date:   2017/06/14 16:13
     *  note:
     *          分页获取关注的人
     */
    NptPaginationData<NptCreditCard> getFollower(Integer toUserId, Integer currentPage, Integer pageSize);

    /**
     *  author: zhanglei
     *  date:   2017/06/14 16:13
     *  note:
     *          获取关注度
     */
    Long getHotValue(Integer toUserId);

    /**
     *  author: zhanglei
     *  date:   2017/06/14 16:14
     *  note:
     *          根据用户id获取信用名片
     */
    NptCreditCard findCreditCardById(Integer userId);

    /**
     *  author: zhanglei
     *  date:   2017/06/14 16:14
     *  note:
     *          关注某人
     */
    NptDict follow(Integer fromUserId, Integer toUserId);

    /**
     *  author: zhanglei
     *  date:   2017/06/14 16:14
     *  note:
     *          取消关注某人
     */
    NptDict unFollow(Integer fromUserId, Integer toUserId);
}
