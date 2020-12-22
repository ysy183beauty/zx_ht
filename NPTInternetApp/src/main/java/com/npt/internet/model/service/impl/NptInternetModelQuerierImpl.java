package com.npt.internet.model.service.impl;

import com.npt.dsp.manager.NptDatabaseManager;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.model.NptBaseModel;
import com.npt.grs.model.service.NptGRSBaseModelService;
import com.npt.internet.model.service.NptInternetModelQuerier;
import com.npt.internet.query.bean.NptInternetModel;
import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.bridge.dict.NptRmsDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.web.dataBinder.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2016/12/16 14:45
 * 描述:
 */
@Service
public class NptInternetModelQuerierImpl implements NptInternetModelQuerier{

    @Resource(name = "nptGrsModelService")
    protected NptGRSBaseModelService modelService;
    @Resource(name = "rmsArchService")
    private NptRmsArchService rmsArchService;
    @Resource(name = "nptDssOracleManager")
    protected NptDatabaseManager databaseManager;

    /**
     * 作者：owen
     * 时间：2016/12/16 14:47
     * 描述:
     * 加载模型的分组信息
     *
     * @param model
     */
    @Override
    public Collection<NptBaseModelGroup> listModelGroups(NptBaseModel model) {
        return modelService.getBaseModelGroups(model);
    }

    /**
     * 作者：owen
     * 时间：2016/12/16 14:48
     * 描述:
     * 加载分组的数据池信息
     *
     * @param group
     */
    @Override
    public Collection<NptBaseModelPool> listModelGrouPools(NptBaseModelGroup group) {
        return modelService.getBaseModelGrouPools(group);
    }

    /**
     * 作者：owen
     * 时间：2016/12/16 14:50
     * 描述:
     * 加载数据池的分页数据
     *
     * @param poolId
     * @param bean
     */
    @Override
    public NptRmsDict getBaseModelGroupoolPaginationData(Long poolId, NptWebBridgeBean bean) {
        return modelService.getBaseModelGroupoolPaginationData(poolId,bean);
    }

    /**
     * 作者：owen
     * 时间：2016/12/16 14:58
     * 描述:
     * 依据主体及类别加载模型
     *
     * @param host
     * @param cate
     */
    @Override
    public Collection<NptBaseModel> listModels(NptRmsDict host, NptRmsDict cate) {
        return modelService.listModels(host,cate);
    }

    /**
     * 作者：owen
     * 时间：2016/12/16 15:04
     * 描述:
     * 获取模型的主数据池分页列表数据
     *
     * @param model
     * @param bean
     */
    @Override
    public NptRmsDict getModelMainFieldPaginationData(NptBaseModel model, NptWebBridgeBean bean) {
        //获取模型的主组
        NptBaseModelPool mainPool = modelService.getBaseModelGroupMainPool(model);
        if(null == mainPool){
            return NptRmsDict.RST_EXCEPTION("模型ID：" + model.getId() + "不存在主数据池");
        }
        return getBaseModelGroupoolPaginationData(mainPool.getId(),bean);
    }

    /**
     * 作者：owen
     * 时间：2016/12/16 15:19
     * 描述:
     * 依据数据主键加载其在该模型中的详细数据
     *
     * @param model
     * @param bean
     */
    @Override
    public NptRmsDict getModelGroupDetailList(NptBaseModel model, NptWebBridgeBean bean) {
        if(null == model){
            return NptRmsDict.RST_EXCEPTION("模型不存在");
        }
        if(null == bean){
            return NptRmsDict.RST_EXCEPTION("数据载体不存在");
        }
        String pkValue = modelService.getModelMainPoolPKValueByUKValue(model,bean.getPrimaryKeyValue());
        if(null == pkValue || pkValue.isEmpty()){
            return NptRmsDict.RST_EXCEPTION("业务主键值不能为空！");
        }

        Collection<NptWebDetailGroup> webGroupList = new ArrayList<>();
        //加载模型的所有分组及每个分组的数据池
        Collection<NptBaseModelGroup> groupList = modelService.getBaseModelGroups(model);
        if(null != groupList && !groupList.isEmpty()){
            //当前模型已分组，则依次加载其每个组的相关信息
            for(NptBaseModelGroup group:groupList) {
                Collection<NptBaseModelPool> aPoolList = modelService.getBaseModelGrouPools(group);
                if (null != aPoolList && !aPoolList.isEmpty()) {
                    NptWebDetailGroup wGroup = new NptWebDetailGroup();
                    wGroup.setGroupTitle(group.getGroupTitle());
                    wGroup.setGroupName(group.getGroupName());
                    Collection<NptWebDetailBlock> blocks = new ArrayList<>();
                    for(NptBaseModelPool pool:aPoolList){
                        NptWebDetailBlock block = getBaseModelGrouPoolData(pool,pkValue);
                        if(null != block){
                            blocks.add(block);
                        }
                    }
                    wGroup.setBlockList(blocks);
                    webGroupList.add(wGroup);
                }
            }
        }else {
            return NptRmsDict.RST_EXCEPTION("当前模型未分组，数据暂时不提供查询");
        }
        bean.setDataList(webGroupList);
        bean.setPrimaryKeyValue(pkValue);
        return NptRmsDict.RST_SUCCESS;
    }

