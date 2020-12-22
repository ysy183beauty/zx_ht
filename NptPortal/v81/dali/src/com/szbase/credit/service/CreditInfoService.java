/**
* @Title: IAppealService.java
* @Package com.szbase.credit.service
* @Description: TODO
* @author Owen Szbase
* @date 2015年7月15日 下午4:35:59
* @version V1.0
*/
package com.szbase.credit.service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: IAppealService
 * @Description: TODO
 * @author Owen Szbase
 * @date 2015年7月15日 下午4:35:59
 *
 */
public interface CreditInfoService {

        public Map<String,Object> baseCompanyList(String qymc, String gszch, int start,int limit);
        public Map<String,Object> baseCompanyDetail(String qybs);
        // 企业信用查询委办局资源信息
        public Map<String,Object> creditCompanyTypeDetail(String mc);
        public Map<String,Object> creditCompanyDetail(String mc,String type);
        public Map<String,Object> creditCompanyList(String param_bs, String param_type,
                                        String tablename, int start, int limit , String wbjmc);
        public Map<String,Object> creditPersonList(String grbs);
        public Map<String,Object> basePersonDetail(String qybs);
}
