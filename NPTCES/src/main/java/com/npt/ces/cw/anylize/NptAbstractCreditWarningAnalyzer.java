package com.npt.ces.cw.anylize;

import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.props.bean.NptBaseModelExMetaData;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.ces.cw.bean.NptCWResultDetailBox;
import com.npt.ces.cw.entity.NptCWModelDmsProps;
import com.npt.ces.cw.entity.NptCWModelProps;
import com.npt.ces.cw.entity.NptCWModelSubDmsProps;
import com.npt.ces.cw.entity.NptCWRiskIndex;
import com.npt.ces.cw.service.NptCWModelService;
import com.npt.ces.cw.service.NptCWResultWriter;
import com.npt.ces.utils.NptCesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * author: owen
 * date:   2017/7/7 下午5:48
 * note:
 */
@Service
@Transactional
public class NptAbstractCreditWarningAnalyzer implements NptCreditWarningAnalyzer {

    @Autowired
    private NptCWResultWriter resultWriter;
    @Autowired
    private NptCWModelService cwModelService;

    /**
     * author  : owen
     * date    : 2017/7/7 下午5:46
     * params  :
     * [modelId]:预警模型ID
     * []:
     * note    :
     * 依据指定的模型进行预警分析
     *
     * @param modelId
     */
    @Override
    public NptDict analyze(Long modelId) {

        NptBaseModelExMetaData<
                NptCWModelProps,
                NptCWModelDmsProps,
                NptCWModelSubDmsProps>   cwModelMeta = new NptBaseModelExMetaData<>();


        cwModelService.init(modelId,cwModelMeta);
        if(null == cwModelMeta){
            return NptDict.RST_EXCEPTION("通过ID[" + modelId + "]未找到合适的信用预警模型!");
        }
        if(!cwModelMeta.getCompleted()) {
            return NptDict.RST_EXCEPTION(cwModelMeta.getCompleteNote());
        }


        /**
         * 生成此次分析的批次号
         */
        final String batchNo = NptCesUtil.generateBatchNO(modelId);
        Integer todayTotalScore = 0;
        List<NptCWResultDetailBox> needSave = new ArrayList<>();

        Long batchCount = 0l;

        List<String> pFieldValue = cwModelService.loadBaseModelCommonPoolDistinctPFieldValues(cwModelMeta);

        if (null != pFieldValue && !pFieldValue.isEmpty()) {
            for (String ceId : pFieldValue) {

                System.out.println("当前批次共计[" + pFieldValue.size() + "]个需要处理，当前开始处理第[" + (batchCount + 1) + "]个");

                NptCreditWarningEntityData entityData = cwModelService.loadCWEntityData(cwModelMeta, ceId);
                if (null != entityData) {
                    try {
                        NptCWResultDetailBox result = cwModelService.compute(batchNo, cwModelMeta, entityData);
                        System.out.println("已完成[" + ceId + "]的计算，预警评分为[" + (null == result ? 0 : result.getCwResult().getRiskScore()) + "]");
                        if (null != result && result.getReady()) {
                            todayTotalScore += result.getCwResult().getRiskScore();
                            needSave.add(result);
                            batchCount++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        resultWriter.write(needSave);
        System.out.println("预警评分结果已保存完毕！");

        NptCWRiskIndex ri = new NptCWRiskIndex();
        todayTotalScore = Math.toIntExact(todayTotalScore / batchCount);
        ri.setRiskIndex(todayTotalScore);
        ri.setBatchNO(batchNo);
        ri.setComputeDay(new Date());
        ri.setCreditEntityType(cwModelMeta.getModelEx().getBaseModel().getHostId());

        resultWriter.write(ri);
        System.out.println("整体预警指数已更新完毕！");

        NptCWModelProps mp = cwModelMeta.getModelEx().getModelProperties();

        if (!batchNo.equals(mp.getCurrentBatch())) {
            mp.setLastBatch(mp.getCurrentBatch());
            mp.setCurrentBatch(batchNo);

            cwModelService.getCWModelPropsManager().updateModelProps(mp);
        }

        return NptDict.RST_SUCCESS;
    }

    protected Boolean hadAnalyzedThisBatch(String ceId, String batchNo){
        return false;
    }
    /**
     * author  : owen
     * date    : 2017/7/7 下午5:47
     * params  :
     * []:
     * []:
     * note    :
     * 获取当前批次处理的数据范围标识码
     */
    @Override
    public List<String> getDataRangeCode(){

        List<Integer> todayChar = NptCesUtil.getWeekTailChar(NptCommonUtil.getWeekDayNumber());
        todayChar.add(2);

        List<String> result = new ArrayList<>();

        for(Integer tc:todayChar) {
            for (int i = 0; i <= 9; i++) {
                result.add(String.valueOf(i) + String.valueOf(tc));
            }
        }
        return result;
    }
}
