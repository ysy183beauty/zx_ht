/**
 * @Title: UserServiceImpl.java
 * @Package com.szbase.credit.service.impl
 * @Description: TODO
 * @author Owen Szbase
 * @date 2015年7月15日 下午2:58:05
 * @version V1.0
 */
package com.credit.CreditQuery.service.impl;

import com.credit.CreditQuery.dao.ApplyInfoDao;
import com.credit.CreditQuery.entity.ApplyInfo;
import com.credit.CreditQuery.service.ApplyInfoService;
import com.jeecms.common.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: UserServiceImpl
 * @Description: 用户管理——字段加解密的类
 *              主要加密: 密码，电话，身份证号
 * @author jiaoss
 * @date 2017年3月2日16:40:38
 *
 */
@Service
@Transactional
public class ApplyInfoServiceImpl implements ApplyInfoService {

    @Autowired
    private ApplyInfoDao applyInfoDao;

    @Override
    public ApplyInfo saveApplyInfo(ApplyInfo info){
         return applyInfoDao.save(info);
    }

    @Override
    public ApplyInfo findById(int id){
        return applyInfoDao.findById(id);
    }

    @Override
    public Pagination getApplyInfo(int userid, String flag, int pageNo, int PageSize){
        return applyInfoDao.getApplyInfo(userid,flag,pageNo,PageSize);
    }

    @Override
    public List<ApplyInfo> getActiveApplyInfo(String syncFlag){
        return applyInfoDao.getActiveApplyInfo(syncFlag);
    }

    @Override
    public boolean updateAppInfo(int id,String flag,String syncFlag) {
        int sum=applyInfoDao.updateAppInfo(id,flag,syncFlag);
        if(sum==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateAppInfoByUserId(int userid,String flag,String syncFlag) {
        int sum=applyInfoDao.updateAppInfoByUserId(userid,flag,syncFlag);
        if(sum==1){
            return true;
        }
        return false;
    }


    public boolean isActiveApplyInfo(int userId, String flag) {
        return applyInfoDao.isActiveApplyInfo(userId, flag).size() > 0;
    }

    @Override
    public List<ApplyInfo> getOutDateInfo() {
        return applyInfoDao.getOutDateInfo();
    }

    @Override
    public void update(ApplyInfo info) {
        applyInfoDao.update(info);
    }
}
