package com.npt.ces.cw.service;

import com.npt.bridge.dict.NptDict;
import com.npt.ces.cw.bean.NptCWResultDetailBox;
import com.npt.ces.cw.entity.NptCWRiskIndex;

import java.util.List;

/**
 * author: owen
 * date:   2017/7/6 上午10:46
 * note:
 *      预警结果写入接口
 */
public interface NptCWResultWriter {

    NptDict write(NptCWResultDetailBox box);

    NptDict write(List<NptCWResultDetailBox> boxList);

    NptDict write(NptCWRiskIndex ri);
}
