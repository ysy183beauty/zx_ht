package com.npt.thirdpart.fineReport.extend.action;

import cn.com.ikdo.style.sitemesh3.AbstractPaginationAction;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fr.third.fr.pdf.kernel.log.SystemOutCounter;
import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.rms.util.NptRmsUtil;
import com.npt.thirdpart.fineReport.extend.entity.FindReport;
import com.npt.thirdpart.fineReport.extend.manager.FindReportHistoryManager;
import com.npt.thirdpart.fineReport.extend.manager.FindReportManager;
import com.npt.thirdpart.fineReport.extend.service.FindReportService;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.summer.core.common.Operator;
import org.summer.core.common.TitleObject;
import org.summer.extend.orm.Pagination;
import org.summer.extend.orm.condition.Conditions;
import org.summer.extend.security.PlatformSecurityContext;
import org.summer.security.service.SecurityService;
import org.summer.security.service.UserService;
import org.summer.upload.po.FileObject;
import org.summer.upload.utils.CommonUtil;
import org.summer.upload.utils.UploadPreferencesUtils;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Controller("summer.findReportAction")
@Scope("prototype")
public class FindReportAction extends AbstractPaginationAction<FindReport> {

    private static final long serialVersionUID = 1L;

    @Autowired
    private FindReportManager findReportManager;
    @Autowired
    private FindReportHistoryManager findReportHistoryManager;
    @Autowired
    private FindReportService rptService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "securityService")
    private SecurityService securityService;

    private FindReport data;

    public FindReport getData() {
        return data;
    }

    public void setData(FindReport data) {
        this.data = data;
    }

    public String index() {
        return list();
    }

    public String list() {
        //获取报表显示方式
        String reportDisplay=ServletActionContext.getServletContext().
                getInitParameter("reportDisplay")==null?"1":ServletActionContext.getServletContext().
                getInitParameter("reportDisplay");
        Integer beginIndex = this.getIntParameter("beginIndex");
        Integer currPage = this.getIntParameter("currPage");
        Integer pageSize = this.getIntParameter("pageSize");

        currPage = currPage == null ? 1 : currPage;
        pageSize = pageSize == null ? this.configPageSize() : pageSize;
        beginIndex = (beginIndex == null ? ((currPage - 1) * pageSize + 1) : beginIndex) - 1;
        Pagination<FindReport> dataPagination = this.findReportManager.findAll(beginIndex, pageSize, Conditions.desc("id"));
        this.setAttribute("dataPagination", createPaginationResult(dataPagination, currPage, pageSize));
        this.setAttribute("findReportDisplay",Integer.parseInt(reportDisplay));
        return SUCCESS;
    }

    public String addPage() {
        Operator operator = PlatformSecurityContext.getCurrentOperator();

        java.util.Collection<TitleObject> grantRoles = securityService.getCanGrantRoles(operator.getOperatorId(), operator.isAdministrator());
        this.setAttribute("grantRoles", grantRoles);

        List<String> reportsDefines = getReportFileList();
        this.setAttribute("reportsDefines", reportsDefines);

        this.setAttribute("reportsCategory", NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.RPC));
        this.setAttribute("reportsHost", NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.RPH));
        this.setAttribute("reportsPubLevel", NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.RPP));
        this.setAttribute("reportsStyle", NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.RPS));
        this.setAttribute("MAX_MENU_INDEX", FindReport.MAX_MENU_INDEX);
        return SUCCESS;
    }

    public void add() {
        String[] rolenames = this.getParameterValues("rolename");
        Set<String> roles = new java.util.HashSet<>();
        if (rolenames != null) {
            for (String r : rolenames) {
                roles.add(r);
            }
        }
        this.data.setRoles(roles);
        if (!getRptFile()) return;

        //绑定菜单
        if (!bindMenu()) return;


        this.data.setCreateTime(NptCommonUtil.getCurrentSysTimestamp());
        this.data.setCreatorId(NptRmsUtil.getCurrentUserId());

        try {
            rptService.save(this.data);
            this.outputSuccessResult();
        } catch (Exception e) {
            this.logger.warn("添加失败", e);
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        }
    }

    /**
     * 作者: 张磊
     * 日期: 17/1/6 下午2:17
     * 备注: 获取模板文件, 成功返回 true
     */
    protected boolean getRptFile() {
        // 本地上传
        List<FileObject> newFiles = CommonUtil.getNewFiles(getRequest(), "upload_file");
        if (newFiles.size() == 0) {
            this.outputErrorResult("添加失败，未上传模板文件!");
            return false;
        }
        FileObject newFile = newFiles.get(0);
        String fileName = newFile.getRealName().substring(0, newFile.getRealName().length() - 4) + "-" + System.currentTimeMillis() + ".cpt";
        // 构造文件对象
        File srcFile = new File(UploadPreferencesUtils.getUploadRootPath() + newFile.getFileName());

        // 临时文件已被删除
        if (!srcFile.exists()) {
            this.outputErrorResult("添加失败，临时文件已被删除, 请重新上传");
            return false;
        }

        try {
            File destFile = new File(this.getSession().getServletContext().getRealPath("/") + "WEB-INF/reportlets/" + fileName);

            if (!destFile.exists()) {
                // 保存文件对象
                FileUtils.copyFile(srcFile, destFile);
                //文件上传成功
                this.data.setRptPath(fileName);
            } else {
                // 同名文件已存在
                this.outputErrorResult("添加失败，同名文件已存在");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.outputErrorResult("添加失败，服务器IO错误");
            return false;
        }
        return true;
    }

    protected List<String> getReportFileList() {
        String reportletsPath = this.getSession().getServletContext().getRealPath("/WEB-INF/reportlets/");
        File reportPath = new File(reportletsPath);
        String[] fileNames = reportPath.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".cpt");
            }
        });
        List<String> files = new java.util.LinkedList<>();
        for (String name : fileNames) {
            files.add(name);
        }
        return files;
    }


    public String editPage() {
        Long id = this.getLongParameter("id");
        this.data = this.findReportManager.findFindReportById(id);

        Operator operator = PlatformSecurityContext.getCurrentOperator();
        java.util.Collection<TitleObject> grantRoles = securityService.getCanGrantRoles(operator.getOperatorId(), operator.isAdministrator());
        this.setAttribute("grantRoles", grantRoles);

        this.setAttribute("roleNames", this.data.getRoles());

        List<String> reportsDefines = getReportFileList();
        this.setAttribute("reportsDefines", reportsDefines);

        this.setAttribute("reportsVersion", findReportHistoryManager.findByProperty("rptReport.id", id));

        this.setAttribute("reportsCategory", NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.RPC));
        this.setAttribute("reportsHost", NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.RPH));
        this.setAttribute("reportsPubLevel", NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.RPP));
        this.setAttribute("reportsStyle", NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.RPS));
        this.setAttribute("MAX_MENU_INDEX", FindReport.MAX_MENU_INDEX);

        return SUCCESS;
    }

    public void edit() {
        try {
            String[] addRoles = this.getParameterSplit("addRoles");
            String[] delRoles = this.getParameterSplit("delRoles");

            FindReport report = findReportManager.findFindReportById(this.data.getId());

            boolean f = CommonUtil.getNewFiles(getRequest(), "upload_file").size() == 0; // 未上传模板文件
            if (f) {
                this.data.setRptPath(report.getRptPath());
            } else {
                if (!getRptFile()) return;
            }


            if (!report.getShowStyle().equals(this.data.getShowStyle()) || !this.data.getMenuIndex().equals(report.getMenuIndex())) {
                //绑定菜单
                if (!bindMenu()) return;
            }

            rptService.updateFindReport(this.data, addRoles, delRoles);
            this.outputSuccessResult();
        } catch (Exception e) {
            this.logger.warn("保存失败", e);
            this.outputErrorResult(e.getMessage() == null ? e.toString() : e.getMessage());
        }
    }

    /**
     * 作者: 张磊
     * 日期: 17/1/13 下午2:38
     * 备注: 绑定菜单, 成功返回true
     */
    protected boolean bindMenu() {
        if (this.data.getShowStyle() == NptDict.RPS_MENU.getCode()) {
            if (findReportManager.countByCondition(Conditions.eq("rptCategory", this.data.getRptCategory()), Conditions.eq("menuIndex", this.data.getMenuIndex())) != 0) {
                this.outputErrorResult("菜单已被绑定");
                return false;
            }
        } else {
            this.data.setMenuIndex(null);
        }
        return true;
    }


    public void disabled() {
        Long id = this.getLongParameter("id");
        this.findReportManager.execDisabled(id);
    }

    public void enabled() {
        Long id = this.getLongParameter("id");
        this.findReportManager.execEnabled(id);
    }

    public void delete() {
        Long id = this.getLongParameter("id");
        rptService.deleteById(id);
    }

    public String reports() {
        //获取报表展示方式
        String reportDisplay=ServletActionContext.getServletContext().
                getInitParameter("reportDisplay")==null?"1":ServletActionContext.getServletContext().
                getInitParameter("reportDisplay");
        String[] roles = PlatformSecurityContext.getCurrentOperatorRoles();
        java.util.Collection<FindReport> reports = this.findReportManager.findFindReportByRoles(roles);
        this.setAttribute("reports", reports);
        this.setAttribute("reportsHost", NptCommonUtil.getNptRmsDictGroupByPrefix(NptCommonUtil.NPT_DICT_PREFIX.RPH));
        this.setAttribute("findReportDisplay",Integer.parseInt(reportDisplay));
        return SUCCESS;
    }

    @Override
    protected JSONObject toJSON(FindReport data) {
        JSONObject result = new JSONObject();
        result.put("id", data.getId());
        result.put("rptName", data.getRptName());
        result.put("rptPath", data.getRptPath());
        result.put("rptNote", data.getRptNote());
        result.put("rptCategory", data.getRptCategory());
        result.put("rptHost", data.getRptHost());
        result.put("showStyle", data.getShowStyle());
        result.put("menuIndex", data.getMenuIndex());
        result.put("rptSecondPath",data.getReservedField06());
        if (data.getRptCategory() == null) {
            result.put("rptCategory", null);
        } else if (data.getRptCategory() == NptDict.RPC_REPORT.getCode()) {
            result.put("rptCategory", NptDict.RPC_REPORT.getTitle());
        } else if (data.getRptCategory() == NptDict.RPC_STATISTIC.getCode()) {
            result.put("rptCategory", NptDict.RPC_STATISTIC.getTitle());
        } else if (data.getRptCategory() == NptDict.RPC_DECLARE.getCode()) {
            result.put("rptCategory", NptDict.RPC_DECLARE.getTitle());
        }
        if (data.getRptHost() == null) {
            result.put("rptHost", null);
        } else if (data.getRptHost() == NptDict.RPH_BUSINESS.getCode()) {
            result.put("rptHost", NptDict.RPH_BUSINESS.getTitle());
        } else if (data.getRptHost() == NptDict.RPH_PERSONAL.getCode()) {
            result.put("rptHost", NptDict.RPH_PERSONAL.getTitle());
        }
        if (data.getPubLevel() == null) {
            result.put("pubLevel", null);
        } else if (data.getPubLevel() == NptDict.RPP_ANONYMOUS.getCode()) {
            result.put("pubLevel", NptDict.RPP_ANONYMOUS.getTitle());
        } else if (data.getPubLevel() == NptDict.RPP_LOGIN.getCode()) {
            result.put("pubLevel", NptDict.RPP_LOGIN.getTitle());
        } else if (data.getPubLevel() == NptDict.RPP_AUTH.getCode()) {
            result.put("pubLevel", NptDict.RPP_AUTH.getTitle());
        }
        result.put("status", data.getStatus());
        return result;
    }

    /**
     * 作者: 张磊
     * 日期: 17/1/5 下午3:1
     * 备注: 获取企业/个人报告模板
     */
    public void getReportList() {
        Integer rptCategory = getIntParameter("rptCategory");
        Integer rptHost = getIntParameter("rptHost");
        List<FindReport> reports = findReportManager.findByCondition(Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()), Conditions.eq("rptCategory", rptCategory), Conditions.eq("rptHost", rptHost));
        List<JSONObject> results = reports.stream()
                .filter(report -> findReportManager.validateSecurity(report.getId(), report.getRptPath()))
                .map(this::toJSON)
                .collect(Collectors.toList());
        this.outputSuccessResult(JSON.toJSONString(results));
    }

    /**
     * 作者: 张磊
     * 日期: 17/1/10 上午11:22
     * 备注: 根据id获取模板文件名称
     */
    public void getReportById() {
        Long id = this.getLongParameter("id");
        FindReport report = findReportManager.findById(id);
        if (report != null) {
            this.outputSuccessResult(report.getRptPath());
        } else {
            this.outputErrorResult("not found");
        }
    }

    /**
     * 作者: 张磊
     * 日期: 17/1/13 下午1:47
     * 备注: 根据模板类型和菜单index查找报告
     */
    public String showReport(Integer rptCategory ,Integer menuIndex){
        //获取报表展示方式
        String reportDisplay=ServletActionContext.getServletContext().
                getInitParameter("reportDisplay")==null?"1":ServletActionContext.getServletContext().
                getInitParameter("reportDisplay");
        FindReport report = findReportManager.findUniqueByCondition(Conditions.eq(NptBaseEntity.PROPERTY_STATUS, NptDict.IDS_ENABLED.getCode()), Conditions.eq("showStyle", NptDict.RPS_MENU.getCode()), Conditions.eq("rptCategory", rptCategory),Conditions.eq("menuIndex",menuIndex));
        this.setAttribute("REPORT", report);
        this.setAttribute("findReportDisplay",Integer.parseInt(reportDisplay));
        return SUCCESS;
    }

    public String report10(){
        return showReport(1,0);
    }
    public String report11(){
        return showReport(1,1);
    }
    public String report12(){
        return showReport(1,2);
    }
    public String report13(){
        return showReport(1,3);
    }
    public String report20(){
        return showReport(2,0);
    }
    public String report21(){
        return showReport(2,1);
    }
    public String report22(){
        return showReport(2,2);
    }
    public String report23(){
        return showReport(2,3);
    }

    /**
     * 学生报表信息
     */
    public String studentReport(){
        StringBuilder sb=new StringBuilder();
        sb.append("select ROUND((a1.cz)/(a1.cz+a2.dx+a3.gz+a4.xx)*100,4) as 初中生占比,");
        sb.append("ROUND((a2.dx)/(a1.cz+a2.dx+a3.gz+a4.xx)*100,4) as 大学生占比,");
        sb.append("ROUND((a3.gz)/(a1.cz+a2.dx+a3.gz+a4.xx)*100,4) as 高中生占比,");
        sb.append("ROUND((a4.xx)/(a1.cz+a2.dx+a3.gz+a4.xx)*100,4) as 小学生占比,");
        sb.append("a1.cz as 初中生,a2.dx as 大学生,a3.gz as 高中生,a4.xx as 小学生");
        sb.append(" from  (select count(1) cz from NCE_RK_ZXXSXX_JIAOYJ) a1 ,");
        sb.append("(select count(1) dx from NCE_RK_XYXSXX_JIAOYJ) a2 ,");
        sb.append("(select count(1) gz from NCE_RK_GZXSXX_JIAOYJ) a3 ,");
        sb.append("(select count(1) xx from NCE_RK_XXXSXX_JIAOYJ) a4");
        List<Map> result= this.findReportManager.selectDataBySql(sb.toString());
        if (result.size()>0){
            JSONObject jsonObjectOne=new JSONObject();
            jsonObjectOne.put("大学生",result.get(0).get("大学生"));
            jsonObjectOne.put("高中生",result.get(0).get("高中生"));
            jsonObjectOne.put("初中生",result.get(0).get("初中生"));
            jsonObjectOne.put("小学生",result.get(0).get("小学生"));
            JSONObject jsonObjectTwo=new JSONObject();
            jsonObjectTwo.put("大学生占比",result.get(0).get("大学生占比"));
            jsonObjectTwo.put("高中生占比",result.get(0).get("高中生占比"));
            jsonObjectTwo.put("初中生占比",result.get(0).get("初中生占比"));
            jsonObjectTwo.put("小学生占比",result.get(0).get("小学生占比"));
            //存放json数据信息
            this.setAttribute("jsonObjectOne", jsonObjectOne.toString());
            this.setAttribute("jsonObjectTwo",jsonObjectTwo.toString());
        }
        return  SUCCESS;
    }

    /**
     * 委办局数据采集情况报表信息
     */
    public String wbjReport(){
        StringBuilder sb=new StringBuilder();
        sb.append("select * from (select wbj,sjl from nce_temp_rkltj  order by sjl desc) t where rownum<=10");
        List<Map> topResult=this.findReportManager.selectDataBySql(sb.toString());
        JSONArray arr=new JSONArray(topResult);
        this.setAttribute("jsonObjectTop",arr.toString());
        //查询委办局数据比重
        sb.setLength(0);
        sb.append("select sjl,wbj from nce_temp_rkltj t");
        List<Map> rightResult=this.findReportManager.selectDataBySql(sb.toString());
        JSONArray arrRight=new JSONArray(rightResult);
        this.setAttribute("arrRight",arrRight.toString());
        return SUCCESS;
    }

    /**
     * 企业信用报告报表
     */
    public String qyxyReport(){
        Integer id=1683853;
        StringBuilder sb=new StringBuilder();
        sb.append("select fr.zzjgdm,lx.code_name frlx,fr.frmc,fr.fddbr,fr.frsfzh,fr.frzs,");
        sb.append("fr.clrq,zt.code_name frzt,fr.gszch from nce_fr_jbxx_dl fr,(");
        sb.append("select * from npt_business_code c where c.code_field_id='1276') lx,(");
        sb.append("select * from npt_business_code c where c.code_field_id='1284') zt ");
        sb.append("where lx.code_value=fr.frlx and zt.code_value=fr.frzt and fr.id="+id+"");
        List<Map> result1=this.findReportManager.selectDataBySql(sb.toString());
        JSONArray arr1=new JSONArray(result1);
        this.setAttribute("qyxyInfo",arr1.toString());
        return SUCCESS;
    }

    /**
     * 个人信用报告报表
     */
    public String grxyReport(){
        //查询数据信息
        StringBuilder sb=new StringBuilder();
        sb.append("select xm,mzmc,xb,TO_CHAR(csrq,'yyyy-MM-dd') csrq,sfzh,mlxz from nce_rk_hjxx_gongaj ");
        sb.append(" where rybh='532901014000057298'");
        List<Map> result1=this.findReportManager.selectDataBySql(sb.toString());
        JSONArray arr1=new JSONArray(result1);
        this.setAttribute("grbgInfo",arr1.toString());
        return SUCCESS;
    }

    /**
     * 自然人表彰与处罚情况分析
     */
    public String bycfReport(){
        StringBuilder sb=new StringBuilder();
        //表彰与处罚
        sb.append("select 2006 dateTime,893 praiseCount,363 punishCount, 893 C,363 D from dual union all ");
        sb.append("select 2007 dateTime,1043 praiseCount,273 punishCount, 968 C,318 D from dual union all ");
        sb.append("select 2008 dateTime,1284 praiseCount,173 punishCount, 1073.33 C,269.67 D from dual union all ");
        sb.append("select 2009 dateTime,1536 praiseCount,253 punishCount, 1189 C,265.5 D from dual union all ");
        sb.append("select 2010 dateTime,1736 praiseCount,183 punishCount, 1298.4 C,249 D from dual union all ");
        sb.append("select 2011 dateTime,2032 praiseCount,273 punishCount, 1420.67 C,253 D from dual union all ");
        sb.append("select 2012 dateTime,2537 praiseCount,382 punishCount, 1580.14 C,271.43 D from dual union all ");
        sb.append("select 2013 dateTime,2837 praiseCount,127 punishCount, 1737.25 C,253.38 D from dual union all ");
        sb.append("select 2014 dateTime,3297 praiseCount,213 punishCount, 1910.56 C,248.89 D from dual union all ");
        sb.append("select 2015 dateTime,3647 praiseCount,23 punishCount, 2084.2 C,226.3 D from dual union all ");
        sb.append("select 2016 dateTime,3865 praiseCount,321 punishCount, 2246.09 C,234.91 D from dual");
        List<Map> result1=this.findReportManager.selectDataBySql(sb.toString());
        JSONArray arr1=new JSONArray(result1);
        this.setAttribute("bycfCount",arr1.toString());
        //表彰分领域
        sb.setLength(0);
        sb.append("select '医疗卫生' work, 0.28 percent from dual union all ");
        sb.append("select '劳动就业' work, 0.14 percent from dual union all ");
        sb.append("select '教育领域' work, 0.16 percent from dual union all ");
        sb.append("select '社会保障' work, 0.19 percent from dual union all ");
        sb.append("select '公共安全' work, 0.08 percent from dual union all ");
        sb.append("select '纳税领域' work, 0.15 percent from dual ");
        List<Map> result2=this.findReportManager.selectDataBySql(sb.toString());
        JSONArray arr2=new JSONArray(result2);
        this.setAttribute("bzWork",arr2.toString());
        //处罚分领域
        sb.setLength(0);
        sb.append("select '环境保护' work,0.35 percent from dual union all ");
        sb.append("select '社会治安' work,0.26 percent from dual union all ");
        sb.append("select '市容市貌' work,0.14 percent from dual union all ");
        sb.append("select '社会服务' work,0.09 percent from dual union all ");
        sb.append("select '医疗卫生' work,0.16 percent from dual ");
        List<Map> result3=this.findReportManager.selectDataBySql(sb.toString());
        JSONArray arr3=new JSONArray(result3);
        this.setAttribute("cfWork",arr3.toString());
        //清空数据信息
        sb.setLength(0);
        return SUCCESS;
    }

    /**
     * 自然人年龄段信用情况分析
     */
    public String nlxyReport(){
        StringBuilder sb=new StringBuilder();
        //查询性别数据信息
        sb.append("select '0-18' sxrq,0 man,-0 feman from dual union all");
        sb.append(" select '19-23' sxrq,38 man,-22 feman from dual union all");
        sb.append(" select '24-28' sxrq,84 man,-58 feman from dual union all");
        sb.append(" select '29-33' sxrq,123 man,-85 feman from dual union all");
        sb.append(" select '34-38' sxrq,145 man,-123 feman from dual union all");
        sb.append(" select '39-43' sxrq,116 man,-87 feman from dual union all");
        sb.append(" select '44-48' sxrq,83 man,-58 feman from dual union all");
        sb.append(" select '49-53' sxrq,74 man,-42 feman from dual union all");
        sb.append(" select '54-58' sxrq,46 man,-27 feman from dual union all");
        sb.append(" select '59-63' sxrq,27 man,-16 feman from dual union all");
        sb.append(" select '64+' sxrq,21 man,-11 feman from dual");
        List<Map> result1=this.findReportManager.selectDataBySql(sb.toString());
        JSONArray arr1=new JSONArray(result1);
        this.setAttribute("sexResult",arr1.toString());
        //查询各级别学历占比信息
        sb.setLength(0);
        sb.append("select '大学专科' A,26.23/100 B from dual union all");
        sb.append(" select '中专' A,22.36/100 B from dual union all");
        sb.append(" select '大学本科' A,18.88/100 B from dual union all");
        sb.append(" select '高中' A,15.94/100 B from dual union all");
        sb.append(" select '初中' A,15.36/100 B from dual union all");
        sb.append(" select '博士后' A,0.50/100 B from dual union all");
        sb.append(" select '硕士研究生' A,0.39/100 B from dual union all");
        sb.append(" select '小学' A,0.20/100 B from dual union all");
        sb.append(" select '博士研究生' A,0.15/100 B from dual");
        List<Map> result2=this.findReportManager.selectDataBySql(sb.toString());
        JSONArray arr2=new JSONArray(result2);
        this.setAttribute("grudationResult",arr2.toString());
        //分职业失效人数
        sb.setLength(0);
        sb.append("select '企业基层工作人员' A,31.12/100 B from dual union all");
        sb.append(" select '工人' A,18.32/100 B from dual union all");
        sb.append(" select '企业高层工作人员' A,11.83/100 B from dual union all");
        sb.append(" select '国家机关处级以下' A,9.02/100 B from dual union all");
        sb.append(" select '个人业主' A,8.05/100 B from dual union all");
        sb.append(" select '企业中级工作人员' A,7.04/100 B from dual union all");
        sb.append(" select '文员' A,6.89/100 B from dual union all");
        sb.append(" select '受雇普通技术人员' A,6.25/100 B from dual union all");
        sb.append(" select '其他' A,1.48/100 B from dual");
        List<Map> result3=this.findReportManager.selectDataBySql(sb.toString());
        JSONArray arr3=new JSONArray(result3);
        this.setAttribute("workResult",arr3.toString());
        sb.setLength(0);//清空sb对象里面的数据信息
        return SUCCESS;
    }

    /**
     * 数据展现
     */
    public String sjzxReport(){
        StringBuilder sb=new StringBuilder();
        String frbs="c5752ea75f3f4666be8b37539b48e31d";
        sb.append("select frmc,rownum from  nce_fr_jbxx_dl ");
        sb.append(" where frbs='"+frbs+"' and frmc is not null");
        List<Map> result=this.findReportManager.selectDataBySql(sb.toString());
        JSONArray arr=new JSONArray(result);
        this.setAttribute("result",arr.toString());
        return SUCCESS;
    }

    /**
     * 各单位失信情况
     */
    public String unitReport(){
        StringBuilder sb=new StringBuilder();
        sb.append("select qy,sjl from nce_temp_gdwsxqk t");
        List<Map> result=this.findReportManager.selectDataBySql(sb.toString());
        JSONArray arr=new JSONArray(result);
        this.setAttribute("sxResult",arr.toString());
        return SUCCESS;
    }

    /**
     * 企业规模信用统计分析
     */
    public String qygmReport(){
        StringBuilder sb=new StringBuilder();
        sb.append("select '微型企业' scal,120 ryCount,62 sxCount from dual union all");
        sb.append(" select '小型企业' scal,620 ryCount,163 sxCount from dual union all");
        sb.append(" select '中性企业' scal,21 ryCount,32 sxCount from dual union all");
        sb.append(" select '大型企业' scal,18 ryCount,0 sxCount from dual union all");
        sb.append(" select '特大型企业' scal,5 ryCount,0 sxCount from dual");
        List<Map> result=this.findReportManager.selectDataBySql(sb.toString());
        JSONArray arr=new JSONArray(result);
        this.setAttribute("result",arr.toString());
        return SUCCESS;
    }

    /**
     * 企业信用评估情况
     */
    public String qyxypgReport(){
        StringBuilder sb=new StringBuilder();
        sb.append("select '基本情况' A, dbms_random.value(0,100) B from dual union all");
        sb.append(" select '资质情况' A, dbms_random.value(0,100) B from dual union all");
        sb.append(" select '失信情况' A, dbms_random.value(0,100) B from dual union all");
        sb.append(" select '荣誉情况' A, dbms_random.value(0,100) B from dual union all");
        sb.append(" select '社会评价' A, dbms_random.value(0,100) B from dual");
        List<Map> result=this.findReportManager.selectDataBySql(sb.toString());
        JSONArray arr=new JSONArray(result);
        this.setAttribute("xypg",arr.toString());
        return SUCCESS;
    }
}
