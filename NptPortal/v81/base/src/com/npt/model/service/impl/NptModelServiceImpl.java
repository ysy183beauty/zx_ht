package com.npt.model.service.impl;

import com.npt.arch.entity.NptLogicDataField;
import com.npt.arch.entity.NptLogicDataProvider;
import com.npt.arch.entity.NptLogicDataTable;
import com.npt.arch.service.NptArchService;
import com.npt.database.service.NptDataBaseService;
import com.npt.dict.NptDict;
import com.npt.model.bean.NptWebBridgeBean;
import com.npt.model.bean.NptWebFieldDataArray;
import com.npt.model.entity.*;
import com.npt.model.manager.*;
import com.npt.model.service.NptModelService;
import com.npt.util.NptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
public class NptModelServiceImpl implements NptModelService{

    @Autowired
    private NptBaseModelManager modelManager;
    @Autowired
    private NptBaseModelGroupManager groupManager;
    @Autowired
    private NptBaseModelPoolManager poolManager;
    @Autowired
    private NptBaseModelMainFieldManager mainFieldManager;
    @Autowired
    private NptBaseModelPoolLinkManager poolLinkManager;
    @Autowired
    private NptArchService archService;
    @Autowired
    private NptDataBaseService dataBaseService;

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
        loadBaseModelGroupoolTitle(pool);
        return pool;
    }
    
    @Override
    public NptBaseModelPool fastFindBaseModelGrouPoolById(Long id) {
        NptBaseModelPool pool = poolManager.fastFindById(id);
        loadBaseModelGroupoolTitle(pool);
        return pool;
    }

    private void loadBaseModelGroupoolTitle(NptBaseModelPool pool){
        if(null == pool){
            return;
        }
        
        NptLogicDataTable type = archService.findDataTableById(pool.getDataTypeId());
        if (null != type) {
            pool.setPoolTitle(type.getTypeName());
        }
        NptLogicDataProvider provider = archService.findParent(type);
        if(null != provider){
            pool.setProviderTitle(provider.getOrgName());
        }
        NptLogicDataField pField = archService.fastFindDataFieldById(pool.getPrimaryFieldId());
        if (null != pField) {
            pool.setpFieldTitle(pField.getAlias());
        }
        Integer linkedCount = this.getBaseModelGrouPoolLinkCount(pool,NptDict.IDS_ENABLED);
        pool.setLinkedPoolCount(linkedCount);
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

    /**
     * 作者：owen
     * 时间：2016/12/16 18:00
     * 描述:
     * 不做任何处理直接保存
     *
     * @param mf
     */
    @Override
    public NptDict directSave(NptBaseModelMainField mf) {
        try {
            mainFieldManager.save(mf);
            return NptDict.RST_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return NptDict.RST_EXCEPTION;
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
                Collection<NptBaseModelMainField> mainFieldss = this.getBaseModelMainFields(model);
                if(null != mainFieldss){
                    for(NptBaseModelMainField mf:mainFieldss){
                        mainFieldManager.delete(mf);
                    }
                }

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
        if(null != result && result.size() > NptUtil.INTEGER_0){
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
    public Collection<NptBaseModelMainField> getBaseModelMainFields(NptBaseModel m) {
        NptBaseModelPool mainPool = getBaseModelGroupMainPool(m);
        if(null == mainPool){
            return null;
        }
        Collection<NptBaseModelMainField> mainFieldses = mainFieldManager.getBaseModelMainFields(m);

        return mainFieldses;
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
            loadBaseModelGroupoolTitle(mainPool);
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
                Collection<NptBaseModelPool> pools = this.getBaseModelGrouPools(group);
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
    public Collection<NptBaseModelPool> getBaseModelGrouPools(NptBaseModelGroup group) {
        Collection<NptBaseModelPool> pools = poolManager.getBaseModelGrouPools(group);

        loadBaseModelGrouPoolTitle(pools);

        return pools;
    }

    private void loadBaseModelGrouPoolTitle(Collection<NptBaseModelPool> pools) {
        if(null != pools && !pools.isEmpty()) {
            for (NptBaseModelPool pool : pools) {
                loadBaseModelGroupoolTitle(pool);
            }
        }
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
                Collection<NptBaseModelPoolLink> p2pList = this.getBaseModelGroupoolLinkedPools(pool, null);
                Collection<NptBaseModelPoolLink> meP2pList = this.getBaseModelGroupoolLinkedMePools(pool, null);

                if (null != p2pList && !p2pList.isEmpty()) {
                    if (null != meP2pList) {
                        p2pList.addAll(meP2pList);
                    }

                    for (NptBaseModelPoolLink p2p : p2pList) {
                        poolLinkManager.delete(p2p);
                    }
                }

                Collection<NptBaseModelMainField> fields = this.getBaseModelGrouPoolMainFields(pool);
                if (null != fields && !fields.isEmpty()) {
                    for (NptBaseModelMainField f : fields) {
                        mainFieldManager.delete(f);
                    }
                }

                poolManager.delete(pool);
            }
            return NptDict.RST_SUCCESS;
        }catch (Exception e){
            return NptDict.RST_EXCEPTION(e.getMessage());
        }

    }

    /**
     * 作者: 张磊
     * 日期: 17/2/17 下午2:43
     * 备注: 查询关联向指定数据池的其它数据池, status为null则表示全部状态
     */
    @Override
    public Collection<NptBaseModelPoolLink> getBaseModelGroupoolLinkedMePools(NptBaseModelPool p, NptDict status) {
        if (null != p) {
            Collection<NptBaseModelPoolLink> popList = poolLinkManager.getBaseModelGroupoolLinkedMePools(p, status);

            if (null != popList && !popList.isEmpty()) {
                for (NptBaseModelPoolLink p2p : popList) {
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
    public Collection<NptBaseModelPoolLink> getBaseModelGroupoolLinkedPools(NptBaseModelPool p, NptDict status) {
        if(null != p) {
            Collection<NptBaseModelPoolLink> popList = poolLinkManager.getBaseModelGroupoolLinkedPools(p, status);

            if(null != popList && !popList.isEmpty()){
                for(NptBaseModelPoolLink p2p:popList){
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
        return modelManager.lookupModelGroupPools(group);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午2:38
     * 备注: 获取用户指定的数据池的主字段
     */
    @Override
    public Collection<NptBaseModelMainField> getBaseModelGrouPoolMainFields(NptBaseModelPool p) {
        return modelManager.getBaseModelGrouPoolMainFields(p);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/15 下午2:31
     * 备注: 获取主数据池的列表开放字段
     */
    @Override
    public Collection<NptLogicDataField> getBaseModelGrouPoolMainDataFields(NptBaseModelPool p) {
        Collection<NptBaseModelMainField> mainFieldses = this.getBaseModelGrouPoolMainFields(p);
        List<NptLogicDataField> result = new ArrayList<NptLogicDataField>();
        if(null != p) {
            NptLogicDataTable poolType = archService.findDataTableById(p.getDataTypeId());
            if(null != poolType && !NptUtil.getDefaultParentId().equals(poolType.getUkFieldId())) {
                NptLogicDataField uniqueField = archService.findDataFieldById(poolType.getUkFieldId());
                if (null != uniqueField) {
                    uniqueField.setDisplay(NptUtil.INTEGER_0);
                    result.add(uniqueField);
                    if (null != mainFieldses && !mainFieldses.isEmpty()) {
                        for (NptBaseModelMainField field : mainFieldses) {
                            NptLogicDataField temp = mainFieldManager.getBaseModelGrouPoolFieldById(field.getFieldId());
                            if (null != temp) {
                                //文本输入查询条件,支持模糊查询
                                if (NptDict.SCT_TEXTBOX.getCode() == field.getSearchCondition()) {
                                    temp.setLikeQuery(NptUtil.INTEGER_1);
                                }
                                temp.setDisplay(NptUtil.INTEGER_1);
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
    public NptLogicDataTable getBaseModelGrouPoolDataType(NptBaseModelPool p) {
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
    public NptDict getPaginationData(NptLogicDataTable type, Collection<NptLogicDataField> fields, NptWebBridgeBean bean, Boolean applyed) {
        //构建查询语句
        if(null != fields && fields.size() > 0 && null != type) {
            String[] sql = dataBaseService.makePaginationSql(
                    type.getTypeDbName(),
                    fields,
                    bean.getCurrPage(),
                    bean.getPageSize(),
                    bean.getQueryCondition(),
                    bean.getQueryOrderBy()
            );

            int totalCount = dataBaseService.getCount(sql[0]);
            if(totalCount > 0){
                Collection<NptWebFieldDataArray> formatData = queryAndFormat(sql[1],fields,null,applyed);
                if(null != formatData && !formatData.isEmpty()) {
                    bean.setColumnTitles(formatData.iterator().next().getTitleList());
                    bean.setDataList(formatData);
                }
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
    private Collection<NptWebFieldDataArray> queryAndFormat(String sql, Collection<NptLogicDataField> fields,NptBaseModelPool pool,boolean applyed){
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
        Collection<Long> p2pFieldIdList = new ArrayList<Long>();
        if (null != pool) {
            Collection<NptBaseModelPoolLink> p2pList = poolLinkManager.getBaseModelGroupoolLinkedPools(pool, NptDict.IDS_ENABLED);
            if (null != p2pList && !p2pList.isEmpty()) {
                for (NptBaseModelPoolLink p2p : p2pList) {
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
            int index = NptUtil.INTEGER_0;
            /**
             * 数据行中的字段循环
             */
            for (String title : keys) {
                Object value = temp.get(title);
                /**
                 * 检测当前行数据是否已被锁定
                 */
/*                Collection<NptWebFieldDataArray> stopResult = showStyleController.stopLookingup(title,value);
                if(null != stopResult){
                    dataArrayList.addAll(stopResult);
                    break;
                }*/
                NptWebFieldDataArray.NptWebFieldData fieldData = dataArray.instanceFieldData();
                fieldData.setTitle(title);
                fieldData.setValue(String.valueOf(value));
                if (p2pFieldIdList.contains(fieldArray[index].getId())) {
                    fieldData.setLinked(NptUtil.INTEGER_1);
                    fieldData.setFieldId(fieldArray[index].getId());
                } else {
                    fieldData.setLinked(NptUtil.INTEGER_0);
                }
//                showStyleController.makeValueShowStyle(value, applyed, fieldArray[index], fieldData);

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
            NptLogicDataTable dataType = archService.fastFindDataTableById(dataTypeId);
            if(null != dataType){
                return archService.findParent(dataType);
            }
        }
        return null;
    }

    @Override
    public NptDict getBaseModelGroupoolPaginationData(Long poolId, NptWebBridgeBean bean) {
        NptBaseModelPool pool = this.fastFindBaseModelGrouPoolById(poolId);
        if(null == pool || null == bean){
            return NptDict.RST_EXCEPTION("指定的数据池不存在:数据池ID[" + poolId + "]");
        }
        //获取数据池的物理表信息
        NptLogicDataTable dataType = archService.findDataTableById(pool.getDataTypeId());
        if(null == dataType){
            return NptDict.RST_EXCEPTION("数据池对应的数据表不存在:数据池ID[" + poolId + "]");
        }
        //获取主数据池字段
        Collection<NptLogicDataField> showFields = archService.listDataField(dataType.getId(),NptDict.FAL_SOPEN,NptDict.IDS_ENABLED);
        if(null == showFields || showFields.isEmpty()){
            return NptDict.RST_EXCEPTION("数据池对应的数据表不包含任何可显示的字段:数据池ID[" + poolId + "]");
        }

        //加载分页数据
        return getPaginationData(dataType,showFields,bean,false);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/16 上午11:46
     * 备注: 获取模型的主数据池分页列表数据
     */
    @Override
    public NptDict getModelMainFieldPaginationData(NptBaseModel model, NptWebBridgeBean bean) {
        //获取模型的主组
        NptBaseModelPool mainPool = poolManager.getBaseModelGroupMainPool(model);
        if (null == mainPool) {
            return NptDict.RST_EXCEPTION("模型ID：" + model.getId() + "不存在主数据池");
        } else {
            loadBaseModelGroupoolTitle(mainPool);
        }
        return getBaseModelGroupoolPaginationData(mainPool.getId(), bean);
    }
}
