package com.npt.grs.model.service;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.dataBinder.NptWebDetailBlock;
import com.npt.bridge.dataBinder.NptWebFieldDataArray;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.*;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/10 20:28
 * 备注：
 */
public interface NptGRSBaseModelService {

    /**
     * 作者：owen
     * 日期：2016/10/20 14:21
     * 备注：
     *      获取指定模型的基本信息
     * 参数：
     * 返回：
     */
    NptBaseModel findBaseModelById(Long id);
    NptBaseModel fastFindBaseModelById(Long id);
    /**
     *作者：OWEN
     *时间：2016/11/13 15:17
     *描述: 
     *      
     */
    NptBaseModelGroup findBaseModelGroupById(Long id);
    NptBaseModelGroup fastFindBaseModelGroupById(Long id);
    NptBaseModelGroup findBaseModelGroupByCode(Long modelId, NptDict code);

    /**
     *作者：OWEN
     *时间：2016/11/13 15:07
     *描述: 
     *      加载模型列表
     */
    Collection<NptBaseModel> listModels(NptDict host, NptDict cate);

    Collection<NptBaseModel> listModels(List<NptDict> hostList, List<NptDict> cateList);

    Collection<NptBaseModel> listModels();
    /**
     * 作者：owen
     * 日期：2016/10/20 15:18
     * 备注：
     *      获取指定宿主下唯一的原生基础模型
     * 参数：
     * 返回：
     */
    NptBaseModel getBaseModelHostNativeModel(NptDict host);

    /**
     * 作者：owen
     * 日期：2016/10/20 14:23
     * 备注：
     *      获取基础模型的主数据池
     * 参数：
     * 返回：
     */
    NptBaseModelPool getBaseModelMainGrouPool(NptBaseModelGroup g);

    /**
     * 作者：owen
     * 日期：2016/10/24 21:09
     * 备注：
     *      通过id获取指定的数据池
     * 参数：
     * 返回：
     */
    NptBaseModelPool findBaseModelGrouPoolById(Long id);
    NptBaseModelPool fastFindBaseModelGrouPoolById(Long id);

    /**
     * 作者：owen
     * 日期：2016/11/4 12:04
     * 备注：
     *      获取指定数据池关联的其它数据池的详细信息
     * 参数：
     * 返回：
     */
    Collection<NptWebDetailBlock> getBaseModelGroupoolLinkedPoolData(Long poolId, Long refFieldId, String pkValue);

