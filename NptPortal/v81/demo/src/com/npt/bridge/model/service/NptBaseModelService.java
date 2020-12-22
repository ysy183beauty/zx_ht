package com.npt.bridge.model.service;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.dataBinder.NptWebDetailBlock;
import com.npt.bridge.dataBinder.NptWebFieldDataArray;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.*;

import java.util.Collection;
import java.util.Map;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 12:02
 * 描述:
 */
public interface NptBaseModelService {

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

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午5:14
     * 备注:
     */
    NptBaseModelPool findBaseModelGrouPoolById(Long id);
    NptBaseModelPool fastFindBaseModelGrouPoolById(Long id);

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午5:16
     * 备注: 检测当前数据池是否有关联的数据池
     */
    Integer getBaseModelGrouPoolLinkCount(NptBaseModelPool p, NptDict status);

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

    NptDict directSave(Collection<NptBaseModelPool> pools);

    /**
     *作者：owen
     *时间：2016/12/16 18:00
     *描述:
     *      不做任何处理直接保存
     */
    NptDict directSave(NptBaseModelPoolIndex mf);

    /**
     * 作者: owen
     * 时间: 2017/3/16 上午11:19
     * 描述:
     *      直接保存
     */
    NptDict directSave(NptBaseModelPoolCdt c);

    /**
     *作者：OWEN
     *时间：2016/12/6 20:53
     *描述:
     *
     */
    NptDict deleteBaseModelById(Long modelId);

    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午11:48
     * 备注: 获取基础模型的所有分组
     */
    Collection<NptBaseModelGroup> getBaseModelGroups(NptBaseModel model);

    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午11:55
     * 备注: 获取基础模型的权重组
     */
    NptBaseModelGroup getBaseModelMainGroup(NptBaseModel m);

    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午11:33
     * 备注: 确定某个模型的主字段列表
     */
    Collection<NptBaseModelPoolIndex> getBaseModelIndexFields(NptBaseModel model);

    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午11:35
     * 备注: 获取模型的主数据池
     */
    NptBaseModelPool getBaseModelGroupMainPool(NptBaseModel m);

    /**
     *作者：OWEN
     *时间：2016/12/6 20:53
     *描述:
     *
     */
    NptDict deleteBaseModelGroupById(Long groupId);

