package com.npt.bridge.model.service.impl;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.arch.service.NptArchService;
import com.npt.bridge.dataBinder.*;
import com.npt.bridge.database.manager.NptDatabaseManager;
import com.npt.bridge.database.service.NptDataBaseService;
import com.npt.bridge.dict.NptBusinessCode;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.*;
import com.npt.bridge.model.manager.*;
import com.npt.bridge.model.service.NptBaseModelService;
import com.npt.bridge.util.NptCommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 12:04
 * 描述:
 *
 *      模型查询服务类
 */
@Service
@Transactional
public class NptBaseModelServiceImpl implements NptBaseModelService {

    @Autowired
    private NptBaseModelManager modelManager;
    @Autowired
    private NptBaseModelGroupManager groupManager;
    @Autowired
    private NptBaseModelPoolManager poolManager;
    @Autowired
    private NptBaseModelPoolIndexManager poolIndexManager;
    @Autowired
    private NptBaseModelPoolLinkManager poolLinkManager;
    @Autowired
    private NptBaseModelPoolConditionManager poolConditionManager;
    @Autowired
    private NptArchService archService;
    @Autowired
    private NptDataBaseService dataBaseService;
    @Autowired
    private NptEntityDataShowStyleController showStyleController;
    @Autowired
    private NptDatabaseManager databaseManager;
    @Autowired
    private NptRmsCommonService commonService;

    /**
     * 作者：owen
     * 日期：2016/10/20 14:21
     * 备注：
     * 获取指定模型的基本信息
     * 参数：
     * 返回：
     *
     * @param id
     */
    @Override
    public NptBaseModel findBaseModelById(Long id) {
        return modelManager.findById(id);
    }

