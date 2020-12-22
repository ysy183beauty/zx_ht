package com.credit.CreditServices.dao.impl;

import com.credit.CreditServices.dao.CreditServiceInfoDao;
import com.credit.CreditServices.entity.CreditServiceInfo;
import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.page.Pagination;
import com.npt.base.NptBaseDaoImpl;
import com.npt.bridge.dict.NptDict;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 项目: zxcms
 * 作者: 张磊
 * 日期: 17/3/7 下午4:41
 * 备注:
 */
@Repository
public class CreditServiceInfoDaoImpl extends NptBaseDaoImpl<CreditServiceInfo, Long> implements CreditServiceInfoDao {
    @Override
    protected Class<CreditServiceInfo> getEntityClass() {
        return CreditServiceInfo.class;
    }

    /**
     * 作者: 张磊
     * 日期: 17/3/7 下午8:44
     * 备注:
     */
    @Override
    public Pagination getPage(int userId, String flag, int pageNo, int pageSize) {
        String hql = "from CreditServiceInfo bean where 1=1 ";
        Finder f = Finder.create(hql);
        f.append(" and bean.user.id = :userId").setParam("userId", userId);
        if (flag.equals(String.valueOf(NptDict.CSF_OBJECTION.getCode()))) {
            f.append(" and (bean.flag = :flag or bean.flag = " + NptDict.CSF_SELF.getCode() + ")").setParam("flag", flag);
        } else {
            f.append(" and bean.flag = :flag").setParam("flag", flag);
        }
        f.append(" order by bean.createTime desc");
        return find(f, pageNo, pageSize);
    }

    @Override
    public CreditServiceInfo detail(int userId, Long id) {
        String hql = "from CreditServiceInfo bean where 1=1 ";
        Finder f = Finder.create(hql);
        f.append(" and bean.user.id = :userId").setParam("userId", userId);
        f.append(" and bean.id = :id").setParam("id", id);
        List list = find(f);
        return list.size() == 1 ? (CreditServiceInfo) list.get(0) : null;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/22 上午11:06
     * 备注: 根据状态查
     */
    @Override
    public Collection<CreditServiceInfo> findByStatus(NptDict code) {
        String hql = "from CreditServiceInfo bean where 1=1 ";
        Finder f = Finder.create(hql);
        f.append(" and bean.syncStatus = :syncStatus").setParam("syncStatus", code.getCode());
        f.append(" order by bean.createTime desc");
        return find(f);
    }
}
