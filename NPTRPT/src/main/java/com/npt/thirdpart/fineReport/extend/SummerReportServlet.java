package com.npt.thirdpart.fineReport.extend;

import com.fr.web.ReportServlet;
import com.npt.thirdpart.fineReport.extend.manager.FindReportManager;
import com.npt.thirdpart.fineReport.extend.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SummerReportServlet extends ReportServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String reportlet = request.getParameter("reportlet");
        if (reportlet == null) {
            reportlet = request.getParameter("formlet");
        }
        if (StringUtils.isNotBlank(reportlet)) {
            String reportletId = request.getParameter("id");

            if (StringUtils.isBlank(reportletId) || !NumberUtils.isNumber(reportletId)) {
                this.log("参数错误，无效Id参数。");
                request.setAttribute("message", "请求参数错误");
                response.getWriter().println("parameters error.");
                return;
            }

            boolean result = getFindReportManager().validateSecurity(Long.valueOf(reportletId), reportlet);
            if (!result) {
                request.setAttribute("message", "没有访问权限！");
                response.getWriter().println("no access");
                return;
            }
        }

        super.doGet(request, response);
    }

    protected FindReportManager findReportManager;

    protected FindReportManager getFindReportManager() {
        if (this.findReportManager == null) {
            this.findReportManager = BeanUtil.getBean(FindReportManager.class);
        }
        return this.findReportManager;
    }

}
