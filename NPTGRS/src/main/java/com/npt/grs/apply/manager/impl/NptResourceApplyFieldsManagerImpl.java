package com.npt.grs.apply.manager.impl;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.database.manager.NptDatabaseManager;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.NptBaseModelPool;
import com.npt.grs.apply.dao.NptResourceApplyFieldsDao;
import com.npt.grs.apply.entity.NptResourceApply;
import com.npt.grs.apply.entity.NptResourceApplyField;
import com.npt.grs.apply.manager.NptResourceApplyFieldsManager;
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
 * 作者: 97175
 * 日期：2016/10/10 15:52
 * 备注：
 */
@Service
@Transactional
public class NptResourceApplyFieldsManagerImpl extends BaseManagerImpl<NptResourceApplyField> implements NptResourceApplyFieldsManager {
    @Autowired
    private NptResourceApplyFieldsDao resourceApplyFieldsDao;

    @Autowired
    private NptBaseModelGrouPoolManager poolManager;

    @Resource(name = "rmsArchService")
    private NptRmsArchService rmsArchService;

    @Resource(name = "nptDssOracleManager")
    protected NptDatabaseManager databaseManager;

    /**
     * 作者：owen
     * 日期：2016/10/30 17:46
     * 备注：
     * 根据申请号查询当前批次所申请的字段集合
     * 参数：
     * 返回：
     *
     * @param no
     */
    @Override
    public Collection<NptResourceApplyField> getApplyFieldsByApplyNO(String no) {
        return this.findByCondition(Conditions.eq(NptResourceApply.PROPERTY_APPLY_NO,no));
    }

    /**
     * 作者：owen
     * 日期：2016/11/1 18:16
     * 备注：
     * 获取申请的字段详情
     * 参数：
     * 返回：
     *
     * @param no
     */
    @Override
    public Collection<NptLogicDataField> getApplyFieldsDetailByApplyNO(String no) {

        Collection<NptLogicDataField> result = new ArrayList<>();
        Collection<NptResourceApplyField> applyFields = getApplyFieldsByApplyNO(no);
        Collection<Long> fieldIdList = new ArrayList<>();
        if(null != applyFields){
            for(NptResourceApplyField f:applyFields){
                fieldIdList.add(f.getFieldId());
            }
            Collection<NptLogicDataField> searchResult = rmsArchService.listDataField(fieldIdList);
            if(null != searchResult){
                result.addAll(searchResult);
            }
        }
        return result;
    }

    /**
     * 作者：owen
     * 日期：2016/11/2 14:43
     * 备注：
     * 以申请编号查询申请的字段的详细数据
     * 参数：
     * 返回：
     *
     * @param apply
     */
    @Override
    public Object queryApplyFieldData(NptResourceApply apply) {
        if(null != apply){
            Collection<NptLogicDataField> fields = getApplyFieldsDetailByApplyNO(apply.getApplyNo());
            if(null != fields){
                Long poolId = apply.getApplyGrouPoolId();
                NptBaseModelPool pool = poolManager.findById(poolId);
                if(null != pool){
                    NptLogicDataType dataType = poolManager.getBaseModelGrouPoolDataType(pool);
                    NptLogicDataField pkField = rmsArchService.fastFindDataFieldById(pool.getPrimaryFieldId());
                    if(null != dataType && null != pkField){
                        Map<String,String> condition = new HashMap<>();
                        condition.put(pkField.getFieldDbName(),apply.getApplyBusinessKey());

                        String sql = databaseManager.makeUniqueSql(
                                dataType.getTypeDbName(),
                                fields,
                                condition,
                                NptDict.CST_ENG_AS_CHN
                                );

                        return databaseManager.queryUnique(sql);
                    }
                }

            }
        }
        return null;
    }

}
