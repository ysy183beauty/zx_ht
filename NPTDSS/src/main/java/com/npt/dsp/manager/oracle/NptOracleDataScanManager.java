package com.npt.dsp.manager.oracle;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.database.bean.NameTitleType;
import com.npt.bridge.database.bean.PhyDBItems;
import com.npt.bridge.database.manager.NptDatabaseManager;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.dsp.bean.NptDataScanResult;
import com.npt.dsp.dao.NptDataScanDao;
import com.npt.dsp.manager.NptDataScanManager;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.rms.util.NptRmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/28 12:06
 * 备注：
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NptOracleDataScanManager implements NptDataScanManager{

    @Autowired
    private NptDataScanDao scanDao;
    @Autowired
    private NptDatabaseManager databaseManager;
    @Resource(name = "rmsArchService")
    private NptRmsArchService archService;


    private List<PhyDBItems> loadPhyTables(){
        //根据当前机构加载出所有的物理表
        Collection<NptLogicDataProvider> orgList = archService.listAllEnabledOrg();
        List<PhyDBItems> phyTables = new ArrayList<>();
        for(NptLogicDataProvider org:orgList) {
            List<Map<String,String>> temp = scanDao.loadDataTypeByOrg(org.getOrgCode());
            PhyDBItems opt = new PhyDBItems();
            opt.parentId = org.getId();
            for(Map<String,String> t:temp) {
                NameTitleType nt = new NameTitleType(t.get("TABLE_NAME"), t.get("COMMENTS"),null,null);
                opt.dbInfo.add(nt);
            }

            phyTables.add(opt);
        }
        return phyTables;
    }

    private List<PhyDBItems> loadPhyColumns(){
        //计算当前所有的数据表
        Collection<NptLogicDataType> allDTList = archService.listAllEnabledDataType();

        //提取当前所有数据表的的合法字段
        List<PhyDBItems> phyColumns = new ArrayList<>();
        for(NptLogicDataType dt:allDTList){
            List<Map<String,String>> temp = scanDao.loadDataField(dt.getTypeDbName());
            PhyDBItems opt = new PhyDBItems();
            opt.parentId = dt.getId();
            for(Map<String,String> t:temp) {
                NameTitleType nt = new NameTitleType(dt.getTypeDbName() + "@" + t.get("COLUMN_NAME"), t.get("COMMENTS"),t.get("DATA_TYPE"),t.get("DATA_LENGTH"));
                opt.dbInfo.add(nt);
            }

            phyColumns.add(opt);
        }

        return phyColumns;
    }


    private Map<String,NptLogicDataType> loadExistedDTMap(){
        //加载出当前已存在的所有数据类别跟数据字段
        Collection<NptLogicDataType> existedDTList = archService.listAllDataType();

        //构建DT映射集合
        Map<String,NptLogicDataType> namedDTMap = new HashMap<>();
        for(NptLogicDataType dt:existedDTList){
            namedDTMap.put(dt.getTypeDbName(),dt);
        }

        return namedDTMap;
    }

    @Override
    public NptDataScanResult mountTables(){

        List<PhyDBItems> phyTables = loadPhyTables();

        Map<String,NptLogicDataType> namedDTMap = loadExistedDTMap();

        List<NptLogicDataType> needUpdateDTList = new ArrayList<>();
        List<NptLogicDataType> needInsertDTList = new ArrayList<>();
        List<NptLogicDataType> needDeleteDTList = new ArrayList<>();

        int unknowCount = 0;
        //依次检测每个机构的扫描数据
        for(PhyDBItems opt:phyTables){
            List<NameTitleType> nts = opt.dbInfo;

            //依次处理每个机构下已扫描到的表
            for(NameTitleType nt:nts){
                //已存在，则更新
                if(namedDTMap.containsKey(nt.name)){
                    needUpdateDTList.add(namedDTMap.get(nt.name));
                }else {
                    //不存在，则新建
                    NptLogicDataType dt = new NptLogicDataType();
                    if(null == nt.title || nt.title.isEmpty()){
                        dt.setTypeName("unknow");
                        unknowCount ++;
                    }else {
                        dt.setTypeName(nt.title);
                    }

                    dt.setTypeDbName(nt.name);
                    if(dt.getTypeName().length() > NptBaseEntity.ALIAS_MAX_LENGTH){
                        dt.setAlias(dt.getTypeName().substring(0,NptBaseEntity.ALIAS_MAX_LENGTH));
                    }else {
                        dt.setAlias(dt.getTypeName());
                    }
                    dt.setCreateTime(new Date());
                    dt.setStatus(NptDict.IDS_ENABLED.getCode());
                    dt.setPubLevel(NptDict.FAL_GOPEN.getCode());
                    dt.setParentId(opt.parentId);
                    dt.setUkFieldId(NptCommonUtil.getDefaultParentId());

                    needInsertDTList.add(dt);
                }
            }
        }

        archService.batchSaveDataType(needInsertDTList);

        NptDataScanResult result = new NptDataScanResult();
        result.setInsertCount(needInsertDTList.size());
        result.setUpdateCount(needUpdateDTList.size());
        result.setUnknowCount(unknowCount);

        Set<String> namedDTKey = namedDTMap.keySet();
        for(String key:namedDTKey){
            NptLogicDataType extDT = namedDTMap.get(key);
            if(!needUpdateDTList.contains(extDT)){
                extDT.setStatus(NptDict.IDS_DELETED.getCode());
                needDeleteDTList.add(extDT);
            }
        }
        result.setDeletedCount(needDeleteDTList.size());
        archService.batchUpdateDataType(needDeleteDTList);

        return result;
    }


    private Map<String,NptLogicDataField> loadExistedDFMap(){
        //构建DF映射集合
        Map<String,NptLogicDataField> namedDFMap = new HashMap<>();
        Collection<NptLogicDataType> existedDTList = archService.listAllDataType();
        if(null != existedDTList){
            for(NptLogicDataType dt:existedDTList){
                Collection<NptLogicDataField> existedDFList = archService.listDataFieldEx(dt.getId(), NptDict.IDS_DELETED);
                for(NptLogicDataField df:existedDFList){
                    namedDFMap.put(dt.getTypeDbName() + "@" + df.getFieldDbName(),df);
                }
            }
        }
        return namedDFMap;
    }

    /**
     * 作者：owen
     * 日期：2016/11/1 22:31
     * 备注：
     *     扫描表字段的数据类型，只单独将时间类型单独列出，其它全当普通字符串处理
     * 参数：
     * 返回：
     */
    private NptDict getNptTypeByOracleTyep(String oraType){

        String str = oraType;
        int location = oraType.indexOf("(");
        if(location > 0){
            str = oraType.substring(0,location);
        }

        if(str.equals("DATE") || str.equals("TIMESTAMP")){
            return NptDict.FSS_DATE;
        }
        return NptDict.FSS_COMMON_TEXT;
    }


    @Override
    public NptDataScanResult mountColumns(){

        List<PhyDBItems> phyColumns = loadPhyColumns();
        Map<String,NptLogicDataField> namedDFMap = loadExistedDFMap();

        List<NptLogicDataField> needUpdateDFList = new ArrayList<>();
        List<NptLogicDataField> needInsertDFList = new ArrayList<>();
        List<NptLogicDataField> needDeleteDFList = new ArrayList<>();

        int unknowCount = 0;
        //依次检测每个机构的扫描数据
        for(PhyDBItems opt:phyColumns){
            List<NameTitleType> nts = opt.dbInfo;
            if(!databaseManager.containReservedField(nts)){
                unknowCount += nts.size();
                continue;
            }
            int displayOrder = 1;
            List<String> tempFieldAlias = new ArrayList<>();
            //依次处理每个机构下已扫描到的表
            for(NameTitleType nt:nts){
                //已存在，则更新
                if(namedDFMap.containsKey(nt.name)){
                    needUpdateDFList.add(namedDFMap.get(nt.name));
                }else {
                    if(null != nt.title && nt.title.length() > 0){
                        //不存在，则新建
                        NptLogicDataField df = new NptLogicDataField();
                        df.setFieldName(nt.title);
                        df.setFieldDbName(nt.name.substring(nt.name.indexOf("@") + 1));
                        if(df.getFieldDbName().startsWith("NCF_")){
                            continue;
                        }

                        String alias;
                        if(nt.title.length() > NptBaseEntity.ALIAS_MAX_LENGTH){
                            alias = nt.title.substring(0,NptBaseEntity.ALIAS_MAX_LENGTH);
                        }else{
                            alias = nt.title;
                        }
                        if(tempFieldAlias.contains(alias)){
                            alias = df.getFieldDbName();
                        }

                        df.setAlias(alias);
                        df.setDisplayOrder(displayOrder);
                        df.setFieldDbType(nt.type);
                        df.setFieldDbLen(nt.len);
                        df.setParentId(opt.parentId);
                        df.setShowStyle(getNptTypeByOracleTyep(nt.type).name());
                        df.setCreateTime(NptCommonUtil.getCurrentSysDate());
                        df.setCreatorId(NptRmsUtil.getCurrentUserId());
                        df.setModifyId(NptRmsUtil.getCurrentUserId());
                        df.setLastModifyTime(NptCommonUtil.getCurrentSysDate());
                        df.setStatus(NptDict.IDS_ENABLED.getCode());
                        df.setPubLevel(NptDict.FAL_GOPEN.getCode());

                        needInsertDFList.add(df);

                        tempFieldAlias.add(df.getAlias());
                    }else {
                        unknowCount ++;
                    }
                }
                displayOrder ++;
            }
            tempFieldAlias.clear();
        }

        NptDataScanResult result = new NptDataScanResult();

        result.setUnknowCount(unknowCount);
        result.setInsertCount(needInsertDFList.size());
        result.setUpdateCount(needUpdateDFList.size());
        archService.batchSaveDataField(needInsertDFList);

        Set<String> namedDTKey = namedDFMap.keySet();
        for(String key:namedDTKey){
            NptLogicDataField extDF = namedDFMap.get(key);
            if(!needUpdateDFList.contains(extDF)){
                extDF.setStatus(NptDict.IDS_DELETED.getCode());
                needDeleteDFList.add(extDF);
            }
        }
        result.setDeletedCount(needDeleteDFList.size());
        archService.batchUpdateDataField(needDeleteDFList);

        return result;
    }
}
