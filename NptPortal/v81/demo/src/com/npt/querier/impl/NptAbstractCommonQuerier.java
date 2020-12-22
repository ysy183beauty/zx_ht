package com.npt.querier.impl;

import com.jeecms.core.entity.CmsUser;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.dataBinder.NptWebDetailBlock;
import com.npt.bridge.dataBinder.NptWebFieldDataArray;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.*;
import com.npt.bridge.model.service.NptBaseModelService;
import com.npt.querier.NptCommonQuerier;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 项目： CreditPortal
 * 作者： owen
 * 时间： 2017/2/20 11:28
 * 描述：
 */
public abstract class NptAbstractCommonQuerier implements NptCommonQuerier {

    @Autowired
    private NptBaseModelService service;

    public NptBaseModelService getService() {
        return service;
    }

    /**
     * 作者：owen
     * 时间: 2017/2/20 11:17
     * 描述:
     * 获取某一专题的模型的基础信息
     */
    @Override
    public abstract NptBaseModel getThisModel();

    /**
     * 作者：owen
     * 时间: 2017/2/20 11:18
     * 描述:
     * 获取某一专题的模型的分组信息
     *
     * @param model
     */
    @Override
    public Collection<NptBaseModelGroup> getBaseModelGroups(NptBaseModel model) {
        return service.getBaseModelGroups(model);
    }