    /**
     * 作者：owen
     * 日期：2016/10/20 21:33
     * 备注：
     *      获取分组下的所有数据池
     * 参数：
     * 返回：
     */
    Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModelGroup g, NptDict status, Boolean needLink);

    /**
     *  author: zhanglei
     *  date:   2017/05/24 15:41
     *  note:
     *          获取状态可用的,权重在[startScore,endScore]区间内的数据池
     */
    Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModelGroup g, Integer startScore, Integer endScore);

    /**
     * 作者：owen
     * 日期：2016/10/20 21:39
     * 备注：
     *      
     * 参数：
     * 返回：
     */
    Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModel m, NptDict status, Boolean needLink);

    /**
     *  author: zhanglei
     *  date:   2017/05/24 15:41
     *  note:
     *          获取状态可用的,权重在[startScore,endScore]区间内的数据池
     */
    Collection<NptBaseModelPool> getBaseModelPools(NptBaseModel m, Integer startScore, Integer endScore);

    /**
     *作者：OWEN
     *时间：2016/12/7 15:01
     *描述:
     *      获取不跟当前数据池同属同一模型的所有可用数据池
     */
    Collection<NptBaseModelPool> getOtherModelAllAvailableGroupools(Long poolId);

    /**
     *作者：OWEN
     *时间：2016/12/7 14:32
     *描述:
     *      获取所有业务主键跟指定字段杰克匹配的数据池
     */
    Collection<NptBaseModelPool> getAllPossibleGroupools(Long poolId, Long fieldId);

    /**
     * 作者：owen
     * 日期：2016/10/24 13:43
     * 备注：
     *      获取模型的主数据池
     * 参数：
     * 返回：
     */
    NptBaseModelPool getBaseModelGroupMainPool(NptBaseModel m);

    /**
     *作者：owen
     *时间：2016/12/21 17:58
     *描述:
     *      检测模型是否有主数据池
     */
    Boolean isBaseModelHaveMainPool(NptBaseModel m);

    /**
     * 作者：owen
     * 日期：2016/10/20 14:24
     * 备注：
     *      获取基础模型的权重组
     * 参数：
     * 返回：
     */
    NptBaseModelGroup getBaseModelMainGroup(NptBaseModel m);
    
    /**
     * 作者：owen
     * 日期：2016/10/20 21:32
     * 备注：
     *      获取基础模型的所有分组
     * 参数：
     * 返回：
     */
    Collection<NptBaseModelGroup> getBaseModelGroups(NptBaseModel m);

    Collection<NptBaseModelGroup> getBaseModelGroups(NptBaseModel m, NptDict status);

    /**
     * 作者：owen
     * 日期：2016/10/20 14:26
     * 备注：
     *      获取指定数据池的业务主键字段
     * 参数：
     * 返回：
     */
    NptLogicDataField getBaseModelGrouPoolPrimaryField(NptBaseModelPool p);

    /**
     * 作者：owen
     * 日期：2016/10/21 12:04
     * 备注：
     *      获取指定字段的详情
     * 参数：
     * 返回：
     */
    NptLogicDataField getBaseModelGrouPoolFieldById(Long id);

    /**
     * 作者：owen
     * 日期：2016/10/20 14:29
     * 备注：
     *      获取指定模型组数据池的主字段,若业务主键不存在于主字段中，将追加之
     * 参数：
     * 返回：
     */
    Collection<NptLogicDataField> getBaseModelIndexDataFields(NptBaseModelPool p);

    /**
     *作者：OWEN
     *时间：2016/11/16 17:25
     *描述:
     *      获取用户指定的数据池的主字段
     */
    Collection<NptBaseModelPoolIndex> getBaseModelPoolIndex(NptBaseModelPool p);

    /**
     * 作者：owen
     * 日期：2016/10/21 11:53
     * 备注：
     *      确定指定模型的主数据池的索引字段列表
     * 参数：
     * 返回：
     */
    Collection<NptBaseModelPoolIndex> getBaseModelIndex(NptBaseModel m);

    /**
     * 作者: owen
     * 时间: 2017/3/15 下午8:08
     * 描述:
     *      确定指定模型的主数据池的查询字段列表
     */
    Collection<NptBaseModelPoolCdt> getBaseModelCondition(NptBaseModel m);


    /**
     * 作者: owen
     * 时间: 2017/3/15 下午8:15
     * 描述:
     *      加载模型核心数据池的查询条件
     */
    void loadBaseModelConditions(NptBaseModel model, NptWebBridgeBean bean);

    /**
     * 作者: owen
     * 时间: 2017/3/15 下午8:16
     * 描述:
     *      加载指定数据池的查询条件
     */
    void loadBaseModelPoolConditions(NptBaseModelPool pool, NptWebBridgeBean bean);

    /**
     * 作者：owen
     * 日期：2016/10/20 14:33
     * 备注：
     *      获取指定数据池的表信息
     * 参数：
     * 返回：
     */
    NptLogicDataType getBaseModelGrouPoolDataType(NptBaseModelPool p);

    /**
     * 作者：owen
     * 日期：2016/10/20 14:35
     * 备注：
     *      获取数据池的数据提供方信息
     * 参数：
     * 返回：
     */
    NptLogicDataProvider getBaseModelGrouPoolProvider(NptBaseModelPool p);


    /**
     * 作者：owen
     * 日期：2016/10/20 16:05
     * 备注：
     *      加载指定模型的默认可公开字段数据列表
     * 参数：
     * 返回：
     */
    NptDict loadBaseModelOpenList(NptWebBridgeBean bean, NptBaseModel m, Boolean applyed);

    /**
     *  author: owen
     *  date:   2017/3/23 下午2:21
     *  note:
     *          从核心数据池中依据数据池的条件字段进行[或]关系的模糊查询
     */
    NptDict fuzzySearchFromMainPool(
            NptBaseModelPool pool,
            String keyword,
            NptDict pubLevel,
            NptWebBridgeBean bean);


    /**
     *  author: owen
     *  date:   2017/3/23 15:53
     *  note:
     *          从普通数据池中依据数据池的条件字段进行【或】关系的模糊查询
     */
    Collection<NptWebFieldDataArray> fuzzySearchFromCommonPool(
            NptBaseModelPool pool,
            String keyword,
            NptDict pubLevel);

    /**
     *作者：owen
     *时间：2016/12/16 15:29
     *描述:
     *      通过数据主键获取模型主数据池中的业务主键值
     */
    String getModelPoolPKValueByUKValue(NptBaseModelPool pool, String ukValue);

    /**
     * 作者：owen
     * 日期：2016/10/20 16:07
     * 备注：
     *      加载指定模型的某条业务记录的详细分组数据
     *
     *      这里有三项入口信息：数据主键，数据主键的值，业务主键
     *      其中，数据主键及其值用于定位表里的唯一一行记录
     *      业务主键用于主数据向副数据的扩展
     * 参数：
     * 返回：
     */
    NptDict loadBaseModelAuthGroupsByUK(NptWebBridgeBean bean, NptBaseModel m);

    NptDict loadBaseModelPoolDataByUK(NptWebBridgeBean bean, NptBaseModelPool p);

    NptDict loadBaseModelDetailByUK(NptWebBridgeBean bean, NptBaseModel m);


    /**
     *作者：OWEN
     *时间：2016/11/21 15:33
     *描述:
     *      通过业务主键查询指定模型的分组详情
     */
    NptDict loadBaseModelAuthGroupsByPK(NptWebBridgeBean bean, NptBaseModel m);

    NptDict loadBaseModelPoolAuthBlocksByPK(NptWebBridgeBean bean, NptBaseModelPool p);

    /**
     *  author: owen
     *  date:   31/03/2017 9:33 PM
     *  note:
     *          依据业务主键加载指定分组的的数据
     */
    NptDict loadBaseModelGroupDataByUnitedPk(NptBaseModelGroup group, String pkValue, NptWebBridgeBean bean);

    /**
     * 作者：owen
     * 日期：2016/10/24 13:58
     * 备注：
     *      获取指定数据池的详细数据
     * 参数：若pkValue为null，则只加载数据池的基本信息，不加载其真实数据
     *      first为true，表示按优先级只返回一条记录，为false表示返回全部数据
     * 返回：
     */
    NptWebDetailBlock getBaseModelGrouPoolData(NptBaseModelPool p, String pkValue, Boolean first);

    Collection<NptWebFieldDataArray> getBaseModelGrouPoolDataList(NptBaseModelPool p, List<Long> fields, String pkValue, Boolean applyed);


    /**
     * 作者：owen
     * 日期：2016/11/4 11:54
     * 备注：
     *      检测当前数据池是否有关联的数据池
     * 参数：
     * 返回：
     */
    Integer getBaseModelGrouPoolLinkCount(NptBaseModelPool p, NptDict status);

    /**
     *作者：OWEN
     *时间：2016/11/13 23:15
     *描述:
     *      查询指定数据池关联的指定状态的其它数据池
     *
     *      status为null则表示全部状态
     */
    Collection<NptBaseModelLink> getBaseModelGroupoolLinkedPools(NptBaseModelPool p, NptDict status);

    /**
     *作者：OWEN
     *时间：2016/12/7 17:05
     *描述:
     *      查询关联向指定数据池的其它数据池
     *
     *      status为null则表示全部状态
     */
    Collection<NptBaseModelLink> getBaseModelGroupoolLinkedMePools(NptBaseModelPool p, NptDict status);

    /**
     *作者：OWEN
     *时间：2016/11/14 15:19
     *描述:
     *      查询指定数据池指定模型外键关联的其它数据池
     */
    Collection<NptBaseModelLink> getBaseModelGroupoolLinkedPools(Long poolId, Long fieldId);

    /**
     * 作者: 张磊
     * 日期: 2017/04/11 下午09:08
     * 备注:
     */
    Collection<NptBaseModelLink> listBaseModelGroupoolLinkedPools(Long fromPoolId, Long fkFieldId, Long toPoolId);

    /**
     *作者：OWEN
     *时间：2016/11/16 22:53
     *描述:
     *      设置模型关联的状态
     */
    NptDict setBaseModelGroupoolLinkStatus(Long linkId, String status);

    /**
     * 作者：owen
     * 日期：2016/10/26 12:19
     * 备注：
     *      加载当前用户对指定数据池的字段申请状态
     * 参数：
     * 返回：
     */
    NptWebDetailBlock getBaseModelGroupoolApplyFields(NptBaseModelPool p, String pkValue);

    /**
     *作者：OWEN
     *时间：2016/11/13 20:55
     *描述:
     *      添加基础模型基础信息
     */
    NptDict addBaseModelBasicInfo(NptBaseModel model);

    /**
     *作者：OWEN
     *时间：2016/11/13 20:39
     *描述:
     *      为指定的模型添加分组
     */
    NptDict addGroupToBaseModel(Long modelId, NptBaseModelGroup group);

    /**
     *作者：OWEN
     *时间：2016/11/13 20:40
     *描述:
     *      设置指定的模型中指定的分组的状态
     */
    NptDict setBaseModelGroupStatus(Long modelId, Long groupId, NptDict status);

    /**
     *作者：OWEN
     *时间：2016/12/6 11:08
     *描述:
     *      设置指定模型的状态
     */
    NptDict setBaseModelStatus(Long modelId, NptDict status);

    /**
     *作者：OWEN
     *时间：2016/12/7 17:40
     *描述:
     *      向分组中新增一个数据池
     */
    NptDict addPoolToBaseModelGroup(Long groupId, Long dataTypeId, Long pkFieldId, List<Long> indexFields, NptDict poolType, List<Long> pConFieldIds, NptBaseModelPool myPool);

    /**
     *作者：OWEN
     *时间：2016/12/7 18:13
     *描述:
     *      更新指定的数据池
     */
    NptDict updatePoolToBaseModelGroup(Long poolId, Long dataTypeId, Long pkFieldId, List<Long> indexFields, NptDict poolType, List<Long> pConFieldIds, NptBaseModelPool myPool);

    /**
     *作者：OWEN
     *时间：2016/11/13 20:43
     *描述:
     *      从指定的分组中删除指定的数据池
     */
    NptDict setBaseModeGroupoolStatus(Long groupId, Long poolId, NptDict status);

    /**
     *作者：OWEN
     *时间：2016/11/13 20:43
     *描述:
     *      为指定的分组设置主数据池
     */
    NptDict setBaseModelGroupMainPool(Long poolId);

    /**
     *作者：OWEN
     *时间：2016/11/16 16:00
     *描述:
     *      添加模型关联信息
     */
    NptDict addBaseModelGroupoolLink(NptBaseModelPool from, NptBaseModelPool to, NptLogicDataField fk, Long toKeyId, String linkTitle);

    /**
     *作者：OWEN
     *时间：2016/11/28 14:44
     *描述:
     *      加载基础模型的结构信息
     */
    NptBaseModelStructure loadBaseModelStructure(Long modelId);
    NptBaseModelStructure loadBaseModelStructure(NptBaseModelPool pool);

    /**
     *  author: owen
     *  date:   2017/3/27 11:28
     *  note:
     *          加载模型树状结构
     */
    NptBaseModelTree loadBaseModelTree(Long modelId, NptWebBridgeBean bean);

    /**
     *作者：owen
     *时间：2016/12/19 18:09
     *描述:
     *      检测数据池是否可以同步到外部系统
     */
    NptDict statusFilter(NptBaseModelStructure structure);

    /**
     *作者：owen
     *时间：2016/12/13 20:32
     *描述:
     *      加载模型数据池的增量数据
     */
    NptBaseModelPoolRow loadBaseModelGrouPoolIncrementData(NptBaseModelPool pool, Timestamp max, Integer start, Integer end, Boolean transform);

    /**
     *作者：owen
     *时间：2016/12/14 18:04
     *描述:
     *      更新指定模型的所有数据池实体数据的最后"操作时间"
     */
    void updateBaseModelPoolDataTimestamp(Long poolId, NptDict useBy, Timestamp current);

    /**
     *作者：OWEN
     *时间：2016/11/29 16:20
     *描述:
     *      更新模型基础信息
     */
    NptBaseModel update(NptBaseModel model);


    /**
     *作者: xuqinyuan
     *时间: 2016/12/5 14:44
     *备注:  更新分组基础信息
     *
     * @param group
     * @return
     */
    NptBaseModelGroup update(NptBaseModelGroup group);

    /**
     * 作者：xuqinyuan
     * 时间：2017/1/18 15:59
     * 备注：更新数据池信息
     */
    NptBaseModelPool update(NptBaseModelPool pool);

    /**
     *作者: xuqinyuan
     *时间: 2016/12/5 14:39
     *备注: 检查模型名称是否存在
     *
     * @param modelName
     */
    NptDict checkBaseModel(String modelName);

    /**
     *作者: xuqinyuan
     *时间: 2016/12/5 14:39
     *备注: 检查模型名称是否存在
     *
     * @param modelId
     * @param groupName
     * @return
     */
    NptDict checkBaseModelGroup(Long modelId, String groupName);

    /**
     *作者：OWEN
     *时间：2016/12/5 21:11
     *描述:
     *      不做任何处理直接保存
     */
    NptDict directSave(NptBaseModel model);

    /**
     *作者：OWEN
     *时间：2016/12/5 21:11
     *描述:
     *      不做任何处理直接保存
     */
    NptDict directSave(NptBaseModelGroup group);

    /**
     *作者：OWEN
     *时间：2016/12/5 21:11
     *描述:
     *      不做任何处理直接保存
     */
    NptDict directSave(NptBaseModelPool pool);

    /**
     *作者：owen
     *时间：2016/12/16 18:00
     *描述:
     *      不做任何处理直接保存
     */
    NptDict directSave(NptBaseModelPoolIndex mf);

    /**
     *作者：OWEN
     *时间：2016/12/6 20:53
     *描述:
     *
     */
    NptDict deleteBaseModelById(Long modelId);

    /**
     *作者：OWEN
     *时间：2016/12/6 20:53
     *描述:
     *
     */
    NptDict deleteBaseModelGroupById(Long groupId);

    /**
     *作者：OWEN
     *时间：2016/12/6 20:53
     *描述:
     *
     */
    NptDict deleteBaseModelGrouPoolById(Long poolId);

    /**
     *作者：OWEN
     *时间：2016/12/6 21:00
     *描述:
     *
     */
    NptDict delete(NptBaseModelLink p2p);

    /**
     *作者：owen
     *时间：2016/12/14 20:09
     *描述:
     *      加载指定数据池的分页列表数据
     */
    NptDict getBaseModelGroupoolPaginationData(Long poolId, NptWebBridgeBean bean, Boolean onlyIndex, Boolean applyed, Boolean exact);

    /**
     *作者：owen
     *时间：2016/12/16 15:08
     *描述:
     *      获取分页数据
     */
    NptDict getPaginationData(NptLogicDataType type, Collection<NptLogicDataField> fields, NptWebBridgeBean bean, Boolean applyed, Boolean exact);

    List<Object> getPoolPKFieldDataListByUKFieldTailNO(NptBaseModelPool pool, String where);

    List<String> getPoolPKFieldDistinctValues(NptBaseModelPool pool);



    /**
     * 作者：xuqinyuan
     * 时间：2017/1/18 15:39
     * 备注：分组排序
     */
    void updateGroupDisplayOrder(List<Long> GroupIds);

    /**
     * 作者：xuqinyuan
     * 时间：2017/1/18 15:40
     * 备注：数据池排序
     */
    void updateGrouPoolDisplayOrder(List<Long> poolIds);

    /**
     * 作者：xuqinyuan
     * 时间：2017/1/18 15:50
     * 备注：批量更新分组
     */
    void batchUpdateGroup(Collection<NptBaseModelGroup> list);

    /**
     * 作者：xuqinyuan
     * 时间：2017/1/18 15:51
     * 备注：批量更新数据池
     */
    void batchUpdateGrouPool(Collection<NptBaseModelPool> list);

    /**
     * 作者: 张磊
     * 日期: 2017/03/14 下午10:33
     * 备注: 获取查询条件
     */
    Collection<NptBaseModelPoolCdt> getBaseModelPoolConditions(NptBaseModelPool pool);

    /**
     * 作者: 张磊
     * 日期: 2017/04/12 上午10:36
     * 备注: 删除poolLink
     */
    NptBaseModelLink deleteBaseModelGroupoolLink(Long linkId);

    /**
     * 作者: 张磊
     * 日期: 2017/04/18 下午01:47
     * 备注: 更新poolLink
     */
    NptDict updateBaseModelGroupoolLink(Long fromPoolId, Long toPoolId, Long fkFieldId, List<Long> toKeyIds, List<String> linkTitles, List<Long> relFieldIds);

    /**
     *  author: owen
     *  date:   2017/4/24 13:31
     *  note:
     *
     */
    Map<String,String> listFieldCodeValue(NptLogicDataField field);


    /**
     *  author: owen
     *  date:   2017/4/26 14:45
     *  note:
     *          通过创建索引的方式优化模型的查询效率
     */
    void optimizeQuery(Long modelId);

    /**
     *作者：owen
     *时间：2016/12/16 15:29
     *描述:
     *      通过数据主键获取模型主数据池中的业务主键值
     */
    Map<String,Object> getModelMainPoolTypicalValueByUKValue(NptBaseModel model, String ukValue);
    Map<String,Object> getModelMainPoolTypicalValueByPKValue(NptBaseModel model, String pkValue);

    Map<String,Object> getBaseModelPoolIndexValueByUKValue(NptBaseModelPool p, String ukValue);

    NptWebFieldDataArray getDataTypeRowDataByUKValue(Long dataTypeId, String ukValue);

    Boolean checkPKValueExisted(NptBaseModelPool pool, String pkValue);
}
