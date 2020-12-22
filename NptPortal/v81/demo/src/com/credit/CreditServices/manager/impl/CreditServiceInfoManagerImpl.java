package com.credit.CreditServices.manager.impl;

import com.credit.CreditServices.dao.CreditServiceInfoDao;
import com.credit.CreditServices.entity.CreditServiceInfo;
import com.credit.CreditServices.manager.CreditServiceInfoManager;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.web.springmvc.RealPathResolver;
import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.base.manager.NptBaseManagerImpl;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.ImageBase64Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * 项目: zxcms
 * 作者: 张磊
 * 日期: 17/3/7 下午4:54
 * 备注:
 */
@Service
@Transactional
public class CreditServiceInfoManagerImpl extends NptBaseManagerImpl<CreditServiceInfo, Long> implements CreditServiceInfoManager {
    @Autowired
    private CreditServiceInfoDao dao;
    @Autowired
    private RealPathResolver realPathResolver;

    /**
     * 作者: 张磊
     * 日期: 17/3/7 下午8:44
     * 备注:
     */
    @Override
    public Pagination getPage(int userId, String flag, int pageNo, int pageSize) {
        return dao.getPage(userId, flag, pageNo, pageSize);
    }

    @Override
    public CreditServiceInfo detail(int userId, Long id) {
        return dao.detail(userId, id);
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/22 上午11:06
     * 备注: 根据状态查
     */
    @Override
    public Collection<CreditServiceInfo> findByStatus(NptDict code) {
        Collection<CreditServiceInfo> infos = dao.findByStatus(code);
        Iterator<CreditServiceInfo> iterator = infos.iterator();
        while (iterator.hasNext()) {
            CreditServiceInfo info = iterator.next();
            if (info.getAttach() != null) {
                try {
                    String s = ImageBase64Utils.imageToBase64(realPathResolver.get(info.getAttach()));
                    info.setAttachFile(s);
                } catch (IOException e) {
                    iterator.remove();
                    e.printStackTrace();
                }
            }
        }
        return infos;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/01 下午09:56
     * 备注: 查询附件路径
     */
    @Override
    public String getAttach(Integer userId, Long infoId) {
        CreditServiceInfo info = dao.findById(infoId);
        if (userId.equals(info.getUser().getId())) {
            return info.getAttach();
        }
        return null;
    }

    @Override
    public NptBaseDao<CreditServiceInfo, Long> getThisDao() {
        return dao;
    }

}
