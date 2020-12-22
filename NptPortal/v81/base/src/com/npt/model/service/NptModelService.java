package com.npt.model.service;

import com.npt.arch.entity.NptLogicDataField;
import com.npt.arch.entity.NptLogicDataProvider;
import com.npt.arch.entity.NptLogicDataTable;
import com.npt.dict.NptDict;
import com.npt.model.bean.NptWebBridgeBean;
import com.npt.model.entity.*;

import java.util.Collection;

/**
 * 项目：CreditPortal
 * 作者：owen
 * 时间：2017/1/17 12:02
 * 描述:
 */
public interface NptModelService {

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

    /**
     *作者：owen
     *时间：2016/12/16 18:00
     *描述:
     *      不做任何处理直接保存
     */
    NptDict directSave(NptBaseModelMainField mf);

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
    Collection<NptBaseModelMainField> getBaseModelMainFields(NptBaseModel model);

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
    Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModelGroup group);

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
    Collection<NptBaseModelPoolLink> getBaseModelGroupoolLinkedMePools(NptBaseModelPool pool, NptDict status);

    /**
     * 作者: 张磊
     * 日期: 17/2/17 下午2:41
     * 备注:
     */
    Collection<NptBaseModelPoolLink> getBaseModelGroupoolLinkedPools(NptBaseModelPool pool, NptDict status);


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
    Collection<NptBaseModelMainField> getBaseModelGrouPoolMainFields(NptBaseModelPool p);

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午2:20
     * 备注: 获取主数据池的列表开放字段
     */
    Collection<NptLogicDataField> getBaseModelGrouPoolMainDataFields(NptBaseModelPool pool);

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午2:22
     * 备注: 获取主数据池的物理表信息
     */
    NptLogicDataTable getBaseModelGrouPoolDataType(NptBaseModelPool pool);

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午1:53
     * 备注: 加载分页数据
     */
    NptDict getPaginationData(NptLogicDataTable type, Collection<NptLogicDataField> fields, NptWebBridgeBean bean, Boolean applyed);

    NptLogicDataProvider getBaseModelGrouPoolProvider(NptBaseModelPool toPool);

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午5:5
     * 备注: 加载指定数据池的分页列表数据
     */
    NptDict getBaseModelGroupoolPaginationData(Long poolId, NptWebBridgeBean bean);

    /**
     * 作者: 张磊
     * 日期: 17/2/16 上午11:46
     * 备注: 获取模型的主数据池分页列表数据
     */
    NptDict getModelMainFieldPaginationData(NptBaseModel model,NptWebBridgeBean bean);
}
