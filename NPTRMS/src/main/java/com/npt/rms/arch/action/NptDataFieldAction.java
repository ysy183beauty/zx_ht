package com.npt.rms.arch.action;

import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.arch.manager.NptOrganizationManager;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.rms.base.action.NptRMSAction;
import com.npt.web.action.NptPaginationAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/27 20:11
 * 备注：
 */
@Controller("npt.rms.datafield")
public class NptDataFieldAction extends NptRMSAction<NptLogicDataField> {

    @Autowired
    private NptRmsArchService archService;

    @Autowired
    private NptOrganizationManager organManager;

    private NptLogicDataField data;

    public NptLogicDataField getData() {
        return data;
    }

    public void setData(NptLogicDataField data) {
        this.data = data;
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:2
     * 备注: index
     */
    public String index() {
        return list();
    }

    /**
     * 作者: 张磊
     * 日期: 16/10/9 下午4:27
     * 备注: 显示字段列表
     */
    public String list() {
        try {
            Long parentId = getWebParentId();
            Collection<NptLogicDataField> result = archService.listDataField(parentId,null,null);
            this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(),result);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return ERROR;
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:2
     * 备注: 根据id删除字段
     */
    public void delete() {
        try {
            NptLogicDataField field = archService.findDataFieldById(getWebId());
            archService.delete(field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:3
     * 备注: 添加字段页面
     */
    public String addPage() {
        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:3
     * 备注: 添加字段
     */
    public void add() {
        NptLogicDataField field = new NptLogicDataField();
        field.setFieldName(this.getParameter("data.fieldName"));
        field.setFieldDbName(this.getParameter("data.fieldDbName"));
        field.setCreatorId(getOperatorId());
        field.setCreateTime(NptCommonUtil.getCurrentSysDate());
        field.setStatus(this.getIntParameter("data.status"));
        field.setParentId(Long.parseLong(this.getParameter("data.asset")));
        archService.save(field);
        this.outputSuccessResult();
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:4
     * 备注: 编辑字段页面
     */
    public String editPage() {
        try {
            this.data = archService.fastFindDataFieldById(getWebId());
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return ERROR;
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/24 上午9:43
     * 备注: 字段批量编辑页面
     */
    public String batchUpdatePage() {
        try {
            Long[] ids = this.getLongParameterSplit("ids");
            Collection<NptLogicDataField> dataFields = archService.fastFindDataFieldById(Arrays.asList(ids));
            this.setAttribute("_DATA_FIELDS", dataFields);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return ERROR;
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/24 上午9:43
     * 备注: 字段批量编辑
     */
    public void batchUpdate() {
        try {
            Long[] ids = this.getLongParameterSplit("ids");
            List<NptLogicDataField> dataFields = new ArrayList<>();
            for (Long id : ids) {
                NptLogicDataField dataField = archService.findDataFieldById(id);

                // 略过无效id
                if (dataField == null) continue;

                dataField.setStatus(this.getIntParameter("data.status"));
                dataField.setPubLevel(this.getIntParameter("data.pubLevel"));
                dataField.setShowStyle(this.getParameter("data.showStyle"));

                // 将数据类别的数据主键设为启用状态
                NptLogicDataType parent = archService.findParent(dataField);
                if (parent != null && parent.getUkFieldId().equals(dataField.getId())) {
                    dataField.setStatus(NptDict.IDS_ENABLED.getCode());
                }

                dataFields.add(dataField);
            }
            archService.batchUpdateDataField(dataFields);
            this.outputSuccessResult();
        } catch (Exception e) {
            this.logger.warn("批量编辑失败", e);
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        } finally {
        }
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:4
     * 备注: 保存编辑字段
     */
    public void edit() {
        try {
            this.data = archService.findDataFieldById(getWebId());
            if(null != this.data){
                this.data.setStatus(this.getIntParameter("data.status"));
                this.data.setAlias(this.getParameter("data.alias"));
                this.data.setPubLevel(this.getIntParameter("data.pubLevel"));
                this.data.setShowStyle(this.getParameter("data.showStyle"));
                NptLogicDataType parent = archService.findParent(this.data);
                if(null == parent){
                    this.outputErrorResult("字段不属于任何一个数据类别");
                }else {
                    if(parent.getUkFieldId().equals(this.data.getId()) &&
                            !this.data.getStatus().equals(NptDict.IDS_ENABLED.getCode())){
                        this.outputErrorResult("数据类别的数据主键不允许被禁用,锁定或删除!");
                    }else {
                        archService.update(this.data);
                        this.outputSuccessResult();
                    }
                }
            }
        } catch (Exception e) {
            this.logger.warn("保存失败", e);
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        } finally {
        }
    }

    /**
     *作者：OWEN
     *时间：2016/11/25 17:14
     *描述:
     *      字段排序
     */
    public void sortField(){
        try {
            String orderFields = getParameter("sortedIdList");
            List<Long> orderFieldArray = NptCommonUtil.getOrderObjectArrayBySeprator(orderFields,",");

            archService.updateFieldDisplayOrder(orderFieldArray);

            this.outputSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        }
    }

    /**
     *作者：owen
     *时间：2016/12/15 13:48
     *描述:
     *      设置数据类别的数据主键
     */
    public void setDataTypeUField(){
        Long dataTypeId = getLongParameter("typeId");
        Long fieldId = getLongParameter("fieldId");

        NptDict result = archService.setDataTypeUField(dataTypeId,fieldId);
        if(NptDict.RST_SUCCESS.equals(result)){
            this.outputSuccessResult(result.getTitle());
        }else {
            this.outputErrorResult(result.getTitle());
        }
    }
    public void updateSelectCondition(){
        String typeId=this.getParameter("typeId");
        String fieldName=this.getParameter("fieldName");
        String fieldEngName=this.getParameter("fieldEngName");
        try {
            Integer modelId=organManager.selectCurrentModelId(NptDict.BMH_BSMAP);
            String sql="select * from NPT_BASEMODEL_GROUPOOL l where l.datatype_id="+typeId+" and l.model_id="+modelId+"";
            List<Map> list=this.organManager.selectpoolId(sql);
            if(list!=null&&list.size()>0){
                String updateSql="update NPT_DATA_FIELD set FIELD_DB_NAME='"+fieldEngName+"',FIELD_NAME='"+fieldName+"',OBJ_ALIAS='"+fieldName+"' where id="+Long.parseLong(list.get(0).get("PFIELD_ID").toString())+"";
                organManager.updateCondition(updateSql);
            }
            this.outputSuccessResult();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        }

    }
}
