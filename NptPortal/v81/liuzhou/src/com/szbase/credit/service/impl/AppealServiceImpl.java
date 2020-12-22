/**
* @Title: AppealServiceImpl.java
* @Package com.szbase.credit.service.impl
* @Description: TODO
* @author Owen Szbase
* @date 2015年7月15日 下午4:37:06
* @version V1.0
*/
package com.szbase.credit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szbase.credit.dao.IAppealDao;
import com.szbase.credit.service.IAppealService;

/**
 * @ClassName: AppealServiceImpl
 * @Description: TODO
 * @author Owen Szbase
 * @date 2015年7月15日 下午4:37:06
 *
 */
@Service
public class AppealServiceImpl implements IAppealService {
	
	@Autowired
	private IAppealDao appealDao;

	public IAppealDao getAppealDao() {return appealDao;}
	public void setAppealDao(IAppealDao appealDao) {this.appealDao = appealDao;}
	
}
