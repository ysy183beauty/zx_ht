package com.npt.rms.arch.action;

import com.npt.bridge.arch.NptLogicDataType;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.rms.base.action.NptRMSAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Collection;

/**
 * 项目：NPTWebApp
 * 作者: owen
 * 日期：2016/9/27 20:11
 * 备注：
 */
@Controller("npt.rms.datatype")
public class NptDataTypeAction extends NptRMSAction<NptLogicDataType> {

    @Autowired
    private NptRmsArchService archService;

    private NptLogicDataType data;

    public NptLogicDataType getData() {
        return data;
    }

    public void setData(NptLogicDataType data) {
        this.data = data;
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:5
     * 备注: index
     */
    public String index() {
        return list();
    }

    /**
     * 作者: 张磊
     * 日期: 16/10/9 下午4:26
     * 备注: 显示数据类别列表
     */
    public String list() {
        Long parentId=getWebParentId();
        Collection<NptLogicDataType> dtList;
        if(null == parentId || NptCommonUtil.getDefaultParentId().equals(parentId)){
            dtList = archService.listAllDataType();
        }else {
            dtList = archService.listLinealDataType(parentId,null);
        }
        this.setAttribute(NptCommonUtil.getActionReturnedAttributeName(),dtList);
        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:7
     * 备注: 根据id删除数据类别
     */
    public void delete() {
        try {
            NptLogicDataType type = archService.findDataTypeById(getWebId());
            archService.delete(type);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:7
     * 备注: 添加数据类别页面
     */
    public String addPage() {
        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:7
     * 备注: 添加数据类别
     */
    public void add() {
        try {
            this.data.setTypeName(this.getParameter("data.typeName"));
            this.data.setTypeDbName(this.getParameter("data.typeDbName"));
            this.data.setKeyword(this.getParameter("data.keyword"));
            this.data.setCreatorId(getOperatorId());
            this.data.setCreateTime(new java.sql.Timestamp(new java.util.Date().getTime()));
            this.data.setStatus(this.getIntParameter("data.status"));
            this.data.setTypeCategory(this.getIntParameter("data.typeCategory"));
            this.data.setParentId(Long.parseLong(this.getParameter("data.orgId")));
            archService.save(this.data);
            this.outputSuccessResult();
        } catch (Exception e) {
            this.logger.warn("添加失败", e);
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        } finally {
        }
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:8
     * 备注: 编辑数据类别页面
     */
    public String editPage() {
        try {
            this.data = archService.findDataTypeById(getWebId());
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return ERROR;
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:8
     * 备注: 保存编辑数据类别
     */
    public void edit() {
        try {
            this.data = archService.findDataTypeById(getWebId());
            if(null != this.data){
                this.data.setAlias(this.getParameter("alias"));
                this.data.setStatus(this.getIntParameter("status"));
                archService.update(this.data);
                this.outputSuccessResult();
            }
        } catch (Exception e) {
            this.logger.warn("保存失败", e);
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        } finally {
        }
    }
}