    /**
     * 作者: 张磊
     * 日期: 17/2/17 上午11:59
     * 备注: 获取分组下的所有数据池
     */
    Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModelGroup group,NptDict lockLevel);

    Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModel m,NptDict lockLevel);

    /**
     *作者：OWEN
     *时间：2016/12/6 20:53
     *描述:
     *
     */
    NptDict deleteBaseModelGrouPoolById(Long poolId);

    /**
     * 作者: 张磊
     * 日期: 17/2/17 下午2:43
     * 备注: 查询关联向指定数据池的其它数据池, status为null则表示全部状态
     */
    Collection<NptBaseModelLink> getBaseModelGroupoolLinkedMePools(NptBaseModelPool pool, NptDict status);

    /**
     * 作者: 张磊
     * 日期: 17/2/17 下午2:41
     * 备注:
     */
    Collection<NptBaseModelLink> getBaseModelGroupoolLinkedPools(NptBaseModelPool pool, NptDict status);


    /********************************************************************************************
     * 模型的结构：大概念（模型）包含多个小类别（分组），每个类别又包含多个详细的数据表（池）
     *
     *
     *                                模型
     *            |-------------|------------|---------------|
     *          分组一         分组二        分组三           分组四
     *     |---|----|---|     |---|     |——-|---|      |---|----|---|
     *    池1，池2，池3，池4   池1，池2   池1，池2，池3    池1，池2，池3，池4
     *     |   |    |   |     |   |     |   |   |      |   |    |   |
     *     表  表   表  表     表   表    表  表  表      表  表   表   表   （具有相同的业务主键）
     *
     *******************************************************************************************/



    /**
     *作者：owen
     *时间：2017/2/14 11:37
     *描述:
     *      根据模型类别与模型主体定位唯一模型
     *
     *      此方法作用于门户上的模型查询，比如定位红黑榜模型，定位双公示模型，定位企业信息模型等。
     *
     *      若cate和host定位出多个模型，则取第一个模型
     */
    NptBaseModel lookupModelByCategoryAndHost(NptDict cate,NptDict host);

    /**
     *作者：owen
     *时间：2017/2/14 11:41
     *描述:
     *      查询模型的所有分组
     */
    Collection<NptBaseModelGroup> lookupModelGroups(NptBaseModel model);

    /**
     *作者：owen
     *时间：2017/2/14 11:43
     *描述:
     *      查询模型指定分组的所有数据池
     */
    Collection<NptBaseModelPool> lookupModelGroupPools(NptBaseModelGroup group);

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午2:37
     * 备注: 获取用户指定的数据池的主字段
     */
    Collection<NptBaseModelPoolIndex> getBaseModelPoolIndexFields(NptBaseModelPool p);

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午2:20
     * 备注: 获取主数据池的列表开放字段
     */
    Collection<NptLogicDataField> getBaseModelPoolIndexDataFields(NptBaseModelPool pool);

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午2:22
     * 备注: 获取主数据池的物理表信息
     */
    NptLogicDataType getBaseModelGrouPoolDataType(NptBaseModelPool pool);

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午1:53
     * 备注: 加载分页数据
     */
    NptDict getPaginationData(NptLogicDataType type, Collection<NptLogicDataField> fields, NptWebBridgeBean bean, Boolean applyed,Boolean exact);

    NptLogicDataProvider getBaseModelGrouPoolProvider(NptBaseModelPool toPool);

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午5:5
     * 备注: 加载指定数据池的分页列表数据
     */
    NptDict getBaseModelGroupoolPaginationData(Long poolId, NptWebBridgeBean bean,Boolean onlyIndex,Boolean exact);


    /**
     * 作者: 张磊
     * 日期: 17/2/16 上午11:46
     * 备注: 获取模型的主数据池分页列表数据
     */
    NptDict getBaseModelIndexFieldPaginationData(NptBaseModel model, NptWebBridgeBean bean,Boolean exact);

    /**
     * 作者: owen
     * 时间: 2017/3/12 下午1:59
     * 描述:
     *      加载模型的结构信息
     */
    NptBaseModelTree loadBaseModelTree(NptBaseModel model);

    /**
     * 作者: 张磊
     * 日期: 2017/03/13 上午11:38
     * 备注: 根据code获取group
     */
    NptBaseModelGroup getModelGroupByCode(NptBaseModel model, NptDict gCode);


    /**
     * 作者：owen
     * 时间：2017/3/17 下午3:41
     * 描述：
     *      查询指定数据池指定数据主键的唯一一条记录
     */
    NptWebFieldDataArray loadBaseModelPoolRowData(Long poolId, String uFieldValue);

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
    NptDict loadBaseModelAuthGroupsByUK(NptWebBridgeBean bean, NptBaseModel m,Boolean applyed);

    /**
     *作者：owen
     *时间：2016/12/16 15:29
     *描述:
     *      通过数据主键获取模型主数据池中的业务主键值
     */
    Map<String,Object> getModelMainPoolTypicalValueByUKValue(NptBaseModel model, String ukValue);

    /**
     *  author: owen
     *  date:   2017/3/21 下午8:53
     *  note:
     *          通过业务主键查询授权信息
     */
    NptDict loadBaseModelAuthGroupsByPK(NptWebBridgeBean bean, NptBaseModel m,NptDict lockLevel,Boolean applyed);

    /**
     *  author: owen
     *  date:   2017/3/21 下午8:58
     *  note:
     *          检测指定模型与业务主键的数据是否存在
     */
    NptDict isBaseModelAuthPKValueExisted(NptBaseModel model,String pkValue);

    /**
     *  author: owen
     *  date:   2017/3/21 下午9:34
     *  note:
     *          检测指定的模型是否已开放查询
     */
    NptDict isBaseModelQueryInService(NptBaseModel model);

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
    NptWebDetailBlock getBaseModelGrouPoolData(NptBaseModelPool pool, String pkValue,NptDict lockLevel, Boolean first,Boolean applyed);
    NptWebDetailBlock getBaseModelGrouPoolData(Long poolId, String pkValue, NptDict lockLevl,Boolean first,Boolean applyed);


    /**
     * 作者: owen
     * 时间: 2017/3/13 下午9:55
     * 描述:
     *      获取数据池的查询条件列表
     */
    Collection<NptBaseModelPoolCdt> getBaseModelPoolConditions(NptBaseModelPool p);

    Collection<NptBaseModelPoolCdt> getBaseModelPoolConditions(Long poolId);


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
     *作者：OWEN
     *时间：2016/11/13 15:07
     *描述:
     *      加载模型列表
     */
    Collection<NptBaseModel> listModels(NptDict host, NptDict cate);



    /**
     *  author: owen
     *  date:   2017/4/17 下午2:55
     *  note:
     *          获取数据池记录的总条数
     */
    Long getBaseModelPoolDataCount(NptBaseModelPool p);
}
