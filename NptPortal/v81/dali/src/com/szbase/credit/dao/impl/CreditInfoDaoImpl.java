/**
* @Title: IAppealService.java
* @Package com.szbase.credit.service
* @Description: TODO
* @author Owen Szbase
* @date 2015年7月15日 下午4:35:59
* @version V1.0
*/
package com.szbase.credit.dao.impl;

import com.szbase.credit.dao.CreditInfoDao;
import com.szbase.credit.entity.base.DBContextHolder;
import com.szbase.credit.util.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: IAppealService
 * @Description: TODO
 * @author Owen Szbase
 * @date 2015年7月15日 下午4:35:59
 *
 */
@Repository
public class CreditInfoDaoImpl implements CreditInfoDao {

    @Resource(name = "simpleJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

        public Map<String,Object> baseCompanyList(String qymc, String gszch, int start,int limit){
            DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE_ZX);
            HashMap<String,Object> map = new HashMap<String, Object>();
            String condition="";
            if (qymc!=null && !"".equals(qymc)) {
                condition =  " and t.frmc like '%"+qymc+"%'";
            }
            if (gszch!=null &&!"".equals(gszch)) {
                condition = condition + " and t.zch like '%"+gszch+"%'";
            }

            String sql = "select * from (select t.frbs qybs,t.frmc qymc,t.zch gszch,t.fddbr,ROWNUM rn from FR_jbxx_v@ALPHA  t where 1=1  "+
                    condition+"  AND ROWNUM <= "+(start+limit)+") s where s.rn>"+start;
            List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
            map.put("list",list);

            String countSql="select count(*) sum from FR_jbxx_v@ALPHA  t where 1=1  "+condition;
            Map<String,Object> count = jdbcTemplate.queryForMap(countSql);
            map.put("count",count.get("SUM"));

            return map;
        }
        public  Map<String,Object> baseCompanyDetail(String qybs){
            Map<String,Object> detail = jdbcTemplate.queryForMap("select frmc qymc,fddbr,zch gszch,zzjgdm,frzs jycs,jyfw,to_char(kyrq,'YYYY-MM-DD') kyrq from FR_jbxx_v@ALPHA  t  where t.frbs= '"+qybs+"'");

            return detail;
        }
        // 企业信用查询委办局资源信息
        public Map<String,Object> creditCompanyTypeDetail(String mc){

            //获得数据类别以及相关信息
            List<Map<String,Object>> type = jdbcTemplate.queryForList("select * from ggxyxxcxpzb@ALPHA t where t.qybs=1");

            Map<String,Object> creditData = new HashMap<String, Object>();
            for (int i = 0; i <type.size() ; i++) {
                Map<String,Object> map = type.get(i);
                String sql = StringUtils.getStr(map.get("SQL"));
                if("".equals(sql)){//没有配置sql则直接通过字段查询
                    sql = "select "+map.get("ZDBM")+" from "+map.get("BYWM")+"@ALPHA  where "+map.get("FRMC")+" = '"+mc+"'";
                }else{
                    sql= sql+"'"+mc+"'";
                }
                List<Map<String,Object>> data = jdbcTemplate.queryForList(sql);

                if(data.size()>0){//存在关联数据
                    //组装字段值和描述为map
                    Map<String,String> title = new HashMap<String, String>();
                    String[] zdmcs = StringUtils.getStr(map.get("ZDMC")).split(",");
                    String[] zdbms = StringUtils.getStr(map.get("ZDBM")).split(",");
                    System.out.println(zdbms.length+"---"+zdbms.length);
                    for (int j=0; j<zdbms.length;j++){
                        title.put(zdbms[j],zdmcs[j]);
                    }

                    String dataType = StringUtils.getStr(map.get("XXFL"));
                    if("主体身份类".equals(dataType)){
                        Map<String, Object> dataMap = new HashMap<String, Object>();

                        dataMap.put("data", data);
                        dataMap.put("title", title);
                        creditData.put("main",dataMap);
                    }else {
                        if (creditData.containsKey(dataType)) {
                            List<Map<String, Object>> list = (ArrayList) creditData.get(dataType);
                            Map<String, Object> dataMap = new HashMap<String, Object>();
                            dataMap.put("des", map);
                            dataMap.put("data", data);
                            dataMap.put("title", title);
                            list.add(dataMap);
                            creditData.put(dataType, list);
                        } else {
                            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                            Map<String, Object> dataMap = new HashMap<String, Object>();
                            dataMap.put("des", map);
                            dataMap.put("data", data);
                            dataMap.put("title", title);
                            list.add(dataMap);
                            creditData.put(dataType, list);
                        }
                    }
                }
            }

            return creditData;
        }

    // 企业信用查询委办局资源信息
    public Map<String,Object> creditCompanyDetail(String mc,String type){

        System.out.println(mc);
        Map<String,Object> map = jdbcTemplate.queryForMap("select * from ggxyxxcxpzb@ALPHA t where t.qybs=1 and t.bzwm='"+type+"'");

        Map<String,Object> creditData = new HashMap<String,Object>();

            String tableName = map.get("BYWM").toString().toUpperCase();
            String sql = "select c.column_NAME,c.comments from user_col_comments@ALPHA c where  c.table_name='"+tableName+"'" +
                    "        and c.column_NAME not in ('ID','NCF_STATUS','NCF_CREATE_TIME','NCF_CREATE_USER','NCF_MODIFY_TIME','NCF_MODIFY_USER','FRBS','ZT') " +
                    "        and c.comments is not null";

            List<Map<String,Object>> head = jdbcTemplate.queryForList(sql);
            System.out.println(sql);

            String dataSql = "select  * from "+tableName+"@ALPHA  where "+map.get("FRMC").toString()+" = '"+mc+"'";
            System.out.println(dataSql);
            Map<String,Object> data = jdbcTemplate.queryForMap(dataSql);

        creditData.put("head",head);
        creditData.put("data",data);

        return creditData;
    }

        public Map<String,Object> creditCompanyList(String param_bs, String param_type, String tablename,
                                        int start, int limit, String wbjmc){
            return new HashMap<String, Object>();
        }
        public Map<String,Object> creditPersonList(String grbs){
            return new HashMap<String, Object>();
        }
        public Map<String,Object> basePersonDetail(String grbs){
            return new HashMap<String, Object>();
        }
}