    @Override
    public Collection<NptBaseModelGroup> getBaseModelGroups(Long modelId) {
        NptBaseModel model = service.fastFindBaseModelById(modelId);
        if (model != null) {
            return service.getBaseModelGroups(model);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 作者：owen
     * 时间: 2017/2/20 11:19
     * 描述:
     * 获取某一专题的模型的所有数据池信息
     *
     * @param model
     */
    @Override
    public Collection<NptBaseModelPool> getBaseModelPools(NptBaseModel model) {
        Collection<NptBaseModelPool> result = new ArrayList<>();
        Collection<NptBaseModelGroup> groups = service.getBaseModelGroups(model);
        for (NptBaseModelGroup group : groups) {
            Collection<NptBaseModelPool> pools = service.getBaseModelGrouPools(group, null);
            result.addAll(pools);
        }
        return result;
    }

    @Override
    public Collection<NptBaseModelPool> getBaseModelPools(Long modelId) {
        NptBaseModel model = service.fastFindBaseModelById(modelId);
        if (model != null) {
            return this.getBaseModelPools(model);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 作者：owen
     * 时间: 2017/2/20 11:20
     * 描述:
     * 获取某一模型分组的所有数据池
     *
     * @param group
     */
    @Override
    public Collection<NptBaseModelPool> getBaseModelGroupPools(NptBaseModelGroup group) {
        Collection<NptBaseModelPool> result = service.getBaseModelGrouPools(group, null);

        Map<String, Collection<NptBaseModelPool>> map = new HashMap<>();
        groupByProvider(map, result);

        Collection<NptBaseModelPool> sortedPools = new ArrayList<>();
        if (null != map && !map.isEmpty()) {
            for (String pid : map.keySet()) {
                sortedPools.addAll(map.get(pid));
            }
        }
        return sortedPools;
    }

    @Override
    public Collection<NptBaseModelPool> getBaseModelGroupPools(Long groupId) {
        NptBaseModelGroup group = service.fastFindBaseModelGroupById(groupId);
        if (group != null) {
            return getBaseModelGroupPools(group);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Collection<NptBaseModelPool> getBaseModelGroupPools(NptDict gCode) {
        NptBaseModelGroup group = service.getModelGroupByCode(this.getThisModel(), gCode);

        if (null == group) {
            return new ArrayList<>();
        }
        return getBaseModelGroupPools(group);
    }

    /**
     * 作者：owen
     * 时间：2017/3/17 下午12:36
     * 描述：
     * 以数据提供者进行分组的数据池
     *
     * @param model
     */
    @Override
    public Map<String, Collection<NptBaseModelPool>> getBaseModelProviderPoolsMap(NptBaseModel model) {

        Map<String, Collection<NptBaseModelPool>> result = new HashMap<>();
        if (null == model) {
            return result;
        }

        Collection<NptBaseModelPool> pools = getBaseModelPools(model);

        groupByProvider(result, pools);

        return result;
    }

    /**
     * 作者：owen
     * 时间：2017/3/17 下午2:51
     * 描述：
     * <p>
     * map
     */
    private void groupByProvider(Map<String, Collection<NptBaseModelPool>> map, Collection<NptBaseModelPool> pl) {
        if (null != pl && !pl.isEmpty() && null != map) {
            for (NptBaseModelPool p : pl) {
                if (map.keySet().contains(p.getProviderTitle())) {
                    map.get(p.getProviderTitle()).add(p);
                } else {
                    Collection<NptBaseModelPool> tmp = new ArrayList<>();
                    tmp.add(p);
                    map.put(p.getProviderTitle(), tmp);
                }
            }
        }
    }

    @Override
    public Map<String, Collection<NptBaseModelPool>> getBaseModelProviderPoolsMap(Long modelId) {
        NptBaseModel model = service.fastFindBaseModelById(modelId);

        return getBaseModelProviderPoolsMap(model);
    }

    /**
     * 作者：owen
     * 时间：2017/3/17 下午12:41
     * 描述：
     * 以数据提供者进行分组的数据池
     *
     * @param group
     */
    @Override
    public Map<String, Collection<NptBaseModelPool>> getBaseModelGroupProviderPoolsMap(NptBaseModelGroup group) {
        Map<String, Collection<NptBaseModelPool>> result = new HashMap<>();
        if (null == group) {
            return result;
        }

        Collection<NptBaseModelPool> pools = getBaseModelGroupPools(group);

        groupByProvider(result, pools);

        return result;
    }

    @Override
    public Map<String, Collection<NptBaseModelPool>> getBaseModelGroupProviderPoolsMap(Long groupId) {
        NptBaseModelGroup group = service.fastFindBaseModelGroupById(groupId);

        return getBaseModelGroupProviderPoolsMap(group);
    }

    @Override
    public Map<String, Collection<NptBaseModelPool>> getBaseModelGroupProviderPoolsMap(NptDict gCode) {
        NptBaseModelGroup group = service.getModelGroupByCode(this.getThisModel(), gCode);

        return getBaseModelGroupProviderPoolsMap(group);
    }

    /**
     * 作者：owen
     * 时间: 2017/2/20 11:24
     * 描述:
     * 获取某一模型的主字段分页数据
     *
     * @param model
     * @param bean
     */
    @Override
    public NptDict getBaseModelIndexFieldPaginationData(NptBaseModel model, NptWebBridgeBean bean, Boolean exact) {
        return service.getBaseModelIndexFieldPaginationData(model, bean, exact);
    }

    @Override
    public NptDict getBaseModelIndexFieldPaginationData(Long modelId, NptWebBridgeBean bean, Boolean exact) {
        NptBaseModel model = service.fastFindBaseModelById(modelId);
        if (model != null) {
            return getBaseModelIndexFieldPaginationData(model, bean, exact);
        } else {
            return NptDict.RST_EXCEPTION("指定的模型不存在:模型ID[" + modelId + "]");
        }
    }

    /**
     * 作者：owen
     * 时间: 2017/2/20 11:25
     * 描述:
     * 获取某一数据池的分页数据
     *
     * @param pool
     * @param bean
     */
    @Override
    public NptDict getBaseModelPoolPaginationData(NptBaseModelPool pool, NptWebBridgeBean bean, Boolean onlyIndex, Boolean exact) {
        return service.getBaseModelGroupoolPaginationData(pool.getId(), bean, onlyIndex, exact);
    }

    @Override
    public NptDict getBaseModelPoolPaginationData(Long poolId, NptWebBridgeBean bean, Boolean onlyIndex, Boolean exact) {
        if (bean.getWebQueryCondition() != null) {
            List<NptBaseModelPoolCdt> webQueryCondition = bean.getWebQueryCondition();
            List<Map<String, String>> queryCondition = new ArrayList<>();
            for (NptBaseModelPoolCdt condition : webQueryCondition) {
                Map<String, String> map = new HashMap<>();
                map.put("name", condition.getFieldDBName());
                map.put("value", condition.getFieldQueryValue());
                queryCondition.add(map);
            }
            bean.setQueryCondition(queryCondition);
        } else {
            List<NptBaseModelPoolCdt> conditions = new ArrayList<>(this.getBaseModelPoolConditions(poolId));
            bean.setWebQueryCondition(conditions);
        }
        return service.getBaseModelGroupoolPaginationData(poolId, bean, onlyIndex, exact);
    }

    /**
     * 作者：owen
     * 日期：2016/10/20 16:07
     * 备注：
     * 加载指定模型的某条业务记录的详细分组数据
     * <p>
     * 这里有三项入口信息：数据主键，数据主键的值，业务主键
     * 其中，数据主键及其值用于定位表里的唯一一行记录
     * 业务主键用于主数据向副数据的扩展
     * 参数：
     * 返回：
     */
    @Override
    public NptDict loadBaseModelAuthGroupsByUK(NptWebBridgeBean bean, NptBaseModel m,Boolean applyed) {
        return service.loadBaseModelAuthGroupsByUK(bean, m,applyed);
    }

    public NptDict loadBaseModelAuthGroupsByPK(NptWebBridgeBean bean, NptBaseModel m, NptDict lockLevel,Boolean applyed) {
        return service.loadBaseModelAuthGroupsByPK(bean, m, lockLevel,applyed);
    }

    public NptWebDetailBlock getBaseModelGrouPoolData(Long poolId, String primaryKeyValue,NptDict lockLevel,Boolean b,Boolean applyed) {
        return service.getBaseModelGrouPoolData(poolId, primaryKeyValue,lockLevel,b,applyed);
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/14 下午02:20
     * 备注: 获取pool的查询条件
     */
    public Collection<NptBaseModelPoolCdt> getBaseModelPoolConditions(Long poolId) {
        return getService().getBaseModelPoolConditions(poolId);
    }


    /**
     * 作者：owen
     * 时间：2017/3/17 下午3:55
     * 描述：
     * 获取指定数据池指定数据主键的唯一一行记录
     *
     * @param poolId
     * @param uFieldValue
     */
    @Override
    public NptWebFieldDataArray getBaseModelPoolRowData(Long poolId, String uFieldValue) {
        return getService().loadBaseModelPoolRowData(poolId, uFieldValue);
    }

    /**
     * author: owen
     * date:   2017/3/23 下午1:59
     * note:
     * 在模型内部以关键字进行模糊查询，查询依据为模型中主数据池（若存在）或每个普通数据
     * 池的条件字段
     *
     * @param keyword
     * @param bean
     */
    @Override
    public NptDict fuzzySearch(String keyword, NptWebBridgeBean bean) {

        NptBaseModel model = getThisModel();
        if (null == model) {
            return NptDict.RST_EXCEPTION("该服务暂未开放");
        }
        if (StringUtils.isBlank(keyword)) {
            return NptDict.RST_EXCEPTION("查询关键字为空");
        }
        if (null == bean) {
            return NptDict.RST_EXCEPTION("系统内部错误");
        }

        NptBaseModelPool mainPool = getService().getBaseModelGroupMainPool(model);

        if (null != mainPool) {
            /**
             * 若模型存在核心数据池，则模糊查询以其核心数据池的条件字段为查询依据，在核心数据池中进行
             * 条件字段[或]关系的模糊查询
             */
            return fuzzySearchFromMainPool(bean, mainPool, keyword);
        } else {
            /**
             * 若模型不存在核心数据池，则模糊查询依次在相应的数据池中以其自身的条件字段的[或]关系进行
             * 模糊查询
             */
            return fuzzySearchFromEveryPool(model, keyword, bean);
        }
    }

    /**
     * author: owen
     * date:   2017/3/23 下午2:09
     * note:
     * 在主数据池中进行多条件字段的模糊查询
     */
    protected NptDict fuzzySearchFromMainPool(NptWebBridgeBean bean, NptBaseModelPool pool, String keyword) {

        return getService().fuzzySearchFromMainPool(pool, keyword, NptDict.FAL_SOPEN, bean);
    }

    /**
     * author: owen
     * date:   2017/3/23 下午2:10
     * note:
     * 在模型中的每个数据池进行模糊查询
     */
    protected NptDict fuzzySearchFromEveryPool(NptBaseModel model, String keyword, NptWebBridgeBean bean) {

        Collection<NptBaseModelPool> pools = getService().getBaseModelGrouPools(model, NptDict.MPL_LEVEL_0);
        if (null != pools && !pools.isEmpty()) {
            Map<String, NptBaseModelPoolRowsData> mapResult = new HashMap<>();
            for (NptBaseModelPool p : pools) {
                Collection<NptWebFieldDataArray> poolResult = getService().fuzzySearchFromCommonPool(p, keyword, NptDict.FAL_SOPEN);
                if (null != poolResult && !poolResult.isEmpty()) {
                    String key = p.getPoolTitle();
                    NptBaseModelPoolRowsData rowsData = new NptBaseModelPoolRowsData();
                    rowsData.setPool(p);
                    rowsData.setTitleValues(poolResult);
                    mapResult.put(key, rowsData);
                }
            }
            bean.setDataList(mapResult);
        }
        return NptDict.RST_SUCCESS;
    }


    /**
     * author: owen
     * date:   2017/3/21 下午8:45
     * note:
     * 加载已实名认证的个人的信用信息
     *
     * @param user
     */
    @Override
    public NptDict loadAuthedInfo(NptBaseModel model,NptWebBridgeBean bean, CmsUser user,Boolean applyed) {

        String userName = user.getRealname();
        String idCard = user.getIdCard();

        if (null == userName || StringUtils.isBlank(userName) || null == idCard || StringUtils.isBlank(idCard)) {
            return NptDict.RST_EXCEPTION("登录用户[" + user.getUsername() + "]的实名认证信息存在异常！");
        }

        if (null == model) {
            return NptDict.RST_EXCEPTION("实名认证信用查询服务暂未开放");
        }

        NptDict inService = getService().isBaseModelQueryInService(model);
        if (!NptDict.RST_SUCCESS.equals(inService)) {
            return inService;
        }

        NptDict userInfoExisted = getService().isBaseModelAuthPKValueExisted(model, idCard);
        if (NptDict.RST_SUCCESS.equals(userInfoExisted)) {
            bean.setPrimaryKeyValue(idCard);
            return getService().loadBaseModelAuthGroupsByPK(bean, model, null,applyed);
        } else {
            return userInfoExisted;
        }
    }

    /**
     * author: owen
     * date:   2017/4/17 下午2:52
     * note:
     * 获取链式模型（有核心数据池）的信息体量
     *
     */
    @Override
    public Long getBaseModelEntityCount() {
        Long result = 0L;

        NptBaseModel model = getThisModel();
        if(null != model && model.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
            NptBaseModelPool mainPool = getService().getBaseModelGroupMainPool(model);
            if(null != mainPool && mainPool.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
                result = getService().getBaseModelPoolDataCount(mainPool);
            }
        }

        return result;
    }
}