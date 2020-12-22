package com.npt.rms.base.manager.impl;

import com.npt.bridge.base.NptEntitySerializable;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.base.manager.NptCachedManager;
import com.npt.rms.util.NptRmsUtil;
import org.springframework.util.Assert;
import org.summer.extend.cache.ICache;
import org.summer.extend.cache.IElement;
import org.summer.extend.manager.BaseManagerImpl;

import java.io.Serializable;

/**
 * 项目：NPTWebApp
 * 作者：OWEN
 * 时间：2016/11/28 21:25
 * 描述:
 */
public abstract class NptCachedManagerImpl<T extends NptEntitySerializable> extends BaseManagerImpl<T> implements NptCachedManager<T>{

    /**
     *作者：OWEN
     *时间：2016/11/28 21:43
     *描述:
     *      此方法的作用是指定实体在缓存中的key
     *
     *      这里所有的实体都是NptEntitySerializable的子类，故统一采用id作为缓存的key
     */
    protected  Serializable getCacheKey(T var1){
        NptEntitySerializable nptObject = (NptEntitySerializable) var1;
        return nptObject.getId();
    }

    /**
     *作者：OWEN
     *时间：2016/11/29 11:34
     *描述:
     *      获取每个实体管理器的自身注入的缓存实体
     */
    protected abstract ICache getLocalEhcache();

    /**
     * 作者：OWEN
     * 时间：2016/12/7 23:24
     * 描述:
     * 从缓存中快速获取实体
     * 若取得实体是为了更新，不要使用改方法，请使用findById
     *
     * @param id
     */
    @Override
    public T fastFindById(Serializable id) {
        IElement elem = this.getLocalEhcache().get(id);
        T t = null;
        if(null == elem || (null != elem && null == elem.getValue())) {
            t = super.findById(id);
            if(null != t) {
                this.getLocalEhcache().put(id, t);
            }
        } else {
            t = (T)elem.getValue();
        }
        return t;
    }

    @Override
    public void delete(T entity) {
        super.delete(entity);
        if(this.getLocalEhcache().isKeyInCache(getCacheKey(entity))) {
            this.getLocalEhcache().remove(getCacheKey(entity));
        }
    }

    /**
     *作者：OWEN
     *时间：2016/11/29 11:37
     *描述:
     *      重写update方法，首先使用基类方法更新DB，然后替换缓存中的实体信息
     */
    @Override
    public T update(T entity) {
        Assert.notNull(entity);

        entity.setModifyId(NptRmsUtil.getCurrentUserId());
        entity.setLastModifyTime(NptCommonUtil.getCurrentSysDate());

        T result = super.update(entity);
        if(null != result) {
            if (this.getLocalEhcache().isKeyInCache(getCacheKey(entity))) {
                this.getLocalEhcache().remove(getCacheKey(entity));
            }
            this.getLocalEhcache().put(getCacheKey(entity), result);
        }
        return result;
    }
}
