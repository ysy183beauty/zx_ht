package com.npt.model.manager.impl;

import com.npt.dict.NptDict;
import com.npt.model.dao.NptBaseModelDao;
import com.npt.model.entity.NptBaseModel;
import com.npt.model.entity.NptBaseModelGroup;
import com.npt.model.entity.NptBaseModelMainField;
import com.npt.model.entity.NptBaseModelPool;
import com.npt.model.manager.NptBaseModelManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 16:59
 * 描述:
 */
@Service
@Transactional
public class NptBaseModelManagerImpl implements NptBaseModelManager{

    @Autowired
    private NptBaseModelDao modelDao;

    /**
     * 作者：owen
     * 时间：2017/1/16 20:59
     * 描述:
     * 依据ID查找
     *
     * @param id
     */
    @Override
    public NptBaseModel findById(Long id) {
        return modelDao.findById(id);
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
    public NptBaseModel fastFindById(Long id) {
        return modelDao.findById(id);
    }

    /**
     * 作者：owen
     * 时间：2017/1/16 21:18
     * 描述:
     * 加载列表
     */
    @Override
    public Collection<NptBaseModel> getList() {
        return modelDao.getList();
    }

    @Override
    public void save(NptBaseModel model) {
        modelDao.save(model);
    }

    @Override
    public void delete(NptBaseModel entity) {
        modelDao.delete(entity);
    }

    @Override
    public void update(NptBaseModel entity) {
        modelDao.update(entity);
    }

    @Override
    public void updateAll(Collection<NptBaseModel> list) {
        modelDao.updateAll(list);
    }

    @Override
    public void saveAll(Collection<NptBaseModel> list) {
        modelDao.saveAll(list);
    }

    /**
     * 作者：owen
     * 时间：2017/2/15 10:38
     * 描述:
     * 依据模型类别和模型主体查询模型
     *
     * @param cate
     * @param host
     */
    @Override
    public Collection<NptBaseModel> findModelByCategoryAndHost(NptDict cate, NptDict host) {
        return modelDao.findModelByCategoryAndHost(cate,host);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 上午11:37
     * 备注: 查询模型的所有分组
     */
    @Override
    public Collection<NptBaseModelGroup> lookupModelGroups(NptBaseModel model) {
        if (model == null) return null;
        return modelDao.lookupModelGroups(model);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 上午11:41
     * 备注: 查询模型指定分组的所有数据池
     */
    @Override
    public Collection<NptBaseModelPool> lookupModelGroupPools(NptBaseModelGroup group) {
        if (group == null) return null;
        return modelDao.lookupModelGroupPools(group);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午2:39
     * 备注: 获取用户指定的数据池的主字段
     */
    @Override
    public Collection<NptBaseModelMainField> getBaseModelGrouPoolMainFields(NptBaseModelPool p) {
        if (p == null) return null;
        return modelDao.getBaseModelGrouPoolMainFields(p);
    }
}
