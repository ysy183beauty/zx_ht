package com.npt.grs.model.service.impl;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dataBinder.*;
import com.npt.bridge.database.manager.NptDatabaseManager;
import com.npt.bridge.dict.NptBusinessCode;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.*;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.grs.apply.entity.NptBusinessFlowLog;
import com.npt.grs.apply.entity.NptResourceApply;
import com.npt.grs.apply.entity.NptResourceApplyField;
import com.npt.grs.apply.manager.NptResourceApplyManager;
import com.npt.grs.model.manager.*;
import com.npt.grs.model.manager.control.NptEntityDataShowStyleController;
import com.npt.grs.model.manager.impl.NptBaseModelPool2PoolManagerImpl;
import com.npt.grs.model.service.NptGRSBaseModelService;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.rms.auth.entity.NptDataPermission;
import com.npt.rms.auth.entity.NptSimpleUser;
import com.npt.rms.auth.service.NptRmsAuthService;
import com.npt.rms.base.NptRmsCommonService;
import com.npt.rms.util.NptRmsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.summer.core.context.module.dependent.PublicBean;
import org.summer.extend.orm.Condition;
import org.summer.extend.orm.condition.Conditions;
import org.summer.extend.security.PlatformSecurityContext;
import org.summer.security.entity.User;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/10 20:28
 * 备注：
 */
@Service("nptGrsModelService")
@Transactional
public class NptGRSBaseModelServiceImpl implements NptGRSBaseModelService,PublicBean{


    @Autowired
    private NptBaseModelManager baseModelManager;

    @Autowired
    private NptBaseModelGrouPoolManager baseModelGrouPoolManager;

    @Autowired
    private NptBaseModelPoolIndexManager baseModelPoolIndexManager;

    @Autowired
    private NptBaseModelGroupManager baseModelGroupManager;

    @Autowired
    private NptBaseModelPool2PoolManagerImpl baseModelPool2PoolManager;

    @Autowired
    private NptBaseModelDataTimestampManager baseModelDataTimestampManager;

    @Autowired
    private NptResourceApplyManager applyManager;

    @Resource(name = "rmsArchService")
    private NptRmsArchService rmsArchService;
    @Resource(name = "rmsAuthService")
    private NptRmsAuthService rmsAuthService;
    @Resource(name = "nptDssOracleManager")
    protected NptDatabaseManager databaseManager;
    @Resource(name = "rmsCommonService")
    protected NptRmsCommonService commonService;

    @Autowired
    protected NptGRSBaseModelService baseModelService;

    @Autowired
    private NptEntityDataShowStyleController showStyleController;

