package com.npt.grs.map.service;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.dataBinder.NptWebBridgeBean;
import com.npt.bridge.database.manager.NptDatabaseManager;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.map.service.NptMapServiceBase;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelLink;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.NptBaseModelPoolCdt;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.grs.model.service.NptGRSBaseModelService;
import com.npt.rms.arch.service.NptRmsArchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * author: owen
 * date:   2017/4/11 下午5:45
 * note:
 */
@Service
@Transactional
public class NptBaseModelMapService extends NptMapServiceBase{

    @Autowired
    private NptGRSBaseModelService modelService;

    @Resource(name = "nptDssOracleManager")
    protected NptDatabaseManager databaseManager;

    @Resource(name = "rmsArchService")
    private NptRmsArchService rmsArchService;


    /**
     * author: owen
     * date:   2017/4/19 下午3:47
     * note:
     * 加载图谱结点的基本信息
     *
     * @param poolId
     * @param ukValue
     * @param bean
     */
    @Override
    public NptDict getNodeInfo(Long poolId, String ukValue, NptWebBridgeBean bean) {

        NptBaseModelPool pool = modelService.findBaseModelGrouPoolById(poolId);
        if(null == pool || !pool.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
            return NptDict.RST_EXCEPTION("数据池[id=" + poolId + "不存在或状态异常");
        }
        if(null == bean || StringUtils.isBlank(ukValue)){
            return NptDict.RST_INVALID_PARAMS;
        }

        bean.setPrimaryKeyValue(ukValue);

        return modelService.loadBaseModelPoolDataByUK(bean,pool);
    }

    /**
     * author: owen
     * date:   2017/4/11 下午9:17
     * note:
     *
     * @param model
     * @param value
     */
    @Override
    public NptDict fuzzySearchFromMainPool(NptBaseModel model, String value, NptWebBridgeBean bean) {
        NptBaseModelPool mainPool = getBaseModelMainPool(model);
        if(null != mainPool && mainPool.getStatus().equals(NptDict.IDS_ENABLED.getCode())) {
            return modelService.fuzzySearchFromMainPool(mainPool, value, null, bean);
        }else {
            return NptDict.RST_EXCEPTION("当前查询服务未开放");
        }
    }

    /**
     * author: owen
     * date:   2017/4/11 下午8:18
     * note:
     * 加载模型的查询字段
     *
     * @param model
     */
    @Override
    public Collection<NptBaseModelPoolCdt> getBaseModelSearchConditions(NptBaseModel model) {
        return modelService.getBaseModelCondition(model);
    }

    /**
     * author: owen
     * date:   2017/4/11 下午2:07
     * note:
     * 获取数据池的外键链接
     *
     * @param p
     */
    @Override
    public Collection<NptBaseModelLink> getPoolLinks(NptBaseModelPool p) {

        return modelService.getBaseModelGroupoolLinkedPools(p,NptDict.IDS_ENABLED);
    }

    /**
     * author: owen
     * date:   2017/4/10 下午8:25
     * note:
     * SELECT COUNT(1) FROM DATATYPE WHERE FIELD=VALUE
     *
     * @param dataType
     * @param field
     * @param value
     */
    @Override
    public Integer selectCount(NptLogicDataType dataType, NptLogicDataField field, String value) {

        StringBuilder sb = new StringBuilder();

        if(null != dataType && null != field && !StringUtils.isBlank(value)) {
            sb.append("SELECT COUNT(1) FROM ")
                    .append(dataType.getTypeDbName())
                    .append(" WHERE ")
                    .append(field.getFieldDbName())
                    .append(" = '")
                    .append(value)
                    .append("'");

            return databaseManager.getCount(sb.toString());
        }
        return NptCommonUtil.IntegerZero();
    }

    /**
     * author: owen
     * date:   2017/4/10 下午8:22
     * note:
     * SELECT SEARCHFIELDS FROM DATATYPE WHERE FIELD=VALUE
     *
     * @param dataType
     * @param searchFields
     * @param field
     * @param value
     * @param onlyEng
     */
    @Override
    public List<Object> selectRows(NptLogicDataType dataType,
                                   Collection<NptLogicDataField> searchFields,
                                   NptLogicDataField field,
                                   String value,
                                   NptDict onlyEng) {

        if(null != dataType
                && null != searchFields
                && !searchFields.isEmpty()
                && null != field
                && !StringUtils.isBlank(value)
                && null != onlyEng){

            StringBuilder sb = new StringBuilder();

            sb.append("SELECT ")
                    .append(NptCommonUtil.getFieldString(searchFields,",",onlyEng))
                    .append(" FROM ")
                    .append(dataType.getTypeDbName())
                    .append(" WHERE ")
                    .append(field.getFieldDbName())
                    .append(" = '")
                    .append(value)
                    .append("'");

            return databaseManager.queryList(sb.toString(),searchFields);
        }
        return new ArrayList<>();
    }

    /**
     * author: owen
     * date:   2017/4/10 下午8:14
     * note:
     * 获取逻辑数据字段
     *
     * @param id
     */
    @Override
    public NptLogicDataField getLogicDataFieldById(Long id) {
        return rmsArchService.fastFindDataFieldById(id);
    }

    /**
     * author: owen
     * date:   2017/4/10 下午8:12
     * note:
     * 获取逻辑数据类别
     *
     * @param id
     */
    @Override
    public NptLogicDataType getLogicDataTypeById(Long id) {
        return rmsArchService.fastFindDataTypeById(id);
    }

    /**
     * author: owen
     * date:   2017/4/10 下午7:59
     * note:
     * 加载模型的索引字段
     *
     * @param p
     */
    @Override
    public Collection<NptLogicDataField> getBaseModelPoolIndexFields(NptBaseModelPool p) {
        return modelService.getBaseModelIndexDataFields(p);
    }

    /**
     * author: owen
     * date:   2017/4/10 下午7:37
     * note:
     * 获取模型的主数据池
     *
     * @param model
     */
    @Override
    public NptBaseModelPool getBaseModelMainPool(NptBaseModel model) {
        return modelService.getBaseModelGroupMainPool(model);
    }

    /**
     * author: owen
     * date:   2017/4/24 13:25
     * note:
     * 加载逻辑字段的码表信息
     *
     * @param fields
     */
    @Override
    public Map<String, Map<String, String>> getFieldCodeValue(List<NptLogicDataField> fields) {
        Map<String,Map<String,String>> rst = new HashMap<>();

        if(null != fields && !fields.isEmpty()){
            for(NptLogicDataField field:fields){
                if(NptDict.FSS_CODE.name().equals(field.getShowStyle())){
                    Map<String,String> fc = modelService.listFieldCodeValue(field);
                    if(null!= fc && !fc.isEmpty()){
                        rst.put(field.getFieldDbName(),fc);
                    }
                }
            }
        }

        return rst;
    }

    /**
     * author: owen
     * date:   2017/4/11 下午2:09
     * note:
     * 通过ID获取数据池信息
     *
     * @param id
     */
    @Override
    public NptBaseModelPool getBaseModelPoolById(Long id) {
        return modelService.fastFindBaseModelGrouPoolById(id);
    }
}
