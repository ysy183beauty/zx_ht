package com.npt.grs.appeal.manager.impl;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.database.manager.NptDatabaseManager;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.sync.entity.NptUserAppeal;
import com.npt.bridge.sync.entity.NptUserAppealDetail;
import com.npt.grs.appeal.manager.NptAppealDetailManager;
import com.npt.grs.model.manager.NptBaseModelGrouPoolManager;
import com.npt.rms.arch.service.NptRmsArchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.summer.extend.manager.BaseManagerImpl;
import org.summer.extend.orm.condition.Conditions;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目：NPTWebApp
 * 作者：97175
 * 日期：2016/11/7 12:10
 * 备注：
 */
@Service
@Transactional
public class NptAppealDetailManagerImpl extends BaseManagerImpl<NptUserAppealDetail> implements NptAppealDetailManager{

    @Resource(name = "rmsArchService")
    private NptRmsArchService rmsArchService;

    @Resource(name = "nptDssOracleManager")
    protected NptDatabaseManager databaseManager;

    @Autowired
    private NptBaseModelGrouPoolManager poolManager;

    @Override
    public Collection<NptUserAppealDetail> getAppealDetailByNo(String appealNo) {
        Collection<NptUserAppealDetail> appealDetails = this.findByCondition(
                Conditions.eq(NptUserAppeal.PROPERTY_APPEAL_NO,appealNo)
                ,Conditions.asc("fieldId")
        );
        return appealDetails;
    }

    @Override
    public Collection<NptLogicDataField> getFieldByNo(String appealNo) {
        Collection<NptLogicDataField> dataFields = new ArrayList<>();
        Collection<Long> fieldIds = new ArrayList<>();
        Collection<NptUserAppealDetail> appealDetails = this.getAppealDetailByNo(appealNo);

        if (null != appealDetails){
            for (NptUserAppealDetail detail: appealDetails){
                fieldIds.add(detail.getFieldId());
            }
            Collection<NptLogicDataField> result = rmsArchService.listDataField(fieldIds);
            if (null != result){
                dataFields.addAll(result);
            }
        }

        return dataFields;
    }

    @Override
    public Object queryAppealFieldData(NptUserAppeal appeal) {
        if (null != appeal){
            Collection<NptLogicDataField> dataFields = this.getFieldByNo(appeal.getAppealNo());

            if (null != dataFields){

                    NptLogicDataType dataType = rmsArchService.fastFindDataTypeById(appeal.getAppealDTID());
                    NptLogicDataField pkField = rmsArchService.fastFindDataFieldById(appeal.getAppealPKID());

                    if (null != dataType && null != pkField){
                        Map<String,String> condition = new HashMap<>();
                        condition.put(pkField.getFieldDbName(),appeal.getAppealBusinessKey());

                        String sql = databaseManager.makeLikeSql(dataType.getTypeDbName(),dataFields,condition, NptDict.CST_ENG_AS_CHN);

                        return databaseManager.queryUnique(sql);
                    }

            }
        }
     return null;
    }
}