    @Autowired
    private NptBaseModelPoolConditionManager baseModelPoolConditionManager;


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
        return baseModelManager.findById(id);
    }

    @Override
    public NptBaseModel fastFindBaseModelById(Long id) {
        return baseModelManager.fastFindById(id);
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
        return baseModelGroupManager.findById(id);
    }

    @Override
    public NptBaseModelGroup fastFindBaseModelGroupById(Long id) {
        return baseModelGroupManager.fastFindById(id);
    }

    @Override
    public NptBaseModelGroup findBaseModelGroupByCode(Long modelId,NptDict code) {
        return baseModelGroupManager.findUniqueByCondition(
                Conditions.eq(NptBaseModelGroup.PROPERTY_GROUP_CODE,code.getCode()),
                Conditions.eq(NptBaseModelGroup.PROPERTY_MODEL_ID,modelId),
                Conditions.eq(NptBaseEntity.PROPERTY_STATUS,NptDict.IDS_ENABLED.getCode()));
    }

    /**
     *  author: zhanglei
     *  date:   2017/07/16 14:42
     * 描述:
     * 加载模型列表
     *
     * @param host
     * @param cate
     */
    @Override
    public Collection<NptBaseModel> listModels(NptDict host, NptDict cate) {
        Collection<Condition> conditions = new ArrayList<>();
        if (host != null) {
            conditions.add(Conditions.eq(NptBaseModel.PROPERTY_HOST_ID, host.getCode()));
        }
        if (cate != null) {
            conditions.add(Conditions.eq(NptBaseModel.PROPERTY_CATE_ID, cate.getCode()));
        }
        conditions.add(Conditions.eq(NptBaseModel.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        return baseModelManager.findByCondition(conditions.toArray(new Condition[]{}));
    }

    /**
     *  author: zhanglei
     *  date:   2017/07/16 14:42
     *  note:
     *
     */
    @Override
    public Collection<NptBaseModel> listModels(List<NptDict> hostList, List<NptDict> cateList) {
        Collection<Condition> conditions = new ArrayList<>();
        if (hostList != null) {
            for (NptDict host : hostList) {
                conditions.add(Conditions.eq(NptBaseModel.PROPERTY_HOST_ID, host.getCode()));
            }
        }
        if (cateList != null) {
            for (NptDict cate : cateList) {
                conditions.add(Conditions.eq(NptBaseModel.PROPERTY_CATE_ID, cate.getCode()));
            }
        }
        conditions.add(Conditions.eq(NptBaseModel.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        return baseModelManager.findByCondition(conditions.toArray(new Condition[]{}));
    }

    @Override
    public Collection<NptBaseModel> listModels(){
        return baseModelManager.findByCondition(Conditions.eq(NptBaseModel.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
    }

    /**
     * 作者：owen
     * 日期：2016/10/20 15:18
     * 备注：
     * 获取指定宿主下唯一的原生基础模型
     * 参数：
     * 返回：
     *
     * @param host
     */
    @Override
    public NptBaseModel getBaseModelHostNativeModel(NptDict host) {
        if(null != host){
            return baseModelManager.findUniqueByCondition(
                    Conditions.eq(NptBaseModel.PROPERTY_HOST_ID,host.getCode()),
                    Conditions.eq(NptBaseModel.PROPERTY_CATE_ID, NptDict.BMC_NATIVE.getCode()),
                    Conditions.not(Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_DELETED.getCode())));
        }
        return null;
      }

    /**
     * 作者：owen
     * 日期：2016/10/20 14:23
     * 备注：
     * 获取基础模型的主数据池
     * 参数：
     * 返回：
     *
     * @param group
     */
    @Override
    public NptBaseModelPool getBaseModelMainGrouPool(NptBaseModelGroup group) {
        NptBaseModelPool pool = baseModelGrouPoolManager.findUniqueByCondition(
                Conditions.eq(NptBaseModelPool.PROPERTY_GROUP_ID,group.getId()),
                Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()),
                Conditions.eq(NptBaseModelPool.PROPERTY_POOL_WEIGHT, NptDict.CLD_MAIN.getCode()));
        loadBaseModelGroupoolTitle(pool,false);
        return pool;
    }

    /**
     * 作者：owen
     * 日期：2016/10/24 21:09
     * 备注：
     * 通过id获取指定的数据池
     * 参数：
     * 返回：
     *
     * @param id
     */
    @Override
    public NptBaseModelPool findBaseModelGrouPoolById(Long id) {
        NptBaseModelPool pool = baseModelGrouPoolManager.findById(id);
        if(loadBaseModelGroupoolTitle(pool,false)) {
            return pool;
        }else {
            return null;
        }
    }

    @Override
    public NptBaseModelPool fastFindBaseModelGrouPoolById(Long id) {
        NptBaseModelPool pool = baseModelGrouPoolManager.fastFindById(id);
        if(loadBaseModelGroupoolTitle(pool,false)) {
            return pool;
        }else {
            return null;
        }
    }

    /**
     * 作者：owen
     * 日期：2016/11/4 12:04
     * 备注：
     * 获取指定数据池关联的其它数据池的详细信息
     * 参数：
     * 返回：
     *
     * @param poolId
     *      数据池ID
     * @param refFieldId
     *      数据池中某个引用其它模型主键的业务外键ID
     * @param ukValue
     *      数据池中由UK的值为ukValue指定的那行记录
     *
     *      通过POOL及其数据主键与数据主键的值唯一定位一行记录，然后拿到业务外键的值,然后由该值作为业务主键去取关联数据池的数据
     */
    @Override
    public Collection<NptWebDetailBlock> getBaseModelGroupoolLinkedPoolData(Long poolId,Long refFieldId,String ukValue) {
        Collection<NptWebDetailBlock> blocks = new ArrayList<>();

        NptBaseModelPool pool = this.findBaseModelGrouPoolById(poolId);
        NptLogicDataField refField = this.getBaseModelGrouPoolFieldById(refFieldId);

        if(null != pool && null != refField && pool.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
            Collection<NptBaseModelLink> p2pList = baseModelPool2PoolManager.findByCondition(
                    Conditions.eq(NptBaseModelLink.PROPERTY_FROM_POOL_ID,poolId),
                    Conditions.eq(NptBaseModelLink.PROPERTY_FROM_POOL_REFKEYID,refFieldId),
                    Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));

            if(null != p2pList && !p2pList.isEmpty()){
                Map<String,Object> keyRow = getPoolRowData(pool,ukValue);
                if(null != keyRow) {
                    String pkValue = String.valueOf(keyRow.get(refField.getFieldDbName()));
                    if(null != pkValue){
                        for(NptBaseModelLink p2p:p2pList) {
                            NptBaseModelPool thatPool = this.fastFindBaseModelGrouPoolById(p2p.getToPoolId());
                            NptWebDetailBlock block = getBaseModelGrouPoolData(thatPool, pkValue, true);
                            if (null != block) {
                                blocks.add(block);
                            }
                        }
                    }
                }
            }
        }

        return blocks;
    }


    /**
     * 作者：owen
     * 日期：2016/11/4 12:20
     * 备注：
     *      获取指定数据池的指定行数据
     * 参数：
     * 返回：
     */
    private Map<String, Object> getPoolRowData(NptBaseModelPool p, String ukValue) {
        Assert.notNull(null != p && null != ukValue);

        NptLogicDataType fromType = baseModelGrouPoolManager.getBaseModelGrouPoolDataType(p);
        Collection<NptLogicDataField> fromFields = baseModelGrouPoolManager.getBaseModelGrouPoolFields(p, NptDict.IDS_ENABLED);
        if (null != fromType && !NptCommonUtil.getDefaultParentId().equals(fromType.getUkFieldId())) {
            Map<String, String> condition = new HashMap<>();
            NptLogicDataField ukField = rmsArchService.fastFindDataFieldById(fromType.getUkFieldId());
            if(null != ukField) {
                condition.put(ukField.getFieldDbName(), ukValue);
                String sql = databaseManager.makeUniqueSql(
                        fromType.getTypeDbName(),
                        fromFields,
                        condition,
                        NptDict.CST_ONLY_ENG
                );

                Map<String, Object> keyRow = (Map<String, Object>) databaseManager.queryUnique(sql);
                return keyRow;
            }
        }
        return null;
    }


    /**
     * 作者：owen
     * 日期：2016/10/20 21:33
     * 备注：
     * 获取分组下的所有数据池
     * 参数：
     * 返回：
     *
     * @param g
     */
    @Override
    public Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModelGroup g,NptDict status,Boolean needLink) {
        Collection<NptBaseModelPool> pools =
                baseModelGrouPoolManager.findByCondition(
                        Conditions.eq(NptBaseModelPool.PROPERTY_GROUP_ID,g.getId()),
                        null == status?
                                Conditions.isNotNull(NptBaseEntity.PROPERTY_ID):
                                Conditions.eq(NptBaseEntity.PROPERTY_STATUS, status.getCode()),
                        Conditions.asc(NptBaseEntity.PROPERTY_DISPLAY_ORDER));

        return loadBaseModelGrouPoolTitle(pools,needLink);
    }

    /**
     *  author: zhanglei
     *  date:   2017/05/24 15:42
     *  note:
     *
     */
    @Override
    public Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModelGroup g, Integer startScore, Integer endScore) {
        Collection<Condition> conditions = new ArrayList<>();
        conditions.add(Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        conditions.add(Conditions.eq(NptBaseModelPool.PROPERTY_GROUP_ID, g.getId()));
        if (startScore != null) {
            conditions.add(Conditions.ge(NptBaseModelPool.PROPERTY_SCORE_WEIGHT, startScore));
        }
        if (endScore != null) {
            conditions.add(Conditions.le(NptBaseModelPool.PROPERTY_SCORE_WEIGHT, endScore));
        }
        Conditions.asc(NptBaseModelPool.PROPERTY_POOL_WEIGHT);

        Collection<NptBaseModelPool> pools = baseModelGrouPoolManager.findByCondition(conditions.toArray(new Condition[conditions.size()]));

        return loadBaseModelGrouPoolTitle(pools, false);
    }

    /**
     * 作者：owen
     * 日期：2016/10/20 21:35
     * 备注：
     * <p>
     * 参数：
     * 返回：
     *
     * @param m
     */
    @Override
    public Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModel m,NptDict status,Boolean needLink) {

        Collection<NptBaseModelPool> pools =
             baseModelGrouPoolManager.findByCondition(
                     Conditions.eq(NptBaseModelGroup.PROPERTY_MODEL_ID,m.getId()),
                     Conditions.asc(NptBaseModelPool.PROPERTY_POOL_WEIGHT),
                     null == status?
                             Conditions.isNotNull(NptBaseEntity.PROPERTY_ID):
                             Conditions.eq(NptBaseEntity.PROPERTY_STATUS, status.getCode()));

        return loadBaseModelGrouPoolTitle(pools,needLink);
    }

    /**
     *  author: zhanglei
     *  date:   2017/05/24 15:43
     *  note:
     *
     */
    @Override
    public Collection<NptBaseModelPool> getBaseModelPools(NptBaseModel m, Integer startScore, Integer endScore) {
        Collection<Condition> conditions = new ArrayList<>();
        conditions.add(Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        conditions.add(Conditions.eq(NptBaseModelGroup.PROPERTY_MODEL_ID, m.getId()));
        if (startScore != null) {
            conditions.add(Conditions.ge(NptBaseModelPool.PROPERTY_SCORE_WEIGHT, startScore));
        }
        if (endScore != null) {
            conditions.add(Conditions.le(NptBaseModelPool.PROPERTY_SCORE_WEIGHT, endScore));
        }
        Conditions.asc(NptBaseModelPool.PROPERTY_POOL_WEIGHT);

        Collection<NptBaseModelPool> pools = baseModelGrouPoolManager.findByCondition(conditions.toArray(new Condition[conditions.size()]));

        return loadBaseModelGrouPoolTitle(pools, false);
    }


    /**
     * 作者：OWEN
     * 时间：2016/12/7 15:01
     * 描述:
     * 获取不跟当前数据池同属同一模型的所有可用数据池
     *
     * @param poolId
     */
    @Override
    public Collection<NptBaseModelPool> getOtherModelAllAvailableGroupools(Long poolId) {
        Collection<NptBaseModelPool> result = new ArrayList<>();
        NptBaseModelPool pool = this.fastFindBaseModelGrouPoolById(poolId);
        if(null != pool) {
            Collection<NptBaseModelPool> searchResult = baseModelGrouPoolManager.findByCondition(
                    Conditions.ne(NptBaseModelPool.PROPERTY_MODEL_ID,pool.getModelId()),
                    Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
            if (null != searchResult) {
                result.addAll(loadBaseModelGrouPoolTitle(searchResult,false));
            }
        }
        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/12/7 14:32
     * 描述:
     * 获取所有业务主键跟指定字段杰克匹配的数据池
     *
     * @param fieldId
     */
    @Override
    public Collection<NptBaseModelPool> getAllPossibleGroupools(Long poolId, Long fieldId) {
        List<NptBaseModelPool> result = new ArrayList<>();
        Collection<NptBaseModelPool> availablePools = this.getOtherModelAllAvailableGroupools(poolId);
        if(null != availablePools && !availablePools.isEmpty()){
            NptLogicDataField fkField = rmsArchService.fastFindDataFieldById(fieldId);
            if(null == fkField){
                result.addAll(availablePools);
            }else {
                for(NptBaseModelPool p:availablePools){
                    NptLogicDataField pkField = rmsArchService.fastFindDataFieldById(p.getPrimaryFieldId());
                    if(null != pkField){
                        Integer likeLen = NptRmsUtil.businessList(pkField,fkField);
                        if(likeLen > NptCommonUtil.IntegerZero()){
                            p.setiFlag(likeLen);
                            result.add(p);
                        }
                    }
                }
                Comparator<NptBaseModelPool> comparator = (o1, o2) -> o2.getiFlag() - o1.getiFlag();
                Collections.sort(result,comparator);
            }
        }
        return result;
    }

    /**
     * 作者：owen
     * 日期：2016/10/24 13:43
     * 备注：
     * 获取模型的主数据池
     * 参数：
     * 返回：
     *
     * @param m
     */
    @Override
    public NptBaseModelPool getBaseModelGroupMainPool(NptBaseModel m) {
        if (null == m) {
            return null;
        }
        NptBaseModelPool mainPool = baseModelGrouPoolManager.findUniqueByCondition(
                Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()),
                Conditions.eq(NptBaseModelPool.PROPERTY_MODEL_ID, m.getId()),
                Conditions.eq(NptBaseModelPool.PROPERTY_POOL_WEIGHT, NptDict.CLD_MAIN.getCode()));
        if(null != mainPool){
            Boolean inUse = loadBaseModelGroupoolTitle(mainPool,false);
            if(inUse){
                return mainPool;
            }else {
                return null;
            }
        }
        return mainPool;
    }

    /**
     *作者：owen
     *时间：2016/12/21 17:59
     *描述:
     *      检测模型是否有主数据池
     */
    public Boolean isBaseModelHaveMainPool(NptBaseModel m){
        if (null == m) {
            return false;
        }
        return baseModelGrouPoolManager.countByCondition(
                Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()),
                Conditions.eq(NptBaseModelPool.PROPERTY_MODEL_ID, m.getId()),
                Conditions.eq(NptBaseModelPool.PROPERTY_POOL_WEIGHT, NptDict.CLD_MAIN.getCode()))
                > NptCommonUtil.IntegerZero();
    }

    private Collection<NptBaseModelPool> loadBaseModelGrouPoolTitle(Collection<NptBaseModelPool> pools,Boolean needLink){
        Collection<NptBaseModelPool> result = new ArrayList<>();
        if(null != pools && !pools.isEmpty()) {
            for (NptBaseModelPool pool : pools) {
               if(loadBaseModelGroupoolTitle(pool,needLink)){
                   result.add(pool);
               }
            }
        }

        return result;
    }

    private Boolean loadBaseModelGroupoolTitle(NptBaseModelPool pool,Boolean needLink){
        if(null == pool){
            return false;
        }
        NptLogicDataType type = baseModelGrouPoolManager.getBaseModelGrouPoolDataType(pool);
        if (null == type || !type.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {
            return false;
        }
        if (StringUtils.isBlank(pool.getAlias())) {
            pool.setPoolTitle(type.getTypeName());
        } else {
            pool.setPoolTitle(pool.getAlias());
        }
        NptLogicDataProvider provider = rmsArchService.findParent(type);
        if(null != provider){
            pool.setProviderId(provider.getId());
            pool.setProviderTitle(provider.getOrgName());
        }
        NptLogicDataField pField = rmsArchService.fastFindDataFieldById(pool.getPrimaryFieldId());
        if (null != pField) {
            pool.setpFieldTitle(pField.getAlias());
        }
        if (needLink) {
            Integer linkedCount = this.getBaseModelGrouPoolLinkCount(pool, NptDict.IDS_ENABLED);
            pool.setLinkedPoolCount(linkedCount);
        }else {
            pool.setLinkedPoolCount(NptCommonUtil.INTEGER_0);
        }


        return true;
    }


    /**
     * 作者：owen
     * 日期：2016/10/20 14:24
     * 备注：
     * 获取基础模型的权重组
     * 参数：
     * 返回：
     *
     * @param m
     */
    @Override
    public NptBaseModelGroup getBaseModelMainGroup(NptBaseModel m) {
        NptBaseModelPool mainPool = this.getBaseModelGroupMainPool(m);
        if(null != mainPool){
            return baseModelGroupManager.fastFindById(mainPool.getGroupId());
        }
        return null;
    }

    /**
     * 作者：owen
     * 日期：2016/10/20 21:32
     * 备注：
     * 获取基础模型的所有分组
     * 参数：
     * 返回：
     *
     * @param m
     */
    @Override
    public Collection<NptBaseModelGroup> getBaseModelGroups(NptBaseModel m) {
        return this.getBaseModelGroups(m,null);
    }

    @Override
    public Collection<NptBaseModelGroup> getBaseModelGroups(NptBaseModel m, NptDict status) {

        Collection<NptBaseModelGroup> result = baseModelGroupManager.findByCondition(
                Conditions.eq(NptBaseModelGroup.PROPERTY_MODEL_ID, m.getId()),
                null != status?Conditions.eq(NptBaseModelGroup.PROPERTY_STATUS,status.getCode()):
                Conditions.ne(NptBaseModelGroup.PROPERTY_STATUS, NptDict.IDS_DELETED.getCode()),
                Conditions.asc(NptBaseEntity.PROPERTY_DISPLAY_ORDER));
        if(null != result && result.size() > NptCommonUtil.IntegerZero()){
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
            return rmsArchService.fastFindDataFieldById(fieldId);
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
        return this.baseModelPoolIndexManager.getBaseModelGrouPoolFieldById(id);
    }

    /**
     * 作者：owen
     * 日期：2016/10/20 14:29
     * 备注：
     * 获取指定模型组数据池的主字段,若业务主键不存在于主字段中，将追加之
     * 参数：
     * 返回：
     *
     * @param p
     */
    @Override
    public Collection<NptLogicDataField> getBaseModelIndexDataFields(NptBaseModelPool p) {

        List<NptLogicDataField> result = new ArrayList<>();

        if(null != p) {
            /**
             * 加载索引字段
             */
            Collection<NptBaseModelPoolIndex> idxFields = this.getBaseModelPoolIndex(p);
            /**
             * 加载数据类别
             */
            NptLogicDataType poolType = rmsArchService.fastFindDataTypeById(p.getDataTypeId());
            /**
             * 数据类别必须已配置数据主键
             */
            if(null != poolType && !NptCommonUtil.getDefaultParentId().equals(poolType.getUkFieldId())) {
                /**
                 * 模型数据展示的过程中，必须提取数据主键，将其放置到每行数据的第一个位置，在页面上隐藏显示，
                 * 以便通过此隐藏的数据主键进行数据详情的定位工作
                 */
                NptLogicDataField uniqueField = rmsArchService.fastFindDataFieldById(poolType.getUkFieldId());
                if (null != uniqueField) {
                    uniqueField.setDisplay(NptCommonUtil.IntegerZero());
                    result.add(uniqueField);
                    /**
                     * 加载其它的索引字段
                     */
                    List<NptLogicDataField> sortedList = new ArrayList<>();
                    if (null != idxFields && !idxFields.isEmpty()) {
                        for (NptBaseModelPoolIndex field : idxFields) {
                            NptLogicDataField temp = rmsArchService.fastFindDataFieldById(field.getFieldId());
                            if (null != temp && temp.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {
                                sortedList.add(temp);
                            }
                        }
                    }
                    Collections.sort(sortedList, (o1, o2) -> {
                        if(o1.getDisplayOrder() > o2.getDisplayOrder()){
                            return 1;
                        }
                        if(o1.getDisplayOrder() == o2.getDisplayOrder()){
                            return 0;
                        }
                        return -1;
                    });


                    result.addAll(sortedList);
                }
            }
        }
        return result;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/16 17:25
     * 描述:
     * 获取用户指定的数据池的主字段
     *
     * @param p
     */
    @Override
    public Collection<NptBaseModelPoolIndex> getBaseModelPoolIndex(NptBaseModelPool p) {

        Collection<NptBaseModelPoolIndex> result = new ArrayList<>();
        Collection<NptBaseModelPoolIndex> searchResult = baseModelPoolIndexManager.findByCondition(
                        Conditions.eq(NptBaseModelPoolIndex.PROPERTY_POOL_ID,p.getId()),
                        Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()),
                        Conditions.asc(NptBaseEntity.PROPERTY_DISPLAY_ORDER));

        if(null != searchResult && !searchResult.isEmpty()){
            for(NptBaseModelPoolIndex idx:searchResult){
                NptLogicDataField field = rmsArchService.fastFindDataFieldById(idx.getFieldId());
                if(null != field && field.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
                    result.add(idx);
                }
            }
        }
        return result;
    }

    /**
     * 作者：owen
     * 日期：2016/10/21 11:53
     * 备注：
     * 确定某个模型的主字段列表
     * 参数：
     * 返回：
     *
     * @param m
     */
    @Override
    public Collection<NptBaseModelPoolIndex> getBaseModelIndex(NptBaseModel m) {

        NptBaseModelPool mainPool = getBaseModelGroupMainPool(m);
        if(null == mainPool){
            return null;
        }

        return this.getBaseModelPoolIndex(mainPool);
    }


    /**
     * 作者: owen
     * 时间: 2017/3/15 下午8:08
     * 描述:
     * 确定指定模型的主数据池的查询字段列表
     *
     * @param m
     */
    @Override
    public Collection<NptBaseModelPoolCdt> getBaseModelCondition(NptBaseModel m) {
        NptBaseModelPool mainPool = getBaseModelGroupMainPool(m);
        if(null == mainPool){
            return null;
        }
        return this.getBaseModelPoolConditions(mainPool);
    }


    /**
     * 作者: owen
     * 时间: 2017/3/15 下午8:15
     * 描述:
     * 加载模型核心数据池的查询条件
     *
     * @param model
     */
    @Override
    public void loadBaseModelConditions(NptBaseModel model,NptWebBridgeBean bean) {
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
     * 日期：2016/10/20 14:33
     * 备注：
     * 获取指定数据池的表信息
     * 参数：
     * 返回：
     *
     * @param p
     */
    @Override
    public NptLogicDataType getBaseModelGrouPoolDataType(NptBaseModelPool p) {
        return baseModelGrouPoolManager.getBaseModelGrouPoolDataType(p);
    }

    /**
     * 作者：owen
     * 日期：2016/10/20 14:35
     * 备注：
     * 获取数据池的数据提供方信息
     * 参数：
     * 返回：
     *
     * @param p
     */
    @Override
    public NptLogicDataProvider getBaseModelGrouPoolProvider(NptBaseModelPool p){
        if(null != p){
            Long dataTypeId = p.getDataTypeId();
            NptLogicDataType dataType = rmsArchService.fastFindDataTypeById(dataTypeId);
            if(null != dataType){
                return rmsArchService.findParent(dataType);
            }
        }
        return null;
    }

    /**
     * 作者：owen
     * 日期：2016/10/20 16:05
     * 备注：
     * 加载指定模型的默认可公开字段数据列表
     * 参数：
     * 返回：
     *
     * @param bean
     */
    @Override
    public NptDict loadBaseModelOpenList(NptWebBridgeBean bean, NptBaseModel model, Boolean applyed) {
        //获取模型的主组
        NptBaseModelPool mainPool = getBaseModelGroupMainPool(model);
        if(null == mainPool){
            return NptDict.RST_EXCEPTION("模型ID：" + model.getId() + "不存在主数据池");
        }
        return getBaseModelGroupoolPaginationData(mainPool.getId(),bean,true,false,false);
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
                NptLogicDataType dataType = rmsArchService.fastFindDataTypeById(pool.getDataTypeId());
                if(null != dataType){
                    Map<String,String> orWhere = new HashMap<>();
                    for(NptBaseModelPoolCdt c:cdts){
                        orWhere.put(c.getFieldDBName(),keyword);
                    }
                    NptLogicDataField ukField = rmsArchService.findDataFieldById(dataType.getUkFieldId());
                    if(null != ukField) {
                        Collection<NptLogicDataField> searchFields = new ArrayList<>();
                        searchFields.add(ukField);
                        Collection<NptLogicDataField> pubFields = getBaseModelIndexDataFields(pool);
                        if (null != pubFields && !pubFields.isEmpty()) {

                            searchFields.addAll(pubFields);
                            String sql = databaseManager.makeMultiLikeSql(
                                    dataType.getTypeDbName(),
                                    searchFields,
                                    orWhere,
                                    bean.getCurrPage(),
                                    bean.getPageSize(),
                                    NptDict.CST_ENG_AS_CHN);

                            if (!StringUtils.isBlank(sql)) {
                                Collection<NptWebFieldDataArray> formatData = queryAndFormat(sql, pubFields, null, true);
                                if (null != formatData && !formatData.isEmpty()) {
                                    bean.setDataList(formatData);
                                    bean.setTotalCount(formatData.size());
                                    bean.setColumnTitles(formatData.iterator().next().getTitleList());
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
                NptLogicDataType dataType = rmsArchService.fastFindDataTypeById(pool.getDataTypeId());
                if (null != dataType) {
                    Map<String, String> orWhere = new HashMap<>();
                    for (NptBaseModelPoolCdt c : cdts) {
                        orWhere.put(c.getFieldDBName(), keyword);
                    }
                    NptLogicDataField ukField = rmsArchService.findDataFieldById(dataType.getUkFieldId());
                    if (null != ukField) {
                        Collection<NptLogicDataField> searchFields = new ArrayList<>();
                        searchFields.add(ukField);
                        Collection<NptLogicDataField> pubFields = getBaseModelIndexDataFields(pool);
                        if (null != pubFields && !pubFields.isEmpty()) {

                            searchFields.addAll(pubFields);
                            String sql = databaseManager.makeMultiLikeSql(
                                    dataType.getTypeDbName(),
                                    searchFields,
                                    orWhere,
                                    null,
                                    null,
                                    NptDict.CST_ENG_AS_CHN);

                            if (!StringUtils.isBlank(sql)) {
                                Collection<NptWebFieldDataArray> formatData = queryAndFormat(sql, pubFields, null, true);
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


    /**
     *作者：owen
     *时间：2016/12/16 15:29
     *描述:
     *      通过数据主键获取模型主数据池中的业务主键值
     */
    @Override
    public String getModelPoolPKValueByUKValue(NptBaseModelPool pool, String ukValue){
        if(null == pool || null == ukValue || ukValue.isEmpty()){
            return null;
        }
        NptLogicDataType mainType = rmsArchService.fastFindDataTypeById(pool.getDataTypeId());
        if(null == mainType || NptCommonUtil.getDefaultParentId().equals(mainType.getUkFieldId())){
            return null;
        }
        NptLogicDataField uField = rmsArchService.fastFindDataFieldById(mainType.getUkFieldId());
        if(null == uField){
            return null;
        }
        NptLogicDataField pField = baseModelPoolIndexManager.getBaseModelGrouPoolFieldById(pool.getPrimaryFieldId());
        if(null == pField){
            return null;
        }
        //获取物理表信息及依据数据主键查询出业务主键的值，以便进行数据池之间的业务主键关系查询
        NptLogicDataType pType = baseModelGrouPoolManager.getBaseModelGrouPoolDataType(pool);

        Collection<NptLogicDataField> onlySelectPField = new ArrayList<>();
        onlySelectPField.add(pField);
        Map<String,String> uFieldWhere = new HashMap<>();
        uFieldWhere.put(uField.getFieldDbName(),ukValue);

        //加载业务主键实体值
        if(null != pType){
            String sql = databaseManager.makeUniqueSql(pType.getTypeDbName(),onlySelectPField,uFieldWhere, NptDict.CST_ENG_AS_CHN);
            Map<String,Object> result = (Map<String, Object>) databaseManager.queryUnique(sql);
            if(null != result && 1 == result.size()) {
                return String.valueOf(result.values().iterator().next());
            }
        }
        return null;
    }

    /**
     * 作者：owen
     * 日期：2016/10/20 16:07
     * 备注：
     * 加载指定模型的某条业务记录的详细分组数据
     * 这里有三项入口信息：数据主键，数据主键的值，业务主键
     *      其中，数据主键及其值用于定位表里的唯一一行记录
     *      业务主键用于主数据向副数据的扩展
     * 参数：
     * 返回：
     *
     * @param bean
     * @param m
     */
    @Override
    public NptDict loadBaseModelAuthGroupsByUK(NptWebBridgeBean bean, NptBaseModel m) {

        NptBaseModelPool mainPool = getBaseModelGroupMainPool(m);

        String pkValueObj = getModelPoolPKValueByUKValue(mainPool,bean.getPrimaryKeyValue());

        bean.setPrimaryKeyValue(pkValueObj);

        return loadBaseModelAuthGroupsByPK(bean,m);
    }


    @Override
    public NptDict loadBaseModelPoolDataByUK(NptWebBridgeBean bean, NptBaseModelPool p) {

        String pkValueObj = getModelPoolPKValueByUKValue(p,bean.getPrimaryKeyValue());

        bean.setPrimaryKeyValue(pkValueObj);

        return loadBaseModelPoolAuthBlocksByPK(bean,p);
    }

    @Override
    public NptDict loadBaseModelDetailByUK(NptWebBridgeBean bean, NptBaseModel m) {
        NptBaseModelPool mainPool = baseModelService.fastFindBaseModelGrouPoolById(bean.getParentId());
        //NptBaseModelPool mainPool = getBaseModelGroupMainPool(m);
        String pkValueObj = getModelPoolPKValueByUKValue(mainPool,bean.getPrimaryKeyValue());
        if(pkValueObj!=null){
            pkValueObj=pkValueObj.replaceAll("\\s*", "");
        }
        bean.setPrimaryKeyValue(pkValueObj);
        return loadBaseModelAuthGroupsByPK(bean,m);
    }

    /**
     *作者：OWEN
     *时间：2016/11/21 15:32
     *描述:
     *      通过业务主键加载指定模型的分组详情
     */
    public NptDict loadBaseModelAuthGroupsByPK(NptWebBridgeBean bean, NptBaseModel m) {
        if(null == bean || null == m){
            return NptDict.RST_EXCEPTION("参数未初始或模型不存在!");
        }
        String pkValue = bean.getPrimaryKeyValue();
        if(null == pkValue || pkValue.isEmpty()){
            return NptDict.RST_EXCEPTION("业务主键值不能为空！");
        }

        Collection<NptWebDetailGroup> webGroupList = new ArrayList<>();
        //加载模型的所有分组及每个分组的数据池
        Collection<NptBaseModelGroup> groupList = baseModelGroupManager.getBaseModelGroups(m);
        Long providerId = bean.getListCondition(NptWebBridgeBean.PROVIDER_QUERY_CONDITION_NAME);
        if(null == groupList || groupList.isEmpty()){
            //当前模型未分组，则直接去加载数据池的相关信息,即将模型下调为组级别
            Collection<NptBaseModelPool> aPoolList = getBaseModelGrouPools(m,NptDict.IDS_ENABLED,true);
            if(null != aPoolList && !aPoolList.isEmpty()){

                NptWebDetailGroup wGroup = loadOrderedGroupoolsData(m.getModelNote(),aPoolList,pkValue,providerId);
                webGroupList.add(wGroup);
            }

        }else {
            //当前模型已分组，则依次加载其每个组的相关信息
            for(NptBaseModelGroup group:groupList) {
                Long listGroupId = bean.getListCondition(NptWebBridgeBean.GROUP_QUERY_CONDITION_NAME);
                if(null == listGroupId || (null != listGroupId && listGroupId.equals(group.getId()))) {
                    Collection<NptBaseModelPool> aPoolList = getBaseModelGrouPools(group,NptDict.IDS_ENABLED,true);
                    if (null != aPoolList && !aPoolList.isEmpty()) {
                        NptWebDetailGroup wGroup = loadOrderedGroupoolsData(group.getGroupTitle(), aPoolList, pkValue,providerId);
                        webGroupList.add(wGroup);
                    }
                }
            }
        }

        bean.setDataList(webGroupList);
        bean.setPrimaryKeyValue(pkValue);

        return NptDict.RST_SUCCESS;
    }

    @Override
    public NptDict loadBaseModelPoolAuthBlocksByPK(NptWebBridgeBean bean, NptBaseModelPool pool) {
        if(null == bean || null == pool){
            return NptDict.RST_EXCEPTION("参数未初始或数据池不存在!");
        }
        String pkValue = bean.getPrimaryKeyValue();
        if(null == pkValue || pkValue.isEmpty()){
            return NptDict.RST_EXCEPTION("业务主键值不能为空！");
        }

        Collection<NptWebDetailGroup> webGroupList = new ArrayList<>();
        Collection<NptWebDetailBlock> blockList = new ArrayList<>();
        NptWebDetailBlock block = getBaseModelGrouPoolData(pool, String.valueOf(pkValue), true);
        if(null != block && block.getDataCount() > NptCommonUtil.INTEGER_0){
            blockList.add(block);
        }
        NptWebDetailGroup group = new NptWebDetailGroup();
        group.setBlockList(blockList);
        webGroupList.add(group);
        bean.setDataList(webGroupList);

        return NptDict.RST_SUCCESS;
    }

    /**
     *  author: owen
     *  date:   31/03/2017 9:32 PM
     *  note:
     *          依据分组的业务主键加载指定分组的数据
     *
     *          要求分组内的数据池的业务主键业务含义相同
     */
    @Override
    public NptDict loadBaseModelGroupDataByUnitedPk(NptBaseModelGroup group, String pkValue, NptWebBridgeBean bean){

        if(null == group || StringUtils.isBlank(pkValue) || null == bean){
            return NptDict.RST_INVALID_PARAMS;
        }

        Collection<NptWebDetailGroup> webGroupList = new ArrayList<>();
        NptWebDetailGroup webGroup = new NptWebDetailGroup();
        webGroup.setGroupTitle(group.getGroupTitle());

        Collection<NptWebDetailBlock> blocks = new ArrayList<>();
        Collection<NptBaseModelPool> pools = getBaseModelGrouPools(group,NptDict.IDS_ENABLED,true);

        if(null != pools && !pools.isEmpty()){
            Iterator<NptBaseModelPool> ite = pools.iterator();
            while (ite.hasNext()){
                NptBaseModelPool pool = ite.next();
                NptWebDetailBlock block = getBaseModelGrouPoolData(pool, String.valueOf(pkValue), true);
                if(block.getDataCount() > NptCommonUtil.INTEGER_0){
                    blocks.add(block);
                }
            }
            webGroup.setBlockList(blocks);
        }
        webGroupList.add(webGroup);
        bean.setDataList(webGroupList);
        bean.setPrimaryKeyValue(pkValue);
        bean.setTotalCount(blocks.size());
        return NptDict.RST_SUCCESS;
    }

    /**
     * 作者：owen
     * 日期：2016/10/28 15:03
     * 备注：
     *      加载POOL集合中每个POOL的数据，其中第一个POOL会加载实体数据，其它POLL只先加载基本信息
     * 参数：
     * 返回：
     */
    private NptWebDetailGroup loadOrderedGroupoolsData(String title, Collection<NptBaseModelPool> poolList, Object pkValue, Long providerId){
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
                    NptWebDetailBlock block = getBaseModelGrouPoolData(pool, String.valueOf(pkValue), true);
                    if(null != block && block.getDataCount() > NptCommonUtil.INTEGER_0){
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
    public NptWebDetailBlock getBaseModelGrouPoolData(NptBaseModelPool pool, String pkValue, Boolean first) {
        //构建结果块
        NptWebDetailBlock<NptBaseModelPool,NptLogicDataField,NptLogicDataProvider> block = new NptWebDetailBlock();
        if(null == pool){
            return null;
        }
        //获取数据池的物理表信息
        NptLogicDataType poolType = baseModelGrouPoolManager.getBaseModelGrouPoolDataType(pool);

        NptLogicDataProvider providerOrg = baseModelGrouPoolManager.getBaseModelGrouPoolDataTypeProvider(pool);
        //获取数据池的物理字段集合
        Collection<NptLogicDataField> fieldList = baseModelGrouPoolManager.getBaseModelGrouPoolFields(pool, NptDict.IDS_ENABLED);
        if(null == poolType || !poolType.getStatus().equals(NptDict.IDS_ENABLED.getCode()) || null == fieldList || fieldList.isEmpty()){
            return null;
        }
        fieldList.addAll(databaseManager.getReservedDataField());
        //数据池自身信息
        pool.setPoolTitle(poolType.getAlias());
        block.setBlockInfo(pool);
        //数据池提供方信息
        block.setParentInfo(providerOrg);
        if(null != pkValue && !pkValue.isEmpty()){
            //构建业务主键查询条件
            NptLogicDataField pkField = this.getBaseModelGrouPoolPrimaryField(pool);
            if(null == pkField){
                return null;
            }

            Map<String,String> pkWhere = new HashMap<>();
            pkWhere.put(pkField.getFieldDbName(),pkValue);
            Integer queryCount = 1;
            if(!first){
                queryCount = NptBaseModelGrouPoolManager.DEFAULT_POOL_LOADED_COUNT;
            }
            //构建查询语句
            String[] sql = databaseManager.makeLastedDataSql(
                    poolType.getTypeDbName(),
                    fieldList,
                    pkWhere,
                    null,
                    queryCount
            );

            //确认是否存在实体数据
            int totalCount = 0;
            try {
                totalCount = databaseManager.getCount(sql[0]);
                if(totalCount > NptCommonUtil.IntegerZero()){
                    NptSimpleUser user = rmsAuthService.getUserById((Long) PlatformSecurityContext.getCurrentOperator().getOperatorId());
                    if(null != user) {
                        NptDataPermission permission = new NptDataPermission();
                        permission.setOrgId(providerOrg.getId());
                        permission.setAction(NptDict.PMS_DATA_PENETRATION.getCode());
                        boolean dataPenetration = rmsAuthService.havePermission(user,permission);
                        if(!dataPenetration) {
                            dataPenetration = applyManager.isUserValideApplyEffective(user.getUserId(),pool.getId(),pkValue);
                        }
                        block.setDataArray(queryAndFormat(sql[1], fieldList,pool,dataPenetration));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //当前数据池指定业务对象的所有字段值
            block.setDataCount(totalCount);
        }

        return block;
    }

    @Override
    public Collection<NptWebFieldDataArray> getBaseModelGrouPoolDataList(NptBaseModelPool pool, List<Long> fields,String pkValue,Boolean applyed) {

        if(null != pool && pool.checkIsEnable()){
            //获取数据池的物理表信息
            NptLogicDataType poolType = baseModelGrouPoolManager.getBaseModelGrouPoolDataType(pool);
            List<NptLogicDataField> searchField = new ArrayList<>();
            if(null == fields) {
                NptLogicDataField titleField = rmsArchService.fastFindDataFieldById(pool.getTitleFieldId());
                if(null != titleField){
                    searchField.add(titleField);
                }
                Collection<NptLogicDataField> indexFields = this.getBaseModelIndexDataFields(pool);
                if(null != indexFields){
                    for(NptLogicDataField idx:indexFields){
                        if(!searchField.contains(idx)){
                            searchField.add(idx);
                        }
                    }
                }
            }else {
                for(Long fid:fields){
                    NptLogicDataField f = this.getBaseModelGrouPoolFieldById(fid);
                    if(null != f){
                        searchField.add(f);
                    }
                }
            }

            Map<String, String> pkWhere = new HashMap<>();
            if (!StringUtils.isEmpty(pkValue)) {
                //构建业务主键查询条件
                NptLogicDataField pkField = this.getBaseModelGrouPoolPrimaryField(pool);
                if (null != pkField && pkField.checkIsEnable() && null != searchField && !searchField.isEmpty()) {

                    pkWhere.put(pkField.getFieldDbName(), pkValue);

                }
            }
            //构建查询语句
            String[] sql = databaseManager.makeAllDataSql(
                    poolType.getTypeDbName(),
                    searchField,
                    pkWhere,
                    null,
                    null);

            int totalCount = databaseManager.getCount(sql[0]);
            if (totalCount > NptCommonUtil.IntegerZero()) {
                List<Object> queryData = databaseManager.queryList(sql[1], searchField);
                if (null != queryData && !queryData.isEmpty()) {
                    return formatTitleData(queryData, searchField, pool, applyed);
                }
            }
        }
        return null;
    }


    /**
     * 作者：owen
     * 日期：2016/11/4 11:54
     * 备注：
     * 检测当前数据池是否有关联的数据池
     * 参数：
     * 返回：
     *
     * @param p
     */
    @Override
    public Integer getBaseModelGrouPoolLinkCount(NptBaseModelPool p, NptDict status) {
        if(null != p) {
            Collection<NptBaseModelLink> result = new ArrayList<>();
            if(null != status) {
                Collection<NptBaseModelLink> popList = baseModelPool2PoolManager.findByCondition(
                        Conditions.eq(NptBaseModelLink.PROPERTY_FROM_POOL_ID, p.getId()),
                        Conditions.eq(NptBaseEntity.PROPERTY_STATUS,status.getCode()));
                if (null != popList) {
                    result.addAll(popList);
                }
            }else {
                Collection<NptBaseModelLink> popList = baseModelPool2PoolManager.findByCondition(
                        Conditions.eq(NptBaseModelLink.PROPERTY_FROM_POOL_ID, p.getId()));
                if (null != popList) {
                    result.addAll(popList);
                }
            }
            return result.size();
        }
        return 0;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/13 23:15
     * 描述:
     *
     * @param p
     */
    @Override
    public Collection<NptBaseModelLink> getBaseModelGroupoolLinkedPools(NptBaseModelPool p, NptDict status) {
        if(null != p) {
            Collection<NptBaseModelLink> popList;
            if(null != status) {
               popList = baseModelPool2PoolManager.findByCondition(
                        Conditions.eq(NptBaseModelLink.PROPERTY_FROM_POOL_ID, p.getId()),
                        Conditions.eq(NptBaseEntity.PROPERTY_STATUS,status.getCode()));
            }else {
                 popList = baseModelPool2PoolManager.findByCondition(
                        Conditions.eq(NptBaseModelLink.PROPERTY_FROM_POOL_ID, p.getId()));
            }
            if(null != popList && !popList.isEmpty()){
                for(NptBaseModelLink p2p:popList){
                    NptBaseModelPool toPool = this.findBaseModelGrouPoolById(p2p.getToPoolId());
                    NptLogicDataProvider provicer = this.getBaseModelGrouPoolProvider(toPool);
                    NptLogicDataField fkField = rmsArchService.fastFindDataFieldById(p2p.getPoolRefKeyId());
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
     * 作者：OWEN
     * 时间：2016/12/7 17:05
     * 描述:
     * 查询关联向指定数据池的其它数据池
     * <p>
     * status为null则表示全部状态
     *
     * @param p
     * @param status
     */
    @Override
    public Collection<NptBaseModelLink> getBaseModelGroupoolLinkedMePools(NptBaseModelPool p, NptDict status) {
        if (null != p) {
            Collection<NptBaseModelLink> popList;
            if (null != status) {
                popList = baseModelPool2PoolManager.findByCondition(
                        Conditions.eq(NptBaseModelLink.PROPERTY_TO_POOL_ID, p.getId()),
                        Conditions.eq(NptBaseEntity.PROPERTY_STATUS, status.getCode()));
            } else {
                popList = baseModelPool2PoolManager.findByCondition(
                        Conditions.eq(NptBaseModelLink.PROPERTY_TO_POOL_ID, p.getId()));
            }
            if (null != popList && !popList.isEmpty()) {
                for (NptBaseModelLink p2p : popList) {
                    NptBaseModelPool toPool = this.findBaseModelGrouPoolById(p2p.getToPoolId());
                    NptLogicDataProvider provicer = this.getBaseModelGrouPoolProvider(toPool);
                    NptLogicDataField fkField = rmsArchService.fastFindDataFieldById(p2p.getPoolRefKeyId());
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
     * 作者：OWEN
     * 时间：2016/11/14 15:19
     * 描述:
     * 查询指定数据池指定模型外键关联的其它数据池
     *
     * @param poolId
     * @param fieldId
     */
    @Override
    public Collection<NptBaseModelLink> getBaseModelGroupoolLinkedPools(Long poolId, Long fieldId) {
        Collection<NptBaseModelLink> result = new ArrayList<>();
        Collection<NptBaseModelLink> searchResult = baseModelPool2PoolManager.findByCondition(
                Conditions.eq(NptBaseModelLink.PROPERTY_FROM_POOL_ID, poolId),
                Conditions.eq(NptBaseModelLink.PROPERTY_FROM_POOL_REFKEYID,fieldId),
                Conditions.not(Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_DELETED.getCode())));
        if(null != searchResult){
            result.addAll(searchResult);
        }
        return result;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/11 下午09:09
     * 备注:
     */
    @Override
    public Collection<NptBaseModelLink> listBaseModelGroupoolLinkedPools(Long fromPoolId, Long fkFieldId, Long toPoolId) {
        return baseModelPool2PoolManager.findByCondition(
                Conditions.eq(NptBaseModelLink.PROPERTY_FROM_POOL_ID, fromPoolId),
                Conditions.eq(NptBaseModelLink.PROPERTY_FROM_POOL_REFKEYID, fkFieldId),
                Conditions.eq(NptBaseModelLink.PROPERTY_TO_POOL_ID, toPoolId),
                Conditions.not(Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_DELETED.getCode())));
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/16 22:53
     * 描述:
     * 设置模型关联的状态
     *
     * @param linkId
     * @param status
     */
    @Override
    public NptDict setBaseModelGroupoolLinkStatus(Long linkId, String status) {
        NptBaseModelLink p2p = baseModelPool2PoolManager.findById(linkId);
        if(null == p2p){
            return NptDict.RST_EXCEPTION("模型关联信息不存在！");
        }
        NptDict dict = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.IDS,status);
        if(null == dict){
            return NptDict.RST_EXCEPTION("指定的模型关联状态异常！");
        }
        if(!p2p.getStatus().equals(dict.getCode())){
            p2p.setStatus(dict.getCode());
            baseModelPool2PoolManager.update(p2p);
        }
        return NptDict.RST_SUCCESS;
    }

    /**
     * 作者：owen
     * 日期：2016/10/31 14:48
     * 备注：
     *      构建字段申请列表
     * 参数：
     * 返回：
     */
    private void initFieldApply(NptWebDetailBlock block, User user, NptBaseModelPool p, String pkValue){
        if(null != p){
            NptLogicDataType pType = baseModelGrouPoolManager.getBaseModelGrouPoolDataType(p);
            if(null != pType) {
                p.setPoolTitle(pType.getAlias());
            }
            block.setBlockInfo(p);

            NptLogicDataProvider pOrg = baseModelGrouPoolManager.getBaseModelGrouPoolDataTypeProvider(p);
            if(null != pOrg){
                block.setParentInfo(pOrg);
            }

            try {
                Long belongedOrgId = Long.parseLong(user.getField01());
                NptLogicDataProvider userOrg = baseModelGrouPoolManager.getBaseModelGrouPoolDataTypeProviderById(belongedOrgId);
                if(null != userOrg){
                    block.setStrTempThree(userOrg.getOrgName());
                }
            }catch (Exception e){
                block.setStrTempThree(NptDict.RST_UNKNOW.getTitle());
            }

            Collection<NptLogicDataField> fieldList = baseModelGrouPoolManager.getBaseModelGrouPoolDefaultHiddenFields(p);
            block.setDataCount(0);
            if(!fieldList.isEmpty()){
                block.setDataCount(fieldList.size());
                //获取当前用户的已申请字段
                Collection<NptResourceApplyField> applyFields = applyManager.getUserValideApplyedFields(
                        user.getId(),
                        p.getId(),
                        pkValue);
                List<Long> applyedFieldIdList = new ArrayList<>();
                if(null != applyFields && !applyFields.isEmpty()){
                    for(NptResourceApplyField f:applyFields){
                        applyedFieldIdList.add(f.getFieldId());
                    }
                }
                for(NptLogicDataField f:fieldList){
                    if(applyedFieldIdList.contains(f.getId())){
                        f.setbFlag(true);
                    }else {
                        f.setbFlag(false);
                    }
                }
                block.setColumnList(fieldList);
            }
        }
    }

    /**
     * 作者：owen
     * 日期：2016/10/26 12:19
     * 备注：
     * 加载当前用户对指定数据池的字段申请状态
     * 参数：
     * 返回：
     *      最终返回的字段列表中,bFlag为true表示已申请，页面需要打上勾，是false的表示未申请，不需要打勾
     * @param p
     */
    @Override
    public NptWebDetailBlock getBaseModelGroupoolApplyFields(NptBaseModelPool p, String pkValue) {
        NptWebDetailBlock<NptBaseModelPool,NptLogicDataField,NptLogicDataProvider> block = new NptWebDetailBlock<>();
        //获取当前用户息
        User currentUser = (User) PlatformSecurityContext.getCurrentOperator().getProxy();
        if(null == currentUser){
            block.setStrTempOne(NptDict.RST_UNKNOW.getTitle());
            block.setStrTempTwo(NptDict.RST_ERROR.getTitle());
            return block;
        }

        Collection<NptDict> status = new ArrayList<>();
        status.add(NptDict.RAS_ACCEPTED);
        status.add(NptDict.RAS_WAITTING);
        status.add(NptDict.RAS_PROCESSING);

        Collection<NptResourceApply> applyList = applyManager.getUserApply(currentUser.getId(),p.getId(),pkValue,status);
        //初始化要显示的字段列表
        initFieldApply(block,currentUser,p,pkValue);

        /**
         * 已存在申请，则由strTempOne显示申请状态，strTempTwo申请状态描述,flagOne表示编辑区是否为锁定，0不锁定，1锁定
         */
        block.setReadOnly(NptCommonUtil.IntegerOne());
        if(null != applyList && applyList.size() == NptCommonUtil.IntegerOne()){
            NptResourceApply apply = applyList.iterator().next();
            int applyStatus = apply.getApplyStatus();
            block.setStartTime(apply.getApplyedStartDate());
            block.setEndTime(apply.getApplyedEndDate());
            if(applyStatus == NptDict.RAS_EXPIRED.getCode()){
                //已过期，当申请不存在处理
                block.setReadOnly(NptCommonUtil.IntegerZero());
            }else if(applyStatus == NptDict.RAS_WAITTING.getCode()){
                //已提交过申请但未受理
                block.setStrTempOne(NptDict.RAS_WAITTING.getTitle());
                block.setStrTempTwo(NptDict.RAS_WAITTING.getTitle());
            }else if(applyStatus == NptDict.RAS_ACCEPTED.getCode()){
                //已批准，可正常访问申请的字段数据
                block.setStrTempTwo(NptDict.RAS_ACCEPTED.getTitle());
            }else if(applyStatus == NptDict.RAS_REFUSED.getCode()){
                //已拒绝，不可访问申请的字段数据并需要返回拒绝原因
                block.setStrTempOne(NptDict.RST_ERROR.getTitle());
                NptBusinessFlowLog log = applyManager.getResourceApplyLog(apply.getApplyNo(),applyStatus);
                if(null != log){
                    block.setStrTempTwo(log.getRemark());
                }
            }else if(applyStatus == NptDict.RAS_PROCESSING.getCode()){
                //处理中
                block.setStrTempOne(NptDict.RAS_PROCESSING.getTitle());
                block.setStrTempTwo(NptDict.RAS_PROCESSING.getTitle());
            }else {
                block.setStrTempTwo(NptDict.RST_ERROR.getTitle());
            }
        }else {
            //申请不存在
            block.setReadOnly(NptCommonUtil.IntegerZero());
        }
        return block;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/13 20:55
     * 描述:
     * 添加基础模型基础信息
     *
     *      每种信息实体的系统原生模型只能有一个
     *
     *      每个模型的标题必须不相同
     *
     * @param model
     */
    @Override
    public NptDict addBaseModelBasicInfo(NptBaseModel model) {
        if(null != model){
            /**
             * 只有系统定义模型可以在每个宿主上添加多个模型
             */
            if(!model.getCateId().equals(NptDict.BMC_CUSTOM.getCode())){
                NptDict host = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.BMH,model.getHostId());
                NptDict cate = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.BMC,model.getCateId());
                Collection<NptBaseModel> models = this.listModels(host,cate);

                if(null != models && models.size() > NptCommonUtil.IntegerZero()){
                    return NptDict.RST_EXCEPTION("设置的模型类型错误：系统原生模型及外系统查询模型每个宿主只能存在一个！");
                }
            }
            NptBaseModel _model = baseModelManager.findUniqueByCondition(
                    Conditions.eq(NptBaseModel.PROPERTY_MODEL_TITLE,model.getModelNote()));
            if(null != _model){
                return NptDict.RST_EXCEPTION("模型名称已存在！");
            }

            model.setCreatorId(NptRmsUtil.getCurrentUserId());
            model.setCreateTime(NptCommonUtil.getCurrentSysDate());
            model.setParentId(NptCommonUtil.getDefaultParentId());
            model.setStatus(NptDict.IDS_ENABLED.getCode());


            /**
             * 特殊业务含义的模型，则直接创建预定义的分组
             */
            Collection<NptBaseModelGroup> groups = new ArrayList<>();
            if(model.getHostId() > NptCommonUtil.BMH_SPECIAL_MIN){
                Collection<NptDict> groupDicts = NptCommonUtil.getSpecialModelGroups(model.getHostId());
                if(null == groupDicts || groupDicts.isEmpty()){
                    return NptDict.RST_EXCEPTION("具有业务含义的特殊模型未配置分组!");
                }
                for(NptDict dict:groupDicts){
                    NptBaseModelGroup group = new NptBaseModelGroup();
                    group.setGroupName(dict.getTitle());
                    group.setGroupTitle(dict.getTitle());
                    group.setGroupNote(dict.getTitle());
                    group.setSpecialCode(dict.getCode());
                    groups.add(group);
                }
            }

            baseModelManager.save(model);
            if(model.getHostId() > NptCommonUtil.BMH_SPECIAL_MIN){
                for(NptBaseModelGroup g:groups){
                    addGroupToBaseModel(model.getId(),g);
                }
            }
            return NptDict.RST_SUCCESS;
        }else {
            return NptDict.RST_EXCEPTION("模型参数异常！");
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/13 20:39
     * 描述:
     * 为指定的模型添加分组
     *
     * @param modelId
     * @param group
     */
    @Override
    public NptDict addGroupToBaseModel(Long modelId, NptBaseModelGroup group) {
        NptBaseModel model = this.fastFindBaseModelById(modelId);
        if(null == model){
           return NptDict.RST_EXCEPTION("目标模型不存在！");
        }

        if(null != group){
            NptBaseModelGroup _group = baseModelGroupManager.findUniqueByCondition(
                    Conditions.eq(NptBaseModelGroup.PROPERTY_MODEL_ID,model.getId()),
                    Conditions.eq(NptBaseModelGroup.PROPERTY_GROUP_TITLE,group.getGroupTitle()));
            if(null != _group){
                return NptDict.RST_EXCEPTION("分组名称已存在！");
            }
            group.setModelId(modelId);
            group.setStatus(NptDict.IDS_ENABLED.getCode());
            group.setCreateTime(NptCommonUtil.getCurrentSysDate());
            group.setCreatorId(NptRmsUtil.getCurrentUserId());

            baseModelGroupManager.save(group);
        }
        return NptDict.RST_SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/13 20:40
     * 描述:
     * 从指定的模型中删除指定的分组
     *
     * @param modelId
     * @param groupId
     */
    @Override
    public NptDict setBaseModelGroupStatus(Long modelId, Long groupId, NptDict status) {
        NptBaseModel model = this.findBaseModelById(modelId);
        if(null == model){
            return NptDict.RST_EXCEPTION("指定的模型不存在！");
        }
        NptBaseModelGroup group = this.findBaseModelGroupById(groupId);
        if(null == group){
            return NptDict.RST_EXCEPTION("指定的模型分组不存在！");
        }
        if(!group.getModelId().equals(modelId)){
            return NptDict.RST_EXCEPTION("指定的分组不属于该模型！");
        }
        NptBaseModelGroup mainGroup = this.getBaseModelMainGroup(model);
        if(null != mainGroup && mainGroup.getId().equals(groupId)
                &&(status == NptDict.IDS_DISABLED
                || status == NptDict.IDS_DELETED
                || status == NptDict.IDS_LOCKED)){
            return NptDict.RST_EXCEPTION("主分组不允许被禁用锁定与删除！");
        }
        if(status.getCode() != group.getStatus()){
            group.setStatus(status.getCode());
            baseModelGroupManager.update(group);
            Collection<NptBaseModelPool> pools = this.getBaseModelGrouPools(group,null,false);
            if(null != pools && !pools.isEmpty()){
                for(NptBaseModelPool p:pools){
                    this.setBaseModeGroupoolStatus(groupId,p.getId(),status);
                }
            }
            group.setStatus(status.getCode());
            baseModelGroupManager.update(group);
        }
        return NptDict.RST_SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/12/6 11:08
     * 描述:
     * 设置指定模型的状态
     *
     * @param modelId
     * @param status
     */
    @Override
    public NptDict setBaseModelStatus(Long modelId, NptDict status) {
        NptBaseModel model = this.findBaseModelById(modelId);
        if(null == model){
            return NptDict.RST_EXCEPTION("目标模型不存在！");
        }
        if(!model.getStatus().equals(status.getCode())){
           Collection<NptBaseModelGroup> groupList = this.getBaseModelGroups(model);
           Collection<NptBaseModelPool> poolList = new ArrayList<>();
           Collection<NptBaseModelPoolIndex> indexFieldList = new ArrayList<>();
           Collection<NptBaseModelLink> p2pList = new ArrayList<>();
           if(null != groupList && !groupList.isEmpty()) {
               for (NptBaseModelGroup g : groupList) {
                    Collection<NptBaseModelPool> _pl = this.getBaseModelGrouPools(g,null,false);
                    if(null != _pl){
                        poolList.addAll(_pl);
                    }
               }

               if(!poolList.isEmpty()){
                   for(NptBaseModelPool p:poolList){
                       Collection<NptBaseModelPoolIndex> _mf = this.getBaseModelPoolIndex(p);
                       if(null != _mf){
                           indexFieldList.addAll(_mf);
                       }

                       Collection<NptBaseModelLink> _p2p = this.getBaseModelGroupoolLinkedPools(p, NptDict.IDS_ENABLED);
                       if(null != _p2p){
                           p2pList.addAll(_p2p);
                       }
                   }

                   for(NptBaseModelPoolIndex m:indexFieldList){
                       m.setStatus(status.getCode());
                       baseModelPoolIndexManager.update(m);
                   }

                   for(NptBaseModelLink p2p:p2pList){
                       p2p.setStatus(status.getCode());
                       baseModelPool2PoolManager.update(p2p);
                   }

                   for(NptBaseModelPool p:poolList){
                       p.setStatus(status.getCode());
                       baseModelGrouPoolManager.update(p);
                   }
               }
               for (NptBaseModelGroup g : groupList) {
                   g.setStatus(status.getCode());
                   baseModelGroupManager.update(g);
               }
           }

            model.setStatus(status.getCode());
            baseModelManager.update(model);
        }
        return NptDict.RST_SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/12/7 17:40
     * 描述:
     * 向分组中新增一个数据池
     * @param groupId
     * @param dataTypeId
     * @param pkFieldId
     * @param indexFields
     * @param poolType
     * @param pConFieldIds
     * @param myPool
     */
    @Override
    public NptDict addPoolToBaseModelGroup(Long groupId, Long dataTypeId, Long pkFieldId, List<Long> indexFields, NptDict poolType, List<Long> pConFieldIds, NptBaseModelPool myPool) {
        /**
         * 依赖检测
         */
        NptBaseModelGroup group = this.fastFindBaseModelGroupById(groupId);
        if (null == group || !group.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {
            return NptDict.RST_EXCEPTION("指定数据池的所属分组不存在或状态异常!");
        }
        NptBaseModel model = this.fastFindBaseModelById(group.getModelId());
        if(null != model){
            NptBaseModelPool mainPool = this.getBaseModelGroupMainPool(model);
            if(NptDict.CLD_MAIN.equals(poolType) && null != mainPool){
                return NptDict.RST_EXCEPTION("每个模型只能有唯一的核心数池，请勿重复指定。当前的核心数据池为【" + mainPool.getPoolTitle() + "】");
            }
        }else {
            return NptDict.RST_EXCEPTION("当前数据池不属于任何一个模型!");
        }
        NptDict result = checkBaseModelGroupoolDependency(dataTypeId,pkFieldId,indexFields,poolType);
        if(!NptDict.RST_SUCCESS.equals(result)){
            return result;
        }
        /**
         * 新增数据池并保存
         */
        NptBaseModelPool pool = new NptBaseModelPool();
        pool.setMainPool(poolType.getCode());
        pool.setModelId(group.getModelId());
        pool.setGroupId(groupId);
        pool.setPrimaryFieldId(pkFieldId);
        pool.setDataTypeId(dataTypeId);
        pool.setStatus(NptDict.IDS_ENABLED.getCode());
        pool.setAlias(myPool.getAlias());
        pool.setLockLevel(myPool.getLockLevel());
        pool.setTitleFieldId(myPool.getTitleFieldId());
        pool.setOrderFieldId(myPool.getOrderFieldId());
        pool.setScoreWeight(myPool.getScoreWeight());
        pool.setDataHost(myPool.getDataHost());

        baseModelGrouPoolManager.save(pool);

        /**
         * 刷新数据池的索引字段与条件字段
         */
        return refreshPoolIndexAndCondition(pool,indexFields,pConFieldIds);
    }

    /**
     * 作者: owen
     * 时间: 2017/3/15 下午4:23
     * 描述:
     *      刷新数据池的索引字段与条件字段
     */
    private NptDict refreshPoolIndexAndCondition(NptBaseModelPool pool,List<Long> indexFields,List<Long> pConFieldIds){
        /**
         * 清空索引字段
         */
        Collection<NptBaseModelPoolIndex> mfList = this.getBaseModelPoolIndex(pool);
        if(null != mfList){
            for(NptBaseModelPoolIndex mf:mfList){
                baseModelPoolIndexManager.delete(mf);
            }
        }
        /**
         * 清空条件字段
         */
        Collection<NptBaseModelPoolCdt> cdtList = this.getBaseModelPoolConditions(pool);
        if(null != cdtList){
            for(NptBaseModelPoolCdt mf:cdtList){
                baseModelPoolConditionManager.delete(mf);
            }
        }

        NptDict result;

        /**
         * 为数据池添加可能存在的索引字段
         */
        if(null != indexFields && !indexFields.isEmpty()){
            result = addIndexFieldsToGroupPool(pool,indexFields);
            if(!NptDict.RST_SUCCESS.equals(result)){
                return result;
            }
        }
        /**
         * 为数据池添加可能存在的条件字段
         */
        if(null != pConFieldIds && !pConFieldIds.isEmpty()){
            result = addConditionFieldsToGroupPool(pool,pConFieldIds);
            if(!NptDict.RST_SUCCESS.equals(result)){
                return result;
            }
        }
        return NptDict.RST_SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/12/7 18:13
     * 描述:
     * 更新指定的数据池
     * @param poolId
     * @param dataTypeId
     * @param pkFieldId
     * @param indexFields
     * @param poolType
     * @param pConFieldIds
     * @param myPool
     */
    @Override
    public NptDict updatePoolToBaseModelGroup(Long poolId, Long dataTypeId, Long pkFieldId, List<Long> indexFields, NptDict poolType, List<Long> pConFieldIds, NptBaseModelPool myPool) {
        NptBaseModelPool pool = this.findBaseModelGrouPoolById(poolId);
        if(null == pool){
            return NptDict.RST_EXCEPTION("要编辑的数据池不存在!");
        }

        /**
         * 依赖检测
         */
        NptBaseModel model = this.findBaseModelById(pool.getModelId());
        if(null != model){
            NptBaseModelPool mainPool = this.getBaseModelGroupMainPool(model);
            if(NptDict.CLD_MAIN.equals(poolType) && null != mainPool && !mainPool.equals(pool)){
                return NptDict.RST_EXCEPTION("每个模型只能有唯一的核心数池，请勿重复指定。当前的核心数据池为【" + mainPool.getPoolTitle() + "】");
            }
        }else {
            return NptDict.RST_EXCEPTION("当前数据池不属于任何一个模型!");
        }
        NptBaseModelGroup group = this.findBaseModelGroupById(pool.getGroupId());
        if (null == group || !group.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {
            return NptDict.RST_EXCEPTION("指定数据池的所属分组不存在或状态异常!");
        }
        NptDict checkResult = checkBaseModelGroupoolDependency(dataTypeId,pkFieldId,indexFields,poolType);
        if(!NptDict.RST_SUCCESS.equals(checkResult)){
            return checkResult;
        }

        /**
         * 更新数据池基本信息
         */
        pool.setMainPool(poolType.getCode());
        pool.setPrimaryFieldId(pkFieldId);
        pool.setDataTypeId(dataTypeId);
        pool.setAlias(myPool.getAlias());
        pool.setLockLevel(myPool.getLockLevel());
        pool.setTitleFieldId(myPool.getTitleFieldId());
        pool.setOrderFieldId(myPool.getOrderFieldId());
        pool.setScoreWeight(myPool.getScoreWeight());
        pool.setDataHost(myPool.getDataHost());
        baseModelGrouPoolManager.update(pool);

        /**
         * 刷新数据池的索引字段与条件字段
         */
        return refreshPoolIndexAndCondition(pool,indexFields,pConFieldIds);
    }

    /**
     *作者：OWEN
     *时间：2016/12/7 18:31
     *描述:
     *      检测数据依赖项的合法性
     */
    private NptDict checkBaseModelGroupoolDependency(Long dataTypeId, Long pFieldId, List<Long> indexFields, NptDict poolType) {

        if (NptDict.CLD_MAIN.equals(poolType) && (null == indexFields || indexFields.isEmpty())) {
            return NptDict.RST_EXCEPTION("核心数据池必须指定主列表字段！");
        }
        NptLogicDataType dataType = rmsArchService.fastFindDataTypeById(dataTypeId);
        if (null == dataType || !dataType.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {
            return NptDict.RST_EXCEPTION("数据池赖的数据类别不存在或状态异常！ ");
        }
        if (pFieldId.equals(dataType.getUkFieldId())) {
            return NptDict.RST_EXCEPTION("数据主键跟业务主键不得相同");
        }
        NptLogicDataField pkField = rmsArchService.fastFindDataFieldById(pFieldId);
        if(null == pkField || !pkField.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
            return NptDict.RST_EXCEPTION("为数据池指定的业务主键不存在或状态异常!");
        }
        NptLogicDataField ukField = rmsArchService.fastFindDataFieldById(dataType.getUkFieldId());
        if(null == ukField || !ukField.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
            return NptDict.RST_EXCEPTION("为数据池指定的数据类别不存在数据主键!");
        }
        return NptDict.RST_SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/13 21:17
     * 描述:
     * 为数据池添加索引字段
     *
     */
    private NptDict addIndexFieldsToGroupPool(NptBaseModelPool pool, List<Long> indexFields) {
        Collection<NptBaseModelPoolIndex> idxList = new ArrayList<>();

        if(null != indexFields && !indexFields.isEmpty()){
            for(int i = 0;i < indexFields.size();i++){
                NptLogicDataField field = rmsArchService.fastFindDataFieldById(indexFields.get(i));
                if(null != field) {
                    NptBaseModelPoolIndex idxField = new NptBaseModelPoolIndex();
                    idxField.setDisplayOrder(field.getDisplayOrder());
                    idxField.setFieldId(indexFields.get(i));
                    idxField.setPoolId(pool.getId());
                    idxField.setStatus(NptDict.IDS_ENABLED.getCode());
                    idxField.setCreatorId(NptRmsUtil.getCurrentUserId());
                    idxField.setCreateTime(NptCommonUtil.getCurrentSysDate());
                    idxList.add(idxField);
                }
            }
        }
        baseModelPoolIndexManager.saveAll(idxList);
        return NptDict.RST_SUCCESS;
    }

    /**
     * 作者: owen
     * 时间: 2017/3/15 下午4:12
     * 描述:
     *      为数据池添加条件字段
     */
    private NptDict addConditionFieldsToGroupPool(NptBaseModelPool pool,List<Long> cdtFields){
        Collection<NptBaseModelPoolCdt> cdtList = new ArrayList<>();

        if(null != cdtFields && !cdtFields.isEmpty()){
            for(int i = 0;i < cdtFields.size();i++){
                NptLogicDataField field = rmsArchService.fastFindDataFieldById(cdtFields.get(i));
                if(null != field) {

                    NptBaseModelPoolCdt cdtField = new NptBaseModelPoolCdt();
                    cdtField.setDisplayOrder(field.getDisplayOrder());
                    cdtField.setFieldId(cdtFields.get(i));
                    cdtField.setPoolId(pool.getId());
                    cdtField.setConditionType(NptDict.FSS_COMMON_TEXT.getCode());
                    cdtField.setStatus(NptDict.IDS_ENABLED.getCode());
                    cdtField.setCreatorId(NptRmsUtil.getCurrentUserId());
                    cdtField.setCreateTime(NptCommonUtil.getCurrentSysDate());
                    cdtList.add(cdtField);
                }
            }
        }
        baseModelPoolConditionManager.saveAll(cdtList);
        return NptDict.RST_SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/13 20:43
     * 描述:
     * 从指定的分组中删除指定的数据池
     *
     * @param groupId
     * @param poolId
     * @param status
     */
    @Override
    public NptDict setBaseModeGroupoolStatus(Long groupId, Long poolId, NptDict status) {
        NptBaseModelGroup group = this.fastFindBaseModelGroupById(groupId);
        if(null == group){
            return NptDict.RST_EXCEPTION("分组参数异常！");
        }
        NptBaseModelPool pool = this.findBaseModelGrouPoolById(poolId);
        if(null == pool){
            return NptDict.RST_EXCEPTION("数据池参数异常！");
        }
        if(!pool.getGroupId().equals(groupId)){
            return NptDict.RST_EXCEPTION("指定的数据池不属于指定的分组，操作停止！");
        }
        if(NptDict.CLD_MAIN.getCode() ==  pool.getMainPool()
                &&(status == NptDict.IDS_DISABLED
                || status == NptDict.IDS_DELETED
                || status == NptDict.IDS_LOCKED)){
            return NptDict.RST_EXCEPTION("指定的数据池为核心数据池，核心数据池不允许被禁用锁定或删除！");
        }
        if(status.getCode() != pool.getStatus()){
            Collection<NptBaseModelLink> ppList = this.getBaseModelGroupoolLinkedPools(pool,status);
            if(null != ppList && !ppList.isEmpty()){
                for(NptBaseModelLink p2p:ppList){
                    p2p.setStatus(status.getCode());
                    baseModelPool2PoolManager.update(p2p);
                }
            }
            pool.setStatus(status.getCode());
            baseModelGrouPoolManager.update(pool);
        }
        return NptDict.RST_SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/13 20:43
     * 描述:
     * 为指定的分组设置主数据池
     *
     * @param poolId
     */
    @Override
    public NptDict setBaseModelGroupMainPool(Long poolId) {
        NptBaseModelPool pool = this.findBaseModelGrouPoolById(poolId);
        if(null == pool){
            return NptDict.RST_EXCEPTION("数据池参数异常！");
        }else if(!pool.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
            return NptDict.RST_EXCEPTION("数据池当前状态异常，不可设置为核心数据池！");
        }
        NptBaseModelGroup group = baseModelGroupManager.findById(pool.getGroupId());
        if(null == group){
            return NptDict.RST_EXCEPTION("数据池不属于任何一个分组，操作停止！");
        }
        NptBaseModelPool mpool = this.getBaseModelMainGrouPool(group);
        if(null != mpool){
            return NptDict.RST_EXCEPTION("当前分组已存在核心数据池，本次操作不被允许！");
        }
        Collection<NptBaseModelPoolIndex> mainFields = this.getBaseModelPoolIndex(pool);
        if(null == mainFields || mainFields.isEmpty()){
            return NptDict.RST_EXCEPTION("设置核心数据池必须同时指定数据池的主列表字段！");
        }
        pool.setMainPool(NptDict.CLD_MAIN.getCode());
        baseModelGrouPoolManager.update(pool);

        return NptDict.RST_SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/16 16:00
     * 描述:
     * 添加模型关联信息
     * @param from
     * @param to
     * @param fk
     * @param toKeyId
     * @param linkTitle
     */
    @Override
    public NptDict addBaseModelGroupoolLink(NptBaseModelPool from, NptBaseModelPool to, NptLogicDataField fk, Long toKeyId, String linkTitle) {
        if(null != from && null != to && null != fk){
            NptBaseModelLink p2p = baseModelPool2PoolManager.findUniqueByCondition(
                    Conditions.eq(NptBaseModelLink.PROPERTY_FROM_POOL_ID,from.getId()),
                    Conditions.eq(NptBaseModelLink.PROPERTY_TO_POOL_ID,to.getId()),
                    Conditions.eq(NptBaseModelLink.PROPERTY_FROM_POOL_REFKEYID,fk.getId()),
                    Conditions.not(Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_DELETED.getCode())));
            if(null != p2p){
                if(NptDict.IDS_ENABLED.getCode() == p2p.getStatus()){
                    return NptDict.RST_EXCEPTION("模型关联已存在，勿重复操作！");
                }else {
                    p2p.setStatus(NptDict.IDS_ENABLED.getCode());
                    baseModelPool2PoolManager.update(p2p);
                    return NptDict.RST_SUCCESS;
                }
            }else {
                if(from.getId().equals(to.getId())){
                    return NptDict.RST_EXCEPTION("模型关联不可与自身关联！");
                }
                p2p = new NptBaseModelLink();
                p2p.setStatus(NptDict.IDS_ENABLED.getCode());
                p2p.setFromPoolId(from.getId());
                p2p.setToPoolId(to.getId());
                p2p.setPoolRefKeyId(fk.getId());
                p2p.setToKeyId(toKeyId);
                p2p.setLinkTitle(linkTitle);
                p2p.setRelLink(NptDict.MLR_NORMAL.getCode());
                p2p.setCreateTime(NptCommonUtil.getCurrentSysDate());
                p2p.setCreatorId(NptRmsUtil.getCurrentUserId());
                p2p.setParentId(NptCommonUtil.getDefaultParentId());
                baseModelPool2PoolManager.save(p2p);
                return NptDict.RST_SUCCESS;
            }
        }
        return NptDict.RST_EXCEPTION("模型关联时指定的参数异常！");
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/28 14:44
     * 描述:
     * 加载基础模型的结构信息
     *
     * @param modelId
     */
    @Override
    public NptBaseModelStructure loadBaseModelStructure(Long modelId){
        NptBaseModel model = this.fastFindBaseModelById(modelId);
        if(null == model){
            return null;
        }
        /**
         * 设置模型信息
         */
        NptBaseModelStructure modelStructure = new NptBaseModelStructure();
        modelStructure.setModel(model);

        Collection<NptBaseModelGroup> groupList = this.getBaseModelGroups(model);
        if(null != groupList){
            /**
             * 创建结构所需的承载实体
             */
            Map<Long,Collection<NptBaseModelPool>> grouPoolMap = new HashMap<>();
            Map<Long,NptLogicDataType> poolDataTypeMap = new HashMap<>();
            Map<Long,Collection<NptLogicDataField>> typeFieldMap = new HashMap<>();
            Map<Long,Collection<NptBaseModelPoolIndex>> poolIndexMap = new HashMap<>();
            Map<Long,Collection<NptBaseModelPoolCdt>> poolCdtMap = new HashMap<>();
            /**
             * 循环查找每一个分组的数据池信息
             */
            for(NptBaseModelGroup group:groupList){
                Collection<NptBaseModelPool> grouPooList = this.getBaseModelGrouPools(group,NptDict.IDS_ENABLED,false);
                if(null != grouPooList) {
                    /**
                     * 建立分组ID跟数据池的映射关系
                     */
                    grouPoolMap.put(group.getId(), grouPooList);
                    /**
                     * 循环遍历分组内的每一个数据池
                     */
                    for(NptBaseModelPool pool:grouPooList){
                        /**
                         * 获取数据池的数据类别及类别的字段
                         */
                        NptLogicDataType dataType = rmsArchService.fastFindDataTypeById(pool.getDataTypeId());
                        if (null != dataType && NptDict.IDS_ENABLED.getCode() == dataType.getStatus()) {
                            poolDataTypeMap.put(pool.getId(), dataType);
                            Collection<NptLogicDataField> fieldList = rmsArchService.listDataField(dataType.getId(),null,null);
                            if (null != fieldList) {
                                typeFieldMap.put(dataType.getId(), fieldList);
                            }
                        }
                        /**
                         * 获取数据池的索引字段
                         */
                        Collection<NptBaseModelPoolIndex> poolIndex = getBaseModelPoolIndex(pool);
                        poolIndexMap.put(pool.getId(),poolIndex);
                        /**
                         * 获取数据池的条件字段
                         */
                        Collection<NptBaseModelPoolCdt> poolCdts = getBaseModelPoolConditions(pool);
                        poolCdtMap.put(pool.getId(),poolCdts);
                    }
                }
            }
            modelStructure.setPoolIndexMap(poolIndexMap);
            modelStructure.setPoolCdtMap(poolCdtMap);
            modelStructure.setModelGroups(groupList);
            modelStructure.setGrouPoolMap(grouPoolMap);
            modelStructure.setPoolDataType(poolDataTypeMap);
            modelStructure.setTypeFieldMap(typeFieldMap);
        }
        return modelStructure;
    }

    /**
     *  author: zhanglei
     *  date:   2017/06/02 10:30
     *  note:
     *
     */
    @Override
    public NptBaseModelStructure loadBaseModelStructure(NptBaseModelPool pool) {
        NptBaseModelStructure modelStructure = new NptBaseModelStructure();
        /**
         * 设置模型信息
         */
        modelStructure.setModel(this.fastFindBaseModelById(pool.getModelId()));

        NptBaseModelGroup group = this.findBaseModelGroupById(pool.getGroupId());
        if(null != group){
            /**
             * 创建结构所需的承载实体
             */
            Map<Long,Collection<NptBaseModelPool>> grouPoolMap = new HashMap<>();
            Map<Long,NptLogicDataType> poolDataTypeMap = new HashMap<>();
            Map<Long,Collection<NptLogicDataField>> typeFieldMap = new HashMap<>();
            Map<Long,Collection<NptBaseModelPoolIndex>> poolIndexMap = new HashMap<>();
            Map<Long,Collection<NptBaseModelPoolCdt>> poolCdtMap = new HashMap<>();
            /**
             * 循环查找每一个分组的数据池信息
             */
//            for(NptBaseModelGroup group:groupList){
                Collection<NptBaseModelPool> grouPooList = new ArrayList<NptBaseModelPool>(){{add(pool);}};
                if(null != grouPooList) {
                    /**
                     * 建立分组ID跟数据池的映射关系
                     */
                    grouPoolMap.put(group.getId(), grouPooList);
                    /**
                     * 循环遍历分组内的每一个数据池
                     */
//                    for(NptBaseModelPool pool:grouPooList){
                        /**
                         * 获取数据池的数据类别及类别的字段
                         */
                        NptLogicDataType dataType = rmsArchService.fastFindDataTypeById(pool.getDataTypeId());
                        if (null != dataType && NptDict.IDS_ENABLED.getCode() == dataType.getStatus()) {
                            poolDataTypeMap.put(pool.getId(), dataType);
                            Collection<NptLogicDataField> fieldList = rmsArchService.listDataField(dataType.getId(),null,null);
                            if (null != fieldList) {
                                typeFieldMap.put(dataType.getId(), fieldList);
                            }
                        }
                        /**
                         * 获取数据池的索引字段
                         */
                        Collection<NptBaseModelPoolIndex> poolIndex = getBaseModelPoolIndex(pool);
                        poolIndexMap.put(pool.getId(),poolIndex);
                        /**
                         * 获取数据池的条件字段
                         */
                        Collection<NptBaseModelPoolCdt> poolCdts = getBaseModelPoolConditions(pool);
                        poolCdtMap.put(pool.getId(),poolCdts);
//                    }
//                }
            }
            modelStructure.setPoolIndexMap(poolIndexMap);
            modelStructure.setPoolCdtMap(poolCdtMap);
            modelStructure.setModelGroups(new ArrayList<NptBaseModelGroup>(){{add(group);}});
            modelStructure.setGrouPoolMap(grouPoolMap);
            modelStructure.setPoolDataType(poolDataTypeMap);
            modelStructure.setTypeFieldMap(typeFieldMap);
        }
        return modelStructure;
    }

    @Override
    public NptBaseModelTree loadBaseModelTree(Long modelId,NptWebBridgeBean bean) {
        NptBaseModelTree modelTree = new NptBaseModelTree();
        NptBaseModel model = fastFindBaseModelById(modelId);
        if(null != model){
            modelTree.setModel(model);
            Collection<NptBaseModelGroupTree> groupTrees = new ArrayList();

            Collection<NptBaseModelGroup> groups = getBaseModelGroups(model);
            if(null != groups && !groups.isEmpty()){
                for(NptBaseModelGroup group:groups){
                    NptBaseModelGroupTree groupTree = new NptBaseModelGroupTree();

                    groupTree.setGroup(group);

                    Collection<NptBaseModelPool> pools = getBaseModelGrouPools(group,NptDict.IDS_ENABLED,false);
                    groupTree.setPools(pools);

                    groupTrees.add(groupTree);
                }
            }

            modelTree.setGroupTrees(groupTrees);
        }

        if(null != bean){
            bean.setDataList(modelTree);
        }
        return modelTree;
    }

    /**
     *作者：owen
     *时间：2016/12/19 18:09
     *描述:
     *      检测数据池是否可以同步到外部系统
     */
    public NptDict statusFilter(NptBaseModelStructure structure){

        if(null != structure){
            Collection<NptBaseModelGroup> groups = structure.getModelGroups();
            if(null == groups || groups.isEmpty()){
                return NptDict.RST_EXCEPTION("模型不包含任何分组，停止同步");
            }
            Map<Long,Collection<NptBaseModelPool>> gpMap = structure.getGrouPoolMap();
            if(null == gpMap || gpMap.isEmpty()){
                return NptDict.RST_EXCEPTION("模型不包含任何数据池，停止同步");
            }
            Collection<NptBaseModelGroup> okGroups = new ArrayList<>();
            Map<Long,Collection<NptBaseModelPool>> okGPMap = new HashMap<>();
            for(NptBaseModelGroup group:groups){
                if(group.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
                    okGroups.add(group);

                    Collection<NptBaseModelPool> gPools = gpMap.get(group.getId());
                    if(null != gPools || !gPools.isEmpty()){
                        for(NptBaseModelPool p:gPools){
                            if(isPoolCanSync(p)){
                                Collection<NptBaseModelPool> okPools = okGPMap.get(group.getId());
                                if(null == okPools){
                                    okPools = new ArrayList<>();
                                    okGPMap.put(group.getId(),okPools);
                                }
                                okPools.add(p);
                            }
                        }
                    }
                }
            }
            structure.setModelGroups(okGroups);
            structure.setGrouPoolMap(okGPMap);

            return NptDict.RST_SUCCESS;
        }else {
            return NptDict.RST_EXCEPTION("模型结构异常，同步停止");
        }
    }

    private Boolean isPoolCanSync(NptBaseModelPool p){
        if(null == p || !p.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
            return false;
        }
        NptLogicDataType type = rmsArchService.fastFindDataTypeById(p.getDataTypeId());
        if(null == type || !type.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
            return false;
        }
        if(null == type.getUkFieldId() || NptCommonUtil.getDefaultParentId().equals(type.getUkFieldId())){
            return false;
        }
        NptLogicDataField uField = rmsArchService.fastFindDataFieldById(type.getUkFieldId());
        if(null == uField || !uField.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
            return false;
        }
        NptLogicDataField pField = rmsArchService.fastFindDataFieldById(p.getPrimaryFieldId());
        if(null == pField || !pField.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
            return false;
        }
        Collection<NptLogicDataField> openFields = rmsArchService.listDataField(type.getId(), NptDict.FAL_SOPEN, NptDict.IDS_ENABLED);
        if(null == openFields || openFields.isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * 作者：owen
     * 时间：2016/12/13 20:32
     * 描述:
     * 加载基础模型数据池的增量数据
     *
     */
    @Override
    public NptBaseModelPoolRow loadBaseModelGrouPoolIncrementData(NptBaseModelPool pool, Timestamp max, Integer start, Integer end, Boolean transform) {
        NptBaseModelPoolRow poolData = new NptBaseModelPoolRow();
        if(null != pool) {
            poolData.setPool(pool);
            NptLogicDataType type = rmsArchService.fastFindDataTypeById(pool.getDataTypeId());
            if (null != type) {
                Collection<NptLogicDataField> syncFields = new ArrayList<>();
                NptLogicDataField uField = rmsArchService.fastFindDataFieldById(type.getUkFieldId());
                NptLogicDataField pField = rmsArchService.fastFindDataFieldById(pool.getPrimaryFieldId());
                if (null == uField || null == pField) {
                    return null;
                }
                Collection<NptLogicDataField> busiFields = rmsArchService.listDataField(type.getId(), NptDict.FAL_SOPEN, NptDict.IDS_ENABLED);
                if (null != busiFields && !busiFields.isEmpty()) {
                    syncFields.addAll(busiFields);
                    if (!syncFields.contains(pField)) {
                        syncFields.add(pField);
                    }
                    if (!syncFields.contains(uField)) {
                        syncFields.add(uField);
                    }
                    poolData.setFields(syncFields);
                    Timestamp last = null;
                    NptBaseModelPoolStamp timestamp = baseModelDataTimestampManager.getUniqueTimestamp(pool.getDataTypeId(), NptDict.DUB_OUT_SYNC);
                    if (null != timestamp) {
                        last = timestamp.getLastTime();
                    }
                    List result = databaseManager.getIncrementData(type.getTypeDbName(), syncFields, last, max, start, end,transform);
                    if (null != result && !result.isEmpty()) {
                        poolData.setRowCount(result.size());
                        poolData.setPoolData(result);
                    } else {
                        poolData.setRowCount(NptCommonUtil.IntegerZero());
                    }
                }else {
                    poolData.setRowCount(NptCommonUtil.IntegerZero());
                }
            }
        }
        return poolData;
    }

    /**
     * 作者：owen
     * 时间：2016/12/14 18:04
     * 描述:
     * 更新指定模型的所有数据池实体数据的最后"操作时间"
     *
     * @param useBy
     */
    @Override
    public void updateBaseModelPoolDataTimestamp(Long poolId, NptDict useBy, Timestamp current) {
        NptBaseModelPool pool = this.fastFindBaseModelGrouPoolById(poolId);
        if(null != pool) {
            baseModelDataTimestampManager.updateTimestamp(pool.getDataTypeId(), useBy, current);
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/29 16:20
     * 描述:
     * 更新模型基础信息
     *
     * @param model
     */
    @Override
    public NptBaseModel update(NptBaseModel model) {
        return baseModelManager.update(model);
    }


    @Override
    public NptBaseModelGroup update(NptBaseModelGroup group) {
        return baseModelGroupManager.update(group);
    }

    @Override
    public NptBaseModelPool update(NptBaseModelPool pool) {
        return baseModelGrouPoolManager.update(pool);
    }

    @Override
    public NptDict checkBaseModel(String modelName) {
        NptBaseModel model = baseModelManager.findUniqueByCondition(
                Conditions.eq(NptBaseModel.PROPERTY_MODEL_NAME,modelName)
        );
        if (null != model){
            return  NptDict.RST_DUPLICATE_OPERATION;
        }
        return NptDict.RST_SUCCESS;
    }

    @Override
    public NptDict checkBaseModelGroup(Long modelId, String groupName) {
        NptBaseModelGroup group = baseModelGroupManager.findUniqueByCondition(
                Conditions.eq(NptBaseModelGroup.PROPERTY_MODEL_ID,modelId),
                Conditions.eq(NptBaseModelGroup.PROPERTY_GROUP_NAME,groupName)
        );
        if (null != group){
            return  NptDict.RST_DUPLICATE_OPERATION;
        }
        return NptDict.RST_SUCCESS;
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
            baseModelManager.save(model);
            return NptDict.RST_SUCCESS;
        }catch (Exception e){
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
            baseModelGroupManager.save(group);
            return NptDict.RST_SUCCESS;
        }catch (Exception e){
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
            baseModelGrouPoolManager.save(pool);
            return NptDict.RST_SUCCESS;
        }catch (Exception e){
            return NptDict.RST_EXCEPTION;
        }
    }

    /**
     *作者：owen
     *时间：2016/12/16 18:00
     *描述:
     *      不做任何处理直接保存
     */
    public NptDict directSave(NptBaseModelPoolIndex mf){
        try {
            baseModelPoolIndexManager.save(mf);
            return NptDict.RST_SUCCESS;
        }catch (Exception e){
            return NptDict.RST_EXCEPTION;
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/12/6 20:53
     * 描述:
     *
     */
    @Override
    public NptDict deleteBaseModelById(Long modelId) {
        try {
            NptBaseModel model = baseModelManager.findById(modelId);
            if(null != model) {
                Collection<NptBaseModelGroup> groups = this.getBaseModelGroups(model);
                if (null != groups && !groups.isEmpty()) {
                    for (NptBaseModelGroup g : groups) {
                        this.deleteBaseModelGroupById(g.getId());
                    }
                }
                baseModelManager.delete(model);
            }
            return NptDict.RST_SUCCESS;
        }catch (Exception e){
            return NptDict.RST_EXCEPTION(e.getMessage());
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/12/6 20:53
     * 描述:
     *
     */
    @Override
    public NptDict deleteBaseModelGroupById(Long groupId) {
        try {
            NptBaseModelGroup group = baseModelGroupManager.findById(groupId);
            if(null != group) {
                Collection<NptBaseModelPool> pools = this.getBaseModelGrouPools(group,null,false);
                if (null != pools && !pools.isEmpty()) {
                    for (NptBaseModelPool p : pools) {
                        this.deleteBaseModelGrouPoolById(p.getId());
                    }
                }
                baseModelGroupManager.delete(group);
            }
            return NptDict.RST_SUCCESS;
        }catch (Exception e){
            return NptDict.RST_EXCEPTION(e.getMessage());
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/12/6 20:53
     * 描述:
     *
     */
    @Override
    public NptDict deleteBaseModelGrouPoolById(Long poolId) {
        try {
            NptBaseModelPool pool = baseModelGrouPoolManager.findById(poolId);
            if(null != pool) {
                Collection<NptBaseModelLink> p2pList = this.getBaseModelGroupoolLinkedPools(pool, null);
                Collection<NptBaseModelLink> meP2pList = this.getBaseModelGroupoolLinkedMePools(pool, null);

                if (null != p2pList && !p2pList.isEmpty()) {
                    if (null != meP2pList) {
                        p2pList.addAll(meP2pList);
                    }

                    for (NptBaseModelLink p2p : p2pList) {
                        this.delete(p2p);
                    }
                }

                Collection<NptBaseModelPoolIndex> idxFields = this.getBaseModelPoolIndex(pool);
                if(null != idxFields){
                    for(NptBaseModelPoolIndex mf:idxFields){
                        baseModelPoolIndexManager.delete(mf);
                    }
                }

                Collection<NptBaseModelPoolCdt> cdtFields = this.getBaseModelPoolConditions(pool);
                if(null != cdtFields){
                    for(NptBaseModelPoolCdt mf:cdtFields){
                        baseModelPoolConditionManager.delete(mf);
                    }
                }

                baseModelGrouPoolManager.delete(pool);
            }
            return NptDict.RST_SUCCESS;
        }catch (Exception e){
            return NptDict.RST_EXCEPTION(e.getMessage());
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/12/6 20:55
     * 描述:
     *
     * @param p2p
     */
    @Override
    public NptDict delete(NptBaseModelLink p2p) {
        try{
            baseModelPool2PoolManager.delete(p2p);
            return NptDict.RST_SUCCESS;
        }catch (Exception e){
            return NptDict.RST_EXCEPTION(e.getMessage());
        }
    }

    /**
     * 作者：owen
     * 时间：2016/12/14 20:09
     * 描述:
     * 加载指定数据池的分页列表数据
     *
     * @param poolId
     * @param bean
     * @param onlyIndex 只查询索引字段
     * @param applyed 是否已通过申请
     * @param exact 是否精确查询
     */
    @Override
    public NptDict getBaseModelGroupoolPaginationData(Long poolId, NptWebBridgeBean bean,Boolean onlyIndex,Boolean applyed,Boolean exact) {
        NptBaseModelPool pool = this.fastFindBaseModelGrouPoolById(poolId);
        if(null == pool || null == bean){
            return NptDict.RST_EXCEPTION("指定的数据池不存在:数据池ID[" + poolId + "]");
        }
        //获取数据池的物理表信息
        NptLogicDataType dataType = rmsArchService.fastFindDataTypeById(pool.getDataTypeId());
        if(null == dataType || !dataType.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
            return NptDict.RST_EXCEPTION("数据池对应的数据表不存在或已被禁用:数据池ID[" + poolId + "]");
        }

        Collection<NptLogicDataField> showFields;
        if(onlyIndex){
            //获取数据的索引字段
            showFields = getBaseModelIndexDataFields(pool);
        }else {
            showFields = rmsArchService.listDataField(dataType.getId(), NptDict.FAL_SOPEN, NptDict.IDS_ENABLED);
        }

        if(null == showFields || showFields.isEmpty()){
            return NptDict.RST_EXCEPTION("数据池对应的数据表不包含任何可显示的字段:数据池ID[" + poolId + "]");
        }

        makeDefaultOrderCondition(pool,bean);
        //加载分页数据
        return getPaginationData(dataType,showFields,bean,applyed,exact);
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
            if (bean.getOrderCondition().isEmpty()) {
                Long orderFieldId = pool.getOrderFieldId();
                if (null != orderFieldId) {
                    NptLogicDataField orderField = rmsArchService.fastFindDataFieldById(Math.abs(orderFieldId));
                    if (null != orderField && orderField.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {
                        String orderName = "ASC";
                        if(orderFieldId < 0L){
                            orderName = "DESC";
                        }

                        bean.getOrderCondition().put(orderField.getFieldDbName(),orderName);
                    }
                }
                bean.getOrderCondition().put("ROWNUM", "ASC");
            }
        }
    }

    /**
     *作者：owen
     *时间：2017/1/3 14:00
     *描述:
     *      获取分页数据
     */
    @Override
    public NptDict getPaginationData(NptLogicDataType type, Collection<NptLogicDataField> fields, NptWebBridgeBean bean, Boolean applyed,Boolean exact){
        //构建查询语句
        if(null != fields && fields.size() > 0 && null != type) {
            String[] sql = databaseManager.makePaginationSql(
                    type.getTypeDbName(),
                    fields,
                    bean.getCurrPage(),
                    bean.getPageSize(),
                    bean.getQueryCondition(),
                    bean.getOrderCondition(),
                    exact
            );

            int totalCount = databaseManager.getCount(sql[0]);
            if(totalCount > 0){
                Collection<NptWebFieldDataArray> formatData = queryAndFormat(sql[1],fields,null,applyed);
                if(null != formatData && !formatData.isEmpty()) {
                    bean.setColumnTitles(formatData.iterator().next().getTitleList());
                    bean.setDataList(formatData);
                }
            } else {
                // 设置title，用于前台确定colspan的值
                bean.setColumnTitles(fields);
            }
            bean.setTotalCount(totalCount);
            return NptDict.RST_SUCCESS;
        }
        return NptDict.RST_EXCEPTION("要查询的数据类别为空为字段列表为空");
    }

    @Override
    public List<Object> getPoolPKFieldDataListByUKFieldTailNO(NptBaseModelPool pool, String where) {

        if(null != pool && null != where){
            NptLogicDataField pkField = this.getBaseModelGrouPoolPrimaryField(pool);
            Collection<NptLogicDataField> fieldList = new ArrayList<>();
            if(null != pkField){
                fieldList.add(pkField);
            }
            NptLogicDataType dataType = this.getBaseModelGrouPoolDataType(pool);
            if(null != dataType){
                String[] sql = databaseManager.makeDistinctSql(dataType.getTypeDbName(),pkField,where);

                if(databaseManager.getCount(sql[0]) < NptCommonUtil.dbMaxCountOnce){
                    return databaseManager.queryList(sql[1],null);
                }
            }
        }
        return null;
    }


    @Override
    public List<String> getPoolPKFieldDistinctValues(NptBaseModelPool pool) {

        List<String> result = new ArrayList<>();
        if(null != pool){
           NptLogicDataField pkField = this.getBaseModelGrouPoolPrimaryField(pool);
           NptLogicDataType dataType = this.getBaseModelGrouPoolDataType(pool);

           if(null != pkField && null != dataType){
               String[] sql = databaseManager.makeDistinctSql(dataType.getTypeDbName(),pkField,null);

               if(databaseManager.getCount(sql[0]) > NptCommonUtil.IntegerZero()){
                   List<Object> searchResult = databaseManager.queryList(sql[1],null);
                   if(null != searchResult && !searchResult.isEmpty()){
                       searchResult.forEach(o ->{
                           Map<String,Object> fv = (Map<String, Object>) o;
                           result.add(String.valueOf(fv.values().iterator().next()));
                       });
                   }
               }
           }
        }

        return result;
    }



    /**
     * 作者：owen
     * 日期：2016/10/24 15:08
     * 备注：
     *      查询并格式化数据
     * 参数：
     * 返回：
     */
    private Collection<NptWebFieldDataArray> queryAndFormat(String sql, Collection<NptLogicDataField> fields, NptBaseModelPool pool, boolean applyed){
        try {
            List<Object> queryData = databaseManager.queryList(sql,fields);
            if(null != queryData && !queryData.isEmpty()) {
                return formatTitleData(queryData, fields,pool,applyed);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 作者：owen
     * 日期：2016/10/23 16:10
     * 备注：
     *      此方法用于决定某一字段在页面上的显示方式:
     *      1:若字段无值,则以"-"代替
     *      2:若字段有值,但属于敏感字段,按其showStyle方式处理
     *      3：若字段有值，但属于需要申请之后才能查看的字段，则以"*"代替
     *
     *      最终，字段的值都被转换成字符串在页面上显示
     * 参数：
     * 返回：
     */
    private List<NptWebFieldDataArray> formatTitleData(List<Object> data, Collection<NptLogicDataField> fields, NptBaseModelPool pool, boolean applyed) {

        NptLogicDataField[] fieldArray = new NptLogicDataField[fields.size()];
        fields.toArray(fieldArray);

        /**
         * 汇总当前数据池的每个字段已存在的模型间业务外键
         */
        List<NptWebFieldDataArray> dataArrayList = new ArrayList<>();
        Collection<Long> p2pFieldIdList = new ArrayList<>();

        /**
         * 若数据池不为空，则表示要检测其字段跳接，此时默认不显示数据主键
         */
        NptLogicDataField ukField = null;
        if (null != pool) {
            Collection<NptBaseModelLink> p2pList = this.getBaseModelGroupoolLinkedPools(pool, NptDict.IDS_ENABLED);
            if (null != p2pList && !p2pList.isEmpty()) {
                for (NptBaseModelLink p2p : p2pList) {
                    p2pFieldIdList.add(p2p.getPoolRefKeyId());
                }
            }

            NptLogicDataType poolType = rmsArchService.fastFindDataTypeById(pool.getDataTypeId());
            if(null != poolType){
                ukField = rmsArchService.fastFindDataFieldById(poolType.getUkFieldId());
            }

        }


        /**
         * 数据行级循环
         */
        for (Object obj : data) {
            NptWebFieldDataArray dataArray = new NptWebFieldDataArray();
            Map<String, Object> temp = (Map<String, Object>) obj;
            Set<String> keys = temp.keySet();
            int index = NptCommonUtil.IntegerZero();

            /**
             * 数据行中的字段循环
             */
            for (String title : keys) {
                Object value = temp.get(title);

                /**
                 * 特殊处理数据主键,默认在详情页面不显示
                 */
                if(null != ukField && ukField.equals(fieldArray[index])){
                    dataArray.setuFieldValue(String.valueOf(value));
                }else {

                    /**
                     * 检测当前行数据是否已被锁定
                     */
                    Collection<NptWebFieldDataArray> stopResult = showStyleController.stopLookingup(title, value);
                    if (null != stopResult) {
                        dataArrayList.addAll(stopResult);
                        break;
                    }
                    NptWebFieldDataArray.NptWebFieldData fieldData = dataArray.instanceFieldData();
                    fieldData.setTitle(title);
                    if (p2pFieldIdList.contains(fieldArray[index].getId())) {
                        fieldData.setLinked(NptCommonUtil.IntegerOne());
                    } else {
                        fieldData.setLinked(NptCommonUtil.IntegerZero());
                    }

                    fieldData.setFieldId(fieldArray[index].getId());
                    showStyleController.makeValueShowStyle(value, applyed, fieldArray[index], fieldData);

                    dataArray.getDataArray().add(fieldData);
                }
                index++;
            }

            dataArrayList.add(dataArray);
        }
        return dataArrayList;
    }

    /**
     * 作者：xuqinyuan
     * 时间：2017/1/18 15:42
     * 备注：分组排序
     */
    @Override
    public void updateGroupDisplayOrder(List<Long> groupIds) {
        if(null != groupIds && !groupIds.isEmpty()){
            Collection<NptBaseModelGroup> groups = new ArrayList<>();
            for(int i = 0; i < groupIds.size();i++){
                NptBaseModelGroup group = this.findBaseModelGroupById(groupIds.get(i));
                if(null != group){
                    group.setDisplayOrder(i);
                    groups.add(group);
                }
            }
            batchUpdateGroup(groups);
        }
    }

    /**
     * 作者：xuqinyuan
     * 时间：2017/1/18 15:42
     * 备注：数据池排序
     */
    @Override
    public void updateGrouPoolDisplayOrder(List<Long> poolIds) {
        if(null != poolIds && !poolIds.isEmpty()){
            Collection<NptBaseModelPool> pools = new ArrayList<>();
            for(int i = 0; i < poolIds.size();i++){
                NptBaseModelPool pool = this.fastFindBaseModelGrouPoolById(poolIds.get(i));
                if(null != pool){
                    pool.setDisplayOrder(i);
                    pools.add(pool);
                }
            }
            batchUpdateGrouPool(pools);
        }
    }

    /**
     * 作者：xuqinyuan
     * 时间：2017/1/18 15:52
     * 备注：批量更新分组
     */
    @Override
    public void batchUpdateGroup(Collection<NptBaseModelGroup> list) {
        if (null != list && !list.isEmpty()){
            for(NptBaseModelGroup group:list){
                update(group);
            }
        }
    }

    /**
     * 作者：xuqinyuan
     * 时间：2017/1/18 15:53
     * 备注：批量更新数据池
     */
    @Override
    public void batchUpdateGrouPool(Collection<NptBaseModelPool> list) {
        if (null != list && !list.isEmpty()){
            for(NptBaseModelPool pool:list){
                update(pool);
            }
        }
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/14 下午10:33
     * 备注: 获取查询条件
     */
    @Override
    public Collection<NptBaseModelPoolCdt> getBaseModelPoolConditions(NptBaseModelPool pool){
        Collection<NptBaseModelPoolCdt> result = new ArrayList<>();
        Collection<NptBaseModelPoolCdt> searchResult = baseModelPoolConditionManager.findByCondition(
                Conditions.eq(NptBaseModelPoolCdt.PROPERTY_POOL_ID,pool.getId()),
                Conditions.eq(NptBaseEntity.PROPERTY_STATUS,NptDict.IDS_ENABLED.getCode()),
                Conditions.asc(NptBaseEntity.PROPERTY_DISPLAY_ORDER));

        if(null != searchResult && !searchResult.isEmpty()){
            for(NptBaseModelPoolCdt c:searchResult){
                NptLogicDataField field = rmsArchService.fastFindDataFieldById(c.getFieldId());
                if(null != field && field.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {
                    c.setFieldDBName(field.getFieldDbName());
                    c.setFieldTitle(field.getAlias());
                    result.add(c);
                }
            }
        }

        return result;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/12 上午10:36
     * 备注: 删除poolLink
     */
    @Override
    public NptBaseModelLink deleteBaseModelGroupoolLink(Long linkId) {
        return baseModelPool2PoolManager.deleteById(linkId);
    }

    /**
     * author: owen
     * date:   2017/4/26 14:45
     * note:
     * 通过创建索引的方式优化模型的查询效率
     *
     * @param modelId
     */
    @Override
    public void optimizeQuery(Long modelId) {

        NptBaseModel model = findBaseModelById(modelId);
        if(null != model && model.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
            Map<String,List<String>> typeFieldMap = new HashMap<>();

            Collection<NptBaseModelPool> modelPools = getBaseModelGrouPools(model,NptDict.IDS_ENABLED,false);
            if(null != modelPools && !modelPools.isEmpty()){
                for(NptBaseModelPool pool:modelPools){
                    NptLogicDataType dataType = rmsArchService.fastFindDataTypeById(pool.getDataTypeId());
                    if(null != dataType){
                        List<String> fieldDBNames = typeFieldMap.get(dataType.getTypeDbName());
                        if(null == fieldDBNames){
                            fieldDBNames = new ArrayList<>();
                            typeFieldMap.put(dataType.getTypeDbName(),fieldDBNames);
                        }

                        NptLogicDataField ukField = rmsArchService.fastFindDataFieldById(dataType.getUkFieldId());
                        if(null != ukField && !fieldDBNames.contains(ukField.getFieldDbName())){
                            fieldDBNames.add(ukField.getFieldDbName());
                        }

                        NptLogicDataField pkField = rmsArchService.fastFindDataFieldById(pool.getPrimaryFieldId());
                        if(null != pkField && !fieldDBNames.contains(pkField.getFieldDbName())){
                            fieldDBNames.add(pkField.getFieldDbName());
                        }

                        Collection<NptBaseModelLink> poolLinks = getBaseModelGroupoolLinkedPools(pool,null);
                        if(null != poolLinks && !poolLinks.isEmpty()){
                            for(NptBaseModelLink link:poolLinks){
                                NptLogicDataField refField = rmsArchService.fastFindDataFieldById(link.getPoolRefKeyId());
                                if(null != refField && !fieldDBNames.contains(refField.getFieldDbName())){
                                    fieldDBNames.add(refField.getFieldDbName());
                                }
                            }
                        }
                    }
                }
            }

            databaseManager.createRefreshTableIndex(typeFieldMap);
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
        NptLogicDataType mainType = rmsArchService.fastFindDataTypeById(mainPool.getDataTypeId());
        if(null == mainType || NptCommonUtil.getDefaultParentId().equals(mainType.getUkFieldId())){
            return null;
        }
        NptLogicDataField uField = rmsArchService.fastFindDataFieldById(mainType.getUkFieldId());
        if(null == uField){
            return null;
        }
        NptLogicDataField pField = rmsArchService.findDataFieldById(mainPool.getPrimaryFieldId());
        if(null == pField){
            return null;
        }
        NptLogicDataField titleField = rmsArchService.findDataFieldById(mainPool.getTitleFieldId());
        if(null == titleField){
            return null;
        }
        //获取物理表信息及依据数据主键查询出业务主键的值，以便进行数据池之间的业务主键关系查询
        NptLogicDataType pType = rmsArchService.fastFindDataTypeById(mainPool.getDataTypeId());

        Collection<NptLogicDataField> searchField = new ArrayList<>();
        searchField.add(pField);
        searchField.add(titleField);

        Map<String,String> uFieldWhere = new HashMap<>();
        uFieldWhere.put(uField.getFieldDbName(),ukValue);

        //加载业务主键实体值
        if(null != pType){
            String sql = databaseManager.makeUniqueSql(pType.getTypeDbName(),searchField,uFieldWhere, NptDict.CST_ENG_AS_CHN);
            return  (Map<String, Object>) databaseManager.queryUnique(sql);
        }
        return null;
    }

    @Override
    public Map<String, Object> getModelMainPoolTypicalValueByPKValue(NptBaseModel model, String pkValue) {
        if(null == model || null == pkValue || pkValue.isEmpty()){
            return null;
        }

        NptBaseModelPool mainPool = getBaseModelGroupMainPool(model);
        if(null == mainPool){
            return null;
        }
        NptLogicDataType mainType = rmsArchService.fastFindDataTypeById(mainPool.getDataTypeId());
        if(null == mainType || NptCommonUtil.getDefaultParentId().equals(mainType.getUkFieldId())){
            return null;
        }
        NptLogicDataField uField = rmsArchService.fastFindDataFieldById(mainType.getUkFieldId());
        if(null == uField){
            return null;
        }
        NptLogicDataField pField = rmsArchService.findDataFieldById(mainPool.getPrimaryFieldId());
        if(null == pField){
            return null;
        }
        NptLogicDataField titleField = rmsArchService.findDataFieldById(mainPool.getTitleFieldId());
        if(null == titleField){
            return null;
        }
        //获取物理表信息及依据数据主键查询出业务主键的值，以便进行数据池之间的业务主键关系查询
        NptLogicDataType pType = rmsArchService.fastFindDataTypeById(mainPool.getDataTypeId());

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
    public Map<String,Object> getBaseModelPoolIndexValueByUKValue(NptBaseModelPool p, String ukValue) {
        NptLogicDataType mainType = rmsArchService.fastFindDataTypeById(p.getDataTypeId());
        if(null == mainType || NptCommonUtil.getDefaultParentId().equals(mainType.getUkFieldId())){
            return null;
        }
        NptLogicDataField uField = rmsArchService.fastFindDataFieldById(mainType.getUkFieldId());
        if(null == uField){
            return null;
        }

        Collection<NptLogicDataField> indexFields = getBaseModelIndexDataFields(p);
        if(null == indexFields || indexFields.isEmpty()){
            return null;
        }

        Map<String,String> uFieldWhere = new HashMap<>();
        uFieldWhere.put(uField.getFieldDbName(),ukValue);

        String sql = databaseManager.makeUniqueSql(mainType.getTypeDbName(),indexFields,uFieldWhere, NptDict.CST_ENG_AS_CHN);
        return  (Map<String, Object>) databaseManager.queryUnique(sql);
    }


    @Override
    public NptWebFieldDataArray getDataTypeRowDataByUKValue(Long dataTypeId, String ukValue) {
        NptLogicDataType mainType = rmsArchService.fastFindDataTypeById(dataTypeId);
        if (null == mainType || NptCommonUtil.getDefaultParentId().equals(mainType.getUkFieldId())) {
            return null;
        }
        Collection<NptLogicDataField> indexFields = rmsArchService.listDataFieldByConditions(Conditions.eq(NptLogicDataField.PROPERTY_PARENT_ID, dataTypeId), Conditions.eq(NptLogicDataField.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        NptLogicDataField uField = rmsArchService.fastFindDataFieldById(mainType.getUkFieldId());
        Map<String, String> uFieldWhere = new HashMap<>();
        uFieldWhere.put(uField.getFieldDbName(), ukValue);
        String sql = databaseManager.makeUniqueSql(mainType.getTypeDbName(), indexFields, uFieldWhere, NptDict.CST_ENG_AS_CHN);
        Map<String, Object> row = (Map<String, Object>) databaseManager.queryUnique(sql);
        NptWebFieldDataArray dataArray = new NptWebFieldDataArray();
        dataArray.resetData(row, ukValue);
        return dataArray;
    }

    @Override
    public Boolean checkPKValueExisted(NptBaseModelPool pool, String pkValue) {

        NptLogicDataField pkField = getBaseModelGrouPoolPrimaryField(pool);
        NptLogicDataType dataType = getBaseModelGrouPoolDataType(pool);

        if(null != pkField && null != dataType && !StringUtils.isEmpty(pkValue)){

            String sql = "select count(1) from " + dataType.getTypeDbName() + " where " + pkField.getFieldDbName() + " = '" + pkValue + "'";

            Integer count = databaseManager.getCount(sql);

            return count > 0;
        }

        return false;
    }

    /**
     * author: owen
     * date:   2017/4/24 13:31
     * note:
     *
     * @param field
     */
    @Override
    public Map<String, String> listFieldCodeValue(NptLogicDataField field) {
        Map<String,String> rst = new HashMap<>();
        if(null != field && NptDict.FSS_CODE.name().equals(field.getShowStyle())){
            Collection<NptBusinessCode> codes = commonService.listFieldCodes(field.getId());
            if(null != codes && !codes.isEmpty()){
                for(NptBusinessCode c:codes){
                    rst.put(c.getCodeName(),c.getCodeValue());
                }
            }
        }

        return rst;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/18 下午01:47
     * 备注: 更新poolLink
     */
    @Override
    public NptDict updateBaseModelGroupoolLink(Long fromPoolId, Long toPoolId, Long fkFieldId, List<Long> toKeyIds, List<String> linkTitles,List<Long> relFieldIds) {
        List<NptBaseModelLink> existLinks = baseModelPool2PoolManager.findByCondition(Conditions.eq(NptBaseModelLink.PROPERTY_FROM_POOL_ID, fromPoolId),
                Conditions.eq(NptBaseModelLink.PROPERTY_TO_POOL_ID, toPoolId),
                Conditions.eq(NptBaseModelLink.PROPERTY_FROM_POOL_REFKEYID, fkFieldId));
        List<Long> existKeyIds = existLinks.stream().map(NptBaseModelLink::getToKeyId).collect(Collectors.toList());

        Long toKeyId;
        NptBaseModelLink link;
        // add
        for (int i = 0; i < toKeyIds.size(); i++) {
            toKeyId = toKeyIds.get(i);
            if (!existKeyIds.contains(toKeyId)) {
                link = new NptBaseModelLink();
                link.setStatus(NptDict.IDS_ENABLED.getCode());
                link.setFromPoolId(fromPoolId);
                link.setToPoolId(toPoolId);
                link.setPoolRefKeyId(fkFieldId);
                link.setToKeyId(toKeyId);
                if(null != relFieldIds && relFieldIds.contains(toKeyId)){
                    link.setRelLink(NptDict.MLR_RELATION.getCode());
                }else {
                    link.setRelLink(NptDict.MLR_NORMAL.getCode());
                }
                link.setLinkTitle(linkTitles.get(i));
                link.setCreateTime(NptCommonUtil.getCurrentSysDate());
                link.setCreatorId(NptRmsUtil.getCurrentUserId());
                link.setParentId(NptCommonUtil.getDefaultParentId());
                baseModelPool2PoolManager.save(link);
            }

        }
        // update or delete
        for (Iterator<NptBaseModelLink> iterator = existLinks.iterator(); iterator.hasNext(); ) {
            link = iterator.next();
            boolean exist = false;
            for (int i = 0; i < toKeyIds.size(); i++) {
                toKeyId = toKeyIds.get(i);
                if (toKeyId.equals(link.getToKeyId())) {
                    exist = true;
                    link.setLinkTitle(linkTitles.get(i));
                    if(null != relFieldIds && relFieldIds.contains(toKeyId)){
                        link.setRelLink(NptDict.MLR_RELATION.getCode());
                    }else {
                        link.setRelLink(NptDict.MLR_NORMAL.getCode());
                    }
                    baseModelPool2PoolManager.update(link);
                    break;
                }
            }
            if (!exist) {
                baseModelPool2PoolManager.delete(link);
            }
        }
        return NptDict.RST_SUCCESS;
    }

}
