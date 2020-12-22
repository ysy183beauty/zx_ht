package com.npt.ces.cw.anylize;

import com.npt.bridge.dict.NptDict;

import java.util.List;

/**
 * author: owen
 * date:   2017/7/7 下午5:32
 * note:
 *      预警结果生成接口
 */
public interface NptCreditWarningAnalyzer {

    /**
     *  author  : owen
     *  date    : 2017/7/7 下午5:46
     *  params  :
     *              [modelId]:预警模型ID
     *              []:
     *  note    :
     *          依据指定的模型进行预警分析
     */
    NptDict analyze(Long modelId);

    /**
     *  author  : owen
     *  date    : 2017/7/7 下午5:47
     *  params  :
     *              []:
     *              []:
     *  note    :
     *          获取当前批次处理的数据范围标识码
     */
    List<String> getDataRangeCode();
}
