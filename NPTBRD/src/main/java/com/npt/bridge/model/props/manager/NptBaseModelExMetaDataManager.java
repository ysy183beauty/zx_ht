package com.npt.bridge.model.props.manager;

import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.props.bean.NptBaseModelExMetaData;

import java.util.List;

/**
 * author: owen
 * date:   2017/7/11 上午11:51
 * note:
 */
public interface NptBaseModelExMetaDataManager {

    NptDict init(Long modelId, NptBaseModelExMetaData result);

    List<Object> loadBaseModelMainPoolPFieldValueByUFieldTail(NptBaseModelExMetaData metaData,String ufTail);

    List<String> loadBaseModelCommonPoolDistinctPFieldValues(NptBaseModelExMetaData metaData);
}
