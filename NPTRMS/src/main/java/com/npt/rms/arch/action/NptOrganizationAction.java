package com.npt.rms.arch.action;

import com.alibaba.fastjson.JSONObject;
import com.npt.bridge.arch.NptLogicDataProvider;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.arch.bean.ZTree;
import com.npt.rms.arch.manager.NptOrganizationManager;
import com.npt.rms.arch.service.NptRmsArchService;
import com.npt.rms.base.action.NptRMSAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.Collator;
import java.util.*;

/**
 * 项目: NPTRMS
 * 作者: 张磊
 * 日期: 16/9/27 下午9:43
 * 备注: org管理
 */
@Controller("npt.rms.org")
public class NptOrganizationAction extends NptRMSAction<NptLogicDataProvider> {
    @Autowired
    private NptRmsArchService archService;
    @Autowired
    private NptOrganizationManager organManager;

    private NptLogicDataProvider data;

    public NptLogicDataProvider getData() {
        return data;
    }

    public void setData(NptLogicDataProvider data) {
        this.data = data;
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:8
     * 备注: index
     */
    public String index() {
        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:9
     * 备注: 根据id禁用org
     */
    public String disabled() {
        try {
            NptLogicDataProvider org = archService.findOrgById(getWebId());
            archService.disable(org);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return ERROR;
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:9
     * 备注: 根据id启用org
     */
    public String enabled() {
        try {
            NptLogicDataProvider org = archService.findOrgById(getWebId());
            archService.enable(org);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return ERROR;
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:10
     * 备注: 根据parentId显示子org, parentId为空时显示根机构
     */
    public void showOrgTreeByParentId() {
        try {
            //List<ZTree> trees=new ArrayList<>();
           /* Collection<NptLogicDataProvider> ncmOrgs = archService.listOrg(getWebParentId());
            for (NptLogicDataProvider ncmOrg : ncmOrgs) {
                ZTree tree = new ZTree();
                tree.setId(String.valueOf(ncmOrg.getId()));
                tree.setpId(String.valueOf(ncmOrg.getParentId()));
                tree.setName(ncmOrg.getOrgName());
                tree.setIsParent(true);
                tree.setIconSkin("pIcon01");
                trees.add(tree);
            }*/
            List<ZTree> trees =organManager.selectOrganTreeList(getWebParentId());
            sortByChina(trees);
            this.ajaxOutPutJson(JSONObject.toJSON(trees));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 按照中文名称进行排序
     * @param list
     */
    public  void sortByChina(List<ZTree> list){
        // 对结果按别名排序
        Collections.sort(list, new Comparator<ZTree>() {
            public int compare(ZTree o1, ZTree o2) {
                Comparator<Object> comparator = Collator.getInstance(java.util.Locale.CHINA);
                return comparator.compare(o1.getName(), o2.getName());
            }
            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });
    }

    /**
     * 查询单位名称信息(下拉框值信息)
     */
    public void selectUnitInfoForSelect(){
        try {
            String sql="select t.id,t.org_name as name from NPT_ORGANIZATION t where t.status=1 group by t.id,t.org_name order by t.id asc";
            List<Map> map=this.organManager.selectUnitInfo(sql);
            List<ZTree> trees = new ArrayList<>();
            for(Map p:map){
                ZTree ztree=new ZTree();
                ztree.setId(p.get("ID").toString());
                ztree.setName(p.get("NAME").toString());
                trees.add(ztree);
            }
            this.ajaxOutPutJson(JSONObject.toJSON(trees));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:11
     * 备注: 根据id删除org
     */
    public void delete() {
        try {
            NptLogicDataProvider org = archService.findOrgById(getWebId());
            archService.delete(org);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:11
     * 备注: 添加org页面
     */
    public String addPage() {
        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:11
     * 备注: 添加org
     */
    public void add() {
        try {
            NptLogicDataProvider organization = new NptLogicDataProvider();
            organization.setOrgName(this.getParameter("data.orgName"));
            organization.setOrgCode(this.getParameter("data.orgCode"));
            organization.setOrgNote(this.getParameter("data.orgNote"));
            organization.setOrgAddr(this.getParameter("data.orgAddr"));
            organization.setOrgTel(this.getParameter("data.orgTel"));
            organization.setOrgMail(this.getParameter("data.orgMail"));
            organization.setOrgSite(this.getParameter("data.orgSite"));
            organization.setAlias(organization.getOrgName());
            organization.setOrgWechart(this.getParameter("data.orgWechart"));
            organization.setStatus(Integer.parseInt(this.getParameter("data.status")));
            organization.setCreatorId(getOperatorId());
            organization.setCreateTime(new Date());
            organization.setParentId(Long.parseLong(this.getParameter("data.parentId")));
            organization.setPubLevel(this.getIntParameter("data.pubLevel"));
            if(archService.checkOrg(organization)){
                archService.save(organization);
                this.outputSuccessResult();
            }else{
                this.outputErrorResult("重复的机构名称/英文缩写");
            }
        } catch (Exception e) {
            this.logger.warn("添加失败", e);
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        } finally {
        }
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:12
     * 备注: 编辑org页面
     */
    public String editPage() {
        try {
            this.data = archService.findOrgById(getWebId());
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return ERROR;
    }

    /**
     * 作者: 张磊
     * 日期: 16/9/27 下午9:12
     * 备注: 编辑org
     */
    public void edit() {
        try {
            this.data = archService.findOrgById(getWebId());
            if(null != this.data){
                this.data.setOrgName(this.getParameter("data.orgName"));
                this.data.setOrgCode(this.getParameter("data.orgCode"));
                this.data.setOrgNote(this.getParameter("data.orgNote"));
                this.data.setOrgAddr(this.getParameter("data.orgAddr"));
                this.data.setOrgTel(this.getParameter("data.orgTel"));
                this.data.setOrgMail(this.getParameter("data.orgMail"));
                this.data.setOrgSite(this.getParameter("data.orgSite"));
                this.data.setOrgWechart(this.getParameter("data.orgWechart"));
                this.data.setStatus(Integer.parseInt(this.getParameter("data.status")));
                this.data.setParentId(Long.parseLong(this.getParameter("data.parentId")));
                this.data.setPubLevel(this.getIntParameter("data.pubLevel"));
                archService.update(this.data);
                this.outputSuccessResult();
            }
        } catch (Exception e) {
            this.logger.warn("保存失败", e);
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        } finally {
        }
    }

    /**
     *作者：owen
     *时间：2016/12/15 11:07
     *描述:
     *      新增根机构
     */
    public void initRootOrg() {
        NptLogicDataProvider rootOrg = new NptLogicDataProvider();
        rootOrg.setOrgName(this.getParameter("data.orgName"));
        rootOrg.setOrgCode(this.getParameter("data.orgCode"));
        rootOrg.setOrgNote(this.getParameter("data.orgNote"));
        rootOrg.setOrgAddr(this.getParameter("data.orgAddr"));
        rootOrg.setOrgTel(this.getParameter("data.orgTel"));
        rootOrg.setOrgMail(this.getParameter("data.orgMail"));
        rootOrg.setOrgSite(this.getParameter("data.orgSite"));
        rootOrg.setAlias(rootOrg.getOrgName());
        rootOrg.setOrgWechart(this.getParameter("data.orgWechart"));
        rootOrg.setStatus(Integer.parseInt(this.getParameter("data.status")));
        rootOrg.setCreatorId(getOperatorId());
        rootOrg.setCreateTime(new Date());
        rootOrg.setPubLevel(this.getIntParameter("data.pubLevel"));

        rootOrg.setParentId(NptCommonUtil.getDefaultParentId());
        rootOrg.setId(NptCommonUtil.getDefaultParentId() * 2);

        archService.save(rootOrg);
        this.outputSuccessResult();
    }
}
