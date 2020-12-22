package com.credit.CreditQuery.dao.impl;

import com.credit.CreditQuery.dao.ApplyInfoDao;
import com.credit.CreditQuery.entity.ApplyInfo;
import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.hibernate4.HibernateBaseDao;
import com.jeecms.common.page.Pagination;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: infoDaoImpl
 * @Description: TODO
 * @author jiaoss
 * @date 2017年3月24日11:27:34
 *
 */
@Repository
public class ApplyInfoDaoImpl extends HibernateBaseDao<ApplyInfo, Integer> implements ApplyInfoDao {
    @Override
    public ApplyInfo save(ApplyInfo info){
        getSession().save(info);
        getSession().flush();
        return info;
    }
    @Override
    public ApplyInfo findById(int id){

        ApplyInfo info= get(id);

        return  info;
    }


    public List<ApplyInfo> isActiveApplyInfo(int userId,String flag){
        String hql=" from ApplyInfo bean where bean.userId=:userId ";
        Finder finder=Finder.create(hql).setParam("userId", userId);

        finder.append("  and bean.applyFlag=:applyFlag").setParam("applyFlag",flag);

        return find(finder);
    }

    @Override
    public List<ApplyInfo> getOutDateInfo() {
        String hql = "from ApplyInfo bean where bean.applyFlag=:applyFlag and trunc(sysdate)-7>trunc(bean.syncTime)";
        Finder finder = Finder.create(hql).setParam("applyFlag", "3");
        return find(finder);
    }

    @Override
    public void update(ApplyInfo info) {
        getSession().update(info);
        getSession().flush();
    }

    @Override
    public Pagination getApplyInfo(int userid, String flag, int pageNo, int PageSize) {

        String hql=" from ApplyInfo bean where bean.userId=:userId ";
        Finder finder=Finder.create(hql).setParam("userId", userid);

        if(null !=flag &&!"".equals(flag)){
            finder.append("  and bean.applyFlag=:applyFlag").setParam("applyFlag",flag);
        }
        return find(finder,pageNo,PageSize);
    }


    @Override
    public List<ApplyInfo> getActiveApplyInfo(String syncFlag) {
        String hql="from ApplyInfo bean where bean.syncFlag=:syncFlag ";

        Query query =getSession().createQuery(hql).setParameter("syncFlag",syncFlag);
        List<ApplyInfo> info= (List<ApplyInfo>)query.list();

        return info;
    }

    @Override
    public int updateAppInfo(int id,String flag,String syncFlag) {
        Query query =  getSession().createQuery("update ApplyInfo bean set bean.applyFlag =:applyFlag,bean.syncFlag=:syncFlag,bean.syncTime=sysdate where bean.id =:id");
        query.setParameter("applyFlag",flag);
        query.setParameter("syncFlag",syncFlag);
        query.setParameter("id",id);
        int sum =  query.executeUpdate();

        return sum;
    }

    @Override
    public int updateAppInfoByUserId(int userid,String flag,String syncFlag) {
        Query query =  getSession().createQuery("update ApplyInfo bean set bean.applyFlag =:applyFlag,bean.syncFlag=:syncFlag where bean.userId =:userId");
        query.setParameter("applyFlag",flag);
        query.setParameter("syncFlag",syncFlag);
        query.setParameter("userId",userid);
        int sum =  query.executeUpdate();

        return sum;
    }


    @Override
    protected Class<ApplyInfo> getEntityClass() {
        return ApplyInfo.class;
    }
}
