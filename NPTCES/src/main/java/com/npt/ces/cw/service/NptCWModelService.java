package com.npt.ces.cw.service;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.dataBinder.NptWebFieldDataArray;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModel;
import com.npt.bridge.model.NptBaseModelGroup;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.bridge.model.props.bean.NptBaseModelEx;
import com.npt.bridge.model.props.bean.NptBaseModelExMetaData;
import com.npt.bridge.model.props.bean.NptBaseModelGroupEx;
import com.npt.bridge.model.props.bean.NptBaseModelPoolEx;
import com.npt.bridge.model.props.manager.NptBaseModelExMetaDataManager;
import com.npt.ces.cw.anylize.NptCreditWarningEntityData;
import com.npt.ces.cw.bean.NptCWResultDetailBox;
import com.npt.ces.cw.entity.NptCWModelDmsProps;
import com.npt.ces.cw.entity.NptCWModelProps;
import com.npt.ces.cw.entity.NptCWModelSubDmsProps;
import com.npt.ces.cw.manager.NptCWModelDimensionPropsManager;
import com.npt.ces.cw.manager.NptCWModelPropsManager;
import com.npt.ces.cw.manager.NptCWModelSubDmsPropsManager;
import com.npt.grs.model.service.NptGRSBaseModelService;

import java.util.Collection;

/**
 * author: owen
 * date:   2017/7/11 上午10:59
 * note:
 */
public interface NptCWModelService extends NptBaseModelExMetaDataManager {

    String PROPERTY_UNKNOW = "暂无数据";

    /**
     *  author: zhanglei
     *  date:   2017/07/16 15:15
     *  note:
     *          列出信用预警模型
     */
    Collection<NptBaseModelEx> listModels();

    Collection<NptBaseModelGroupEx> listGroups(Long modelId);

    Collection<NptBaseModelPoolEx> listPools(Long groupId);

    Collection<NptLogicDataField> listDataTypeFields(Long poolId);

    Long getUkFieldId(Long poolId);

    /**
     *  author  : owen
     *  date    : 2017/7/11 上午11:08
     *  params  :
     *              []:
     *              []:
     *  note    :
     *          获取基础模型的预警拓展属性
     */
    NptCWModelProps getCreditWarningModelProperties(NptBaseModel baseModel);

    /**
     *  author  : owen
     *  date    : 2017/7/11 上午11:09
     *  params  :
     *              []:
     *              []:
     *  note    :
     *          获取基础模型分组的预警拓展属性
     */
    NptCWModelDmsProps getCreditWarningModelDimensionProperties(NptBaseModelGroup group);
    NptCWModelDmsProps getCreditWarningModelDimensionPropertiesById(Long groupId);
    Collection<NptCWModelDmsProps> getCreditWarningModelDimensionProperties(Collection<NptBaseModelGroup> groups);
    Collection<NptCWModelDmsProps> getCreditWarningModelDimensionPropertiesById(Collection<Long> groupIds);


    /**
     *  author  : owen
     *  date    : 2017/7/11 上午11:14
     *  params  :
     *              []:
     *              []:
     *  note    :
     *          获取基础模型数据池的预警拓展属性
     */
    NptCWModelSubDmsProps getCreditWarningModelSubDimensionProperties(NptBaseModelPool pool);
    NptCWModelSubDmsProps getCreditWarningModelSubDimensionPropertiesById(Long poolId);
    Collection<NptCWModelSubDmsProps> getCreditWarningModelSubDimensionProperties(Collection<NptBaseModelPool> pools);
    Collection<NptCWModelSubDmsProps> getCreditWarningModelSubDimensionPropertiesById(Collection<Long> poolIds);

    /**
     *  author  : owen
     *  date    : 2017/7/11 下午3:09
     *  params  :
     *              []:
     *              []:
     *  note    :
     *          拿实体数据依照元数据的配置信息进行预警评估
     */
    NptCWResultDetailBox compute(String batchNo, NptBaseModelExMetaData metaData, NptCreditWarningEntityData entityData);

    /**
     *  author  : owen
     *  date    : 2017/7/14 下午2:27
     *  params  :
     *              []:
     *              []:
     *  note    :
     *          加载信用实体实体数据
     */
    NptCreditWarningEntityData loadCWEntityData(NptBaseModelExMetaData metaData,String ceId);


    /**
     *  author  : owen
     *  date    : 2017/7/14 下午4:19
     *  params  :
     *              []:
     *              []:
     *  note    :
     *          为数据池的数值字段准备功效计算
     */
    NptDict prepareForEfficacyComputePerPool(NptBaseModelPool pool,Boolean forceUpdate);


    /**
     *  author  : owen
     *  date    : 2017/7/16 下午2:52
     *  params  :
     *              []:
     *              []:
     *  note    :
     *          加载模型的所有数据池
     */
    Collection<NptBaseModelPool> listModelPools(Long modelId);


    /**
     *  author  : owen
     *  date    : 2017/7/16 下午3:52
     *  params  :
     *              []:
     *              []:
     *  note    :
     *          对模型的实体数据进行预处理
     */
    NptDict preTreatEntityData(Long modelId,Boolean forceUpdate);

    NptBaseModel getCreditWarningBaseModel(NptDict type);

    NptCWModelPropsManager getCWModelPropsManager();
    NptCWModelDimensionPropsManager getCWModelDimensionPropsManager();
    NptCWModelSubDmsPropsManager getCWModelSubDimensionPropsManager();
    NptGRSBaseModelService getGRSBaseModelService();

    NptWebFieldDataArray getDataTypeRowDataByUKValue(Long dataTypeId, String ukValue);
}
