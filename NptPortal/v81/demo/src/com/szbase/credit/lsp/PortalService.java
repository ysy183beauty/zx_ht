/**
* @Title: PortalService.java
* @Package com.szbase.credit.lsp
* @Description: TODO
* @author Owen Szbase
* @date 2015年7月15日 下午3:50:18
* @version V1.0
*/
package com.szbase.credit.lsp;

/**
 * @ClassName: PortalService
 * @Description: TODO
 * 	本地服务提供者
 * 
 * 	门户对外提供的查询接口（主要是对公共信用信息平台）
 * @author Owen Szbase
 * @date 2015年7月15日 下午3:50:18
 *
 */
public interface PortalService {


	/**
	* @Title: lookupComplaintList
	* @Description:
	* 	查询用户投诉列表
	* @param start		分页的起始位置
	* @param size		查询数量
	* @return
	* 	Json串
	* @throws
	* @Date:2015年7月15日 下午3:53:47
	*/
	public String lookupComplaintList(int start,int size);
	
	/**
	* @Title: lookupComplaintDetail
	* @Description:
	* 	查询指定投诉的详情，包括其状态
	* @param id		投诉ID
	* @return
	* 	Json串
	* @throws
	* @Date:2015年7月15日 下午3:55:08
	*/
	public String lookupComplaintDetail(String id);
}
