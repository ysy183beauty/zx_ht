package com.npt.rms.arch.dao.impl;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.arch.bean.ZTree;
import com.npt.rms.arch.dao.NptOrganizationDao;
import com.npt.rms.base.dao.NptHibernateBaseDaoImpl;
import com.npt.rms.util.JdbcConnection;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/27 15:39
 * 备注：
 */
@Repository
public class NptOrganizationDaoImpl extends NptHibernateBaseDaoImpl<NptLogicDataProvider> implements NptOrganizationDao {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactoryOverride(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        super.setSessionFactory(sessionFactory);
    }
    @Override
    public List<Map> selectUnitInfo(String sql) {
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List<Map> list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    @Override
    public  List<ZTree> selectOrganTreeList(Long orgId) {
        List<ZTree> trees =null;
        if(orgId==null){
            orgId=new Long(-1);
        }
        if(null == orgId || orgId == NptCommonUtil.getDefaultParentId()){
            String querySql="select a1.id,a1.org_addr,a1.org_code,a1.org_name,a1.parent_id,1 as is_open from NPT_ORGANIZATION a1 where a1.parent_id="+orgId+"";
            SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(querySql);
            List<Map> list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            if(list!=null&&list.size()>0){
                orgId=Long.parseLong(list.get(0).get("ID").toString());
                trees=getZtreeList(list,trees);
            }
        }
        StringBuilder sql=new StringBuilder();
        sql.append("select a1.id,a1.org_addr,a1.org_code,a1.org_name,a1.parent_id,");
        sql.append("case when (select count(1) from NPT_ORGANIZATION a2 where a2.parent_id=a1.id)>0 then 1 else 0 end is_open from NPT_ORGANIZATION a1 ");
        sql.append("  where a1.parent_id="+orgId+" order by a1.id");
        SQLQuery query= sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
        List<Map> resultList= query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        trees=getZtreeList(resultList,trees);
        return trees;
    }

    @Override
    public Integer selectCurrentModelId(NptDict host) {
        Integer result=0;
        StringBuilder sql=new StringBuilder();
        sql.append("select * from NPT_BASE_MODEL t");
        sql.append(" where t.host_id=0");
        sql.append(" and t.cate_id="+NptDict.BMC_NATIVE.getCode()+"");
        sql.append(" and t.status!="+NptDict.IDS_DELETED.getCode()+"");
        SQLQuery query= sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
        List<Map> resultList= query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        String count=resultList.get(0).get("ID")==null?"0":resultList.get(0).get("ID").toString();
        result=Integer.parseInt(count);
        return result;
    }

    @Override
    public List<Map> selectpoolId(String sql) {
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List<Map> list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    @Override
    public Integer updateCondition(String sql) {
        try {
            JdbcConnection.getInstance().updateInfo(sql);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private List<ZTree> getZtreeList(List<Map> list,List<ZTree> ztreeList){
         if(ztreeList==null){
             ztreeList=new ArrayList<ZTree>();
         }
        for(Map m:list){
            ZTree tree = new ZTree();
            tree.setId(m.get("ID")==null?"":m.get("ID").toString());
            tree.setpId(m.get("PARENT_ID")==null?"":m.get("PARENT_ID").toString());
            tree.setName(m.get("ORG_NAME")==null?"":m.get("ORG_NAME").toString());
            if(Integer.parseInt(m.get("IS_OPEN").toString())==1){
                tree.setOpen(true);
                tree.setIsParent(true);
            }else{
                tree.setOpen(false);
                tree.setIsParent(false);
            }
            ztreeList.add(tree);
        }
        return ztreeList;
    }
}
