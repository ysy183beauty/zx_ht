package com.jeecms.core.dao.impl;

import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.hibernate4.HibernateBaseDao;
import com.jeecms.core.dao.CmsUserExtDao;
import com.jeecms.core.entity.CmsUserExt;
import com.npt.bridge.dict.NptDict;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CmsUserExtDaoImpl extends HibernateBaseDao<CmsUserExt, Integer> implements CmsUserExtDao {
	public CmsUserExt findById(Integer id) {
		CmsUserExt entity = get(id);
		return entity;
	}

	public CmsUserExt save(CmsUserExt bean) {
		getSession().save(bean);
		return bean;
	}
    public int updateUser(CmsUserExt bean) {
        StringBuffer sql=new StringBuffer("update CmsUserExt bean set ");
        String mobile = bean.getMobile();
        if(mobile!=null || !"".equals(mobile)){
            sql.append("bean.mobile =:mobile,");
        }
        String idCard = bean.getIdCard();
        if(idCard!=null || !"".equals(idCard)){
            sql.append("bean.idCard=:idCard,");
        }
        String flag = bean.getFlag();
        if(flag!=null || !"".equals(flag)){
            sql.append("bean.flag=:flag,");
        }
        String type = bean.getType();
        if(type!=null || !"".equals(type)){
            sql.append("bean.type=:type,");
        }
        String realname = bean.getRealname();
        if(realname!=null || !"".equals(realname)){
            sql.append("bean.realname=:realname,");
        }
        String idCardImg = bean.getIdCardImg();
        if(idCardImg!=null || !"".equals(idCardImg)){
            sql.append("bean.idCardImg=:idCardImg,");
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(" where bean.id =:id");
        Query query =  getSession().createQuery(sql.toString());

        if(mobile!=null || !"".equals(mobile)){
            query.setParameter("mobile",bean.getMobile());
        }
        if(idCard!=null || !"".equals(idCard)){
            query.setParameter("idCard",bean.getIdCard());
        }
        if(flag!=null || !"".equals(flag)){
            query.setParameter("flag",bean.getFlag());
        }
        if(type!=null || !"".equals(type)){
            query.setParameter("type",bean.getType());
        }
        if(realname!=null || !"".equals(realname)){
            query.setParameter("realname",bean.getRealname());
        }
        if(idCardImg!=null || !"".equals(idCardImg)){
            query.setParameter("idCardImg",bean.getIdCardImg());
        }
        query.setParameter("id",bean.getId());
        int sum =  query.executeUpdate();

        return sum;
    }
    public CmsUserExt mobileExist(String mobile){
        String hql="from CmsUserExt bean where bean.mobile=:mobile and bean.flag<>9";
        CmsUserExt ext= (CmsUserExt) getSession().createQuery(hql).setParameter("mobile",
                mobile).uniqueResult();
        return ext;
    }

    public int changeMobile(String mobile,Integer id){
        Query query =  getSession().createQuery("update CmsUserExt bean set bean.mobile =:mobile where bean.id =:id");
        query.setParameter("mobile",mobile);
        query.setParameter("id",id);
        int sum =  query.executeUpdate();

        return sum;
    }

    public List<CmsUserExt> getSyncUser(){
        Finder f=Finder.create(" from CmsUserExt bean where bean.flag=:flag and ( bean.syncFlag=:syncFlag or bean.syncFlag is null)").setParam("flag",
                "2").setParam("syncFlag", NptDict.RCS_NOTSYNED.getCode()+"");

        return (List<CmsUserExt>)find(f, 1, 5).getList();
    }

    public int updateSyncUser(int id,String time,String syncFlag){
        Query query =  getSession().createQuery("update CmsUserExt bean set bean.syncTime =:syncTime,bean.syncFlag=:syncFlag where bean.id =:id");
        query.setParameter("syncTime",time);
        query.setParameter("syncFlag",syncFlag);
        query.setParameter("id",id);
        int sum =  query.executeUpdate();

        return sum;
    }

    public int updateSyncUserResult(int id,String time,String syncFlag,String flag,String failComment){
        Query query =  getSession().createQuery("update CmsUserExt bean set bean.syncTime =:syncTime," +
                "bean.syncFlag=:syncFlag,bean.flag=:flag,bean.failComment=:failComment where bean.id =:id");
        query.setParameter("syncTime",time);
        query.setParameter("syncFlag",syncFlag);
        query.setParameter("flag",flag);
        query.setParameter("failComment",failComment);
        query.setParameter("id",id);
        int sum =  query.executeUpdate();

        return sum;
    }


    //认证状态
    public int changeflag(Integer id,String flag,String msg){
        Query query =  getSession().createQuery("update CmsUserExt bean set bean.flag =:flag,bean.failComment=:failComment where bean.id =:id");
        query.setParameter("flag",flag);
        query.setParameter("failComment",msg);
        query.setParameter("id",id);
        int sum =  query.executeUpdate();

        return sum;
    }

    @Override
    public CmsUserExt realNameExist(String realname) {
        String hql="from CmsUserExt bean where bean.realname=:realname and bean.flag<>9";
        CmsUserExt ext= (CmsUserExt) getSession().createQuery(hql).setParameter("realname",
                realname).uniqueResult();
        return ext;
    }


    public CmsUserExt idCardExist(String idCard){
        String hql="from CmsUserExt bean where bean.idCard=:idCard and bean.flag<>9";
        CmsUserExt ext= (CmsUserExt) getSession().createQuery(hql).setParameter("idCard",
                idCard).uniqueResult();
        return ext;
    }

	@Override
	protected Class<CmsUserExt> getEntityClass() {
		return CmsUserExt.class;
	}
}