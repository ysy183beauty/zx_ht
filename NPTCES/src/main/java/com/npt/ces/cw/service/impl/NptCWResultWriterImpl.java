package com.npt.ces.cw.service.impl;

import com.npt.bridge.dict.NptDict;
import com.npt.ces.cw.bean.NptCWResultDetailBox;
import com.npt.ces.cw.entity.*;
import com.npt.ces.cw.manager.*;
import com.npt.ces.cw.service.NptCWResultWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * author: owen
 * date:   2017/7/16 下午7:27
 * note:
 */
@Service
@Transactional
public class NptCWResultWriterImpl implements NptCWResultWriter {

    @Autowired
    private NptCWResultManager cwResultManager;
    @Autowired
    private NptCWDmsResultManager cwDmsResultManager;
    @Autowired
    private NptCWSubDmsResultManager cwSubDmsResultManager;
    @Autowired
    private NptCWSubDmsResultDetailManager cwSubDmsResultDetailManager;
    @Autowired
    private NptCWRiskIndexManager riskIndexManager;

    @Override
    public NptDict write(NptCWResultDetailBox box) {

        try {
            NptCWResult cwResult = saveCWResult(box);
            if(null != cwResult){
                List<NptCWDmsResult> savedDMSR = saveDmsResult(box,cwResult);
                saveSDmsResult(box,savedDMSR);

                return NptDict.RST_SUCCESS;
            }else {
                return NptDict.RST_EXCEPTION("保存失败，当前不存在任何可保存的结果");
            }
        }catch (Exception e){
            e.printStackTrace();
            return NptDict.RST_EXCEPTION(e.getMessage());
        }
    }

    @Override
    public NptDict write(List<NptCWResultDetailBox> boxList) {
        if(null != boxList && !boxList.isEmpty()){
            boxList.forEach(box -> this.write(box));
        }
        return NptDict.RST_SUCCESS;
    }

    @Override
    public NptDict write(NptCWRiskIndex ri) {

        riskIndexManager.saveOrUpdate(ri);

        return NptDict.RST_SUCCESS;
    }


    public NptCWResult saveCWResult(NptCWResultDetailBox box){
        if(null != box && box.getReady()){
            return cwResultManager.save(box.getCwResult());
        }
        return null;
    }

    public List<NptCWDmsResult> saveDmsResult(NptCWResultDetailBox box,NptCWResult cwResult){
        List<NptCWDmsResult> results = new ArrayList<>();

        if(null != box && box.getReady() && null != cwResult && null != cwResult.getId()){
            List<NptCWDmsResult> readyToSave = box.getDmsResults();

            readyToSave.forEach(dr -> {
                dr.setResultId(cwResult.getId());
                NptCWDmsResult sdr = cwDmsResultManager.save(dr);
                if(null != sdr){
                    results.add(sdr);
                }
            });
        }

        return results;
    }

    public void saveSDmsResult(NptCWResultDetailBox box,List<NptCWDmsResult> dmsResults){

        if(null != box && box.getReady() && null != dmsResults && !dmsResults.isEmpty()){

            dmsResults.forEach(dr ->{
                List<NptCWSubDmsResult> sdrs = box.getSdmsResultMap().get(dr.getBaseModelGroupId());
                if(null != sdrs && !sdrs.isEmpty()){
                    sdrs.forEach(sdr ->{
                        List<NptCWSubDmsResultDetail> sdrds = box.getSdmsRDetailMap().get(sdr.getDimensionRstId());
                        sdr.setDimensionRstId(dr.getId());
                        sdr.setResultId(dr.getResultId());

                        NptCWSubDmsResult ssdr = cwSubDmsResultManager.save(sdr);
                        if(null != ssdr && null != sdrds && !sdrds.isEmpty()){
                            sdrds.forEach(sdrd ->{
                                sdrd.setSubDimensionRstId(ssdr.getId());
                                cwSubDmsResultDetailManager.save(sdrd);
                            });
                        }
                    });
                }
            });
        }
    }
}
