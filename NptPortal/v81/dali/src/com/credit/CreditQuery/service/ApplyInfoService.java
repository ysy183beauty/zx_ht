/**
* @Title: IUserService.java
* @Package com.szbase.credit.service
* @Description: TODO
* @author Owen Szbase
* @date 2015年7月15日 下午2:57:33
* @version V1.0
*/
package com.credit.CreditQuery.service;

import com.credit.CreditQuery.entity.ApplyInfo;
import com.jeecms.common.page.Pagination;

import java.util.List;

/**
 * @ClassName: IUserService
 * @Description: TODO
 * @author jiaoss
 * @date 2017年3月2日16:42:38
 *
 */
public interface ApplyInfoService {

    public ApplyInfo saveApplyInfo(ApplyInfo info);

    public ApplyInfo findById(int id);

    public Pagination getApplyInfo(int userid, String flag, int pageNo, int PageSize);

    public List<ApplyInfo> getActiveApplyInfo(String syncFlag) ;

    public boolean updateAppInfo(int id,String flag,String syncFlag) ;

    public boolean updateAppInfoByUserId(int userid,String flag,String syncFlag) ;

    boolean isActiveApplyInfo(int userId,String flag);


    /**
     * 作者: 张磊
     * 日期: 2017/04/11 上午11:29
     * 备注: 获取过期的申请数据
     */
    List<ApplyInfo> getOutDateInfo();

    void update(ApplyInfo info);
}
