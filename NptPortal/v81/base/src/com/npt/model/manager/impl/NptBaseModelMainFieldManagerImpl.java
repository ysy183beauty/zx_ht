package com.npt.model.manager.impl;

import com.npt.arch.entity.NptLogicDataField;
import com.npt.model.dao.NptBaseModelMainFieldDao;
import com.npt.model.entity.NptBaseModel;
import com.npt.model.entity.NptBaseModelMainField;
import com.npt.model.manager.NptBaseModelMainFieldManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:12
 * 描述:
 */
@Service
@Transactional
public class NptBaseModelMainFieldManagerImpl implements NptBaseModelMainFieldManager{

    @Autowired
    private NptBaseModelMainFieldDao mainFieldDao;

    /**
     * 作者：owen
     * 时间：2017/1/16 20:59
     * 描述:
     * 依据ID查找
     *
     * @param id
     */
    @Override
    public NptBaseModelMainField findById(Long id) {
        return mainFieldDao.findById(id);
    }

    /**
     * 作者：owen
     * 时间：2017/1/16 21:00
     * 描述:
     * 优先从缓存中查找
     *
     * @param id
     */
    @Override
    public NptBaseModelMainField fastFindById(Long id) {
        return mainFieldDao.findById(id);
    }

    /**
     * 作者：owen
     * 时间：2017/1/16 21:18
     * 描述:
     * 加载列表
     */
    @Override
    public Collection<NptBaseModelMainField> getList() {
        return mainFieldDao.getList();
    }

    @Override
    public void save(NptBaseModelMainField model) {
        mainFieldDao.save(model);
    }

    @Override
    public void delete(NptBaseModelMainField entity) {
        mainFieldDao.delete(entity);
    }

    @Override
    public void update(NptBaseModelMainField entity) {
        mainFieldDao.update(entity);
    }

    @Override
    public void updateAll(Collection<NptBaseModelMainField> list) {
        mainFieldDao.updateAll(list);
    }

    @Override
    public void saveAll(Collection<NptBaseModelMainField> list) {
        mainFieldDao.saveAll(list);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午3:10
     * 备注: 获取字段详情
     */
    @Override
    public NptLogicDataField getBaseModelGrouPoolFieldById(Long id) {
        return mainFieldDao.getBaseModelGrouPoolFieldById(id);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午11:41
     * 备注: 确定某个模型的主字段列表
     */
    @Override
    public Collection<NptBaseModelMainField> getBaseModelMainFields(NptBaseModel m) {
        return mainFieldDao.getBaseModelMainFields(m);
    }
}
