package com.credit.SystemManager.dao.impl;

import com.credit.SystemManager.dao.UserDao;
import com.credit.SystemManager.entity.CreditUser;
import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.hibernate4.HibernateBaseDao;
import com.jeecms.common.page.Pagination;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: UserDaoImpl
 * @Description: TODO
 * @author jiaoss
 * @date 2017年3月2日16:40:38
 *
 */
@Repository
public class UserDaoImpl extends HibernateBaseDao<CreditUser, Integer> implements UserDao {

    @Override
    public Boolean userRegister(CreditUser user) {
        Serializable id =  getSession().save(user);
        getSession().flush();
        if(id==null){
           return  false;
        }
        return true;
    }


    @Override
    public int userAuthentification(CreditUser user) {
        Query query = getSession().createQuery("update CreditUser bean set bean.idCard =:idCard ,bean.userName =:userName," +
                "bean.type =:type,bean.flag=:flag,bean.phone=:phone where bean.loginName =:loginName");
        query.setParameter("loginName",user.getLoginName());
        query.setParameter("idCard",user.getIdCard());
        query.setParameter("userName",user.getUserName());
        query.setParameter("flag",user.getFlag());
        query.setParameter("type",user.getType());
        query.setParameter("phone",user.getPhone());
        int sum =  query.executeUpdate();

        return sum;
    }

    @Override
    public CreditUser getUserByLoginName(String loginName) {
        String hql="from CreditUser bean where bean.loginName=:loginName and bean.flag<>9";
        CreditUser user= (CreditUser) getSession().createQuery(hql).setParameter("loginName",
                loginName).uniqueResult();

        return user;
    }

    @Override
    public String sendMessage(String message,String phone) {
        return null;
    }

    @Override
    public String checkMessage(String jym,String phone) {
        return null;
    }

    @Override
    public int changePassword(String phone, String password) {

        Query query = getSession().createQuery("update CreditUser bean set bean.passWord =:passWord where bean.phone =:phone ");
        query.setParameter("passWord",password);
        query.setParameter("phone",phone);
        int sum =  query.executeUpdate();

        return sum;
    }

    @Override
    public int changePasswordByPwd(String oldPassWord, String passWord) {

        Query query =  getSession().createQuery("update CreditUser bean set bean.passWord =:passWord where bean.passWord =:oldPassWord");
        query.setParameter("passWord",passWord);
        query.setParameter("oldPassWord",oldPassWord);
        int sum =  query.executeUpdate();

        return sum;
    }

    @Override
    public int changePhoneByPhone(String oldPhone, String phone) {
        Query query = getSession().createQuery("update CreditUser bean set bean.phone =:phone where bean.phone =:oldPhone");
        query.setParameter("phone",phone);
        query.setParameter("oldPhone",oldPhone);
        int sum =  query.executeUpdate();

        return sum;
    }


    public CreditUser getUserById(Long id){
        String hql="from CreditUser bean where bean.id=:id";
        CreditUser user= (CreditUser) getSession().createQuery(hql).setParameter("id",
                id).uniqueResult();

        return  user;
    }

    public Pagination getUserList(int current, int preNum,CreditUser user){

        String hql="from CreditUser u where 1=1";
        //判断查询条件是否为空
        String flag = user.getFlag();
        if(flag!=null && !flag.equals("0")){
            hql=hql+" and u.flag='"+flag+"'";
        }
        String type = user.getType();
        if(type!=null && !type.equals("0")){
            hql=hql+" and u.type='"+type+"'";
        }
        String idCard = user.getIdCard();
        if(idCard!=null && !"".equals(idCard.trim())){
            hql=hql+" and u.idCard='"+idCard+"'";
        }
        String phone = user.getPhone();
        if(phone!=null && !"".equals(phone.trim())){
            hql=hql+" and u.phone='"+phone+"'";
        }
        String loginName = user.getLoginName();
        if(loginName!=null && !"".equals(loginName.trim())){
            hql=hql+" and u.loginName='"+loginName+"'";
        }
        String userName = user.getUserName();
        if(userName!=null && !"".equals(userName.trim())){
            hql=hql+" and u.userName='"+userName+"'";
        }

        hql=hql+" order by u.flag desc";

        Finder f=Finder.create(hql);

        return  find(f, current, preNum);
    }

    public int  disableUser(Long id){
        Query query = getSession().createQuery("update CreditUser bean set bean.backFlag=bean.flag,bean.flag =:flag where bean.id =:id");
        query.setParameter("id",id);
        query.setParameter("flag","9");
        int sum =  query.executeUpdate();

        return sum;
    }

    public int  enableUser(Long id){
        Query query = getSession().createQuery("update CreditUser bean set bean.flag =bean.backFlag where bean.id =:id");
        query.setParameter("id",id);
        int sum =  query.executeUpdate();

        return sum;
    }

    public List<CreditUser> userListByCondition(String condition){
        String hql="from CreditUser where " + condition;
        Finder f=Finder.create(hql);
        List<CreditUser> list =find(f);

        return list;
    }

    @Override
    protected Class<CreditUser> getEntityClass() {
        return CreditUser.class;
    }
}