    @Override
    public NptBaseModel fastFindBaseModelById(Long id) {
        return modelManager.fastFindById(id);
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/13 15:17
     * 描述:
     *
     * @param id
     */
    @Override
    public NptBaseModelGroup findBaseModelGroupById(Long id) {
        return null;
    }

    @Override
    public NptBaseModelGroup fastFindBaseModelGroupById(Long id) {
        return null;
    }

    @Override
    public NptBaseModelPool findBaseModelGrouPoolById(Long id) {
        NptBaseModelPool pool = poolManager.findById(id);
        if(loadBaseModelGroupoolTitle(pool)) {
            return pool;
        }else {
            return null;
        }
    }
    
    @Override
    public NptBaseModelPool fastFindBaseModelGrouPoolById(Long id) {
        NptBaseModelPool pool = poolManager.fastFindById(id);
        if(loadBaseModelGroupoolTitle(pool)) {
            return pool;
        }else {
            return null;
        }
    }

    private Boolean loadBaseModelGroupoolTitle(NptBaseModelPool pool){
        if(null == pool){
            return false;
        }

        NptLogicDataType type = archService.findDataTableById(pool.getDataTypeId());
        if(null == type || !type.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
            return false;
        }

        if(!StringUtils.isBlank(pool.getAlias())){
            pool.setPoolTitle(pool.getAlias());
        }else {
            if (null != type) {
                pool.setPoolTitle(type.getTypeName());
            }
        }
        if(null != type){
            NptLogicDataProvider provider = archService.findParent(type);
            if(null != provider){
                pool.setProviderTitle(provider.getOrgName());
                pool.setProviderId(provider.getId());
            }
        }
        return true;
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午5:16
     * 备注: 检测当前数据池是否有关联的数据池
     */
    @Override
    public Integer getBaseModelGrouPoolLinkCount(NptBaseModelPool p, NptDict status) {
        return poolLinkManager.getBaseModelGrouPoolLinkCount(p, status);
    }
    
    /**
     * 作者：OWEN
     * 时间：2016/12/5 21:11
     * 描述:
     * 不做任何处理直接保存
     *
     * @param model
     */
    @Override
    public NptDict directSave(NptBaseModel model) {
        try {
            modelManager.save(model);
            return NptDict.RST_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return NptDict.RST_EXCEPTION;
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/12/5 21:11
     * 描述:
     * 不做任何处理直接保存
     *
     * @param group
     */
    @Override
    public NptDict directSave(NptBaseModelGroup group) {
        try {
            groupManager.save(group);
            return NptDict.RST_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return NptDict.RST_EXCEPTION;
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/12/5 21:11
     * 描述:
     * 不做任何处理直接保存
     *
     * @param pool
     */
    @Override
    public NptDict directSave(NptBaseModelPool pool) {
        try {
            poolManager.save(pool);
            return NptDict.RST_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return NptDict.RST_EXCEPTION;
        }
    }

    @Override
    public NptDict directSave(Collection<NptBaseModelPool> pools) {
        try {
            poolManager.saveAll(pools);
            return NptDict.RST_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return NptDict.RST_EXCEPTION(e.getMessage());
        }
    }

    /**
     * 作者：owen
     * 时间：2016/12/16 18:00
     * 描述:
     * 不做任何处理直接保存
     *
     * @param mf
     */
    @Override
    public NptDict directSave(NptBaseModelPoolIndex mf) {
        try {
            poolIndexManager.save(mf);
            return NptDict.RST_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return NptDict.RST_EXCEPTION;
        }
    }

    /**
     * 作者: owen
     * 时间: 2017/3/16 上午11:19
     * 描述:
     * 直接保存
     *
     * @param c
     */
    @Override
    public NptDict directSave(NptBaseModelPoolCdt c) {
        try {
            poolConditionManager.save(c);
            return NptDict.RST_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return NptDict.RST_EXCEPTION(e.getMessage());
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/12/6 20:53
     * 描述:
     *
     * @param modelId
     */
    @Override
    public NptDict deleteBaseModelById(Long modelId) {
        try {
            NptBaseModel model = modelManager.findById(modelId);
            if(null != model) {
                Collection<NptBaseModelGroup> groups = this.getBaseModelGroups(model);
                if (null != groups && !groups.isEmpty()) {
                    for (NptBaseModelGroup g : groups) {
                        this.deleteBaseModelGroupById(g.getId());
                    }
                }
                modelManager.delete(model);
            }
            return NptDict.RST_SUCCESS;
        }catch (Exception e){
            return NptDict.RST_EXCEPTION(e.getMessage());
        }
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午11:47
     * 备注: 获取基础模型的所有分组
     */
    @Override
    public Collection<NptBaseModelGroup> getBaseModelGroups(NptBaseModel m) {
        Collection<NptBaseModelGroup> result = groupManager.getBaseModelGroups(m);
        if(null != result && result.size() > NptCommonUtil.INTEGER_0){
            NptBaseModelGroup mainGroup = this.getBaseModelMainGroup(m);
            for (NptBaseModelGroup g : result) {
                if(null == mainGroup){
                    g.setMainGroup(NptDict.CLD_ADDITION.getCode());
                }else {
                    if (g.getId().equals(mainGroup.getId())) {
                        g.setMainGroup(NptDict.CLD_MAIN.getCode());
                    } else {
                        g.setMainGroup(NptDict.CLD_ADDITION.getCode());
                    }
                }
            }
        }
        return result;
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午11:55
     * 备注: 获取基础模型的权重组
     */
    @Override
    public NptBaseModelGroup getBaseModelMainGroup(NptBaseModel m) {
        NptBaseModelPool mainPool = this.getBaseModelGroupMainPool(m);
        if(null != mainPool){
            return groupManager.fastFindById(mainPool.getGroupId());
        }
        return null;
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午11:33
     * 备注: 确定某个模型的主字段列表
     */
    @Override
    public Collection<NptBaseModelPoolIndex> getBaseModelIndexFields(NptBaseModel m) {
        NptBaseModelPool mainPool = getBaseModelGroupMainPool(m);
        if(null == mainPool){
            return null;
        }
        return poolIndexManager.getBaseModelPoolIndex(mainPool);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午11:35
     * 备注: 获取模型的主数据池
     */
    @Override
    public NptBaseModelPool getBaseModelGroupMainPool(NptBaseModel m) {
        if (null == m) {
            return null;
        }
        NptBaseModelPool mainPool = poolManager.getBaseModelGroupMainPool(m);
        if(null != mainPool){
            if(loadBaseModelGroupoolTitle(mainPool)){
                return mainPool;
            }else {
                return null;
            }
        }
        return mainPool;
    }

    /**
     * 作者：OWEN
     * 时间：2016/12/6 20:53
     * 描述:
     *
     * @param groupId
     */
    @Override
    public NptDict deleteBaseModelGroupById(Long groupId) {
        try {
            NptBaseModelGroup group = groupManager.findById(groupId);
            if(null != group) {
                Collection<NptBaseModelPool> pools = this.getBaseModelGrouPools(group,null);
                if (null != pools && !pools.isEmpty()) {
                    for (NptBaseModelPool p : pools) {
                        this.deleteBaseModelGrouPoolById(p.getId());
                    }
                }
                groupManager.delete(group);
            }
            return NptDict.RST_SUCCESS;
        }catch (Exception e){
            return NptDict.RST_EXCEPTION(e.getMessage());
        }
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午11:59
     * 备注: 获取分组下的所有数据池
     */
    @Override
    public Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModelGroup group,NptDict lockLevel) {
        if(null == group){
            return new ArrayList<>();
        }

        Collection<NptBaseModelPool> pools = poolManager.getBaseModelGrouPools(group,lockLevel);

        return loadBaseModelGrouPoolTitle(pools);
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/13 下午01:47
     * 备注: 
     */
    @Override
    public Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModel m,NptDict lockLevel) {
        if(null == m){
            return new ArrayList<>();
        }
        Collection<NptBaseModelPool> pools = poolManager.getBaseModelGrouPools(m,lockLevel);
        return loadBaseModelGrouPoolTitle(pools);
    }
    
    private Collection<NptBaseModelPool> loadBaseModelGrouPoolTitle(Collection<NptBaseModelPool> pools) {
        Collection<NptBaseModelPool> result = new ArrayList<>();

        if(null != pools && !pools.isEmpty()) {
            for (NptBaseModelPool pool : pools) {
                if(loadBaseModelGroupoolTitle(pool)){
                    result.add(pool);
                }
            }
        }

        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/12/6 20:53
     * 描述:
     *
     * @param poolId
     */
    @Override
    public NptDict deleteBaseModelGrouPoolById(Long poolId) {
        try {
            NptBaseModelPool pool = poolManager.findById(poolId);
            if(null != pool) {

                deleteBaseModelPoolLinks(pool);

                deleteBaseModelPoolIndexs(pool);

                deleteBaseModelPoolConditions(pool);

                poolManager.delete(pool);
            }
            return NptDict.RST_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return NptDict.RST_EXCEPTION(e.getMessage());
        }

    }

    /**
     * 作者：owen
     * 时间：2017/3/17 上午10:28
     * 描述：
     *      删除数据池的模型关联信息
     */
    private void deleteBaseModelPoolLinks(NptBaseModelPool pool){
        if(null == pool){
            return;
        }

        Collection<NptBaseModelLink> p2pList = this.getBaseModelGroupoolLinkedPools(pool, null);
        Collection<NptBaseModelLink> meP2pList = this.getBaseModelGroupoolLinkedMePools(pool, null);

        if (null != p2pList && !p2pList.isEmpty()) {
            if (null != meP2pList) {
                p2pList.addAll(meP2pList);
            }

            for (NptBaseModelLink p2p : p2pList) {
                poolLinkManager.delete(p2p);
            }
        }
    }

    /**
     * 作者：owen
     * 时间：2017/3/17 上午10:29
     * 描述：
     *      删除数据池的索引字段
     */
    private void deleteBaseModelPoolIndexs(NptBaseModelPool pool){

        if(null == pool){
            return;
        }

        Collection<NptBaseModelPoolIndex> fields = this.getBaseModelPoolIndexFields(pool);
        if (null != fields && !fields.isEmpty()) {
            for (NptBaseModelPoolIndex f : fields) {
                poolIndexManager.delete(f);
            }
        }
    }

    /**
     * 作者：owen
     * 时间：2017/3/17 上午10:29
     * 描述：
     *      删除数据池的查询字段
     */
    private void deleteBaseModelPoolConditions(NptBaseModelPool pool){

        if(null == pool){
            return;
        }

        Collection<NptBaseModelPoolCdt> fields = getBaseModelPoolConditions(pool);
        if(null != fields && !fields.isEmpty()){
            for(NptBaseModelPoolCdt cdt:fields){
                poolConditionManager.delete(cdt);
            }
        }
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/17 下午2:43
     * 备注: 查询关联向指定数据池的其它数据池, status为null则表示全部状态
     */
    @Override
    public Collection<NptBaseModelLink> getBaseModelGroupoolLinkedMePools(NptBaseModelPool p, NptDict status) {
        if (null != p) {
            Collection<NptBaseModelLink> popList = poolLinkManager.getBaseModelGroupoolLinkedMePools(p, status);

            if (null != popList && !popList.isEmpty()) {
                for (NptBaseModelLink p2p : popList) {
                    NptBaseModelPool toPool = this.findBaseModelGrouPoolById(p2p.getToPoolId());
                    NptLogicDataProvider provicer = this.getBaseModelGrouPoolProvider(toPool);
                    NptLogicDataField fkField = archService.fastFindDataFieldById(p2p.getPoolRefKeyId());
                    if (null != provicer) {
                        p2p.setToPoolProviderTitle(provicer.getOrgName());
                    } else {
                        p2p.setToPoolProviderTitle(NptDict.RST_UNKNOW.getTitle());
                    }
                    if (null != toPool) {
                        p2p.setToPoolTitle(toPool.getPoolTitle());
                    } else {
                        p2p.setToPoolTitle(NptDict.RST_UNKNOW.getTitle());
                    }
                    if (null != fkField) {
                        p2p.setPoolRefKeyTitle(fkField.getAlias());
                    } else {
                        p2p.setPoolRefKeyTitle(NptDict.RST_UNKNOW.getTitle());
                    }
                }
            }
            return popList;
        }
        return null;
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/17 下午2:41
     * 备注: 
     */
    @Override
    public Collection<NptBaseModelLink> getBaseModelGroupoolLinkedPools(NptBaseModelPool p, NptDict status) {
        if(null != p) {
            Collection<NptBaseModelLink> popList = poolLinkManager.getBaseModelGroupoolLinkedPools(p, status);

            if(null != popList && !popList.isEmpty()){
                for(NptBaseModelLink p2p:popList){
                    NptBaseModelPool toPool = this.findBaseModelGrouPoolById(p2p.getToPoolId());
                    NptLogicDataProvider provicer = this.getBaseModelGrouPoolProvider(toPool);
                    NptLogicDataField fkField = archService.fastFindDataFieldById(p2p.getPoolRefKeyId());
                    if(null != provicer){
                        p2p.setToPoolProviderTitle(provicer.getOrgName());
                    }else {
                        p2p.setToPoolProviderTitle(NptDict.RST_UNKNOW.getTitle());
                    }
                    if(null != toPool){
                        p2p.setToPoolTitle(toPool.getPoolTitle());
                    }else {
                        p2p.setToPoolTitle(NptDict.RST_UNKNOW.getTitle());
                    }
                    if(null != fkField){
                        p2p.setPoolRefKeyTitle(fkField.getAlias());
                    }else {
                        p2p.setPoolRefKeyTitle(NptDict.RST_UNKNOW.getTitle());
                    }
                }
            }
            return popList;
        }
        return null;
    }

    /**
     * 作者：owen
     * 时间：2017/2/14 11:37
     * 描述:
     * 根据模型类别与模型主体定位唯一模型
     * <p>
     * 此方法作用于门户上的模型查询，比如定位红黑榜模型，定位双公示模型，定位企业信息模型等。
     * <p>
     * 若cate和host定位出多个模型，则取第一个模型
     *
     * @param cate
     * @param host
     */
    @Override
    public NptBaseModel lookupModelByCategoryAndHost(NptDict cate, NptDict host) {
        Collection<NptBaseModel> models = modelManager.findModelByCategoryAndHost(cate,host);
        if(null != models && !models.isEmpty()){
            return models.iterator().next();
        }
        return null;
    }

    /**
     * 作者：owen
     * 时间：2017/2/14 11:41
     * 描述:
     * 查询模型的所有分组
     *
     * @param model
     */
    @Override
    public Collection<NptBaseModelGroup> lookupModelGroups(NptBaseModel model) {
        return modelManager.lookupModelGroups(model);
    }

    /**
     * 作者：owen
     * 时间：2017/2/14 11:43
     * 描述:
     * 查询模型指定分组的所有数据池
     *
     * @param group
     */
    @Override
    public Collection<NptBaseModelPool> lookupModelGroupPools(NptBaseModelGroup group) {
        Collection<NptBaseModelPool> pools = modelManager.lookupModelGroupPools(group);
        return loadBaseModelGrouPoolTitle(pools);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午2:38
     * 备注: 获取用户指定的数据池的主字段
     */
    @Override
    public Collection<NptBaseModelPoolIndex> getBaseModelPoolIndexFields(NptBaseModelPool p) {
        return modelManager.getBaseModelGrouPoolMainFields(p);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午2:31
     * 备注: 获取主数据池的列表开放字段
     */
    @Override
    public Collection<NptLogicDataField> getBaseModelPoolIndexDataFields(NptBaseModelPool p) {
        Collection<NptBaseModelPoolIndex> mainFieldses = this.getBaseModelPoolIndexFields(p);
        List<NptLogicDataField> result = new ArrayList<NptLogicDataField>();
        if(null != p) {
            NptLogicDataType poolType = archService.findDataTableById(p.getDataTypeId());
            if(null != poolType && !NptCommonUtil.getDefaultParentId().equals(poolType.getUkFieldId())) {
                NptLogicDataField uniqueField = archService.findDataFieldById(poolType.getUkFieldId());
                if (null != uniqueField) {
                    uniqueField.setDisplay(NptCommonUtil.INTEGER_0);
                    result.add(uniqueField);
                    if (null != mainFieldses && !mainFieldses.isEmpty()) {
                        for (NptBaseModelPoolIndex field : mainFieldses) {
                            NptLogicDataField temp = archService.findDataFieldById(field.getFieldId());
                            if (null != temp && temp.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {
                                result.add(temp);
                            }
                        }
                    }
                }
            }
        }
        return result;

    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午2:31
     * 备注: 获取主数据池的物理表信息
     */
    @Override
    public NptLogicDataType getBaseModelGrouPoolDataType(NptBaseModelPool p) {
        if(null != p){
            return archService.findDataTableById(p.getDataTypeId());
        }
        return null;
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午2:31
     * 备注: 加载分页数据
     */
    @Override
    public NptDict getPaginationData(NptLogicDataType type, Collection<NptLogicDataField> fields, NptWebBridgeBean bean, Boolean applyed,Boolean exact) {
        //构建查询语句
        if(null != fields && fields.size() > 0 && null != type) {
            String[] sql = dataBaseService.makePaginationSql(
                    type.getTypeDbName(),
                    fields,
                    bean.getCurrPage(),
                    bean.getPageSize(),
                    bean.getQueryCondition(),
                    bean.getLimitCondition(),
                    bean.getOrderCondition(),
                    exact
            );

            int totalCount = dataBaseService.getCount(sql[0]);
            if(totalCount > 0){
                Collection<NptWebFieldDataArray> formatData = queryAndFormat(sql[1],fields,null,applyed);
                if(null != formatData && !formatData.isEmpty()) {
                    bean.setColumnTitles(formatData.iterator().next().getTitleList());
                    bean.setDataList(formatData);
                }
            } else {
                List<String> titles = fields.stream().map(p -> p.getAlias()).collect(Collectors.toList());
                // 设置title，用于前台确定colspan的值
                bean.setColumnTitles(titles);
            }
            bean.setTotalCount(totalCount);
            return NptDict.RST_SUCCESS;
        }
        return NptDict.RST_EXCEPTION("要查询的数据类别为空为字段列表为空");
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/16 上午9:54
     * 备注: 查询并格式化数据
     */
    private Collection<NptWebFieldDataArray> queryAndFormat(String sql, Collection<NptLogicDataField> fields, NptBaseModelPool pool, boolean applyed){
        try {
            List<Object> queryData = dataBaseService.queryList(sql,fields);
            if(null != queryData && !queryData.isEmpty()) {
                return formatTitleData(queryData, fields,pool,applyed);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/16 上午9:59
     * 备注: 格式化输出结果
     */
    private Collection<NptWebFieldDataArray> formatTitleData(List<Object> data, Collection<NptLogicDataField> fields, NptBaseModelPool pool, boolean applyed) {

        NptLogicDataField[] fieldArray = new NptLogicDataField[fields.size()];
        fields.toArray(fieldArray);

        /**
         * 汇总当前数据池的每个字段已存在的模型间业务外键
         */
        Collection<NptWebFieldDataArray> dataArrayList = new ArrayList<NptWebFieldDataArray>();
        Collection<Long> p2pFieldIdList = new ArrayList<>();
        if (null != pool) {
            Collection<NptBaseModelLink> p2pList = poolLinkManager.getBaseModelGroupoolLinkedPools(pool, NptDict.IDS_ENABLED);
            if (null != p2pList && !p2pList.isEmpty()) {
                for (NptBaseModelLink p2p : p2pList) {
                    NptBaseModelPool toPool = this.findBaseModelGrouPoolById(p2p.getToPoolId());
                    NptLogicDataProvider provicer = this.getBaseModelGrouPoolProvider(toPool);
                    NptLogicDataField fkField = archService.fastFindDataFieldById(p2p.getPoolRefKeyId());
                    if(null != provicer){
                        p2p.setToPoolProviderTitle(provicer.getOrgName());
                    }else {
                        p2p.setToPoolProviderTitle(NptDict.RST_UNKNOW.getTitle());
                    }
                    if(null != toPool){
                        p2p.setToPoolTitle(toPool.getPoolTitle());
                    }else {
                        p2p.setToPoolTitle(NptDict.RST_UNKNOW.getTitle());
                    }
                    if(null != fkField){
                        p2p.setPoolRefKeyTitle(fkField.getAlias());
                    }else {
                        p2p.setPoolRefKeyTitle(NptDict.RST_UNKNOW.getTitle());
                    }
                    p2pFieldIdList.add(p2p.getPoolRefKeyId());
                }
            }
        }
        /**
         * 数据行级循环
         */
        for (Object obj : data) {
            NptWebFieldDataArray dataArray = new NptWebFieldDataArray();
            Map<String, Object> temp = (Map<String, Object>) obj;
            Set<String> keys = temp.keySet();
            int index = NptCommonUtil.INTEGER_0;
            /**
             * 数据行中的字段循环
             */
            for (String title : keys) {
                Object value = temp.get(title);
                NptWebFieldDataArray.NptWebFieldData fieldData = dataArray.instanceFieldData();
                fieldData.setTitle(title);
                fieldData.setValue(String.valueOf(value));
                if (p2pFieldIdList.contains(fieldArray[index].getId())) {
                    fieldData.setLinked(NptCommonUtil.INTEGER_1);
                } else {
                    fieldData.setLinked(NptCommonUtil.INTEGER_0);
                }
                fieldData.setFieldId(fieldArray[index].getId());
                showStyleController.makeValueShowStyle(value, applyed, fieldArray[index], fieldData);

                index++;
                dataArray.getDataArray().add(fieldData);
            }
            dataArrayList.add(dataArray);
        }
        return dataArrayList;
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/16 上午10:27
     * 备注: 获取数据池的数据提供方信息
     */
    @Override
    public NptLogicDataProvider getBaseModelGrouPoolProvider(NptBaseModelPool p) {
        if(null != p){
            Long dataTypeId = p.getDataTypeId();
            NptLogicDataType dataType = archService.fastFindDataTableById(dataTypeId);
            if(null != dataType){
                return archService.findParent(dataType);
            }
        }
        return null;
    }

    @Override
    public NptDict getBaseModelGroupoolPaginationData(Long poolId, NptWebBridgeBean bean,Boolean onlyIndex,Boolean exact) {
        NptBaseModelPool pool = this.fastFindBaseModelGrouPoolById(poolId);
        if(null == pool || null == bean){
            return NptDict.RST_EXCEPTION("指定的数据池不存在:数据池ID[" + poolId + "]");
        }
        //获取数据池的物理表信息
        NptLogicDataType dataType = archService.findDataTableById(pool.getDataTypeId());
        if(null == dataType || !dataType.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
            return NptDict.RST_EXCEPTION("数据池对应的数据表不存在或已被禁用:数据池ID[" + poolId + "]");
        }

        Collection<NptLogicDataField> showFields;
        if(onlyIndex){
            //获取数据的索引字段
            showFields = getBaseModelPoolIndexDataFields(pool);
        }else {
            //获取数据池的所有字段
            showFields = archService.listDataField(dataType.getId(), NptDict.FAL_SOPEN, NptDict.IDS_ENABLED);
        }
        if (null == showFields || showFields.isEmpty()) {
            return NptDict.RST_EXCEPTION("数据池对应的数据表不包含任何可显示的字段:数据池ID[" + poolId + "]");
        }

        if (bean.getOrderType() != null && bean.getOrderType() == NptDict.ORDER_FILTER.getCode()) {
            makeDefaultLimitCondition(pool, bean);
        }

        makeDefaultOrderCondition(pool, bean);

        bean.setWebNote(pool.getPoolNote());
        //加载分页数据
        return getPaginationData(dataType,showFields,bean,false,exact);
    }

    /**
     *作者：owen
     *时间: 2017/5/2 14:12
     *描述:
     *      添加实体数据的默认排序字段过滤
     */
    private void makeDefaultOrderCondition(NptBaseModelPool pool,NptWebBridgeBean bean){
        if(null != pool && null != bean) {
            if (null == bean.getOrderCondition()) {
                bean.setOrderCondition(new HashMap<>());
            }
                Long orderFieldId = pool.getOrderFieldId();
                if (null != orderFieldId) {
                    NptLogicDataField orderField = archService.fastFindDataFieldById(Math.abs(orderFieldId));
                    if (null != orderField && orderField.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {
                        String orderName = "ASC";
                        if(orderFieldId < 0L){
                            orderName = "DESC";
                        }

                        bean.getOrderCondition().put(orderField.getFieldDbName(),orderName);
                        return;
                    }
                }
                bean.getOrderCondition().put("ROWNUM", "ASC");
        }
    }


    private void makeDefaultLimitCondition(NptBaseModelPool pool, NptWebBridgeBean bean) {
        if (null != pool && null != bean) {
            if (null == bean.getLimitCondition()) {
                bean.setLimitCondition(new ArrayList<>());
            }
            if (bean.getLimitCondition().isEmpty()) {
                Long orderFieldId = pool.getOrderFieldId();
                if (null != orderFieldId) {
                    NptLogicDataField orderField = archService.fastFindDataFieldById(Math.abs(orderFieldId));
                    if (null != orderField && orderField.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {
                        String orderName = "<";
                        if (orderFieldId < 0L) {
                            orderName = ">";
                        }
                        //TODO 自定义显示方式
                        bean.getLimitCondition().add(new NptWebLimitBean("like", orderField.getFieldDbName(), "'%-%-%'"));
                        bean.getLimitCondition().add(new NptWebLimitBean(orderName, "to_date("+orderField.getFieldDbName()+",'yyyy-MM-dd')", "sysdate"));
                    }
                }
            }
        }

    }

    /**
     * 作者: 张磊
     * 日期: 17/2/16 上午11:46
     * 备注: 获取模型的主数据池分页列表数据
     */
    @Override
    public NptDict getBaseModelIndexFieldPaginationData(NptBaseModel model, NptWebBridgeBean bean,Boolean exact) {
        //获取模型的主组
        NptBaseModelPool mainPool = poolManager.getBaseModelGroupMainPool(model);
        if (null == mainPool) {
            return NptDict.RST_EXCEPTION("模型ID：" + model.getId() + "不存在主数据池");
        } else {
            if(!loadBaseModelGroupoolTitle(mainPool)){
                return NptDict.RST_EXCEPTION("模型ID：" + model.getId() + "主数据池存在异常");
            }
        }
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
            List<NptBaseModelPoolCdt> conditions = new ArrayList<>(this.getBaseModelPoolConditions(mainPool.getId()));
            bean.setWebQueryCondition(conditions);
        }
        return getBaseModelGroupoolPaginationData(mainPool.getId(), bean,true,exact);
    }

    /**
     * 作者: owen
     * 时间: 2017/3/12 下午1:59
     * 描述:
     * 加载模型的结构信息
     *
     * @param model
     */
    @Override
    public NptBaseModelTree loadBaseModelTree(NptBaseModel model) {

        NptBaseModelTree modelTree = new NptBaseModelTree();

        if(null != model){
            modelTree.setModel(model);
            Collection<NptBaseModelGroupTree> groupTrees = new ArrayList();

            Collection<NptBaseModelGroup> groups = getBaseModelGroups(model);
            if(null != groups && !groups.isEmpty()){
                for(NptBaseModelGroup group:groups){
                    NptBaseModelGroupTree groupTree = new NptBaseModelGroupTree();

                    groupTree.setGroup(group);

                    Collection<NptBaseModelPool> pools = getBaseModelGrouPools(group,null);
                    groupTree.setPools(pools);

                    groupTrees.add(groupTree);
                }
            }

            modelTree.setGroupTrees(groupTrees);
        }

        return modelTree;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/13 上午11:38
     * 备注: 根据code获取group
     */
    @Override
    public NptBaseModelGroup getModelGroupByCode(NptBaseModel model, NptDict gCode) {
        if(null != model) {
            Collection<NptBaseModelGroup> groups = modelManager.lookupModelGroups(model);
            if (null != groups && !groups.isEmpty()) {
                for (NptBaseModelGroup g : groups) {
                    if (null != g.getSpecialCode() && g.getSpecialCode().equals(gCode.getCode())) {
                        return g;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 作者：owen
     * 时间：2017/3/17 下午3:41
     * 描述：
     * 查询指定数据池指定数据主键的唯一一条记录
     *
     * @param poolId
     * @param uFieldValue
     */
    @Override
    public NptWebFieldDataArray loadBaseModelPoolRowData(Long poolId, String uFieldValue) {

        NptBaseModelPool pool = fastFindBaseModelGrouPoolById(poolId);
        if(null != pool && !StringUtils.isBlank(uFieldValue)){
            NptLogicDataType dataType = archService.fastFindDataTableById(pool.getDataTypeId());
            if(null != dataType && dataType.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
                String tbName = dataType.getTypeDbName();
                Collection<NptLogicDataField> typeFields = archService.listDataField(dataType.getId(),NptDict.FAL_SOPEN,NptDict.IDS_ENABLED);
                if(null != typeFields && !typeFields.isEmpty()){

                    NptLogicDataField ukField = archService.fastFindDataFieldById(dataType.getUkFieldId());
                    if(null != ukField){
                        Map<String,String> ukWhere = new HashMap<>();
                        ukWhere.put(ukField.getFieldDbName(),uFieldValue);

                        //构建查询语句
                        String[] sql = dataBaseService.makeLastedDataSql(
                                tbName,
                                typeFields,
                                ukWhere,
                                null,
                                NptCommonUtil.IntegerOne());

                        //确认是否存在实体数据
                        int totalCount = dataBaseService.getCount(sql[0]);
                        if(totalCount > 0){
                            List<NptWebFieldDataArray> result = (List<NptWebFieldDataArray>) queryAndFormat(sql[1], typeFields,pool,false);
                            return result.get(0);
                        }
                    }
                }
            }
        }
        return new NptWebFieldDataArray();
    }


    @Override
    public NptDict loadBaseModelAuthGroupsByUK(NptWebBridgeBean bean, NptBaseModel m,Boolean applyed) {
        //先通过唯一数据主键查询出模型的业务主键值，以便进行数据关联
        Map<String,Object> tyValues = getModelMainPoolTypicalValueByUKValue(m,bean.getPrimaryKeyValue());

        if(null != tyValues && !tyValues.values().isEmpty()) {
            List<Object> valueList = new ArrayList<>(tyValues.values());
            bean.setPrimaryKeyValue(String.valueOf(valueList.get(0)));
            if(NptCommonUtil.INTEGER_1.equals(valueList.size())){
                bean.setTitle(String.valueOf(valueList.get(0)));
            }else {
                bean.setTitle(String.valueOf(valueList.get(1)));
            }
            return loadBaseModelAuthGroupsByPK(bean, m, NptDict.MPL_LEVEL_0,applyed);
        }else {
            return NptDict.RST_EXCEPTION("通过数据主键无法定位业务主键及标题字段的信息");
        }
    }

    /**
     *作者：owen
     *时间：2016/12/16 15:29
     *描述:
     *      通过数据主键获取模型主数据池中的业务主键值
     */
    @Override
    public Map<String,Object> getModelMainPoolTypicalValueByUKValue(NptBaseModel model, String ukValue){
        if(null == model || null == ukValue || ukValue.isEmpty()){
            return null;
        }

        NptBaseModelPool mainPool = getBaseModelGroupMainPool(model);
        if(null == mainPool){
            return null;
        }
        NptLogicDataType mainType = archService.fastFindDataTableById(mainPool.getDataTypeId());
        if(null == mainType || NptCommonUtil.getDefaultParentId().equals(mainType.getUkFieldId())){
            return null;
        }
        NptLogicDataField uField = archService.fastFindDataFieldById(mainType.getUkFieldId());
        if(null == uField){
            return null;
        }
        NptLogicDataField pField = archService.findDataFieldById(mainPool.getPrimaryFieldId());
        if(null == pField){
            return null;
        }
        NptLogicDataField titleField = archService.findDataFieldById(mainPool.getTitleFieldId());
        if(null == titleField){
            return null;
        }
        //获取物理表信息及依据数据主键查询出业务主键的值，以便进行数据池之间的业务主键关系查询
        NptLogicDataType pType = archService.fastFindDataTableById(mainPool.getDataTypeId());

        Collection<NptLogicDataField> searchField = new ArrayList<>();
        searchField.add(pField);
        searchField.add(titleField);

        Map<String,String> uFieldWhere = new HashMap<>();
        uFieldWhere.put(uField.getFieldDbName(),ukValue);

        //加载业务主键实体值
        if(null != pType){
            String sql = dataBaseService.makeUniqueSql(pType.getTypeDbName(),searchField,uFieldWhere, NptDict.CST_ENG_AS_CHN);
            return  (Map<String, Object>) dataBaseService.queryUnique(sql);
        }
        return null;
    }

    /**
     *作者：OWEN
     *时间：2016/11/21 15:32
     *描述:
     *      通过业务主键加载指定模型的分组详情
     */
    public NptDict loadBaseModelAuthGroupsByPK(NptWebBridgeBean bean, NptBaseModel m,NptDict lockLevel,Boolean applyed) {
        if(null == bean || null == m){
            return NptDict.RST_EXCEPTION("参数未初始或模型不存在!");
        }
        String pkValue = bean.getPrimaryKeyValue();
        if(null == pkValue || pkValue.isEmpty()){
            return NptDict.RST_EXCEPTION("业务主键值不能为空！");
        }

        Collection<NptWebDetailGroup> webGroupList = new ArrayList<>();
        //加载模型的所有分组及每个分组的数据池
        Collection<NptBaseModelGroup> groupList = groupManager.getBaseModelGroups(m);
        Long providerId = bean.getListCondition(NptWebBridgeBean.PROVIDER_QUERY_CONDITION_NAME);
        if(null == groupList || groupList.isEmpty()){
            //当前模型未分组，则直接去加载数据池的相关信息,即将模型下调为组级别
            Collection<NptBaseModelPool> aPoolList = getBaseModelGrouPools(m,lockLevel);
            if(null != aPoolList && !aPoolList.isEmpty()){

                NptWebDetailGroup wGroup = loadOrderedGroupoolsData(m.getModelNote(),aPoolList,pkValue,providerId,applyed);
                webGroupList.add(wGroup);
            }

        }else {
            //当前模型已分组，则依次加载其每个组的相关信息
            for(NptBaseModelGroup group:groupList) {
                Long listGroupId = bean.getListCondition(NptWebBridgeBean.GROUP_QUERY_CONDITION_NAME);
                if(null == listGroupId || (null != listGroupId && listGroupId.equals(group.getId()))) {
                    Collection<NptBaseModelPool> aPoolList = getBaseModelGrouPools(group,lockLevel);
                    if (null != aPoolList && !aPoolList.isEmpty()) {
                        NptWebDetailGroup wGroup = loadOrderedGroupoolsData(group.getGroupTitle(), aPoolList, pkValue,providerId,applyed);
                        webGroupList.add(wGroup);
                    }
                }
            }
        }
        bean.setDataList(webGroupList);
        bean.setPrimaryKeyValue(pkValue);

        return NptDict.RST_SUCCESS;
    }

    /**
     * author: owen
     * date:   2017/3/21 下午8:58
     * note:
     * 检测指定模型与业务主键的数据是否存在
     *
     * @param model
     * @param pkValue
     */
    @Override
    public NptDict isBaseModelAuthPKValueExisted(NptBaseModel model, String pkValue) {

        if(null == pkValue || StringUtils.isBlank(pkValue)){
            return NptDict.RST_EXCEPTION("指定的查询对象参数为空");
        }

        NptBaseModelPool mainPool = getBaseModelGroupMainPool(model);
        if(null != mainPool){
            NptLogicDataType dataType = archService.fastFindDataTableById(mainPool.getDataTypeId());
            NptLogicDataField pkField = archService.fastFindDataFieldById(mainPool.getPrimaryFieldId());
            if(null != dataType && null != pkField){
                Collection<NptLogicDataField> searchFields = new ArrayList<NptLogicDataField>(){{add(pkField);}};
                Map<String,String> condition = new HashMap<String,String>(){{put(pkField.getFieldDbName(),pkValue);}};

                String sql = dataBaseService.makeCountSql(dataType.getTypeDbName(),searchFields,condition);

                Integer count = dataBaseService.getCount(sql);
                if(NptCommonUtil.INTEGER_1.equals(count)) {
                    return NptDict.RST_SUCCESS;
                }else {
                    return NptDict.RST_UNKNOW;
                }
            }
        }
        return NptDict.RST_EXCEPTION("当前业务暂未开放查询或已暂停查询服务");
    }

    /**
     * author: owen
     * date:   2017/3/21 下午9:34
     * note:
     * 检测指定的模型是否已开放查询
     *
     * @param model
     */
    @Override
    public NptDict isBaseModelQueryInService(NptBaseModel model) {
        NptDict result = NptDict.RST_EXCEPTION("当前业务暂未开放查询或已暂停查询服务");

        if(null == model || NptDict.IDS_ENABLED.getCode() == model.getStatus()){

            NptBaseModelPool mainPool = getBaseModelGroupMainPool(model);
            if(null != mainPool){

                NptLogicDataType dataType = archService.fastFindDataTableById(mainPool.getDataTypeId());
                if(null != dataType){

                    NptLogicDataField pkField = archService.findDataFieldById(mainPool.getPrimaryFieldId());
                    if(null != pkField){
                        return NptDict.RST_SUCCESS;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 作者：owen
     * 日期：2016/10/28 15:03
     * 备注：
     *      加载POOL集合中每个POOL的数据，其中第一个POOL会加载实体数据，其它POLL只先加载基本信息
     * 参数：
     * 返回：
     */
    private NptWebDetailGroup loadOrderedGroupoolsData(
            String title,
            Collection<NptBaseModelPool> poolList,
            Object pkValue,
            Long providerId,
            Boolean applyed){
        //构建一个信息块组
        NptWebDetailGroup webGroup = new NptWebDetailGroup();
        webGroup.setGroupTitle(title);

        Collection<NptWebDetailBlock> blockList = new ArrayList<>();
        if(null != poolList && !poolList.isEmpty()){
            Iterator<NptBaseModelPool> ite = poolList.iterator();
            while (ite.hasNext()){
                NptBaseModelPool pool = ite.next();
                NptLogicDataProvider poolProvider = this.getBaseModelGrouPoolProvider(pool);
                if(null == providerId || (null != providerId && providerId.equals(poolProvider.getId()))) {
                    NptWebDetailBlock block = getBaseModelGrouPoolData(pool, String.valueOf(pkValue), true,applyed);
                    if(null != block && block.getDataCount() > NptCommonUtil.IntegerZero()) {
                        blockList.add(block);
                    }
                }
            }
        }
        webGroup.setBlockList(blockList);

        return webGroup;
    }

    /**
     * 作者：owen
     * 日期：2016/10/24 13:58
     * 备注：
     * 获取指定数据池的详细数据
     * 参数：若pkValue为null，则只加载数据池的基本信息，不加载其真实数据
     * 返回：
     *
     * @param
     */
    @Override
    public NptWebDetailBlock getBaseModelGrouPoolData(NptBaseModelPool pool, String pkValue, Boolean first,Boolean applyed) {
        //构建结果块
        NptWebDetailBlock<NptBaseModelPool,NptLogicDataField,NptLogicDataProvider> block = new NptWebDetailBlock();
        if(null == pool){
            return null;
        }
        //获取数据池的物理表信息
        NptLogicDataType poolType = archService.fastFindDataTableById(pool.getDataTypeId());

        NptLogicDataProvider providerOrg = archService.fastFindProviderById(poolType.getParentId());
        //获取数据池的物理字段集合
        Collection<NptLogicDataField> fieldList = archService.listDataField(pool.getDataTypeId(), NptDict.FAL_SOPEN, NptDict.IDS_ENABLED);
        if(null == poolType || null == fieldList || fieldList.isEmpty()){
            return null;
        }

        NptLogicDataField titleField = archService.fastFindDataFieldById(pool.getTitleFieldId());
        if(null != titleField && !fieldList.contains(titleField)){
            fieldList.add(titleField);
        }

        //数据池自身信息
        pool.setPoolTitle(poolType.getAlias());
        block.setBlockInfo(pool);
        //数据池提供方信息
        block.setParentInfo(providerOrg);
        if(null != pkValue && !pkValue.isEmpty()){
            //构建业务主键查询条件
            NptLogicDataField pkField = archService.fastFindDataFieldById(pool.getPrimaryFieldId());
            if(null == pkField){
                return null;
            }

            Map<String,String> pkWhere = new HashMap<>();
            pkWhere.put(pkField.getFieldDbName(),pkValue);
            Integer queryCount = 1;
            if(!first){
                queryCount = NptBaseModelPoolManager.DEFAULT_POOL_LOADED_COUNT;
            }
            //构建查询语句
            String[] sql = dataBaseService.makeLastedDataSql(
                    poolType.getTypeDbName(),
                    fieldList,
                    pkWhere,
                    null,
                    queryCount
            );
            //确认是否存在实体数据
            int totalCount = dataBaseService.getCount(sql[0]);
            if(totalCount > 0){
                block.setDataArray(queryAndFormat(sql[1], fieldList,pool,applyed));
            }
            //当前数据池指定业务对象的所有字段值
            block.setDataCount(totalCount);
        }

        return block;
    }

    @Override
    public NptWebDetailBlock getBaseModelGrouPoolData(Long poolId, String pkValue, Boolean first,Boolean applyed) {
        return getBaseModelGrouPoolData(poolManager.fastFindById(poolId), pkValue, first,applyed);
    }

    /**
     * 作者: owen
     * 时间: 2017/3/13 下午9:55
     * 描述:
     * 获取数据池的查询条件列表
     *
     * @param p
     */
    @Override
    public Collection<NptBaseModelPoolCdt> getBaseModelPoolConditions(NptBaseModelPool p) {
        Collection<NptBaseModelPoolCdt> result = new ArrayList<>();
        Collection<NptBaseModelPoolCdt> conditions = poolConditionManager.getPoolSearchConditions(p);
        if(null != conditions && !conditions.isEmpty()){
            for (NptBaseModelPoolCdt c : conditions) {
                NptLogicDataField field = archService.findDataFieldById(c.getFieldId());
                if(null != field && NptDict.IDS_ENABLED.getCode() == field.getStatus()){
                    c.setFieldDBName(field.getFieldDbName());
                    c.setFieldTitle(field.getAlias());
                    result.add(c);
                }
            }
        }
        return result;
    }

    @Override
    public Collection<NptBaseModelPoolCdt> getBaseModelPoolConditions(Long poolId) {
        NptBaseModelPool p = poolManager.findById(poolId);
        if(null != p){
            return getBaseModelPoolConditions(p);
        }
        return new ArrayList<>();
    }

    /**
     * author: owen
     * date:   2017/3/23 下午2:21
     * note:
     * 从数据池中依据数据池的条件字段进行[或]关系的模糊查询
     *
     * @param pool
     * @param keyword
     */
    @Override
    public NptDict fuzzySearchFromMainPool(NptBaseModelPool pool, String keyword, NptDict pubLevel, NptWebBridgeBean bean) {

        if(null != pool && !StringUtils.isBlank(keyword)){

            Collection<NptBaseModelPoolCdt> cdts = getBaseModelPoolConditions(pool);
            if(null != cdts && !cdts.isEmpty()){
                NptLogicDataType dataType = archService.fastFindDataTableById(pool.getDataTypeId());
                if(null != dataType){
                    Map<String,String> orWhere = new HashMap<>();
                    for(NptBaseModelPoolCdt c:cdts){
                        orWhere.put(c.getFieldDBName(),keyword);
                    }
                    NptLogicDataField ukField = archService.findDataFieldById(dataType.getUkFieldId());
                    if(null != ukField) {
                        Collection<NptLogicDataField> searchFields = new ArrayList<>();
                        searchFields.add(ukField);
                        Collection<NptLogicDataField> pubFields = getBaseModelPoolIndexDataFields(pool);
                        if (null != pubFields && !pubFields.isEmpty()) {

                            searchFields.addAll(pubFields);
                            String[] sql = dataBaseService.makeMultiLikeSql(
                                    dataType.getTypeDbName(),
                                    searchFields,
                                    orWhere,
                                    bean.getCurrPage(),
                                    bean.getPageSize(),
                                    NptDict.CST_ENG_AS_CHN);

                            if (!StringUtils.isBlank(sql[1])) {
                                int totalCount = dataBaseService.getCount(sql[0]);
                                bean.setTotalCount(totalCount);
                                Collection<NptWebFieldDataArray> formatData = queryAndFormat(sql[1], pubFields, null, true);
                                if (null != formatData && !formatData.isEmpty()) {
                                    bean.setDataList(formatData);
                                }
                                return NptDict.RST_SUCCESS;
                            } else {
                                return NptDict.RST_ERROR;
                            }
                        } else {
                            return NptDict.RST_EXCEPTION("当前信息类别未公开任何可查询的信息项");
                        }
                    }else {
                        return NptDict.RST_ERROR;
                    }
                }else {
                    return NptDict.RST_ERROR;
                }
            }else {
                return NptDict.RST_EXCEPTION("当前信息类型不支持信息项的模糊查询");
            }
        }else {
            return NptDict.RST_INVALID_PARAMS;
        }
    }

    /**
     * author: owen
     * date:   2017/3/23 15:53
     * note:
     * 从普通数据池中依据数据池的条件字段进行【或】关系的模糊查询
     *
     * @param pool
     * @param keyword
     * @param pubLevel
     */
    @Override
    public Collection<NptWebFieldDataArray> fuzzySearchFromCommonPool(NptBaseModelPool pool, String keyword, NptDict pubLevel) {

        Collection<NptWebFieldDataArray> result = new ArrayList<>();

        if(null != pool && !StringUtils.isBlank(keyword)){
            Collection<NptBaseModelPoolCdt> cdts = getBaseModelPoolConditions(pool);
            if(null != cdts && !cdts.isEmpty()) {
                NptLogicDataType dataType = archService.fastFindDataTableById(pool.getDataTypeId());
                if (null != dataType) {
                    Map<String, String> orWhere = new HashMap<>();
                    for (NptBaseModelPoolCdt c : cdts) {
                        orWhere.put(c.getFieldDBName(), keyword);
                    }
                    NptLogicDataField ukField = archService.findDataFieldById(dataType.getUkFieldId());
                    if (null != ukField) {
                        Collection<NptLogicDataField> searchFields = new ArrayList<>();
                        searchFields.add(ukField);
                        Collection<NptLogicDataField> pubFields = getBaseModelPoolIndexDataFields(pool);
                        if (null != pubFields && !pubFields.isEmpty()) {

                            searchFields.addAll(pubFields);
                            String[] sql = dataBaseService.makeMultiLikeSql(
                                    dataType.getTypeDbName(),
                                    searchFields,
                                    orWhere,
                                    null,
                                    null,
                                    NptDict.CST_ENG_AS_CHN);

                            if (!StringUtils.isBlank(sql[1])) {
                                Collection<NptWebFieldDataArray> formatData = queryAndFormat(sql[1], pubFields, null, true);
                                if (null != formatData && !formatData.isEmpty()) {
                                    result.addAll(formatData);
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    @Override
    public Collection<NptBaseModel> listModels(NptDict host, NptDict cate) {
        Collection<NptBaseModel> result = new ArrayList<>();

        Collection<NptBaseModel> searchResult = modelManager.findModelByCategoryAndHost(cate,host);
        if(null != searchResult && !searchResult.isEmpty()){
            result.addAll(searchResult);
        }

        return result;
    }


    /**
     * 作者：owen
     * 时间: 2017/5/4 16:58
     * 描述:
     * 加载模型分组的统计信息
     *
     * @param gid
     */
    @Override
    public NptBaseModelGroupStatistic loadBaseModelGroupStatistic(Long gid) {

        NptBaseModelGroup group = fastFindBaseModelGroupById(gid);

        return loadBaseModelGroupStatistic(group);
    }

    @Override
    public NptBaseModelGroupStatistic loadBaseModelGroupStatistic(NptBaseModelGroup group) {

        if(null != group){
            NptBaseModelGroupStatistic gs = new NptBaseModelGroupStatistic(group.getGroupTitle());

            Collection<NptBaseModelPool> pools = getBaseModelGrouPools(group,NptDict.IDS_ENABLED);
            if(null != pools && !pools.isEmpty()){
                for(NptBaseModelPool p:pools){

                    NptBaseModelPoolStatistic ps = loadBaseModelPoolStatistic(p);
                    if(null != ps){
                        gs.getPoolStatistics().add(ps);
                    }
                }
            }

            gs.countSum();

            return gs;
        }

        return null;
    }

    /**
     * 作者：owen
     * 时间: 2017/5/4 16:58
     * 描述:
     * 加载模型数据池的统计信息
     *
     * @param pid
     */
    @Override
    public NptBaseModelPoolStatistic loadBaseModelPoolStatistic(Long pid) {

        NptBaseModelPool pool = fastFindBaseModelGrouPoolById(pid);

        return loadBaseModelPoolStatistic(pool);
    }

    @Override
    public NptBaseModelPoolStatistic loadBaseModelPoolStatistic(NptBaseModelPool pool) {

        if(null != pool){

            NptBaseModelPoolStatistic ps = new NptBaseModelPoolStatistic(pool.getPoolTitle());

            NptLogicDataType type = archService.fastFindDataTypeById(pool.getDataTypeId());

            if(null != type && type.checkIsEnable()) {
                ps.setDataCount(dataBaseService.getRowCount(type));

                if (ps.getDataCount() > 0L) {

                    NptLogicDataField titleField = archService.fastFindDataFieldById(pool.getTitleFieldId());
                    if (null != titleField && titleField.checkIsEnable()) {
                        Collection<NptLogicDataField> fields = new ArrayList<>();
                        fields.add(titleField);

                        String[] sql = dataBaseService.makeLastedDataSql(type.getTypeDbName(),fields,null,null,5);

                        List<Object> list = dataBaseService.queryList(sql[1],fields);

                        if(null != list && !list.isEmpty()){
                            for(Object obj:list){
                                Map<String,Object> rd = (Map<String, Object>) obj;
                                ps.getHotDataTitles().add(String.valueOf(rd.values().iterator().next()));
                            }
                        }
                    }
                }
            }
            return ps;
        }

        return null;
    }

    /**
     * 作者：owen
     * 时间: 2017/5/4 16:58
     * 描述:
     * 加载模型的统计信息
     *
     * @param modelId
     */
    @Override
    public NptBaseModelStatistic loadBaseModelStatistic(Long modelId) {

        NptBaseModel model = fastFindBaseModelById(modelId);

        return loadBaseModelStatistic(model);
    }

    @Override
    public NptBaseModelStatistic loadBaseModelStatistic(NptBaseModel model) {

        if(null != model) {
            NptBaseModelStatistic ms = new NptBaseModelStatistic(model.getModelName());

            Collection<NptBaseModelGroup> groups = getBaseModelGroups(model);
            if(null != groups && !groups.isEmpty()){

                for(NptBaseModelGroup g:groups){

                    NptBaseModelGroupStatistic gs = loadBaseModelGroupStatistic(g);
                    if(null != gs){
                        ms.getGroupStatistics().add(gs);
                    }
                }
            }

            ms.countSum();


            return ms;
        }

        return null;
    }

    /**
     * author: owen
     * date:   2017/4/17 下午2:55
     * note:
     * 获取数据池记录的总条数
     *
     * @param p
     */
    @Override
    public Long getBaseModelPoolDataCount(NptBaseModelPool p) {
        Long result = 0L;

        if(null != p && p.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
            NptLogicDataType dataType = archService.findDataTypeById(p.getDataTypeId());
            if(null != dataType && !StringUtils.isBlank(dataType.getTypeDbName())){
                result = dataBaseService.qureyTableRows(dataType);
            }
        }

        return result;
    }

    /**
     *作者：owen
     *时间：2016/12/16 15:29
     *描述:
     *      通过数据主键获取模型主数据池中的业务主键值
     */
    @Override
    public Map<String,Object> getModelMainPoolTypicalValueByPKValue(NptBaseModel model, String pkValue){
        if(null == model || null == pkValue || pkValue.isEmpty()){
            return null;
        }

        NptBaseModelPool mainPool = getBaseModelGroupMainPool(model);
        if(null == mainPool){
            return null;
        }
        NptLogicDataType mainType = archService.fastFindDataTypeById(mainPool.getDataTypeId());
        if(null == mainType || NptCommonUtil.getDefaultParentId().equals(mainType.getUkFieldId())){
            return null;
        }
        NptLogicDataField uField = archService.fastFindDataFieldById(mainType.getUkFieldId());
        if(null == uField){
            return null;
        }
        NptLogicDataField pField = archService.findDataFieldById(mainPool.getPrimaryFieldId());
        if(null == pField){
            return null;
        }
        NptLogicDataField titleField = archService.findDataFieldById(mainPool.getTitleFieldId());
        if(null == titleField){
            return null;
        }
        //获取物理表信息及依据数据主键查询出业务主键的值，以便进行数据池之间的业务主键关系查询
        NptLogicDataType pType = archService.fastFindDataTypeById(mainPool.getDataTypeId());

        Collection<NptLogicDataField> searchField = new ArrayList<>();
        searchField.add(uField);
        searchField.add(titleField);

        Map<String,String> uFieldWhere = new HashMap<>();
        uFieldWhere.put(pField.getFieldDbName(),pkValue);

        //加载业务主键实体值
        if(null != pType){
            String sql = databaseManager.makeUniqueSql(pType.getTypeDbName(),searchField,uFieldWhere, NptDict.CST_ENG_AS_CHN);
            return  (Map<String, Object>) databaseManager.queryUnique(sql);
        }
        return null;
    }

    @Override
    public NptDict loadBaseModelOpenList(NptWebBridgeBean bean, NptBaseModel model, Boolean applyed) {
        //获取模型的主组
        NptBaseModelPool mainPool = getBaseModelGroupMainPool(model);
        if (null == mainPool) {
            return NptDict.RST_EXCEPTION("模型ID：" + model.getId() + "不存在主数据池");
        }
        return getBaseModelGroupoolPaginationData(mainPool.getId(), bean, true, false);
    }

    @Override
    public void loadBaseModelConditions(NptBaseModel model, NptWebBridgeBean bean) {
        NptBaseModelPool mainPool = getBaseModelGroupMainPool(model);
        if(null != mainPool){
            loadBaseModelPoolConditions(mainPool,bean);
        }
    }

    /**
     * 作者: owen
     * 时间: 2017/3/15 下午8:16
     * 描述:
     * 加载指定数据池的查询条件
     *
     * @param pool
     */
    @Override
    public void loadBaseModelPoolConditions(NptBaseModelPool pool,NptWebBridgeBean bean) {

        if (null != pool && null != bean) {
            /**
             * 数据池的业务主键默认支持文本式的模糊查询
             */
            NptLogicDataField pkField = getBaseModelGrouPoolPrimaryField(pool);
            if (null != pkField) {
                NptWebTextBox textBox = new NptWebTextBox(pkField.getFieldDbName(), pkField.getAlias());
                bean.getTextBoxes().add(textBox);
                bean.setPrimaryKeyId(pkField.getId());
            }

            /**
             * 加载在模型管理中配置的查询条件
             */
            Collection<NptBaseModelPoolCdt> poolCdts = getBaseModelPoolConditions(pool);
            if(null != poolCdts && !poolCdts.isEmpty()){
                for(NptBaseModelPoolCdt cdt:poolCdts){
                    /**
                     * 查询字段检测其属性
                     */
                    NptLogicDataField dataField = getBaseModelGrouPoolFieldById(cdt.getFieldId());
                    if (null != dataField && !pkField.getId().equals(dataField.getId())) {

                        if (NptDict.FSS_COMMON_TEXT.name().equals(dataField.getShowStyle())
                                && bean.getTextBoxes().size() < NptCommonUtil.ConditionMax()) {
                            /**
                             * 文本条件
                             */
                            NptWebTextBox textBox = new NptWebTextBox(dataField.getFieldDbName(), dataField.getAlias());
                            bean.getTextBoxes().add(textBox);
                        } else if (NptDict.FSS_CODE.name().equals(dataField.getShowStyle())
                                && bean.getDropBoxList().size() < NptCommonUtil.ConditionMax()) {
                            /**
                             * 下拉列表条件
                             */
                            Collection<NptBusinessCode> codeTables = commonService.getBusinessCodeByType(dataField.getId());
                            NptWebDropBox dropBox = new NptWebDropBox(dataField.getFieldDbName(), dataField.getAlias());
                            List<NptWebDropBoxElement> elements = new ArrayList<>();
                            for (NptBusinessCode code : codeTables) {
                                NptWebDropBoxElement element = new NptWebDropBoxElement(code.getId(), code.getCodeName());
                                elements.add(element);
                            }
                            dropBox.setOrderedElements(elements);
                            bean.getDropBoxList().add(dropBox);
                        }
                    }
                }
            }
        }
    }

    /**
     * 作者：owen
     * 日期：2016/10/20 14:26
     * 备注：
     * 获取指定数据池的业务主键字段
     * 参数：
     * 返回：
     *
     * @param p
     */
    @Override
    public NptLogicDataField getBaseModelGrouPoolPrimaryField(NptBaseModelPool p) {
        if(null != p){
            Long fieldId = p.getPrimaryFieldId();
            return archService.fastFindDataFieldById(fieldId);
        }
        return null;
    }

    /**
     * 作者：owen
     * 日期：2016/10/21 12:04
     * 备注：
     * 获取指定字段的详情
     * 参数：
     * 返回：
     *
     * @param id
     */
    @Override
    public NptLogicDataField getBaseModelGrouPoolFieldById(Long id) {
        return poolIndexManager.getBaseModelGrouPoolFieldById(id);
    }
}
