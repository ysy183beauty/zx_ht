/**
* @Title: ComplaintServiceImpl.java
* @Package com.szbase.credit.service.impl
* @Description: TODO
* @author Owen Szbase
* @date 2015年7月15日 下午4:36:40
* @version V1.0
*/
package com.szbase.credit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szbase.credit.dao.IComplaintDao;
import com.szbase.credit.service.IComplaintService;

/**
 * @ClassName: ComplaintServiceImpl
 * @Description: TODO
 * @author Owen Szbase
 * @date 2015年7月15日 下午4:36:40
 *
 */
@Service
public class ComplaintServiceImpl implements IComplaintService {

	@Autowired
	private IComplaintDao complaintDao;

	public IComplaintDao getComplaintDao() {return complaintDao;}
	public void setComplaintDao(IComplaintDao complaintDao) {this.complaintDao = complaintDao;}
	
}
