package com.npt.bridge.model.manager.impl;

import com.npt.bridge.base.NptBaseDao;
import com.npt.bridge.base.manager.NptBaseManagerImpl;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.NptBaseModelPoolIndex;
import com.npt.bridge.model.dao.NptBaseModelDao;
import com.npt.bridge.model.manager.NptBaseModelManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 16:59
 * 描述:
 */
@Service
@Transactional
public class NptBaseModelManagerImpl extends NptBaseManagerImpl<NptBaseModel, Long> implements NptBaseModelManager {

    @Autowired
    private NptBaseModelDao modelDao;

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
    public Collection<NptBaseModelPoolIndex> getBaseModelGrouPoolMainFields(NptBaseModelPool p) {
        Collection<NptBaseModelPoolIndex> result = new ArrayList<>();
        Collection<NptBaseModelPoolIndex> searchR = modelDao.getBaseModelPoolIndexFields(p);

        Collection<Long> fieldIds = new ArrayList<>();
        if(null != searchR && !searchR.isEmpty()){
            for(NptBaseModelPoolIndex i:searchR){
                if(!fieldIds.contains(i.getFieldId())){
                    result.add(i);
                    fieldIds.add(i.getFieldId());
                }
            }
        }

        return result;
    }

    @Override
    public NptBaseDao<NptBaseModel, Long> getThisDao() {
        return modelDao;
    }
}
