package com.credit.CreditQuery.dao;

import com.credit.CreditQuery.entity.ApplyInfo;
import com.jeecms.common.page.Pagination;

import java.util.List;

/**
 * @ClassName: UserDao
 * @Description: TODO
 * @author jiaoss
 * @date 2017年3月2日16:330:38
 *
 */
public interface ApplyInfoDao {
    public ApplyInfo save(ApplyInfo info);

    public ApplyInfo findById(int id);

    public Pagination getApplyInfo(int userid, String flag, int pageNo, int PageSize);

    public List<ApplyInfo> getActiveApplyInfo(String syncFlag) ;

    public int updateAppInfo(int id,String flag,String syncFlag) ;

    public int updateAppInfoByUserId(int userid,String flag,String syncFlag) ;

    public  List<ApplyInfo> isActiveApplyInfo(int userId,String flag);


    List<ApplyInfo> getOutDateInfo();

    void update(ApplyInfo info);
}
