package com.npt.ces.cw.action;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.model.props.bean.NptBaseModelEx;
import com.npt.bridge.model.props.bean.NptBaseModelGroupEx;
import com.npt.bridge.model.props.bean.NptBaseModelPoolEx;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.ces.cw.anylize.NptCreditWarningAnalyzer;
import com.npt.ces.cw.entity.NptCWCountType;
import com.npt.ces.cw.entity.NptCWModelDmsProps;
import com.npt.ces.cw.entity.NptCWModelProps;
import com.npt.ces.cw.entity.NptCWModelSubDmsProps;
import com.npt.ces.cw.manager.NptCWCountTypeManager;
import com.npt.ces.cw.manager.NptCWModelDimensionPropsManager;
import com.npt.ces.cw.manager.NptCWModelPropsManager;
import com.npt.ces.cw.manager.NptCWModelSubDmsPropsManager;
import com.npt.ces.cw.service.NptCWModelService;
import com.npt.web.action.NptPaginationAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.summer.extend.orm.Condition;
import org.summer.extend.orm.condition.Conditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 项目: 〔大理〕征信
 * 作者: zhanglei
 * 日期: 2017/7/12
 * 备注: 信用预警模型属性编辑
 */
@Controller("npt.ces.model.cw")
public class NptCwModelAction extends NptPaginationAction {

    @Autowired
    private NptCWModelService cwModelService;

    @Autowired
    private NptCreditWarningAnalyzer cwAnalyzer;

    @Autowired
    private NptCWModelPropsManager modelPropsManager;
    @Autowired
    private NptCWModelDimensionPropsManager dimensionPropsManager;
    @Autowired
    private NptCWModelSubDmsPropsManager subDmsPropsManager;

    @Autowired
    private NptCWCountTypeManager countTypeManager;

    /**
     *  author: zhanglei
     *  date:   2017/07/13 19:43
     *  note:
     *          信用预警模型首页
     */
    public String index() {
        refreshModels();
        listCpt();
        return SUCCESS;
    }

    /**
     *  author: zhanglei
     *  date:   2017/07/13 19:44
     *  note:
     *          模型列表
     */
    public String refreshModels() {
        Collection<NptBaseModelEx> models = cwModelService.listModels();
        this.setAttribute("_MODEL_LIST", models);
        return SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/14 21:29
     * 描述:
     * 加载模型的所有分组
     */
    public String listModelGroups() {
        Long modelId = getLongParameter("modelId");
        Collection<NptBaseModelGroupEx> groupList = cwModelService.listGroups(modelId);
        this.setAttribute("_MODEL_GROUP_LIST", groupList);
        this.setAttribute("_THIS_MODEL_ID", modelId);
        return SUCCESS;
    }

    /**
     * 作者：OWEN
     * 时间：2016/11/14 15:29
     * 描述:
     * 配置指定分组的数据源
     */
    public String configBaseModelGroup() {
        Long groupId = getLongParameter("groupId");
        Collection<NptBaseModelPoolEx> poolList = cwModelService.listPools(groupId);
        this.setAttribute("_GROUP_POOL_LIST", poolList);
        this.setAttribute("_THIS_GROUP_ID", groupId);
        return SUCCESS;
    }

    /**
     *  author: zhanglei
     *  date:   2017/07/13 17:04
     *  note:
     *          进入编辑DimensionProps页面
     */
    public String editDimensionPropsPage() {
        Long id = this.getLongParameter("id");
        if (id != null) {
            this.dimensionProps = dimensionPropsManager.findById(id);
        } else {
            this.dimensionProps = new NptCWModelDmsProps();
        }
        return SUCCESS;
    }

    /**
     *  author: zhanglei
     *  date:   2017/07/13 17:04
     *  note:
     *          编辑DimensionProps
     */
    public void editDimensionProps() {
        try {
            if (this.dimensionProps.getId() == null) {
                dimensionPropsManager.save(this.dimensionProps);
            } else {
                dimensionPropsManager.updateDimensionProps(this.dimensionProps);
            }
            this.outputSuccessResult();
        } catch (Exception e) {
            this.logger.warn("保存失败", e);
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        }
    }

    /**
     *  author: zhanglei
     *  date:   2017/07/13 17:04
     *  note:
     *          进入编辑SubDmsProps页面
     */
    public String editSubDmsPropsPage() {
        Long id = this.getLongParameter("id");
        if (id != null) {
            this.subDmsProps = subDmsPropsManager.findById(id);
        } else {
            this.subDmsProps = new NptCWModelSubDmsProps();
        }

        // get ces cpm code list
        Collection<NptDict> cpmList = NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.CES_CPM);
        this.setAttribute("CES_CPM_LIST", cpmList);

        // get ces mu code list
        Collection<NptDict> muList = NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.CES_MU);
        this.setAttribute("CES_MU_LIST", muList);