    /**
     * 作者：owen
     * 时间：2016/12/21 13:13
     * 描述:
     * 列表所有模型
     */
    @Override
    public Collection<NptInternetModel> listModels() {
        Collection<NptInternetModel> result = new ArrayList<>();

        Collection<NptBaseModel> searchResult = modelService.listModels();
        if(null != searchResult && !searchResult.isEmpty()){
            for(NptBaseModel bm:searchResult){
                NptInternetModel im = new NptInternetModel();
                im.setModelId(bm.getId());
                im.setModelTitle(bm.getModelName());
                im.setModelNote(bm.getModelNote());
                NptRmsDict modelHost = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.BMH,bm.getHostId());
                if(null != modelHost){
                    im.setModelCategory(modelHost.name());
                }
                result.add(im);
            }
        }
        return result;
    }

    /**
     * 作者：owen
     * 时间：2016/12/21 14:23
     * 描述:
     *
     * @param modelId
     */
    @Override
    public NptBaseModel getBaseModelById(Long modelId) {
        return modelService.fastFindBaseModelById(modelId);
    }

    /**
     * 作者：owen
     * 时间：2016/12/21 14:25
     * 描述:
     *
     * @param model
     */
    @Override
    public NptWebModelTree getBaseModelTree(NptBaseModel model) {
        NptWebModelTree<NptBaseModel,NptBaseModelGroup,NptBaseModelPool> tree = new NptWebModelTree();
        if(null != model){
            tree.setRoot(model);
            Collection<NptBaseModelGroup> groups = this.listModelGroups(model);
            if(null != groups){
                for(NptBaseModelGroup g:groups){
                    NptWebModelTree.NptWebModelSketelon sketelon = tree.instanceSkeleton();
                    sketelon.setSketeton(g);
                    Collection<NptBaseModelPool> pools = this.listModelGrouPools(g);
                    if(null != pools){
                        sketelon.getLeafs().addAll(pools);
                    }
                    tree.getSkeletons().add(sketelon);
                }
            }
        }
        return tree;
    }

    /**
     * 作者：owen
     * 时间：2016/12/21 14:30
     * 描述:
     *
     * @param model
     */
    @Override
    public Boolean isModelHaveMainFields(NptBaseModel model) {
        return modelService.isBaseModelHaveMainPool(model);
    }

    private NptWebDetailBlock getBaseModelGrouPoolData(NptBaseModelPool pool, String pkValue) {
        //构建结果块
        NptWebDetailBlock<NptBaseModelPool,NptLogicDataField,NptLogicDataProvider> block = new NptWebDetailBlock();
        if(null == pool){
            return null;
        }
        //获取数据池的物理表信息
        NptLogicDataType poolType = modelService.getBaseModelGrouPoolDataType(pool);
        NptLogicDataProvider providerOrg = modelService.getBaseModelGrouPoolProvider(pool);

        if(null == poolType){
            return null;
        }
        //获取数据池的物理字段集合
        Collection<NptLogicDataField> fieldList = rmsArchService.listDataField(poolType.getId(),NptRmsDict.FAL_SOPEN,NptRmsDict.IDS_ENABLED);
        if(null == fieldList || fieldList.isEmpty()){
            return null;
        }
        //数据池自身信息
        pool.setPoolTitle(poolType.getAlias());
        block.setBlockInfo(pool);
        //数据池提供方信息
        block.setParentInfo(providerOrg);
        if(null != pkValue && !pkValue.isEmpty()){
            //构建业务主键查询条件
            NptLogicDataField pkField = modelService.getBaseModelGrouPoolPrimaryField(pool);
            if(null == pkField){
                return null;
            }

            Map<String,String> pkWhere = new HashMap<>();
            pkWhere.put(pkField.getFieldDbName(),pkValue);
            Integer queryCount = 1;
            //构建查询语句
            String[] sql = databaseManager.makeLastedDataSql(
                    poolType.getTypeDbName(),
                    fieldList,
                    pkWhere,
                    null,
                    queryCount
            );
            int totalCount = databaseManager.getCount(sql[0]);
            if(totalCount > NptCommonUtil.IntegerZero()){
                List<Object> queryData = databaseManager.queryList(sql[1],fieldList);
                Collection<NptWebFieldDataArray> dataArrayList = new ArrayList<>();
                for (Object obj : queryData) {
                    NptWebFieldDataArray dataArray = new NptWebFieldDataArray();
                    Map<String, Object> temp = (Map<String, Object>) obj;
                    Set<String> keys = temp.keySet();
                    for (String title : keys) {
                        String value = String.valueOf(temp.get(title));
                        NptWebFieldDataArray.NptWebFieldData fieldData = dataArray.instanceFieldData();
                        fieldData.setTitle(title);
                        fieldData.setValue(value);
                        dataArray.getDataArray().add(fieldData);
                    }
                    dataArrayList.add(dataArray);
                }
                block.setDataArray(dataArrayList);
            }
            //当前数据池指定业务对象的所有字段值
            block.setDataCount(totalCount);
        }

        return block;
    }
}
