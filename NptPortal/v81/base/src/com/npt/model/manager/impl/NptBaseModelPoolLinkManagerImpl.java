package com.npt.model.manager.impl;

import com.npt.dict.NptDict;
import com.npt.model.dao.NptBaseModelPoolLinkDao;
import com.npt.model.entity.NptBaseModelPool;
import com.npt.model.entity.NptBaseModelPoolLink;
import com.npt.model.manager.NptBaseModelPoolLinkManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 17:11
 * 描述:
 */
@Service
@Transactional
public class NptBaseModelPoolLinkManagerImpl implements NptBaseModelPoolLinkManager{

    @Autowired
    private NptBaseModelPoolLinkDao poolLinkDao;

    /**
     * 作者：owen
     * 时间：2017/1/16 20:59
     * 描述:
     * 依据ID查找
     *
     * @param id
     */
    @Override
    public NptBaseModelPoolLink findById(Long id) {
        return poolLinkDao.findById(id);
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
    public NptBaseModelPoolLink fastFindById(Long id) {
        return poolLinkDao.findById(id);
    }

    /**
     * 作者：owen
     * 时间：2017/1/16 21:18
     * 描述:
     * 加载列表
     */
    @Override
    public Collection<NptBaseModelPoolLink> getList() {
        return poolLinkDao.getList();
    }

    @Override
    public void save(NptBaseModelPoolLink model) {
        poolLinkDao.save(model);
    }

    @Override
    public void delete(NptBaseModelPoolLink entity) {
        poolLinkDao.delete(entity);
    }

    @Override
    public void update(NptBaseModelPoolLink entity) {
        poolLinkDao.update(entity);
    }

    @Override
    public void updateAll(Collection<NptBaseModelPoolLink> list) {
        poolLinkDao.updateAll(list);
    }

    @Override
    public void saveAll(Collection<NptBaseModelPoolLink> list) {
        poolLinkDao.saveAll(list);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午5:30
     * 备注: 检测当前数据池是否有关联的数据池
     */
    @Override
    public Integer getBaseModelGrouPoolLinkCount(NptBaseModelPool p, NptDict status) {
        if (p == null) return 0;
        return poolLinkDao.getBaseModelGrouPoolLinkCount(p, status);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/16 上午10:9
     * 备注:
     */
    @Override
    public Collection<NptBaseModelPoolLink> getBaseModelGroupoolLinkedPools(NptBaseModelPool pool, NptDict idsEnabled) {
        if (pool == null) return null;
        return poolLinkDao.getBaseModelGroupoolLinkedPools(pool, idsEnabled);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/17 下午2:52
     * 备注: 查询关联向指定数据池的其它数据池, status为null则表示全部状态
     */
    @Override
    public Collection<NptBaseModelPoolLink> getBaseModelGroupoolLinkedMePools(NptBaseModelPool p, NptDict status) {
        return poolLinkDao.getBaseModelGroupoolLinkedMePools(p, status);
    }
}
