/**
* @Title: IAppealService.java
* @Package com.szbase.credit.service
* @Description: TODO
* @author Owen Szbase
* @date 2015年7月15日 下午4:35:59
* @version V1.0
*/
package com.szbase.credit.service.impl;

import com.szbase.credit.dao.CreditInfoDao;
import com.szbase.credit.service.CreditInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName: IAppealService
 * @Description: TODO
 * @author Owen Szbase
 * @date 2015年7月15日 下午4:35:59
 *
 */
@Service
public class CreditInfoServiceImpl implements CreditInfoService{

    @Autowired
    private CreditInfoDao creditInfoDao;

        public Map<String,Object> baseCompanyList(String qymc, String gszch, int start,int limit){
            return creditInfoDao.baseCompanyList(qymc,gszch,start,limit);
        }
        public Map<String,Object> baseCompanyDetail(String qybs){
            return creditInfoDao.baseCompanyDetail(qybs);
        }
        // 企业信用查询委办局资源信息
        public Map<String,Object> creditCompanyTypeDetail(String mc){
            return creditInfoDao.creditCompanyTypeDetail(mc);
        }
        // 企业信用查询委办局资源信息
        public Map<String,Object> creditCompanyDetail(String mc,String type){
            return creditInfoDao.creditCompanyDetail(mc,type);
        }

        public Map<String,Object> creditCompanyList(String param_bs, String param_type, String tablename,
                                        int start, int limit, String wbjmc){
            return creditInfoDao.creditCompanyList(param_bs,param_type,tablename,
            start,limit,wbjmc);
        }
        public Map<String,Object> creditPersonList(String grbs){
            return creditInfoDao.creditPersonList(grbs);
        }
        public Map<String,Object> basePersonDetail(String grbs){
            return creditInfoDao.basePersonDetail(grbs);
        }
}
