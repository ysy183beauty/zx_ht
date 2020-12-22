/**
* @Title: PartnersService.java
* @Package com.szbase.credit.rsc
* @Description: TODO
* @author Owen Szbase
* @date 2015年7月15日 下午3:40:34
* @version V1.0
*/
package com.szbase.credit.rsc;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: PartnersService
 * @Description: TODO
 * 	合作伙伴服务，这里主要是12345便民服务热线
 * @author Owen Szbase
 * @date 2015年7月15日 下午3:40:34
 *
 */
public interface PartnersService {
	
	/**
	* @Title: pushComplaint
	* @Description:
	* 	向12345推送投诉
	* @param id		投诉ID
	* @param cnt	投诉内容
	* @throws
	* @Date:2015年7月15日 下午4:00:06
	*/
	public void pushComplaint(String id,String cnt);
	
	/**
	* @Title: lookupStatus
	* @Description:
	* 	从12345查询已推送的投诉的处理状态
	* @param idList		投诉ID列表
	* @return			<投诉ID，处理状态>
	* @throws
	* @Date:2015年7月15日 下午4:00:33
	*/
	public Map<String,String> lookupStatus(List<String> idList);
}
