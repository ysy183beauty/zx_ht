package com.npt.thirdpart.fineReport.extend.action;

import com.fr.web.ReportServlet;
import org.springframework.stereotype.Controller;
import org.summer.security.web.action.BaseAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Controller
public class ReportAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    private static CurentReportServlet servlet = null;

    public void report() throws Exception {
        if (servlet == null) {
            servlet = new CurentReportServlet();
            servlet.servletContext = this.getSession().getServletContext();
            servlet.init(null);
        }
        setDefaultContentType();
        servlet.doGet(this.getRequest(), this.getResponse());
    }

    class CurentReportServlet extends ReportServlet {

        private static final long serialVersionUID = 1L;

        private ServletContext servletContext = null;

        public void init(ServletConfig servletConfig) throws ServletException {
            super.init(new ServletConfig() {

                @Override
                public String getInitParameter(String s) {
                    return null;
                }

                @SuppressWarnings("rawtypes")
                @Override
                public Enumeration getInitParameterNames() {
                    return new java.util.Properties().elements();
                }

                @Override
                public ServletContext getServletContext() {
                    return servletContext;
                }

                @Override
                public String getServletName() {
                    return "ProxollAdminServlet";
                }
            });
        }

        public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            this.doGet(request, response);
        }

        public void setServletContext(ServletContext servletContext) {
            this.servletContext = servletContext;
        }
    }

    ;
}