        // get pool field list
        Long poolId = this.getLongParameter("poolId");
        Collection<NptLogicDataField> dataTypeFields = cwModelService.listDataTypeFields(poolId);
        this.setAttribute("_POOL_FIELD_LIST", dataTypeFields);
        return SUCCESS;
    }

    /**
     *  author: zhanglei
     *  date:   2017/07/13 17:04
     *  note:
     *          编辑SubDmsProps
     */
    public void editSubDmsProps() {
        try {
            if (this.subDmsProps.getId() == null) {
                Long ukFieldId = cwModelService.getUkFieldId(this.subDmsProps.getPoolId());
                this.subDmsProps.setuFieldId(ukFieldId);
                subDmsPropsManager.save(this.subDmsProps);
            } else {
                subDmsPropsManager.updateSubDmsProps(this.subDmsProps);
            }
            this.outputSuccessResult();
        } catch (Exception e) {
            this.logger.warn("保存失败", e);
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        }
    }

    /**
     *  author: zhanglei
     *  date:   2017/07/13 17:04
     *  note:
     *          进入编辑ModelProps页面
     */
    public String editModelPropsPage() {
        Long id = this.getLongParameter("id");
        if (id != null) {
            this.modelProps = modelPropsManager.findById(id);
        } else {
            this.modelProps = new NptCWModelProps();
        }
        return SUCCESS;
    }

    /**
     *  author: zhanglei
     *  date:   2017/07/13 17:04
     *  note:
     *          编辑ModelProps
     */
    public void editModelProps() {
        try {
            if (this.modelProps.getId() == null) {
                modelPropsManager.save(this.modelProps);
            } else {
                modelPropsManager.updateModelProps(this.modelProps);
            }
            this.outputSuccessResult();
        } catch (Exception e) {
            this.logger.warn("保存失败", e);
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        }
    }

    /**
     *  author: zhanglei
     *  date:   2017/07/14 17:40
     *  note:
     *          数据预处理
     */
    public void preTreat() {
        try {
            Long modelId = this.getLongParameter("id");

            NptDict rst = cwAnalyzer.analyze(modelId);

            this.outputSuccessResult(rst.getTitle());
        } catch (Exception e) {
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        }
    }

    public String listCpt() {
        Collection<Condition> conditions = new ArrayList<>();
        Integer computeType = this.getIntParameter("computeType");
        if (computeType != null) {
            conditions.add(Conditions.eq(NptCWCountType.PROPERTY_COMPUTE_TYPE, computeType));
        }
        conditions.add(Conditions.eq(NptCWCountType.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()));
        List<NptCWCountType> cptTypes = countTypeManager.findByCondition(conditions.toArray(new Condition[]{}));
        this.setAttribute("cptTypes", cptTypes);

        // get ces cpm code list
        Collection<NptDict> cpmList = NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.CES_CPM);
        this.setAttribute("CES_CPM_LIST", cpmList);
        return SUCCESS;
    }

    public String addCptPage() {
        this.countType = new NptCWCountType();
        // get ces cpm code list
        Collection<NptDict> cpmList = NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.CES_CPM);
        this.setAttribute("CES_CPM_LIST", cpmList);
        return SUCCESS;
    }

    public void addCpt() {
        try {
            countTypeManager.save(this.countType);
            this.outputSuccessResult();
        } catch (Exception e) {
            this.logger.warn("保存失败", e);
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        }
    }

    public String editCptPage() {
        Long id = this.getLongParameter("id");
        if (id != null) {
            this.countType = countTypeManager.findById(id);
        }
        // get ces cpm code list
        Collection<NptDict> cpmList = NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.CES_CPM);
        this.setAttribute("CES_CPM_LIST", cpmList);
        return SUCCESS;
    }

    public void editCpt() {
        try {
            countTypeManager.updateCountType(this.countType);
            this.outputSuccessResult();
        } catch (Exception e) {
            this.logger.warn("保存失败", e);
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        }
    }

    public void deleteCpt() {
        try {
            Long id = this.getLongParameter("id");
            countTypeManager.deleteById(id);
            this.outputSuccessResult();
        } catch (Exception e) {
            this.logger.warn("删除失败", e);
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        }
    }

    private NptCWModelProps modelProps;
    private NptCWModelDmsProps dimensionProps;
    private NptCWModelSubDmsProps subDmsProps;
    private NptCWCountType countType;

    public NptCWModelProps getModelProps() {
        return modelProps;
    }

    public void setModelProps(NptCWModelProps modelProps) {
        this.modelProps = modelProps;
    }

    public NptCWModelDmsProps getDimensionProps() {
        return dimensionProps;
    }

    public void setDimensionProps(NptCWModelDmsProps dimensionProps) {
        this.dimensionProps = dimensionProps;
    }

    public NptCWModelSubDmsProps getSubDmsProps() {
        return subDmsProps;
    }

    public void setSubDmsProps(NptCWModelSubDmsProps subDmsProps) {
        this.subDmsProps = subDmsProps;
    }

    public NptCWCountType getCountType() {
        return countType;
    }

    public void setCountType(NptCWCountType countType) {
        this.countType = countType;
    }
}
