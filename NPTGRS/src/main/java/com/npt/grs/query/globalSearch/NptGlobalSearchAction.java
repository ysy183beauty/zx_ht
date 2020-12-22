package com.npt.grs.query.globalSearch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.npt.bridge.arch.NptLogicDataField;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.dict.NptDict;
import com.npt.grs.query.globalSearch.manager.NptGlobalSearchManager;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.rms.auth.bean.NptGlobalSearchBean;
import com.npt.rms.base.action.NptRMSAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/11/2 17:09
 * 备注：
 */
@Controller("npt.grs.globalSearch")
public class NptGlobalSearchAction extends NptRMSAction<NptGlobalSearchBean> {

    public static final String webConditionName = "globalSearchBean";


    @Resource(name = "rmsArchService")
    private NptRmsArchService rmsArchService;
    @Autowired
    private NptGlobalSearchManager searchManager;


    public NptGlobalSearchBean getSearchConditions() {
        String webStr = getParameter(NptGlobalSearchAction.webConditionName);
        if (null != webStr) {
            JSONObject object = JSON.parseObject(webStr);

            NptGlobalSearchBean bean = JSONObject.toJavaObject(object, NptGlobalSearchBean.class);
            return bean;
        }
        return null;
    }

    /**
     * 作者：97175
     * 日期：2016/11/7 18:27
     * 备注：
     * 初始页面
     * 参数：
     * 返回：
     */
    public String globalSearch() {
        return SUCCESS;
    }

    /**
     * 作者：97175
     * 日期：2016/11/7 18:27
     * 备注：
     * 依据条件的结果列表
     * 参数：
     * 返回：
     */
    public String resultList() {

        String pkValue = getPrimaryKeyValue();
        String fromField = getParameter("fromField");
        String toField = getParameter("toField");

        Collection<NptGlobalSearchBean> result = searchManager.search(pkValue, fromField, toField);

        this.setAttribute("globalSearchResult", result);

        return SUCCESS;
    }

    /**
     * 作者：97175
     * 日期：2016/11/7 18:27
     * 备注：
     * 提出异议
     * 参数：
     * 返回：
     */
    public String showAppeal() {

        NptGlobalSearchBean bean = getSearchConditions();

        if (null != bean) {
            NptLogicDataType dataType = rmsArchService.fastFindDataTypeById(bean.getDataTypeId());
            if (null != dataType) {
                NptLogicDataProvider org = rmsArchService.fastFindOrgById(dataType.getParentId());
                Collection<NptLogicDataField> fields = rmsArchService.listDataField(bean.getDataTypeId(),null, NptDict.IDS_ENABLED);
                Collection<NptLogicDataField> orderedFields = new ArrayList<>();

                NptLogicDataField pkField = null;
                for (NptLogicDataField field : fields) {
                    if (field.getId().equals(bean.getPkFieldId())) {
                        pkField = field;
                    }
                }

                if (null != pkField) {
                    Object rowData = searchManager.queryUniqueData(dataType, pkField, bean.getPkValue());
                    if (null != rowData) {
                        Map<String, Object> queryMap = (Map<String, Object>) rowData;
                        pkField.setFieldValue(queryMap.get(pkField.getFieldDbName()));
                        orderedFields.add(pkField);

                        for (NptLogicDataField field : fields) {
                            if (!field.getId().equals(pkField.getId())) {
                                field.setFieldValue(queryMap.get(field.getFieldDbName()));
                                orderedFields.add(field);
                            }
                        }

                        this.setAttribute("key",bean.getPkValue());
                        this.setAttribute("dataTypeInfo", dataType);
                        this.setAttribute("orgInfo", org);
                        this.setAttribute("fieldList", orderedFields);
                        this.setAttribute("byPKField",pkField);
                    }
                }
            } else {
                this.outputErrorResult(NptDict.RST_INVALID_PARAMS.getTitle());
            }
        } else {
            this.outputErrorResult(NptDict.RST_INVALID_PARAMS.getTitle());
        }

        return SUCCESS;
    }
}
