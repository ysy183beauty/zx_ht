package com.npt.grs.model.action;

import com.alibaba.fastjson.JSONObject;
import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.*;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.grs.model.service.NptGRSBaseModelService;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.rms.base.action.NptRMSAction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.summer.extend.orm.condition.Conditions;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 项目：NPTWebApp
 * 作者: 97175
 * 日期：2016/10/11 13:49
 * 备注：
 */
@Controller("npt.grs.model")
public class NptBaseModelAction extends NptRMSAction {

    @Autowired
    private NptGRSBaseModelService baseModeService;

    @Resource(name = "rmsArchService")
    private NptRmsArchService rmsArchService;

    private NptBaseModelPool pool;

    public NptBaseModelPool getPool() {
        return pool;
    }

    public void setPool(NptBaseModelPool pool) {
        this.pool = pool;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/13 13:33
     * 描述:
     * 加载基础模型列表
     */
    public String index() {
        return refreshModels();
    }


    /**
     * 作者：OWEN
     * 时间：2016/11/14 20:45
     * 描述:
     */
    public String refreshModels() {
        Collection<NptBaseModel> models = baseModeService.listModels();
        if (null != models) {
            for (NptBaseModel m : models) {
                NptDict dict = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.BMH, m.getHostId());
                NptDict result = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.BMC, m.getCateId());
                if (null != dict) m.setHostTitle(dict.getTitle());
                if (null != result) m.setCateTitle(result.getTitle());
            }
        }
        this.setAttribute("_MODEL_LIST", models);
        return SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/13 12:40
     * 描述:
     */
    public String addModel() {
        Collection<NptDict> hostList = NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.BMH);
        Collection<NptDict> cateList = NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.BMC);
        this.setAttribute("_MODEL_HOST_LIST", hostList);
        this.setAttribute("_MODEL_CATE_LIST", cateList);
        return SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/13 12:51
     * 描述:
     * 添加模型基础信息
     * <p>
     * 系统原生模型，每个信息实体只能存在一个
     */
    public void addModelBaseInfo() {
        String modelName = getParameter("modelName");
        String modelNote = getParameter("modelNote");
        Integer modelHost = NptDict.valueOf(getParameter("modelHost")).getCode();
        Integer modelCatelog = NptDict.valueOf(getParameter("modelCatelog")).getCode();

        NptBaseModel model = new NptBaseModel();
        model.setModelName(modelName);
        model.setModelNote(modelNote);
        model.setHostId(modelHost);
        model.setCateId(modelCatelog);

        NptDict result = baseModeService.addBaseModelBasicInfo(model);
        if (result == NptDict.RST_SUCCESS) {
            this.outputSuccessResult(NptDict.RST_SUCCESS.getTitle());
        } else {
            this.outputErrorResult(result.getTitle());
        }
    }

    /**
     * 作者: xuqinyuan
     * 时间: 2016/12/5 11:40
     * 备注: 修改模型基本信息
     */
    public void editModel() {
        Long modelId = getLongParameter("modelId");
        String modelName = getParameter("modelName");
        Integer status = getIntParameter("status");

        NptBaseModel model = baseModeService.findBaseModelById(modelId);
        if (null != model) {
            if (!modelName.equals(model.getModelName())) {
                NptDict result = baseModeService.checkBaseModel(modelName);
                if (result == NptDict.RST_SUCCESS) {
                    model.setModelName(modelName);
                    model.setStatus(status);
                    baseModeService.update(model);

                    this.outputSuccessResult(NptDict.RST_SUCCESS.getTitle());
                } else {
                    this.outputErrorResult(result.getTitle());
                }
            } else {
                model.setStatus(status);
                baseModeService.update(model);

                this.outputSuccessResult(NptDict.RST_SUCCESS.getTitle());
            }
        } else {
            this.outputErrorResult(NptDict.RST_ERROR.getTitle());
        }
    }

    /**
     * 作者: xuqinyuan
     * 时间: 2016/12/5 11:40
     * 备注: 修改分组基本信息
     */
    public void editGroup() {
        Long modelId = getLongParameter("modelId");
        Long groupId = getLongParameter("groupId");
        String groupName = getParameter("groupName");
        String groupNote = getParameter("groupNote");
        Integer status = getIntParameter("status");

        NptBaseModelGroup group = baseModeService.findBaseModelGroupById(groupId);
        if (null != group) {
            if (!groupName.equals(group.getGroupName())) {
                NptDict result = baseModeService.checkBaseModelGroup(modelId, groupName);
                if (result == NptDict.RST_SUCCESS) {
                    group.setGroupName(groupName);
                    group.setGroupNote(groupNote);
                    group.setGroupTitle(groupName);
                    group.setStatus(status);

                    baseModeService.update(group);
                    this.outputSuccessResult(NptDict.RST_SUCCESS.getTitle());
                } else {
                    this.outputErrorResult(result.getTitle());
                }
            } else {
                group.setGroupNote(groupNote);
                group.setStatus(status);

                baseModeService.update(group);
                this.outputSuccessResult(NptDict.RST_SUCCESS.getTitle());
            }
        } else {
            this.outputErrorResult(NptDict.RST_ERROR.getTitle());
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/14 21:29
     * 描述:
     * 加载模型的所有分组
     */
    public String listModelGroups() {
        Long modelId = getLongParameter("modelId");
        NptBaseModel model = baseModeService.findBaseModelById(modelId);
        if (null != model) {
            Collection<NptBaseModelGroup> result = baseModeService.getBaseModelGroups(model);
            this.setAttribute("_MODEL_GROUP_LIST", result);
            this.setAttribute("_THIS_MODEL_ID", model.getId());
        }
        return SUCCESS;
    }

    /**
     * 作者：jss
     * 时间：2018年3月16日09:16:28
     * 描述:
     * 加载当前模型分组下面的模型和数据池
     */
    public String getModalGroupsAndPool() {
        Long modelId = getLongParameter("modelId");
        if (null != modelId) {
            Collection<NptBaseModel> all = baseModeService.listModels();
            HashMap<String,Object> modalMap=new HashMap<>();
            HashMap<String,Object> baseModalMap=new HashMap<>();
            HashMap<String,Object> poolMap=new HashMap<>();
            for (NptBaseModel bm:all){
                Collection<NptBaseModelGroup> groups = baseModeService.getBaseModelGroups(bm);
                baseModalMap.put(bm.getId().toString(),bm);
                modalMap.put(bm.getId().toString(),groups);
                for (NptBaseModelGroup bmg:groups){
                    Collection<NptBaseModelPool> poolList = baseModeService.getBaseModelGrouPools(bmg,null,true);
                    poolMap.put(bmg.getId().toString(),poolList);
                }
            }

            Object nbm=baseModalMap.get(modelId.toString());
            Object nbmg=modalMap.get(modelId.toString());
            Object nbmgp=poolMap.get(((List<NptBaseModelGroup>)modalMap.get(modelId.toString())).get(0).getId().toString());
            this.setAttribute("_BASE_MODEL_LIST", baseModalMap);
            this.setAttribute("_MODEL_GROUP_LIST", modalMap);
            this.setAttribute("_THIS_MODEL_ID", modelId);
            this.setAttribute("_BASE_ACTIVE_MODAL", nbm);
            this.setAttribute("_BASE_ACTIVE_MODAL_GROUP", nbmg);
            this.setAttribute("_BASE_ACTIVE_MODAL_GROUP_POOL", nbmgp);
            this.setAttribute("_MODEL_GROUP_POOL_LIST",poolMap);
        }
        return SUCCESS;
    }

    /**
     * 作者：jss
     * 时间：2018年3月16日09:16:28
     * 描述:
     * 复制模型的数据池配置
     */
    public void copyModalGroupsPool() {
        String[] groupPoolId = getParameter("groupPoolId").split("@_@");
        Long targetGroupId = getLongParameter("targetGroupId");
        for (int i = 0; i < groupPoolId.length; i++) {

            NptBaseModelPool pool = baseModeService.findBaseModelGrouPoolById(Long.parseLong(groupPoolId[i]));
            if (null != pool) {
                //查询条件
                Collection<NptBaseModelPoolCdt> conditions = baseModeService.getBaseModelPoolConditions(pool);
                List<Long> pConFieldIds=new ArrayList<>();
                for (NptBaseModelPoolCdt condition:conditions){
                    pConFieldIds.add(condition.getFieldId());
                }

                //索引字段
                Collection<NptBaseModelPoolIndex> indexs = baseModeService.getBaseModelPoolIndex(pool);
                List<Long> pIndexFieldIds=new ArrayList<>();
                for (NptBaseModelPoolIndex index:indexs){
                    pIndexFieldIds.add(index.getFieldId());
                }

                NptDict poolTypeDict = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.CLD, pool.getMainPool());

                NptDict result = baseModeService.addPoolToBaseModelGroup(targetGroupId, pool.getDataTypeId(), pool.getPrimaryFieldId(), pIndexFieldIds, poolTypeDict, pConFieldIds, pool);
                if (!NptDict.RST_SUCCESS.equals(result)) {
                    this.outputErrorResult(result.getTitle());
                }
            }

        }

        return ;
    }

    /**
     * 作者: xuqinyuan
     * 时间: 2016/11/14 22:04
     * 备注:打开新增分组页面
     */
    public String addGroupPage() {
        String modelId = getParameter("modelId");
        this.setAttribute("_THIS_MODEL_ID", modelId);
        return SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/13 14:41
     * 描述:
     * 为模型添加分组，默认都不是主分组
     */
    public String addModelGroup() {
        Long modeId = getWebParentId();
        NptBaseModel model = baseModeService.findBaseModelById(modeId);

        if (null == model) {
            this.outputErrorResult("目标模型不存在!");
        } else if (model.getHostId() > NptCommonUtil.BMH_SPECIAL_MIN) {
            this.outputErrorResult("特殊的模型主体已添加标准的分组，不允许添加自定义的分组!");
        } else {

            String groupName = getParameter("groupName");
            String groupNote = getParameter("groupNote");

            NptBaseModelGroup group = new NptBaseModelGroup();
            group.setGroupName(groupName);
            group.setGroupTitle(groupName);
            group.setGroupNote(groupNote);

            NptDict result = baseModeService.addGroupToBaseModel(modeId, group);

            if (result == NptDict.RST_SUCCESS) {
                Collection<NptBaseModelGroup> groupList = baseModeService.getBaseModelGroups(model);
                this.setAttribute("_MODEL_GROUP_LIST", groupList);
            } else {
                this.outputErrorResult(result.getTitle());
            }
        }
        return SUCCESS;
    }


    /**
     * 作者：OWEN
     * 时间：2016/11/13 20:36
     * 描述:
     * 修改指定模型的指定分组的状态，可使用此方法实现分组的逻辑删除
     */
    public void setModelGroupStatus() {
        Long modelId = getLongParameter("modelId");
        Long groupId = getLongParameter("groupId");
        Integer status = getIntParameter("groupStatus");

        NptDict status_dict = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.IDS, status);
        NptDict result = baseModeService.setBaseModelGroupStatus(modelId, groupId, status_dict);
        if (NptDict.RST_SUCCESS == result) {
            this.outputSuccessResult(NptDict.RST_SUCCESS.getTitle());
        } else {
            this.outputErrorResult(result.getTitle());
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/12/6 11:06
     * 描述:
     * 设置模型的状态，可借此方法进行逻辑删除
     */
    public void setModelStatus() {
        Long modelId = getLongParameter("modelId");
        Integer status = getIntParameter("modelStatus");

        NptDict status_dict = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.IDS, status);

        NptDict result = baseModeService.setBaseModelStatus(modelId, status_dict);
        if (NptDict.RST_SUCCESS == result) {
            this.outputSuccessResult(result.getTitle());
        } else {
            this.outputErrorResult(result.getTitle());
        }
    }

    /**
     * 作者: xuqinyuan
     * 时间: 2016/11/14 23:12
     * 备注:打开新增数据池页面
     */
    public String addPoolPage() {
        Collection<NptLogicDataProvider> orgList = rmsArchService.listAllOrg();
        this.setAttribute("_ORG_LIST", orgList);
        String groupId = getParameter("groupId");
        this.setAttribute("_THIS_GROUP_ID", groupId);
        this.setAttribute("_POOL_LOCKLEVELS", NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.MPL));
        this.setAttribute("_POOL_DATAHOSTS", NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.PDM));
        return SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/13 16:22
     * 描述:
     * 为模型设置主数据池
     */
    public void setGroupMainPool() {
        Long poolId = getLongParameter("poolId");

        NptDict result = baseModeService.setBaseModelGroupMainPool(poolId);
        if (result == NptDict.RST_SUCCESS) {
            this.outputSuccessResult(NptDict.RST_SUCCESS.getTitle());
        } else {
            this.outputErrorResult(result.getTitle());
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/14 15:29
     * 描述:
     * 配置指定分组的数据源
     */
    public String configBaseModelGroup() {
        Long groupId = getLongParameter("groupId");
        NptBaseModelGroup group = baseModeService.findBaseModelGroupById(groupId);
        if (null != group) {
            Collection<NptBaseModelPool> poolList = baseModeService.getBaseModelGrouPools(group,null,true);
            this.setAttribute("_GROUP_POOL_LIST", poolList);
            this.setAttribute("_THIS_GROUP_ID", groupId);
        }
        return SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/14 15:37
     * 描述:
     * 配置指定分组数据源的详细信息
     */
    public String configBaseModelGroupool() {
        Long poolId = getLongParameter("poolId");
        NptBaseModelPool pool = baseModeService.findBaseModelGrouPoolById(poolId);
        if (null != pool) {
            NptLogicDataType dataType = rmsArchService.findDataTypeById(pool.getDataTypeId());
            NptLogicDataProvider provider = rmsArchService.findParent(dataType);
            Collection<NptLogicDataProvider> orgList = rmsArchService.listAllOrg();
            Collection<NptLogicDataType> orgTypeList = new ArrayList<>();
            rmsArchService.listAvailableDataTypeForBaseModel(provider, orgTypeList);

            NptLogicDataField pkField = rmsArchService.findDataFieldById(pool.getPrimaryFieldId());

            Collection<NptLogicDataField> dataTypeFields = rmsArchService.listDataField(dataType.getId(), null, NptDict.IDS_ENABLED);

            this.setAttribute("_POOL_FIELD_LIST", dataTypeFields);

            Collection<NptBaseModelPoolCdt> conditions = baseModeService.getBaseModelPoolConditions(pool);
            this.setAttribute("_POOL_CONDITIONS", conditions);

            Collection<NptBaseModelPoolIndex> indexs = baseModeService.getBaseModelPoolIndex(pool);
            this.setAttribute("_POOL_INDEXS",indexs);

            this.setAttribute("_POOL_LOCKLEVELS", NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.MPL));
            this.setAttribute("_POOL_DATAHOSTS", NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.PDM));

            this.setAttribute("_POOL_INFO", pool);
            this.setAttribute("_POOL_ORG", provider);
            this.setAttribute("_ORG_LIST", orgList);
            this.setAttribute("_POOL_DATA_TYPE", dataType);
            this.setAttribute("_ORG_DATA_TYPE_LIST", orgTypeList);
            this.setAttribute("_POOL_PK_FIELD", pkField);
        }
        return SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/13 16:35
     * 描述:
     * 加载指定机构的数据类别
     */
    public void listOrgDataTypes() {
        Long orgId = getWebParentId();
        NptLogicDataProvider org = rmsArchService.findOrgById(orgId);
        if (null != org) {
            Collection<NptLogicDataType> typeList = new ArrayList<>();
            rmsArchService.listAvailableDataTypeForBaseModel(org, typeList);
            this.ajaxOutPutJson(JSONObject.toJSON(typeList));
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/13 20:12
     * 描述:
     * 加载指定类别的字段
     */
    public String listDataTypeFields() {
        Long typeId = getWebParentId();
        NptLogicDataType type = rmsArchService.findDataTypeById(typeId);
        if (null != type) {
            Collection<NptLogicDataField> fieldList = rmsArchService.listDataField(typeId, null, NptDict.IDS_ENABLED);
            this.setAttribute("_DATATYPE_FIELD_LIST", fieldList);
        }
        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/11 下午03:25
     * 备注:
     */
    public String listDataTypeFieldsFree() {
        return listDataTypeFields();
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/13 20:13
     * 描述:
     * 添加分组的数据池
     */
    public String addGroupool() {
        Long groupId = getLongParameter("groupId");
        Long dataTypeId = getLongParameter("dataTypeId");
        Long pFieldId = getLongParameter("pFieldId");
        String poolType = getParameter("poolType");

        String cdtFields = getParameter("paramCdtFields");
        String idxFields = getParameter("paramIdxFields");
        String groupPoolPage=getParameter("groupPoolPage");
        //索引字段默认为业务主键
        if (StringUtils.isEmpty(idxFields)) {
            idxFields = String.valueOf(pFieldId);
        }

        List<Long> pConFieldIds = NptCommonUtil.getOrderObjectArrayBySeprator(cdtFields,",");
        List<Long> pIndexFieldIds = NptCommonUtil.getOrderObjectArrayBySeprator(idxFields,",");

        NptDict poolTypeDict = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.CLD, poolType);

        NptDict result = baseModeService.addPoolToBaseModelGroup(groupId, dataTypeId, pFieldId, pIndexFieldIds, poolTypeDict, pConFieldIds, pool);
        if (NptDict.RST_SUCCESS.equals(result)) {
            NptBaseModelGroup group = baseModeService.findBaseModelGroupById(groupId);
            Collection<NptBaseModelPool> poolList = baseModeService.getBaseModelGrouPools(group,NptDict.IDS_ENABLED,true);
            this.setAttribute("_GROUP_POOL_LIST", poolList);
            this.setAttribute("_groupPoolPage",groupPoolPage);
        } else {
            this.outputErrorResult(result.getTitle());
        }
        return SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/16 17:14
     * 描述:
     * 更新数据池
     */
    public void updateGroupool() {
        Long poolId = getLongParameter("poolId");
        Long dataTypeId = getLongParameter("dataTypeId");
        Long pFieldId = getLongParameter("pFieldId");
        String poolType = getParameter("poolType");
        String cdtFields = getParameter("paramCdtFields");
        String idxFields = getParameter("paramIdxFields");
        //索引字段默认为业务主键
        if (StringUtils.isEmpty(idxFields)) {
            idxFields = String.valueOf(pFieldId);
        }

        List<Long> pConFieldIds = NptCommonUtil.getOrderObjectArrayBySeprator(cdtFields, ",");
        List<Long> pIndexFieldIds = NptCommonUtil.getOrderObjectArrayBySeprator(idxFields,",");

        NptDict poolTypeDict = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.CLD, poolType);

        NptDict result =
                baseModeService.updatePoolToBaseModelGroup(poolId, dataTypeId, pFieldId, pIndexFieldIds, poolTypeDict, pConFieldIds, pool);
        if (NptDict.RST_SUCCESS.equals(result)) {
            this.outputSuccessResult(NptDict.RST_SUCCESS.getTitle());
        } else {
            this.outputErrorResult(result.getTitle());
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/13 20:37
     * 描述:
     * 删除指定分组的指定数据池
     */
    public void setModelGroupoolStatus() {
        Long groupId = getLongParameter("groupId");
        Long poolId = getLongParameter("poolId");
        Integer status = getIntParameter("poolStatus");

        NptDict status_dict = NptCommonUtil.getDict(NptCommonUtil.NPT_DICT_PREFIX.IDS, status);
        NptDict result = baseModeService.setBaseModeGroupoolStatus(groupId, poolId, status_dict);
        if (NptDict.RST_SUCCESS == result) {
            this.outputSuccessResult(NptDict.RST_SUCCESS.getTitle());
        } else {
            this.outputErrorResult(result.getTitle());
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/14 15:10
     * 描述:
     * 加载所有模型的所有数据池信息，以便进行模型关联的创建
     */
    public String listPossibleLinkablePools() {
        Long fromPoolId = getLongParameter("fromPoolId");
        Long fkFieldId = getLongParameter("fkFieldId");

        Collection<NptBaseModelLink> p2pList = baseModeService.getBaseModelGroupoolLinkedPools(fromPoolId, fkFieldId);
        Map<Long, NptBaseModelLink> linkedId = new HashMap<>();
        if (null != p2pList && !p2pList.isEmpty()) {
            for (NptBaseModelLink p : p2pList) {
                linkedId.put(p.getToPoolId(), p);
            }
        }
        //确定选中状态
        Collection<NptBaseModelPool> poolList = baseModeService.getAllPossibleGroupools(fromPoolId,fkFieldId);
        List<NptBaseModelPool> pools=new ArrayList<NptBaseModelPool>() ;

        if (null != poolList && !poolList.isEmpty()) {

            for (NptBaseModelPool p : poolList) {
                if (linkedId.keySet().contains(p.getId())) {
                    p.setbFlag(true);
                    p.setLinkTitle(linkedId.get(p.getId()).getLinkTitle());
                    pools.add(p);
                } else {
                    p.setbFlag(false);
                }
            }
            //去掉数据类重复的数据池
            for (NptBaseModelPool p : poolList) {
                boolean isHas=false;
                for (NptBaseModelPool pool : pools){
                    if(p.getDataTypeId()==pool.getDataTypeId()){
                        isHas=true;
                    }
                }
                if(!isHas){
                    pools.add(p);
                }
            }
        }
        this.setAttribute("_POOL_ID", fromPoolId);
        this.setAttribute("_FK_FIELD_ID", fkFieldId);
        this.setAttribute("_ALL_POOL_LIST", pools);
        return SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/16 21:29
     * 描述:
     * 加载指定数据池所有已存在的模型关联
     */
    public String listPoolLinks() {
        Long poolId = getLongParameter("poolId");
        NptBaseModelPool pool = baseModeService.findBaseModelGrouPoolById(poolId);
        if (null != pool) {
            Collection<NptBaseModelLink> linkList = baseModeService.getBaseModelGroupoolLinkedPools(pool, null);
            this.setAttribute("_POOL_LINK_LIST", linkList);
        }
        return SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/16 22:51
     * 描述:
     * 设置模型关联的状态，以启用或禁用
     */
    public void setPoolLinkStatus() {
        Long linkId = getLongParameter("poolLinkId");
        String status = getParameter("newStatus");

        NptDict result = baseModeService.setBaseModelGroupoolLinkStatus(linkId, status);
        if (NptDict.RST_SUCCESS == result) {
            this.outputSuccessResult(NptDict.RST_SUCCESS.getTitle());
        } else {
            this.outputErrorResult(result.getTitle());
        }
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/12 上午10:36
     * 备注: 删除poolLink
     */
    public void deletePoolLink() {
        try {
            Long linkId = getLongParameter("poolLinkId");
            baseModeService.deleteBaseModelGroupoolLink(linkId);
            this.outputSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            this.outputErrorResult("删除失败");
        }
    }


    /**
     * 作者：OWEN
     * 时间：2016/11/14 15:08
     * 描述:
     * 添加数据池的模型链接，成功之后会返回数据池的所有模型关联关系
     */
    public String addGroupoolLink() {
        Long fromPoolId = getLongParameter("fromPoolId");
        String[] toPoolIds = getParameterValues("toPoolId[]");
        Long fkFieldId = getLongParameter("fkFieldId");
        Long toKeyId = getLongParameter("toKeyId");
        String[] linkTitles = getParameterValues("linkTitle[]");

        if (toPoolIds == null || linkTitles == null || toPoolIds.length != linkTitles.length) {
            this.setAttribute("_ERROR_RESULT", "参数个数不匹配");
            return SUCCESS;
        }

        NptBaseModelPool fromPool = baseModeService.findBaseModelGrouPoolById(fromPoolId);
        NptLogicDataField fkField = rmsArchService.findDataFieldById(fkFieldId);

        Collection<NptDict> result = new ArrayList<>();
        for (int i = 0; i < toPoolIds.length; i++) {
            String pId = toPoolIds[i];
            NptBaseModelPool toPool = baseModeService.findBaseModelGrouPoolById(Long.valueOf(pId));
            if (null != toPool) {
                if (toKeyId == null) {
                    toKeyId = toPool.getPrimaryFieldId();
                }
                result.add(baseModeService.addBaseModelGroupoolLink(fromPool, toPool, fkField, toKeyId, linkTitles[i]));
            }
        }

        if (result.contains(NptDict.RST_SUCCESS)) {
            NptBaseModelGroup group = baseModeService.findBaseModelGroupById(fromPool.getGroupId());
            Collection<NptBaseModelPool> poolList = baseModeService.getBaseModelGrouPools(group,NptDict.IDS_ENABLED,true);
            this.setAttribute("_GROUP_POOL_LIST", poolList);
        } else {
            this.setAttribute("_ERROR_RESULT", result.iterator().next().getTitle());
        }
        return SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/12/7 16:45
     * 描述:
     * 删除模型
     */
    public void deleteModel() {
        Long modelId = getLongParameter("modelId");
        NptDict result = baseModeService.deleteBaseModelById(modelId);
        if (NptDict.RST_SUCCESS.equals(result)) {
            this.outputSuccessResult(NptDict.RST_SUCCESS.getTitle());
        } else {
            this.outputErrorResult(result.getTitle());
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/12/7 16:45
     * 描述:
     * 删除模型分组
     */
    public void deleteModelGroup() {
        Long groupId = getLongParameter("groupId");
        NptDict result = baseModeService.deleteBaseModelGroupById(groupId);
        if (NptDict.RST_SUCCESS.equals(result)) {
            this.outputSuccessResult(NptDict.RST_SUCCESS.getTitle());
        } else {
            this.outputErrorResult(result.getTitle());
        }
    }

    /**
     * 作者：OWEN
     * 时间：2016/12/7 16:47
     * 描述:
     * 删除模型数据池
     */
    public void deleteModelGrouPool() {
        Long poolId = getLongParameter("poolId");
        NptDict result = baseModeService.deleteBaseModelGrouPoolById(poolId);
        if (NptDict.RST_SUCCESS.equals(result)) {
            this.outputSuccessResult(NptDict.RST_SUCCESS.getTitle());
        } else {
            this.outputErrorResult(result.getTitle());
        }
    }

    /**
     * 作者：xuqinyuan
     * 时间：2017/1/18 15:35
     * 备注：分组排序
     */
    public void sortGroup() {
        try {
            String orderGroups = getParameter("sortedIdList");
            List<Long> orderGroupArray = NptCommonUtil.getOrderObjectArrayBySeprator(orderGroups, ",");

            baseModeService.updateGroupDisplayOrder(orderGroupArray);

            this.outputSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        }
    }

    /**
     * 作者：xuqinyuan
     * 时间：2017/1/18 15:36
     * 备注：数据池排序
     */
    public void sortGrouPool() {
        try {
            String orderGrouPools = getParameter("sortedIdList");
            List<Long> orderGrouPoolArray = NptCommonUtil.getOrderObjectArrayBySeprator(orderGrouPools, ",");

            baseModeService.updateGrouPoolDisplayOrder(orderGrouPoolArray);

            this.outputSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        }
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/11 下午02:04
     * 备注: 加载图谱groups
     */
    public String listDSGroups() {
        this.setAttribute("_POOL_ID", this.getParameter("fromPoolId"));
        this.setAttribute("_FK_FIELD_ID", this.getParameter("fkFieldId"));
        Collection<NptBaseModel> nptBaseModels = baseModeService.listModels(NptDict.BMH_BMDS, NptDict.BMC_NATIVE);
        if (nptBaseModels.iterator().hasNext()) {
            NptBaseModel model = nptBaseModels.iterator().next();
            Collection<NptBaseModelGroup> result = baseModeService.getBaseModelGroups(model);
            this.setAttribute("_MODEL_GROUP_LIST", result);
            this.setAttribute("_THIS_MODEL_ID", model.getId());
        }
        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/11 下午05:39
     * 备注: 根据groupId加载pools
     */
    public String listDSPools() {
        return configBaseModelGroup();
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/11 下午05:44
     * 备注: 根据poolId加载dataFields
     */
    public String listDSDataFields() {
        Long toPoolId = this.getLongParameter("toPoolId");
        Long fromPoolId = getLongParameter("fromPoolId");
        Long fkFieldId = getLongParameter("fkFieldId");

        Collection<NptBaseModelLink> p2pList = baseModeService.listBaseModelGroupoolLinkedPools(fromPoolId, fkFieldId, toPoolId);
        this.setAttribute("_LINK_LIST", p2pList);

        NptBaseModelPool pool = baseModeService.findBaseModelGrouPoolById(toPoolId);
        if (pool != null) {
            Collection<NptLogicDataField> dataFields = rmsArchService.listDataFieldByConditions(
                    Conditions.eq(NptBaseEntity.PROPERTY_PARENT_ID, pool.getDataTypeId()),
                    Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode())
            );
            this.setAttribute("_POOL_DATA_FIELD_LIST", dataFields);
        }
        return SUCCESS;
    }


    /**
     * 作者: 张磊
     * 日期: 2017/04/18 上午11:23
     * 备注: 添加poolLink
     */
    public String addDSGroupoolLink() {
        Long groupId = getLongParameter("groupId");
        Long fromPoolId = getLongParameter("fromPoolId");
        Long toPoolId = getLongParameter("toPoolId");
        Long fkFieldId = getLongParameter("fkFieldId");
        String[] toKeyIds = getParameterValues("toKeyId[]");
        String[] linkTitles = getParameterValues("linkTitle[]");
        String[] relFieldIds = getParameterValues("relLinkFieldId[]");

        if (toKeyIds == null || linkTitles == null || linkTitles.length != toKeyIds.length) {
            this.setAttribute("_ERROR_RESULT", "参数个数不匹配");
            return SUCCESS;
        }
        NptBaseModelPool fromPool = baseModeService.findBaseModelGrouPoolById(fromPoolId);
        Collection<NptDict> result = new ArrayList<>();
        result.add(baseModeService.updateBaseModelGroupoolLink(
                fromPoolId,
                toPoolId,
                fkFieldId,
                Stream.of(toKeyIds).map(Long::valueOf).collect(Collectors.toList()),
                Stream.of(linkTitles).collect(Collectors.toList()),
                null == relFieldIds?null:Stream.of(relFieldIds).map(Long::valueOf).collect(Collectors.toList())));


        if (result.contains(NptDict.RST_SUCCESS)) {
            NptBaseModelGroup group = baseModeService.findBaseModelGroupById(fromPool.getGroupId());
            Collection<NptBaseModelPool> poolList = baseModeService.getBaseModelGrouPools(group,NptDict.IDS_ENABLED,true);
            this.setAttribute("_GROUP_POOL_LIST", poolList);
        } else {
            this.setAttribute("_ERROR_RESULT", result.iterator().next().getTitle());
        }
        return SUCCESS;
    }


    /**
     *  author: owen
     *  date:   2017/4/26 14:44
     *  note:
     *          优化模型的查询效率
     */
    public void optimizeQuery(){
        Long modelId = getLongParameter("modelId");

        baseModeService.optimizeQuery(modelId);
    }

    /**
     *  author: zhanglei
     *  date:   2017/05/04 20:05
     *  note:
     *          update pool note
     */
    public void updatePoolNote() {
        try {
            Long poolId = this.getLongParameter("poolId");
            String poolNote = this.getParameter("poolNote");
            NptBaseModelPool pool = baseModeService.findBaseModelGrouPoolById(poolId);
            pool.setPoolNote(poolNote);
            baseModeService.update(pool);
            this.outputSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            this.outputErrorResult(e.getClass().getSimpleName());
        }
    }
}
