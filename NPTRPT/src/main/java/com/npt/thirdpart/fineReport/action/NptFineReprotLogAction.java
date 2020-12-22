package com.npt.thirdpart.fineReport.action;

import com.fr.base.FRContext;
import com.fr.base.Parameter;
import com.fr.dav.LocalEnv;
import com.fr.general.ModuleContext;
import com.fr.io.TemplateWorkBookIO;
import com.fr.io.exporter.PDFExporter;
import com.fr.main.impl.WorkBook;
import com.fr.main.workbook.ResultWorkBook;
import com.fr.report.module.EngineModule;
import com.fr.stable.WriteActor;
import com.npt.bridge.dict.NptDict;
import com.npt.bridge.util.FileUtils;
import com.npt.bridge.util.MultiStream;
import com.npt.bridge.util.NptCommonUtil;
import com.npt.thirdpart.fineReport.entity.ReportLogBean;
import com.npt.thirdpart.fineReport.extend.entity.FindReport;
import com.npt.thirdpart.fineReport.extend.manager.FindReportManager;
import com.npt.thirdpart.fineReport.manager.ReportLogManager;
import com.npt.web.action.NptPaginationAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.summer.extend.orm.condition.Conditions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 项目: NPTWebApp
 * 作者: 张磊
 * 日期: 16/11/28 下午4:16
 * 备注: 历史报告
 */
@Controller("npt.web.fineReport.log")
public class NptFineReprotLogAction extends NptPaginationAction<ReportLogBean> {
    @Autowired
    private ReportLogManager reportLogManager;
    @Autowired
    private FindReportManager reportManager;

    public String index() {
        return list();
    }

    public String list() {
        String rpbs = this.getParameter("rpbs");
        String rptype = this.getParameter("rptype");
        String rpmc = this.getParameter("rpmc");
        StringBuilder sql=new StringBuilder("");
        sql.append("select * from NPT_REPORT_LOG t");
        sql.append(" where t.rpbs='"+rpbs+"'");
        sql.append(" and t.report_type='"+rptype+"'");
        sql.append(" and t.user_id="+this.getOperatorId()+"");
        sql.append(" order by t.create_time desc");
        List<ReportLogBean> dataList=new ArrayList<ReportLogBean>();
        try {
            List<Map> list=reportLogManager.selectPersonReportHistory(sql.toString());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for(Map map:list){
                ReportLogBean bean=new ReportLogBean();
                if(map.get("ID")!=null&&!"".equals(map.get("ID"))){
                    bean.setId(Long.parseLong(map.get("ID").toString()));
                }
                if(map.get("USER_ID")!=null&&!"".equals(map.get("USER_ID"))){
                    bean.setUserId(Long.parseLong(map.get("USER_ID").toString()));
                }
                bean.setTemplate(map.get("TEMPLATE")==null?"":map.get("TEMPLATE").toString());
                bean.setRpbs(map.get("RPBS")==null?"":map.get("RPBS").toString());
                bean.setRpmc(rpmc);
                bean.setFileName(map.get("FILE_NAME")==null?"":map.get("FILE_NAME").toString());
                bean.setReportType(map.get("REPORT_TYPE")==null?"":map.get("REPORT_TYPE").toString());
                if(map.get("CREATE_TIME")!=null&&!"".equals(map.get("CREATE_TIME"))){
                    bean.setCreateTime(df.parse(map.get("CREATE_TIME").toString()));
                }
                dataList.add(bean);
            }
            /*List<ReportLogBean> dataList = this.reportLogManager.findByCondition(Conditions.eq("rpbs", rpbs),
                    Conditions.eq("reportType", rptype),
                    Conditions.eq("userId",this.getOperatorId()),
                    Conditions.desc("createTime"));
            for (ReportLogBean bean : dataList) {
                bean.setRpmc(rpmc);
            }*/
            this.setAttribute("dataList", dataList);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public void detail() {
        String filePath = this.getSession().getServletContext().getRealPath("") + "/WEB-INF/pdf/" + this.getParameter("fileName");

        FileUtils.download(this.getResponse(), filePath, "历史报告.pdf");
    }

    /**
     * 作者: 张磊
     * 日期: 16/11/28 上午10:54
     * 备注: 导出pdf报告并记录历史记录
     */
    public void exportReportToPDF() {
        String rpbs = this.getParameter("rpbs");
        String rptype = this.getParameter("rptype");
        String template = this.getParameter("template");
        Long id = this.getLongParameter("id");

        FindReport report = reportManager.findById(id);
        if (report == null || !report.getRptPath().equals(template) || !reportManager.validateSecurity(id, template)) {
            return;
        }

        // 定义报表运行环境,才能执行报表
        String envpath = this.getSession().getServletContext().getRealPath("") + "/WEB-INF";
        FRContext.setCurrentEnv(new LocalEnv(envpath));
        // 多次调用报错，暂时这么解决
        try {
            com.fr.general.ModuleContext.startModule(EngineModule.class.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultWorkBook rworkbook = null;
        try {
            // 未执行模板工作薄
            WorkBook workbook = (WorkBook) TemplateWorkBookIO
                    .readTemplateWorkBook(FRContext.getCurrentEnv(),
                            "/" + template);
            // 获取报表参数并设置值，导出内置数据集时数据集会根据参数值查询出结果从而转为内置数据集
            Parameter[] parameters = workbook.getParameters();
            parameters[0].setValue(rpbs);
            // 定义parametermap用于执行报表，将执行后的结果工作薄保存为rworkBook
            java.util.Map parameterMap = new java.util.HashMap();
            for (int i = 0; i < parameters.length; i++) {
                parameterMap.put(parameters[i].getName(), parameters[i]
                        .getValue());
            }
            // 定义输出流
            OutputStream fileOutputStream;
            OutputStream httpOutputStream;

            // 将结果工作薄导出为Pdf文件
            String fileName = UUID.randomUUID().toString().replace("-", "") + ".pdf";
            fileOutputStream = new FileOutputStream(new File(envpath + "/pdf/" + fileName));

            this.getResponse().reset();  //重置结果集
            this.getResponse().addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(template.substring(0, template.lastIndexOf("-")), "UTF-8") + ".pdf");  //返回头 文件名
            this.getResponse().setContentType("application/pdf");

            httpOutputStream = this.getResponse().getOutputStream();

            MultiStream mStream = new MultiStream(fileOutputStream, httpOutputStream);

            PDFExporter PdfExport = new PDFExporter();
            PdfExport.export(mStream, workbook.execute(parameterMap, new WriteActor()));

            mStream.close();

            ModuleContext.stopModules();

            ReportLogBean reportLogBean = new ReportLogBean();
            reportLogBean.setStatus(NptDict.IDS_ENABLED.getCode());
            reportLogBean.setRpbs(rpbs);
            reportLogBean.setTemplate(report.getRptName());
            reportLogBean.setCreateTime(NptCommonUtil.getCurrentSysTimestamp());
            reportLogBean.setFileName(fileName);
            reportLogBean.setUserId(this.getOperatorId());
            reportLogBean.setReportType(rptype);
            reportLogManager.save(reportLogBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
