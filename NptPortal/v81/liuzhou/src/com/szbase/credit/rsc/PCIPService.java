/**
* @Title: PCIPService.java
* @Package com.szbase.credit.rsc
* @Description: TODO
* @author Owen
* @date 2015年7月15日 下午2:53:30
* @version V1.0
*/
package com.szbase.credit.rsc;

import java.util.Map;

/**
* @ClassName: PCIPService
* @Description: TODO
* @author Owen Szbase
* @date 2015年7月15日 下午3:15:51
*
*/
public interface PCIPService {
	
	/**
	* @Title: lookupEnterpriseCredit
	* @Description:
	* 	企业信用查询
	* @param eName		企业名称
	* @param icrNum		工商注册号
	* @param orgCode	组织机构代码
	* @return
	* 	Json串
	* @throws
	* @Date:2015年7月15日 下午3:21:39
	*/
	public String lookupEnterpriseCredit(String eName,String icrNum,String orgCode);
	
	
	/**
	* @Title: lookupPersonalCredit
	* @Description:
	* 	个人信息查询
	* 
	* 	不需要登录即可查询
	* @param name		姓名
	* @param idCard		身份证
	* @return
	* 	Json串
	* @throws
	* @Date:2015年7月15日 下午3:26:13
	*/
	public String lookupPersonalCredit(String name,String idCard);
	
	
	/**
	* @Title: lookupFocusGroupCredit
	* @Description:
	* 	重点人群信用查询
	* 	只有经过实名认证且已登录的用户可以进行此查询
	* @param name		姓名
	* @param idCard		身份证号
	* @param focusType	重点人群类型
	* @param qcNum		资格证号
	* @return
	* 	Json串
	* @throws
	* @Date:2015年7月15日 下午3:29:39
	*/
	public String lookupFocusGroupCredit(String name,String idCard,int focusType,String qcNum);
	
	
	/**
	* @Title: lookupupSelfCredit
	* @Description:
	* 	自身信用查询
	* 	经过实名认证的用户登录到门户之后，可以在某个页面查询自身的信用
	* @param selfType	自身类型，目前只有两种类型，分别是企业和个人
	* @param jsonParam	由于企业和个人的查询参数 ，这里采用JSON格式的查询参数
	* @return
	* 	Json串
	* @throws
	* @Date:2015年7月15日 下午3:36:30
	*/
	public String lookupupSelfCredit(String selfType,String jsonParam);
	
	
	/**
	* @Title: pushAppeal
	* @Description:
	* 	向公共信用信息平台推送用户的信用申诉
	* 
	* 	用户的申诉信息在处理完成之前不可在页面上显示
	* @param id		申诉ID
	* @param cnt	申诉内容
	* @throws
	* @Date:2015年7月15日 下午4:07:30
	*/
	public void pushAppeal(String id,String cnt);
	
	/**
	* @Title: lookupAppealStauts
	* @Description:
	* 	查询用户申诉的处理状态
	* @param id		申诉ID
	* @return		申诉的处理状态
	* @throws
	* @Date:2015年7月15日 下午4:08:27
	*/
	public String lookupAppealStauts(String id);
	
	
	/**
	* @Title: lookupOrgList
	* @Description:
	* 	展示提供数据的各委办局名称，点击委办局名称则跳转至相关网站。
	* @return		<委办局中文名称,委办局网站URL>
	* @throws
	* @Date:2015年7月15日 下午4:11:14
	*/
	public Map<String,String> lookupOrgList();

	/**
	 * 查询红黑榜列表
	 * @param listUrl 请求地址
	 * @param param_xxlb 信息类别
	 * @param start 起始页
	 * @param limit 每页条数
	 * @return Json格式串
	 */
	String getHhbxxPageList(String listUrl, String param_xxlb, int start, int limit);

	/**
	 * 查询红黑榜详细信息
	 * @param detailUrl 请求地址
	 * @param xxxxglbm 详细信息关联表名
	 * @param start 起始页
	 * @param limit 每页条数
	 * @return Json格式串
	 */
	String getHhbxxDetail(String detailUrl, String paramId, int start, int limit,String first,String queryword);

	/**
	 * 查询企业信用信息列表
	 *
	 * @param listUrl 请求地址
	 * @param qymc    企业名称
	 * @param gsczh   工商注册号
	 * @param start   起始记录号
	 * @param limit   每页条数
	 * @return Json格式串
	 */
	String getQyJcxxPageList(String listUrl, String qymc, String gsczh, int start, int limit);

	/**
	 * 查询企业信用详细信息
	 *
	 * @param detailUrl 请求地址
	 * @param qybs      企业标识
	 * @return Json格式串
	 */
	String getQyJcxxDetail(String detailUrl, String qybs);
	/**
	 * 查询企业信用详细信息
	 *
	 * @param param_bs	企业标识
     * @param param_type 类型,1是企业，2是人
	 * @return Json格式串
	 */
	String getQyxyxxDetail(String detailUrl, String param_bs,String param_type);
	/**
	 * 查询企业信用详细信息
	 *
	 * @param param_bs	企业标识
     * @param param_type	类型
     * @param tablename	实体表名
     * @param start	起始记录号
     * @param limit	每页条数
	 * @return Json格式串
	 */
	String getQyZyPageList(String detailUrl, String param_bs,String param_type,String tablename,int start,int limit);
	/**
	 * 个人基础信息查询详细信息接口
	 *
	 */
	String getGrJcxxDetail(String detailUrl, String sfzh);

	/**
	 * 双公示
	 *
	 */
	public String getSGS(String detailUrl ,int start,int limit,String type,String queryday,String xzxdrm);
	

}
