package com.npt.arch;

import com.npt.bridge.arch.dao.NptLogicDataProviderDao;
import com.npt.base.NptBaseDaoImpl;
import com.npt.bridge.arch.NptLogicDataProvider;
import org.springframework.stereotype.Repository;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2017/1/16 21:05
 * 描述:
 */
@Repository
public class NptLogicDataProviderDaoImpl extends NptBaseDaoImpl<NptLogicDataProvider, Long> implements NptLogicDataProviderDao {
    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    @Override
    protected Class<NptLogicDataProvider> getEntityClass() {
        return NptLogicDataProvider.class;
    }
}
