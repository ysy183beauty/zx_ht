package com.npt.thirdpart.fineReport.extend.action;

import cn.com.ikdo.style.sitemesh3.AbstractPaginationAction;
import com.alibaba.fastjson.JSONObject;
import com.npt.bridge.base.NptBaseEntity;
import com.npt.bridge.util.FileUtils;
import com.npt.thirdpart.fineReport.extend.entity.FindReportHistory;
import com.npt.thirdpart.fineReport.extend.manager.FindReportHistoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.summer.extend.orm.Pagination;
import org.summer.extend.orm.condition.Conditions;

import java.io.File;

/**
 * 项目: NPTRPT
 * 作者: 张磊
 * 日期: 17/1/9 下午4:21
 * 备注:
 */
@Controller("summer.findReportHistoryAction")
@Scope("prototype")
public class FindReportHistoryAction extends AbstractPaginationAction<FindReportHistory> {
    @Autowired
    private FindReportHistoryManager findReportHistoryManager;

    /**
     * 作者: 张磊
     * 日期: 17/1/9 下午4:22
     * 备注:
     */
    public String index() {
        return list();
    }

    /**
     * 作者: 张磊
     * 日期: 17/1/9 下午6:28
     * 备注:
     */
    public String list() {
        Integer beginIndex = this.getIntParameter("beginIndex");
        Integer currPage = this.getIntParameter("currPage");
        Integer pageSize = this.getIntParameter("pageSize");

        currPage = currPage == null ? 1 : currPage;
        pageSize = pageSize == null ? this.configPageSize() : pageSize;
        beginIndex = (beginIndex == null ? ((currPage - 1) * pageSize + 1) : beginIndex) - 1;

        Pagination<FindReportHistory> dataPagination = this.findReportHistoryManager.findAll(beginIndex, pageSize, Conditions.eq("rptReport.id", this.getLongParameter("id")), Conditions.desc(NptBaseEntity.PROPERTY_CREATE_TIME));
        this.setAttribute("dataPagination", createPaginationResult(dataPagination, currPage, pageSize));
        return SUCCESS;
    }

    /**
     * 作者: 张磊
     * 日期: 17/1/9 下午6:28
     * 备注:
     */
    public void delete() {
        Long id = this.getLongParameter("id");
        findReportHistoryManager.deleteById(id);
    }

    /**
     * 作者: 张磊
     * 日期: 17/1/9 下午6:28
     * 备注:
     */
    @Override
    protected JSONObject toJSON(FindReportHistory data) {
        JSONObject result = new JSONObject();
        result.put("id", data.getId());
        result.put("rptPath", data.getRptPath());
        result.put("createTime", data.getCreateTime());
        result.put("status", data.getStatus());
        return result;
    }

    /**
     * 作者: 张磊
     * 日期: 17/1/6 下午4:56
     * 备注: 下载报表模板
     */
    public void download() {
        Long id = this.getLongParameter("id");
        FindReportHistory history = findReportHistoryManager.findById(id);
        String rptPath = history.getRptPath();
        String fileName = rptPath.substring(0, rptPath.lastIndexOf("-")) + rptPath.substring(rptPath.lastIndexOf("."));
        String filePath = this.getSession().getServletContext().getRealPath("") + "/WEB-INF/reportlets/" + rptPath;

        if (new File(filePath).exists()) {
            FileUtils.download(this.getResponse(), filePath, fileName);
        } else {
            this.outputErrorResult("文件'" + fileName + "'不存在");
        }
    }
}
